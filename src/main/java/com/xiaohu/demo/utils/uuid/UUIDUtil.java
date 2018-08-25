package com.xiaohu.demo.utils.uuid;

import java.util.UUID;

public class UUIDUtil {

	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void main(String[] args) {
	}
}