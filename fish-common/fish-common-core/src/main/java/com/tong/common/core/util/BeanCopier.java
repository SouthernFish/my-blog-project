package com.tong.common.core.util;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author TR
 * @Create 2021/8/16 10:04
 * @Title: BeanCopier
 * @Description:
 */
public class BeanCopier {

    /**
     * 属性转换工具，对日期
     * 支持 Date转换成String 默认格式 yyyy-MM-dd HH:mm:ss
     */
    public static void copy(Object source, Object target) {

        BeanUtils.copyProperties(source, target);

        Field[] sourceDeclaredFields = getAllField(source.getClass());
        Field[] targetDeclaredFields = getAllField(target.getClass());
        for (Field sourceField : sourceDeclaredFields) {
            String simpleName = sourceField.getType().getSimpleName();
            if (simpleName.equals(Date.class.getSimpleName())
                    || simpleName.equals(LocalDateTime.class.getSimpleName())
                    || simpleName.equals(LocalDate.class.getSimpleName())) {
                Field targetField = getSameNameField(targetDeclaredFields, sourceField);
                if (targetField != null && targetField.getType().getSimpleName().equals(String.class.getSimpleName())) {
                    try {
                        sourceField.setAccessible(true);
                        targetField.setAccessible(true);
                        if (simpleName.equals(Date.class.getSimpleName()) && sourceField.get(source) != null) {
                            targetField.set(target, DateUtil.formatDateTime((Date) sourceField.get(source)));
                        } else if (simpleName.equals(LocalDateTime.class.getSimpleName()) && sourceField.get(source) != null) {
                            targetField.set(target, DateUtil.formatDateTime((LocalDateTime) sourceField.get(source)));
                        } else if (simpleName.equals(LocalDate.class.getSimpleName()) && sourceField.get(source) != null) {
                            LocalDate date = (LocalDate) sourceField.get(source);
                            targetField.set(target, date.format(DateTimeFormatter.ISO_DATE));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static <T> T copy(Object source, Class<T> clazz) {
        try {
            T t = clazz.getDeclaredConstructor().newInstance();
            copy(source, t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> listCopy(List<?> sources, Class<T> clazz) {
        if (sources == null) {
            return new ArrayList<>();
        }
        return sources.stream().map(source -> copy(source, clazz)).collect(Collectors.toList());
    }

    private static Field[] getAllField(Class<?> clazz) {
        Field[] array = null;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] field = clazz.getDeclaredFields();
            array = ArrayUtils.addAll(array, field);
        }
        return array;
    }

    private static Field getSameNameField(Field[] fields, Field targetField) {
        for (Field field : fields) {
            if (field.getName().equals(targetField.getName())) {
                return field;
            }
        }
        return null;
    }
}
