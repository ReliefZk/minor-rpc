package com.zk.rpc.client.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.rpc.bean.RpcRequest;
import com.zk.rpc.bean.RpcResponse;
import com.zk.rpc.common.client.Client;

/**
 * 客户端Handler
 * 
 * @author sunney
 * 
 */
public class ClientChannelHandler extends
		ChannelInboundMessageHandlerAdapter<RpcResponse> {
	private Client client;
	private RpcRequest request;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public ClientChannelHandler(Client client) {
		this.client = client;
	}

	public ClientChannelHandler(Client client, RpcRequest request) {
		this(client);
		this.request = request;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("请求实体：{}", request);
		ctx.write(request);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, RpcResponse msg)
			throws Exception {
		logger.info("服务器返回数据：{}", msg);
		client.setResponse(request.getMagic(), msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

}
