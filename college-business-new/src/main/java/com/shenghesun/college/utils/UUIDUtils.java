package com.shenghesun.college.utils;

import java.util.UUID;

public class UUIDUtils {

	public static String getUUId() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static void main(String[] args) {
		for(int i = 0 ; i < 84 ; i ++) {
			System.out.println(getUUId());
		}
	}
}
