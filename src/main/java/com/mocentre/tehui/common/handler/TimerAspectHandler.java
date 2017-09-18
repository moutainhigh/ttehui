//package com.mocentre.tehui.common.handler;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//import com.mocentre.tehui.common.utils.LoggerUtil;
//
///**
// * 类TimerAspectHandler.java的实现描述：Service方法执行时间
// * 
// * @author sz.gong 2017年6月12日 下午2:10:57
// */
//@Component
//@Aspect
//public class TimerAspectHandler {
//
//    @Around("execution(* com.mocentre.*.*.service.*.*(..))")
//    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//
//        long time = System.currentTimeMillis();
//        Object retVal = pjp.proceed(pjp.getArgs());
//        String className = pjp.getTarget().getClass().getName();
//        String mothodName = pjp.getSignature().getName();
//        time = System.currentTimeMillis() - time;
//        LoggerUtil.tehuiLog.debug("--------------- class:" + className + "." + mothodName + " process time: " + time
//                + " ms");
//        return retVal;
//    }
//}
