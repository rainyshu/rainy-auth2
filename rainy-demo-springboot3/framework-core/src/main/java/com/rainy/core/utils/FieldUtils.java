package com.rainy.core.utils;

import com.rainy.core.entity.BaseEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FieldUtils {

    /**
     * 驼峰与下划线转换规则
     */
    private static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");
    /**
     * 实体最大向上深度值
     */
    private static final Integer SUPPER_DEPEND_MAX = 5;

    public static List<String> allTableFields(Class clazz){
        return FieldUtils.allFields(clazz).stream().map(item->humpToLine2(item.getName())).collect(Collectors.toList());
    }

    public static List<String> allNameParamsFields(Class clazz){
        return FieldUtils.allFields(clazz).stream().map(item->":"+item.getName()).collect(Collectors.toList());
    }

    public static List<Field> allFields(Class clazz){
        ArrayList<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        return fields;
    }

    public static List<Field> allEntityFields(Class clazz){
        ArrayList<Field> fields = new ArrayList<>();
        for (int i = 0; i < SUPPER_DEPEND_MAX; i++) {
            if (clazz.getSuperclass().equals(Object.class)) {
                break;
            } else if (clazz.getSuperclass().equals(BaseEntity.class)) {
                fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
                fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
                break;
            }
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * 所有字母大写时toLower(),
     * 所有字母小写时启用驼峰转换，
     * 大小写字母都有，不转换
     * 下划线转驼峰
     */
    public static String underlineToCamel(String name){
        boolean allUpper = true;
        boolean allLower = true;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if(Character.isLowerCase(c)){
                allUpper = false;
            }else if (Character.isUpperCase(c)){
                allLower = false;
            }
        }

        if (allUpper){
            name = name.toLowerCase();
            allLower = true;
        }
        if (!allLower){
            return name;
        }

        StringBuilder sb = new StringBuilder(name.length());
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if ('_' == c) {
                if (++i < name.length()){
                    sb.append(Character.toUpperCase(name.charAt(i)));
                }
            }else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     */
    public static String humpToLine2(String str) {
        Matcher matcher = HUMP_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}

