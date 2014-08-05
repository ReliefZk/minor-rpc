package com.zk.rpc.test.bean;

public interface Hello {
	void say();

	Data say(Data data);

	void say(int count, Data data);

	String doSth();

	String doSth(Data data);

	String doSth(int count, String tag, Data data);

	Data get();

	Data get(int count, String name);
}
