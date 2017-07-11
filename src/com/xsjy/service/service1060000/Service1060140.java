package com.xsjy.service.service1060000;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.xsjy.pojo.Custom.pojo_1060000.Pojo1060140;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;

public class Service1060140 extends BaseService {

	private DBManager db = null;

	public Service1060140() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getStudentEvaluateList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-16
	 */
	public int getStudentEvaluateList_TotalCount(Pojo1060140 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT (*) FROM (");
			sql.append(" SELECT A.PJXX_PJID");
			sql.append("   FROM PJXX A,YHXX B,YHXX C");
			sql.append("  WHERE  A.PJXX_SCBZ = '0' AND A.PJXX_PJR = B.YHXX_UUID AND A.PJXX_BPR = C.YHXX_UUID AND B.YHXX_JSID='106'");
			if (MyStringUtils.isNotBlank(beanIn.getPJXX_XSXM())) {
				sql.append(" AND B.YHXX_YHMC LIKE '%").append(beanIn.getPJXX_XSXM())
				.append("%'");
			}
			sql.append(" UNION ");
			sql.append(" SELECT A.PJXX_PJID");
			sql.append("   FROM PJXX A,YHXX B,YHXX C");
			sql.append("  WHERE  A.PJXX_SCBZ = '0' AND A.PJXX_PJR = B.YHXX_UUID AND A.PJXX_BPR = C.YHXX_UUID AND C.YHXX_JSID='106'");
			if (MyStringUtils.isNotBlank(beanIn.getPJXX_XSXM())) {
				sql.append(" AND C.YHXX_YHMC LIKE '%").append(beanIn.getPJXX_XSXM())
				.append("%'");
			}
			sql.append(")");
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
	 * @FunctionName: getStudentEvaluateList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1060140>
	 * @author czl
	 * @date 2014-12-16
	 */
	public List<Pojo1060140> getStudentEvaluateList_PageData(Pojo1060140 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1060140> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.PJXX_PJID,");// 评价ID
			sql.append("        A.PJXX_BCID,");// 班次ID
			sql.append("        A.PJXX_PJR,");// 评价人
			sql.append("        B.YHXX_YHMC AS PJR,");// 评价人
			sql.append("        A.PJXX_BPR,");// 被评人
			sql.append("        C.YHXX_YHMC AS BPR,");//被评人
			sql.append("        A.PJXX_PJNR,");//评价内容
			sql.append("        A.PJXX_PJJF,");//评价积分
			sql.append("        A.PJXX_PJSJ");//评价时间
			sql.append("   FROM PJXX A,YHXX B,YHXX C");
			sql.append("  WHERE  A.PJXX_SCBZ = '0' AND A.PJXX_PJR = B.YHXX_UUID AND A.PJXX_BPR = C.YHXX_UUID AND B.YHXX_JSID='106'");
			if (MyStringUtils.isNotBlank(beanIn.getPJXX_XSXM())) {
				sql.append(" AND B.YHXX_YHMC LIKE '%").append(beanIn.getPJXX_XSXM())
				.append("%'");
			}
			sql.append(" UNION ");
			sql.append(" SELECT A.PJXX_PJID,");// 评价ID
			sql.append("        A.PJXX_BCID,");// 班次ID
			sql.append("        A.PJXX_PJR,");// 评价人
			sql.append("        B.YHXX_YHMC AS PJR,");// 评价人
			sql.append("        A.PJXX_BPR,");// 被评人
			sql.append("        C.YHXX_YHMC AS BPR,");//被评人
			sql.append("        A.PJXX_PJNR,");//评价内容
			sql.append("        A.PJXX_PJJF,");//评价积分
			sql.append("        A.PJXX_PJSJ");//评价时间
			sql.append("   FROM PJXX A,YHXX B,YHXX C");
			sql.append("  WHERE  A.PJXX_SCBZ = '0' AND A.PJXX_PJR = B.YHXX_UUID AND A.PJXX_BPR = C.YHXX_UUID AND C.YHXX_JSID='106'");
			if (MyStringUtils.isNotBlank(beanIn.getPJXX_XSXM())) {
				sql.append(" AND C.YHXX_YHMC LIKE '%").append(beanIn.getPJXX_XSXM())
				.append("%'");
			}
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1060140>> rs = new BeanListHandler<Pojo1060140>(
					Pojo1060140.class);
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
