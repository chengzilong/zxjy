package com.xsjy.service.service9010000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_YHJS;
import com.xsjy.pojo.Custom.pojo_9010000.Pojo9010120;

public class Service9010120 extends BaseService {
	private DBManager db = null;

	public Service9010120() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getRoleList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-27
	 */
	public int getRoleDataCount(Pojo9010120 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         COUNT(YHJS_JSID) ");//角色信息个数
			strbuf.append(" FROM ");
			strbuf.append("         YHJS ");
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
	 * @FunctionName: getRoleList_PageData
	 * @Description: 取得查询详细信息
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo9010120>
	 * @author czl
	 * @date 2017-07-27
	 */
	public List<Pojo9010120> getRoleDataList(Pojo9010120 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo9010120> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("   YHJS_JSID, ");//角色ID
			strbuf.append("   YHJS_JSMC, ");//角色名称
			strbuf.append("   YHJS_JSLX, ");//角色类型（0-工作人员，1-教师，2-学生）
			strbuf.append("   CASE WHEN YHJS_JSLX = 0 THEN '工作人员' WHEN YHJS_JSLX = 1 THEN '教师' ELSE '学生' END AS JSLX, ");//角色类型
			strbuf.append("   YHJS_JSMS, ");//角色描述
			strbuf.append("   B.YHXX_YHMC AS YHJS_CJR, ");//创建人
			strbuf.append("	  LEFT(YHJS_CJSJ,10) AS YHJS_CJSJ, ");//创建时间
			strbuf.append("   B.YHXX_YHMC AS YHJS_GXR, ");//更新人
			strbuf.append("	  LEFT(YHJS_GXSJ,10) AS YHJS_GXSJ ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("   YHJS A, YHXX B ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND  A.YHJS_CJR = B.YHXX_YHID ");
			strbuf.append(" AND  A.YHJS_GXR = B.YHXX_YHID ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("   A.YHJS_CJSJ ");

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo9010120>> rs = new BeanListHandler<Pojo9010120>(
					Pojo9010120.class);
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
	 * @author czl
	 * @date 2017-07-27
	 */
	private String searchSql(Pojo9010120 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 角色ID */
			if (MyStringUtils.isNotBlank(beanIn.getYHJS_JSID())) {
				strbuf.append(" AND YHJS_JSID = '").append(beanIn.getYHJS_JSID())
						.append("'");
			}
			/* 角色名称 */
			if (MyStringUtils.isNotBlank(beanIn.getYHJS_JSMC())) {
				strbuf.append(" AND YHJS_JSMC LIKE '%").append(beanIn.getYHJS_JSMC())
						.append("%'");
			}
			/* 角色类型 */
			if (MyStringUtils.isNotBlank(beanIn.getYHJS_JSLX())) {
				if (!"000".equals(beanIn.getYHJS_JSLX())) {
					strbuf.append(" AND YHJS_JSLX = '").append(beanIn.getYHJS_JSLX())
							.append("'");
				}
			}
			/* 角色描述 */
			if (MyStringUtils.isNotBlank(beanIn.getYHJS_JSMS())) {
				strbuf.append(" AND YHJS_JSMS LIKE '%").append(beanIn.getYHJS_JSMS())
						.append("%'");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * @FunctionName: insertRole
	 * @Description: 新增角色信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-27
	 */
	public int insertRole(Pojo_YHJS beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("         YHJS ");
			strbuf.append(" ( ");
			strbuf.append("         YHJS_JSID, ");//角色id
			strbuf.append("         YHJS_JSMC, ");//角色名称
			strbuf.append("         YHJS_JSLX, ");//角色类型（0-工作人员，1-教师，2-学生）
			strbuf.append("         YHJS_JSMS, ");//角色描述
			strbuf.append("         YHJS_CJR, ");//创建人
			strbuf.append("         YHJS_CJSJ, ");//创建时间
			strbuf.append("         YHJS_GXR, ");//修改人
			strbuf.append("         YHJS_GXSJ  ");//修改时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("         '"+beanIn.getYHJS_JSID()+"', ");//角色id
			strbuf.append("         '"+beanIn.getYHJS_JSMC()+"', ");//角色名称
			strbuf.append("         '"+beanIn.getYHJS_JSLX()+"', ");//角色类型（0-工作人员，1-教师，2-学生）
			strbuf.append("         '"+beanIn.getYHJS_JSMS()+"', ");//角色描述
			strbuf.append("         '"+beanIn.getYHJS_CJR()+"', ");//创建人
			strbuf.append("         NOW(), ");//创建时间
			strbuf.append("         '"+beanIn.getYHJS_GXR()+"', ");//修改人
			strbuf.append("         NOW() ");//修改时间
			strbuf.append(" ) ");

			result = db.executeSQL(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: updateRole
	 * @Description: 修改角色信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-27
	 */
	public int updateRole(Pojo_YHJS beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("           YHJS ");
			strbuf.append(" SET ");
			strbuf.append("           YHJS_JSMC='").append(beanIn.getYHJS_JSMC()).append("', ");//角色名称
			strbuf.append("           YHJS_JSLX='").append(beanIn.getYHJS_JSLX()).append("', ");//角色类型（0-工作人员，1-教师，2-学生）
			strbuf.append("           YHJS_JSMS='").append(beanIn.getYHJS_JSMS()).append("', ");//角色描述
			strbuf.append("           YHJS_GXR='").append(beanIn.getYHJS_GXR()).append("', ");//修改人
			strbuf.append("           YHJS_GXSJ= NOW() ");//修改时间
			strbuf.append(" WHERE ");
			strbuf.append("           YHJS_JSID='").append(beanIn.getYHJS_JSID()).append("' ");//角色id

			result = db.executeSQL(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: deleteRole
	 * @Description: 删除角色信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-27
	 */
	public int deleteRole(Pojo_YHJS beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE FROM ");
			strbuf.append("         YHJS ");
			strbuf.append(" WHERE ");
			strbuf.append("         YHJS_JSID='").append(beanIn.getYHJS_JSID()).append("' ");//角色id

			result = db.executeSQL(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
}