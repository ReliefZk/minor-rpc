//package com.zk.rpc.common.common.codec;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.sunney.rpc.bean.RpcMessage;
//import org.sunney.rpc.bean.RpcRequest;
//import org.sunney.rpc.bean.RpcResponse;
//import org.sunney.rpc.serialize.HessianSerializer;
//import org.sunney.rpc.serialize.Serializer;
//
//public class Version2CodecUtils {
//	private static Charset charset = Charset.forName(CharsetName.ASCII.charsetName());
//	private static Serializer serializer = new HessianSerializer();
//	private static Logger logger = LoggerFactory.getLogger(Version2CodecUtils.class);
//
//	/**
//	 * 序列化 RpcRequest -> ByteBuf （客户端调用）
//	 * 
//	 * @param message
//	 * @return
//	 */
//	public static ByteBuf rpcRequestToByteBuf(RpcRequest message) {
//		int byteLength = 0;
//
//		byte magic = message.getMagic();
//		byteLength += 1;
//
//		byte[] beanNameBytes = message.getBeanName().getBytes(charset);
//		byteLength += beanNameBytes.length + 4;
//
//		byte[] methodKeyBytes = message.getMethodKey().getBytes(charset);
//		byteLength += methodKeyBytes.length + 4;
//
//		Map<Integer, byte[]> argsLengthByteMap = new HashMap<Integer, byte[]>();
//		for (Object obj : message.getArgs()) {
//			try {
//				byte[] bytes = serializer.enSerialize(obj);
//				byteLength += bytes.length + 4;
//				argsLengthByteMap.put(bytes.length, bytes);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		ByteBuf byteBuf = Unpooled.buffer(byteLength);
//		byteBuf.writeByte(magic);
//
//		byteBuf.writeInt(beanNameBytes.length);
//		byteBuf.writeBytes(beanNameBytes);
//
//		byteBuf.writeInt(methodKeyBytes.length);
//		byteBuf.writeBytes(methodKeyBytes);
//
//		byteBuf.writeInt(argsLengthByteMap.size());
//		Set<Entry<Integer, byte[]>> argSet = argsLengthByteMap.entrySet();
//		for (Entry<Integer, byte[]> entry : argSet) {
//			byteBuf.writeInt(entry.getKey());
//			byteBuf.writeBytes(entry.getValue());
//		}
//		logger.info("序列化对象：{}", message);
//		return byteBuf;
//	}
//
//	/**
//	 * 反序列化 RpcRequest <- ByteBuf （服务器端调用）
//	 * 
//	 * @param message
//	 * @return
//	 */
//	public static RpcMessage byteBufToRpcRequest(ByteBuf buff) {
//		RpcRequest request = new RpcRequest();
//		byte magic = buff.readByte();
//		request.setMagic(magic);
//
//		int beanNameLength = buff.readInt();
//		byte[] beanNameBytes = new byte[beanNameLength];
//		buff.readBytes(beanNameBytes);
//		String beanName = new String(beanNameBytes, charset);
//		request.setBeanName(beanName);
//
//		int methodKeyLength = buff.readInt();
//		byte[] methodKeyBytes = new byte[methodKeyLength];
//		buff.readBytes(methodKeyBytes);
//		String methodKey = new String(methodKeyBytes, charset);
//		request.setMethodKey(methodKey);
//
//		int argsLength = buff.readInt();
//		Object[] args = new Object[argsLength];
//		int count = 0;
//		while (count < argsLength) {
//			try {
//				int tmpLenth = buff.readInt();
//				byte[] tmpBytes = new byte[tmpLenth];
//				buff.readBytes(tmpBytes);
//				args[count] = serializer.deSerialize(tmpBytes);
//				count++;
//			} catch (IOException e) {
//			}
//		}
//		request.setArgs(args);
//		logger.info("反序列化對象:{}", request);
//		return request;
//	}
//
//	/**
//	 * 序列化 RpcResponse -> ByteBuf （服务器端调用）
//	 * 
//	 * @param message
//	 * @return
//	 */
//	public static ByteBuf rpcResponseToByteBuf(RpcResponse message) {
//		logger.info("Server Handler返回对象：{}", message);
//		try {
//			byte[] bytes = serializer.enSerialize(message.getResponse());
//			int lenght = bytes.length;
//			ByteBuf buff = Unpooled.buffer(lenght + 4);
//			buff.writeInt(lenght);
//			buff.writeBytes(bytes);
//			return buff;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 反序列化 RpcResponse <- ByteBuf (客户端调用)
//	 * 
//	 * @param message
//	 * @return
//	 */
//	public static RpcMessage byteBufToRpcResponse(ByteBuf buff) {
//		int lenght = buff.readInt();
//		byte[] dst = new byte[lenght];
//		buff.readBytes(dst);
//		RpcResponse rpcResponse = new RpcResponse();
//		try {
//			rpcResponse.setResponse(serializer.deSerialize(dst));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		logger.info("反序列化Server Handler返回的对象为：{}", rpcResponse);
//		return rpcResponse;
//	}
//}
