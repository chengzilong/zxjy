package com.xsjy.service.service1040000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_1040000.Pojo1040130;
import com.xsjy.pojo.Custom.pojo_1040000.Pojo1040131;

public class Service1040130 extends BaseService {
	private DBManager db = null;
	
	public Service1040130() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getClassCount
	 * @Description: 统计列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2014年12月19日 上午10:04:30
	 */
	public int getClassCount(Pojo1040130 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(M.BCXX_BCID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     BCXX M, KCFY A, KCXX B, KCLX C, SKSD D, JSXX E ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchClassSql(beanIn));
			
			result = db.queryForInteger(strbuf);
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
	 * @FunctionName: getClassList
	 * @Description: 获取列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1040130>
	 * @author ztz
	 * @date 2014年12月19日 上午10:04:43
	 */
	public List<Pojo1040130> getClassList(Pojo1040130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1040130> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     M.BCXX_BCID, ");//班次ID
			strbuf.append("     M.BCXX_BCMC, ");//班次名称
			strbuf.append("     M.BCXX_KCFYID, ");//课程费用ID
			strbuf.append("     M.BCXX_KCID, ");//课程ID
			strbuf.append("     B.KCXX_KCMC AS KCMC, ");//课程名称
			strbuf.append("     A.KCFY_LXID AS KCLXID, ");//课程类型ID
			strbuf.append("     C.KCLX_LXMC AS KCLXMC, ");//课程类型名称
			strbuf.append("     A.KCFY_SDID AS SKSDID, ");//上课时段ID
			strbuf.append("     D.SKSD_SDMC AS SKSDMC, ");//上课时段名称
			strbuf.append("     A.KCFY_XS AS XS, ");//学时
			strbuf.append("     A.KCFY_JSID AS SKJSID, ");//授课教师ID
			strbuf.append("     E.JSXX_JSXM AS SKJSXM, ");//授课教师姓名
			strbuf.append("     M.BCXX_FY, ");//费用
			strbuf.append("     M.BCXX_KSSJ, ");//开始时间
			strbuf.append("     M.BCXX_JSSJ, ");//结束时间
			strbuf.append("     M.BCXX_SKDD, ");//上课地点
			strbuf.append("     M.BCXX_BCZT, ");//班次状态
			strbuf.append("     CASE WHEN M.BCXX_BCZT = 0 THEN '未开课' WHEN M.BCXX_BCZT = 1 THEN '已开课' ELSE '已结束' END AS BCZT, ");//班次状态
			strbuf.append("     M.BCXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     M.BCXX_CJR, ");//创建人
			strbuf.append("     M.BCXX_CJSJ, ");//创建时间
			strbuf.append("     M.BCXX_GXR, ");//更新人
			strbuf.append("     M.BCXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     BCXX M, KCFY A, KCXX B, KCLX C, SKSD D, JSXX E ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchClassSql(beanIn));

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1040130>> rs = new BeanListHandler<Pojo1040130>(
					Pojo1040130.class);
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
	 * @FunctionName: searchClassSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2014年12月19日 上午10:04:54
	 */
	private String searchClassSql(Pojo1040130 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND M.BCXX_KCFYID = A.KCFY_FYID");
			strbuf.append(" AND A.KCFY_XXID = B.KCXX_KCID");
			strbuf.append(" AND A.KCFY_LXID = C.KCLX_LXID");
			strbuf.append(" AND A.KCFY_SDID = D.SKSD_SDID");
			strbuf.append(" AND A.KCFY_JSID = E.JSXX_JSID(+)");
			strbuf.append(" AND M.BCXX_SCBZ = '0'");
			strbuf.append(" AND A.KCFY_SCBZ = '0'");
			strbuf.append(" AND B.KCXX_SCBZ = '0'");
			strbuf.append(" AND C.KCLX_SCBZ = '0'");
			strbuf.append(" AND D.SKSD_SCBZ = '0'");
			strbuf.append(" AND E.JSXX_SCBZ(+) = '0'");
			strbuf.append(" AND M.BCXX_BCZT IN (0,1)");
			/* 班次名称 */
			if (MyStringUtils.isNotBlank(beanIn.getBCXX_BCMC())) {
				strbuf.append(" AND M.BCXX_BCMC LIKE '%").append(beanIn.getBCXX_BCMC()).append("%'");
			}
			/* 课程名称 */
			if (MyStringUtils.isNotBlank(beanIn.getBCXX_KCID())) {
				if (!"000".equals(beanIn.getBCXX_KCID())) {
					strbuf.append(" AND M.BCXX_KCID = '").append(beanIn.getBCXX_KCID()).append("'");
				}
			}
			/* 课程类型 */
			if (MyStringUtils.isNotBlank(beanIn.getKCLXID())) {
				if (!"000".equals(beanIn.getKCLXID())) {
					strbuf.append(" AND A.KCFY_LXID = '").append(beanIn.getKCLXID()).append("'");
				}
			}
			/* 上课时段 */
			if (MyStringUtils.isNotBlank(beanIn.getSKSDID())) {
				if (!"000".equals(beanIn.getSKSDID())) {
					strbuf.append(" AND A.KCFY_SDID = '").append(beanIn.getSKSDID()).append("'");
				}
			}
			/* 授课教师 */
			if (MyStringUtils.isNotBlank(beanIn.getSKJSID())) {
				if (!"000".equals(beanIn.getSKJSID())) {
					strbuf.append(" AND A.KCFY_JSID = '").append(beanIn.getSKJSID()).append("'");
				}
			}
			/* 班次状态 */
			if (MyStringUtils.isNotBlank(beanIn.getBCXX_BCZT())) {
				if (!"000".equals(beanIn.getBCXX_BCZT())) {
					strbuf.append(" AND M.BCXX_BCZT = '").append(beanIn.getBCXX_BCZT()).append("'");
				}
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: getTeacherCount
	 * @Description: 统计列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2014年12月19日 上午10:05:08
	 */
	public int getTeacherCount(Pojo1040131 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.JSXX_JSID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     JSXX A, BCJS B ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchTeacherSql(beanIn));
			
			result = db.queryForInteger(strbuf);
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
	 * @FunctionName: getTeacherList
	 * @Description: 获取列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1040131>
	 * @author ztz
	 * @date 2014年12月19日 上午10:05:48
	 */
	public List<Pojo1040131> getTeacherList(Pojo1040131 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1040131> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.JSXX_JSID, ");//教师ID
			strbuf.append("     A.JSXX_JSBM, ");//教师编码
			strbuf.append("     A.JSXX_JSXM, ");//教师姓名
			strbuf.append("     A.JSXX_XB, ");//教师性别（1-男 2-女）
			strbuf.append("     CASE WHEN A.JSXX_XB = 1 THEN '男' WHEN A.JSXX_XB = 2 THEN '女' ELSE '' END AS JSXB, ");//教师性别（1-男 2-女）
			strbuf.append("     A.JSXX_CSRQ, ");//出生日期
			strbuf.append("     A.JSXX_LXFS, ");//联系方式
			strbuf.append("     A.JSXX_ZZ, ");//住址
			strbuf.append("     A.JSXX_XL, ");//学历
			strbuf.append("     A.JSXX_GRJJ, ");//个人简介
			strbuf.append("     A.JSXX_SFRZ, ");//是否认证(0-未认证 1-已认证)
			strbuf.append("     CASE WHEN A.JSXX_SFRZ = 0 THEN '未认证' WHEN A.JSXX_SFRZ = 1 THEN '已认证' ELSE '' END AS SFRZ, ");//是否认证(0-未认证 1-已认证)
			strbuf.append("     A.JSXX_BYXX, ");//毕业学校
			strbuf.append("     A.JSXX_BYNF, ");//毕业年份
			strbuf.append("     A.JSXX_SFZH, ");//身份证号
			strbuf.append("     A.JSXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     A.JSXX_CJR, ");//创建人
			strbuf.append("     A.JSXX_CJSJ, ");//创建时间
			strbuf.append("     A.JSXX_GXR, ");//更新人
			strbuf.append("     A.JSXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     JSXX A, BCJS B ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchTeacherSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.JSXX_JSBM ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1040131>> rs = new BeanListHandler<Pojo1040131>(
					Pojo1040131.class);
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
	 * @FunctionName: searchTeacherSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2014年12月19日 上午10:06:07
	 */
	private String searchTeacherSql(Pojo1040131 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.JSXX_JSID = B.BCJS_JSID");
			strbuf.append(" AND A.JSXX_SFRZ = '1'");//是否认证(0-未认证 1-已认证)
			strbuf.append(" AND B.BCJS_BCID = '").append(beanIn.getBCID()).append("'");
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: getRelationCount
	 * @Description: 统计列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2014年12月19日 上午10:07:33
	 */
	public int getRelationCount(Pojo1040131 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.JSXX_JSID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     JSXX A, YHXX C, ");
			strbuf.append(" (SELECT ");
			strbuf.append("     A.BCJS_JSID,");
			strbuf.append("     COUNT (A.BCJS_JSID) AS JSGS");
			strbuf.append(" FROM ");
			strbuf.append("     BCJS A ");
			strbuf.append(" WHERE ");
			strbuf.append("     A.BCJS_SCBZ = '0'");
			strbuf.append(" GROUP BY ");
			strbuf.append("     A.BCJS_JSID) B, ");
			strbuf.append(" (SELECT ");
			strbuf.append("     C.JSKM_JSID AS KM_JSID, ");
			strbuf.append("     WMSYS.WM_CONCAT (C.JSKM_KCMC) AS SKKMID, ");
			strbuf.append("     WMSYS.WM_CONCAT (D.KCXX_KCMC) AS SKKMMC, ");
			strbuf.append("     WMSYS.WM_CONCAT (D.KCXX_KCJD) AS JDID, ");
			strbuf.append("     WMSYS.WM_CONCAT (D.KCXX_KMID) AS KMID ");
			strbuf.append(" FROM ");
			strbuf.append("     JSKM C, KCXX D ");
			strbuf.append(" WHERE ");
			strbuf.append("     C.JSKM_KCMC = D.KCXX_KCID ");
			strbuf.append(" AND C.JSKM_SCBZ = 0 ");
			strbuf.append(" AND D.KCXX_SCBZ = 0 ");
			strbuf.append(" GROUP BY ");
			strbuf.append("     C.JSKM_JSID) D, ");
			strbuf.append(" (SELECT ");
			strbuf.append("     F.JSQY_JSID AS QY_JSID, ");
			strbuf.append("     WMSYS.WM_CONCAT (G.XZQY_QYID) AS SKQYID, ");
			strbuf.append("     WMSYS.WM_CONCAT (G.XZQY_QYMC) AS SKQYMC ");
			strbuf.append(" FROM ");
			strbuf.append("     JSQY F, XZQY G ");
			strbuf.append(" WHERE ");
			strbuf.append("     F.JSQY_QYID = G.XZQY_QYID ");
			strbuf.append(" AND F.JSQY_SCBZ = 0 ");
			strbuf.append(" AND G.XZQY_SCBZ = 0 ");
			strbuf.append(" GROUP BY ");
			strbuf.append("     F.JSQY_JSID) E ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchRelationSql(beanIn));
			
			result = db.queryForInteger(strbuf);
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
	 * @FunctionName: getRelationList
	 * @Description: 获取列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1040131>
	 * @author ztz
	 * @date 2014年12月19日 上午10:07:50
	 */
	public List<Pojo1040131> getRelationList(Pojo1040131 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1040131> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.JSXX_JSID, ");//教师ID
			strbuf.append("     A.JSXX_JSBM, ");//教师编码
			strbuf.append("     A.JSXX_JSXM, ");//教师姓名
			strbuf.append("     A.JSXX_XB, ");//教师性别（1-男 2-女）
			strbuf.append("     CASE WHEN A.JSXX_XB = 1 THEN '男' WHEN A.JSXX_XB = 2 THEN '女' ELSE '' END AS JSXB, ");//教师性别（1-男 2-女）
			strbuf.append("     A.JSXX_CSRQ, ");//出生日期
			strbuf.append("     A.JSXX_LXFS, ");//联系方式
			strbuf.append("     A.JSXX_ZZ, ");//住址
			strbuf.append("     A.JSXX_XL, ");//学历
			strbuf.append("     A.JSXX_GRJJ, ");//个人简介
			strbuf.append("     A.JSXX_SFRZ, ");//是否认证(0-未认证 1-已认证)
			strbuf.append("     CASE WHEN A.JSXX_SFRZ = 0 THEN '未认证' WHEN A.JSXX_SFRZ = 1 THEN '已认证' ELSE '' END AS SFRZ, ");//是否认证(0-未认证 1-已认证)
			strbuf.append("     A.JSXX_BYXX, ");//毕业学校
			strbuf.append("     A.JSXX_BYNF, ");//毕业年份
			strbuf.append("     A.JSXX_SFZH, ");//身份证号
			strbuf.append("     CASE WHEN B.JSGS IS NULL THEN '无课' ELSE '有课' END AS SFYK, ");//是否有课
			strbuf.append("     E.SKQYMC AS SKQY, ");//授课区域
			strbuf.append("     D.SKKMMC AS SKKM, ");//授课科目
			strbuf.append("     A.JSXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     C.YHXX_YHMC AS JSXX_CJR, ");//创建人
			strbuf.append("     A.JSXX_CJSJ, ");//创建时间
			strbuf.append("     A.JSXX_GXR, ");//更新人
			strbuf.append("     A.JSXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     JSXX A, YHXX C, ");
			strbuf.append(" (SELECT ");
			strbuf.append("     A.BCJS_JSID,");
			strbuf.append("     COUNT (A.BCJS_JSID) AS JSGS");
			strbuf.append(" FROM ");
			strbuf.append("     BCJS A ");
			strbuf.append(" WHERE ");
			strbuf.append("     A.BCJS_SCBZ = '0'");
			strbuf.append(" GROUP BY ");
			strbuf.append("     A.BCJS_JSID) B, ");
			strbuf.append(" (SELECT ");
			strbuf.append("     C.JSKM_JSID AS KM_JSID, ");
			strbuf.append("     WMSYS.WM_CONCAT (C.JSKM_KCMC) AS SKKMID, ");
			strbuf.append("     WMSYS.WM_CONCAT (D.KCXX_KCMC) AS SKKMMC, ");
			strbuf.append("     WMSYS.WM_CONCAT (D.KCXX_KCJD) AS JDID, ");
			strbuf.append("     WMSYS.WM_CONCAT (D.KCXX_KMID) AS KMID ");
			strbuf.append(" FROM ");
			strbuf.append("     JSKM C, KCXX D ");
			strbuf.append(" WHERE ");
			strbuf.append("     C.JSKM_KCMC = D.KCXX_KCID ");
			strbuf.append(" AND C.JSKM_SCBZ = 0 ");
			strbuf.append(" AND D.KCXX_SCBZ = 0 ");
			strbuf.append(" GROUP BY ");
			strbuf.append("     C.JSKM_JSID) D, ");
			strbuf.append(" (SELECT ");
			strbuf.append("     F.JSQY_JSID AS QY_JSID, ");
			strbuf.append("     WMSYS.WM_CONCAT (G.XZQY_QYID) AS SKQYID, ");
			strbuf.append("     WMSYS.WM_CONCAT (G.XZQY_QYMC) AS SKQYMC ");
			strbuf.append(" FROM ");
			strbuf.append("     JSQY F, XZQY G ");
			strbuf.append(" WHERE ");
			strbuf.append("     F.JSQY_QYID = G.XZQY_QYID ");
			strbuf.append(" AND F.JSQY_SCBZ = 0 ");
			strbuf.append(" AND G.XZQY_SCBZ = 0 ");
			strbuf.append(" GROUP BY ");
			strbuf.append("     F.JSQY_JSID) E ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchRelationSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.JSXX_JSBM ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1040131>> rs = new BeanListHandler<Pojo1040131>(
					Pojo1040131.class);
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
	 * @FunctionName: searchRelationSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2014年12月19日 上午10:08:10
	 */
	private String searchRelationSql(Pojo1040131 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.JSXX_JSID = B.BCJS_JSID(+)");
			strbuf.append(" AND A.JSXX_CJR = C.YHXX_YHID");
			strbuf.append(" AND A.JSXX_JSID = D.KM_JSID(+) ");
			strbuf.append(" AND A.JSXX_JSID = E.QY_JSID(+) ");
			strbuf.append(" AND A.JSXX_SCBZ = '0'");
			strbuf.append(" AND C.YHXX_SCBZ = '0'");
			strbuf.append(" AND A.JSXX_SFRZ = '1'");//是否认证(0-未认证 1-已认证)
			/* 教师姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getJSXX_JSXM())) {
				strbuf.append(" AND A.JSXX_JSXM LIKE '%").append(beanIn.getJSXX_JSXM()).append("%'");
			}
			/* 教师电话 */
			if (MyStringUtils.isNotBlank(beanIn.getJSXX_JSBM())) {
				strbuf.append(" AND A.JSXX_JSBM = '").append(beanIn.getJSXX_JSBM()).append("'");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * @FunctionName: checkBcjsexist
	 * @Description: 验证是否存在班次教师
	 * @param beanIn
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2015-02-02
	 */
	public String checkBcjsexist(String teaIds,String teaMcs,String bcid) throws Exception {
		int result = 0;
		String jsxm = "";
		try {
			db.openConnection();
			String[] teaId = teaIds.split(",");
			String[] teaMc = teaMcs.split(",");
			
			for (int i = 0; i < teaId.length; i++) {
				StringBuffer sql = new StringBuffer();
				sql.append(" SELECT COUNT(BCJS_BCJSID)");
				sql.append("   FROM BCJS A ");
				sql.append("  WHERE 1 = 1 AND BCJS_SCBZ = '0' ");
				if (MyStringUtils.isNotBlank(teaIds)) {	
					sql.append("  AND A.BCJS_JSID = ").append(teaId[i])
						.append("");	
				}
				if (MyStringUtils.isNotBlank(bcid)) {	
					sql.append("  AND A.BCJS_BCID = '").append(bcid)
						.append("'");	
				}
				result = db.queryForInteger(sql);
				if(result>0){
					jsxm+=teaMc[i]+".";		
				}
			}
			
			
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return jsxm;
	}
	/**
	 * 
	 * @FunctionName: relationData
	 * @Description: 班次教师建立关联
	 * @param bcId
	 * @param stuIds
	 * @param beanUser
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2014年12月19日 上午10:34:17
	 */
	public boolean relationData(String bcId, String teaIds, Pojo_YHXX beanUser) throws Exception {
		boolean result = false;
		int countBCJS = 0;
		StringBuffer strbufBCJS = null;
		String bcxsId = null;
		
		try {
			db.openConnection();
			db.beginTran();
			/* 向BCJS表插入数据Start */
			String[] teaId = teaIds.split(",");
			for (int i = 0; i < teaId.length; i++) {
				bcxsId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入站点ID
				strbufBCJS = new StringBuffer();
				strbufBCJS.append(" INSERT INTO ");
				strbufBCJS.append("     BCJS ");
				strbufBCJS.append(" ( ");
				strbufBCJS.append("     BCJS_BCJSID, ");//班次教师ID
				strbufBCJS.append("     BCJS_BCID, ");//班次ID
				strbufBCJS.append("     BCJS_JSID, ");//教师ID
				strbufBCJS.append("     BCJS_SCBZ, ");//删除标志（0-正常 1-删除）
				strbufBCJS.append("     BCJS_CJR, ");//创建人
				strbufBCJS.append("     BCJS_CJSJ, ");//创建时间
				strbufBCJS.append("     BCJS_GXR, ");//更新人
				strbufBCJS.append("     BCJS_GXSJ  ");//更新时间
				strbufBCJS.append(" ) ");
				strbufBCJS.append(" VALUES ");
				strbufBCJS.append(" ( ");
				strbufBCJS.append("     '"+bcxsId+"', ");//班次教师ID
				strbufBCJS.append("     '"+bcId+"', ");//班次ID
				strbufBCJS.append("     "+teaId[i]+", ");//教师ID
				strbufBCJS.append("     '0', ");//删除标志（0-正常 1-删除）
				strbufBCJS.append("     '"+beanUser.getYHXX_YHID()+"', ");//创建人
				strbufBCJS.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
				strbufBCJS.append("     '"+beanUser.getYHXX_YHID()+"', ");//更新人
				strbufBCJS.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
				strbufBCJS.append(" ) ");
				countBCJS += db.executeSQL(strbufBCJS);
			}
			/* 向BCJS表插入数据End */
			if(countBCJS > 0 && countBCJS == teaId.length) {
				result = true;
				db.commit();
			} else {
				db.rollback();
			}
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
	 * @FunctionName: cancelRelationData
	 * @Description: 班次教师取消关联
	 * @param bcId
	 * @param teaIds
	 * @param beanUser
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2014年12月19日 上午10:37:36
	 */
	public boolean cancelRelationData(String bcId, String teaIds, Pojo_YHXX beanUser) throws Exception {
		boolean result = false;
		int countBCJS = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			/* 删除BCJS表数据Start */
			StringBuffer strbufBCJS = new StringBuffer();
			strbufBCJS.append(" DELETE ");
			strbufBCJS.append("     BCJS ");
			strbufBCJS.append(" WHERE ");
			strbufBCJS.append("     BCJS_BCID = '").append(bcId).append("'");//班次ID
			strbufBCJS.append(" AND BCJS_JSID IN (").append(teaIds).append(")");//教师ID
			countBCJS = db.executeSQL(strbufBCJS);
			/* 删除BCJS表数据End */
			if(countBCJS > 0 && countBCJS == teaIds.split(",").length) {
				result = true;
				db.commit();
			} else {
				db.rollback();
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
}
