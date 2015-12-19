package com.zk.rpc.common.client;

import com.zk.rpc.bean.RpcRequest;
import com.zk.rpc.bean.RpcResponse;

/**
 * 客户端帮助类
 *
 * @author sunney
 *
 */
public interface Client {
	Object invoke(RpcRequest request);

	void sendRequest(RpcRequest request);

	void setResponse(Integer requestIndex, RpcResponse response);

	RpcResponse getResponse(Integer requestIndex);

	String getBeanName();
}
