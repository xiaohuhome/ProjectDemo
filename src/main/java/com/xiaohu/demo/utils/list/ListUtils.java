package com.xiaohu.demo.utils.list;

import java.util.List;

public class ListUtils {
	
	public static boolean isNotEmpty(List list) {
		boolean flag = false;
		if(list != null && list.size() > 0) {
			flag = true;
		}
		return flag;
	}
}
