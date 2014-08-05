package com.zk.rpc.common.server;

import java.lang.reflect.Method;

/**
 * Method Key生成器
 * @author sunney
 *
 */
public class MethodKeyImpl implements MethodKey{

	@Override
	public String methodKey(String beanName, Method method, Class<?>[] argTypes) {
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(beanName).append("#").append(method.getName());
		for (Class<?> clazz : argTypes) {
			sbuilder.append("_").append(clazz.getName());
		}
		return sbuilder.toString();
	}
	
	public static void main(String[] args) {
//		MethodKey mk = new MethodKeyImpl();
//		Method[] methods = MethodKeyImpl.class.getMethods();
//		for (Method method : methods) {
//			String methodKey = mk.methodKey("hello", method, method.getParameterTypes());
//			System.out.println(methodKey);
////			if(method.getName().equals("doSth") && method.getParameterTypes().length == 3){
////			}
//		}
	}
}
