package com.xsjy.service.service9010000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_9010000.Pojo9010110;

public class Service9010110 extends BaseService {

	private DBManager db = null;

	public Service9010110() {
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
	public int getUserList_TotalCount(Pojo9010110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         COUNT(A.YHXX_YHID) ");//用户信息个数
			strbuf.append(" FROM ");
			strbuf.append("         YHXX A ");
			strbuf.append(" WHERE 1=1");
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
	 * @Description: 取得查询结果
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<USERS>
	 * @author czl
	 * @date 2017-07-27
	 */
	public List<Pojo9010110> getUserList_PageData(Pojo9010110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo9010110> result = null;

		try {
			db.openConnection();

			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("         A.YHXX_UUID, ");//UUID
			sql.append("         A.YHXX_YHID, ");//用户ID
			sql.append("         A.YHXX_YHMC, ");//用户名称
			sql.append("         A.YHXX_YHMM, ");//用户密码
			sql.append("         A.YHXX_JSID, ");//角色ID
			sql.append("         B.YHJS_JSMC, ");//角色名称
			sql.append("         A.YHXX_CJR, ");//创建人
			sql.append("		 LEFT(A.YHXX_CJSJ,10) AS YHXX_CJSJ, ");//创建时间
			sql.append("         A.YHXX_GXR, ");//更新人
			sql.append("         LEFT(A.YHXX_GXSJ,10) AS YHXX_GXSJ, ");//更新时间
			sql.append("         A.YHXX_SDZT, ");//锁定状态(0--正常 1--冻结)
			sql.append("         A.YHXX_SCBZ, ");//删除标志（0：未删除，1：已删除）
			sql.append("         C.YHZD_ZDID AS YHXX_ZDID, ");//所属站点ID
			sql.append("         D.PXWD_ZDMC AS ZDMC, ");//所属站点名称
			sql.append("         CASE WHEN A.YHXX_SDZT = '0' THEN '正常' ELSE '冻结' END AS SDZT, ");//锁定状态显示名称
			sql.append("         CASE WHEN A.YHXX_SCBZ = '0' THEN '未删除' ELSE '已删除' END AS SCBZ ");//删除标志显示名称
			sql.append(" FROM ");
			sql.append("         YHXX A ");
			sql.append(" INNER JOIN YHJS B ON A.YHXX_JSID = B.YHJS_JSID ");
			sql.append(" LEFT JOIN YHZD C ON A.YHXX_UUID = C.YHZD_YHID ");
			sql.append(" LEFT JOIN PXWD D ON C.YHZD_ZDID = D.PXWD_ZDID ");
			sql.append(" WHERE 1=1");
			sql.append(this.searchSql(beanIn));
			sql.append(" ORDER BY  A.YHXX_JSID,A.YHXX_CJSJ");


			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);

			ResultSetHandler<List<Pojo9010110>> rs = new BeanListHandler<Pojo9010110>(
					Pojo9010110.class);

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
	private String searchSql(Pojo9010110 beanIn) throws Exception {
		StringBuffer sql = new StringBuffer();

		try {
			/* 用户ID */
			if (MyStringUtils.isNotBlank(beanIn.getYHXX_YHID())) {
				sql.append(" AND A.YHXX_YHID = '").append(beanIn.getYHXX_YHID())
						.append("'");
			}
			/* 用户名称 */
			if (MyStringUtils.isNotBlank(beanIn.getYHXX_YHMC())) {
				sql.append(" AND A.YHXX_YHMC LIKE '%")
						.append(beanIn.getYHXX_YHMC()).append("%'");
			}
			/* 锁定状态 */
			if (MyStringUtils.isNotBlank(beanIn.getYHXX_SDZT())) {
				if (!"000".equals(beanIn.getYHXX_SDZT())) {
					sql.append(" AND A.YHXX_SDZT = '").append(beanIn.getYHXX_SDZT()).append("'");
				}
			}
			/* 角色 */
			if (MyStringUtils.isNotBlank(beanIn.getYHXX_JSID())) {
				if (!"000".equals(beanIn.getYHXX_JSID())) {
					sql.append(" AND A.YHXX_JSID = '").append(beanIn.getYHXX_JSID())
							.append("'");
				}
			}
			/* 所属站点 */
			if(MyStringUtils.isNotBlank(beanIn.getYHXX_ZDID())) {
				if(!"000".equals(beanIn.getYHXX_ZDID())) {
					sql.append(" AND EXISTS(SELECT T.YHZD_YHID FROM YHZD T WHERE T.YHZD_YHID = A.YHXX_UUID AND T.YHZD_ZDID ='").append(beanIn.getYHXX_ZDID()).append("')");
				}
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return sql.toString();
	}
	/**
	 * @FunctionName: insertUser
	 * @Description: 插入新用户
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2017-07-27
	 */
	public boolean insertUser(Pojo9010110 beanIn) throws Exception {
		int resultYHXX = 0;
		int resultYHZD = 0;
		boolean result = false;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbufYHXX = new StringBuffer();
			String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			strbufYHXX.append(" INSERT INTO ");
			strbufYHXX.append("         YHXX ");
			strbufYHXX.append(" ( ");
			strbufYHXX.append("         YHXX_UUID, ");// UUid
			strbufYHXX.append("         YHXX_YHID, ");// 用户id
			strbufYHXX.append("         YHXX_YHMC, ");// 用户名
			strbufYHXX.append("         YHXX_YHMM, ");// 密码
			strbufYHXX.append("         YHXX_JSID, ");// 角色id
			strbufYHXX.append("         YHXX_SDZT, ");// 锁定状态(0--正常 1--冻结)
			strbufYHXX.append("         YHXX_CJR, ");// 创建人
			strbufYHXX.append("         YHXX_CJSJ, ");// 创建时间
			strbufYHXX.append("         YHXX_GXR, ");// 修改人
			strbufYHXX.append("         YHXX_GXSJ, ");// 修改时间
			strbufYHXX.append("         YHXX_DLSJ, ");// 登陆时间
			strbufYHXX.append("         YHXX_SCBZ ");// 删除标志（0：未删除，1：已删除）
			strbufYHXX.append(" ) ");
			strbufYHXX.append(" VALUES ");
			strbufYHXX.append(" ( ");
			strbufYHXX.append("         '" + uuid + "', ");// UUID
			strbufYHXX.append("         '" + beanIn.getYHXX_YHID() + "', ");// 用户id
			strbufYHXX.append("         '" + beanIn.getYHXX_YHMC() + "', ");// 用户名
			strbufYHXX.append("         '" + beanIn.getYHXX_YHMM() + "', ");// 密码
			strbufYHXX.append("         '" + beanIn.getYHXX_JSID() + "', ");// 角色id
			strbufYHXX.append("         '" + beanIn.getYHXX_SDZT() + "', ");// 锁定状态
			strbufYHXX.append("         '" + beanIn.getYHXX_CJR() + "', ");// 创建人
			strbufYHXX.append("         NOW(), ");// 创建时间
			strbufYHXX.append("         '" + beanIn.getYHXX_GXR() + "', ");// 修改人
			strbufYHXX.append("         NOW(), ");// 修改时间
			strbufYHXX.append("         NOW(), ");// 登陆时间
			strbufYHXX.append("         '0'  ");// 删除标志（0：未删除，1：已删除）
			strbufYHXX.append(" ) ");
			resultYHXX = db.executeSQL(strbufYHXX);

			if(!"000".equals(beanIn.getYHXX_ZDID())) {
				StringBuffer strbufYHZD = new StringBuffer();
				strbufYHZD.append(" INSERT INTO ");
				strbufYHZD.append("         YHZD ");
				strbufYHZD.append(" ( ");
				strbufYHZD.append("         YHZD_YHID, ");// 用户id
				strbufYHZD.append("         YHZD_ZDID, ");// 站点ID
				strbufYHZD.append("         YHZD_CJR, ");// 创建人
				strbufYHZD.append("         YHZD_CJSJ, ");// 创建时间
				strbufYHZD.append("         YHZD_GXR, ");// 修改人
				strbufYHZD.append("         YHZD_GXSJ, ");// 修改时间
				strbufYHZD.append("         YHZD_DLSJ ");// 登陆时间
				strbufYHZD.append(" ) ");
				strbufYHZD.append(" VALUES ");
				strbufYHZD.append(" ( ");
				strbufYHZD.append("         '" + uuid + "', ");// UUID
				strbufYHZD.append("         '" + beanIn.getYHXX_ZDID() + "', ");// 站点ID
				strbufYHZD.append("         '" + beanIn.getYHXX_CJR() + "', ");// 创建人
				strbufYHZD.append("         NOW(), ");// 创建时间
				strbufYHZD.append("         '" + beanIn.getYHXX_GXR() + "', ");// 修改人
				strbufYHZD.append("         NOW(), ");// 修改时间
				strbufYHZD.append("         NOW() ");// 登陆时间
				strbufYHZD.append(" ) ");
				resultYHZD = db.executeSQL(strbufYHZD);

				if(resultYHXX > 0 && resultYHZD > 0 && resultYHXX == resultYHZD) {
					db.commit();
					result = true;
				} else {
					db.rollback();
				}
			} else {
				if(resultYHXX > 0 && resultYHZD == 0) {
					db.commit();
					result = true;
				} else {
					db.rollback();
				}
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
	 * @FunctionName: updateUser
	 * @Description: 修改用户信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2017-07-27
	 */
	public boolean updateUser(Pojo9010110 beanIn) throws Exception {
		int resultYHXX = 0;
		int resultYHZD = 0;
		boolean result = false;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbufYHXX = new StringBuffer();
			strbufYHXX.append(" UPDATE ");
			strbufYHXX.append("           YHXX ");
			strbufYHXX.append(" SET ");
			strbufYHXX.append("           YHXX_YHMC='").append(beanIn.getYHXX_YHMC()).append("', ");//用户名
			if(!"**********".equals(beanIn.getYHXX_YHMM())){
				strbufYHXX.append("           YHXX_YHMM='").append(beanIn.getYHXX_YHMM()).append("', ");//密码
			}
			strbufYHXX.append("           YHXX_JSID='").append(beanIn.getYHXX_JSID()).append("', ");//用户角色
			strbufYHXX.append("           YHXX_SDZT='").append(beanIn.getYHXX_SDZT()).append("', ");//锁定状态
			strbufYHXX.append("           YHXX_GXR='").append(beanIn.getYHXX_GXR()).append("', ");//修改人
			strbufYHXX.append("           YHXX_GXSJ=NOW() ");//修改时间
			strbufYHXX.append(" WHERE ");
			strbufYHXX.append("           YHXX_UUID='").append(beanIn.getYHXX_UUID()).append("' ");//UUID
			resultYHXX = db.executeSQL(strbufYHXX);

			StringBuffer strbufYHZD = new StringBuffer();
			int count = this.getYhzdCount(beanIn.getYHXX_UUID(), beanIn.getYHXX_ZDID());//获取当前用户关联站点记录个数
			if(!"000".equals(beanIn.getYHXX_ZDID())) {//如果选择所属站点，需要判断用户之前是否关联过所属站点数据
				if (count == 0 ) {//如果此用户之前未关联过站点，则新增用户关联站点数据
					strbufYHZD.append(" INSERT INTO ");
					strbufYHZD.append("         YHZD ");
					strbufYHZD.append(" ( ");
					strbufYHZD.append("         YHZD_YHID, ");// 用户id
					strbufYHZD.append("         YHZD_ZDID, ");// 站点ID
					strbufYHZD.append("         YHZD_CJR, ");// 创建人
					strbufYHZD.append("         YHZD_CJSJ, ");// 创建时间
					strbufYHZD.append("         YHZD_GXR, ");// 修改人
					strbufYHZD.append("         YHZD_GXSJ, ");// 修改时间
					strbufYHZD.append("         YHZD_DLSJ ");// 登陆时间
					strbufYHZD.append(" ) ");
					strbufYHZD.append(" VALUES ");
					strbufYHZD.append(" ( ");
					strbufYHZD.append("         '" + beanIn.getYHXX_UUID() + "', ");// 用户id
					strbufYHZD.append("         '" + beanIn.getYHXX_ZDID() + "', ");// 站点ID
					strbufYHZD.append("         '" + beanIn.getYHXX_CJR() + "', ");// 创建人
					strbufYHZD.append("         NOW(), ");// 创建时间
					strbufYHZD.append("         '" + beanIn.getYHXX_GXR() + "', ");// 修改人
					strbufYHZD.append("         NOW(), ");// 修改时间
					strbufYHZD.append("         NOW() ");// 登陆时间
					strbufYHZD.append(" ) ");
				} else {//如果此用户之前关联过站点，则更新用户关联站点数据
					strbufYHZD.append(" UPDATE ");
					strbufYHZD.append("           YHZD ");
					strbufYHZD.append(" SET ");
					strbufYHZD.append("           YHZD_ZDID='").append(beanIn.getYHXX_ZDID()).append("', ");//用户名
					strbufYHZD.append("           YHZD_GXR='").append(beanIn.getYHXX_GXR()).append("', ");//修改人
					strbufYHZD.append("           YHZD_GXSJ=NOW() ");//修改时间
					strbufYHZD.append(" WHERE ");
					strbufYHZD.append("           YHZD_YHID='").append(beanIn.getYHXX_UUID()).append("' ");//UUID
				}
			} else {//如果取消所属站点，需要删除用户之前关联的所属站点数据
					strbufYHZD.append(" DELETE FROM");
					strbufYHZD.append("          YHZD ");
					strbufYHZD.append(" WHERE ");
					strbufYHZD.append("         YHZD_YHID = '").append(beanIn.getYHXX_UUID()).append("' ");//用户ID
			}
			resultYHZD = db.executeSQL(strbufYHZD);

			if("000".equals(beanIn.getYHXX_ZDID()) && count == 0 && resultYHXX > 0) {
				db.commit();
				result = true;
			} else {
				if(resultYHXX > 0 && resultYHZD > 0 && resultYHXX == resultYHZD) {
					db.commit();
					result = true;
				} else {
					db.rollback();
				}
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
	 * @FunctionName: getYhzdCount
	 * @Description: 根据用户ID和站点ID获取用户关联站点数据个数
	 * @param yhid
	 * @param zdid
	 * @return
	 * @throws Exception
	 * @return StringBuffer
	 * @author czl
	 * @date 2017-07-27
	 */
	private int getYhzdCount(String uuid, String zdid) throws Exception {
		int count = 0;

		try {
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         COUNT(YHZD_YHID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("         YHZD ");
			strbuf.append(" WHERE ");
			strbuf.append("         YHZD_YHID = '").append(uuid).append("' ");//UUID

			count = db.queryForInteger(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return count;
	}
	/**
	 * @FunctionName: deleteUser
	 * @Description: 删除用户
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-27
	 */
	public int deleteUser(Pojo_YHXX beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("           YHXX ");
			strbuf.append(" SET ");
			strbuf.append("           YHXX_SCBZ='1', ");//用户名
			strbuf.append("           YHXX_GXR='").append(beanIn.getYHXX_GXR()).append("', ");//修改人
			strbuf.append("           YHXX_GXSJ=NOW() ");//修改时间
			strbuf.append(" WHERE ");
			strbuf.append("           YHXX_UUID='").append(beanIn.getYHXX_UUID()).append("' ");//UUID

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
	 *
	 * @FunctionName: recoveryUser
	 * @Description: 恢复删除用户
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-27
	 */
	public int recoveryUser(Pojo_YHXX beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("           YHXX ");
			strbuf.append(" SET ");
			strbuf.append("           YHXX_SCBZ='0', ");//用户名
			strbuf.append("           YHXX_GXR='").append(beanIn.getYHXX_GXR()).append("', ");//修改人
			strbuf.append("           YHXX_GXSJ=NOW() ");//修改时间
			strbuf.append(" WHERE ");
			strbuf.append("           YHXX_UUID='").append(beanIn.getYHXX_UUID()).append("' ");//UUID

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