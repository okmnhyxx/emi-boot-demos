package com.emi.boot.common.enums;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by emi on 2016/1/14.
 */
public class EnumUserType implements UserType, ParameterizedType {
    private Method recreateEnumMethod;
    private Method getValueMethod;
    private Class enumClass;
    private Class valueType;

    public EnumUserType() {
    }

    public void setParameterValues(Properties parameters) {
        if(parameters != null) {
            String recreateEnumMethodName = parameters.getProperty("recreateEnumMethod");
            String getValueMethodName = parameters.getProperty("getValueMethod");
            String className = parameters.getProperty("enumClass");

            try {
                this.enumClass = Class.forName(className);
                this.getValueMethod = this.enumClass.getMethod(getValueMethodName, new Class[0]);
                this.valueType = this.getValueMethod.getReturnType();
                this.recreateEnumMethod = this.enumClass.getMethod(recreateEnumMethodName, new Class[]{this.valueType});
            } catch (ClassNotFoundException var6) {
                throw new HibernateException(var6);
            } catch (NoSuchMethodException var7) {
                throw new HibernateException(var7);
            }
        }

    }

    public int[] sqlTypes() {
        return this.valueType.equals(Integer.TYPE)?new int[]{4}:(this.valueType.equals(String.class)?new int[]{1}:new int[0]);
    }

    public Class returnedClass() {
        return this.enumClass;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        return this.nullSafeEquals(x, y);
    }

    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Object value = null;
        if(this.valueType.equals(Integer.TYPE)) {
            value = Integer.valueOf(rs.getInt(names[0]));
        } else if(this.valueType.equals(String.class)) {
            value = rs.getString(names[0]);
        }

        Object returnValue = null;
        if(value == null) {
            return null;
        } else {
            try {
                returnValue = this.recreateEnumMethod.invoke(this.enumClass, new Object[]{value});
                return returnValue;
            } catch (IllegalAccessException var8) {
                throw new HibernateException(var8);
            } catch (InvocationTargetException var9) {
                throw new HibernateException(var9);
            }
        }
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if(value == null) {
            st.setObject(index, (Object)null);
        } else {
            try {
                if(this.valueType.equals(Integer.TYPE)) {
                    int ex = Integer.parseInt(this.getValueMethod.invoke(value, new Object[0]).toString());
                    st.setInt(index, ex);
                } else if(this.valueType.equals(String.class)) {
                    String ex1 = (String)this.getValueMethod.invoke(value, new Object[0]);
                    st.setString(index, ex1);
                }
            } catch (IllegalAccessException var6) {
                throw new HibernateException(var6);
            } catch (InvocationTargetException var7) {
                throw new HibernateException(var7);
            }
        }

    }

    public Object deepCopy(Object value) throws HibernateException {
        if(value != null) {
            try {
                Object e = this.getValueMethod.invoke(value, new Object[0]);
                return this.recreateEnumMethod.invoke(this.enumClass, new Object[]{e});
            } catch (IllegalAccessException var3) {
                throw new HibernateException(var3);
            } catch (InvocationTargetException var4) {
                new HibernateException(var4);
            }
        }

        return null;
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        Object deepCopy = this.deepCopy(value);
        return !(deepCopy instanceof Serializable)?(Serializable)deepCopy:null;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return this.deepCopy(cached);
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return this.deepCopy(original);
    }

    private boolean nullSafeEquals(Object o1, Object o2) {
        if(o1 == o2) {
            return true;
        } else if(o1 != null && o2 != null) {
            if(o1.equals(o2)) {
                return true;
            } else {
                if(o1.getClass().isArray() && o2.getClass().isArray()) {
                    if(o1 instanceof Object[] && o2 instanceof Object[]) {
                        return Arrays.equals((Object[]) ((Object[]) ((Object[]) o1)), (Object[]) ((Object[]) ((Object[]) o2)));
                    }

                    if(o1 instanceof boolean[] && o2 instanceof boolean[]) {
                        return Arrays.equals((boolean[])((boolean[])((boolean[])o1)), (boolean[])((boolean[])((boolean[])o2)));
                    }

                    if(o1 instanceof byte[] && o2 instanceof byte[]) {
                        return Arrays.equals((byte[])((byte[])((byte[])o1)), (byte[])((byte[])((byte[])o2)));
                    }

                    if(o1 instanceof char[] && o2 instanceof char[]) {
                        return Arrays.equals((char[])((char[])((char[])o1)), (char[])((char[])((char[])o2)));
                    }

                    if(o1 instanceof double[] && o2 instanceof double[]) {
                        return Arrays.equals((double[])((double[])((double[])o1)), (double[])((double[])((double[])o2)));
                    }

                    if(o1 instanceof float[] && o2 instanceof float[]) {
                        return Arrays.equals((float[])((float[])((float[])o1)), (float[])((float[])((float[])o2)));
                    }

                    if(o1 instanceof int[] && o2 instanceof int[]) {
                        return Arrays.equals((int[])((int[])((int[])o1)), (int[])((int[])((int[])o2)));
                    }

                    if(o1 instanceof long[] && o2 instanceof long[]) {
                        return Arrays.equals((long[])((long[])((long[])o1)), (long[])((long[])((long[])o2)));
                    }

                    if(o1 instanceof short[] && o2 instanceof short[]) {
                        return Arrays.equals((short[])((short[])((short[])o1)), (short[])((short[])((short[])o2)));
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }
}
