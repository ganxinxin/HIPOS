package com.alibaba.dubbo.demo.provider.main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProviderMain {
	Logger log = Logger.getLogger(ProviderMain.class);

	public static void main(String args[]) {
		// PropertyConfigurator.configure("E:/workspace/program/employment-provider/src/main/java/log4j.properties");
		new Thread() {
			public void run() {
				ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
						"classpath:/META-INF/spring/dubbo-demo-provider.xml");
				context.start();

				while (true) {
					try {
						Thread.sleep(Long.MAX_VALUE);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
