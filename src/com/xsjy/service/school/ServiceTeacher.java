package com.xsjy.service.school;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030120;
import com.xsjy.pojo.Custom.pojo_school.PojoTeacher;

public class ServiceTeacher extends BaseService {

	private DBManager db = null;

	public ServiceTeacher() {
		db = new DBManager();
	}
	/**
	 *
	 * @FunctionName: getDataBeanCount
	 * @Description: 获取列表数据个数
	 * @param dataBean
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-7-14
	 */
	public int getDataBeanCount(PojoTeacher dataBean) throws Exception {
		int dataCount = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.JSXX_JSID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     JSXX A ");
			strbuf.append(" INNER JOIN JSZG B");
			strbuf.append(" ON A.JSXX_JSZG = B.JSZG_ZGID ");
			strbuf.append(" LEFT JOIN ");
			strbuf.append(" (SELECT ");
			strbuf.append("     C.JSKM_JSID AS KM_JSID, ");
			strbuf.append("     GROUP_CONCAT(C.JSKM_KCMC) AS SKKMID, ");
			strbuf.append("     GROUP_CONCAT(D.KCXX_KCMC) AS SKKMMC, ");
			strbuf.append("     GROUP_CONCAT(D.KCXX_KCJD) AS JDID, ");
			strbuf.append("     GROUP_CONCAT(D.KCXX_KMID) AS KMID ");
			strbuf.append(" FROM ");
			strbuf.append("     JSKM C, KCXX D ");
			strbuf.append(" WHERE ");
			strbuf.append("     C.JSKM_KCMC = D.KCXX_KCID ");
			strbuf.append(" AND C.JSKM_SCBZ = 0 ");
			strbuf.append(" AND D.KCXX_SCBZ = 0 ");
			strbuf.append(" GROUP BY ");
			strbuf.append("     C.JSKM_JSID) C ");
			strbuf.append(" ON A.JSXX_JSID = C.KM_JSID ");
			strbuf.append(" LEFT JOIN ");
			strbuf.append(" (SELECT ");
			strbuf.append("     E.PJXX_BPR AS PJ_JSID, ");
			strbuf.append("     CONCAT(FORMAT(COUNT(CASE WHEN E.PJXX_PJJF = 5 THEN 1 END)/COUNT(E.PJXX_PJJF), 2) * 100,'%' )AS HPL ");
			strbuf.append(" FROM ");
			strbuf.append("     PJXX E ");
			strbuf.append(" WHERE ");
			strbuf.append("     E.PJXX_SCBZ = 0 ");
			strbuf.append(" GROUP BY ");
			strbuf.append("     E.PJXX_BPR) D ");
			strbuf.append(" ON A.JSXX_JSBM = D.PJ_JSID ");
			strbuf.append(" LEFT JOIN ");
			strbuf.append(" (SELECT ");
			strbuf.append("     F.JSQY_JSID AS QY_JSID, ");
			strbuf.append("     GROUP_CONCAT(G.XZQY_QYID) AS SKQYID, ");
			strbuf.append("     GROUP_CONCAT(G.XZQY_QYMC) AS SKQYMC ");
			strbuf.append(" FROM ");
			strbuf.append("     JSQY F, XZQY G ");
			strbuf.append(" WHERE ");
			strbuf.append("     F.JSQY_QYID = G.XZQY_QYID ");
			strbuf.append(" AND F.JSQY_SCBZ = 0 ");
			strbuf.append(" AND G.XZQY_SCBZ = 0 ");
			strbuf.append(" GROUP BY ");
			strbuf.append("     F.JSQY_JSID) E ");
			strbuf.append(" ON A.JSXX_JSID = E.QY_JSID ");
			strbuf.append(" WHERE 1=1");
			strbuf.append(this.searchSql(dataBean));

			dataCount = db.queryForInteger(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return dataCount;
	}
	/**
	 *
	 * @FunctionName: getDataBeanList
	 * @Description: 获取页面数据列表
	 * @param dataBean
	 * @return
	 * @throws Exception
	 * @return List<PojoTeacher>
	 * @author ztz
	 * @date 207-07-18
	 */
	public List<PojoTeacher> getDataBeanList(PojoTeacher dataBean) throws Exception {
		List<PojoTeacher> dataBeanList = null;
		String sort = "";// 排序关键字

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.JSXX_JSID, ");//教学学段
			strbuf.append("     A.JSXX_JSBM, ");
			strbuf.append("     A.JSXX_JSXM, ");
			strbuf.append("     A.JSXX_XL, ");
			strbuf.append("     A.JSXX_BYXX, ");
			strbuf.append("     A.JSXX_ZY, ");
			strbuf.append("     A.JSXX_NJ, ");
			strbuf.append("     CONCAT(A.JSXX_NJ,'级') AS NJ, ");
			strbuf.append("     A.JSXX_JNJY, ");
			strbuf.append("     CASE WHEN A.JSXX_JNJY = '0' THEN '无经验' WHEN A.JSXX_JNJY = '11' THEN '10年以上经验' ELSE A.JSXX_JNJY || '年经验' END AS SKJY, ");
			strbuf.append("     B.JSZG_ZGMC AS JSZGMC, ");
			strbuf.append("     E.SKQYMC AS SKQY, ");
			strbuf.append("     C.SKKMMC AS SKKM, ");
			strbuf.append("     CASE WHEN D.HPL IS NULL THEN '0%' ELSE D.HPL END AS HPL, ");
			strbuf.append("     A.JSXX_GRJJ, ");
			strbuf.append("     A.JSXX_JSJB, ");
			strbuf.append("     CASE WHEN A.JSXX_JSJB = 0 THEN '金牌教师' ELSE '优秀教师' END AS JBCW");
			strbuf.append(" FROM ");
			strbuf.append("     JSXX A ");
	        strbuf.append(" INNER JOIN JSZG B");
	        strbuf.append(" ON A.JSXX_JSZG = B.JSZG_ZGID ");
	        strbuf.append(" LEFT JOIN ");
			strbuf.append(" (SELECT ");
			strbuf.append("     C.JSKM_JSID AS KM_JSID, ");
			strbuf.append("     GROUP_CONCAT(C.JSKM_KCMC) AS SKKMID, ");
			strbuf.append("     GROUP_CONCAT(D.KCXX_KCMC) AS SKKMMC, ");
			strbuf.append("     GROUP_CONCAT(D.KCXX_KCJD) AS JDID, ");
			strbuf.append("     GROUP_CONCAT(D.KCXX_KMID) AS KMID ");
			strbuf.append(" FROM ");
			strbuf.append("     JSKM C, KCXX D ");
			strbuf.append(" WHERE ");
			strbuf.append("     C.JSKM_KCMC = D.KCXX_KCID ");
			strbuf.append(" AND C.JSKM_SCBZ = 0 ");
			strbuf.append(" AND D.KCXX_SCBZ = 0 ");
			strbuf.append(" GROUP BY ");
			strbuf.append("     C.JSKM_JSID) C ");
			strbuf.append(" ON A.JSXX_JSID = C.KM_JSID ");
            strbuf.append(" LEFT JOIN ");
			strbuf.append(" (SELECT ");
			strbuf.append("     E.PJXX_BPR AS PJ_JSID, ");
			strbuf.append("     CONCAT(FORMAT(COUNT(CASE WHEN E.PJXX_PJJF = 5 THEN 1 END)/COUNT(E.PJXX_PJJF), 2) * 100,'%' )AS HPL ");
			strbuf.append(" FROM ");
			strbuf.append("     PJXX E ");
			strbuf.append(" WHERE ");
			strbuf.append("     E.PJXX_SCBZ = 0 ");
			strbuf.append(" GROUP BY ");
			strbuf.append("     E.PJXX_BPR) D ");
			strbuf.append(" ON A.JSXX_JSBM = D.PJ_JSID ");
            strbuf.append(" LEFT JOIN ");
			strbuf.append(" (SELECT ");
			strbuf.append("     F.JSQY_JSID AS QY_JSID, ");
			strbuf.append("     GROUP_CONCAT(G.XZQY_QYID) AS SKQYID, ");
			strbuf.append("     GROUP_CONCAT(G.XZQY_QYMC) AS SKQYMC ");
			strbuf.append(" FROM ");
			strbuf.append("     JSQY F, XZQY G ");
			strbuf.append(" WHERE ");
			strbuf.append("     F.JSQY_QYID = G.XZQY_QYID ");
			strbuf.append(" AND F.JSQY_SCBZ = 0 ");
			strbuf.append(" AND G.XZQY_SCBZ = 0 ");
			strbuf.append(" GROUP BY ");
			strbuf.append("     F.JSQY_JSID) E ");
			strbuf.append(" ON A.JSXX_JSID = E.QY_JSID ");
            strbuf.append(" WHERE 1=1");
			strbuf.append(this.searchSql(dataBean));
			strbuf.append(" ORDER BY ");
			if ("zhpx".equals(dataBean.getFLBQ())) {
				sort = "JSXX_JSJB";
			} else if ("rq".equals(dataBean.getFLBQ())) {
				sort = "HPL DESC";
			} else if ("jy".equals(dataBean.getFLBQ())) {
				sort = "TO_NUMBER(JSXX_JNJY) DESC";
			} else {
				sort = "JSXX_JSJB";
			}
			strbuf.append(sort);

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), Integer.parseInt(dataBean.getPAGE()),
					2, sort);

			ResultSetHandler<List<PojoTeacher>> rsh = new BeanListHandler<PojoTeacher>(PojoTeacher.class);

			dataBeanList = db.queryForBeanListHandler(execSql, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return dataBeanList;
	}
	/**
	 *
	 * @FunctionName: searchSql
	 * @Description: 查询条件部分
	 * @param dataBean
	 * @return
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2017-07-18
	 */
	private String searchSql(PojoTeacher dataBean) throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 公共部分 */
			strbuf.append(" AND A.JSXX_SCBZ = 0 ");
			strbuf.append(" AND B.JSZG_SCBZ = 0 ");
			/* 教学学段 */
			if (MyStringUtils.isNotBlank(dataBean.getJXXD())) {
				if (!"000".equals(dataBean.getJXXD())) {
					strbuf.append(" AND C.JDID LIKE '%").append(dataBean.getJXXD()).append("%'");
				}
			}
			/* 求教学科 */
			if (MyStringUtils.isNotBlank(dataBean.getQJXK())) {
				if (!"000".equals(dataBean.getQJXK())) {
					strbuf.append(" AND C.KMID LIKE '%").append(dataBean.getQJXK()).append("%'");
				}
			}
			/* 教师类型 */
			if (MyStringUtils.isNotBlank(dataBean.getJSLX())) {
				if (!"000".equals(dataBean.getJSLX())) {
					strbuf.append(" AND A.JSXX_JSZG = '").append(dataBean.getJSLX()).append("'");
				}
			}
			/* 性别要求 */
			if (MyStringUtils.isNotBlank(dataBean.getXBYQ())) {
				if (!"000".equals(dataBean.getXBYQ())) {
					strbuf.append(" AND A.JSXX_XB = '").append(dataBean.getXBYQ()).append("'");
				}
			}
			/* 授课区域 */
			if (MyStringUtils.isNotBlank(dataBean.getSKQY())) {
				if (!"000".equals(dataBean.getSKQY())) {
					strbuf.append(" AND E.SKQYID LIKE '%").append(dataBean.getSKQY()).append("%'");
				}
			}
			/* 教师姓名 */
			if (MyStringUtils.isNotBlank(dataBean.getJSXX_JSXM())) {
				strbuf.append(" AND A.JSXX_JSXM LIKE '%").append(dataBean.getJSXX_JSXM()).append("%'");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 *
	 * @FunctionName: getDataBean
	 * @Description: 获取页面数据详情
	 * @param teaId
	 * @param xsbm
	 * @return
	 * @throws Exception
	 * @return PojoTeacher
	 * @author ztz
	 * @date 2014年12月30日 下午2:51:29
	 * @update ztz at 2015年1月20日 下午2:51:03
	 */
	public PojoTeacher getDataBean(String teaId, String xsbm) throws Exception {
		PojoTeacher dataBean = null;

		try {
			String stuId = this.getStuId(xsbm);
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.JSXX_JSID, ");//教学学段
			strbuf.append("     A.JSXX_JSBM, ");
			strbuf.append("     A.JSXX_JSXM, ");
			strbuf.append("     A.JSXX_XB, ");
			strbuf.append("     CASE WHEN A.JSXX_XB = 1 THEN '男' WHEN A.JSXX_XB = 2 THEN '女' ELSE '' END AS XBMC, ");
			strbuf.append("     A.JSXX_XL, ");
			strbuf.append("     A.JSXX_ZZ, ");
			strbuf.append("     A.JSXX_SFRZ, ");
			strbuf.append("     CASE WHEN A.JSXX_SFRZ = 1 THEN '教师已认证' ELSE '教师未认证' END AS SFRZ, ");
			strbuf.append("     A.JSXX_BYXX, ");
			strbuf.append("     A.JSXX_ZY, ");
			strbuf.append("     A.JSXX_NJ, ");
			strbuf.append("     A.JSXX_NJ || '级' AS NJ, ");
			strbuf.append("     A.JSXX_JNJY, ");
			strbuf.append("     CASE WHEN A.JSXX_JNJY = '0' THEN '无经验' WHEN A.JSXX_JNJY = '11' THEN '10年以上经验' ELSE A.JSXX_JNJY || '年经验' END AS SKJY, ");
			strbuf.append("     B.JSZG_ZGMC AS JSZGMC, ");
			strbuf.append("     E.SKQYMC AS SKQY, ");
			strbuf.append("     C.SKKMMC AS SKKM, ");
			strbuf.append("     CASE WHEN D.HPL IS NULL THEN '0%' ELSE D.HPL END AS HPL, ");
			strbuf.append("     A.JSXX_GRJJ, ");
			strbuf.append("     A.JSXX_JSJB, ");
			strbuf.append("     CASE WHEN A.JSXX_JSJB = 0 THEN '金牌教师' ELSE '优秀教师' END AS JBCW, ");
			strbuf.append("     A.JSXX_SCLY, ");
			strbuf.append("     A.JSXX_XQAH, ");
			strbuf.append("     A.JSXX_GRJJ, ");
			strbuf.append("     CASE WHEN F.GZXX_GZID IS NULL THEN '0' ELSE '1' END AS ISGZ ");
			strbuf.append(" FROM ");
			strbuf.append("     JSXX A, JSZG B, ");
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
			strbuf.append("     C.JSKM_JSID) C, ");
			strbuf.append(" (SELECT ");
			strbuf.append("     E.PJXX_BPR AS PJ_JSID, ");
			strbuf.append("     TO_CHAR(ROUND(COUNT (CASE WHEN E.PJXX_PJJF = 5 THEN 1 END) / COUNT (E.PJXX_PJJF), 2) * 100, 'FM990') || '%' AS HPL ");
			strbuf.append(" FROM ");
			strbuf.append("     PJXX E ");
			strbuf.append(" WHERE ");
			strbuf.append("     E.PJXX_SCBZ = 0 ");
			strbuf.append(" GROUP BY ");
			strbuf.append("     E.PJXX_BPR) D, ");
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
			strbuf.append("     F.JSQY_JSID) E, GZXX F ");
			strbuf.append(" WHERE ");
			strbuf.append("     A.JSXX_JSZG = B.JSZG_ZGID ");
			strbuf.append(" AND A.JSXX_JSID = C.KM_JSID(+) ");
			strbuf.append(" AND A.JSXX_JSBM = D.PJ_JSID(+) ");
			strbuf.append(" AND A.JSXX_JSID = E.QY_JSID(+) ");
			strbuf.append(" AND A.JSXX_JSID = F.GZXX_BGR(+)");
			strbuf.append(" AND A.JSXX_SCBZ = 0 ");
			strbuf.append(" AND B.JSZG_SCBZ = 0 ");
			strbuf.append(" AND A.JSXX_JSID = '").append(teaId).append("'");
			strbuf.append(" AND F.GZXX_GZR(+) = '").append(stuId).append("'");

			ResultSetHandler<PojoTeacher> rsh = new BeanHandler<PojoTeacher>(PojoTeacher.class);

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
	 * @FunctionName: getRotaDateList
	 * @Description: 获取从当前日期开始一周的排班表日期信息（用于查询教师是否有课）
	 * @return
	 * @throws Exception
	 * @return List<PojoTeacher>
	 * @author ztz
	 * @date 2014年12月31日 下午2:01:01
	 */
	public List<PojoTeacher> getRotaDateList() throws Exception {
		List<PojoTeacher> rotaDateList = new ArrayList<PojoTeacher>();

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("    TO_CHAR (SYSDATE + ROWNUM - 1, 'yyyy-MM') AS ROTA_NY,");
			strbuf.append("    TO_CHAR (SYSDATE + ROWNUM - 1, 'dd') AS ROTA_DAY,");
			strbuf.append("    TO_CHAR (SYSDATE + ROWNUM - 1, 'DAY') AS ROTA_WEEK");
			strbuf.append(" FROM ");
			strbuf.append("    DUAL");
			strbuf.append(" CONNECT BY ");
			strbuf.append("    ROWNUM <= 7");

			ResultSetHandler<List<PojoTeacher>> rsh = new BeanListHandler<PojoTeacher>(PojoTeacher.class);

			rotaDateList = db.queryForBeanListHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return rotaDateList;
	}
	/**
	 *
	 * @FunctionName: getRotaCourseList
	 * @Description: 获取从当前日期开始一周的排班表教师是否有课信息
	 * @param teaId
	 * @return
	 * @throws Exception
	 * @return List<PojoTeacher>
	 * @author ztz
	 * @date 2015年1月4日 下午2:06:26
	 * @update ztz at 2015年1月4日 下午2:06:26
	 */
	public List<PojoTeacher> getRotaCourseList(String teaId) throws Exception {
		List<PojoTeacher> rotaDateList = new ArrayList<PojoTeacher>();
		List<PojoTeacher> rotaCourseList = new ArrayList<PojoTeacher>();
		StringBuffer strbuf = new StringBuffer();
		String strSelect = "";

		try {
			/* 获取排课表SQL中SELECT部分语句Start */
			rotaDateList = this.getRotaDateList();
			for(int i = 0; i < rotaDateList.size(); i++) {
				PojoTeacher rotaDateBean = (PojoTeacher)rotaDateList.get(i);
				strSelect += " CASE WHEN SUM(CASE WHEN C.BCXX_BCID IS NULL OR D.BCRC_BCRCID IS NULL THEN 0 ELSE CASE WHEN D.BCRC_NY = '" + rotaDateBean.getROTA_NY() + "' THEN TO_NUMBER(NVL(D.BCRC_DAY" + rotaDateBean.getROTA_DAY() + ", 0)) END END) = 0 THEN 'keyue' ELSE 'meikong' END  AS ROTA_DAY" + (i+1) + ", ";
			}
			db.openConnection();
			/* 获取排课表SQL中SELECT部分语句End */
			strbuf.append(" SELECT ");
			strbuf.append("    B.SKSD_SDMC AS ROTA_SDMC, ");
			strbuf.append(strSelect);
			strbuf.append("    B.SKSD_SDID ");
			strbuf.append(" FROM ");
			strbuf.append("    KCFY A, SKSD B, BCXX C, BCRC D");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.KCFY_SDID(+) = B.SKSD_SDID");
			strbuf.append(" AND A.KCFY_FYID = C.BCXX_KCFYID(+)");
			strbuf.append(" AND C.BCXX_BCID = D.BCRC_BCID(+)");
			strbuf.append(" AND A.KCFY_SCBZ(+) = '0'");
			strbuf.append(" AND B.SKSD_SCBZ = '0'");
			strbuf.append(" AND C.BCXX_SCBZ(+) = '0'");
			strbuf.append(" AND D.BCRC_SCBZ(+) = '0'");
			strbuf.append(" AND A.KCFY_JSID(+) = '").append(teaId).append("'");
			strbuf.append(" GROUP BY ");
			strbuf.append("    B.SKSD_SDID, B.SKSD_SDMC");
			strbuf.append(" ORDER BY ");
			strbuf.append("    B.SKSD_SDID");

			ResultSetHandler<List<PojoTeacher>> rsh = new BeanListHandler<PojoTeacher>(PojoTeacher.class);

			rotaCourseList = db.queryForBeanListHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return rotaCourseList;
	}
	/**
	 *
	 * @FunctionName: getPjxxCount
	 * @Description: 获取评价信息列表个数
	 * @param teaId
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月19日 下午4:38:03
	 */
	public int getPjxxCount(String teaId) throws Exception {
		int count = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.PJXX_PJID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     PJXX A, XSXX B ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.PJXX_PJR = B.XSXX_XSID");
			strbuf.append(" AND A.PJXX_BPR = '").append(teaId).append("'");

			count = db.queryForInteger(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return count;
	}
	/**
	 *
	 * @FunctionName: getPjxxList
	 * @Description: 获取评价信息列表数据
	 * @param teaId
	 * @return
	 * @throws Exception
	 * @return List<PojoTeacher>
	 * @author ztz
	 * @date 2015年1月19日 下午3:58:00
	 */
	public List<PojoTeacher> getPjxxList(String page, String teaId) throws Exception {
		List<PojoTeacher> pjxxList = new ArrayList<PojoTeacher>();

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.PJXX_PJID, ");//评价ID
			strbuf.append("     A.PJXX_BCID, ");//班次ID
			strbuf.append("     A.PJXX_PJR, ");//评价人
			strbuf.append("     A.PJXX_BPR, ");//被评人
			strbuf.append("     A.PJXX_PJNR, ");//评价内容
			strbuf.append("     A.PJXX_PJJF, ");//评价积分
			strbuf.append("     A.PJXX_PJSJ, ");//评价时间
			strbuf.append("     B.XSXX_XSXM AS XSXM ");//学生姓名
			strbuf.append(" FROM ");
			strbuf.append("     PJXX A, XSXX B ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.PJXX_PJR = B.XSXX_XSID");
			strbuf.append(" AND A.PJXX_BPR = '").append(teaId).append("'");

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), Integer.parseInt(page),
					3, "");

			ResultSetHandler<List<PojoTeacher>> rsh = new BeanListHandler<PojoTeacher>(PojoTeacher.class);

			pjxxList = db.queryForBeanListHandler(execSql, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return pjxxList;
	}
	/**
	 *
	 * @FunctionName: getHdxxCount
	 * @Description: 获取回答信息列表个数
	 * @param teaId
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月20日 上午10:30:03
	 */
	public int getHdxxCount(String teaId) throws Exception {
		int count = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.HDXX_HDID) ");//回答ID
			strbuf.append(" FROM ");
			strbuf.append("     HDXX A, WTXX B ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.HDXX_WTID = B.WTXX_WTID ");
			strbuf.append(" AND A.HDXX_SCBZ = '0'");
			strbuf.append(" AND B.WTXX_SCBZ = '0'");
			strbuf.append(" AND A.HDXX_HDR = '").append(teaId).append("'");

			count = db.queryForInteger(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return count;
	}
	/**
	 *
	 * @FunctionName: getPjxxList
	 * @Description: 获取评价信息列表数据
	 * @param teaId
	 * @return
	 * @throws Exception
	 * @return List<PojoTeacher>
	 * @author ztz
	 * @date 2015年1月19日 下午3:58:00
	 */
	public List<PojoTeacher> getHdxxList(String page, String teaId) throws Exception {
		List<PojoTeacher> hdxxList = new ArrayList<PojoTeacher>();

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.HDXX_HDID, ");//回答ID
			strbuf.append("     A.HDXX_WTID, ");//问题ID
			strbuf.append("     A.HDXX_HDR, ");//回答人
			strbuf.append("     A.HDXX_HDNR, ");//回答内容
			strbuf.append("     A.HDXX_HDSJ, ");//回答时间
			strbuf.append("     A.HDXX_HDJF, ");//回答积分
			strbuf.append("     B.WTXX_WTID, ");//问题ID
			strbuf.append("     B.WTXX_TWR, ");//提问人
			strbuf.append("     B.WTXX_TWNR, ");//提问内容
			strbuf.append("     B.WTXX_TWSJ, ");//提问时间
			strbuf.append("     B.WTXX_TWJF ");//提问积分
			strbuf.append(" FROM ");
			strbuf.append("     HDXX A, WTXX B, XSXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.HDXX_WTID = B.WTXX_WTID ");
			strbuf.append(" AND B.WTXX_TWR = C.XSXX_XSID");
			strbuf.append(" AND A.HDXX_SCBZ = '0'");
			strbuf.append(" AND B.WTXX_SCBZ = '0'");
			strbuf.append(" AND C.XSXX_SCBZ = '0'");
			strbuf.append(" AND A.HDXX_HDR = '").append(teaId).append("'");

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), Integer.parseInt(page),
					3, "");

			ResultSetHandler<List<PojoTeacher>> rsh = new BeanListHandler<PojoTeacher>(PojoTeacher.class);

			hdxxList = db.queryForBeanListHandler(execSql, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return hdxxList;
	}
	/**
	 *
	 * @FunctionName: getClassList
	 * @Description: 获取班次信息列表
	 * @param teaId
	 * @return
	 * @throws Exception
	 * @return List<Pojo3030120>
	 * @author ztz
	 * @date 2015年1月20日 下午12:08:41
	 */
	public List<Pojo3030120> getClassList(String teaId) throws Exception {
		List<Pojo3030120> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT * FROM (");
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
			strbuf.append("     M.BCXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     M.BCXX_CJR, ");//创建人
			strbuf.append("     M.BCXX_CJSJ, ");//创建时间
			strbuf.append("     M.BCXX_GXR, ");//更新人
			strbuf.append("     M.BCXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     BCXX M, KCFY A, KCXX B, KCLX C, SKSD D, JSXX E ");
			strbuf.append(" WHERE 1=1 ");
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
			strbuf.append(" AND M.BCXX_SFYZ = '1'");
			strbuf.append(" AND E.JSXX_JSID = '").append(teaId).append("'");
			strbuf.append(")");
			strbuf.append(" WHERE ROWNUM <= 5");

			ResultSetHandler<List<Pojo3030120>> rs = new BeanListHandler<Pojo3030120>(
					Pojo3030120.class);
			result = db.queryForBeanListHandler(strbuf, rs);

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
	 * @FunctionName: getStuId
	 * @Description: 通过学生编码获取学生ID
	 * @param xsbm
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年1月20日 下午2:22:55
	 */
	private String getStuId(String xsbm) throws Exception {
		String stuId = "";

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("    A.XSXX_XSID ");//学生ID
			strbuf.append(" FROM ");
			strbuf.append("    XSXX A ");
			strbuf.append(" WHERE 1=1");
			strbuf.append(" AND A.XSXX_SCBZ = '0'");
			strbuf.append(" AND A.XSXX_XSBM = '").append(xsbm).append("'");

			stuId = db.queryForString(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return stuId;
	}
	/**
	 *
	 * @FunctionName: attention
	 * @Description: 增加关注教师数据
	 * @param beanUser
	 * @param teaId
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月20日 下午2:28:52
	 */
	public boolean attention(Pojo_YHXX beanUser, String teaId) throws Exception {
		boolean result = false;
		int count = 0;
		String strId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入ID

		try {
			String stuId = this.getStuId(beanUser.getYHXX_YHID());//获取学生ID
			db.openConnection();
			db.beginTran();
			/* 向GZXX表插入数据Start */
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     GZXX ");
			strbuf.append(" ( ");
			strbuf.append("     GZXX_GZID, ");//关注ID
			strbuf.append("     GZXX_GZR, ");//关注人
			strbuf.append("     GZXX_BGR, ");//被关注人
			strbuf.append("     GZXX_BZXX, ");//备注信息
			strbuf.append("     GZXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     GZXX_CJR, ");//创建人
			strbuf.append("     GZXX_CJSJ, ");//创建时间
			strbuf.append("     GZXX_GXR, ");//更新人
			strbuf.append("     GZXX_GXSJ  ");//更新时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+strId+"', ");//关注ID
			strbuf.append("     '"+stuId+"', ");//关注人
			strbuf.append("     '"+teaId+"', ");//被关注人
			strbuf.append("     '', ");//备注信息
			strbuf.append("     '0', ");//删除标志（0-正常 1-删除）
			strbuf.append("     '"+beanUser.getYHXX_YHID()+"', ");//创建人
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbuf.append("     '"+beanUser.getYHXX_YHID()+"', ");//更新人
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
			strbuf.append(" ) ");
			count = db.executeSQL(strbuf);
			/* 向GZXX表插入数据End */
			if (count > 0) {
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
	 * @FunctionName: cancelAttention
	 * @Description: 取消关注教师数据
	 * @param beanUser
	 * @param teaId
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月20日 下午2:29:05
	 */
	public boolean cancelAttention(Pojo_YHXX beanUser, String teaId) throws Exception {
		boolean result =false;
		int count = 0;

		try {
			String stuId = this.getStuId(beanUser.getYHXX_YHID());//获取学生ID
			db.openConnection();
			db.beginTran();
			/* 删除GZXX表数据Start */
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE ");
			strbuf.append("    GZXX");
			strbuf.append(" WHERE 1=1");
			strbuf.append(" AND GZXX_GZR = '").append(stuId).append("'");
			strbuf.append(" AND GZXX_BGR = '").append(teaId).append("'");
			count = db.executeSQL(strbuf);
			/* 删除GZXX表数据End */
			if (count > 0) {
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
}