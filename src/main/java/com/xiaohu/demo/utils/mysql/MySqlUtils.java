package com.xiaohu.demo.utils.mysql;

import java.lang.reflect.Field;
import java.util.*;

import javax.persistence.Column;

import com.xiaohu.demo.utils.date.DateUtils;
public class MySqlUtils {
	
	@SuppressWarnings("rawtypes")
	public static StringBuilder buildAllAql(ArrayList objectList, String tableName){
		ArrayList<Field> fieldList = new ArrayList<Field>();
		StringBuilder sql = new StringBuilder();
		for(Object obj : objectList){
			if(fieldList.size() == 0){
				sql.append("(");
				fieldList = initBuildSqlByObj(obj,sql);
				sql.replace(sql.length()-2, sql.length()-1, "");
				sql.append("), ");
			} else {
				sql.append("(");
				buildSqlByFiled(obj,fieldList,sql);
				sql.replace(sql.length()-2, sql.length()-1, "");
				sql.append("), ");
			}
		}
		StringBuilder finalSql = new StringBuilder();
		if(sql.length() >= 2){
			sql.replace(sql.length()-2, sql.length(), "");
			finalSql.append("insert into ").append(tableName).append(" (");
			for(Field field : fieldList){
				finalSql.append(field.getAnnotation(Column.class).name()+",");
			}
			finalSql.replace(finalSql.length()-1, finalSql.length(), "").append(") values ").append(sql);
		}
		return finalSql;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<StringBuilder> buildAllAqlNew(ArrayList objectList, String tableName){
		List<StringBuilder> sqlList = new ArrayList<>();
		int num = 0;
		ArrayList objects = new ArrayList();
		for(int i=0;i<objectList.size();i++) {
			objects.add(objectList.get(i));
			num++;
			if(num==20) {
				sqlList.add(buildAllAql(objects,tableName));
				objects = new ArrayList();
				num=0;
			}
		}
		if(objects!=null && objects.size()>0) {
			sqlList.add(buildAllAql(objects,tableName));
		}
		return sqlList;
	}
	
	public static ArrayList<Field> initBuildSqlByObj(Object obj, StringBuilder sql){
		Field[]  filedArr = obj.getClass().getDeclaredFields();
		ArrayList<Field> fieldList = new ArrayList<Field>();
		for(Field field : filedArr){
			if(field.isAnnotationPresent(Column.class)){
				fieldList.add(field);
				try {
					field.setAccessible(true);
//					Column column = field.getAnnotation(Column.class);
					buildSql(field.get(obj), sql);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return fieldList;
	}
	
	public static void buildSqlByFiled(Object obj, ArrayList<Field> fieldList, StringBuilder sql){
		for(Field field : fieldList){
			try {
				field.setAccessible(true);
				buildSql(field.get(obj), sql);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void buildSql(Object field, StringBuilder sql){
		if(field != null){
			String fieldStr = null;
			if(!field.getClass().toString().endsWith("Date")){
				fieldStr = String.valueOf(field);
			} else {
				fieldStr = DateUtils.yyyyMMddHHmmss((Date)field);
			}
			
			sql.append("'"+fieldStr.replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\"")+"', ");
		} else {
			sql.append("null, ");
		}
	}
}
