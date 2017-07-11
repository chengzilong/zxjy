package com.xsjy.service.service1030000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030110;
import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030120;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;

public class Service1030120 extends BaseService {

	private DBManager db = null;

	public Service1030120() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2014-12-17
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
		String date = dateFormat.format( now ); 
		return date;
	}
	public String getDate() throws Exception{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
		String date = dateFormat.format( now ); 
		return date;
	}
	/**
	 * @FunctionName: getNewsList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-17
	 */
	public int getNewsList_TotalCount(Pojo1030110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(XXXX_XXID)");
			sql.append("   FROM XXXX A ");
			sql.append("  WHERE 1 = 1  AND  XXXX_SCBZ = '0' ");	
			sql.append(" AND A.XXXX_YXSJ>='").append(getDate()).append("'");
			result = db.queryForInteger(sql);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	/**
	 * @FunctionName: getNewsList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1030110>
	 * @author czl
	 * @date 2014-12-17
	 */
	public List<Pojo1030110> getNewsList_PageData(Pojo1030110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1030110> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.XXXX_XXID,");// 消息ID
			sql.append("        B.YHXX_YHMC AS XXXX_FBR,");// 发布人
			sql.append("        CASE WHEN length(A.XXXX_FBNR)>25 THEN SUBSTR(A.XXXX_FBNR,0,25)||'...' ELSE A.XXXX_FBNR END AS FBNR,");// 发布内容缩写
			sql.append("        A.XXXX_FBNR,");// 发布内容
			sql.append("        A.XXXX_FBSJ,");// 发布时间
			sql.append("        A.XXXX_YXSJ");// 有效时间
			sql.append("   FROM XXXX A,YHXX B");
			sql.append("  WHERE   A.XXXX_SCBZ = '0' AND A.XXXX_FBR = B.YHXX_YHID");
			sql.append(" AND A.XXXX_YXSJ>='").append(getDate()).append("'");	
			if (MyStringUtils.isNotBlank(beanIn.getXXXX_YXSJ())) {
				sql.append(" AND A.XXXX_YXSJ<=").append("to_date('").append(beanIn.getXXXX_YXSJ()).append("','YYYY-MM-DD')");	
			}
			sql.append(" ORDER BY ");
			sql.append("        A.XXXX_FBSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1030110>> rs = new BeanListHandler<Pojo1030110>(
					Pojo1030110.class);
			result = db.queryForBeanListHandler(execSql, rs);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: getNewsmxList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-17
	 */
	public int getNewsmxList_TotalCount(String strXXID) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(XXMX_MXID)");
			sql.append("   FROM XXMX A ");
			sql.append("  WHERE 1 = 1  AND  XXMX_SCBZ = '0' ");	
			if (MyStringUtils.isNotBlank(strXXID)) {	
				sql.append("  AND A.XXMX_XXID = '").append(strXXID)
					.append("'");	
			}
			result = db.queryForInteger(sql);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	/**
	 * @FunctionName: getNewsmxList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1030120>
	 * @author czl
	 * @date 2014-12-17
	 */
	public List<Pojo1030120> getNewsmxList_PageData(String strXXID, int page,
			int limit, String sort) throws Exception {
		List<Pojo1030120> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.XXMX_MXID,");// 消息明细ID
			sql.append("        A.XXMX_XXID,");// 消息ID
			sql.append("        A.XXMX_JSR,");// 接收人
			sql.append("        CASE WHEN A.XXMX_CKZT='0' THEN '未查看' ELSE '已查看'  END AS XXMX_CKZT");// 查看状态
			sql.append("   FROM XXMX A");
			sql.append("  WHERE   A.XXMX_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(strXXID)) {	
				sql.append("  AND A.XXMX_XXID = '").append(strXXID)
					.append("'");	
			}
			sql.append(" ORDER BY ");
			sql.append("        A.XXMX_CJSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1030120>> rs = new BeanListHandler<Pojo1030120>(
					Pojo1030120.class);
			result = db.queryForBeanListHandler(execSql, rs);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: getRenyuanList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-16
	 */
	public int getRenyuanList_TotalCount(Pojo1030120 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT((");
			sql.append(" SELECT COUNT(JSXX_JSID) FROM JSXX");
			if (MyStringUtils.isNotBlank(beanIn.getJSXM())) {
				sql.append(" WHERE JSXX_JSXM LIKE '%").append(beanIn.getJSXM()).append("%'");	
			}
			sql.append("   )+( ");
			sql.append(" SELECT COUNT(XSXX_XSID) FROM XSXX");
			if (MyStringUtils.isNotBlank(beanIn.getXSXM())) {
				sql.append(" WHERE XSXX_XSXM LIKE '%").append(beanIn.getXSXM()).append("%'");	
			}
			sql.append("   ))");
			sql.append("  FROM DUAL");
			
			result = db.queryForInteger(sql);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	/**
	 * @FunctionName: getRenyuanList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1030120>
	 * @author czl
	 * @date 2014-12-16
	 */
	public List<Pojo1030120> getRenyuanList_PageData(Pojo1030120 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1030120> result = null;
		try {
			db.openConnection();
			if(MyStringUtils.isNotBlank(beanIn.getJSXM())){
				
			}
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT * FROM (SELECT");// 
			sql.append("        JSXX_JSBM AS ID,");// ID
			sql.append("        JSXX_JSXM AS XM,");// 姓名
			sql.append("        JSXX_LXFS AS LXFS,");// 联系方式
			sql.append("        '教师' AS LX");// 类型
			sql.append("         FROM JSXX");
			if (MyStringUtils.isNotBlank(beanIn.getJSXM())) {
				sql.append(" WHERE JSXX_JSXM LIKE '%").append(beanIn.getJSXM()).append("%'");	
			}else if(MyStringUtils.isNotBlank(beanIn.getXSXM())) {
				sql.append(" WHERE JSXX_JSXM = '").append(beanIn.getJSXM()).append("'");	
			}
			sql.append("        	UNION SELECT");
			sql.append("        XSXX_XSBM AS ID,");
			sql.append("        XSXX_XSXM AS XM,");
			sql.append("        XSXX_LXFS AS LXFS,");
			sql.append("        '学生' AS LX");
			sql.append("        FROM XSXX");
			if (MyStringUtils.isNotBlank(beanIn.getXSXM())) {
				sql.append(" WHERE XSXX_XSXM LIKE '%").append(beanIn.getXSXM()).append("%'");	
			}else if(MyStringUtils.isNotBlank(beanIn.getJSXM())) {
				sql.append(" WHERE XSXX_XSXM = '").append(beanIn.getXSXM()).append("'");	
			}
			sql.append("        )WHERE 1= 1");
			sql.append(" ORDER BY ");
			sql.append("        LX ");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1030120>> rs = new BeanListHandler<Pojo1030120>(
					Pojo1030120.class);
			result = db.queryForBeanListHandler(execSql, rs);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: deleteNewsmx
	 * @Description: 删除消息明细
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-17
	 */
	public int deleteNewsmx(String strMXID) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     XXMX ");
			strbuf.append(" SET ");
			strbuf.append("     XXMX_SCBZ='1'");// 删除标志
			strbuf.append(" WHERE XXMX_MXID='").append(strMXID)
					.append("'");
			result = db.executeSQL(strbuf);
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
	 * @FunctionName: sendNews
	 * @Description: 发布消息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-18
	 */
	public int sendNews(Pojo1030120 beanIn) throws Exception {
		int result = 0;
		int resultA = 0;
		int resultO = 0;
		String jsrname = "";
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			if(!beanIn.getREG().equals("other")){
				if(beanIn.getREG().equals("all")){
					jsrname = "所有人";
				}else if(beanIn.getREG().equals("allteacher")){
					jsrname = "所有教师";
				}else if(beanIn.getREG().equals("allstudent")){
					jsrname = "所有学生";
				}
				StringBuffer strbuf = new StringBuffer();
				String mxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
				strbuf.append(" INSERT INTO ");
				strbuf.append("     XXMX ");
				strbuf.append(" ( ");
				strbuf.append("     XXMX_MXID, ");// 消息明细ID
				strbuf.append("     XXMX_XXID, ");// 消息ID
				strbuf.append("     XXMX_JSRID, ");// 接收人ID
				strbuf.append("     XXMX_JSR, ");// 接收人
				strbuf.append("     XXMX_LXFS, ");// 联系方式
				strbuf.append("     XXMX_CKZT, ");// 查看状态
				strbuf.append("     XXMX_CJR, ");// 创建人
				strbuf.append("     XXMX_CJSJ, ");// 创建时间
				strbuf.append("     XXMX_GXR, ");// 更新人
				strbuf.append("     XXMX_GXSJ ");// 修改时间
				strbuf.append(" ) ");
				strbuf.append(" VALUES ");
				strbuf.append(" ( ");
				strbuf.append("     '" + mxid + "', ");
				strbuf.append("     '" + beanIn.getXXMX_XXID() + "', ");
				strbuf.append("     '" + beanIn.getREG() + "', ");
				strbuf.append("     '" + jsrname + "', ");
				strbuf.append("     '', ");
				strbuf.append("     '0', ");
				strbuf.append("     '" + beanIn.getXXMX_CJR() + "', ");
				strbuf.append("     '" + sysdate + "', ");
				strbuf.append("     '" + beanIn.getXXMX_GXR() + "', ");
				strbuf.append("     '" + sysdate + "'");
				strbuf.append(" ) ");
				resultA = db.executeSQL(strbuf);						
			}
			String ids = beanIn.getID();
			String xms = beanIn.getXM();
			String lxfss = beanIn.getLXFS();
			
			if(MyStringUtils.isNotBlank(beanIn.getID())){
				ids = ids.substring(0, ids.length() - 1);
				xms = xms.substring(0, xms.length() - 1);
				lxfss = lxfss.substring(0, lxfss.length() - 1);
				String[] id = ids.split(",");
				String[] xm = xms.split(",");
				String[] lxfs = lxfss.split(",");
				for(int i = 0; i < id.length; i++) {
					String mxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
					StringBuffer strbufO = new StringBuffer();
					strbufO.append(" INSERT INTO ");
					strbufO.append("     XXMX ");
					strbufO.append(" ( ");
					strbufO.append("     XXMX_MXID, ");//消息明细ID
					strbufO.append("     XXMX_XXID, ");// 消息ID
					strbufO.append("     XXMX_JSRID, ");// 接收人ID
					strbufO.append("     XXMX_JSR, ");// 接收人
					strbufO.append("     XXMX_LXFS, ");// 联系方式
					strbufO.append("     XXMX_CKZT, ");// 查看状态
					strbufO.append("     XXMX_CJR, ");// 创建人
					strbufO.append("     XXMX_CJSJ, ");// 创建时间
					strbufO.append("     XXMX_GXR, ");// 更新人
					strbufO.append("     XXMX_GXSJ ");// 修改时间
					strbufO.append(" ) ");
					strbufO.append(" VALUES ");
					strbufO.append(" ( ");
					strbufO.append("     '" + mxid + "', ");
					strbufO.append("     '" + beanIn.getXXMX_XXID() + "', ");
					strbufO.append("     " + id[i] + ", ");
					strbufO.append("     " + xm[i] + ", ");
					strbufO.append("     " + lxfs[i] + ", ");
					strbufO.append("     '0', ");
					strbufO.append("     '" + beanIn.getXXMX_CJR() + "', ");
					strbufO.append("     '" + sysdate + "', ");
					strbufO.append("     '" + beanIn.getXXMX_GXR() + "', ");
					strbufO.append("     '" + sysdate + "'");
					strbufO.append(" ) ");
					resultO = db.executeSQL(strbufO);	
				}
			}
			if(resultA == 1||resultO ==1){
				result = 1;
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
