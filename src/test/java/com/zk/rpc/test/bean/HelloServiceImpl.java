package com.zk.rpc.test.bean;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServiceImpl implements HelloService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void say() {
		logger.error("fk,U do nothing!");
	}

	@Override
	public Data say(Data data) {
//		logger.error("args: data=" + data);
		return DataGener.genData();
	}

	@Override
	public void say(int count, Data data) {
//		logger.error("args: count=" + count + ",data=" + data);
	}

	@Override
	public String doSth() {
//		logger.error("fk,U do nothing!");
		return UUID.randomUUID().toString();
	}

	@Override
	public String doSth(Data data) {
//		logger.error("args: data=" + data);
		return UUID.randomUUID().toString();
	}

	@Override
	public String doSth(int count, String tag, Data data) {
//		logger.error("args: count=" + count + ",tag=" + tag + ",data=" + data);
		return UUID.randomUUID().toString();
	}

	@Override
	public Data get() {
//		logger.error("fk,U do nothing!");
		return DataGener.genData();
	}

	@Override
	public Data get(int count, String name) {
//		logger.error("args: count=" + count + ",name=" + name);
		return DataGener.genData();
	}

}
