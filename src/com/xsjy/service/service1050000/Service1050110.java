package com.xsjy.service.service1050000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_LSBM;
import com.xsjy.pojo.Custom.pojo_1050000.Pojo1050110;

public class Service1050110 extends BaseService {
	private DBManager db = null;

	public Service1050110() {
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
	 * @author ztz
	 * @date 2014年12月12日 上午9:27:29
	 */
	public int getDataCount(Pojo1050110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.LSBM_LSBMID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     LSBM A, KCXX B, YHXX C ");
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
	 * @return List<Pojo1050110>
	 * @author ztz
	 * @date 2014年12月12日 上午9:27:41
	 */
	public List<Pojo1050110> getDataList(Pojo1050110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1050110> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.LSBM_LSBMID, ");//临时报名ID
			strbuf.append("     A.LSBM_XSXM, ");//学生姓名
			strbuf.append("     A.LSBM_XSDH, ");//学生电话
			strbuf.append("     A.LSBM_KCID, ");//课程ID
			strbuf.append("     B.KCXX_KCMC AS KCMC, ");//课程名称
			strbuf.append("     A.LSBM_SFHF, ");//是否回访
			strbuf.append("     CASE WHEN A.LSBM_SFHF = 0 THEN '未回访' ELSE '已回访' END AS SFHF, ");//是否回访
			strbuf.append("     A.LSBM_BZXX, ");//备注信息
			strbuf.append("     A.LSBM_SCBZ, ");//删除标志
			strbuf.append("     A.LSBM_CJR, ");//创建人
			strbuf.append("     A.LSBM_CJSJ, ");//创建时间
			strbuf.append("     C.YHXX_YHMC AS LSBM_GXR, ");//更新人（登记人）
			strbuf.append("     TO_CHAR(TO_DATE(A.LSBM_CJSJ, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS') AS LSBM_GXSJ  ");//更新时间（登记时间）
			strbuf.append(" FROM ");
			strbuf.append("     LSBM A, KCXX B, YHXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.LSBM_CJSJ DESC ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1050110>> rs = new BeanListHandler<Pojo1050110>(
					Pojo1050110.class);
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
	 * @author ztz
	 * @date 2014年12月12日 上午9:35:30
	 */
	private String searchSql(Pojo1050110 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.LSBM_KCID = B.KCXX_KCID(+)");
			strbuf.append(" AND A.LSBM_GXR  = C.YHXX_YHID(+)");
			strbuf.append(" AND A.LSBM_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND '0' = B.KCXX_SCBZ(+)");//删除标志（0-正常 1-删除）
			/* 学生姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getLSBM_XSXM())) {
				strbuf.append(" AND A.LSBM_XSXM LIKE '%").append(beanIn.getLSBM_XSXM())
						.append("%'");
			}
			/* 学生电话 */
			if (MyStringUtils.isNotBlank(beanIn.getLSBM_XSDH())) {
				strbuf.append(" AND A.LSBM_XSDH = '").append(beanIn.getLSBM_XSDH()).append("'");
			}
			/* 所选课程 */
			if (MyStringUtils.isNotBlank(beanIn.getLSBM_KCID())) {
				if (!"000".equals(beanIn.getLSBM_KCID())) {
					strbuf.append(" AND A.LSBM_KCID = '").append(beanIn.getLSBM_KCID()).append("'");
				}
			}
			/* 是否回访 */
			if (MyStringUtils.isNotBlank(beanIn.getLSBM_SFHF())) {
				if (!"000".equals(beanIn.getLSBM_SFHF())) {
					strbuf.append(" AND A.LSBM_SFHF = '").append(beanIn.getLSBM_SFHF()).append("'");
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
	 * @author ztz
	 * @date 2014年12月12日 上午10:24:30
	 */
	public boolean insertData(Pojo_LSBM beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		String strId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入站点ID

		try {
			db.openConnection();
			db.beginTran();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     LSBM ");
			strbuf.append(" ( ");
			strbuf.append("     LSBM_LSBMID, ");//临时报名ID
			strbuf.append("     LSBM_XSXM, ");//学生姓名
			strbuf.append("     LSBM_XSDH, ");//学生电话
			strbuf.append("     LSBM_KCID, ");//课程ID
			strbuf.append("     LSBM_SFHF, ");//是否回访
			strbuf.append("     LSBM_BZXX, ");//备注信息
			strbuf.append("     LSBM_SCBZ, ");//删除标志
			strbuf.append("     LSBM_CJR, ");//创建人
			strbuf.append("     LSBM_CJSJ, ");//创建时间
			strbuf.append("     LSBM_GXR, ");//更新人
			strbuf.append("     LSBM_GXSJ  ");//更新时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+strId+"', ");//临时报名ID
			strbuf.append("     '"+beanIn.getLSBM_XSXM()+"', ");//学生姓名
			strbuf.append("     '"+beanIn.getLSBM_XSDH()+"', ");//学生电话
			strbuf.append("     '"+beanIn.getLSBM_KCID()+"', ");//课程ID
			strbuf.append("     '"+beanIn.getLSBM_SFHF()+"', ");//是否回访
			strbuf.append("     '"+beanIn.getLSBM_BZXX()+"', ");//备注信息
			strbuf.append("     '0', ");//删除标志（0-正常 1-删除）
			strbuf.append("     '"+beanIn.getLSBM_CJR()+"', ");//创建人
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbuf.append("     '"+beanIn.getLSBM_GXR()+"', ");//更新人
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS') ");//更新时间
			strbuf.append(" ) ");
			count = db.executeSQL(strbuf);
			
			if (count > 0) {
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
	 * @author ztz
	 * @date 2014年12月12日 上午10:29:28
	 */
	public boolean updateData(Pojo_LSBM beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     LSBM ");
			strbuf.append(" SET ");
			strbuf.append("     LSBM_XSXM='").append(beanIn.getLSBM_XSXM()).append("',");//学生姓名
			strbuf.append("     LSBM_XSDH='").append(beanIn.getLSBM_XSDH()).append("',");//学生电话
			strbuf.append("     LSBM_KCID='").append(beanIn.getLSBM_KCID()).append("',");//课程ID
			strbuf.append("     LSBM_SFHF='").append(beanIn.getLSBM_SFHF()).append("',");//是否回访
			strbuf.append("     LSBM_BZXX='").append(beanIn.getLSBM_BZXX()).append("',");//备注信息
			strbuf.append("     LSBM_GXR='").append(beanIn.getLSBM_GXR()).append("',");//更新人
			strbuf.append("     LSBM_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     LSBM_LSBMID='").append(beanIn.getLSBM_LSBMID()).append("'");//临时报名ID
			count = db.executeSQL(strbuf);
			
			if (count > 0) {
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
	 * @author ztz
	 * @date 2014年12月12日 上午10:29:36
	 */
	public boolean deleteData(Pojo_LSBM beanIn) throws Exception {
		boolean result = false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     LSBM ");
			strbuf.append(" SET ");
			strbuf.append("     LSBM_SCBZ='1',");//删除标志
			strbuf.append("     LSBM_GXR='").append(beanIn.getLSBM_GXR()).append("',");//更新人
			strbuf.append("     LSBM_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     LSBM_LSBMID='").append(beanIn.getLSBM_LSBMID()).append("'");//临时报名ID
			count = db.executeSQL(strbuf);
			
			if (count > 0) {
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