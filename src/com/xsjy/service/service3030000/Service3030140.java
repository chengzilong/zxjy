package com.xsjy.service.service3030000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030140;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030141;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030142;

public class Service3030140 extends BaseService {
	private DBManager db = null;

	public Service3030140() {
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
	 * @author czl
	 * @date 2017-08-04
	 */
	public int getClassCount(Pojo3030140 beanIn) throws Exception {
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
	 * @return List<Pojo3030140>
	 * @author czl
	 * @date 2017-08-04
	 */
	public List<Pojo3030140> getClassList(Pojo3030140 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3030140> result = null;

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
			ResultSetHandler<List<Pojo3030140>> rs = new BeanListHandler<Pojo3030140>(
					Pojo3030140.class);
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
	 * @author czl
	 * @date 2017-08-04
	 */
	private String searchClassSql(Pojo3030140 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 公共部分 */
			strbuf.append(" AND M.BCXX_KCFYID = A.KCFY_FYID");
			strbuf.append(" AND A.KCFY_XXID = B.KCXX_KCID");
			strbuf.append(" AND A.KCFY_LXID = C.KCLX_LXID");
			strbuf.append(" AND A.KCFY_SDID = D.SKSD_SDID");
			strbuf.append(" AND A.KCFY_JSID = E.JSXX_JSID");
			strbuf.append(" AND M.BCXX_SCBZ = '0'");
			strbuf.append(" AND A.KCFY_SCBZ = '0'");
			strbuf.append(" AND B.KCXX_SCBZ = '0'");
			strbuf.append(" AND C.KCLX_SCBZ = '0'");
			strbuf.append(" AND D.SKSD_SCBZ = '0'");
			strbuf.append(" AND E.JSXX_SCBZ = '0'");
			strbuf.append(" AND M.BCXX_BCZT IN (0,1)");
			strbuf.append(" AND M.BCXX_SFYZ = '1'");
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
	 * @FunctionName: getStudentCount
	 * @Description: 统计列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2018-08-04
	 */
	public int getStudentCount(Pojo3030141 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.XSXX_XSID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     XSXX A ");
			strbuf.append(" LEFT JOIN JDJY B ON A.XSXX_JD = B.JDJY_JDID ");
			strbuf.append(" INNER JOIN BCXS C ON A.XSXX_XSID = C.BCXS_XSID ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchStudentSql(beanIn));

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
	 * @FunctionName: getStudentList
	 * @Description: 获取列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo3030141>
	 * @author czl
	 * @date 2017-08-03
	 */
	public List<Pojo3030141> getStudentList(Pojo3030141 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3030141> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.XSXX_XSID, ");//学生ID
			strbuf.append("     A.XSXX_XSBM, ");//学生编码
			strbuf.append("     A.XSXX_XSXM, ");//学生姓名
			strbuf.append("     A.XSXX_XB, ");//性别（1-男 2-女）
			strbuf.append("     C.BCXS_BCXSID AS BCXSID, ");//班次学生ID
			strbuf.append("     CASE WHEN A.XSXX_XB = 1 THEN '男' WHEN A.XSXX_XB = 2 THEN '女' ELSE '' END AS XSXB, ");//性别（1-男 2-女）
			strbuf.append("     A.XSXX_CSRQ, ");//出生日期
			strbuf.append("     A.XSXX_LXFS, ");//联系方式
			strbuf.append("     A.XSXX_ZZ, ");//住址
			strbuf.append("     A.XSXX_JD, ");//阶段
			strbuf.append("     A.XSXX_NJ, ");//年级
			strbuf.append("     CONCAT(B.JDJY_JDMC,A.XSXX_NJ,'年级') AS JDNJ, ");//阶段年级
			strbuf.append("     A.XSXX_CRJJ, ");//个人简介
			strbuf.append("     A.XSXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     A.XSXX_CJR, ");//创建人
			strbuf.append("     A.XSXX_CJSJ, ");//创建时间
			strbuf.append("     A.XSXX_GXR, ");//更新人
			strbuf.append("     A.XSXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     XSXX A ");
			strbuf.append(" LEFT JOIN JDJY B ON A.XSXX_JD = B.JDJY_JDID ");
            strbuf.append(" INNER JOIN BCXS C ON A.XSXX_XSID = C.BCXS_XSID ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchStudentSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.XSXX_XSBM ");
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3030141>> rs = new BeanListHandler<Pojo3030141>(
					Pojo3030141.class);
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
	 * @FunctionName: searchStudentSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2017-08-04
	 */
	private String searchStudentSql(Pojo3030141 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 公共部分 */
			strbuf.append(" AND C.BCXS_BCID = '").append(beanIn.getBCID()).append("'");
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 *
	 * @FunctionName: getEnrollCount
	 * @Description: 统计列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-08-04
	 */
	public int getEnrollCount(Pojo3030142 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.BMXX_BMID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     BMXX A ");
			strbuf.append(" INNER JOIN XSXX B ON A.BMXX_XSID = B.XSXX_XSID ");
			strbuf.append(" INNER JOIN KCXX C ON A.BMXX_XXID = C.KCXX_KCID ");
			strbuf.append(" INNER JOIN KCLX D ON A.BMXX_LXID = D.KCLX_LXID ");
			strbuf.append(" INNER JOIN SKSD E ON A.BMXX_SDID = E.SKSD_SDID ");
			strbuf.append(" INNER JOIN JSXX F ON A.BMXX_JSID = F.JSXX_JSID ");
			strbuf.append(" INNER JOIN BMFS H ON A.BMXX_BMFS = H.BMFS_FSID ");
			strbuf.append(" INNER JOIN BMZT I ON A.BMXX_BMZT = I.BMZT_BMZTID ");
			strbuf.append(" INNER JOIN YHXX K ON A.BMXX_CJR = K.YHXX_YHID ");
			strbuf.append(" LEFT JOIN JSXX G ON A.BMXX_TJJSID = G.JSXX_JSID AND G.JSXX_SCBZ = '0' ");
			strbuf.append(" LEFT JOIN BCXX J ON A.BMXX_SSBC = J.BCXX_BCID AND J.BCXX_SCBZ = '0' ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchEnrollSql(beanIn));

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
	 * @FunctionName: getEnrollList
	 * @Description: 获取列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo3030142>
	 * @author czl
	 * @date 2017-08-04
	 */
	public List<Pojo3030142> getEnrollList(Pojo3030142 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3030142> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.BMXX_BMID, ");//报名ID
			strbuf.append("     A.BMXX_XSID, ");//学生ID
			strbuf.append("     B.XSXX_XSXM AS XSXM, ");//学生姓名
			strbuf.append("     B.XSXX_XSBM AS XSDH, ");//学生电话
			strbuf.append("     A.BMXX_XXID, ");//课程ID
			strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
			strbuf.append("     A.BMXX_LXID, ");//课程类型ID
			strbuf.append("     D.KCLX_LXMC AS KCLXMC, ");//课程类型名称
			strbuf.append("     A.BMXX_SDID, ");//上课时段ID
			strbuf.append("     E.SKSD_SDMC AS SKSD, ");//上课时段名称
			strbuf.append("     A.BMXX_XS, ");//学时
			strbuf.append("     A.BMXX_JSID, ");//授课教师ID
			strbuf.append("     F.JSXX_JSXM AS SKJSXM, ");//授课教师姓名
			strbuf.append("     A.BMXX_FY, ");//费用
			strbuf.append("     A.BMXX_YJFY, ");//已交费用
			strbuf.append("     A.BMXX_WJFY, ");//未交费用
			strbuf.append("     A.BMXX_SSBC, ");//班次ID
			strbuf.append("     J.BCXX_BCMC AS BCMC, ");//班次名称
			strbuf.append("     A.BMXX_BMFS, ");//报名方式
			strbuf.append("     H.BMFS_FSMC AS BMFS, ");//报名方式
			strbuf.append("     A.BMXX_BMZT, ");//报名状态
			strbuf.append("     I.BMZT_BMZTMC AS BMZT, ");//报名状态
			strbuf.append("     A.BMXX_TJJSID, ");//推荐教师ID
			strbuf.append("     G.JSXX_JSXM AS TJJSXM, ");//推荐教师姓名
			strbuf.append("     A.BMXX_BZXX, ");//备注信息
			strbuf.append("     A.BMXX_SCBZ, ");//删除标志
			strbuf.append("     K.YHXX_YHMC AS BMXX_CJR, ");//创建人
			strbuf.append("     A.BMXX_CJSJ, ");//创建时间
			strbuf.append("     A.BMXX_GXR, ");//更新人
			strbuf.append("     A.BMXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     BMXX A ");
			strbuf.append(" INNER JOIN XSXX B ON A.BMXX_XSID = B.XSXX_XSID ");
            strbuf.append(" INNER JOIN KCXX C ON A.BMXX_XXID = C.KCXX_KCID ");
            strbuf.append(" INNER JOIN KCLX D ON A.BMXX_LXID = D.KCLX_LXID ");
            strbuf.append(" INNER JOIN SKSD E ON A.BMXX_SDID = E.SKSD_SDID ");
            strbuf.append(" INNER JOIN JSXX F ON A.BMXX_JSID = F.JSXX_JSID ");
            strbuf.append(" INNER JOIN BMFS H ON A.BMXX_BMFS = H.BMFS_FSID ");
            strbuf.append(" INNER JOIN BMZT I ON A.BMXX_BMZT = I.BMZT_BMZTID ");
            strbuf.append(" INNER JOIN YHXX K ON A.BMXX_CJR = K.YHXX_YHID ");
            strbuf.append(" LEFT JOIN JSXX G ON A.BMXX_TJJSID = G.JSXX_JSID AND G.JSXX_SCBZ = '0' ");
            strbuf.append(" LEFT JOIN BCXX J ON A.BMXX_SSBC = J.BCXX_BCID AND J.BCXX_SCBZ = '0' ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchEnrollSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.BMXX_CJSJ DESC ");
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3030142>> rs = new BeanListHandler<Pojo3030142>(
					Pojo3030142.class);
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
	 * @FunctionName: searchEnrollSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2017-08-04
	 */
	private String searchEnrollSql(Pojo3030142 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 公共部分 */
			strbuf.append(" AND A.BMXX_SCBZ = '0'");
			strbuf.append(" AND B.XSXX_SCBZ = '0'");
			strbuf.append(" AND C.KCXX_SCBZ = '0'");
			strbuf.append(" AND D.KCLX_SCBZ = '0'");
			strbuf.append(" AND E.SKSD_SCBZ = '0'");
			strbuf.append(" AND F.JSXX_SCBZ = '0'");
			strbuf.append(" AND H.BMFS_SCBZ = '0'");
			strbuf.append(" AND I.BMZT_SCBZ = '0'");
			strbuf.append(" AND K.YHXX_SCBZ = '0'");
			strbuf.append(" AND A.BMXX_SSBC IS NULL");//所属班次
			strbuf.append(" AND A.BMXX_BMZT IN ('1', '2')");//报名状态
			strbuf.append(" AND A.BMXX_KCFYID = '").append(beanIn.getBMXX_KCFYID()).append("'");//课程费用ID
			strbuf.append(" AND F.JSXX_JSBM = '").append(beanIn.getJSBM()).append("'");
			/* 学生姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getXSXM())) {
				strbuf.append(" AND B.XSXX_XSXM LIKE '%").append(beanIn.getXSXM()).append("%'");
			}
			/* 学生电话 */
			if (MyStringUtils.isNotBlank(beanIn.getXSDH())) {
				strbuf.append(" AND B.XSXX_XSBM = '").append(beanIn.getXSDH()).append("'");
			}
			/* 报名状态 */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_BMZT())) {
				if (!"000".equals(beanIn.getBMXX_BMZT())) {
					strbuf.append(" AND A.BMXX_BMZT = '").append(beanIn.getBMXX_BMZT()).append("'");
				}
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * @FunctionName: checkBcxsexist
	 * @Description: 验证是否存在班次学生
	 * @param beanIn
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2017-08-04
	 */
	public String checkBcxsexist(String stuIds,String stuMcs,String bcid) throws Exception {
		int result = 0;
		String xsxm = "";
		try {
			db.openConnection();
			String[] stuId = stuIds.split(",");
			String[] stuMc = stuMcs.split(",");

			for (int i = 0; i < stuId.length; i++) {
				StringBuffer sql = new StringBuffer();
				sql.append(" SELECT COUNT(BCXS_BCXSID)");
				sql.append("   FROM BCXS A ");
				sql.append("  WHERE 1 = 1 AND BCXS_SCBZ = '0' ");
				if (MyStringUtils.isNotBlank(stuIds)) {
					sql.append("  AND A.BCXS_XSID = ").append(stuId[i])
						.append("");
				}
				if (MyStringUtils.isNotBlank(bcid)) {
					sql.append("  AND A.BCXS_BCID = '").append(bcid)
						.append("'");
				}
				result = db.queryForInteger(sql);
				if(result>0){
					xsxm+=stuMc[i]+".";
				}
			}


		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return xsxm;
	}
	/**
	 *
	 * @FunctionName: relationData
	 * @Description: 班次学生建立关联
	 * @param bcId
	 * @param dataIds
	 * @param stuIds
	 * @param beanUser
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2017-08-04
	 */
	public boolean relationData(String bcId, String dataIds, String stuIds, Pojo_YHXX beanUser) throws Exception {
		boolean result = false;
		int countBCXS = 0;
		int countBMXX = 0;
		StringBuffer strbufBCXS = null;
		String bcxsId = null;

		try {
			db.openConnection();
			db.beginTran();
			/* 向BCXS表插入数据Start */
			String[] stuId = stuIds.split(",");
			for (int i = 0; i < stuId.length; i++) {
				bcxsId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入站点ID
				strbufBCXS = new StringBuffer();
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
				strbufBCXS.append("     '"+bcxsId+"', ");//班次学生ID
				strbufBCXS.append("     '"+bcId+"', ");//班次ID
				strbufBCXS.append("     "+stuId[i]+", ");//学生ID
				strbufBCXS.append("     '0', ");//删除标志（0-正常 1-删除）
				strbufBCXS.append("     '"+beanUser.getYHXX_YHID()+"', ");//创建人
				strbufBCXS.append("     NOW(), ");//创建时间
				strbufBCXS.append("     '"+beanUser.getYHXX_YHID()+"', ");//更新人
				strbufBCXS.append("     NOW() ");//更新时间
				strbufBCXS.append(" ) ");
				countBCXS += db.executeSQL(strbufBCXS);
			}
			/* 向BCXS表插入数据End */
			/* 更新BMXX表数据Start */
			StringBuffer strbufBMXX = new StringBuffer();
			strbufBMXX.append(" UPDATE ");
			strbufBMXX.append("     BMXX ");
			strbufBMXX.append(" SET ");
			strbufBMXX.append("     BMXX_SSBC='").append(bcId).append("',");//班次ID
			strbufBMXX.append("     BMXX_BMZT='3',");//报名状态
			strbufBMXX.append("     BMXX_GXR='").append(beanUser.getYHXX_YHID()).append("',");//更新人
			strbufBMXX.append("     BMXX_GXSJ=NOW()");//更新时间
			strbufBMXX.append(" WHERE ");
			strbufBMXX.append("     BMXX_BMID IN (").append(dataIds).append(")");//报名ID
			countBMXX = db.executeSQL(strbufBMXX);
			/* 更新BMXX表数据End */
			if(countBCXS > 0 && countBCXS == stuId.length && countBMXX > 0 && countBMXX == stuId.length) {
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
	 * @Description: 班次学生取消关联
	 * @param bcId
	 * @param stuIds
	 * @param beanUser
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2017-08-04
	 */
	public boolean cancelRelationData(String bcId, String stuIds, String bcxsids, Pojo_YHXX beanUser) throws Exception {
		boolean result = false;
		int countBCXS = 0;

		try {
			db.openConnection();
			db.beginTran();
			/* 删除BCXS表数据Start */
			StringBuffer strbufBCXS = new StringBuffer();
			strbufBCXS.append(" DELETE FROM ");
			strbufBCXS.append("     BCXS ");
			strbufBCXS.append(" WHERE ");
			strbufBCXS.append("     BCXS_BCID = '").append(bcId).append("'");
			strbufBCXS.append(" AND BCXS_BCXSID IN (").append(bcxsids).append(")");
			//strbufBCXS.append(" AND BCXS_XSID IN (").append(stuIds).append(")");
			countBCXS = db.executeSQL(strbufBCXS);
			/* 删除BCXS表数据End */
			/* 更新BMXX表数据Start */
			StringBuffer strbufBMXX = new StringBuffer();
			strbufBMXX.append(" UPDATE ");
			strbufBMXX.append("     BMXX ");
			strbufBMXX.append(" SET ");
			strbufBMXX.append("     BMXX_BMZT='2',");//报名状态
			strbufBMXX.append("     BMXX_SSBC='',");//班次ID
			strbufBMXX.append("     BMXX_GXR='").append(beanUser.getYHXX_YHID()).append("',");//更新人
			strbufBMXX.append("     BMXX_GXSJ=NOW()");//更新时间
			strbufBMXX.append(" WHERE ");
			strbufBMXX.append("     BMXX_SSBC='").append(bcId).append("'");//班次ID
			strbufBMXX.append(" AND BMXX_XSID IN (").append(stuIds).append(")");//学生ID
			db.executeSQL(strbufBMXX);

			/* 更新BMXX表数据End */
			if(countBCXS > 0 && countBCXS == bcxsids.split(",").length ) {
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
