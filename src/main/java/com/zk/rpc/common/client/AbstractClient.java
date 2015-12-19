package com.zk.rpc.common.client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.zk.rpc.bean.RpcRequest;
import com.zk.rpc.bean.RpcResponse;
import com.zk.rpc.common.common.TransportURL;

public abstract class AbstractClient implements Client {
	private AtomicInteger rquestId = new AtomicInteger(0);
	protected String host;
	protected int port;
	protected String beanName;
	protected ConcurrentHashMap<Integer, ArrayBlockingQueue<RpcResponse>> responseMap = new ConcurrentHashMap<Integer, ArrayBlockingQueue<RpcResponse>>();

	public AbstractClient(TransportURL url) {
		this.host = url.getHost();
		this.port = url.getPort();
		this.beanName = url.getPath();
	}

	@Override
	public String getBeanName() {
		return this.beanName;
	}

	@Override
	public void setResponse(Integer requestIndex, RpcResponse response) {
		ArrayBlockingQueue<RpcResponse> responseQueue = responseMap.get(requestIndex);
		if(responseQueue == null){
			responseQueue = new ArrayBlockingQueue<RpcResponse>(1);
		}
		responseQueue.add(response);
		
		responseMap.putIfAbsent(requestIndex, responseQueue);
	}

	@Override
	public RpcResponse getResponse(Integer requestIndex) {
		ArrayBlockingQueue<RpcResponse> responseQueue = responseMap.get(requestIndex);
		if (responseQueue != null) {
			try {
				RpcResponse response = responseQueue.poll(30*1000,TimeUnit.MILLISECONDS);
				return response;
			} catch (InterruptedException e) {
			}finally{
				responseMap.remove(requestIndex);
			}
		}
		return null;
	}

	public Object invoke(RpcRequest request) {
		int rid = rquestId.getAndDecrement();
		request.setMagic(rid);
		sendRequest(request);
		return getResponse(rid).getResponse();
	}
}
