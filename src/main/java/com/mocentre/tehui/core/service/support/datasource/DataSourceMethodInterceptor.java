package com.mocentre.tehui.core.service.support.datasource;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;

public class DataSourceMethodInterceptor implements MethodInterceptor {

    public static final Map<Integer, String> sources = new HashMap<Integer, String>();

    static {
        sources.put(0, "slaveDS1");
        sources.put(1, "slaveDS2");
        sources.put(2, "slaveDS3");
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        DataSourceContextHolder.clearCustomerType();
        String dsStr = null;
        DataSource ds = null;
        Method method = methodInvocation.getMethod();
        ds = method.getAnnotation(DataSource.class);
        if (ds == null) {
            Class<?> clazz = methodInvocation.getThis().getClass();
            ds = clazz.getAnnotation(DataSource.class);
        }
        if (ds != null) {
            dsStr = ds.value();
        }
        if (!StringUtils.isEmpty(dsStr)) {
            if ("write".equals(dsStr)) {
                DataSourceContextHolder.setCustomerType("masterDS");
            } else if ("read".equals(dsStr)) {
                Long times = new Date().getTime();
                Integer i = (int) (times % sources.size());
                String dataSource = sources.get(i);
                DataSourceContextHolder.setCustomerType(dataSource);
            }
        } else {
            DataSourceContextHolder.setCustomerType("masterDS");
        }
        Object result = methodInvocation.proceed();
        return result;
    }
}
