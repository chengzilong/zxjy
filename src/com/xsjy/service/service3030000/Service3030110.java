package com.xsjy.service.service3030000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_3030000.Pojo3030110;

public class Service3030110 extends BaseService {

	private DBManager db = null;

	public Service3030110() {
		db = new DBManager();
	}
	/**
	 *
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年1月5日 上午10:53:09
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
		String date = dateFormat.format( now );
		return date;
	}
	/**
	 *
	 * @FunctionName: getCourseFeeList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-08-03
	 */
	public int getCourseFeeList_TotalCount(Pojo3030110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(KCFY_FYID)");
			sql.append("   FROM KCFY A ");
			sql.append("   LEFT JOIN JSXX D ON A.KCFY_JSID = D.JSXX_JSID");
			sql.append("  WHERE 1 = 1  AND  KCFY_SCBZ = '0' ");
			sql.append(" AND D.JSXX_JSBM = '").append(beanIn.getJSBM()).append("'");
			if (MyStringUtils.isNotBlank(beanIn.getKCFY_XXID())&&!("000").equals(beanIn.getKCFY_XXID())) {
				sql.append("  AND A.KCFY_XXID = '").append(beanIn.getKCFY_XXID())
					.append("'");
			}
			if (MyStringUtils.isNotBlank(beanIn.getKCFY_LXID())&&!("000").equals(beanIn.getKCFY_LXID())) {
				sql.append("  AND A.KCFY_LXID = '").append(beanIn.getKCFY_LXID())
					.append("'");
			}
			if (MyStringUtils.isNotBlank(beanIn.getKCFY_SDID())&&!("000").equals(beanIn.getKCFY_SDID())) {
				sql.append("  AND A.KCFY_SDID = '").append(beanIn.getKCFY_SDID())
					.append("'");
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
	 *
	 * @FunctionName: getCourseFeeList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo3030110>
	 * @author czl
	 * @date 2017-08-03
	 */
	public List<Pojo3030110> getCourseFeeList_PageData(Pojo3030110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3030110> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.KCFY_FYID,");//费用ID
			sql.append("        A.KCFY_XXID,");// 课程信息ID
			sql.append("        E.KCXX_KCMC AS KCMC,");// 课程名称
			sql.append("        A.KCFY_XS,");// 学时
			sql.append("        A.KCFY_FY,");// 费用
			sql.append("        A.KCFY_LXID,");// 上课类型ID
			sql.append("        A.KCFY_SDID,");// 上课时段ID
			sql.append("        A.KCFY_JSID,");// 教师ID
			sql.append("        B.KCLX_LXMC AS LXMC,");// 上课类型
			sql.append("        C.SKSD_SDMC AS SDMC,");// 上课时段
			sql.append("        D.JSXX_JSXM AS JSMC");// 教师名称
			sql.append("   FROM KCFY A LEFT JOIN KCLX B ON A.KCFY_LXID = B.KCLX_LXID ");
			sql.append("   LEFT JOIN SKSD C ON A.KCFY_SDID = C.SKSD_SDID");
			sql.append("   LEFT JOIN JSXX D ON A.KCFY_JSID = D.JSXX_JSID, KCXX E");
			sql.append("  WHERE   A.KCFY_SCBZ = '0'");
			sql.append("  AND A.KCFY_XXID = E.KCXX_KCID");
			sql.append(" AND D.JSXX_JSBM = '").append(beanIn.getJSBM()).append("'");
			if (MyStringUtils.isNotBlank(beanIn.getKCFY_XXID())&&!("000").equals(beanIn.getKCFY_XXID())) {
				sql.append("  AND A.KCFY_XXID = '").append(beanIn.getKCFY_XXID())
					.append("'");
			}
			if (MyStringUtils.isNotBlank(beanIn.getKCFY_LXID())&&!("000").equals(beanIn.getKCFY_LXID())) {
				sql.append("  AND A.KCFY_LXID = '").append(beanIn.getKCFY_LXID())
					.append("'");
			}
			if (MyStringUtils.isNotBlank(beanIn.getKCFY_SDID())&&!("000").equals(beanIn.getKCFY_SDID())) {
				sql.append("  AND A.KCFY_SDID = '").append(beanIn.getKCFY_SDID())
					.append("'");
			}
			sql.append(" ORDER BY ");
			sql.append("        A.KCFY_CJSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3030110>> rs = new BeanListHandler<Pojo3030110>(
					Pojo3030110.class);
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
	 * @FunctionName: getTeacherId
	 * @Description: 获取教师ID
	 * @param yhid
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年1月5日 下午5:18:47
	 */
	private String getTeacherId(String yhid) throws Exception {
		String teaId = "";

		try {
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("    B.JSXX_JSID ");//教师ID
			strbuf.append(" FROM ");
			strbuf.append("    YHXX A, JSXX B");
			strbuf.append(" WHERE ");
			strbuf.append("    A.YHXX_YHID = B.JSXX_JSBM");
			strbuf.append(" AND A.YHXX_SCBZ = '0'");
			strbuf.append(" AND B.JSXX_SCBZ = '0'");
			strbuf.append(" AND A.YHXX_YHID = '").append(yhid).append("'");

			teaId = db.queryForString(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return teaId;
	}
	/**
	 *
	 * @FunctionName: insertCourseFee
	 * @Description: 新增课程费用
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-08-03
	 */
	public int insertCourseFee(Pojo3030110 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			String fyid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			strbuf.append(" INSERT INTO ");
			strbuf.append("     KCFY ");
			strbuf.append(" ( ");
			strbuf.append("     KCFY_FYID, ");// 费用ID
			strbuf.append("     KCFY_XXID, ");// 课程信息ID
			strbuf.append("     KCFY_LXID, ");// 课程类型ID
			strbuf.append("     KCFY_SDID, ");// 上课时段ID
			strbuf.append("     KCFY_XS, ");// 学时
			strbuf.append("     KCFY_JSID, ");// 教师ID
			strbuf.append("     KCFY_FY, ");// 费用
			strbuf.append("     KCFY_CJR, ");// 创建人
			strbuf.append("     KCFY_CJSJ, ");// 创建时间
			strbuf.append("     KCFY_GXR, ");// 更新人
			strbuf.append("     KCFY_GXSJ ");// 修改时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '" + fyid + "', ");
			strbuf.append("     '" + beanIn.getKCFY_XXID() + "', ");
			strbuf.append("     '" + beanIn.getKCFY_LXID() + "', ");
			strbuf.append("     '" + beanIn.getKCFY_SDID() + "', ");
			strbuf.append("     '" + beanIn.getKCFY_XS() + "', ");
			strbuf.append("     '" + this.getTeacherId(beanIn.getKCFY_CJR()) + "', ");
			strbuf.append("     '" + beanIn.getKCFY_FY() + "', ");
			strbuf.append("     '" + beanIn.getKCFY_CJR() + "', ");
			strbuf.append("     NOW(), ");
			strbuf.append("     '" + beanIn.getKCFY_GXR() + "', ");
			strbuf.append("     NOW() ");
			strbuf.append(" ) ");
			result = db.executeSQL(strbuf);
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
	 * @FunctionName: updateCourseFee
	 * @Description: 修改课程费用
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-08-03
	 */
	public int updateCourseFee(Pojo3030110 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     KCFY ");
			strbuf.append(" SET ");
			strbuf.append("     KCFY_XXID='").append(beanIn.getKCFY_XXID()).append("',");// 课程信息ID
			strbuf.append("     KCFY_LXID='").append(beanIn.getKCFY_LXID()).append("',");// 课程类型ID
			strbuf.append("     KCFY_SDID='").append(beanIn.getKCFY_SDID()).append("',");// 上课时段ID
			strbuf.append("     KCFY_XS='").append(beanIn.getKCFY_XS()).append("',");// 学时
			strbuf.append("     KCFY_FY='").append(beanIn.getKCFY_FY()).append("',");// 费用
			strbuf.append("     KCFY_GXR='").append(beanIn.getKCFY_GXR()).append("',");// 修改人
			strbuf.append("     KCFY_GXSJ=NOW()");// 修改时间
			strbuf.append(" WHERE 1 = 1 AND KCFY_SCBZ = '0'");
			strbuf.append("  AND   KCFY_FYID='").append(beanIn.getKCFY_FYID()).append("'");// 费用ID
			result = db.executeSQL(strbuf);
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
	 * @FunctionName: deleteCourseFee
	 * @Description: 删除课程费用
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-08-03
	 */
	public int deleteCourseFee(Pojo3030110 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE FROM ");
			strbuf.append("     KCFY ");
			strbuf.append(" WHERE 1 = 1 AND KCFY_FYID='").append(beanIn.getKCFY_FYID())
					.append("'");
			result = db.executeSQL(strbuf);
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
