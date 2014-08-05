package com.zk.rpc.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

/**
 * Hessian实现的序列化类
 * 
 * @author sunney
 * 
 */
public class HessianSerializer implements Serializer {
	@Override
	public byte[] enSerialize(Object message) throws IOException {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		Hessian2Output output = new Hessian2Output(byteArray);
		output.writeObject(message);
		output.close();
		byte[] bytes = byteArray.toByteArray();
		return bytes;
	}

	@Override
	public Object deSerialize(byte[] byteBuf) throws IOException {
		Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(byteBuf));
		Object resultObject = input.readObject();
		input.close();
		return resultObject;
	}

}
