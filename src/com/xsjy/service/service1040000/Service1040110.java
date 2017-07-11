package com.xsjy.service.service1040000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_1040000.Pojo1040110;

public class Service1040110 extends BaseService {
	private DBManager db = null;

	public Service1040110() {
		db = new DBManager();
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
	 * @return List<Pojo1040110>
	 * @author ztz
	 * @date 2014年12月13日 下午7:11:40
	 */
	public List<Pojo1040110> getDataList(Pojo1040110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1040110> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.BMXX_XXID AS KCID, ");//课程ID
			strbuf.append("     CASE WHEN B.KCXX_KCMC IS NOT NULL THEN B.KCXX_KCMC ELSE '总计' END AS KCMC, ");//课程名称
			strbuf.append("     COUNT(DISTINCT(BMXX_BMID)) AS BMRS  ");//报名人数
			strbuf.append(" FROM ");
			strbuf.append("     BMXX A, KCXX B ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.BMXX_XXID = B.KCXX_KCID");
			strbuf.append(" AND A.BMXX_SCBZ = '0'");
			strbuf.append(" AND B.KCXX_SCBZ = '0'");
			strbuf.append(" AND A.BMXX_SSBC IS NULL");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" GROUP BY GROUPING SETS ");
			strbuf.append("     ((A.BMXX_XXID, B.KCXX_KCMC, B.KCXX_KCJD), ())");
			strbuf.append(" ORDER BY ");
			strbuf.append("     B.KCXX_KCJD");
			
			ResultSetHandler<List<Pojo1040110>> rs = new BeanListHandler<Pojo1040110>(
					Pojo1040110.class);
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
	 * @FunctionName: searchSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2014年12月13日 下午7:08:54
	 */
	private String searchSql(Pojo1040110 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 课程名称 */
			if (MyStringUtils.isNotBlank(beanIn.getKCID())) {
				if (!"000".equals(beanIn.getKCID())) {
					strbuf.append(" AND A.BMXX_XXID = '").append(beanIn.getKCID()).append("'");
				}
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
}