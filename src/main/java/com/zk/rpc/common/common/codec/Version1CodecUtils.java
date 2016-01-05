package com.zk.rpc.common.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.nio.charset.Charset;

import com.zk.rpc.bean.RpcMessage;
import com.zk.rpc.bean.RpcRequest;
import com.zk.rpc.bean.RpcResponse;
import com.zk.rpc.serialize.HessianSerializer;
import com.zk.rpc.serialize.Serializer;

public class Version1CodecUtils {
	private static Charset charset = Charset.forName(CharsetName.ASCII.charsetName());
	private static Serializer serializer = new HessianSerializer();
	public final static int REQUEST_HEAD_SIZE = 16;//
	public final static int RESPONSE_HEAD_SIZE = 4;
	/**
	 * 序列化 RpcRequest -> ByteBuf （客户端调用）
	 * 
	 * @param message
	 * @return
	 * @throws IOException 
	 */
	public static ByteBuf rpcRequestToByteBuf(RpcRequest message) throws IOException {
		int byteLength = REQUEST_HEAD_SIZE;

		int magic = message.getMagic();

		byte[] beanNameBytes = message.getBeanName().getBytes(charset);
		byteLength += beanNameBytes.length;

		byte[] methodKeyBytes = message.getMethodKey().getBytes(charset);
		byteLength += methodKeyBytes.length;
		
		byte[] argsBytes = serializer.enSerialize(message.getArgs());
		byteLength += argsBytes.length;
		ByteBuf byteBuf = Unpooled.buffer(byteLength);
		byteBuf.writeInt(magic);

		byteBuf.writeInt(beanNameBytes.length);
		byteBuf.writeBytes(beanNameBytes);

		byteBuf.writeInt(methodKeyBytes.length);
		byteBuf.writeBytes(methodKeyBytes);

		byteBuf.writeInt(argsBytes.length);
		byteBuf.writeBytes(argsBytes);
		
		return byteBuf;
	}

	/**
	 * 反序列化 RpcRespose <- ByteBuf （服务器端调用）
	 * 
	 * @param buff
	 * @return
	 * @throws IOException 
	 */
	public static RpcMessage byteBufToRpcRequest(ByteBuf buff) throws IOException {
		RpcRequest request = new RpcRequest();
		int magic = buff.readInt();
		request.setMagic(magic);

		int beanNameLength = buff.readInt();
		byte[] beanNameBytes = new byte[beanNameLength];
		buff.readBytes(beanNameBytes);
		String beanName = new String(beanNameBytes, charset);
		request.setBeanName(beanName);

		int methodKeyLength = buff.readInt();
		byte[] methodKeyBytes = new byte[methodKeyLength];
		buff.readBytes(methodKeyBytes);
		String methodKey = new String(methodKeyBytes, charset);
		request.setMethodKey(methodKey);

		int argsLenght = buff.readInt();
		byte[] argsBytes = new byte[argsLenght];
		buff.readBytes(argsBytes);
		Object[] args = (Object[]) serializer.deSerialize(argsBytes);
		
		request.setArgs(args);
		return request;
	}

	/**
	 * 序列化 RpcResponse -> ByteBuf （服务器端调用）
	 * 
	 * @param message
	 * @return
	 * @throws IOException 
	 */
	public static ByteBuf rpcResponseToByteBuf(RpcResponse message) throws IOException {
		byte[] bytes = serializer.enSerialize(message.getResponse());
		int lenght = bytes.length;
		ByteBuf buff = Unpooled.buffer(lenght + RESPONSE_HEAD_SIZE);
		buff.writeInt(lenght);
		buff.writeBytes(bytes);
		return buff;
	}

	/**
	 * 反序列化 RpcResponse <- ByteBuf (客户端调用)
	 * 
	 * @param buff
	 * @return
	 * @throws IOException 
	 */
	public static RpcMessage byteBufToRpcResponse(ByteBuf buff) throws IOException {
		int lenght = buff.readInt();
		byte[] dst = new byte[lenght];
		buff.readBytes(dst);
		RpcResponse rpcResponse = new RpcResponse();
		rpcResponse.setResponse(serializer.deSerialize(dst));
		return rpcResponse;
	}
}
