package com.zk.rpc.test.beanRegister;

import java.lang.reflect.Method;

import com.zk.rpc.common.server.AbstractBeanRegister;
import com.zk.rpc.common.server.Tuple;
import com.zk.rpc.test.bean.HelloImpl;

public class BeanRegisterImpl extends AbstractBeanRegister {
	
	@Override
	public void initBeanRepository() {
		String beanName = "hello";
		Object bean = new HelloImpl();
		register(beanName, bean);
	}

	@Override
	public void register(String beanName, Object bean) {
		beanMap.put(beanName, bean);
		
		Method[] methods = bean.getClass().getMethods();
		for (Method method : methods) {
			methodMap.put(methodKey.methodKey(beanName, method, method.getParameterTypes()), method);
		}
	}

	@Override
	public Tuple<Object, Method> lookup(String beanName, String methodKey) {
		return new Tuple<Object, Method>(beanMap.get(beanName), methodMap.get(methodKey));
	}

}
