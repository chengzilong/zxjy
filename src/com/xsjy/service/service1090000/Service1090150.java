package com.xsjy.service.service1090000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_KMXX;
import com.xsjy.pojo.Custom.pojo_1090000.Pojo1090150;

public class Service1090150 extends BaseService {
	private DBManager db = null;

	public Service1090150() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataExist
	 * @Description: 验证是否重复
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-28
	 */
	public int getDataExist(Pojo1090150 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.KMXX_KMID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     KMXX A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.KMXX_SCBZ = '0'");//删除标志（0-正常 1-删除）
			if (MyStringUtils.isNotBlank(beanIn.getKMXX_KMMC())) {
				strbuf.append(" AND A.KMXX_KMMC = '").append(beanIn.getKMXX_KMMC())
						.append("'");
			}
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
	 * @FunctionName: getDataCount
	 * @Description: 统计列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2014年12月8日 上午11:20:42
	 */
	public int getDataCount(Pojo1090150 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.KMXX_KMID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     KMXX A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.KMXX_SCBZ = '0'");//删除标志（0-正常 1-删除）
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
	 * @return List<Pojo1090150>
	 * @author ztz
	 * @date 2014年12月8日 上午11:21:45
	 */
	public List<Pojo1090150> getDataList(Pojo1090150 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1090150> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.KMXX_KMID, ");//科目ID
			strbuf.append("     A.KMXX_KMMC, ");//科目名称
			strbuf.append("     A.KMXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     CASE WHEN A.KMXX_SCBZ = '0' THEN '可用' ELSE '不可用' END AS SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     A.KMXX_CJR, ");//创建人
			strbuf.append("     A.KMXX_CJSJ, ");//创建时间
			strbuf.append("     A.KMXX_GXR, ");//更新人
			strbuf.append("     A.KMXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     KMXX A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.KMXX_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.KMXX_CJSJ DESC ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1090150>> rs = new BeanListHandler<Pojo1090150>(
					Pojo1090150.class);
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
	 * @date 2014年12月8日 上午11:26:37
	 */
	private String searchSql(Pojo1090150 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 科目名称 */
			if (MyStringUtils.isNotBlank(beanIn.getKMXX_KMMC())) {
				strbuf.append(" AND A.KMXX_KMMC LIKE '%").append(beanIn.getKMXX_KMMC())
						.append("%'");
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
	 * @return int
	 * @author ztz
	 * @date 2014年12月8日 上午11:37:20
	 */
	public int insertData(Pojo_KMXX beanIn) throws Exception {
		int result = 0;
		String strId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入站点ID

		try {
			db.openConnection();
			db.beginTran();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     KMXX ");
			strbuf.append(" ( ");
			strbuf.append("     KMXX_KMID, ");//科目ID
			strbuf.append("     KMXX_KMMC, ");//科目名称
			strbuf.append("     KMXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     KMXX_CJR, ");//创建人
			strbuf.append("     KMXX_CJSJ, ");//创建时间
			strbuf.append("     KMXX_GXR, ");//更新人
			strbuf.append("     KMXX_GXSJ  ");//更新时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+strId+"', ");//科目ID
			strbuf.append("     '"+beanIn.getKMXX_KMMC()+"', ");//科目名称
			strbuf.append("     '0', ");//删除标志（0-正常 1-删除）
			strbuf.append("     '"+beanIn.getKMXX_CJR()+"', ");//创建人
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbuf.append("     '"+beanIn.getKMXX_GXR()+"', ");//更新人
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS') ");//更新时间
			strbuf.append(" ) ");

			result = db.executeSQL(strbuf);
			db.commit();
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
	 * @return int
	 * @author ztz
	 * @date 2014年12月8日 上午11:39:55
	 */
	public int updateData(Pojo_KMXX beanIn) throws Exception {
		int result = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     KMXX ");
			strbuf.append(" SET ");
			strbuf.append("     KMXX_KMMC='").append(beanIn.getKMXX_KMMC()).append("',");//科目名称
			strbuf.append("     KMXX_GXR='").append(beanIn.getKMXX_GXR()).append("',");//更新人
			strbuf.append("     KMXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     KMXX_KMID='").append(beanIn.getKMXX_KMID()).append("'");//科目ID
			
			result = db.executeSQL(strbuf);
			db.commit();
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
	 * @return int
	 * @author ztz
	 * @date 2014年12月8日 上午11:43:07
	 */
	public int deleteData(Pojo_KMXX beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE ");
			strbuf.append("     KMXX ");
			strbuf.append(" WHERE ");
			strbuf.append("     KMXX_KMID='").append(beanIn.getKMXX_KMID()).append("'");//科目ID
			
			result = db.executeSQL(strbuf);
			db.commit();
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