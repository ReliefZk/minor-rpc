package com.zk.rpc.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioEventLoopGroup;
import io.netty.channel.socket.oio.OioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.rpc.bean.RpcRequest;
import com.zk.rpc.bean.RpcResponse;
import com.zk.rpc.codec.RpcDecoder;
import com.zk.rpc.codec.RpcEncoder;
import com.zk.rpc.common.client.AbstractClient;
import com.zk.rpc.common.client.Client;
import com.zk.rpc.common.common.End;
import com.zk.rpc.common.common.TransportURL;

/**
 * Netty实现的客户端
 * 
 * @author sunney
 */
public class NettyClient extends AbstractClient {
	private static final Logger logger = LoggerFactory
			.getLogger(NettyClient.class);

	public NettyClient(TransportURL url) {
		super(url);
	}

	@Override
	public void sendRequest(RpcRequest request) {
		logger.info("建立链接", request);

		ArrayBlockingQueue<RpcResponse> responseQueue = new ArrayBlockingQueue<RpcResponse>(1);
		responseMap.put(request.getMagic(), responseQueue);
		
		ChannelFuture cf = connnect(request);
		cf.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
				if (future.isSuccess()) {
					return;
				} else {
					if (future.channel().isActive()) {
						future.channel().close();
					}
				}
			}
		});
	}

	private ChannelFuture connnect(RpcRequest request) {
		final Client client = this;
		final RpcRequest frequest = request;
		Bootstrap b = new Bootstrap();
		try {
			b.group(new OioEventLoopGroup()).channel(OioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.option(ChannelOption.SO_REUSEADDR, true)
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000 * 10)
					.remoteAddress(new InetSocketAddress(host, port))
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast("encoder", new RpcEncoder(
									End.CLIENT));
							pipeline.addLast("decoder", new RpcDecoder(
									End.CLIENT));
							pipeline.addLast("handler",
									new ClientChannelHandler(client, frequest));
						}
					});

			ChannelFuture future = b.connect().syncUninterruptibly();
			future.awaitUninterruptibly(1000 * 10);
			return future;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
