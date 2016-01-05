package com.zk.rpc.common.common.codec;

import io.netty.buffer.ByteBuf;

import com.zk.rpc.bean.RpcMessage;
import com.zk.rpc.bean.RpcRequest;
import com.zk.rpc.bean.RpcResponse;
import com.zk.rpc.common.common.CodecException;
import com.zk.rpc.common.common.End;

/**
 * 通信协议枚举类，实现编解码方法
 * 
 * @author sunney
 * 
 */
public enum RpcProtocol implements Codec {
	/**
	 * 不存在的协议，主要用来调用parse(byte)方法
	 */
	NULL {
		public ByteBuf encode(End end, RpcMessage message) throws Exception {
			throw new CodecException("不存在的协议信息！！！");
		};

		public RpcMessage decode(End decoder, ByteBuf buff) throws Exception {
			throw new CodecException("不存在的协议信息！！！");
		}
	},
	VERSION_1((byte) 1) {
		public ByteBuf encode(End end, RpcMessage message) throws Exception {
			switch (end) {
			case CLIENT:
				return Version1CodecUtils.rpcRequestToByteBuf((RpcRequest) message);
			case SERVER:
				return Version1CodecUtils.rpcResponseToByteBuf((RpcResponse) message);
			default:
				throw new CodecException("参数有误！！！");
			}
		};

		public RpcMessage decode(End end, ByteBuf buff) throws Exception {
			switch (end) {
			case CLIENT:
				return Version1CodecUtils.byteBufToRpcResponse(buff);
			case SERVER:
				return Version1CodecUtils.byteBufToRpcRequest(buff);
			default:
				throw new CodecException("参数有误！！！");
			}
		}
	};
	
	

	private byte version;

	private RpcProtocol() {
	}

	private RpcProtocol(byte version) {
		this.version = version;
	}

	public byte version() {
		return this.version;
	}

	public RpcProtocol parse(byte version) {
		switch (version) {
		case (byte) 1:
			return VERSION_1;
		default:
			return NULL;
		}
	}
}
