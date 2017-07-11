package com.xsjy.service.service1030000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030110;
import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030111;
import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030112;
import com.xsjy.pojo.Custom.pojo_1030000.Pojo1030120;

public class Service1030110 extends BaseService {

	private DBManager db = null;

	public Service1030110() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2014-12-16
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
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
	 * @date 2014-12-16
	 * @update ztz at 2014年12月23日 下午2:07:05
	 */
	public int getNewsList_TotalCount(Pojo1030110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(XXXX_XXID)");
			sql.append("   FROM XXXX A ");
			sql.append("  WHERE 1 = 1  AND  XXXX_SCBZ = '0' ");
			sql.append(this.searchSql(beanIn));
			
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
	 * @date 2014-12-16
	 * @update ztz at 2014年12月23日 下午2:07:05
	 */
	public List<Pojo1030110> getNewsList_PageData(Pojo1030110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1030110> result = null;
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.XXXX_XXID, ");//消息ID
			strbuf.append("     A.XXXX_FBR, ");//发布人ID
			strbuf.append("     B.YHXX_YHMC AS FBRXM, ");//发布人姓名
			strbuf.append("     A.XXXX_FBZT, ");//发布主题
			strbuf.append("     A.XXXX_FBNR, ");//发布内容
			strbuf.append("     A.XXXX_FBSJ, ");//发布时间
			strbuf.append("     A.XXXX_YXSJ, ");//有效时间
			strbuf.append("     A.XXXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     A.XXXX_CJR, ");//创建人
			strbuf.append("     A.XXXX_CJSJ, ");//创建时间
			strbuf.append("     A.XXXX_GXR, ");//更新人
			strbuf.append("     A.XXXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     XXXX A LEFT JOIN  YHXX B ON A.XXXX_FBR = B.YHXX_UUID");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.XXXX_SCBZ = '0'");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.XXXX_FBSJ DESC");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
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
	 * 
	 * @FunctionName: searchSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2014年12月23日 下午2:11:45
	 */
	private String searchSql(Pojo1030110 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 有效时间 */
			if (MyStringUtils.isNotBlank(beanIn.getXXXX_YXSJ())) {
				strbuf.append(" AND to_date(A.XXXX_YXSJ,'YYYY-MM-DD') <=").append("to_date('").append(beanIn.getXXXX_YXSJ()).append("','YYYY-MM-DD')");	
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * @FunctionName: insertNews
	 * @Description: 创建消息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-16
	 */
	public int insertNews(Pojo1030110 beanIn, Pojo1030111 beanIn1) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			String xxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			strbuf.append(" INSERT INTO ");
			strbuf.append("     XXXX ");
			strbuf.append(" ( ");
			strbuf.append("     XXXX_XXID, ");// 消息ID
			strbuf.append("     XXXX_FBR, ");// 发布人
			strbuf.append("     XXXX_FBZT, ");// 发布主题
			strbuf.append("     XXXX_FBNR, ");// 发布内容
			strbuf.append("     XXXX_FBSJ, ");// 发布时间
			strbuf.append("     XXXX_YXSJ, ");// 有效时间
			strbuf.append("     XXXX_CJR, ");// 创建人
			strbuf.append("     XXXX_CJSJ, ");// 创建时间
			strbuf.append("     XXXX_GXR, ");// 更新人
			strbuf.append("     XXXX_GXSJ ");// 修改时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '" + xxid + "', ");
			strbuf.append("     '" + beanIn.getXXXX_FBR() + "', ");
			strbuf.append("     '" + beanIn.getXXXX_FBZT() + "', ");
			strbuf.append("     '" + beanIn.getXXXX_FBNR() + "', ");
			strbuf.append("     '" + beanIn.getXXXX_FBSJ() + "', ");
			strbuf.append("     '" + beanIn.getXXXX_YXSJ() + "', ");
			strbuf.append("     '" + beanIn.getXXXX_CJR() + "', ");
			strbuf.append("     '" + sysdate + "', ");
			strbuf.append("     '" + beanIn.getXXXX_GXR() + "', ");
			strbuf.append("     '" + sysdate + "'");
			strbuf.append(" ) ");
			result = db.executeSQL(strbuf);	
			
			this.sendInfo(xxid, beanIn1);
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
	 * @FunctionName: updateNews
	 * @Description: 修改消息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-16
	 */
	public int updateNews(Pojo1030110 beanIn, Pojo1030111 beanIn1) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     XXXX ");
			strbuf.append(" SET ");
			strbuf.append("     XXXX_FBZT='").append(beanIn.getXXXX_FBZT()).append("',");// 发布主题
			strbuf.append("     XXXX_FBNR='").append(beanIn.getXXXX_FBNR()).append("',");// 发布内容
			strbuf.append("     XXXX_FBSJ='").append(beanIn.getXXXX_FBSJ()).append("',");// 发布时间
			strbuf.append("     XXXX_YXSJ='").append(beanIn.getXXXX_YXSJ()).append("',");// 有效时间
			strbuf.append("     XXXX_GXR='").append(beanIn.getXXXX_GXR()).append("',");// 更新人
			strbuf.append("     XXXX_GXSJ='" + sysdate + "'");// 更新时间
			strbuf.append(" WHERE 1 = 1 AND XXXX_SCBZ = '0'");
			strbuf.append("  AND   XXXX_XXID='").append(beanIn.getXXXX_XXID()).append("'");// 消息ID
			result = db.executeSQL(strbuf);		
			
			StringBuffer strbufDel = new StringBuffer();
			strbufDel.append(" DELETE ");
			strbufDel.append("     XXMX ");
			strbufDel.append(" WHERE ");
			strbufDel.append("     XXMX_XXID = '").append(beanIn.getXXXX_XXID()).append("'");
			db.executeSQL(strbufDel);
			
			this.sendInfo(beanIn.getXXXX_XXID(), beanIn1);
		} catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	
	private void sendInfo(String xxid, Pojo1030111 beanIn1) throws Exception {
		String sysdate = getSystemdate();
		
		try {
			String ids = beanIn1.getID();
			String xms = beanIn1.getXM();
			String lxfss = beanIn1.getLXFS();
			
			if(MyStringUtils.isNotBlank(beanIn1.getID())){
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
					strbufO.append("     '" + xxid + "', ");
					strbufO.append("     " + id[i] + ", ");
					strbufO.append("     " + xm[i] + ", ");
					strbufO.append("     " + lxfs[i] + ", ");
					strbufO.append("     '0', ");
					strbufO.append("     '" + beanIn1.getXXMX_CJR() + "', ");
					strbufO.append("     '" + sysdate + "', ");
					strbufO.append("     '" + beanIn1.getXXMX_GXR() + "', ");
					strbufO.append("     '" + sysdate + "'");
					strbufO.append(" ) ");
					db.executeSQL(strbufO);	
				}
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
	}
	
	/**
	 * @FunctionName: deleteNews
	 * @Description: 删除消息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-16
	 */
	public int deleteNews(Pojo1030110 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     XXXX ");
			strbuf.append(" SET ");
			strbuf.append("     XXXX_SCBZ='1'");// 发布内容
			strbuf.append(" WHERE XXXX_XXID='").append(beanIn.getXXXX_XXID())
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
	public List<Pojo1030112> getNewsmxList_PageData(String strXXID, int page,
			int limit, String sort) throws Exception {
		List<Pojo1030112> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.XXMX_MXID,");// 消息明细ID
			sql.append("        A.XXMX_XXID,");// 消息ID
			sql.append("        A.XXMX_JSR,");// 接收人
			sql.append("        A.XXMX_CKZT,");// 查看状态（0-未查看 1-已查看）
			sql.append("        CASE WHEN A.XXMX_CKZT='0' THEN '未查看' ELSE '已查看'  END AS CKZT");// 查看状态
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
			ResultSetHandler<List<Pojo1030112>> rs = new BeanListHandler<Pojo1030112>(
					Pojo1030112.class);
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
			sql.append(" SELECT COUNT(JSXX_JSID) FROM JSXX WHERE JSXX_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(beanIn.getJSXM())) {
				sql.append(" AND JSXX_JSXM LIKE '%").append(beanIn.getJSXM()).append("%'");	
			}
			sql.append("   )+( ");
			sql.append(" SELECT COUNT(XSXX_XSID) FROM XSXX WHERE XSXX_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(beanIn.getXSXM())) {
				sql.append(" AND XSXX_XSXM LIKE '%").append(beanIn.getXSXM()).append("%'");	
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
			sql.append("        JSXX_JSID AS ID,");// ID
			sql.append("        JSXX_JSXM AS XM,");// 姓名
			sql.append("        JSXX_LXFS AS LXFS,");// 联系方式
			sql.append("        '教师' AS LX,");// 类型
			sql.append("        XXMX_JSRID AS JSRID");// 接收人ID
			sql.append("         FROM JSXX, XXMX");
			sql.append("        WHERE     JSXX_JSID = XXMX_JSRID(+)");
			sql.append("        AND JSXX_SCBZ = '0'");
			sql.append("        AND XXMX_SCBZ(+) = '0'");
			sql.append("        AND XXMX_XXID(+) = '").append(beanIn.getXXXX_XXID()).append("'");
			if (MyStringUtils.isNotBlank(beanIn.getJSXM())) {
				sql.append(" AND JSXX_JSXM LIKE '%").append(beanIn.getJSXM()).append("%'");	
			}else if(MyStringUtils.isNotBlank(beanIn.getXSXM())) {
				sql.append(" AND JSXX_JSXM = '").append(beanIn.getJSXM()).append("'");	
			}
			sql.append("        	UNION SELECT");
			sql.append("        XSXX_XSID AS ID,");
			sql.append("        XSXX_XSXM AS XM,");
			sql.append("        XSXX_LXFS AS LXFS,");
			sql.append("        '学生' AS LX,");
			sql.append("        XXMX_JSRID AS JSRID");// 接收人ID
			sql.append("        FROM XSXX, XXMX");
			sql.append("        WHERE     XSXX_XSID = XXMX_JSRID(+)");
			sql.append("        AND XSXX_SCBZ = '0'");
			sql.append("        AND XXMX_SCBZ(+) = '0'");
			sql.append("        AND XXMX_XXID(+) = '").append(beanIn.getXXXX_XXID()).append("'");
			if (MyStringUtils.isNotBlank(beanIn.getXSXM())) {
				sql.append(" AND XSXX_XSXM LIKE '%").append(beanIn.getXSXM()).append("%'");	
			}else if(MyStringUtils.isNotBlank(beanIn.getJSXM())) {
				sql.append(" AND XSXX_XSXM = '").append(beanIn.getXSXM()).append("'");	
			}
			sql.append("        )WHERE 1= 1");
			sql.append(" ORDER BY ");
			sql.append("        LX ");
			System.out.println(sql.toString());
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
}
