package com.zk.rpc.common.server;

/**
 * Bean及其对应的Method
 * @author sunney
 *
 * @param <B>
 * @param <M>
 */
public class Tuple<B,M> {
	private B bean;
	private M method;
	
	
	public Tuple(B bean, M method) {
		super();
		this.bean = bean;
		this.method = method;
	}
	
	public B getBean() {
		return bean;
	}
	public void setBean(B bean) {
		this.bean = bean;
	}
	public M getMethod() {
		return method;
	}
	public void setMethod(M method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "Tuple [bean=" + bean + ", method=" + method + "]";
	}
}
