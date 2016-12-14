package com.pxf.first.frame.app.utils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

/**
 * ��չApache Commons BeanUtils, �ṩһЩ���䷽��ȱʧ�ķ�װ.
 */

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    protected static Logger logger = Logger.getLogger(BeanUtils.class);

    /**
     * ��дorg.apache.commons.beanutils.BeanUtils��copyProperties����
     */
    public static void copyProperties(Object dest, Object orig) throws IllegalAccessException,
            InvocationTargetException {

        registerConverter();
        BeanUtilsBean.getInstance().copyProperties(dest, orig);
    }

    /**
     * ע������ת�������Ա����������⣺
     * 
     * 1��������ֵ�ͣ���Դ�����е��ֶ�Ϊnull������ת����0�ȣ�
     * 
     */
    public static void registerConverter() {
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new FloatConverter(null), Float.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
        ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);
        ConvertUtils.register(new SqlTimestampConverter(null), Timestamp.class);
    }
    
    /**
     * ֱ�Ӷ�ȡ��������ֵ,����private/protected���η�,������getter����.
     */
    public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException {
        Field field = getDeclaredField(object, fieldName);
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            logger.error("�������׳����쳣{}", e);
        }
        return result;
    }

    /**
     * ֱ�����ö�������ֵ,����private/protected���η�,������setter����.
     */
    public static void setFieldValue(Object object, String fieldName, Object value) throws NoSuchFieldException {
        Field field = getDeclaredField(object, fieldName);
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            logger.error("�������׳����쳣:{}", e);
        }
    }

    /**
     * ѭ������ת��,��ȡ�����DeclaredField.
     */
    public static Field getDeclaredField(Object object, String fieldName) throws NoSuchFieldException {
        Assert.notNull(object);
        return getDeclaredField(object.getClass(), fieldName);
    }

    /**
     * ѭ������ת��,��ȡ���DeclaredField.
     */
    public static Field getDeclaredField(Class clazz, String fieldName) throws NoSuchFieldException {
        Assert.notNull(clazz);
        Assert.hasText(fieldName);
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // Field���ڵ�ǰ�ඨ��,��������ת��
            }
        }
        throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + fieldName);
    }

    /**
     * �ж�object�Ƿ�Ϊ��(object == null��object���������Ծ�Ϊnull)
     * 
     * @param obj
     * @return
     */
    public static boolean isObjectNull(Object obj) {
        if (obj == null)
            return true;
        Field[] fieldArr = obj.getClass().getDeclaredFields();
        if (fieldArr != null) {
            for (int i = 0; i < fieldArr.length; i++) {
                Field field = fieldArr[i];
                String fieldName = field.getName();
                if ("UUID".equals(fieldName)) {
                    continue;
                }
                try {
                    Object val = BeanUtils.getFieldValue(obj, fieldName);
                    if ("".equals(val)) {
                        continue;
                    }
                    if (val != null) {
                        return false;
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    throw new RuntimeException(ex.getMessage());
                }
            }
        }
        return true;
    }

    /**
     * �ж�object�Ƿ�Ϊ��(object == null��object���������Ծ�Ϊnull�򡰡�)
     * 
     * @param obj
     * @return
     */
    public static boolean isObjectEmpty(Object obj) {
        if (obj == null)
            return true;
        Field[] fieldArr = obj.getClass().getDeclaredFields();
        if (fieldArr != null) {
            for (int i = 0; i < fieldArr.length; i++) {
                Field field = fieldArr[i];
                String fieldName = field.getName();
                if ("UUID".equals(fieldName)) {
                    continue;
                }
                try {
                    Object val = BeanUtils.getFieldValue(obj, fieldName);
                    if (val != null && !"".equals(val)) {
                        return false;
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    throw new RuntimeException(ex.getMessage());
                }
            }
        }
        return true;
    }

    /**
     * ��������Ĺ���������
     * 
     * @param obj �������
     * @param foreignKey ���������
     * @return
     * @throws NoSuchFieldException
     */
    public static void setForeign(Object obj, String foreignKey) throws NoSuchFieldException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!String.class.equals(field.getType()) && !Integer.class.equals(field.getType())
                    && !Long.class.equals(field.getType()) && !Timestamp.class.equals(field.getType())
                    && !Date.class.equals(field.getType()) && !Set.class.equals(field.getType())
                    && !Short.class.equals(field.getType()) && !Double.class.equals(field.getType())
                    && !BigDecimal.class.equals(field.getType())) {
            	String fieldName = field.getName();
                Object val = BeanUtils.getFieldValue(obj, fieldName);
                if ("UUID".equals(field.getName())) {
                    continue;
                } else if (val != null) {
                    if (List.class.equals(field.getType())) {
                        for (Object o : (List) val) {
                            BeanUtils.setFieldValue(o, foreignKey, BeanUtils.getFieldValue(obj, foreignKey));
                        }
                    } else {
                        BeanUtils.setFieldValue(val, foreignKey, BeanUtils.getFieldValue(obj, foreignKey));
                    }
                }
            }
        }
    }

    /**
     * �����ǿ�����
     * 
     * @param target Ŀ�����
     * @param source ԭ����
     * @return
     * @throws NoSuchFieldException
     */
    public static void copyNotNullProps(Object target, Object source) {
        Field[] fieldArr = source.getClass().getDeclaredFields();
        if (fieldArr != null) {
            for (int i = 0; i < fieldArr.length; i++) {
                Field field = fieldArr[i];
                String fieldName = field.getName();
                if (fieldName.equals("UUID")) {
                    continue;
                }
                try {
                    Object val = BeanUtils.getFieldValue(source, fieldName);
                    if (val != null && ifExistField(target, fieldName)) {
                        BeanUtils.setFieldValue(target, fieldName, val);
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    throw new RuntimeException(ex.getMessage());
                }
            }
        }
    }
    

    /**
     * �����ǿ�����,���˵�����Ҫ����������
     * 
     * @param target
     * @param source
     * @param skipList
     */
    public static void copyNotNullProps(Object target, Object source, List skipList) {
        Field[] fieldArr = source.getClass().getDeclaredFields();
        if (fieldArr != null) {
            for (int i = 0; i < fieldArr.length; i++) {
                Field field = fieldArr[i];
                String fieldName = field.getName();
                if (skipList.contains(fieldName)||"UUID".equals(fieldName)) {
                    continue;
                }
                try {
                    Object val = BeanUtils.getFieldValue(source, fieldName);
                    if (val != null && ifExistField(target, fieldName)) {
                        BeanUtils.setFieldValue(target, fieldName, val);
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    throw new RuntimeException(ex.getMessage());
                }
            }
        }
    }
    /**
     * ��������,���˵�����Ҫ����������
     * 
     * @param target
     * @param source
     * @param skipList
     */
    public static void copySkipProps(Object target, Object source, List<String> skipList) {
        Field[] fieldArr = source.getClass().getDeclaredFields();
        if (fieldArr != null) {
            for (int i = 0; i < fieldArr.length; i++) {
                Field field = fieldArr[i];
                String fieldName = field.getName();
                if (skipList.contains(fieldName)||"UUID".equals(fieldName)) {
                    continue;
                }
                try {
                    Object val = BeanUtils.getFieldValue(source, fieldName);
                    if (ifExistField(target, fieldName)) {
                        BeanUtils.setFieldValue(target, fieldName, val);
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    throw new RuntimeException(ex.getMessage());
                }
            }
        }
    }

    /**
     * �ж������Ƿ����ڵ�ǰBEAN����
     * 
     * @param bean
     * @param fieldName
     * @return
     */
    private static boolean ifExistField(Object bean, String fieldName) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            if (name.equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

}

