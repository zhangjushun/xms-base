package com.xms.common.utils.util;

import java.lang.reflect.Field;

public class EntityUtil {
    /**
     * 复制名称相同类型相同字段的值
     *
     * @param obj
     * @param clazz2
     * @param <T1>
     * @param <T2>
     * @return
     */
    public static <T1, T2> T2 copyData(T1 obj, Class<T2> clazz2) {
        //1. 获取源数据的类
        Class clazz1 = obj.getClass();//源数据类
        //2. 创建一个目标数据实例
        final T2 obj2 = getInstance(clazz2);
 
        //3. 获取clazz1和clazz2中的属性
        Field[] fields1 = clazz1.getDeclaredFields();
        Field[] fields2 = clazz2.getDeclaredFields();
        //4. 遍历fields2
        for (Field f1 : fields1) {
            //4-1. 遍历fields1，逐字段匹配
            for (Field f2 : fields2) {
                // 复制字段
                copyField(obj, obj2, f1, f2);
            }
        }
        return obj2;
    }
 
    /**
     * 按照字段表复制相同名称相同类型的字段的值
     *
     * @param obj
     * @param clazz2
     * @param fieldNames
     * @param <T1>
     * @param <T2>
     * @return
     */
    public static <T1, T2> T2 copyData(T1 obj, Class<T2> clazz2, String[] fieldNames) {
        //1. 获取源数据的类
        Class clazz1 = obj.getClass();//源数据类
        //2. 创建一个目标数据实例
        final T2 obj2 = getInstance(clazz2);
 
        //3. 获取clazz1和clazz2中的属性
        Field[] fields1 = clazz1.getDeclaredFields();
        Field[] fields2 = clazz2.getDeclaredFields();
 
        //4. 遍历字段列表
        for (String fieldName : fieldNames) {
            //5. 遍历fields1
            for (Field f1 : fields1) {
                //找到这个字段（找不到就不用遍历fields2）
                if (fieldName.equals(f1.getName())) {
                    //5-1. 遍历fields2，逐字段匹配
                    for (Field f2 : fields2) {
                        //在fields2中也要有这个字段
                        if (fieldName.equals(f2.getName())) {
                            //复制字段
                            copyField(obj, obj2, f1, f2);
                        }
                    }
                }
            }
        }
        return obj2;
    }
 
    /**
     * 复制相同名称相同类型的字段的值
     *
     * @param obj
     * @param obj2
     * @param f1
     * @param f2
     * @param <T1>
     * @param <T2>
     */
    private static <T1, T2> void copyField(T1 obj, T2 obj2, Field f1, Field f2) {
        try {
            //字段名要相同，字段类型也要相同
            if (f1.getName().equals(f2.getName())
                    & f1.getType().getName().equals(f2.getType().getName())) {
                //3-2. 获取obj这个字段的值
                f1.setAccessible(true);
                Object val = f1.get(obj);
                //3-3. 把这个值赋给obj2这个字段
                f2.setAccessible(true);
                f2.set(obj2, val);
                //3-4. 访问权限还原
                f2.setAccessible(false);
                f1.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 获得泛型类的实例
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getInstance(Class<T> tClass) {
        try {
            T t = tClass.newInstance();
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
