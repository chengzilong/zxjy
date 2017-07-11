package com.xsjy.service.service3010000;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.xsjy.pojo.Custom.pojo_3010000.Pojo3010130;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;

public class Service3010130 extends BaseService {

	private DBManager db = null;

	public Service3010130() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getTeacherEvaluateList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-01-05
	 */
	public int getTeacherEvaluateList_TotalCount(Pojo3010130 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT (A.PJXX_PJID)");
			sql.append("   FROM PJXX A,YHXX B");
			sql.append("  WHERE  A.PJXX_SCBZ = '0' AND A.PJXX_PJR = B.YHXX_UUID  AND A.PJXX_BPR = '").append(beanIn.getPJXX_CJR()).append("'");
			if (MyStringUtils.isNotBlank(beanIn.getPJXX_JSXM())) {
				sql.append(" AND B.YHXX_YHMC LIKE '%").append(beanIn.getPJXX_JSXM())
				.append("%'");
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
	 * @FunctionName: getTeacherEvaluateList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo3010130>
	 * @author czl
	 * @date 2014-01-05
	 */
	public List<Pojo3010130> getTeacherEvaluateList_PageData(Pojo3010130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3010130> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.PJXX_PJID,");// 评价ID
			sql.append("        A.PJXX_BCID,");// 班次ID
			sql.append("        A.PJXX_PJR,");// 评价人
			sql.append("        B.YHXX_YHMC AS PJR,");// 评价人
			sql.append("        A.PJXX_PJNR,");//评价内容
			sql.append("        A.PJXX_PJJF,");//评价积分
			sql.append("        A.PJXX_PJSJ");//评价时间
			sql.append("   FROM PJXX A,YHXX B");
			sql.append("  WHERE  A.PJXX_SCBZ = '0' AND A.PJXX_PJR = B.YHXX_UUID  AND A.PJXX_BPR = '").append(beanIn.getPJXX_CJR()).append("'");
			if (MyStringUtils.isNotBlank(beanIn.getPJXX_JSXM())) {
				sql.append(" AND B.YHXX_YHMC LIKE '%").append(beanIn.getPJXX_JSXM())
				.append("%'");
			}
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3010130>> rs = new BeanListHandler<Pojo3010130>(
					Pojo3010130.class);
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
