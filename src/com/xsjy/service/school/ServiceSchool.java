package com.xsjy.service.school;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsjy.pojo.Custom.pojo_school.PojoSchool;

public class ServiceSchool extends BaseService {

	private DBManager db = null;

	public ServiceSchool() {
		db = new DBManager();
	}
	/**
	 *
	 * @FunctionName: getDataBeanList
	 * @Description: 获取页面数据列表
	 * @param dataBean
	 * @return
	 * @throws Exception
	 * @return List<PojoSchool>
	 * @author czl
	 * @date 2017-07-14
	 */
	public List<PojoSchool> getDataBeanList() throws Exception {
		List<PojoSchool> dataBeanList = null;
		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.JSXX_JSID AS JSID, ");//教师ID
			strbuf.append("     A.JSXX_SFRZ, ");//是否认证
			strbuf.append("     A.JSXX_BYXX, ");//毕业学校
			strbuf.append("     CONCAT(A.JSXX_BYXX,' - ',A.JSXX_JSXM,'老师') AS XXJS ");//学校教师
			strbuf.append("   FROM  JSXX A");
			strbuf.append(" WHERE ");
			strbuf.append(" A.JSXX_SCBZ = 0 ");
			strbuf.append(" AND A.JSXX_BYXX IS NOT NULL LIMIT 10 ");
			ResultSetHandler<List<PojoSchool>> rsh = new BeanListHandler<PojoSchool>(
					PojoSchool.class);
			dataBeanList = db.queryForBeanListHandler(strbuf, rsh);
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
	 * @FunctionName: getDataBeanInfo
	 * @Description: 获取页面数据列表
	 * @param dataBean
	 * @return
	 * @throws Exception
	 * @return List<PojoSchool>
	 * @author czl
	 * @date 2017-07-
	 */
	public List<PojoSchool> getDataBeanInfo() throws Exception {
		List<PojoSchool> dataBeanList = null;
		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.JSXX_JSID AS JSID, ");//教师ID
			strbuf.append("     A.JSXX_JSXM||'老师' AS JSXX_JSXM, ");//教师姓名
			strbuf.append("     A.JSXX_NJ||'级' AS JSXX_NJ, ");//年级
			strbuf.append("     A.JSXX_BYXX ");//毕业学校
			strbuf.append("   FROM  JSXX A");
			strbuf.append(" WHERE ");
			strbuf.append(" A.JSXX_SCBZ = 0 ");
			strbuf.append(" AND A.JSXX_BYXX IS NOT NULL AND A.JSXX_NJ IS NOT NULL LIMIT 5 ");
			ResultSetHandler<List<PojoSchool>> rsh = new BeanListHandler<PojoSchool>(
					PojoSchool.class);
			dataBeanList = db.queryForBeanListHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return dataBeanList;
	}
}