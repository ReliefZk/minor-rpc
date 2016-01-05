package com.zk.rpc.test.client;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.rpc.client.BeanProxyFactory;
import com.zk.rpc.test.bean.Data;
import com.zk.rpc.test.bean.DataGener;
import com.zk.rpc.test.bean.HelloService;

public class ClientTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	private Logger logger = LoggerFactory.getLogger(ClientTest.class);

	@Test
	public void testCount() {
		long startMilli = System.currentTimeMillis();
		int count = 0;
		for (int i = 0; i < 10 * 1000; i++) {


			HelloService hello = BeanProxyFactory
					.create(HelloService.class, "//localhost:9991/hello",
							ClientTest.class.getClassLoader());

			Data data = hello.say(DataGener.genData());
			if (data != null) {
				logger.info("{},瀹為檯杩斿洖鐨勫�硷細{}", count++, data);
			}
		}
		long endMilli = System.currentTimeMillis();
		logger.info("startMilli: {},endMilli: {}, (endMilli - startMilli)={}",
				new Long[] { startMilli, endMilli, (endMilli - startMilli) });
	}

	@Test
	public void testNettyClient() {
		long startMilli = System.currentTimeMillis();
		
		HelloService hello = BeanProxyFactory.create(HelloService.class, "//localhost:9991/hello", ClientTest.class.getClassLoader());
		for(int i=0;i<100;i++){
			Data data = hello.say(DataGener.genData());
			System.out.println(data);
		}

		long endMilli = System.currentTimeMillis();
		logger.info("startMilli: {},endMilli: {}, (endMilli - startMilli)={}",
				new Long[] { startMilli, endMilli, (endMilli - startMilli) });
	}

	@Test
	public void testCall(){
		HelloService hello = BeanProxyFactory.create(HelloService.class, "//localhost:9991/helloService", ClientTest.class.getClassLoader());
		String data = hello.doSth(1, "xxx", DataGener.genData());
		System.out.println(data);
	}

}
