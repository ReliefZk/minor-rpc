package com.zk.rpc.bean;

/**
 * 服务器返回信息包装类
 * 
 * @author sunney
 * 
 */
public class RpcResponse extends RpcMessage {
	private static final long serialVersionUID = -5114064797925286890L;
	private Object response = null;

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public RpcResponse() {
		super();
	}

	public RpcResponse(Object response) {
		super();
		this.response = response;
	}

	@Override
	public String toString() {
		return "RpcResponse [response=" + response + "]";
	}
}
