package com.zk.rpc.common.common.codec;

import io.netty.buffer.ByteBuf;

import com.zk.rpc.bean.RpcMessage;
import com.zk.rpc.common.common.End;

/**
 * 编解码接口
 * 
 * @author sunney
 * 
 */
public interface Codec {
	ByteBuf encode(End decoder, RpcMessage message) throws Exception;

	RpcMessage decode(End decoder, ByteBuf buff) throws Exception;
}
