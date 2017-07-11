package com.xsjy.service.service2020000;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_2020000.Pojo2020130;

public class Service2020130 extends BaseService {
	
	private DBManager db = null;
	
	public Service2020130() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getTitleList
	 * @Description: 获取课表日程动态表头数据
	 * @return
	 * @throws Exception
	 * @return List<Pojo2020130>
	 * @author ztz
	 * @date 2015年1月15日 下午2:38:26
	 */
	public List<Pojo2020130> getTitleList() throws Exception {
		List<Pojo2020130> titleList = new ArrayList<Pojo2020130>();
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("    TO_CHAR (SYSDATE + ROWNUM - 1, 'yyyy-MM') AS ROTA_NY,");
			strbuf.append("    TO_CHAR (SYSDATE + ROWNUM - 1, 'dd') AS ROTA_DAY,");
			strbuf.append("    TO_CHAR (SYSDATE + ROWNUM - 1, 'DAY') AS ROTA_WEEK");
			strbuf.append(" FROM ");
			strbuf.append("    DUAL");
			strbuf.append(" CONNECT BY ");
			strbuf.append("    ROWNUM <= 7");
			
			ResultSetHandler<List<Pojo2020130>> rsh = new BeanListHandler<Pojo2020130>(Pojo2020130.class);
			
			titleList = db.queryForBeanListHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return titleList;
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取课表日程列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月15日 下午2:51:47
	 */
	public int getDataCount(Pojo2020130 beanIn) throws Exception {
		int count = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("    COUNT(DISTINCT(C.BCXX_BCID)) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("    XSXX A, BCXS B, BCXX C, BCRC D");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("    C.BCXX_BCID");
			
			count = db.queryForInteger(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return count;
	}
	/**
	 * 
	 * @FunctionName: getDataList
	 * @Description: 获取课表日程列表数据
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo2020130>
	 * @author ztz
	 * @date 2015年1月15日 下午2:49:45
	 */
	public List<Pojo2020130> getDataList(Pojo2020130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo2020130> dataList = new ArrayList<Pojo2020130>();
		String strSelect = "";
		
		try {
			/* 获取排课表SQL中SELECT部分语句Start */
			List<Pojo2020130> titleList = this.getTitleList();
			for(int i = 0; i < titleList.size(); i++) {
				Pojo2020130 titleBean = (Pojo2020130)titleList.get(i);
				strSelect += " CASE WHEN NVL(SUM(CASE WHEN D.BCRC_NY IS NULL THEN 0 ELSE CASE WHEN D.BCRC_NY = '" + titleBean.getROTA_NY() + "' THEN TO_NUMBER(NVL(D.BCRC_DAY" + titleBean.getROTA_DAY() + ", 0)) ELSE 0 END END), 0) = 0 THEN '' ELSE '有课' END  AS ROTA_DAY" + (i+1) + ", ";
			}
			db.openConnection();
			/* 获取排课表SQL中SELECT部分语句End */
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("    C.BCXX_BCMC, ");
			strbuf.append(strSelect);
			strbuf.append("    C.BCXX_BCID ");
			strbuf.append(" FROM ");
			strbuf.append("    XSXX A, BCXS B, BCXX C, BCRC D");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" GROUP BY ");
			strbuf.append("    C.BCXX_BCID, C.BCXX_BCMC");
			strbuf.append(" ORDER BY ");
			strbuf.append("    C.BCXX_BCID");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo2020130>> rs = new BeanListHandler<Pojo2020130>(
					Pojo2020130.class);
			dataList = db.queryForBeanListHandler(execSql, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return dataList;
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
	 * @date 2015年1月15日 下午3:23:51
	 */
	private String searchSql(Pojo2020130 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.XSXX_XSID = B.BCXS_XSID");
			strbuf.append(" AND B.BCXS_BCID = C.BCXX_BCID");
			strbuf.append(" AND C.BCXX_BCID = D.BCRC_BCID(+)");
			strbuf.append(" AND A.XSXX_SCBZ = '0'");
			strbuf.append(" AND B.BCXS_SCBZ = '0'");
			strbuf.append(" AND C.BCXX_SCBZ = '0'");
			strbuf.append(" AND D.BCRC_SCBZ(+) = '0'");
			strbuf.append(" AND A.XSXX_XSBM = '").append(beanIn.getXSBM()).append("'");
			/* 班次名称 */
			if (MyStringUtils.isNotBlank(beanIn.getBCXX_BCID())) {
				if (!"000".equals(beanIn.getBCXX_BCID())) {
					strbuf.append(" AND C.BCXX_BCID = '").append(beanIn.getBCXX_BCID()).append("'");
				}
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
}