package com.zk.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import com.zk.rpc.bean.RpcMessage;
import com.zk.rpc.common.common.End;
import com.zk.rpc.common.common.codec.RpcProtocol;
import com.zk.rpc.common.common.codec.Version1CodecUtils;

/**
 * 解码器 ( 把字节数组反序列化请求实体供处理器(handler使用) )
 * 
 * @author sunney
 * 
 */
public class RpcDecoder extends ByteToMessageDecoder<RpcMessage> {

	private End end;

	public RpcDecoder(End end) {
		this.end = end;
	}

	@Override
	public RpcMessage decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		if (end == End.SERVER && in.writerIndex() < Version1CodecUtils.REQUEST_HEAD_SIZE) {
			ctx.channel().close();
			return null;
		}
		return RpcProtocol.VERSION_1.decode(end, in);
	}
}
