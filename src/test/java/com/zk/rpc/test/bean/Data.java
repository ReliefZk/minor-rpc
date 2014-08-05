package com.zk.rpc.test.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Data implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	private String[] tags;
	private List<String> cates;
	private Map<Integer, String> map;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public List<String> getCates() {
		return cates;
	}

	public void setCates(List<String> cates) {
		this.cates = cates;
	}

	public Map<Integer, String> getMap() {
		return map;
	}

	public void setMap(Map<Integer, String> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "Data [name=" + name + ", age=" + age + ", tags=" + Arrays.toString(tags) + ", cates=" + cates + ", map=" + map + "]";
	}
}
