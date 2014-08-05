package com.zk.rpc.client.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.zk.rpc.bean.RpcRequest;
import com.zk.rpc.bean.RpcResponse;
import com.zk.rpc.common.client.AbstractClient;
import com.zk.rpc.common.common.End;
import com.zk.rpc.common.common.NetConfig;
import com.zk.rpc.common.common.TransportURL;
import com.zk.rpc.common.common.codec.RpcProtocol;

/**
 * Socket请求处理
 * 
 * @author sunney
 * 
 */
public class SocketClient extends AbstractClient {
	private NetConfig netConfig;

	public SocketClient(TransportURL url) {
		super(url);
	}

	public SocketClient(TransportURL url, NetConfig netConfig) {
		this(url);
		this.netConfig = netConfig;
	}

	/**
	 * 向服务端发送请求
	 */
	@Override
	public void sendRequest(RpcRequest request) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		InputStream inputStream = null;
		Socket socket = null;
		try {
			socket = doSendRequest(request);
			inputStream = socket.getInputStream();

			int _byte = -1;
			while ((_byte = inputStream.read()) != -1) {
				byteArrayOutputStream.write(_byte);
			}

			byte[] rbytes = byteArrayOutputStream.toByteArray();
			ByteBuf buff = Unpooled.buffer(rbytes.length);
			buff.writeBytes(rbytes);
			RpcResponse response = (RpcResponse) RpcProtocol.VERSION_1.decode(End.CLIENT, buff);
			this.setResponse(request.getMagic(), response);
			buff.clear();
		} catch (Exception e) {
			this.setResponse(request.getMagic(), new RpcResponse());
		} finally {
			/**
			 * byteArrayOutputStream 不需要显示关闭
			 */
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception e) {
			}

			try {
				if (socket != null)
					socket.close();
			} catch (Exception e) {
			}
		}
	}

	private Socket doSendRequest(RpcRequest request) throws Exception {
		Socket socket = createSocket(host, port);
		OutputStream outputStream = socket.getOutputStream();
		ByteBuf buff = RpcProtocol.VERSION_1.encode(End.CLIENT, request);
		outputStream.write(buff.array());
		buff.clear();
		return socket;
	}

	/**
	 * 使用Socket进行短连接进行网络IO 对资源消耗很小 所以没有做socket池化 (但我会考虑进行池化进行优化) 创建Socket
	 * 
	 * @param host
	 * @param port
	 * @return
	 * @throws IOException
	 */
	private Socket createSocket(String host, int port) throws IOException {
		Socket socket = new Socket();
		socket.setSoTimeout(netConfig.getReadTimeout() * 1000);
		socket.setTcpNoDelay(netConfig.isTcpNoDelay());
		socket.setReuseAddress(netConfig.isReuseAddress());
		socket.setSoLinger(netConfig.getSoLinger() > 0, netConfig.getSoLinger());
		socket.setSendBufferSize(netConfig.getSendBufferSize());
		socket.setReceiveBufferSize(netConfig.getReceiveBufferSize());
		socket.connect(new InetSocketAddress(host, port),
				netConfig.getConnectTimeout() * 1000);
		return socket;
	}

}
