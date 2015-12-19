package com.zk.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.zk.rpc.bean.RpcRequest;
import com.zk.rpc.client.netty.NettyClient;
import com.zk.rpc.common.client.Client;
import com.zk.rpc.common.common.NetConfig;
import com.zk.rpc.common.common.TransportURL;
import com.zk.rpc.common.server.MethodKey;
import com.zk.rpc.common.server.MethodKeyImpl;

/**
 * 代理类
 * 
 * @author sunney
 * 
 */
public class BeanProxy implements InvocationHandler {
	private Client client;
	private MethodKey methodKey;

	public BeanProxy(TransportURL u, NetConfig netConfig) {
		this.client = new NettyClient(u);
		methodKey = new MethodKeyImpl();
	}

	@Override
	public Object invoke(Object bean, Method method, Object[] args) throws Throwable {
		return client.invoke(invokeArgsToRequest(method, args));
	}

	/**
	 * invoke参数转换为请求对象
	 * @param method
	 * @param args
	 * @return
	 */
	private RpcRequest invokeArgsToRequest(Method method, Object[] args) {
		String _beanName = client.getBeanName();
		return new RpcRequest(_beanName, methodKey.methodKey(_beanName, method, method.getParameterTypes()), args);
	}

}
