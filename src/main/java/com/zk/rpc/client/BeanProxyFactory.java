package com.zk.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.zk.rpc.common.common.NetConfig;
import com.zk.rpc.common.common.TransportURL;

/**
 * 动态代理工厂类，主要用于创建Bean代理
 * 
 * @author sunney
 * 
 */
public class BeanProxyFactory {

	private BeanProxyFactory() {
	}

	public static <T> T create(Class<T> api, String urn) {
		return create(api, urn, new NetConfig());
	}

	public static <T> T create(Class<T> api, String urn, NetConfig netConfig) {
		return create(api, urn, netConfig, Thread.currentThread().getContextClassLoader());
	}

	public static <T> T create(Class<T> api, String urn, ClassLoader loader) {
		return create(api, urn, new NetConfig(), loader);
	}

	public static <T> T create(Class<T> api, String urn, NetConfig netConfig, ClassLoader loader) {
		if (api == null) {
			throw new NullPointerException("api must not be null!");
		}
		TransportURL u = new TransportURL(urn);
		InvocationHandler invokeHandler = new BeanProxy(u, netConfig);
		return api.cast(Proxy.newProxyInstance(loader, new Class[] { api }, invokeHandler));
	}

}
