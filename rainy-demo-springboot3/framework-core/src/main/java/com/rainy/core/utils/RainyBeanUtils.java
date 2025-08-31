package com.rainy.core.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class RainyBeanUtils extends org.springframework.beans.BeanUtils {

    // 获取源对象中所有值为null的属性名
    private static String[] getNullPropertyNames(Object source) {
        BeanWrapper srcWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = srcWrapper.getPropertyDescriptors();
        Set<String> nullPropNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            String propName = pd.getName();
            if (srcWrapper.getPropertyValue(propName) == null) {
                nullPropNames.add(propName);
            }
        }
        return nullPropNames.toArray(new String[0]);
    }

    // 复制时忽略null值
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        String[] ignoreProps = getNullPropertyNames(source);
        copyProperties(source, target, ignoreProps);
    }
}
