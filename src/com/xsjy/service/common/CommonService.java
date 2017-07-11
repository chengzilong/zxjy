package com.xsjy.service.common;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.common.Pojo_SELECT_LIST;

/**
 * @ClassName: CommonService
 * @Package:com.blank.service.common
 * @Description: 共通Service
 * @author ljg
 * @date 2014年7月21日 上午8:33:24
 * @update
 */
public class CommonService {
	
	private DBManager db = null;

	public CommonService() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getRoleList
	 * @Description: 角色下拉列表信息
	 * @return
	 * @throws Exception
	 * @return List<SELECT_LIST>
	 * @author ljg
	 * @date 2014年7月21日 上午10:59:16
	 */
	public List<Pojo_SELECT_LIST> getYhjsList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.YHJS_JSID AS CODE,");
			sql.append("        A.YHJS_JSMC AS NAME");
			sql.append("   FROM YHJS A ");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(sql, rs);
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
	 * @FunctionName: getZdmcList
	 * @Description: 站点下拉列表信息
	 * @param flag
	 * @return
	 * @throws Exception
	 * @return List<SELECT_LIST>
	 * @author ztz
	 * @date 2014年10月28日 下午5:50:56
	 */
	public List<Pojo_SELECT_LIST> getZdmcList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.PXWD_ZDID AS CODE,");
			sql.append("        A.PXWD_ZDMC AS NAME");
			sql.append("   FROM PXWD A ");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(sql, rs);

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
	 * @FunctionName: getSsjdList
	 * @Description: 所属阶段下拉列表信息
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ztz
	 * @date 2014年12月8日 下午2:57:00
	 */
	public List<Pojo_SELECT_LIST> getSsjdList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         JDJY_JDID AS CODE, ");
			strbuf.append("         JDJY_JDMC AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         JDJY ");
			strbuf.append(" WHERE ");
			strbuf.append("         JDJY_SCBZ = '0' ");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getKmmcList
	 * @Description: 科目名称下拉列表信息
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ztz
	 * @date 2014年12月8日 下午2:58:57
	 */
	public List<Pojo_SELECT_LIST> getKmmcList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         KMXX_KMID AS CODE, ");
			strbuf.append("         KMXX_KMMC AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         KMXX ");
			strbuf.append(" WHERE ");
			strbuf.append("         KMXX_SCBZ = '0' ");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getKclxList
	 * @Description: 课程类型下拉列表信息
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author czl
	 * @date 2014-12-09
	 */
	public List<Pojo_SELECT_LIST> getKclxList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         KCLX_LXID AS CODE, ");
			strbuf.append("         KCLX_LXMC AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         KCLX ");
			strbuf.append(" WHERE ");
			strbuf.append("         KCLX_SCBZ = '0' ");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getSksdList
	 * @Description: 上课时段下拉列表信息
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author czl
	 * @date 2014-12-09
	 */
	public List<Pojo_SELECT_LIST> getSksdList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         SKSD_SDID AS CODE, ");
			strbuf.append("         SKSD_SDMC AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         SKSD ");
			strbuf.append(" WHERE ");
			strbuf.append("         SKSD_SCBZ = '0' ");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getJsxmList
	 * @Description: 教师姓名下拉列表信息
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author czl
	 * @date 2014-12-09
	 */
	public List<Pojo_SELECT_LIST> getJsxmList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         JSXX_JSID AS CODE, ");
			strbuf.append("         JSXX_JSXM AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         JSXX ");
			strbuf.append(" WHERE ");
			strbuf.append("         JSXX_SCBZ = '0' ");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getKcmcList
	 * @Description: 课程名称下拉列表信息
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author czl
	 * @date 2014-12-09
	 */
	public List<Pojo_SELECT_LIST> getKcmcList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     KCXX_KCID AS CODE, ");
			strbuf.append("     KCXX_KCMC AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("     KCXX A, JDJY B, KMXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.KCXX_KCJD = B.JDJY_JDID ");
			strbuf.append(" AND A.KCXX_KMID = C.KMXX_KMID ");
			strbuf.append(" AND A.KCXX_SCBZ = '0'");
			strbuf.append(" AND B.JDJY_SCBZ = '0'");
			strbuf.append(" AND C.KMXX_SCBZ = '0'");
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.KCXX_KCJD");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getBmfsList
	 * @Description: 报名方式下拉列表信息
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author czl
	 * @date 2014-12-12
	 */
	public List<Pojo_SELECT_LIST> getBmfsList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     BMFS_FSID AS CODE, ");
			strbuf.append("     BMFS_FSMC AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("     BMFS");
			strbuf.append(" WHERE 1=1 AND BMFS_SCBZ = '0'");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getJflxList
	 * @Description: 交费类型下拉列表信息
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author czl
	 * @date 2014-12-12
	 */
	public List<Pojo_SELECT_LIST> getJflxList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     JFLX_JFLXID AS CODE, ");
			strbuf.append("     JFLX_JFMC AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("     JFLX");
			strbuf.append(" WHERE 1=1 AND JFLX_SCBZ = '0'");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getBmztList
	 * @Description: 报名状态下拉列表信息
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ztz
	 * @date 2014年12月12日 下午3:59:45
	 */
	public List<Pojo_SELECT_LIST> getBmztList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     BMZT_BMZTID AS CODE, ");
			strbuf.append("     BMZT_BMZTMC AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("     BMZT");
			strbuf.append(" WHERE 1=1 AND BMZT_SCBZ = '0'");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getXsnjList
	 * @Description: 学生年级下拉列表信息
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ztz
	 * @date 2014年12月13日 下午5:30:01
	 */
	public List<Pojo_SELECT_LIST> getXsnjList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT TO_CHAR(TO_NUMBER(TO_CHAR(SYSDATE,'yyyy'))-5+ROWNUM) || '级' AS CODE,");
			sql.append("        TO_CHAR(TO_NUMBER(TO_CHAR(SYSDATE,'yyyy'))-5+ROWNUM) || '级' AS NAME");
			sql.append("   FROM ALL_OBJECTS");
			sql.append("  WHERE ROWNUM <= 10");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(sql, rs);
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
	 * @FunctionName: getXsxmList
	 * @Description: 学生姓名下拉列表信息
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ztz
	 * @date 2014年12月13日 下午8:12:00
	 */
	public List<Pojo_SELECT_LIST> getXsxmList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         XSXX_XSID AS CODE, ");
			strbuf.append("         XSXX_XSXM AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         XSXX ");
			strbuf.append(" WHERE ");
			strbuf.append("         XSXX_SCBZ = '0' ");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getJoinCount
	 * @Description: 统计临时报名个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2014年12月12日 上午9:27:29
	 */
	public int getJoinCount(String phone) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.LSBM_LSBMID) ");//数据个数
			strbuf.append("   FROM ");
			strbuf.append("     LSBM A ");
			strbuf.append("  WHERE A.LSBM_XSDH = '").append(phone).append("'");
			strbuf.append("    AND A.LSBM_SCBZ = '0'");//删除标志（0-正常 1-删除）
			
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
	 * @FunctionName: insertJoinLession
	 * @Description: 新增临时报名数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2014年12月12日 上午10:24:30
	 */
	public boolean insertJoinLession(String name,String phone,String remark) throws Exception {
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
			strbuf.append("     '"+name+"', ");//学生姓名
			strbuf.append("     '"+phone+"', ");//学生电话
			strbuf.append("     '999', ");//课程ID
			strbuf.append("     '0', ");//是否回访
			strbuf.append("     '"+remark+"', ");//备注信息
			strbuf.append("     '0', ");//删除标志（0-正常 1-删除）
			strbuf.append("     'system', ");//创建人
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbuf.append("     'system', ");//更新人
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
	 * @FunctionName: getDayListByNY
	 * @Description: 通过年月取得日下拉列表内容
	 * @param strYearMonth
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author 
	 * @date 2014-12-19
	 * @author czl
	 */
	public List<Pojo_SELECT_LIST> getDayListByNY(String strYearMonth) throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			strYearMonth = strYearMonth.replace("-", "");
			if(strYearMonth.length()!=6) return result;
			
			db.openConnection();
			
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strYearMonth).append("','YYYYMM')+(ROWNUM-1),'DD') AS CODE, ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strYearMonth).append("','YYYYMM')+(ROWNUM-1),'DD') || '日' AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         DUAL ");
			strbuf.append(" CONNECT BY ROWNUM<=TO_NUMBER(TO_CHAR(LAST_DAY(TO_DATE('").append(strYearMonth).append("','YYYYMM')),'DD')) ");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getDayListBegin_End
	 * @Description: 取得不足一个月的日
	 * @param strMonth
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author 
	 * @date 2014-12-23
	 * @author czl
	 */
	public List<Pojo_SELECT_LIST> getDayListBegin_End(String strbMonth,String streMonth) throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			strbMonth = strbMonth.replace("-", "");
			streMonth = streMonth.replace("-", "");
			db.openConnection();
			
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strbMonth).append("','YYYYMMDD')+(ROWNUM-1),'DD') AS CODE, ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strbMonth).append("','YYYYMMDD')+(ROWNUM-1),'DD') || '日' AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         DUAL ");
			strbuf.append(" CONNECT BY ROWNUM<='").append(Integer.parseInt(streMonth.substring(6, 8))-Integer.parseInt(strbMonth.substring(6, 8))+1).append("'");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getDayListEnd
	 * @Description: 取得不足一个月的日
	 * @param strMonth
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author 
	 * @date 2014-12-22
	 * @author czl
	 */
	public List<Pojo_SELECT_LIST> getDayListEnd(String strMonth) throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			strMonth = strMonth.replace("-", "");

			db.openConnection();
			
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strMonth.substring(0, 6)).append("','YYYYMM')+(ROWNUM-1),'DD') AS CODE, ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strMonth.substring(0, 6)).append("','YYYYMM')+(ROWNUM-1),'DD') || '日' AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         DUAL ");
			strbuf.append(" CONNECT BY ROWNUM<='").append(strMonth.substring(6, 8)).append("'");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getDayListBegin
	 * @Description: 取得不足一个月的日
	 * @param strMonth
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author 
	 * @date 2014-12-22
	 * @author czl
	 */
	public List<Pojo_SELECT_LIST> getDayListBegin(String strMonth) throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			strMonth = strMonth.replace("-", "");	
			db.openConnection();
			
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strMonth).append("','YYYYMMDD')+(ROWNUM-1),'DD') AS CODE, ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strMonth).append("','YYYYMMDD')+(ROWNUM-1),'DD') || '日' AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         DUAL ");
			strbuf.append(" CONNECT BY ROWNUM<=TO_NUMBER(TO_CHAR(LAST_DAY(TO_DATE('").append(strMonth.substring(0, 6)).append("','YYYYMM')),'DD')) +1-").append(strMonth.substring(6, 8));
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getBCMCList
	 * @Description: 班次名称下拉列表
	 * @param beanUser
	 * @throws Exception
	 * @return List<SELECT_LIST>
	 * @author czl
	 * @date 2014-12-19
	 * @update ztz at 2015年1月6日 上午11:34:16
	 */
	public List<Pojo_SELECT_LIST> getBCMCList(Pojo_YHXX beanUser) throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.BCXX_BCID AS CODE,");
			sql.append("        A.BCXX_BCMC AS NAME");
			sql.append("   FROM BCXX A, KCFY B, JSXX C");
			sql.append("   WHERE 1=1");
			sql.append(" AND A.BCXX_KCFYID = B.KCFY_FYID");
			sql.append(" AND B.KCFY_JSID = C.JSXX_JSID");
			sql.append(" AND A.BCXX_SCBZ = '0' ");
			sql.append(" AND B.KCFY_SCBZ = '0' ");
			sql.append(" AND C.JSXX_SCBZ = '0' ");
			if ("105".equals(beanUser.getYHXX_JSID())) {
				sql.append(" AND C.JSXX_JSBM = '").append(beanUser.getYHXX_YHID()).append("'");
			}
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(sql, rs);

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
	 * @FunctionName: getJSZGList
	 * @Description: 班次名称下拉列表
	 * @param 
	 * @throws Exception
	 * @return List<SELECT_LIST>
	 * @author czl
	 * @date 2014-12-24
	 */
	public List<Pojo_SELECT_LIST> getJSZGList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT JSZG_ZGID AS CODE,");
			sql.append("        JSZG_ZGMC AS NAME");
			sql.append("   FROM JSZG");
			sql.append("   WHERE JSZG_SCBZ = '0'");
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(sql, rs);

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
	 * @FunctionName: getQYList
	 * @Description: 取得区域下拉列表
	 * @param strMonth
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author 
	 * @date 2014-12-26
	 * @author czl
	 */
	public List<Pojo_SELECT_LIST> getQYList(String strJB) throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		
		try {
			

			db.openConnection();
			
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT XZQY_QYID AS CODE,");
			strbuf.append("        XZQY_QYMC AS NAME");
			strbuf.append("   FROM XZQY");
			strbuf.append("   WHERE XZQY_SCBZ = '0' AND XZQY_QYJB = '").append(strJB).append("'");
			
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getYXSJList
	 * @Description: 有效时间下拉列表
	 * @param 
	 * @throws Exception
	 * @return List<SELECT_LIST>
	 * @author czl
	 * @date 2015-01-08
	 */
	public List<Pojo_SELECT_LIST> getYXSJList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT YXSJ_YXID AS CODE,");
			sql.append("        YXSJ_YXSJ AS NAME");
			sql.append("   FROM YXSJ");
			sql.append("   WHERE YXSJ_SCBZ = '0'");
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(sql, rs);

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
	 * @FunctionName: getBCXSList
	 * @Description: 班次名称下拉列表-学生
	 * @param beanUser
	 * @throws Exception
	 * @return List<SELECT_LIST>
	 * @author czl
	 * @date 2015-01-14
	 */
	public List<Pojo_SELECT_LIST> getBCXSList(Pojo_YHXX beanUser) throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.BCXX_BCID AS CODE,");
			sql.append("        A.BCXX_BCMC AS NAME");
			sql.append("   FROM BCXX A");
			sql.append("   WHERE 1=1");
			sql.append(" AND A.BCXX_BCID IN (SELECT BCXS_BCID FROM BCXS");
			sql.append(" WHERE BCXS_XSID = (SELECT XSXX_XSID FROM XSXX");
			sql.append(" WHERE XSXX_XSBM = '").append(beanUser.getYHXX_YHID()).append("'))");
			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(sql, rs);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: getUserName
	 * @Description: 取得用户名
	 * @param UserID
	 * @return
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2015-02-02
	 */
	public String getUserName(String strUserId) throws Exception {
		String result = "";

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     TO_CHAR(YHXX_YHMC) ");//用户名称
			strbuf.append(" FROM ");
			strbuf.append("     YHXX A ");
			strbuf.append(" WHERE A.YHXX_SCBZ = '0'");
			strbuf.append("   AND A.YHXX_YHID  ='").append(strUserId).append("'");

			result = db.queryForString(strbuf);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}	
	/**
	 * @FunctionName: getUserMessCount
	 * @Description: 取得消息数
	 * @param UserID
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ljg
	 * @date 2014年12月10日 上午9:25:00
	 */
	public String getUserMessCount(String strUserId) throws Exception {
		String result = "";

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     TO_CHAR(COUNT(A.XXMX_XXID)) AS MESS_CONT ");//用户角色类型
			strbuf.append(" FROM ");
			strbuf.append("     XXMX A ");
			strbuf.append(" WHERE A.XXMX_CKZT = '0'");
			strbuf.append("   AND A.XXMX_JSRID  ='").append(strUserId).append("'");

			result = db.queryForString(strbuf);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}	
}