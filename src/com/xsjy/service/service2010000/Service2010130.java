package com.xsjy.service.service2010000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_2010000.Pojo2010130;

public class Service2010130 extends BaseService {
	private DBManager db = null;

	public Service2010130() {
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
	 * @date 2015-01-12
	 */
	public int getDataCount(Pojo2010130 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(BMXX_BMID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     BMXX A,XSXX B");
			strbuf.append(" WHERE  A.BMXX_XSID = B.XSXX_XSID(+)");
			strbuf.append("  AND A.BMXX_SCBZ = '0' AND B.XSXX_XSBM = '").append(beanIn.getBMXX_CJR()).append("'");
			/* 授课教师 */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_JSID())) {
				if (!"000".equals(beanIn.getBMXX_JSID())) {
					strbuf.append(" AND BMXX_JSID = '").append(beanIn.getBMXX_JSID()).append("'");
				}
			}
			/* 报名状态 */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_BMZT())) {
				if (!"000".equals(beanIn.getBMXX_BMZT())) {
					strbuf.append(" AND BMXX_BMZT = '").append(beanIn.getBMXX_BMZT()).append("'");
				}
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
	 * @FunctionName: getDataList
	 * @Description: 获取列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo2010130>
	 * @author czl
	 * @date 2015-01-12
	 */
	public List<Pojo2010130> getDataList(Pojo2010130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo2010130> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.BMXX_BMID, ");//报名ID
			strbuf.append("     A.BMXX_XSID, ");//学生ID
			strbuf.append("     B.XSXX_XSXM AS XSXM, ");//学生姓名
			strbuf.append("     A.BMXX_XXID, ");//课程信息ID
			strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
			strbuf.append("     A.BMXX_LXID, ");//课程类型ID
			strbuf.append("     D.KCLX_LXMC AS KCLXMC, ");//课程类型名称
			strbuf.append("     A.BMXX_SDID, ");//上课时段ID
			strbuf.append("     E.SKSD_SDMC AS SKSD, ");//上课时段
			strbuf.append("     A.BMXX_XS, ");//学时
			strbuf.append("     A.BMXX_JSID, ");//教师ID
			strbuf.append("     F.JSXX_JSXM AS KCJSXM, ");//授课教师姓名
			strbuf.append("     A.BMXX_FY, ");//费用
			strbuf.append("     A.BMXX_YJFY, ");//已交费用
			strbuf.append("     A.BMXX_WJFY, ");//未交费用
			strbuf.append("     A.BMXX_SSBC, ");//班次ID
			strbuf.append("     J.BCXX_BCMC AS BCMC, ");//班次名称
			strbuf.append("     A.BMXX_BMFS, ");//报名方式
			strbuf.append("     H.BMFS_FSMC AS BMFS, ");//报名方式
			strbuf.append("     A.BMXX_BMZT, ");//报名状态
			strbuf.append("     I.BMZT_BMZTMC AS BMZT, ");//报名状态
			strbuf.append("     A.BMXX_TJJSID, ");//推荐教师ID
			strbuf.append("     G.JSXX_JSXM AS TJJSXM, ");//推荐教师姓名
			strbuf.append("     A.BMXX_BZXX, ");//备注信息
			strbuf.append("     A.BMXX_SCBZ, ");//删除标志
			strbuf.append("     K.YHXX_YHMC AS BMXX_CJR, ");//创建人
			strbuf.append("     A.BMXX_CJSJ, ");//创建时间
			strbuf.append("     A.BMXX_GXR, ");//更新人
			strbuf.append("     A.BMXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     BMXX A, XSXX B, KCXX C, KCLX D, SKSD E, JSXX F, JSXX G, BMFS H, BMZT I, BCXX J, YHXX K ");
			strbuf.append(" WHERE 1=1 AND  B.XSXX_XSBM = '").append(beanIn.getBMXX_CJR()).append("'");
			strbuf.append(" AND A.BMXX_XSID = B.XSXX_XSID");
			strbuf.append(" AND A.BMXX_XXID = C.KCXX_KCID");
			strbuf.append(" AND A.BMXX_LXID = D.KCLX_LXID");
			strbuf.append(" AND A.BMXX_SDID = E.SKSD_SDID");
			strbuf.append(" AND A.BMXX_JSID = F.JSXX_JSID(+)");
			strbuf.append(" AND A.BMXX_TJJSID = G.JSXX_JSID(+)");
			strbuf.append(" AND A.BMXX_BMFS = H.BMFS_FSID");
			strbuf.append(" AND A.BMXX_BMZT = I.BMZT_BMZTID");
			strbuf.append(" AND A.BMXX_SSBC = J.BCXX_BCID(+)");
			strbuf.append(" AND A.BMXX_CJR = K.YHXX_YHID");
			strbuf.append(" AND A.BMXX_SCBZ = '0'");
			strbuf.append(" AND B.XSXX_SCBZ = '0'");
			strbuf.append(" AND C.KCXX_SCBZ = '0'");
			strbuf.append(" AND D.KCLX_SCBZ = '0'");
			strbuf.append(" AND E.SKSD_SCBZ = '0'");
			strbuf.append(" AND F.JSXX_SCBZ(+) = '0'");
			strbuf.append(" AND G.JSXX_SCBZ(+) = '0'");
			strbuf.append(" AND H.BMFS_SCBZ = '0'");
			strbuf.append(" AND I.BMZT_SCBZ = '0'");
			strbuf.append(" AND J.BCXX_SCBZ(+) = '0'");
			strbuf.append(" AND K.YHXX_SCBZ = '0'");
			/* 授课教师 */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_JSID())) {
				if (!"000".equals(beanIn.getBMXX_JSID())) {
					strbuf.append(" AND BMXX_JSID = '").append(beanIn.getBMXX_JSID()).append("'");
				}
			}
			/* 报名状态 */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_BMZT())) {
				if (!"000".equals(beanIn.getBMXX_BMZT())) {
					strbuf.append(" AND BMXX_BMZT = '").append(beanIn.getBMXX_BMZT()).append("'");
				}
			}
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.BMXX_CJSJ DESC ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo2010130>> rs = new BeanListHandler<Pojo2010130>(
					Pojo2010130.class);
			result = db.queryForBeanListHandler(execSql, rs);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	
}