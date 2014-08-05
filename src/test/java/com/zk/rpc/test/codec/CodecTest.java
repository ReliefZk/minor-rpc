package com.zk.rpc.test.codec;

import io.netty.buffer.ByteBuf;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.rpc.bean.RpcMessage;
import com.zk.rpc.bean.RpcRequest;
import com.zk.rpc.bean.RpcResponse;
import com.zk.rpc.common.common.End;
import com.zk.rpc.common.common.codec.RpcProtocol;
import com.zk.rpc.test.bean.DataGener;

public class CodecTest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testEncodeAndDecode() throws Exception {
		int x = 0;
		while(x < 1){
			x++;
			RpcRequest rpcRequest = genRpcRequest();
			rpcRequest.setArgs(new Object[]{10,DataGener.genData()});
			rpcRequest.setBeanName("hello");
			rpcRequest.setMethodKey("hello#say_Data");
			logger.info("Before: request={}", rpcRequest);
			
			ByteBuf buff = RpcProtocol.VERSION_1.encode(End.CLIENT, rpcRequest);
			logger.info("Buff: " + buff);
			
			RpcMessage newRequest = RpcProtocol.VERSION_1.decode(End.SERVER, buff);
			logger.info("After : request={}", newRequest);
		}
	}
	
	@Test
	public void testServerCodec() throws Exception{
		RpcResponse response = genRpcResponse();
		System.out.println(response);
		ByteBuf buff = RpcProtocol.VERSION_1.encode(End.SERVER, response);
		System.out.println("Buff: " + buff);
		RpcMessage newResponse = RpcProtocol.VERSION_1.decode(End.CLIENT, buff);
		System.out.println(newResponse);
	}
	
	@Test
	public void testClientCodec() throws Exception{
		RpcRequest request = genRpcRequest();
		System.out.println(request);
		ByteBuf buff = RpcProtocol.VERSION_1.encode(End.CLIENT, request);
		System.out.println(buff);
		RpcMessage newRequest = RpcProtocol.VERSION_1.decode(End.SERVER, buff);
		System.out.println(newRequest);
	}

	private RpcRequest genRpcRequest() {
		RpcRequest rpcRequest = new RpcRequest();
		rpcRequest.setBeanName("hello");
		rpcRequest.setMethodKey("hello#doSth_int_String_Data");
		Object[] args = { 10, "count", DataGener.genData() };
		rpcRequest.setArgs(args);
		/*
		 * Method[] method = HelloImpl.class.getMethods(); for (Method m :
		 * method) { if (m.getName().equals("doSth") &&
		 * m.getParameterTypes().length == 3) { rpcRequest.setArgs(); } }
		 */

		return rpcRequest;
	}

	private RpcResponse genRpcResponse(){
		RpcResponse response = new RpcResponse(DataGener.genData());
		return response;
	}
}
