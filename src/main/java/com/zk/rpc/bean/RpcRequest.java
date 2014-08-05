package com.zk.rpc.bean;

import java.util.Arrays;

/**
 * 客户端请求信息包装类
 * 
 * @author SunneyZhou
 * 
 */
public class RpcRequest extends RpcMessage {
	private static final long serialVersionUID = -9157514426825564946L;
	private int magic;// 请求ID
	private String beanName;// 服务器端代理调用的Bean ID
	private String methodKey;// 方法键值
	private Object[] args;// 方法调用参数

	public RpcRequest() {
	}

	public RpcRequest(String beanName, String methodKey, Object[] args) {
		this();
		this.beanName = beanName;
		this.methodKey = methodKey;
		this.args = args;
	}

	public int getMagic() {
		return magic;
	}

	public void setMagic(int magic) {
		this.magic = magic;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodKey() {
		return methodKey;
	}

	public void setMethodKey(String methodKey) {
		this.methodKey = methodKey;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	@Override
	public String toString() {
		return "RpcRequest [magic=" + magic + ", beanName=" + beanName + ", methodKey=" + methodKey + ", args=" + Arrays.toString(args) + "]";
	}
	
}
