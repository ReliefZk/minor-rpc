package com.zk.rpc.test.bean;

import java.util.Arrays;
import java.util.HashMap;

import java.util.Map;

public class DataGener {
	public static int age = 1;

	public static Data genData() {
		Data data = new Data();
		data.setAge(age++);
		data.setName("zhoukui");
		data.setTags(new String[] { "闷骚", "男", "单身待解救", "篮球" });
		data.setCates(Arrays.asList(new String[] { "笑容", "帅气", "偶尔" }));
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(165, "cm");
		data.setMap(map);
		return data;
	}
	
	public static void main(String[] args) {
	}

}
