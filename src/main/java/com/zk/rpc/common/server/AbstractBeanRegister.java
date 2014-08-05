package com.zk.rpc.common.server;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean注册器抽象工厂
 * @author sunney
 *
 */
public abstract class AbstractBeanRegister implements BeanRegister {

	protected Map<String,Object> beanMap = new HashMap<String, Object>();
	protected Map<String,Method> methodMap = new HashMap<String, Method>();
	protected MethodKey methodKey = null;
	
	public AbstractBeanRegister() {
		methodKey = new MethodKeyImpl();
		initBeanRepository();
	}

}
