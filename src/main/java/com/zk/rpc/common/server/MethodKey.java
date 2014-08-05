package com.zk.rpc.common.server;

import java.lang.reflect.Method;

/**
 * Method的Key生成器
 * @author sunney
 *
 */
public interface MethodKey {
	/**
	 * @param beanName
	 * @param method
	 * @param argTypes
	 * @return
	 */
	String methodKey(String beanName, Method method, Class<?>[] argTypes);
}
