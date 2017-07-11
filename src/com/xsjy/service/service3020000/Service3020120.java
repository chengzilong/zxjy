package com.xsjy.service.service3020000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_BMXX;
import com.xsjy.pojo.BaseTable.Pojo_XSXX;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020120;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020121;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020122;

public class Service3020120 extends BaseService {
	private DBManager db = null;

	public Service3020120() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 统计列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月8日 上午10:36:25
	 */
	public int getDataCount(Pojo3020120 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.BMXX_BMID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     BMXX A, XSXX B, KCXX C, KCLX D, SKSD E, JSXX F, JSXX G, BMFS H, BMZT I, BCXX J, YHXX K ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			
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
	 * @FunctionName: getDataList
	 * @Description: 获取列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo3020120>
	 * @author ztz
	 * @date 2015年1月8日 上午10:36:33
	 */
	public List<Pojo3020120> getDataList(Pojo3020120 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3020120> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.BMXX_BMID, ");//报名ID
			strbuf.append("     A.BMXX_XSID, ");//学生ID
			strbuf.append("     B.XSXX_XSXM AS XSXM, ");//学生姓名
			strbuf.append("     A.BMXX_XXID, ");//课程信息ID
			strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
			strbuf.append("     A.BMXX_LXID, ");//课程类型ID
			strbuf.append("     D.KCLX_LXMC AS KCLXMC, ");//课程类型名称
			strbuf.append("     A.BMXX_SDID, ");//上课时段ID
			strbuf.append("     E.SKSD_SDMC AS SKSD, ");//上课时段
			strbuf.append("     A.BMXX_XS, ");//学时
			strbuf.append("     A.BMXX_JSID, ");//教师ID
			strbuf.append("     F.JSXX_JSXM AS KCJSXM, ");//授课教师姓名
			strbuf.append("     A.BMXX_FY, ");//费用
			strbuf.append("     A.BMXX_YJFY, ");//已交费用
			strbuf.append("     A.BMXX_WJFY, ");//未交费用
			strbuf.append("     A.BMXX_SSBC, ");//班次ID
			strbuf.append("     J.BCXX_BCMC AS BCMC, ");//班次名称
			strbuf.append("     A.BMXX_BMFS, ");//报名方式
			strbuf.append("     CASE WHEN A.BMXX_TJJSID IS NULL THEN '非代理' ELSE '代理' END AS BMFS, ");
			strbuf.append("     H.BMFS_FSMC, ");//报名方式
			strbuf.append("     A.BMXX_BMZT, ");//报名状态
			strbuf.append("     I.BMZT_BMZTMC AS BMZT, ");//报名状态
			strbuf.append("     A.BMXX_TJJSID, ");//推荐教师ID
			strbuf.append("     G.JSXX_JSXM AS TJJSXM, ");//推荐教师姓名
			strbuf.append("     A.BMXX_BZXX, ");//备注信息
			strbuf.append("     A.BMXX_SCBZ, ");//删除标志
			strbuf.append("     K.YHXX_YHMC AS BMXX_CJR, ");//创建人
			strbuf.append("     TO_CHAR(TO_DATE(A.BMXX_CJSJ, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS') AS BMXX_CJSJ, ");//创建时间
			strbuf.append("     A.BMXX_GXR, ");//更新人
			strbuf.append("     A.BMXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     BMXX A, XSXX B, KCXX C, KCLX D, SKSD E, JSXX F, JSXX G, BMFS H, BMZT I, BCXX J, YHXX K ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.BMXX_CJSJ DESC ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3020120>> rs = new BeanListHandler<Pojo3020120>(
					Pojo3020120.class);
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
	 * @date 2015年1月8日 上午10:36:43
	 */
	private String searchSql(Pojo3020120 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.BMXX_XSID = B.XSXX_XSID");
			strbuf.append(" AND A.BMXX_XXID = C.KCXX_KCID");
			strbuf.append(" AND A.BMXX_LXID = D.KCLX_LXID");
			strbuf.append(" AND A.BMXX_SDID = E.SKSD_SDID");
			strbuf.append(" AND A.BMXX_JSID = F.JSXX_JSID(+)");
			strbuf.append(" AND A.BMXX_TJJSID = G.JSXX_JSID(+)");
			strbuf.append(" AND A.BMXX_BMFS = H.BMFS_FSID");
			strbuf.append(" AND A.BMXX_BMZT = I.BMZT_BMZTID");
			strbuf.append(" AND A.BMXX_SSBC = J.BCXX_BCID(+)");
			strbuf.append(" AND A.BMXX_CJR = K.YHXX_YHID");
			strbuf.append(" AND A.BMXX_SCBZ = '0'");
			strbuf.append(" AND B.XSXX_SCBZ = '0'");
			strbuf.append(" AND C.KCXX_SCBZ = '0'");
			strbuf.append(" AND D.KCLX_SCBZ = '0'");
			strbuf.append(" AND E.SKSD_SCBZ = '0'");
			strbuf.append(" AND F.JSXX_SCBZ(+) = '0'");
			strbuf.append(" AND G.JSXX_SCBZ(+) = '0'");
			strbuf.append(" AND H.BMFS_SCBZ = '0'");
			strbuf.append(" AND I.BMZT_SCBZ = '0'");
			strbuf.append(" AND K.YHXX_SCBZ = '0'");
			strbuf.append(" AND (F.JSXX_JSBM = '").append(beanIn.getJSBM()).append("'");
			strbuf.append(" OR G.JSXX_JSBM = '").append(beanIn.getJSBM()).append("')");
			/* 报名方式 */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_BMFS())) {
				if(!"000".equals(beanIn.getBMXX_BMFS())) {
					if ("1".equals(beanIn.getBMXX_BMFS())) {
						strbuf.append(" AND A.BMXX_TJJSID IS NULL ");
					} else {
						strbuf.append(" AND A.BMXX_TJJSID IS NOT NULL ");
					}
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
	 * @FunctionName: getXsxx
	 * @Description: 获取学生信息
	 * @param xsdh
	 * @return
	 * @throws Exception
	 * @return Pojo_XSXX
	 * @author ztz
	 * @date 2015年1月8日 上午10:36:53
	 */
	public Pojo_XSXX getXsxx(String xsdh) throws Exception {
		Pojo_XSXX dataBean = new Pojo_XSXX();
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.XSXX_XSID, ");//学生ID
			strbuf.append("     A.XSXX_XSXM ");//学生姓名
			strbuf.append(" FROM ");
			strbuf.append("     XSXX A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.XSXX_XSBM = '").append(xsdh).append("'");//学生编码
			
			ResultSetHandler<Pojo_XSXX> rsh = new BeanHandler<Pojo_XSXX>(Pojo_XSXX.class);
			dataBean = db.queryForBeanHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return dataBean;
	}
	/**
	 * 
	 * @FunctionName: getCourseCount
	 * @Description: 统计列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月8日 上午10:37:08
	 */
	public int getCourseCount(Pojo3020121 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.KCFY_FYID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     KCFY A, KCXX B, KCLX C, SKSD D, JSXX E ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchCourseSql(beanIn));
			
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
	 * @FunctionName: getCourseList
	 * @Description: 获取列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo3020121>
	 * @author ztz
	 * @date 2015年1月8日 上午10:37:48
	 */
	public List<Pojo3020121> getCourseList(Pojo3020121 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3020121> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.KCFY_FYID, ");//费用ID
			strbuf.append("     A.KCFY_XXID, ");//课程信息ID
			strbuf.append("     B.KCXX_KCMC AS KCMC, ");//课程名称
			strbuf.append("     A.KCFY_LXID, ");//课程类型ID
			strbuf.append("     C.KCLX_LXMC AS LXMC, ");//类型名称
			strbuf.append("     A.KCFY_SDID, ");//上课时段ID
			strbuf.append("     D.SKSD_SDMC AS SDMC, ");//时段名称
			strbuf.append("     A.KCFY_XS, ");//学时
			strbuf.append("     A.KCFY_JSID, ");//教师ID
			strbuf.append("     E.JSXX_JSBM AS SEARCH_JSBM, ");//教师编码
			strbuf.append("     E.JSXX_JSXM AS JSXM, ");//教师姓名
			strbuf.append("     A.KCFY_FY, ");//费用
			strbuf.append("     A.KCFY_SCBZ, ");//删除标志
			strbuf.append("     A.KCFY_CJR, ");//创建人
			strbuf.append("     A.KCFY_CJSJ, ");//创建时间
			strbuf.append("     A.KCFY_GXR, ");//更新人
			strbuf.append("     A.KCFY_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     KCFY A, KCXX B, KCLX C, SKSD D, JSXX E ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchCourseSql(beanIn));
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3020121>> rs = new BeanListHandler<Pojo3020121>(
					Pojo3020121.class);
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
	 * @FunctionName: searchCourseSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年1月8日 上午10:37:59
	 */
	private String searchCourseSql(Pojo3020121 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.KCFY_XXID = B.KCXX_KCID");
			strbuf.append(" AND A.KCFY_LXID = C.KCLX_LXID");
			strbuf.append(" AND A.KCFY_SDID = D.SKSD_SDID");
			strbuf.append(" AND A.KCFY_JSID = E.JSXX_JSID(+)");
			strbuf.append(" AND A.KCFY_SCBZ = '0'");
			strbuf.append(" AND B.KCXX_SCBZ = '0'");
			strbuf.append(" AND C.KCLX_SCBZ = '0'");
			strbuf.append(" AND D.SKSD_SCBZ = '0'");
			strbuf.append(" AND E.JSXX_SCBZ(+) = '0'");
			if (!MyStringUtils.isNotBlank(beanIn.getSEARCH_JSBM()) && !MyStringUtils.isNotBlank(beanIn.getJSXM())) {
				strbuf.append(" AND E.JSXX_JSBM = '").append(beanIn.getCURRENT_JSBM()).append("'");
			}
			/* 教师编码 */
			if (MyStringUtils.isNotBlank(beanIn.getSEARCH_JSBM())) {
				strbuf.append(" AND E.JSXX_JSBM = '").append(beanIn.getSEARCH_JSBM()).append("'");
			}
			/* 教师姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getJSXM())) {
				strbuf.append(" AND E.JSXX_JSXM LIKE '%").append(beanIn.getJSXM()).append("%'");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
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
	 * @date 2015年1月8日 上午10:38:07
	 */
	public int getClassCount(Pojo3020122 beanIn) throws Exception {
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
	 * @return List<Pojo3020122>
	 * @author ztz
	 * @date 2015年1月8日 上午10:38:56
	 */
	public List<Pojo3020122> getClassList(Pojo3020122 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3020122> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     M.BCXX_BCID, ");//班次ID
			strbuf.append("     M.BCXX_BCMC, ");//班次名称
			strbuf.append("     M.BCXX_KCFYID, ");//课程费用ID
			strbuf.append("     M.BCXX_KCID, ");//课程ID
			strbuf.append("     B.KCXX_KCMC AS KCMC, ");//课程名称
			strbuf.append("     A.KCFY_LXID AS LXID, ");//课程类型ID
			strbuf.append("     C.KCLX_LXMC AS LXMC, ");//课程类型名称
			strbuf.append("     A.KCFY_SDID AS SDID, ");//上课时段ID
			strbuf.append("     D.SKSD_SDMC AS SDMC, ");//上课时段名称
			strbuf.append("     A.KCFY_XS AS XS, ");//学时
			strbuf.append("     A.KCFY_JSID AS JSID, ");//授课教师ID
			strbuf.append("     E.JSXX_JSBM AS SEARCH_JSBM, ");//教师编码
			strbuf.append("     E.JSXX_JSXM AS JSXM, ");//授课教师姓名
			strbuf.append("     A.KCFY_FY AS BCXX_FY, ");//费用
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
			ResultSetHandler<List<Pojo3020122>> rs = new BeanListHandler<Pojo3020122>(
					Pojo3020122.class);
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
	 * @date 2015年1月8日 上午10:39:09
	 */
	private String searchClassSql(Pojo3020122 beanIn) throws Exception {
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
			strbuf.append(" AND M.BCXX_BCZT NOT IN ('2')");
			if (!MyStringUtils.isNotBlank(beanIn.getSEARCH_JSBM()) && !MyStringUtils.isNotBlank(beanIn.getJSXM())) {
				strbuf.append(" AND E.JSXX_JSBM = '").append(beanIn.getCURRENT_JSBM()).append("'");
			}
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
			/* 班次状态 */
			if (MyStringUtils.isNotBlank(beanIn.getBCXX_BCZT())) {
				if (!"000".equals(beanIn.getBCXX_BCZT())) {
					strbuf.append(" AND M.BCXX_BCZT = '").append(beanIn.getBCXX_BCZT()).append("'");
				}
			}
			/* 教师编码 */
			if (MyStringUtils.isNotBlank(beanIn.getSEARCH_JSBM())) {
				strbuf.append(" AND E.JSXX_JSBM = '").append(beanIn.getSEARCH_JSBM()).append("'");
			}
			/* 教师姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getJSXM())) {
				strbuf.append(" AND E.JSXX_JSXM LIKE '%").append(beanIn.getJSXM()).append("%'");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: deleteData
	 * @Description: 删除数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月8日 上午10:39:27
	 */
	public boolean deleteData(Pojo_BMXX beanIn) throws Exception {
		boolean result = false;
		int countBMXX = 0;
		int countBCXS = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbufBMXX = new StringBuffer();
			strbufBMXX.append(" UPDATE ");
			strbufBMXX.append("     BMXX ");
			strbufBMXX.append(" SET ");
			strbufBMXX.append("     BMXX_SCBZ='1',");//删除标志
			strbufBMXX.append("     BMXX_GXR='").append(beanIn.getBMXX_GXR()).append("',");//更新人
			strbufBMXX.append("     BMXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbufBMXX.append(" WHERE ");
			strbufBMXX.append("     BMXX_BMID='").append(beanIn.getBMXX_BMID()).append("'");//报名ID
			countBMXX = db.executeSQL(strbufBMXX);
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_SSBC())) {
				StringBuffer strbufBCXS = new StringBuffer();
				strbufBCXS.append(" DELETE ");
				strbufBCXS.append("     BCXS ");
				strbufBCXS.append(" WHERE 1=1");
				strbufBCXS.append(" AND BCXS_XSID='").append(beanIn.getBMXX_XSID()).append("'");//学生ID
				strbufBCXS.append(" AND BCXS_BCID='").append(beanIn.getBMXX_SSBC()).append("'");//班次ID
				countBCXS = db.executeSQL(strbufBCXS);
			}
			
			if (countBMXX > 0) {
				if (MyStringUtils.isNotBlank(beanIn.getBMXX_SSBC()) && countBCXS > 0 && countBMXX == countBCXS) {
					db.commit();
					result = true;
				} else if (!MyStringUtils.isNotBlank(beanIn.getBMXX_SSBC()) && countBCXS == 0) {
					db.commit();
					result = true;
				} else {
					db.rollback();
				}
			} else {
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
	/**
	 * @FunctionName: checkBcxsexist
	 * @Description: 验证是否存在班次学生
	 * @param beanIn
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2015-02-12
	 */
	public int checkBcxsexist(String stuid,String bcid) throws Exception {
		int result = 0;
		try {
			db.openConnection();		
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(BCXS_BCXSID)");
			sql.append("   FROM BCXS A ");
			sql.append("  WHERE 1 = 1 AND BCXS_SCBZ = '0' ");
			if (MyStringUtils.isNotBlank(stuid)) {	
				sql.append("  AND A.BCXS_XSID = '").append(stuid)
					.append("'");	
			}
			if (MyStringUtils.isNotBlank(bcid)) {	
				sql.append("  AND A.BCXS_BCID = '").append(bcid)
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
	 * 
	 * @FunctionName: insertData
	 * @Description: 新增数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月8日 上午10:39:37
	 */
	public boolean insertData(Pojo3020120 beanIn) throws Exception {
		boolean result = false;
		int countBMXX = 0;
		int countBCXS = 0;
		String strBmxxId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入ID
		String strBcxsId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入ID

		try {
			db.openConnection();
			db.beginTran();
			
			StringBuffer strbufBMXX = new StringBuffer();
			strbufBMXX.append(" INSERT INTO ");
			strbufBMXX.append("     BMXX ");
			strbufBMXX.append(" ( ");
			strbufBMXX.append("     BMXX_BMID, ");//报名ID
			strbufBMXX.append("     BMXX_XSID, ");//学生ID
			strbufBMXX.append("     BMXX_XXID, ");//课程信息ID
			strbufBMXX.append("     BMXX_SDID, ");//上课时段ID
			strbufBMXX.append("     BMXX_XS, ");//学时
			strbufBMXX.append("     BMXX_JSID, ");//教师ID
			strbufBMXX.append("     BMXX_FY, ");//费用
			strbufBMXX.append("     BMXX_YJFY, ");//已交费用
			strbufBMXX.append("     BMXX_WJFY, ");//未交费用
			strbufBMXX.append("     BMXX_LXID, ");//课程类型ID
			strbufBMXX.append("     BMXX_KCFYID, ");//课程费用ID
			strbufBMXX.append("     BMXX_SSBC, ");//所属班次
			strbufBMXX.append("     BMXX_TJJSID, ");//推荐教师ID
			strbufBMXX.append("     BMXX_BMFS, ");//报名方式
			strbufBMXX.append("     BMXX_BMZT, ");//报名状态
			strbufBMXX.append("     BMXX_BZXX, ");//备注信息
			strbufBMXX.append("     BMXX_SCBZ, ");//删除标志
			strbufBMXX.append("     BMXX_CJR, ");//创建人
			strbufBMXX.append("     BMXX_CJSJ, ");//创建时间
			strbufBMXX.append("     BMXX_GXR, ");//更新人
			strbufBMXX.append("     BMXX_GXSJ  ");//更新时间
			strbufBMXX.append(" ) ");
			strbufBMXX.append(" VALUES ");
			strbufBMXX.append(" ( ");
			strbufBMXX.append("     '"+strBmxxId+"', ");//报名ID
			strbufBMXX.append("     '"+beanIn.getBMXX_XSID()+"', ");//学生ID
			strbufBMXX.append("     '"+beanIn.getBMXX_XXID()+"', ");//课程信息ID
			strbufBMXX.append("     '"+beanIn.getBMXX_SDID()+"', ");//上课时段ID
			strbufBMXX.append("     '"+beanIn.getBMXX_XS()+"', ");//学时
			strbufBMXX.append("     '"+beanIn.getBMXX_JSID()+"', ");//教师ID
			strbufBMXX.append("     '"+beanIn.getBMXX_FY()+"', ");//费用
			strbufBMXX.append("     '0', ");//已交费用（默认：0）
			strbufBMXX.append("     '"+beanIn.getBMXX_FY()+"', ");//未交费用=费用-已交费用
			strbufBMXX.append("     '"+beanIn.getBMXX_LXID()+"', ");//课程类型ID
			strbufBMXX.append("     '"+beanIn.getBMXX_KCFYID()+"', ");//课程费用ID
			strbufBMXX.append("     '"+MyStringUtils.safeToString(beanIn.getBMXX_SSBC())+"', ");//所属班次
			if (beanIn.getJSBM().equals(beanIn.getSELECT_JSBM())) {
				strbufBMXX.append("     '', ");//推荐教师ID
				strbufBMXX.append("     '3', ");//报名方式
			} else {
				if ("000".equals(beanIn.getBMXX_TJJSID())) {
					strbufBMXX.append("     '', ");//推荐教师ID
					strbufBMXX.append("     '3', ");//报名方式
				} else {
					strbufBMXX.append("     '"+beanIn.getBMXX_TJJSID()+"', ");//推荐教师ID
					strbufBMXX.append("     '4', ");//报名方式
				}
			}
			if ("0".equals(beanIn.getBMXX_FY())) {//公开课算作已交费，报名状态直接变为“已交费”状态
				strbufBMXX.append("     '2', ");//报名状态
			} else {
				strbufBMXX.append("     '1', ");//报名状态
			}
			strbufBMXX.append("     '"+beanIn.getBMXX_BZXX()+"', ");//备注信息
			strbufBMXX.append("     '0', ");//删除标志
			strbufBMXX.append("     '"+beanIn.getBMXX_CJR()+"', ");//创建人
			strbufBMXX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbufBMXX.append("     '"+beanIn.getBMXX_GXR()+"', ");//更新人
			strbufBMXX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
			strbufBMXX.append(" ) ");
			countBMXX = db.executeSQL(strbufBMXX);
			/* 如果是选班，则向BCXS表插入记录Start */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_SSBC())) {
				StringBuffer strbufBCXS = new StringBuffer();
				strbufBCXS.append(" INSERT INTO ");
				strbufBCXS.append("     BCXS ");
				strbufBCXS.append(" ( ");
				strbufBCXS.append("     BCXS_BCXSID, ");//班次学生ID
				strbufBCXS.append("     BCXS_BCID, ");//班次ID
				strbufBCXS.append("     BCXS_XSID, ");//学生ID
				strbufBCXS.append("     BCXS_SCBZ, ");//删除标志（0-正常 1-删除）
				strbufBCXS.append("     BCXS_CJR, ");//创建人
				strbufBCXS.append("     BCXS_CJSJ, ");//创建时间
				strbufBCXS.append("     BCXS_GXR, ");//更新人
				strbufBCXS.append("     BCXS_GXSJ  ");//更新时间
				strbufBCXS.append(" ) ");
				strbufBCXS.append(" VALUES ");
				strbufBCXS.append(" ( ");
				strbufBCXS.append("     '"+strBcxsId+"', ");//班次学生ID
				strbufBCXS.append("     '"+beanIn.getBMXX_SSBC()+"', ");//班次ID
				strbufBCXS.append("     '"+beanIn.getBMXX_XSID()+"', ");//学生ID
				strbufBCXS.append("     '0', ");//删除标志（0-正常 1-删除）
				strbufBCXS.append("     '"+beanIn.getBMXX_CJR()+"', ");//创建人
				strbufBCXS.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
				strbufBCXS.append("     '"+beanIn.getBMXX_GXR()+"', ");//更新人
				strbufBCXS.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
				strbufBCXS.append(" ) ");
				countBCXS = db.executeSQL(strbufBCXS);
			}
			/* 如果是选班，则向BCXS表插入记录End */
			if (countBMXX > 0) {
				if (MyStringUtils.isNotBlank(beanIn.getBMXX_SSBC()) && countBCXS > 0 && countBMXX == countBCXS) {
					db.commit();
					result = true;
				} else if (!MyStringUtils.isNotBlank(beanIn.getBMXX_SSBC()) && countBCXS == 0) {
					db.commit();
					result = true;
				} else {
					db.rollback();
				}
			} else {
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
	/**
	 * 
	 * @FunctionName: updateData
	 * @Description: 更新数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月8日 上午10:39:57
	 */
	public boolean updateData(Pojo3020120 beanIn) throws Exception {
		boolean result = false;
		int countBMXX = 0;
		int countBCXS = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			
			StringBuffer strbufBMXX = new StringBuffer();
			strbufBMXX.append(" UPDATE ");
			strbufBMXX.append("     BMXX ");
			strbufBMXX.append(" SET ");
			strbufBMXX.append("     BMXX_XXID='").append(beanIn.getBMXX_XXID()).append("',");//课程信息ID
			strbufBMXX.append("     BMXX_SDID='").append(beanIn.getBMXX_SDID()).append("',");//上课时段ID
			strbufBMXX.append("     BMXX_XS='").append(beanIn.getBMXX_XS()).append("',");//学时
			strbufBMXX.append("     BMXX_JSID='").append(beanIn.getBMXX_JSID()).append("',");//教师ID
			strbufBMXX.append("     BMXX_FY='").append(beanIn.getBMXX_FY()).append("',");//费用
			strbufBMXX.append("     BMXX_WJFY='").append(beanIn.getBMXX_FY()).append("',");//未交费用=费用-已交费用
			strbufBMXX.append("     BMXX_LXID='").append(beanIn.getBMXX_LXID()).append("',");//课程类型ID
			strbufBMXX.append("     BMXX_KCFYID='").append(beanIn.getBMXX_KCFYID()).append("',");//课程费用ID
			strbufBMXX.append("     BMXX_SSBC='").append(MyStringUtils.safeToString(beanIn.getBMXX_SSBC())).append("',");//所属班次
			if ("000".equals(beanIn.getBMXX_TJJSID())) {
				strbufBMXX.append("     BMXX_TJJSID='',");//推荐教师ID
				strbufBMXX.append("     BMXX_BMFS='3',");//报名方式
			} else {
				strbufBMXX.append("     BMXX_TJJSID='").append(beanIn.getBMXX_TJJSID()).append("',");//推荐教师ID
				strbufBMXX.append("     BMXX_BMFS='4',");//报名方式
			}
			strbufBMXX.append("     BMXX_BZXX='").append(beanIn.getBMXX_BZXX()).append("',");//备注信息
			strbufBMXX.append("     BMXX_GXR='").append(beanIn.getBMXX_GXR()).append("',");//更新人
			strbufBMXX.append("     BMXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbufBMXX.append(" WHERE ");
			strbufBMXX.append("     BMXX_BMID='").append(beanIn.getBMXX_BMID()).append("'");//报名ID
			countBMXX = db.executeSQL(strbufBMXX);
			/* 如果是选班，则向BCXS表插入记录Start */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_SSBC())) {
				StringBuffer strbufBCXS = new StringBuffer();
				strbufBCXS.append(" UPDATE ");
				strbufBCXS.append("     BCXS ");
				strbufBCXS.append(" SET ");
				strbufBCXS.append("     BCXS_BCID='").append(beanIn.getBMXX_SSBC()).append("',");//班次ID
				strbufBCXS.append("     BCXS_GXR='").append(beanIn.getBMXX_GXR()).append("',");//更新人
				strbufBCXS.append("     BCXS_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
				strbufBCXS.append(" WHERE 1=1");
				strbufBCXS.append(" AND BCXS_BCID='").append(beanIn.getYBCID()).append("'");//班次ID
				strbufBCXS.append(" AND BCXS_XSID='").append(beanIn.getBMXX_XSID()).append("'");//学生ID
				countBCXS = db.executeSQL(strbufBCXS);
			}
			/* 如果是选班，则向BCXS表插入记录End */
			if (countBMXX > 0) {
				if (MyStringUtils.isNotBlank(beanIn.getBMXX_SSBC()) && countBCXS > 0 && countBMXX == countBCXS) {
					db.commit();
					result = true;
				} else if (!MyStringUtils.isNotBlank(beanIn.getBMXX_SSBC()) && countBCXS == 0) {
					db.commit();
					result = true;
				} else {
					db.rollback();
				}
			} else {
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