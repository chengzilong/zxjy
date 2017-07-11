package com.xsjy.service.service2010000;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010150;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;

public class Service2010150 extends BaseService {

	private DBManager db = null;

	public Service2010150() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getTeacherAttentionList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-14
	 */
	public int getTeacherAttentionList_TotalCount(Pojo2010150 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT (A.GZXX_GZID)");
			sql.append("   FROM GZXX A,XSXX B");
			sql.append("  WHERE  A.GZXX_SCBZ = '0' AND A.GZXX_GZR = B.XSXX_XSID  AND B.XSXX_XSBM = '").append(beanIn.getGZXX_CJR()).append("'");
			if (MyStringUtils.isNotBlank(beanIn.getJSXM())) {
				sql.append(" AND A.GZXX_BGR IN (SELECT JSXX_JSID FROM JSXX WHERE JSXX_JSXM LIKE '%").append(beanIn.getJSXM())
				.append("%')");
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
	 * @FunctionName: getTeacherAttentionList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo2010150>
	 * @author czl
	 * @date 2015-01-14
	 */
	public List<Pojo2010150> getTeacherAttentionList_PageData(Pojo2010150 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo2010150> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.GZXX_GZID,");// 关注ID
			sql.append("        A.GZXX_BZXX,");// 备注信息
			sql.append("        B.JSXX_JSXM AS JSXM,");// 教师姓名
			sql.append("        CASE WHEN B.JSXX_XB = 1 THEN '男' WHEN B.JSXX_XB = 2 THEN '女' ELSE '' END AS XB, ");//性别（1-男 2-女）
			sql.append("        B.JSXX_LXFS AS LXFS,");// 联系方式
			sql.append("        B.JSXX_XL AS XL,");// 学历
			sql.append("        B.JSXX_BYXX AS BYXX,");// 毕业学校
			sql.append("        CASE WHEN B.JSXX_JNJY='11' THEN '大于10年' WHEN B.JSXX_JNJY='0' THEN '无' ELSE JSXX_JNJY||'年' END AS JNJY,");// 几年经验
			sql.append("        C.JSZG_ZGMC AS JSZG");// 教师资格
			sql.append("   FROM GZXX A,JSXX B,JSZG C");
			sql.append("  WHERE  A.GZXX_SCBZ = '0' AND A.GZXX_BGR = B.JSXX_JSID  AND B.JSXX_JSZG = C.JSZG_ZGID AND A.GZXX_GZR = (");
			sql.append("  SELECT XSXX_XSID FROM XSXX WHERE XSXX_XSBM = '").append(beanIn.getGZXX_CJR()).append("')");
			if (MyStringUtils.isNotBlank(beanIn.getJSXM())) {
				sql.append(" AND A.GZXX_BGR IN (SELECT JSXX_JSID FROM JSXX WHERE JSXX_JSXM LIKE '%").append(beanIn.getJSXM())
				.append("%')");
			}
			System.out.println(sql.toString());
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo2010150>> rs = new BeanListHandler<Pojo2010150>(
					Pojo2010150.class);
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
