package com.zk.rpc.serialize;

import java.io.IOException;

/**
 * 序列化接口
 * @author sunney
 *
 */
public interface Serializer {
	byte[] enSerialize(Object message) throws IOException;

	Object deSerialize(byte[] byteBuf) throws IOException;
}
