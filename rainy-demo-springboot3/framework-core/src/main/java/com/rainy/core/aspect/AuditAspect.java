package com.rainy.core.aspect;

import com.rainy.core.common.Constants;
import com.rainy.core.entity.BizEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class AuditAspect {

    /**
     * 拦截所有Repository的save方法
     */
    @Before("execution(* org.springframework.data.jpa.repository.JpaRepository+.save(..))")
    public void beforeSave(JoinPoint joinPoint) {
        Object entity = joinPoint.getArgs()[0];
        if (entity instanceof BizEntity) {
            BizEntity auditEntity = (BizEntity) entity;
            String currentUser = getCurrentUsername();
            Date now = new Date();

            // 新增操作
            if (isNewEntity(auditEntity)) {
                if (auditEntity.getCreateUser() == null) {
                    auditEntity.setCreateUser(currentUser);
                }
                if (auditEntity.getCreateTime() == null) {
                    auditEntity.setCreateTime(now);
                }
                auditEntity.setIsDeleted(Constants.IS_DELETED_FALSE);
                auditEntity.setRecordVersion(Constants.DEFAULT_RECORD_VERSION_VALUE);
            }
            // 更新操作（总是设置）
            auditEntity.setUpdateUser(currentUser);
            auditEntity.setUpdateTime(now);
        }
    }

    /**
     * 获取当前用户名（需根据安全框架调整）
     */
    private String getCurrentUsername() {
        // 无安全框架时返回默认值
        return "system";
    }

    /**
     * 判断是否为新增实体（根据主键是否为空）
     */
    private boolean isNewEntity(BizEntity entity) {
        try {
            // 使用反射获取ID字段
            java.lang.reflect.Field idField = entity.getClass().getSuperclass().getDeclaredField("id");
            idField.setAccessible(true);
            Object idValue = idField.get(entity);
            return idValue == null;
        } catch (Exception e) {
            throw new RuntimeException("无法确定实体状态", e);
        }
    }
}
