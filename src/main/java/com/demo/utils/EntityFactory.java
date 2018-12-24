package com.demo.utils;

/**
 * 生成对象工具类
 * @author Administrator
 *
 */
public class EntityFactory {

	public static <T> T  getEntity(Class<T> clazz) {
		try {
			T newInstance = clazz.newInstance();
			return newInstance;
		} catch (Exception e) {
		
		}
		return null;
	}
}
