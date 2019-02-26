package com.xsjy.service.service3020000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_XSXX;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020110;

public class Service3020110 extends BaseService {
	private DBManager db = null;

	public Service3020110() {
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
	 * @author czl
	 * @date 2017-08-02
	 */
	public int getDataCount(Pojo3020110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.XSXX_XSID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     XSXX A LEFT JOIN JDJY B ON A.XSXX_JD = B.JDJY_JDID ");
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
	 * @return List<Pojo3020110>
	 * @author czl
	 * @date 2017-08-02
	 */
	public List<Pojo3020110> getDataList(Pojo3020110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3020110> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.XSXX_XSID, ");//学生ID
			strbuf.append("     A.XSXX_XSBM, ");//学生编码
			strbuf.append("     A.XSXX_XSXM, ");//学生姓名
			strbuf.append("     A.XSXX_XB, ");//性别（1-男 2-女）
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
			strbuf.append("     XSXX A LEFT JOIN JDJY B ON A.XSXX_JD = B.JDJY_JDID ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.XSXX_XSBM ");

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3020110>> rs = new BeanListHandler<Pojo3020110>(
					Pojo3020110.class);
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
	 * @date 2017-08-02
	 */
	private String searchSql(Pojo3020110 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 公共部分 */
			strbuf.append(" AND A.XSXX_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND A.XSXX_CJR = '").append(beanIn.getJSID()).append("'");
			/* 学生编码 */
			if (MyStringUtils.isNotBlank(beanIn.getXSXX_XSBM())) {
				strbuf.append(" AND A.XSXX_XSBM = '").append(beanIn.getXSXX_XSBM())
						.append("'");
			}
			/* 学生姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getXSXX_XSXM())) {
				strbuf.append(" AND A.XSXX_XSXM LIKE '%").append(beanIn.getXSXX_XSXM())
						.append("%'");
			}
			/* 阶段 */
			if (MyStringUtils.isNotBlank(beanIn.getXSXX_JD())) {
				if (!"000".equals(beanIn.getXSXX_JD())) {
					strbuf.append(" AND A.XSXX_JD = '").append(beanIn.getXSXX_JD())
							.append("'");
				}
			}
			/* 年级 */
			if (MyStringUtils.isNotBlank(beanIn.getXSXX_NJ())) {
				if (!"000".equals(beanIn.getXSXX_NJ())) {
					strbuf.append(" AND A.XSXX_NJ = '").append(beanIn.getXSXX_NJ())
							.append("'");
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
	 * @FunctionName: insertData
	 * @Description: 新增数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2017-08-02
	 */
	public boolean insertData(Pojo_XSXX beanIn) throws Exception {
		boolean result = false;
		int resultXSXX = 0;
		int resultYHXX = 0;
		String strId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入站点ID

		try {
			db.openConnection();
			db.beginTran();
			/* 向XSXX表插入数据Start */
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     XSXX ");
			strbuf.append(" ( ");
			strbuf.append("     XSXX_XSID, ");//学生ID
			strbuf.append("     XSXX_XSBM, ");//学生编码
			strbuf.append("     XSXX_XSXM, ");//学生姓名
			strbuf.append("     XSXX_XB, ");//性别（1-男 2-女）
			strbuf.append("     XSXX_CSRQ, ");//出生日期
			strbuf.append("     XSXX_LXFS, ");//联系方式
			strbuf.append("     XSXX_ZZ, ");//住址
			strbuf.append("     XSXX_JD, ");//阶段
			strbuf.append("     XSXX_NJ, ");//年级
			strbuf.append("     XSXX_CRJJ, ");//个人简介
			strbuf.append("     XSXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     XSXX_CJR, ");//创建人
			strbuf.append("     XSXX_CJSJ, ");//创建时间
			strbuf.append("     XSXX_GXR, ");//更新人
			strbuf.append("     XSXX_GXSJ  ");//更新时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+strId+"', ");//学生ID
			strbuf.append("     '"+beanIn.getXSXX_XSBM()+"', ");//学生编码
			strbuf.append("     '"+beanIn.getXSXX_XSXM()+"', ");//学生姓名
			strbuf.append("     '"+MyStringUtils.safeToString(beanIn.getXSXX_XB())+"', ");//性别（1-男 2-女）
			strbuf.append("     '"+beanIn.getXSXX_CSRQ()+"', ");//出生日期
			strbuf.append("     '"+beanIn.getXSXX_LXFS()+"', ");//联系方式
			strbuf.append("     '"+beanIn.getXSXX_ZZ()+"', ");//住址
			strbuf.append("     '"+MyStringUtils.safeToString(beanIn.getXSXX_JD())+"', ");//阶段
			strbuf.append("     '"+MyStringUtils.safeToString(beanIn.getXSXX_NJ())+"', ");//年级
			strbuf.append("     '"+beanIn.getXSXX_CRJJ()+"', ");//个人简介
			strbuf.append("     '0', ");//删除标志（0-正常 1-删除）
			strbuf.append("     '"+beanIn.getXSXX_CJR()+"', ");//创建人
			strbuf.append("     NOW(), ");//创建时间
			strbuf.append("     '"+beanIn.getXSXX_GXR()+"', ");//更新人
			strbuf.append("     NOW() ");//更新时间
			strbuf.append(" ) ");
			resultXSXX = db.executeSQL(strbuf);
			/* 向XSXX表插入数据End */
			/* 向YHXX表插入数据Start */
			StringBuffer strbufYHXX = new StringBuffer();
			strbufYHXX.append(" INSERT INTO ");
			strbufYHXX.append("     YHXX ");
			strbufYHXX.append(" ( ");
			strbufYHXX.append("     YHXX_UUID, ");// UUID
			strbufYHXX.append("     YHXX_YHID, ");// 用户id
			strbufYHXX.append("     YHXX_YHMC, ");// 用户名
			strbufYHXX.append("     YHXX_YHMM, ");// 密码
			strbufYHXX.append("     YHXX_JSID, ");// 角色id
			strbufYHXX.append("     YHXX_SDZT, ");// 锁定状态(0--正常 1--冻结)
			strbufYHXX.append("     YHXX_CJR, ");// 创建人
			strbufYHXX.append("     YHXX_CJSJ, ");// 创建时间
			strbufYHXX.append("     YHXX_GXR, ");// 修改人
			strbufYHXX.append("     YHXX_GXSJ, ");// 修改时间
			strbufYHXX.append("     YHXX_DLSJ, ");// 登陆时间
			strbufYHXX.append("     YHXX_SCBZ ");// 删除标志（0：未删除，1：已删除）
			strbufYHXX.append(" ) ");
			strbufYHXX.append(" VALUES ");
			strbufYHXX.append(" ( ");
			strbufYHXX.append("     '"+strId+"', ");//UUID
			strbufYHXX.append("     '" + beanIn.getXSXX_XSBM() + "', ");// 用户id
			strbufYHXX.append("     '" + beanIn.getXSXX_XSXM() + "', ");// 用户名
			strbufYHXX.append("     '000000', ");// 密码
			strbufYHXX.append("     '106', ");// 角色id
			strbufYHXX.append("     '0', ");// 锁定状态
			strbufYHXX.append("     '" + beanIn.getXSXX_CJR() + "', ");// 创建人
			strbufYHXX.append("     NOW(), ");// 创建时间
			strbufYHXX.append("     '" + beanIn.getXSXX_GXR() + "', ");// 修改人
			strbufYHXX.append("     NOW(), ");// 修改时间
			strbufYHXX.append("     NOW(), ");// 登陆时间
			strbufYHXX.append("     '0'  ");// 删除标志（0：未删除，1：已删除）
			strbufYHXX.append(" ) ");
			resultYHXX = db.executeSQL(strbufYHXX);
			/* 向YHXX表插入数据End */
			if(resultXSXX > 0 && resultYHXX > 0 && resultXSXX == resultYHXX) {
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
	 * @FunctionName: updateData
	 * @Description: 更新数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2017-08-02
	 */
	public boolean updateData(Pojo3020110 beanIn) throws Exception {
		boolean result = false;
		int resultXSXX = 0;
		int resultYHXX = 0;

		try {
			db.openConnection();
			db.beginTran();
			/* 更新XSXX表数据Start */
			StringBuffer strbufXSXX = new StringBuffer();
			strbufXSXX.append(" UPDATE ");
			strbufXSXX.append("     XSXX ");
			strbufXSXX.append(" SET ");
			strbufXSXX.append("     XSXX_XSBM='").append(beanIn.getXSXX_XSBM()).append("',");//学生编码
			strbufXSXX.append("     XSXX_XSXM='").append(beanIn.getXSXX_XSXM()).append("',");//学生姓名
			strbufXSXX.append("     XSXX_XB='").append(MyStringUtils.safeToString(beanIn.getXSXX_XB())).append("',");//性别（1-男 2-女）
			strbufXSXX.append("     XSXX_CSRQ='").append(beanIn.getXSXX_CSRQ()).append("',");//出生日期
			strbufXSXX.append("     XSXX_LXFS='").append(beanIn.getXSXX_LXFS()).append("',");//联系方式
			strbufXSXX.append("     XSXX_ZZ='").append(beanIn.getXSXX_ZZ()).append("',");//住址
			strbufXSXX.append("     XSXX_JD='").append(MyStringUtils.safeToString(beanIn.getXSXX_JD())).append("',");//阶段
			strbufXSXX.append("     XSXX_NJ='").append(MyStringUtils.safeToString(beanIn.getXSXX_NJ())).append("',");//年级
			strbufXSXX.append("     XSXX_CRJJ='").append(beanIn.getXSXX_CRJJ()).append("',");//个人简介
			strbufXSXX.append("     XSXX_GXR='").append(beanIn.getXSXX_GXR()).append("',");//更新人
			strbufXSXX.append("     XSXX_GXSJ=NOW()");//更新时间
			strbufXSXX.append(" WHERE ");
			strbufXSXX.append("     XSXX_XSID='").append(beanIn.getXSXX_XSID()).append("'");//学生ID
			resultXSXX = db.executeSQL(strbufXSXX);
			/* 更新XSXX表数据End */
			/* 更新YHXX表数据Start */
			StringBuffer strbufYHXX = new StringBuffer();
			strbufYHXX.append(" UPDATE ");
			strbufYHXX.append("     YHXX ");
			strbufYHXX.append(" SET ");
			strbufYHXX.append("     YHXX_YHID='").append(beanIn.getXSXX_XSBM()).append("',");//用户ID
			strbufYHXX.append("     YHXX_YHMC='").append(beanIn.getXSXX_XSXM()).append("',");//用户名称
			strbufYHXX.append("     YHXX_GXR='").append(beanIn.getXSXX_GXR()).append("',");//更新人
			strbufYHXX.append("     YHXX_GXSJ=NOW()");//更新时间
			strbufYHXX.append(" WHERE ");
			strbufYHXX.append("     YHXX_UUID='").append(beanIn.getXSXX_XSID()).append("'");//用户ID
			resultYHXX = db.executeSQL(strbufYHXX);
			/* 更新YHXX表数据End */
			if(resultXSXX > 0 && resultYHXX > 0 && resultXSXX == resultYHXX) {
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
	 * @FunctionName: deleteData
	 * @Description: 删除数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2017-08-02
	 */
	public boolean deleteData(Pojo_XSXX beanIn) throws Exception {
		boolean result = false;
		int resultXSXX = 0;
		int resultYHXX = 0;

		try {
			db.openConnection();
			db.beginTran();
			/* 删除XSXX表数据Start */
			StringBuffer strbufXSXX = new StringBuffer();
			strbufXSXX.append(" UPDATE ");
			strbufXSXX.append("     XSXX ");
			strbufXSXX.append(" SET ");
			strbufXSXX.append("     XSXX_SCBZ='1',");//删除标志（0-正常 1-删除）
			strbufXSXX.append("     XSXX_GXR='").append(beanIn.getXSXX_GXR()).append("',");//更新人
			strbufXSXX.append("     XSXX_GXSJ=NOW()");//更新时间
			strbufXSXX.append(" WHERE ");
			strbufXSXX.append("     XSXX_XSID='").append(beanIn.getXSXX_XSID()).append("'");//科目ID
			resultXSXX = db.executeSQL(strbufXSXX);
			/* 删除XSXX表数据End */
			/* 删除YHXX表数据Start */
			StringBuffer strbufYHXX = new StringBuffer();
			strbufYHXX.append(" UPDATE ");
			strbufYHXX.append("     YHXX ");
			strbufYHXX.append(" SET ");
			strbufYHXX.append("     YHXX_SCBZ='1',");//删除标志（0：未删除，1：已删除）
			strbufYHXX.append("     YHXX_GXR='").append(beanIn.getXSXX_GXR()).append("',");//更新人
			strbufYHXX.append("     YHXX_GXSJ=NOW()");//更新时间
			strbufYHXX.append(" WHERE ");
			strbufYHXX.append("     YHXX_UUID='").append(beanIn.getXSXX_XSID()).append("'");//科目ID
			resultYHXX = db.executeSQL(strbufYHXX);
			/* 删除YHXX表数据End */
			if(resultXSXX > 0 && resultYHXX > 0 && resultXSXX == resultYHXX) {
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