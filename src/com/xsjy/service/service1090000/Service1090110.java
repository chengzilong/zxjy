package com.xsjy.service.service1090000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_1090000.Pojo1090110;

public class Service1090110 extends BaseService {
	private DBManager db = null;

	public Service1090110() {
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
	 * @date 2014年12月8日 下午2:16:33
	 */
	public int getDataCount(Pojo1090110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.KCXX_KCID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     KCXX A, JDJY B, KMXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.KCXX_KCJD = B.JDJY_JDID ");
			strbuf.append(" AND A.KCXX_KMID = C.KMXX_KMID ");
			strbuf.append(" AND A.KCXX_SCBZ = '0'");
			strbuf.append(" AND B.JDJY_SCBZ = '0'");
			strbuf.append(" AND C.KMXX_SCBZ = '0'");
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
	 * @return List<Pojo1090110>
	 * @author ztz
	 * @date 2014年12月8日 下午2:16:44
	 */
	public List<Pojo1090110> getDataList(Pojo1090110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1090110> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.KCXX_KCID, ");//课程ID
			strbuf.append("     A.KCXX_KCJD, ");//课程阶段
			strbuf.append("     B.JDJY_JDMC AS JDMC, ");//阶段名称
			strbuf.append("     A.KCXX_KMID, ");//科目ID
			strbuf.append("     C.KMXX_KMMC AS KMMC, ");//科目名称
			strbuf.append("     A.KCXX_KCMC, ");//课程名称
			strbuf.append("     A.KCXX_KCMS, ");//课程描述
			strbuf.append("     A.KCXX_SCBZ, ");//删除标志
			strbuf.append("     CASE WHEN A.KCXX_SCBZ = '0' THEN '可用' ELSE '不可用' END AS SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     A.KCXX_CJR, ");//创建人
			strbuf.append("     A.KCXX_CJSJ, ");//创建时间
			strbuf.append("     A.KCXX_GXR, ");//更新人
			strbuf.append("     A.KCXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     KCXX A, JDJY B, KMXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.KCXX_KCJD = B.JDJY_JDID ");
			strbuf.append(" AND A.KCXX_KMID = C.KMXX_KMID ");
			strbuf.append(" AND A.KCXX_SCBZ = '0'");
			strbuf.append(" AND B.JDJY_SCBZ = '0'");
			strbuf.append(" AND C.KMXX_SCBZ = '0'");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.KCXX_CJSJ DESC ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1090110>> rs = new BeanListHandler<Pojo1090110>(
					Pojo1090110.class);
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
	 * @date 2014年12月8日 下午2:22:16
	 */
	private String searchSql(Pojo1090110 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 所属阶段 */
			if (MyStringUtils.isNotBlank(beanIn.getKCXX_KCJD())) {
				if (!"000".equals(beanIn.getKCXX_KCJD())) {
					strbuf.append(" AND A.KCXX_KCJD = '").append(beanIn.getKCXX_KCJD())
							.append("'");
				}
			}
			/* 科目名称 */
			if (MyStringUtils.isNotBlank(beanIn.getKCXX_KMID())) {
				if (!"000".equals(beanIn.getKCXX_KMID())) {
					strbuf.append(" AND A.KCXX_KMID = '").append(beanIn.getKCXX_KMID())
							.append("'");
				}
			}
			/* 课程描述 */
			if (MyStringUtils.isNotBlank(beanIn.getKCXX_KCMS())) {
				strbuf.append(" AND A.KCXX_KCMS LIKE '%").append(beanIn.getKCXX_KCMS())
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
	 * @date 2014年12月8日 下午3:34:16
	 */
	public int insertData(Pojo1090110 beanIn) throws Exception {
		int result = 0;
		String strId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入站点ID

		try {
			db.openConnection();
			db.beginTran();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     KCXX ");
			strbuf.append(" ( ");
			strbuf.append("     KCXX_KCID, ");//课程ID
			strbuf.append("     KCXX_KCJD, ");//课程阶段
			strbuf.append("     KCXX_KMID, ");//科目ID
			strbuf.append("     KCXX_KCMC, ");//课程名称
			strbuf.append("     KCXX_KCMS, ");//课程描述
			strbuf.append("     KCXX_SCBZ, ");//删除标志
			strbuf.append("     KCXX_CJR, ");//创建人
			strbuf.append("     KCXX_CJSJ, ");//创建时间
			strbuf.append("     KCXX_GXR, ");//更新人
			strbuf.append("     KCXX_GXSJ  ");//更新时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+strId+"', ");//课程ID
			strbuf.append("     '"+beanIn.getKCXX_KCJD()+"', ");//课程阶段
			strbuf.append("     '"+beanIn.getKCXX_KMID()+"', ");//科目ID
			strbuf.append("     '"+beanIn.getJDMC() + beanIn.getKMMC()+"', ");//课程名称=阶段名称+科目名称
			strbuf.append("     '"+MyStringUtils.safeToString(beanIn.getKCXX_KCMS())+"', ");//课程描述
			strbuf.append("     '0', ");//删除标志
			strbuf.append("     '"+beanIn.getKCXX_CJR()+"', ");//创建人
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbuf.append("     '"+beanIn.getKCXX_GXR()+"', ");//更新人
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
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
	 * @date 2014年12月8日 下午3:37:58
	 */
	public int updateData(Pojo1090110 beanIn) throws Exception {
		int result = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     KCXX ");
			strbuf.append(" SET ");
			strbuf.append("     KCXX_KCJD='").append(beanIn.getKCXX_KCJD()).append("',");//课程阶段
			strbuf.append("     KCXX_KMID='").append(beanIn.getKCXX_KMID()).append("',");//科目ID
			strbuf.append("     KCXX_KCMC='").append(beanIn.getJDMC() + beanIn.getKMMC()).append("',");//课程名称=阶段名称+科目名称
			strbuf.append("     KCXX_KCMS='").append(beanIn.getKCXX_KCMS()).append("',");//课程描述
			strbuf.append("     KCXX_GXR='").append(beanIn.getKCXX_GXR()).append("',");//更新人
			strbuf.append("     KCXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     KCXX_KCID='").append(beanIn.getKCXX_KCID()).append("'");//课程ID
			
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
	 * @date 2014年12月8日 下午3:41:49
	 */
	public int deleteData(Pojo1090110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE ");
			strbuf.append("     KCXX ");
			strbuf.append(" WHERE ");
			strbuf.append("     KCXX_KCID='").append(beanIn.getKCXX_KCID()).append("'");//课程ID
			
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