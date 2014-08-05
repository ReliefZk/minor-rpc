package com.zk.rpc.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.rpc.bean.RpcRequest;
import com.zk.rpc.bean.RpcResponse;
import com.zk.rpc.common.server.BeanRegister;
import com.zk.rpc.common.server.Tuple;

/**
 * �������˴�����
 * 
 * @author sunney
 * 
 */
public class ServerChannelHandler extends ChannelInboundMessageHandlerAdapter<RpcRequest> {
	private BeanRegister beanRegister;
	private ExecutorService threadPool;
	private final static Logger logger = LoggerFactory.getLogger(ServerChannelHandler.class);

	public ServerChannelHandler(BeanRegister beanRegister, ExecutorService threadPool) {
		if(threadPool == null){
			throw new NullPointerException("Ŀǰ�̳߳ز���Ϊ�գ�");
		}
		this.beanRegister = beanRegister;
		this.threadPool = threadPool;
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, RpcRequest message) throws Exception {
		try {
			if (message != null) {
				/**
				 * ���������Ӹ��̳߳ش���ҵ�������Netty��worker�߳̾��췵�أ��Ա㴦�������ͻ�������
				 * ����ʵ�ʶԷ������˵�TPSû���Ӱ��(Ӳ������)�����Ǹ�����Ϊ����������߷������˵���������
				 */
				threadPool.execute(new DoHandler(ctx, message, beanRegister));
			}
		} catch (Exception e) {
			logger.error("�̵߳��ȹ��ڷ�æ�����´�������ʧ�ܣ�", e);
		}
	}

	private static class DoHandler implements Runnable {
		private ChannelHandlerContext ctx = null;
		private RpcRequest message = null;
		private BeanRegister beanRegister;

		public DoHandler(ChannelHandlerContext ctx, RpcRequest message, BeanRegister beanRegister) {
			this.ctx = ctx;
			this.message = message;
			this.beanRegister = beanRegister;
		}

		@Override
		public void run() {
			try {
				Tuple<Object, Method> tuple = beanRegister.lookup(message.getBeanName(), message.getMethodKey());
				RpcResponse response = new RpcResponse();
				if (tuple != null && tuple.getBean() != null && tuple.getMethod() != null) {
					Method method = tuple.getMethod();
					Object value = method.invoke(tuple.getBean(), message.getArgs());
					response.setResponse(value);
				} else {
					response.setResponse(null);
				}
				tuple = null;
				message = null;
//				ctx.channel().isActive();
				ChannelFuture future = ctx.write(response);
				ctx.flush();
				future.addListener(ChannelFutureListener.CLOSE);
			} catch (Exception e) {
				logger.error("ҵ�����ʧ�ܣ�", e);
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}
