package com.zk.rpc.common.server;

import java.lang.reflect.Method;

/**
 * 服务器端提供服务的Bean注册器
 * 
 * @author sunney
 * 
 */
public interface BeanRegister {
	/**
	 * 本人已经实现一个抽象注册器 使用2个Map保存Bean及Method
	 * 客户端实现可以按照需要进行另外实现 不必具体实现此方法
	 */
	void initBeanRepository();

	/**
	 * 注册对外服务Bean
	 * (处理method的key请使用 MethodKey--MethodKeyImpl 接口的实现(因为客户端编程需要))
	 * @param beanName
	 * @param bean
	 */
	void register(String beanName, Object bean);

	/**
	 * 从Bean资源库中查找相应Bean
	 * 
	 * @param beanName
	 * @param methodKey
	 * @return
	 */
	Tuple<Object, Method> lookup(String beanName, String methodKey);
}
