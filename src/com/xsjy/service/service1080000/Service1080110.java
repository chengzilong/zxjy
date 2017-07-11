package com.xsjy.service.service1080000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.xsjy.pojo.Custom.pojo_1080000.Pojo1080110;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;


public class Service1080110 extends BaseService {

	private DBManager db = null;

	public Service1080110() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2014-12-12
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
		String date = dateFormat.format( now ); 
		return date;
	}
	/**
	 * @FunctionName: getOnlinePayList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-13
	 */
	public int getOnlinePayList_TotalCount(Pojo1080110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(BMXX_BMID)");
			sql.append("   FROM BMXX A,XSXX B,JSXX C");
			sql.append("  WHERE 1 = 1  AND  BMXX_SCBZ = '0' AND A.BMXX_XSID = B.XSXX_XSID(+) AND A.BMXX_JSID = C.JSXX_JSXM(+)");
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_BMFS())&&!("000").equals(beanIn.getBMXX_BMFS())) {	
				sql.append("  AND A.BMXX_BMFS = '").append(beanIn.getBMXX_BMFS())
					.append("'");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getXSXM())) {	
				sql.append("  AND B.XSXX_XSXM LIKE '%").append(beanIn.getXSXM())
					.append("%'");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getJSXM())) {	
				sql.append("  AND C.JSXX_JSXM LIKE '%").append(beanIn.getJSXM())
					.append("%'");	
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
	 * @FunctionName: getOnlinePayList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1080110>
	 * @author czl
	 * @date 2014-12-13
	 */
	public List<Pojo1080110> getOnlinePayList_PageData(Pojo1080110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1080110> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.BMXX_BMID,");//报名ID
			sql.append("        B.XSXX_XSXM AS XSXM,");// 学生姓名
			sql.append("        C.SKSD_SDMC AS SKSD,");// 上课时段
			sql.append("        A.BMXX_XS,");// 学时
			sql.append("        D.JSXX_JSXM AS JSXM,");// 教师姓名
			sql.append("        A.BMXX_FY,");// 费用
			sql.append("        A.BMXX_YJFY,");// 已交费用
			sql.append("        A.BMXX_WJFY,");// 未交费用
			sql.append("        E.KCLX_LXMC AS KCLX,");// 课程类型
			sql.append("        F.BMFS_FSMC AS BMFS,");// 报名方式
			sql.append("        G.KCXX_KCMC AS KCMC,");// 课程名称
			sql.append("        H.JSXX_JSXM AS TJJS,");// 推荐教师
			sql.append("        A.BMXX_BMZT,");// 报名状态
			sql.append("        A.BMXX_BZXX");// 备注
			sql.append("   FROM BMXX A LEFT JOIN XSXX B ON A.BMXX_XSID = B.XSXX_XSID ");
			sql.append("   LEFT JOIN SKSD C ON A.BMXX_SDID = C.SKSD_SDID");
			sql.append("   LEFT JOIN JSXX D ON A.BMXX_JSID = D.JSXX_JSID");
			sql.append("   LEFT JOIN KCLX E ON A.BMXX_LXID = E.KCLX_LXID");
			sql.append("   LEFT JOIN BMFS F ON A.BMXX_BMFS = F.BMFS_FSID");
			sql.append("   LEFT JOIN KCXX G ON A.BMXX_XXID = G.KCXX_KCID");
			sql.append("   LEFT JOIN JSXX H ON A.BMXX_TJJSID = H.JSXX_JSID");
			sql.append("  WHERE   A.BMXX_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_BMFS())&&!("000").equals(beanIn.getBMXX_BMFS())) {	
				sql.append("  AND A.BMXX_BMFS = '").append(beanIn.getBMXX_BMFS())
					.append("'");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getXSXM())) {	
				sql.append("  AND B.XSXX_XSXM LIKE '%").append(beanIn.getXSXM())
					.append("%'");	
			}
			if (MyStringUtils.isNotBlank(beanIn.getJSXM())) {	
				sql.append("  AND D.JSXX_JSXM LIKE '%").append(beanIn.getJSXM())
					.append("%'");	
			}
			sql.append(" ORDER BY ");
			sql.append("        A.BMXX_BMID");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1080110>> rs = new BeanListHandler<Pojo1080110>(
					Pojo1080110.class);
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
