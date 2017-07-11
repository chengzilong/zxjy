package com.xsjy.service.service1110000;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_1110000.Pojo1110150;

public class Service1110150 extends BaseService {

	private DBManager db = null;

	public Service1110150() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2014-11-19
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
		String date = dateFormat.format( now ); 
		return date;
	}
	/**
	 * @FunctionName: getInfo
	 * @Description: 获得个人信息
	 * @param beanIn
	 * @throws Exception
	 * @return Pojo1110150
	 * @author czl
	 * @date 2014-11-19
	 */
	public Pojo1110150 getInfo(String uuid) throws Exception {
		Pojo1110150 result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.YHXX_UUID, ");//UUID
			sql.append("     A.YHXX_YHID, ");//用户ID
			sql.append("     A.YHXX_YHMC, ");//用户姓名
			sql.append("     B.YHJS_JSMC AS YHXX_JSMC, ");//角色名称
			sql.append("     A.YHXX_YHMM");//用户密码
			sql.append("     FROM ");
			sql.append("     YHXX A ,YHJS B");
			sql.append("     WHERE A.YHXX_JSID = B.YHJS_JSID");
			if (MyStringUtils.isNotBlank(uuid)) {
				sql.append(" AND A.YHXX_UUID ='").append(uuid).append("'");	
						
			}
			ResultSetHandler<Pojo1110150> rsh = new BeanHandler<Pojo1110150>(
					Pojo1110150.class);
			result = db.queryForBeanHandler(sql, rsh);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
	/**
	 * @FunctionName: updateInfo
	 * @Description: 修改个人信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-11-19
	 */
	public int updateInfo(Pojo1110150 beanIn) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     YHXX ");
			strbuf.append(" SET ");
			strbuf.append("     YHXX_YHMC='").append(beanIn.getYHXX_YHMC()).append("',");// 用户名称
			strbuf.append("     YHXX_YHMM='").append(beanIn.getYHXX_YHMM()).append("',");// 用户密码
			strbuf.append("     YHXX_GXR='").append(beanIn.getYHXX_GXR()).append("',");// 修改人
			strbuf.append("     YHXX_GXSJ='" + sysdate + "'");// 修改时间
			strbuf.append(" WHERE 1 = 1");
			strbuf.append("  AND   YHXX_UUID='").append(beanIn.getYHXX_UUID()).append("'");// UUID
			result = db.executeSQL(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: validatePas
	 * @Description: 获得个人信息
	 * @param beanIn
	 * @throws Exception
	 * @return Pojo1110150
	 * @author czl
	 * @date 2015-01-05
	 */
	public Pojo1110150 validatePas(Pojo1110150 beanIn) throws Exception {
		Pojo1110150 result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.YHXX_YHID, ");//员工ID
			sql.append("     A.YHXX_YHMC ");//员工姓名
			sql.append("     FROM ");
			sql.append("     YHXX A");
			sql.append("     WHERE A.YHXX_SCBZ='0'");
			sql.append(" AND A.YHXX_UUID ='").append(beanIn.getYHXX_UUID()).append("'");	
			sql.append(" AND A.YHXX_YHMM ='").append(beanIn.getYHXX_YHMM()).append("'");	
			ResultSetHandler<Pojo1110150> rsh = new BeanHandler<Pojo1110150>(
					Pojo1110150.class);
			result = db.queryForBeanHandler(sql, rsh);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
}
