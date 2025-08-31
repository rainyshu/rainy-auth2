package com.rainy.core.server.impl;

import com.rainy.core.common.Condition;
import com.rainy.core.common.Constants;
import com.rainy.core.common.jdbc.*;
import com.rainy.core.entity.BizEntity;
import com.rainy.core.server.IBaseDao;
import com.rainy.core.server.jpa.BaseJpaRepository;
import com.rainy.core.utils.FieldUtils;
import com.rainy.core.utils.JsonObjectUtils;
import com.rainy.core.utils.RainyBeanUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rainy.shu
 */
public abstract class BaseDaoImpl<E extends BizEntity> implements IBaseDao<E>, SmartInitializingSingleton {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BaseJpaRepository<E> baseJpaRepository;

    private List<Field> fields;

    private Class<E> clazz;

    @Override
    public void afterSingletonsInstantiated() {
        this.baseJpaRepository = getBaseJpaRepository();
        this.clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 获取注入处理对象
     * @return 返回
     */
    public abstract BaseJpaRepository<E> getBaseJpaRepository();

    public E add(E entity) {
        entity.setIsDeleted(Constants.IS_DELETED_FALSE);
        entity.setRecordVersion(0L);
        return baseJpaRepository.save(entity);
    }

    public <C extends JdbcCondition> List<E> getListByCondition(C condition, Class<E> clazz) {
        Specification<E> specification = (root, query, cb) -> null;
        List<Specification<E>> list = new ArrayList<>();
        try {
            list = getSpecification(condition, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (condition.getPageNo() < 1) {
            condition.setPageNo(1);
        }
        for (Specification<E> sp : list) {
            specification = specification.and(sp);
        }
        return baseJpaRepository.findAll(specification);
    }

    public <C extends JdbcCondition> Page<E> getPageByCondition(C condition, Class<E> clazz) {
        logger.info("BaseDaoImpl getPageByCondition entry:{}, clazz:{}", JsonObjectUtils.objToStr(condition), clazz.getSimpleName());
        Specification<E> specification = (root, query, cb) -> null;
        List<Specification<E>> list = new ArrayList<>();
        try {
            list = getSpecification(condition, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (condition.getPageNo() < 1) {
            condition.setPageNo(1);
        }
        List<Sort.Order> orders = getOrders(condition);
        PageRequest pageRequest = PageRequest.of(condition.getPageNo() - 1, condition.getPageSize(), Sort.by(orders));
        org.springframework.data.domain.Page<E> dbPage = baseJpaRepository.findAll(specification, pageRequest);
        Page<E> page = new Page();
        page.setPageNo(condition.getPageNo());
        page.setPageSize(condition.getPageSize());
        page.setTotalNum(dbPage.getTotalElements());
        page.setData(dbPage.getContent());
        return page;
    }

    public E delete(E entity) {
        baseJpaRepository.delete(entity);
        return entity;
    }

    public E deleteLogic(E entity) {
        entity.setIsDeleted(Constants.IS_DELETED_TRUE);
        baseJpaRepository.save(entity);
        return entity;
    }

    public E update(E entity) {
        E dbE = baseJpaRepository.findById(entity.getId()).orElse(null);
        if (null == dbE) {
            return entity;
        }
        RainyBeanUtils.copyPropertiesIgnoreNull(entity, dbE);
        baseJpaRepository.save(dbE);
        return dbE;
    }

    @Override
    public E getById(Long id) {
        E obj = null;
        try {
            obj = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        obj.setIsDeleted(0);
        obj.setId(id);
        Example<E> example = Example.of(obj);
        List<E> list = baseJpaRepository.findAll(example);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 条件参数处理
     * @param condition 参数
     * @param clazz 参数
     * @return 返回
     */
    private <C extends JdbcCondition> List<Specification<E>> getSpecification(C condition, Class<E> clazz) throws Exception {
        List<Specification<E>> list = new ArrayList<>();
        List<QueryParam> queryParams = null == condition.getQueryParams() ? new ArrayList<>() : condition.getQueryParams();
        for (QueryParam queryParam : queryParams) {
            Specification<E> sp = getPredicateByQueryParam(queryParam);
            list.add(sp);
        }
        List<Field> matchField = filterField(condition, clazz);
        for (Field field : matchField) {
            field.setAccessible(true);
            Object obj = field.get(condition);
            if (null == obj) {
                continue;
            }
            list.add(new Specification<E>() {
                @Override
                public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.equal(root.get(field.getName()), obj);
                }
            });
        }

        return list;
    }

    /**
     * 获取参数
     * @param queryParam 参数
     * @return 返回
     */
    private Specification<E> getPredicateByQueryParam(QueryParam queryParam) {
        Specification<E> sp = null;
        OpJpaType opJpaType = queryParam.getOpJpaType();
        Object value = queryParam.getValue();
        switch (opJpaType) {
            case EQUAL:
                sp = new Specification<E>() {
                    @Override
                    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                        return criteriaBuilder.equal(root.get(queryParam.getFieldName()), value);
                    }
                };
                break;
            case NOT_EQUAL:
                sp = new Specification<E>() {
                    @Override
                    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                        return criteriaBuilder.notEqual(root.get(queryParam.getFieldName()), value);
                    }
                };
                break;
            case LIKE:
                String searchWord = (String) value;
                if (searchWord.indexOf("%") == -1) {
                    searchWord = "%" + searchWord + "%";
                }
                final String finalSearchWord = searchWord;
                sp = new Specification<E>() {
                    @Override
                    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                        return criteriaBuilder.like(root.get(queryParam.getFieldName()).as(String.class), finalSearchWord);
                    }
                };
                break;
            case NOT_LIKE:
                String searchWord1 = (String) value;
                if (searchWord1.indexOf("%") == -1) {
                    searchWord1 = "%" + searchWord1 + "%";
                }
                final String finalSearchWord1 = searchWord1;
                sp = new Specification<E>() {
                    @Override
                    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                        return criteriaBuilder.notLike(root.get(queryParam.getFieldName()).as(String.class), finalSearchWord1);
                    }
                };
                break;
            case IN:
                sp = new Specification<E>() {
                    @Override
                    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get(queryParam.getFieldName()));
                        if (value instanceof List) {
                            for (Object val : (List) value) {
                                in.value(val);
                            }
                        } else if (value instanceof Object[]) {
                            for (Object val : (Object[]) value) {
                                in.value(val);
                            }
                        }
                        return in;
                    }
                };
                break;
            case NOT_IN:
                sp = new Specification<E>() {
                    @Override
                    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                        CriteriaBuilder.In<Object> notIn = criteriaBuilder.in(root.get(queryParam.getFieldName()));
                        if (value instanceof List) {
                            for (Object val : (List) value) {
                                notIn.value(val);
                            }
                        } else if (value instanceof Object[]) {
                            for (Object val : (Object[]) value) {
                                notIn.value(val);
                            }
                        }
                        return criteriaBuilder.not(notIn);
                    }
                };
                break;
            case NULL:
                sp = new Specification<E>() {
                    @Override
                    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                        return criteriaBuilder.isNull(root.get(queryParam.getFieldName()));
                    }
                };
                break;
            case NOT_NULL:
                sp = new Specification<E>() {
                    @Override
                    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                        return criteriaBuilder.isNotNull(root.get(queryParam.getFieldName()));
                    }
                };
                break;
            case LESS_THAN_OR_EQUAL_TO:
                if (value instanceof Number) {
                    sp = new Specification<E>() {
                        @Override
                        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                            return criteriaBuilder.le(root.get(queryParam.getFieldName()).as(Number.class), (Number) value);
                        }
                    };
                } else if (value instanceof String) {
                    sp = new Specification<E>() {
                        @Override
                        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                            return criteriaBuilder.lessThanOrEqualTo(root.get(queryParam.getFieldName()).as(String.class), (String) value);
                        }
                    };
                } else if (value instanceof Date) {
                    sp = new Specification<E>() {
                        @Override
                        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                            return criteriaBuilder.lessThanOrEqualTo(root.get(queryParam.getFieldName()).as(Date.class), (Date) value);
                        }
                    };
                }
                break;
            case LESS_THAN:
                if (value instanceof Number) {
                    sp = new Specification<E>() {
                        @Override
                        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                            return criteriaBuilder.lt(root.get(queryParam.getFieldName()).as(Number.class), (Number) value);
                        }
                    };
                } else if (value instanceof String) {
                    sp = new Specification<E>() {
                        @Override
                        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                            return criteriaBuilder.lessThan(root.get(queryParam.getFieldName()).as(String.class), (String) value);
                        }
                    };
                } else if (value instanceof Date) {
                    sp = new Specification<E>() {
                        @Override
                        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                            return criteriaBuilder.lessThan(root.get(queryParam.getFieldName()).as(Date.class), (Date) value);
                        }
                    };
                }
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                if (value instanceof Number) {
                    sp = new Specification<E>() {
                        @Override
                        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                            return criteriaBuilder.ge(root.get(queryParam.getFieldName()).as(Number.class), (Number) value);
                        }
                    };
                } else if (value instanceof String) {
                    sp = new Specification<E>() {
                        @Override
                        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                            return criteriaBuilder.greaterThanOrEqualTo(root.get(queryParam.getFieldName()).as(String.class), (String) value);
                        }
                    };
                } else if (value instanceof Date) {
                    sp = new Specification<E>() {
                        @Override
                        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                            return criteriaBuilder.greaterThanOrEqualTo(root.get(queryParam.getFieldName()).as(Date.class), (Date) value);
                        }
                    };
                }
                break;
            case GREATER_THAN:
                if (value instanceof Number) {
                    sp = new Specification<E>() {
                        @Override
                        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                            return criteriaBuilder.gt(root.get(queryParam.getFieldName()).as(Number.class), (Number) value);
                        }
                    };
                } else if (value instanceof String) {
                    sp = new Specification<E>() {
                        @Override
                        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                            return criteriaBuilder.greaterThan(root.get(queryParam.getFieldName()).as(String.class), (String) value);
                        }
                    };
                } else if (value instanceof Date) {
                    sp = new Specification<E>() {
                        @Override
                        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                            return criteriaBuilder.greaterThan(root.get(queryParam.getFieldName()).as(Date.class), (Date) value);
                        }
                    };
                }
                break;
            case TRUE:
                sp = new Specification<E>() {
                    @Override
                    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                        return criteriaBuilder.isTrue(root.get(queryParam.getFieldName()));
                    }
                };
                break;
            case FALSE:
                sp = new Specification<E>() {
                    @Override
                    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                        return criteriaBuilder.isFalse(root.get(queryParam.getFieldName()));
                    }
                };
                break;
            default:
                break;
        }
        return sp;
    }

    /**
     * 过滤条件
     * @param condition 参数
     * @param clazz 参数
     * @return 返回
     */
    private <C extends Condition> List<Field> filterField(C condition, Class<E> clazz) {
        List<Field> matchField = new ArrayList<>();
        List<Field> entityFields = getFields();
        List<String> entityFieldNames = entityFields.stream().map(Field::getName).collect(Collectors.toList());
        List<Field> conditionFields = FieldUtils.allFields(condition.getClass());
        for (Field conditionField : conditionFields) {
            if (entityFieldNames.contains(conditionField.getName())) {
                matchField.add(conditionField);
            }
        }
        return matchField;
    }

    /**
     * 获取配置
     * @param fieldName 参数
     * @return 返回值
     */
    private Field getField(String fieldName) {
        List<Field> entityFields = getFields();
        for (Field field : entityFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

    /**
     * 获取实体字段
     * @return 返回
     */
    private List<Field> getFields() {
        if (null == fields) {
            synchronized (this) {
                if (null == fields) {
                    fields = FieldUtils.allEntityFields(clazz);
                }
            }
        }
        return fields;
    }

    /**
     * 获取排序信息
     * @param condition 参数
     * @return 返回
     */
    private <C extends JdbcCondition> List<Sort.Order> getOrders(C condition) {
        List<Sort.Order> orders = new ArrayList<>();
        if (null == condition.getQueryOrders()) {
            orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
        } else {
            for (QueryOrder queryOrder : condition.getQueryOrders()) {
                switch (queryOrder.getOrderJpaType()) {
                    case ASC: {
                        orders.add(new Sort.Order(Sort.Direction.ASC, queryOrder.getFieldName()));
                        break;
                    }
                    case DESC: {
                        orders.add(new Sort.Order(Sort.Direction.DESC, queryOrder.getFieldName()));
                        break;
                    }
                }
            }
        }
        return orders;
    }
}

