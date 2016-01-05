package com.zk.rpc.test.serialize;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.rpc.serialize.HessianSerializer;
import com.zk.rpc.serialize.Serializer;
import com.zk.rpc.test.bean.Data;

public class HessianSerializerTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testEnDe() throws Exception {
		int count = 0;
		while (count++ < 10) {
			Data data = new Data();
			data.setAge(25);
			data.setName("zhoukui");
			data.setTags(new String[] { "闷骚", "男", "单身待解救", "篮球" });
			data.setCates(Arrays.asList(new String[]{"笑容", "帅气", "偶尔" }));
			Map<Integer, String> map = new HashMap<Integer, String>();
			map.put(165, "cm");
			data.setMap(map);
			logger.info("Before:{}", data);

			Serializer serializer = new HessianSerializer();
			byte[] bytes = serializer.enSerialize(data);

			Object newData = serializer.deSerialize(bytes);
			logger.info("After :{}", newData);
		}
	}

}
