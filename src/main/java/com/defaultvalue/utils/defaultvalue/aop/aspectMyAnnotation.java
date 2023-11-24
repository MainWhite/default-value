package com.defaultvalue.utils.defaultvalue.aop;

import com.defaultvalue.utils.defaultvalue.annotation.DefaultValue;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 创建时间 date: 2023-11-24
 *
 * @author 白
 * @version 1.0
 * @see DefaultValue
 */
@Aspect
@Component
public class aspectMyAnnotation {

	/**
	 * 定义切入点 @annotation为注解
	 */
	@Pointcut("@annotation(com.defaultvalue.utils.defaultvalue.annotation.DefaultValue)")
	public void annotation() {}

	/**
	 * 切面
	 *
	 * @param joinPoint 使用该注解的方法
	 * @return 使用该注解的方法的返回值
	 */
	@Around("annotation()")
	public Object defaultValue(ProceedingJoinPoint joinPoint) {
		String methodName=joinPoint.getSignature().getName();// 获取使用该注解的方法名
		Object[] args=joinPoint.getArgs();// 获取使用该注解的方法参数
		MethodSignature methodSignature=(MethodSignature) joinPoint.getSignature();// MethodSignature对象是Signature对象的实现类 提供了对方法的操作
		Class<?>[] parameterTypes=methodSignature.getParameterTypes();// 获取方法的参数类型

		Class<?> useAnnotationMethod=joinPoint.getTarget().getClass();// 获取使用该注解的类
		Object ret;// 连接点方法的返回值
		try {
			Method methodData=useAnnotationMethod.getMethod(methodName,parameterTypes);// 获取调用该注解的目标对象
			DefaultValue defaultValue=methodData.getAnnotation(DefaultValue.class);// 获取调用该注解的注解对象
			String[] argsAnnotation=defaultValue.value();// 获取调用该注解中的value属性的值
			Object[] parameter=new Object[parameterTypes.length];// 给予连接点参数的数组
			int annotationIndex=0;// 注解中value的下标
			for (int i = 0; i < parameterTypes.length; i++) {
				// 如果给予的实参是null 那么给予默认值value 如果实参null超过了默认值数组 那么赋值实参 如果不是null赋值实参
				if (args[i]==null && argsAnnotation.length>annotationIndex) {
					parameter[i] = toType(argsAnnotation[!isCustomType(parameterTypes[i]) ? annotationIndex++ : annotationIndex], parameterTypes[i]);
				} else {
					parameter[i]=args[i];
				}
			}
			// 执行连接点方法
			ret=joinPoint.proceed(parameter);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		return ret;
	}

	/**
	 * 如果是value对应的类似基本类型或包装类型(包括String) 则将类型转换
	 * 如果不是 是自定义类型 则创建对象
	 * (这里测试过了 好像必须这样写 直接将String转为Object会报错)
	 *
	 * @param value 注解中的value属性
	 * @param type 实参的数据类型
	 * @return 转换后的类型
	 */
	private Object toType(String value,Class<?> type) {
		Object ret;
		if (type==int.class || type==Integer.class) {
			ret=Integer.parseInt(value);
		} else if (type == double.class || type==Double.class) {
			ret=Double.parseDouble(value);
		} else if (type == long.class || type == Long.class) {
			ret=Long.parseLong(value);
		} else if (type == String.class) {
			ret=value;
		} else if (type == short.class || type == Short.class) {
			ret=Short.parseShort(value);
		} else if (type == byte.class || type == Byte.class) {
			ret=Byte.parseByte(value);
		} else if (type==boolean.class || type==Boolean.class) {
			ret=Boolean.parseBoolean(value);
		} else if (type == float.class || type == Float.class) {
			ret=Float.parseFloat(value);
		} else {
			try {
				ret=type.getDeclaredConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
			         NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
		return ret;
	}

	/**
	 * 判断是否是自定义类型
	 * @param type 实参的类型
	 * @return 如果是true否则false
	 */
	private boolean isCustomType(Class<?> type) {
		return !type.isPrimitive() && !type.isArray() && !type.isEnum() && !type.isInterface() && !isWrapperClass(type);
	}

	/**
	 * 判断是否为基本数据类型的包装类
	 * @param type 实参的类型
	 * @return 如果是true否则false
	 */
	private static boolean isWrapperClass(Class<?> type) {
		return type == Integer.class ||
				type == Long.class ||
				type == Float.class ||
				type == Double.class ||
				type == Boolean.class ||
				type == Byte.class ||
				type == Short.class ||
				type == String.class ||
				type == Character.class;
	}
}
