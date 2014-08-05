package com.zk.rpc.common.common.codec;

public enum CharsetName {
	ASCII("ascii"), UTF_8("utf-8");
	
	private String charsetName;

	CharsetName(String charsetName) {
		this.charsetName = charsetName;
	}
	
	public String charsetName(){
		return this.charsetName;
	}
}
