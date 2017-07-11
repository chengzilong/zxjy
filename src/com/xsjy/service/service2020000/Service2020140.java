package com.xsjy.service.service2020000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_2020000.Pojo2020140;
import com.xsjy.pojo.Custom.pojo_2020000.Pojo2020141;

public class Service2020140 extends BaseService {
	private DBManager db = null;

	public Service2020140() {
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
	 * @date 2015-01-13
	 */
	public int getDataCount(Pojo2020140 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.JSXX_JSID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     JSXX A");
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
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo2020140>
	 * @author czl
	 * @date 2015-01-13
	 */
	public List<Pojo2020140> getDataList(Pojo2020140 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo2020140> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.JSXX_JSID, ");//教师ID
			strbuf.append("     A.JSXX_JSBM, ");//教师编码
			strbuf.append("     A.JSXX_JSXM, ");//教师姓名
			strbuf.append("     A.JSXX_XB, ");//性别（1-男 2-女）
			strbuf.append("     CASE WHEN A.JSXX_XB = 1 THEN '男' WHEN A.JSXX_XB = 2 THEN '女' ELSE '' END AS JSXB, ");//性别（1-男 2-女）
			strbuf.append("     A.JSXX_LXFS, ");//联系方式
			strbuf.append("     A.JSXX_XL, ");//学历
			strbuf.append("     CASE WHEN A.JSXX_JNJY='11' THEN '大于10年' WHEN A.JSXX_JNJY='0' THEN '无' ELSE JSXX_JNJY||'年' END AS JSXX_JNJY,");// 几年经验
			strbuf.append("     CASE WHEN A.JSXX_JSJB = '0' THEN '金牌教师' ELSE '优秀教师' END AS JSXX_JSJB ");//教师级别
			strbuf.append(" FROM ");
			strbuf.append("     JSXX A");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo2020140>> rs = new BeanListHandler<Pojo2020140>(
					Pojo2020140.class);
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
	 * @date 2015-01-13
	 */
	private String searchSql(Pojo2020140 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.JSXX_JSID IN");
			strbuf.append(" (SELECT BCJS_JSID FROM BCJS WHERE BCJS_BCID IN");
			strbuf.append(" (SELECT BCXS_BCID FROM BCXS WHERE BCXS_XSID = ");
			strbuf.append(" (SELECT XSXX_XSID FROM XSXX WHERE XSXX_XSBM ='").append(beanIn.getJSXX_CJR()).append("')))");
			/* 教师编码 */
			if (MyStringUtils.isNotBlank(beanIn.getJSXX_JSBM())) {
				strbuf.append(" AND A.JSXX_JSBM = '").append(beanIn.getJSXX_JSBM())
						.append("'");
			}
			/* 教师姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getJSXX_JSXM())) {
				strbuf.append(" AND A.JSXX_JSXM LIKE '%").append(beanIn.getJSXX_JSXM())
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
	 * @FunctionName: getTalkDataCount
	 * @Description: 统计评价信息列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-13
	 */
	public int getTalkDataCount(Pojo2020141 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(DISTINCT(B.BCXX_BCID)) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     PJXX A, BCXX B, BCJS C, BCXS D, JSXX E, XSXX F ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchTalkSql(beanIn));
			
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
	 * @FunctionName: getTalkDataList
	 * @Description: 获取评价信息列表数据明细
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo2020141>
	 * @author czl
	 * @date 2015-01-13
	 */
	public List<Pojo2020141> getTalkDataList(Pojo2020141 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo2020141> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     E.JSXX_JSID AS JSID, ");//教师ID
			strbuf.append("     E.JSXX_JSBM AS JSBM, ");//教师编码
			strbuf.append("     E.JSXX_JSXM AS JSXM, ");//教师姓名
			strbuf.append("     B.BCXX_BCID, ");//班次ID
			strbuf.append("     B.BCXX_BCMC, ");//班次名称
			strbuf.append("     A.PJXX_PJID, ");//评价ID
			strbuf.append("     A.PJXX_BCID, ");//班次ID
			strbuf.append("     A.PJXX_PJNR, ");//评价内容
			strbuf.append("     A.PJXX_PJJF, ");//评价积分
			strbuf.append("     A.PJXX_PJSJ, ");//评价时间
			strbuf.append("     A.PJXX_CJSJ");//创建时间
			strbuf.append(" FROM ");
			strbuf.append("     PJXX A, BCXX B, BCJS C, BCXS D, JSXX E, XSXX F ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchTalkSql(beanIn));
			strbuf.append(" GROUP BY JSXX_JSID,E.JSXX_JSBM, E.JSXX_JSXM,B.BCXX_BCID,B.BCXX_BCMC,  A.PJXX_PJID,  A.PJXX_BCID,  A.PJXX_PJNR,  A.PJXX_PJJF, A.PJXX_PJSJ,A.PJXX_CJSJ");
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.PJXX_CJSJ DESC ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo2020141>> rs = new BeanListHandler<Pojo2020141>(
					Pojo2020141.class);
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
	 * @FunctionName: searchTalkSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2015-01-13
	 */
	private String searchTalkSql(Pojo2020141 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND B.BCXX_BCID = A.PJXX_BCID(+)");
			strbuf.append(" AND B.BCXX_BCID = C.BCJS_BCID");
			strbuf.append(" AND B.BCXX_BCID = D.BCXS_BCID");
			strbuf.append(" AND C.BCJS_JSID = E.JSXX_JSID");
			strbuf.append(" AND D.BCXS_XSID = F.XSXX_XSID");
			strbuf.append(" AND A.PJXX_SCBZ(+) = '0'");//删除标志(0-正常 1-删除)
			strbuf.append(" AND B.BCXX_SCBZ = '0'");//删除标志(0-正常 1-删除)
			strbuf.append(" AND C.BCJS_SCBZ = '0'");//删除标志(0-正常 1-删除)
			strbuf.append(" AND D.BCXS_SCBZ = '0'");//删除标志(0-正常 1-删除)
			strbuf.append(" AND E.JSXX_SCBZ = '0'");//删除标志(0-正常 1-删除)
			strbuf.append(" AND F.XSXX_SCBZ = '0'");//删除标志(0-正常 1-删除)
			strbuf.append(" AND E.JSXX_JSBM = '").append(beanIn.getJSBM()).append("'");
			strbuf.append(" AND F.XSXX_XSBM = '").append(beanIn.getPJXX_PJR()).append("'");
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: insertTalkData
	 * @Description: 新增评价信息数据
	 * @param beanIn
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2015-01-13
	 */
	public boolean insertTalkData(Pojo2020141 beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		String pjId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数

		try {
			db.openConnection();
			db.beginTran();
			/* 向PJXX表插入数据Start */
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     PJXX ");
			strbuf.append(" ( ");
			strbuf.append("     PJXX_PJID, ");//评价ID
			strbuf.append("     PJXX_BCID, ");//班次ID
			strbuf.append("     PJXX_PJR, ");//评价人
			strbuf.append("     PJXX_BPR, ");//被评人
			strbuf.append("     PJXX_PJNR, ");//评价内容
			strbuf.append("     PJXX_PJJF, ");//评价积分
			strbuf.append("     PJXX_PJSJ, ");//评价时间
			strbuf.append("     PJXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     PJXX_CJR, ");//创建人
			strbuf.append("     PJXX_CJSJ, ");//创建时间
			strbuf.append("     PJXX_GXR, ");//更新人
			strbuf.append("     PJXX_GXSJ  ");//更新时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+pjId+"', ");//评价ID
			strbuf.append("     '"+beanIn.getBCXX_BCID()+"', ");//班次ID
			strbuf.append("     '"+beanIn.getPJXX_GXR()+"', ");//评价人
			strbuf.append("     '"+beanIn.getJSBM()+"', ");//被评人
			strbuf.append("     '"+beanIn.getPJXX_PJNR()+"', ");//评价内容
			strbuf.append("     '"+beanIn.getPJXX_PJJF()+"', ");//评价积分
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYY-MM-DD'), ");//评价时间
			strbuf.append("     '0', ");//删除标志（0-正常 1-删除）
			strbuf.append("     '"+beanIn.getPJXX_GXR()+"', ");//创建人
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbuf.append("     '"+beanIn.getPJXX_GXR()+"', ");//更新人
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
			strbuf.append(" ) ");
			count = db.executeSQL(strbuf);
			/* 向PJXX表插入数据End */
			if(count > 0) {
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
	 * @FunctionName: updateTalkData
	 * @Description: 更新评价信息数据
	 * @param beanIn
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2015-01-13
	 */
	public boolean updateTalkData(Pojo2020141 beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     PJXX ");
			strbuf.append(" SET ");
			strbuf.append("     PJXX_PJNR='").append(beanIn.getPJXX_PJNR()).append("',");//评价内容
			strbuf.append("     PJXX_PJJF='").append(beanIn.getPJXX_PJJF()).append("',");//评价积分
			strbuf.append("     PJXX_PJSJ=TO_CHAR(SYSDATE, 'YYYY-MM-DD'),");//评价时间
			strbuf.append("     PJXX_GXR='").append(beanIn.getPJXX_GXR()).append("',");//更新人
			strbuf.append("     PJXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     PJXX_PJID='").append(beanIn.getPJXX_PJID()).append("'");//评价ID
			count = db.executeSQL(strbuf);
			
			if(count > 0) {
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
	 * @FunctionName: deleteTalkData
	 * @Description: 删除评价信息数据
	 * @param beanIn
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2015-01-13
	 */
	public boolean deleteTalkData(Pojo2020141 beanIn) throws Exception {
		boolean result = false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE ");
			strbuf.append("     PJXX ");
			strbuf.append(" WHERE ");
			strbuf.append("     PJXX_PJID='").append(beanIn.getPJXX_PJID()).append("'");//评价ID
			count = db.executeSQL(strbuf);

			if(count > 0) {
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