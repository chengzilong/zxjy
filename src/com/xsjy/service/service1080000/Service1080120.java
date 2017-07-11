package com.xsjy.service.service1080000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.xsjy.pojo.BaseTable.Pojo_BMXX;
import com.xsjy.pojo.Custom.pojo_1080000.Pojo1080120;
import com.xsjy.pojo.Custom.pojo_1080000.Pojo1080121;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;



public class Service1080120 extends BaseService {

	private DBManager db = null;

	public Service1080120() {
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
	public String getDate() throws Exception{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");//可以方便地修改日期格式
		String date = dateFormat.format( now ); 
		return date;
	}
	/**
	 * @FunctionName: getManagePayList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-12
	 */
	public int getManagePayList_TotalCount(Pojo1080120 beanIn) throws Exception {
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
	 * @FunctionName: getManagePaymxList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-12
	 */
	public int getManagePaymxList_TotalCount(Pojo1080121 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(JFMX_JFMXID)");
			sql.append("   FROM JFMX ");
			sql.append("  WHERE 1 = 1  AND  JFMX_SCBZ = '0' ");
			if (MyStringUtils.isNotBlank(beanIn.getJFMX_BMID())) {	
				sql.append("  AND JFMX_BMID = '").append(beanIn.getJFMX_BMID())
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
	 * @FunctionName: getManagePayList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1080120>
	 * @author czl
	 * @date 2014-12-12
	 */
	public List<Pojo1080120> getManagePayList_PageData(Pojo1080120 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1080120> result = null;
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
			sql.append("        I.BCXX_BCMC AS BCMC,");// 班次名称
			sql.append("        A.BMXX_BMZT,");// 报名状态
			sql.append("        A.BMXX_BZXX");// 备注
			sql.append("   FROM BMXX A LEFT JOIN XSXX B ON A.BMXX_XSID = B.XSXX_XSID ");
			sql.append("   LEFT JOIN SKSD C ON A.BMXX_SDID = C.SKSD_SDID");
			sql.append("   LEFT JOIN JSXX D ON A.BMXX_JSID = D.JSXX_JSID");
			sql.append("   LEFT JOIN KCLX E ON A.BMXX_LXID = E.KCLX_LXID");
			sql.append("   LEFT JOIN BMFS F ON A.BMXX_BMFS = F.BMFS_FSID");
			sql.append("   LEFT JOIN KCXX G ON A.BMXX_XXID = G.KCXX_KCID");
			sql.append("   LEFT JOIN JSXX H ON A.BMXX_TJJSID = H.JSXX_JSID");
			sql.append("   LEFT JOIN BCXX I ON A.BMXX_SSBC = I.BCXX_BCID");
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
			ResultSetHandler<List<Pojo1080120>> rs = new BeanListHandler<Pojo1080120>(
					Pojo1080120.class);
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
	 * @FunctionName: getManagePaymxList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1080121>
	 * @author czl
	 * @date 2014-12-12
	 */
	public List<Pojo1080121> getManagePaymxList_PageData(Pojo1080121 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1080121> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.JFMX_JFMXID,");//交费明细ID
			sql.append("        B.BMXX_FY AS JFMX_FY,");// 费用
			sql.append("        A.JFMX_JFJE,");// 交费金额
			sql.append("        A.JFMX_JFRQ,");// 交费日期
			sql.append("        A.JFMX_BZ");// 备注
			sql.append("   FROM JFMX A ,BMXX B ");
			sql.append("  WHERE   A.JFMX_SCBZ = '0' AND A.JFMX_BMID = B.BMXX_BMID");
			if (MyStringUtils.isNotBlank(beanIn.getJFMX_BMID())) {	
				sql.append("  AND A.JFMX_BMID = '").append(beanIn.getJFMX_BMID())
					.append("'");	
			}
			sql.append(" ORDER BY ");
			sql.append("        A.JFMX_CJSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1080121>> rs = new BeanListHandler<Pojo1080121>(
					Pojo1080121.class);
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
	 * @FunctionName: getValue
	 * @Description: 获得数据
	 * @param beanIn
	 * @throws Exception
	 * @return Pojo1080120
	 * @author czl
	 * @date 2014-12-16
	 */
	public Pojo1080120 getValue(String bmid) throws Exception {
		Pojo1080120 result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.BMXX_BMID, ");//报名ID
			sql.append("     A.BMXX_FY, ");//费用
			sql.append("     A.BMXX_WJFY ");//未交费用
			sql.append("     FROM ");
			sql.append("     BMXX A");
			sql.append("     WHERE A.BMXX_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(bmid)) {
				sql.append(" AND A.BMXX_BMID ='").append(bmid).append("'");	
						
			}
			ResultSetHandler<Pojo1080120> rsh = new BeanHandler<Pojo1080120>(
					Pojo1080120.class);
			result = db.queryForBeanHandler(sql, rsh);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: insertManagePay
	 * @Description: 新增教学点交费
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-13
	 */
	public int insertManagePay(Pojo1080120 beanIn,Pojo1080121 beanInmx) throws Exception {
		int result = 0;
		int resultBM = 0;
		int resultJF = 0;
		String sysdate = getSystemdate();
		String date = getDate();
		try {
			db.openConnection();
			db.beginTran();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     BMXX ");
			strbuf.append(" SET ");
			strbuf.append("     BMXX_YJFY=(").append("SELECT BMXX_YJFY +"+beanIn.getBMXX_YJFY()+" FROM BMXX WHERE BMXX_BMID = '").append(beanIn.getBMXX_BMID()).append("'),");// 已交费用		
			strbuf.append("     BMXX_WJFY=(SELECT BMXX_FY-(SELECT BMXX_YJFY+"+beanIn.getBMXX_YJFY()+" FROM BMXX WHERE BMXX_BMID = '").append(beanIn.getBMXX_BMID()).append("')").append("FROM BMXX WHERE BMXX_BMID = '").append(beanIn.getBMXX_BMID()).append("'),");// 未交费用
			strbuf.append("     BMXX_GXR='").append(beanIn.getBMXX_GXR()).append("',");// 修改人
			strbuf.append("     BMXX_GXSJ='" + sysdate + "'");// 修改时间
			strbuf.append(" WHERE 1 = 1 AND BMXX_SCBZ = '0'");
			strbuf.append("  AND   BMXX_BMID='").append(beanIn.getBMXX_BMID()).append("'");// 报名ID
			resultBM = db.executeSQL(strbuf);
			
			
			//Pojo_BMXX bmxx = null;
			//bmxx = this.getWJFY(beanIn.getBMXX_BMID());
			StringBuffer strbufjf = new StringBuffer();
			String jfid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			strbufjf.append(" INSERT INTO ");
			strbufjf.append("     JFMX ");
			strbufjf.append(" ( ");
			strbufjf.append("     JFMX_JFMXID, ");// 交费明细ID
			strbufjf.append("     JFMX_BMID, ");// 报名ID
			strbufjf.append("     JFMX_JFLXID, ");// 交费类型
			strbufjf.append("     JFMX_JFJE, ");// 交费金额
			strbufjf.append("     JFMX_JFRQ, ");// 交费日期
			strbufjf.append("     JFMX_BZ, ");// 备注
			strbufjf.append("     JFMX_CJR, ");// 创建人
			strbufjf.append("     JFMX_CJSJ, ");// 创建时间
			strbufjf.append("     JFMX_GXR, ");// 更新人
			strbufjf.append("     JFMX_GXSJ ");// 修改时间
			strbufjf.append(" ) ");
			strbufjf.append(" VALUES ");
			strbufjf.append(" ( ");
			strbufjf.append("     '" + jfid + "', ");
			strbufjf.append("     '" + beanIn.getBMXX_BMID() + "', ");
			strbufjf.append("     '" + beanInmx.getJFMX_JFLXID() + "', ");
			strbufjf.append("     '" + beanInmx.getJFMX_JFJE() + "', ");
			strbufjf.append("     '" + date + "', ");
			strbufjf.append("     '" + beanInmx.getJFMX_BZ() + "', ");
			strbufjf.append("     '" + beanIn.getBMXX_CJR() + "', ");
			strbufjf.append("     '" + sysdate + "', ");
			strbufjf.append("     '" + beanIn.getBMXX_GXR() + "', ");
			strbufjf.append("     '" + sysdate + "' ");
			strbufjf.append(" ) ");
			resultJF = db.executeSQL(strbufjf);
			if(resultBM==0||resultJF==0){
				db.rollback();
			}else{
				db.commit();
				result =1;
			}
			Pojo_BMXX bmxx = null;
			bmxx = this.getWJFY(beanIn.getBMXX_BMID());
			String yjfy = bmxx.getBMXX_WJFY();
			String bcid = bmxx.getBMXX_SSBC();
			if(yjfy.equals("0")){
				if(MyStringUtils.isNotBlank(bcid)){
					updateBMZT(beanIn.getBMXX_BMID(),"3");
				}else{
					updateBMZT(beanIn.getBMXX_BMID(),"2");
				}		
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
	 * @FunctionName: updateBMZT
	 * @Description: 修改报名状态
	 * @param beanIn
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2014-12-19
	 */
	public void updateBMZT(String bmid,String bmzt) throws Exception {
		try {
			db.openConnection();
			db.beginTran();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     BMXX ");
			strbuf.append(" SET ");
			strbuf.append("     BMXX_BMZT='"+bmzt+"'");// 报名状态
			strbuf.append(" WHERE 1 = 1 AND BMXX_SCBZ = '0'");
			strbuf.append("  AND   BMXX_BMID='"+bmid+"'");// 报名ID
			db.executeSQL(strbuf);
			db.commit();
		}catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
	}
	//查询报名信息表中的未交费用
	private Pojo_BMXX getWJFY(String bmid) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		Pojo_BMXX bmxx = new Pojo_BMXX();
		try {
				strbuf.append(" SELECT ");
				strbuf.append("     BMXX_WJFY");//未交费用
				strbuf.append(" FROM ");
				strbuf.append("     BMXX ");
				strbuf.append("   WHERE BMXX_BMID =  '");
				strbuf.append(bmid).append("'");

				ResultSetHandler<Pojo_BMXX> rsh = new BeanHandler<Pojo_BMXX>(
						Pojo_BMXX.class);
				
				bmxx = db.queryForBeanHandler(strbuf, rsh);
		} catch (Exception e) {
				MyLogger.error(this.getClass().getName(), e);
				throw e;
		}
			return bmxx;
	}
	/**
	 * @FunctionName: updateManagePay
	 * @Description: 修改教学点交费
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-12
	 */
	public int updateManagePay(Pojo1080120 beanIn,Pojo1080121 beanInmx) throws Exception {
		int result = 0;
		int resultBM = 0;
		int resultJF = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			db.beginTran();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     BMXX ");
			strbuf.append(" SET ");		
			strbuf.append("     BMXX_YJFY=").append("(SELECT BMXX_YJFY +"+beanIn.getBMXX_YJFY()+" FROM BMXX WHERE BMXX_BMID = '").append(beanIn.getBMXX_BMID()).append("'),");// 已交费用		
			strbuf.append("     BMXX_WJFY=(SELECT BMXX_FY-(SELECT BMXX_YJFY+"+beanIn.getBMXX_YJFY()+" FROM BMXX WHERE BMXX_BMID = '").append(beanIn.getBMXX_BMID()).append("')").append("FROM BMXX WHERE BMXX_BMID = '").append(beanIn.getBMXX_BMID()).append("'),");// 未交费用
			strbuf.append("     BMXX_GXR='").append(beanIn.getBMXX_GXR()).append("',");// 修改人
			strbuf.append("     BMXX_GXSJ='" + sysdate + "'");// 修改时间
			strbuf.append(" WHERE 1 = 1 AND BMXX_SCBZ = '0'");
			strbuf.append("  AND   BMXX_BMID='").append(beanIn.getBMXX_BMID()).append("'");// 报名ID
			resultBM = db.executeSQL(strbuf);
			
			StringBuffer strbufjf = new StringBuffer();
			strbufjf.append(" UPDATE ");
			strbufjf.append("     JFMX ");
			strbufjf.append(" SET ");	
			strbufjf.append("     JFMX_JFLXID='").append(beanInmx.getJFMX_JFLXID()).append("',");// 交费类型ID
			strbufjf.append("     JFMX_JFJE='").append(beanInmx.getJFMX_JFJE()).append("',");// 交费金额
			//strbufjf.append("     JFMX_SYJE=").append("(SELECT JFMX_SYJE -("+beanIn.getBMXX_YJFY()+") FROM JFMX WHERE JFMX_JFMXID = '").append(beanInmx.getJFMX_JFMXID()).append("'),");// 剩余金额		
			strbufjf.append("     JFMX_BZ='").append(beanInmx.getJFMX_BZ()).append("',");// 备注
			strbufjf.append("     JFMX_GXR='").append(beanIn.getBMXX_GXR()).append("',");// 修改人
			strbufjf.append("     JFMX_GXSJ='" + sysdate + "'");// 修改时间
			strbufjf.append(" WHERE 1 = 1 AND JFMX_SCBZ = '0'");
			strbufjf.append("  AND   JFMX_JFMXID='").append(beanInmx.getJFMX_JFMXID()).append("'");// 报名ID
			resultJF = db.executeSQL(strbufjf);
			if(resultBM==0||resultJF==0){
				db.rollback();
			}else{
				db.commit();
				result =1;
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
	 * @FunctionName: deleteManagePay
	 * @Description: 删除教学点交费
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-12
	 */
	public int deleteManagePay(Pojo1080121 beanInmx) throws Exception {
		int result = 0;
		int resultBM = 0;
		int resultJF = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			db.beginTran();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     BMXX ");
			strbuf.append(" SET ");		
			strbuf.append("     BMXX_YJFY=").append("(SELECT BMXX_YJFY -"+beanInmx.getJFMX_JFJE()+" FROM BMXX WHERE BMXX_BMID = '").append(beanInmx.getJFMX_BMID()).append("'),");//已交费用
			strbuf.append("     BMXX_WJFY=").append("(SELECT BMXX_WJFY +"+beanInmx.getJFMX_JFJE()+" FROM BMXX WHERE BMXX_BMID = '").append(beanInmx.getJFMX_BMID()).append("'),");//未交费用	
			strbuf.append("     BMXX_GXR='").append(beanInmx.getJFMX_GXR()).append("',");// 修改人
			strbuf.append("     BMXX_GXSJ='" + sysdate + "'");// 修改时间
			strbuf.append(" WHERE 1 = 1 AND BMXX_SCBZ = '0'");
			strbuf.append("  AND   BMXX_BMID='").append(beanInmx.getJFMX_BMID()).append("'");// 报名ID
			resultBM = db.executeSQL(strbuf);
			
			StringBuffer strbufjf = new StringBuffer();
			strbufjf.append(" DELETE ");
			strbufjf.append("     JFMX ");
			strbufjf.append(" WHERE  JFMX_JFMXID='").append(beanInmx.getJFMX_JFMXID()).append("'");// 交费明细ID
			resultJF = db.executeSQL(strbufjf);
			if(resultBM==0||resultJF==0){
				db.rollback();
			}else{
				db.commit();
				result =1;
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
