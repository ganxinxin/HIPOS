package com.point.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class ConsumerProperties {
	private static Properties config = null;

	private static ConsumerProperties instance;

	public static ConsumerProperties getInstance() {
		if (instance == null) {
			instance = new ConsumerProperties();
			InputStream in = ConsumerProperties.class.getClassLoader()
					.getResourceAsStream("config.properties");
			config = new Properties();
			try {
				config.load(in);
				in.close();
			} catch (IOException e) {
				System.out.println("No AreaPhone.properties defined error");
			}
		}
		return instance;
	}

	// 根据key读取value
	public String readValue(String key) {
		// Properties props = new Properties();
		try {
			String value = config.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ConfigInfoError" + e.toString());
			return null;
		}
	}

	// 读取properties的全部信息
	@SuppressWarnings("rawtypes")
	public void readAllProperties() {
		try {

			Enumeration en = config.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = config.getProperty(key);
				System.out.println(key + Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ConfigInfoError" + e.toString());
		}
	}

}
