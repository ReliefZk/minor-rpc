package com.zk.rpc.common.common;

/**
 * 编码异常信息类
 * @author sunney
 *
 */
public class CodecException extends Exception {
	private static final long serialVersionUID = 199852772923643735L;

	public CodecException(String message) {
		super(message);
	}
}
