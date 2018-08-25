package com.xiaohu.demo.dao.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xiaohu.demo.utils.mysql.MySqlUtils;

/**
 * <p>标题: MysTest.java</p>
 */
@Component
public class CommonDao {
	
	
	private static Properties p = new Properties();
	static {
		InputStream inputStream = CommonDao.class.getClassLoader().getResourceAsStream("dbconfig.properties");   
		
		try {   
			p.load(inputStream);   
		} 
		catch (IOException e1) {   
			e1.printStackTrace();  
		}   
	}
	
	public static final String DB_DRIVER = p.getProperty("jdbc.driver");
	public static final String DB_URL = p.getProperty("jdbc.url");
	public static final String DB_USERNAME = p.getProperty("jdbc.username");
	public static final String DB_PASSWORD = p.getProperty("jdbc.password");	
	
	
	private final static Logger logger = LoggerFactory.getLogger(CommonDao.class);
	/** 
	 * 方法名:          queryForList
	 * 方法功能描述:     执行SQL,
	 */
	public List<Map<String,Object>> queryForList(String sql, Object... array) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
	    Connection conn = null;
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    try {
	    	conn = getConnection();
	    	pst = conn.prepareStatement(sql);
	    	for (int i = 0; i < array.length; i++) {
	    		pst.setObject(i+1, array[i]);
	    	}
	    	rs = pst.executeQuery();
	    	ResultSetMetaData md = rs.getMetaData();
	    	int columnCount = md.getColumnCount();
	    	while (rs.next()) {
	    		Map<String,Object> rowData = new HashMap<String,Object>();
	    		for (int i = 1; i <= columnCount; i++) {
	    			rowData.put(md.getColumnName(i), rs.getObject(i)==null?"":rs.getObject(i));
	    		}
	    		results.add(rowData);
	    	}
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	free(rs, pst, conn);
	    }
	    return results;
	}
	
	/** 
	 * 方法名:updateBySql
	 */
	public String updateBySql(String sql, Object... array) {
	    Connection conn = null;
	    PreparedStatement pst = null;
	    String result = null;
	    try {
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			for (int i = 0; i < array.length; i++) {
				pst.setObject(i+1, array[i]);
			}
			result = "success：" + pst.executeUpdate();
	    }catch (Exception e) {
	    	e.printStackTrace();
	    	result = "fail：" + e.getMessage();
	    }finally {
	    	free(null, pst, conn);
	    }
	    return result;
	}
    	
	/** 
	 * 方法名:          executeBySql
	 * 方法功能描述:     执行SQL
	 */
	public String executeBySql(String sql) {
	    Connection conn = null;
	    PreparedStatement pst = null;
	    String result = null;
	    try {
	    	conn = getConnection();
	    	pst = conn.prepareStatement(sql);
	    	result = String.valueOf(pst.executeUpdate());
	    }catch (Exception e) {
	    	e.printStackTrace();
	    	result = "fail：" + e.getMessage();
	    }finally {
	    	free(null, pst, conn);
	    } 
	    return result;
	}
	
	/**
	 * 方法名：batchSaveDataTransaction
	 * 描述：批量执行update，delete,insert sql
	 * */
	public boolean batchSaveDataTransaction(List<StringBuilder> sqlList) {
		boolean result = false;
		Connection conn = getConnection();
		PreparedStatement pst = null;

		try {
			conn.setAutoCommit(false);
			pst = (PreparedStatement) conn.prepareStatement("");
			for (StringBuilder sql : sqlList) {
//				logger.info("batchSaveDataTransactionSql:"+sql);
				pst.addBatch(sql.toString());
			}
			pst.executeBatch();
			conn.commit();
			result = true;
		} 
		catch (Exception e) {
			try {
				System.out.println("rollback  =======================");
				conn.rollback();
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("批量保存数据库出错！", e);
		} 
		finally {
			try {
				conn.setAutoCommit(true);
				if (pst != null) {
					pst.close();
				}
				if (conn != null) {
					conn.close();
				}
			} 
			catch (SQLException e) {
				logger.error("释放数据库出错！", e);
			}
		}
		
		return result;
	}  
	
	/**
	 * 方法名：batchSaveDataTransactionSpec
	 * 描述：分批执行update，delete,insert sql
	 * */
	public void batchSaveDataTransactionSpec(List<StringBuilder> sqlList) {
		List<StringBuilder> lstTemp = new ArrayList<StringBuilder>();
		int i = 0;
		
		for (int j = 0; j < sqlList.size(); j++) {
			lstTemp.add(sqlList.get(j));
			i++;
			
			if (i == 20) {
				batchSaveDataTransaction(lstTemp);
				lstTemp = new ArrayList<StringBuilder>();
				i = 0;
			}
		}
		
		if(lstTemp.size()>0) {
			batchSaveDataTransaction(lstTemp);
		} 
	}
	
	/** 
	  * 批量保存实体List
	  */  
	@SuppressWarnings("unused")
	public void batchSaveOrUpdate(ArrayList objectList, String tableName) {
		batchSaveDataTransactionSpec(MySqlUtils.buildAllAqlNew(objectList, tableName));
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		}catch (Exception e) {
			e.printStackTrace(); 
		}
		return conn;
	}
	
	// 释放连接
	public static void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null) {
				rs.close(); // 关闭结果集
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (st != null) {
					st.close(); // 关闭Statement
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if (conn != null) {
						conn.close(); // 关闭连接
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
