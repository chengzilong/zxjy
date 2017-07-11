package com.xsjy.service.service1040000;

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
import com.xsjy.pojo.Custom.pojo_1040000.Pojo1040170;

public class Service1040170 extends BaseService {

	private DBManager db = null;

	public Service1040170() {
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
	 * @date 2014年12月22日 下午3:51:43
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
	 * @author ztz
	 * @date 2014年12月22日 下午3:51:50
	 */
	public int getCourseFeeList_TotalCount(Pojo1040170 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(KCFY_FYID)");
			sql.append("   FROM KCFY A ");
			sql.append("  WHERE 1 = 1  AND  KCFY_SCBZ = '0' ");
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
			if (MyStringUtils.isNotBlank(beanIn.getKCFY_JSID())&&!("000").equals(beanIn.getKCFY_JSID())) {	
				sql.append("  AND A.KCFY_JSID = '").append(beanIn.getKCFY_JSID())
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
	 * @return List<Pojo1040170>
	 * @author ztz
	 * @date 2014年12月22日 下午3:52:01
	 */
	public List<Pojo1040170> getCourseFeeList_PageData(Pojo1040170 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1040170> result = null;
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
			if (MyStringUtils.isNotBlank(beanIn.getKCFY_JSID())&&!("000").equals(beanIn.getKCFY_JSID())) {	
				sql.append("  AND A.KCFY_JSID = '").append(beanIn.getKCFY_JSID())
					.append("'");	
			}
			sql.append(" ORDER BY ");
			sql.append("        A.KCFY_XXID,A.KCFY_LXID,A.KCFY_SDID,A.KCFY_XS");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1040170>> rs = new BeanListHandler<Pojo1040170>(
					Pojo1040170.class);
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
	 * @FunctionName: insertCourseFee
	 * @Description: 新增课程费用
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2014年12月22日 下午3:52:11
	 */
	public int insertCourseFee(Pojo1040170 beanIn) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
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
			if ("000".equals(beanIn.getKCFY_JSID())) {
				strbuf.append("     '', ");
			} else {
				strbuf.append("     '" + beanIn.getKCFY_JSID() + "', ");
			}
			strbuf.append("     '" + beanIn.getKCFY_FY() + "', ");
			strbuf.append("     '" + beanIn.getKCFY_CJR() + "', ");
			strbuf.append("     '" + sysdate + "', ");
			strbuf.append("     '" + beanIn.getKCFY_GXR() + "', ");
			strbuf.append("     '" + sysdate + "' ");
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
	 * @author ztz
	 * @date 2014年12月22日 下午3:52:19
	 */
	public int updateCourseFee(Pojo1040170 beanIn) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
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
			if ("000".equals(beanIn.getKCFY_JSID())) {
				strbuf.append("     KCFY_JSID='',");// 教师ID
			} else {
				strbuf.append("     KCFY_JSID='").append(beanIn.getKCFY_JSID()).append("',");// 教师ID
			}
			strbuf.append("     KCFY_FY='").append(beanIn.getKCFY_FY()).append("',");// 费用
			strbuf.append("     KCFY_GXR='").append(beanIn.getKCFY_GXR()).append("',");// 修改人
			strbuf.append("     KCFY_GXSJ='" + sysdate + "'");// 修改时间
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
	 * @author ztz
	 * @date 2014年12月22日 下午3:52:28
	 */
	public int deleteCourseFee(Pojo1040170 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE ");
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
