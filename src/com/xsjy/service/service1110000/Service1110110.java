package com.xsjy.service.service1110000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_1110000.Pojo1110110;

public class Service1110110 extends BaseService {

	private DBManager db = null;

	public Service1110110() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2015-01-06
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
		String date = dateFormat.format( now );
		return date;
	}
	/**
	 * @FunctionName: getInfo
	 * @Description: 获取登录人信息
	 * @param beanIn
	 * @throws Exception
	 * @return Pojo1110110
	 * @author czl
	 * @date 2017-07-28
	 */
	public Pojo1110110 getInfo(String yhid) throws Exception {
		Pojo1110110 result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.YHXX_YHID AS YHID,");//用户ID
			sql.append("     A.YHXX_YHMC AS YHMC,");//用户姓名
			sql.append("     B.YGXX_YGID,");//员工ID
			sql.append("     B.YGXX_XB,");//性别
			sql.append("     B.YGXX_CSRQ,");//出生日期
			sql.append("     B.YGXX_LXFS,");//联系方式
			sql.append("     B.YGXX_GRJJ");//个人简介
			sql.append("     FROM ");
			sql.append("     YHXX A LEFT JOIN YGXX B ON A.YHXX_YHID = B.YGXX_YGBM");
			sql.append("     WHERE A.YHXX_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(yhid)) {
				sql.append(" AND A.YHXX_YHID ='").append(yhid).append("'");

			}
			ResultSetHandler<Pojo1110110> rsh = new BeanHandler<Pojo1110110>(
					Pojo1110110.class);
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
	 * @FunctionName: insertStaff
	 * @Description: 新增员工信息
	 * @param beanIn
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2017-07-28
	 */
	public String insertStaff(Pojo1110110 beanIn) throws Exception {
		String result = "";
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			String ygid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			strbuf.append(" INSERT INTO ");
			strbuf.append("     YGXX ");
			strbuf.append(" ( ");
			strbuf.append("     YGXX_YGID, ");// 员工ID
			strbuf.append("     YGXX_YGBM, ");// 员工编码
			strbuf.append("     YGXX_YGXM, ");// 员工姓名
			strbuf.append("     YGXX_XB, ");// 性别
			strbuf.append("     YGXX_CSRQ, ");// 出生日期
			strbuf.append("     YGXX_LXFS, ");// 联系方式
			strbuf.append("     YGXX_GRJJ, ");// 个人简介
			strbuf.append("     YGXX_CJR, ");// 创建人
			strbuf.append("     YGXX_CJSJ, ");// 创建时间
			strbuf.append("     YGXX_GXR, ");// 更新人
			strbuf.append("     YGXX_GXSJ ");// 修改时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '" + ygid + "', ");
			strbuf.append("     '" + beanIn.getYGXX_YGBM() + "', ");
			strbuf.append("     '" + beanIn.getYGXX_YGXM() + "', ");
			if(MyStringUtils.isNotBlank(beanIn.getYGXX_XB())){
				strbuf.append("     '" + beanIn.getYGXX_XB() + "', ");
			}else{
				strbuf.append("     '', ");
			}
			strbuf.append("     '" + beanIn.getYGXX_CSRQ() + "', ");
			strbuf.append("     '" + beanIn.getYGXX_LXFS() + "', ");
			strbuf.append("     '" + beanIn.getYGXX_GRJJ() + "', ");
			strbuf.append("     '" + beanIn.getYGXX_CJR() + "', ");
			strbuf.append("     NOW(), ");
			strbuf.append("     '" + beanIn.getYGXX_GXR() + "', ");
			strbuf.append("     NOW() ");
			strbuf.append(" ) ");
			int i = db.executeSQL(strbuf);
			if(i>0){
				result = ygid;
			}

		} catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: updateStaff
	 * @Description: 修改员工信息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-28
	 */
	public int updateStaff(Pojo1110110 beanIn) throws Exception {
		int resultYG = 0;
		int resultYH = 0;
		int result = 0;
		try {
			db.openConnection();
			db.beginTran();
			//更新员工信息表
			StringBuffer strbufyg = new StringBuffer();
			strbufyg.append(" UPDATE ");
			strbufyg.append("     YGXX ");
			strbufyg.append(" SET ");
			if(MyStringUtils.isNotBlank(beanIn.getYGXX_XB())){
				strbufyg.append("     YGXX_XB='").append(beanIn.getYGXX_XB()).append("',");// 性别
			}else{
				strbufyg.append("     YGXX_XB='").append("").append("',");// 性别
			}
			strbufyg.append("     YGXX_YGXM='").append(beanIn.getYGXX_YGXM()).append("',");// 姓名
			strbufyg.append("     YGXX_CSRQ='").append(beanIn.getYGXX_CSRQ()).append("',");// 出生日期
			strbufyg.append("     YGXX_LXFS='").append(beanIn.getYGXX_LXFS()).append("',");// 联系方式
			strbufyg.append("     YGXX_GRJJ='").append(beanIn.getYGXX_GRJJ()).append("',");// 个人简介
			strbufyg.append("     YGXX_GXR='").append(beanIn.getYGXX_GXR()).append("',");// 修改人
			strbufyg.append("     YGXX_GXSJ=NOW()");// 修改时间
			strbufyg.append(" WHERE 1 = 1 AND YGXX_SCBZ = '0'");
			strbufyg.append("  AND   YGXX_YGID='").append(beanIn.getYGXX_YGID()).append("'");//员工ID
			resultYG = db.executeSQL(strbufyg);
			//更新用户信息表
			StringBuffer strbufyh = new StringBuffer();
			strbufyh.append(" UPDATE ");
			strbufyh.append("     YHXX ");
			strbufyh.append(" SET ");
			strbufyh.append("     YHXX_YHMC='").append(beanIn.getYGXX_YGXM()).append("',");// 出生日期
			strbufyh.append("     YHXX_GXR='").append(beanIn.getYGXX_GXR()).append("',");// 修改人
			strbufyh.append("     YHXX_GXSJ=NOW()");// 修改时间
			strbufyh.append(" WHERE 1 = 1 AND YHXX_SCBZ = '0'");
			strbufyh.append("  AND   YHXX_YHID='").append(beanIn.getYGXX_YGBM()).append("'");//员工编码
			resultYH = db.executeSQL(strbufyh);
			if(resultYG>0&&resultYH>0){
				result = 1;
				db.commit();
			}else{
				result = 0;
				db.rollback();
			}
		} catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
}
