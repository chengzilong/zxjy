package com.xsjy.service.service3030000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_BCXX;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030120;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030121;

public class Service3030120 extends BaseService {
	private DBManager db = null;

	public Service3030120() {
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
	 * @date 2015年1月6日 上午10:55:04
	 */
	public int getDataCount(Pojo3030120 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(M.BCXX_BCID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     BCXX M, KCFY A, KCXX B, KCLX C, SKSD D, JSXX E ");
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
	 * @return List<Pojo3030120>
	 * @author ztz
	 * @date 2015年1月6日 上午10:55:15
	 */
	public List<Pojo3030120> getDataList(Pojo3030120 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3030120> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     M.BCXX_BCID, ");//班次ID
			strbuf.append("     M.BCXX_BCMC, ");//班次名称
			strbuf.append("     M.BCXX_KCFYID, ");//课程费用ID
			strbuf.append("     M.BCXX_KCID, ");//课程ID
			strbuf.append("     B.KCXX_KCMC AS KCMC, ");//课程名称
			strbuf.append("     C.KCLX_LXMC AS LXMC, ");//类型名称
			strbuf.append("     D.SKSD_SDMC AS SDMC, ");//时段名称
			strbuf.append("     A.KCFY_XS AS XS, ");//学时
			strbuf.append("     E.JSXX_JSXM AS JSXM, ");//教师姓名
			strbuf.append("     A.KCFY_FY AS BCXX_FY, ");//费用
			strbuf.append("     M.BCXX_KSSJ, ");//开始时间
			strbuf.append("     M.BCXX_JSSJ, ");//结束时间
			strbuf.append("     M.BCXX_SKDD, ");//上课地点
			strbuf.append("     M.BCXX_BCZT, ");//班次状态
			strbuf.append("     CASE WHEN M.BCXX_BCZT = 0 THEN '未开课' WHEN M.BCXX_BCZT = 1 THEN '已开课' ELSE '已结束' END AS BCZT, ");//班次状态
			strbuf.append("     M.BCXX_SFYZ, ");//是否验证（0-未验证 1-已验证）
			strbuf.append("     CASE WHEN M.BCXX_SFYZ = 0 THEN '未验证' ELSE '已验证' END SFYZ, ");//是否验证
			strbuf.append("     M.BCXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     M.BCXX_CJR, ");//创建人
			strbuf.append("     M.BCXX_CJSJ, ");//创建时间
			strbuf.append("     M.BCXX_GXR, ");//更新人
			strbuf.append("     M.BCXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     BCXX M, KCFY A, KCXX B, KCLX C, SKSD D, JSXX E ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3030120>> rs = new BeanListHandler<Pojo3030120>(
					Pojo3030120.class);
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
	 * @date 2015年1月6日 上午10:55:34
	 */
	private String searchSql(Pojo3030120 beanIn) throws Exception {
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
			strbuf.append(" AND E.JSXX_JSBM = '").append(beanIn.getJSBM()).append("'");
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
			/* 是否验证 */
			if (MyStringUtils.isNotBlank(beanIn.getBCXX_SFYZ())) {
				if (!"000".equals(beanIn.getBCXX_SFYZ())) {
					strbuf.append(" AND M.BCXX_SFYZ = '").append(beanIn.getBCXX_SFYZ()).append("'");
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
	 * @FunctionName: getCourseCount
	 * @Description: 统计列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月6日 上午10:55:47
	 */
	public int getCourseCount(Pojo3030121 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.KCFY_FYID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     KCFY A, KCXX B, KCLX C, SKSD D, JSXX E, ");
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
			strbuf.append("     F.JSQY_JSID) F ");
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
	 * @return List<Pojo3030121>
	 * @author ztz
	 * @date 2015年1月6日 上午10:56:00
	 */
	public List<Pojo3030121> getCourseList(Pojo3030121 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3030121> result = null;
		
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
			strbuf.append("     F.SKQYMC AS SKQY, ");//授课区域
			strbuf.append("     A.KCFY_XS, ");//学时
			strbuf.append("     A.KCFY_JSID, ");//教师ID
			strbuf.append("     E.JSXX_JSXM AS JSXM, ");//教师姓名
			strbuf.append("     A.KCFY_FY, ");//费用
			strbuf.append("     CASE WHEN A.KCFY_FYID='"+beanIn.getKCFY_FYID()+"' THEN 'KCFY' ELSE '' END AS SXKC,");//所选课程
			strbuf.append("     A.KCFY_SCBZ, ");//删除标志
			strbuf.append("     A.KCFY_CJR, ");//创建人
			strbuf.append("     A.KCFY_CJSJ, ");//创建时间
			strbuf.append("     A.KCFY_GXR, ");//更新人
			strbuf.append("     A.KCFY_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     KCFY A, KCXX B, KCLX C, SKSD D, JSXX E, ");
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
			strbuf.append("     F.JSQY_JSID) F ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchCourseSql(beanIn));
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3030121>> rs = new BeanListHandler<Pojo3030121>(
					Pojo3030121.class);
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
	 * @date 2015年1月6日 上午10:56:15
	 */
	private String searchCourseSql(Pojo3030121 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.KCFY_XXID = B.KCXX_KCID");
			strbuf.append(" AND A.KCFY_LXID = C.KCLX_LXID");
			strbuf.append(" AND A.KCFY_SDID = D.SKSD_SDID");
			strbuf.append(" AND A.KCFY_JSID = E.JSXX_JSID(+)");
			strbuf.append(" AND E.JSXX_JSID = F.QY_JSID(+) ");
			strbuf.append(" AND A.KCFY_SCBZ = '0'");
			strbuf.append(" AND B.KCXX_SCBZ = '0'");
			strbuf.append(" AND C.KCLX_SCBZ = '0'");
			strbuf.append(" AND D.SKSD_SCBZ = '0'");
			strbuf.append(" AND E.JSXX_JSBM = '").append(beanIn.getJSBM()).append("'");
			/* 课程名称 */
			if (MyStringUtils.isNotBlank(beanIn.getKCFY_XXID()) && !("000").equals(beanIn.getKCFY_XXID())) {	
				strbuf.append("  AND A.KCFY_XXID = '").append(beanIn.getKCFY_XXID())
					.append("'");	
			}
			/* 课程类型 */
			if (MyStringUtils.isNotBlank(beanIn.getKCFY_LXID()) && !("000").equals(beanIn.getKCFY_LXID())) {	
				strbuf.append("  AND A.KCFY_LXID = '").append(beanIn.getKCFY_LXID())
					.append("'");	
			}
			/* 上课时段 */
			if (MyStringUtils.isNotBlank(beanIn.getKCFY_SDID()) && !("000").equals(beanIn.getKCFY_SDID())) {	
				strbuf.append("  AND A.KCFY_SDID = '").append(beanIn.getKCFY_SDID())
					.append("'");	
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
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
	 * @date 2015年1月6日 上午10:56:28
	 */
	public boolean insertData(Pojo3030120 beanIn) throws Exception {
		boolean result = false;
		int count_BCXX = 0;
		int count_BCJS = 0;
		StringBuffer strbuf_BCXX = new StringBuffer();
		StringBuffer strbuf_BCJS = new StringBuffer();
		String strBcxxId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入ID
		String strBcjsId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入ID

		try {
			db.openConnection();
			db.beginTran();
			/* 向BCXX表插入数据Start */
			strbuf_BCXX.append(" INSERT INTO ");
			strbuf_BCXX.append("     BCXX ");
			strbuf_BCXX.append(" ( ");
			strbuf_BCXX.append("     BCXX_BCID, ");//班次ID
			strbuf_BCXX.append("     BCXX_KCID, ");//课程ID
			strbuf_BCXX.append("     BCXX_KCFYID, ");//课程费用ID
			strbuf_BCXX.append("     BCXX_BCMC, ");//班次名称
			strbuf_BCXX.append("     BCXX_KSSJ, ");//开始时间
			strbuf_BCXX.append("     BCXX_JSSJ, ");//结束时间
			strbuf_BCXX.append("     BCXX_SKDD, ");//上课地点
			strbuf_BCXX.append("     BCXX_FY, ");//费用
			strbuf_BCXX.append("     BCXX_BCZT, ");//班次状态
			strbuf_BCXX.append("     BCXX_SFYZ, ");//是否验证（0-未验证 1-已验证）
			strbuf_BCXX.append("     BCXX_LRR, ");//录入人
			strbuf_BCXX.append("     BCXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf_BCXX.append("     BCXX_CJR, ");//创建人
			strbuf_BCXX.append("     BCXX_CJSJ, ");//创建时间
			strbuf_BCXX.append("     BCXX_GXR, ");//更新人
			strbuf_BCXX.append("     BCXX_GXSJ  ");//更新时间
			strbuf_BCXX.append(" ) ");
			strbuf_BCXX.append(" VALUES ");
			strbuf_BCXX.append(" ( ");
			strbuf_BCXX.append("     '"+strBcxxId+"', ");//班次ID
			strbuf_BCXX.append("     '"+beanIn.getBCXX_KCID()+"', ");//课程ID
			strbuf_BCXX.append("     '"+beanIn.getBCXX_KCFYID()+"', ");//课程费用ID
			strbuf_BCXX.append("     '"+beanIn.getBCXX_BCMC()+"', ");//班次名称
			strbuf_BCXX.append("     '"+beanIn.getBCXX_KSSJ()+"', ");//开始时间
			strbuf_BCXX.append("     '"+beanIn.getBCXX_JSSJ()+"', ");//结束时间
			strbuf_BCXX.append("     '"+beanIn.getBCXX_SKDD()+"', ");//上课地点
			strbuf_BCXX.append("     '"+beanIn.getBCXX_FY()+"', ");//费用
			strbuf_BCXX.append("     '0', ");//班次状态
			strbuf_BCXX.append("     '0', ");//是否验证（0-未验证 1-已验证）
			strbuf_BCXX.append("     '"+beanIn.getBCXX_LRR()+"', ");//录入人
			strbuf_BCXX.append("     '0', ");//删除标志（0-正常 1-删除）
			strbuf_BCXX.append("     '"+beanIn.getBCXX_CJR()+"', ");//创建人
			strbuf_BCXX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbuf_BCXX.append("     '"+beanIn.getBCXX_GXR()+"', ");//更新人
			strbuf_BCXX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
			strbuf_BCXX.append(" ) ");
			count_BCXX = db.executeSQL(strbuf_BCXX);
			/* 向BCXX表插入数据End */
			/* 如果JSID不为空，则向BCJS表插入数据Start */
			if (MyStringUtils.isNotBlank(beanIn.getJSID())) {
				strbuf_BCJS.append(" INSERT INTO ");
				strbuf_BCJS.append("     BCJS ");
				strbuf_BCJS.append(" ( ");
				strbuf_BCJS.append("     BCJS_BCJSID, ");//班次教师ID
				strbuf_BCJS.append("     BCJS_BCID, ");//班次ID
				strbuf_BCJS.append("     BCJS_JSID, ");//教师ID
				strbuf_BCJS.append("     BCJS_SCBZ, ");//删除标志（0-正常 1-删除）
				strbuf_BCJS.append("     BCJS_CJR, ");//创建人
				strbuf_BCJS.append("     BCJS_CJSJ, ");//创建时间
				strbuf_BCJS.append("     BCJS_GXR, ");//更新人
				strbuf_BCJS.append("     BCJS_GXSJ  ");//更新时间
				strbuf_BCJS.append(" ) ");
				strbuf_BCJS.append(" VALUES ");
				strbuf_BCJS.append(" ( ");
				strbuf_BCJS.append("     '"+strBcjsId+"', ");//班次教师ID
				strbuf_BCJS.append("     '"+strBcxxId+"', ");//班次ID
				strbuf_BCJS.append("     '"+beanIn.getJSID()+"', ");//教师ID
				strbuf_BCJS.append("     '0', ");//删除标志（0-正常 1-删除）
				strbuf_BCJS.append("     '"+beanIn.getBCXX_CJR()+"', ");//创建人
				strbuf_BCJS.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
				strbuf_BCJS.append("     '"+beanIn.getBCXX_GXR()+"', ");//更新人
				strbuf_BCJS.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
				strbuf_BCJS.append(" ) ");
				count_BCJS = db.executeSQL(strbuf_BCJS);
			}
			/* 如果JSID不为空，则向BCJS表插入数据End */
			if (count_BCXX > 0) {
				if (MyStringUtils.isNotBlank(beanIn.getJSID()) && count_BCXX == count_BCJS && count_BCJS > 0) {
					db.commit();
					result = true;
				} else if (!MyStringUtils.isNotBlank(beanIn.getJSID())) {
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
	 * @date 2015年1月6日 上午10:57:02
	 */
	public boolean updateData(Pojo3030120 beanIn) throws Exception {
		boolean result = false;
		int count_BCXX = 0;
		int count_BCJS = 0;
		StringBuffer strbuf_BCXX = new StringBuffer();
		StringBuffer strbuf_BCJS_DEL = new StringBuffer();
		StringBuffer strbuf_BCJS_INS = new StringBuffer();
		String strBcjsId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入ID
		
		try {
			db.openConnection();
			db.beginTran();
			/* 更新BCXX表数据Start */
			strbuf_BCXX.append(" UPDATE ");
			strbuf_BCXX.append("     BCXX ");
			strbuf_BCXX.append(" SET ");
			strbuf_BCXX.append("     BCXX_KCID='").append(beanIn.getBCXX_KCID()).append("',");//课程ID
			strbuf_BCXX.append("     BCXX_KCFYID='").append(beanIn.getBCXX_KCFYID()).append("',");//课程费用ID
			strbuf_BCXX.append("     BCXX_BCMC='").append(beanIn.getBCXX_BCMC()).append("',");//班次名称
			strbuf_BCXX.append("     BCXX_KSSJ='").append(beanIn.getBCXX_KSSJ()).append("',");//开始时间
			strbuf_BCXX.append("     BCXX_JSSJ='").append(beanIn.getBCXX_JSSJ()).append("',");//结束时间
			strbuf_BCXX.append("     BCXX_SKDD='").append(beanIn.getBCXX_SKDD()).append("',");//上课地点
			strbuf_BCXX.append("     BCXX_FY='").append(beanIn.getBCXX_FY()).append("',");//费用
			strbuf_BCXX.append("     BCXX_SFYZ='0',");//是否验证（0-未验证 1-已验证）
			strbuf_BCXX.append("     BCXX_GXR='").append(beanIn.getBCXX_GXR()).append("',");//更新人
			strbuf_BCXX.append("     BCXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbuf_BCXX.append(" WHERE ");
			strbuf_BCXX.append("     BCXX_BCID='").append(beanIn.getBCXX_BCID()).append("'");//班次ID
			count_BCXX = db.executeSQL(strbuf_BCXX);
			/* 更新BCXX表数据End */
			/* 如果JSID不为空，则删除BCJS表原始数据，向BCJS表插入新数据Start */
			if (MyStringUtils.isNotBlank(beanIn.getJSID())) {
				/* 清除BCJS表原数据Start */
				strbuf_BCJS_DEL.append(" DELETE");
				strbuf_BCJS_DEL.append("     BCJS ");
				strbuf_BCJS_DEL.append(" WHERE ");
				strbuf_BCJS_DEL.append("     BCJS_SCBZ = '0'");
				strbuf_BCJS_DEL.append(" AND BCJS_BCID = '").append(beanIn.getBCXX_BCID()).append("'");
				db.executeSQL(strbuf_BCJS_DEL);
				/* 清除BCJS表原数据End */
				/* 向BCJS表插入新数据Start */
				strbuf_BCJS_INS.append(" INSERT INTO ");
				strbuf_BCJS_INS.append("     BCJS ");
				strbuf_BCJS_INS.append(" ( ");
				strbuf_BCJS_INS.append("     BCJS_BCJSID, ");//班次教师ID
				strbuf_BCJS_INS.append("     BCJS_BCID, ");//班次ID
				strbuf_BCJS_INS.append("     BCJS_JSID, ");//教师ID
				strbuf_BCJS_INS.append("     BCJS_SCBZ, ");//删除标志（0-正常 1-删除）
				strbuf_BCJS_INS.append("     BCJS_CJR, ");//创建人
				strbuf_BCJS_INS.append("     BCJS_CJSJ, ");//创建时间
				strbuf_BCJS_INS.append("     BCJS_GXR, ");//更新人
				strbuf_BCJS_INS.append("     BCJS_GXSJ  ");//更新时间
				strbuf_BCJS_INS.append(" ) ");
				strbuf_BCJS_INS.append(" VALUES ");
				strbuf_BCJS_INS.append(" ( ");
				strbuf_BCJS_INS.append("     '"+strBcjsId+"', ");//班次教师ID
				strbuf_BCJS_INS.append("     '"+beanIn.getBCXX_BCID()+"', ");//班次ID
				strbuf_BCJS_INS.append("     '"+beanIn.getJSID()+"', ");//教师ID
				strbuf_BCJS_INS.append("     '0', ");//删除标志（0-正常 1-删除）
				strbuf_BCJS_INS.append("     '"+beanIn.getBCXX_GXR()+"', ");//创建人
				strbuf_BCJS_INS.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
				strbuf_BCJS_INS.append("     '"+beanIn.getBCXX_GXR()+"', ");//更新人
				strbuf_BCJS_INS.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
				strbuf_BCJS_INS.append(" ) ");
				count_BCJS = db.executeSQL(strbuf_BCJS_INS);
				/* 向BCJS表插入新数据End */
			}
			/* 如果JSID不为空，则删除BCJS表原始数据，向BCJS表插入新数据Start */
			if (count_BCXX > 0) {
				if (MyStringUtils.isNotBlank(beanIn.getJSID()) && count_BCXX == count_BCJS && count_BCJS > 0) {
					db.commit();
					result = true;
				} else if (!MyStringUtils.isNotBlank(beanIn.getJSID())) {
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
	 * @FunctionName: deleteData
	 * @Description: 删除数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月6日 上午10:57:41
	 */
	public boolean deleteData(Pojo_BCXX beanIn) throws Exception {
		boolean result = false;
		int count_BCXX = 0;
		int count_BCJS = 0;
		StringBuffer strbuf_BCXX = new StringBuffer();
		StringBuffer strbuf_BCJS = new StringBuffer();

		try {
			db.openConnection();
			db.beginTran();
			/* 删除BCXX表数据Start */
			strbuf_BCXX.append(" UPDATE ");
			strbuf_BCXX.append("     BCXX ");
			strbuf_BCXX.append(" SET ");
			strbuf_BCXX.append("     BCXX_SCBZ='1',");//删除标志（0-正常 1-删除）
			strbuf_BCXX.append("     BCXX_GXR='").append(beanIn.getBCXX_GXR()).append("',");//更新人
			strbuf_BCXX.append("     BCXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbuf_BCXX.append(" WHERE ");
			strbuf_BCXX.append("     BCXX_BCID='").append(beanIn.getBCXX_BCID()).append("'");//班次ID
			count_BCXX = db.executeSQL(strbuf_BCXX);
			/* 删除BCXX表数据End */
			/* 清除BCJS表数据Start */
			strbuf_BCJS.append(" DELETE");
			strbuf_BCJS.append("     BCJS ");
			strbuf_BCJS.append(" WHERE ");
			strbuf_BCJS.append("     BCJS_SCBZ = '0'");
			strbuf_BCJS.append(" AND BCJS_BCID = '").append(beanIn.getBCXX_BCID()).append("'");
			count_BCJS = db.executeSQL(strbuf_BCJS);
			/* 清除BCJS表数据End */
			if (count_BCXX > 0 && count_BCJS > 0) {
				db.commit();
				result = true;
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
	 * @FunctionName: updateBczt
	 * @Description: 更新班次状态
	 * @param dataBean
	 * @param flag
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月6日 上午10:57:53
	 */
	public int updateBczt(Pojo3030120 dataBean, String flag) throws Exception {
		int count = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			/* 更新BCXX表数据Start */
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     BCXX ");
			strbuf.append(" SET ");
			if ("begin".equals(flag)) {
				strbuf.append("     BCXX_BCZT='1',");//班次状态（1：已开课）
			} else {
				strbuf.append("     BCXX_BCZT='2',");//班次状态（2：已结束）
			}
			strbuf.append("     BCXX_GXR='").append(dataBean.getBCXX_GXR()).append("',");//更新人
			strbuf.append("     BCXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbuf.append(" WHERE 1=1");
			if ("begin".equals(flag)) {
				strbuf.append(" AND BCXX_KSSJ < '").append(dataBean.getXTSJ()).append("'");//开始时间
				strbuf.append(" AND BCXX_JSSJ >= '").append(dataBean.getXTSJ()).append("'");//结束时间
			} else {
				strbuf.append(" AND BCXX_JSSJ < '").append(dataBean.getXTSJ()).append("'");//结束时间
			}
			count = db.executeSQL(strbuf);
			/* 更新BCXX表数据End */
			db.commit();
		} catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return count;
	}
}