package com.xsjy.service.service1110000;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_1110000.Pojo1110130;

public class Service1110130 extends BaseService {
	private DBManager db = null;

	public Service1110130() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getStudentInfo
	 * @Description: 获取学生信息
	 * @param yhid
	 * @throws Exception
	 * @return Pojo1110130
	 * @author czl
	 * @date 2015-01-07
	 */
	public Pojo1110130 getStudentInfo(String yhid) throws Exception {
		Pojo1110130 result = null;
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.XSXX_XSID, ");//学生ID
			strbuf.append("     A.XSXX_XSBM, ");//学生编码
			strbuf.append("     A.XSXX_XSXM, ");//学生姓名
			strbuf.append("     A.XSXX_XB, ");//性别（1-男 2-女）
			strbuf.append("     CASE WHEN A.XSXX_XB = 1 THEN '男' WHEN A.XSXX_XB = 2 THEN '女' ELSE '' END AS XSXB, ");//性别（1-男 2-女）
			strbuf.append("     A.XSXX_CSRQ, ");//出生日期
			strbuf.append("     A.XSXX_LXFS, ");//联系方式
			strbuf.append("     A.XSXX_ZZ, ");//住址
			strbuf.append("     A.XSXX_JD, ");//阶段
			strbuf.append("     A.XSXX_NJ, ");//年级
			strbuf.append("     B.JDJY_JDMC || '-' || A.XSXX_NJ AS JDNJ, ");//阶段年级
			strbuf.append("     A.XSXX_CRJJ, ");//个人简介
			strbuf.append("     A.XSXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     A.XSXX_CJR, ");//创建人
			strbuf.append("     A.XSXX_CJSJ, ");//创建时间
			strbuf.append("     A.XSXX_GXR, ");//更新人
			strbuf.append("     A.XSXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     XSXX A, JDJY B ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.XSXX_JD = B.JDJY_JDID(+)");
			if (MyStringUtils.isNotBlank(yhid)) {
				strbuf.append(" AND A.XSXX_XSBM ='").append(yhid).append("'");							
			}
			ResultSetHandler<Pojo1110130> rsh = new BeanHandler<Pojo1110130>(
					Pojo1110130.class);
			result = db.queryForBeanHandler(strbuf, rsh);

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
	 * @FunctionName: updateStudent
	 * @Description: 更新数据
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-07
	 */
	public int updateStudent(Pojo1110130 beanIn) throws Exception {
		int result = 0;
		int resultXSXX = 0;
		int resultYHXX = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			/* 更新XSXX表数据Start */
			StringBuffer strbufXSXX = new StringBuffer();
			strbufXSXX.append(" UPDATE ");
			strbufXSXX.append("     XSXX ");
			strbufXSXX.append(" SET ");
			strbufXSXX.append("     XSXX_XSXM='").append(beanIn.getXSXX_XSXM()).append("',");//学生姓名
			strbufXSXX.append("     XSXX_XB='").append(MyStringUtils.safeToString(beanIn.getXSXX_XB())).append("',");//性别（1-男 2-女）
			strbufXSXX.append("     XSXX_CSRQ='").append(beanIn.getXSXX_CSRQ()).append("',");//出生日期
			strbufXSXX.append("     XSXX_LXFS='").append(beanIn.getXSXX_LXFS()).append("',");//联系方式
			strbufXSXX.append("     XSXX_ZZ='").append(beanIn.getXSXX_ZZ()).append("',");//住址
			strbufXSXX.append("     XSXX_JD='").append(MyStringUtils.safeToString(beanIn.getXSXX_JD())).append("',");//阶段
			strbufXSXX.append("     XSXX_NJ='").append(MyStringUtils.safeToString(beanIn.getXSXX_NJ())).append("',");//年级
			strbufXSXX.append("     XSXX_CRJJ='").append(beanIn.getXSXX_CRJJ()).append("',");//个人简介
			strbufXSXX.append("     XSXX_GXR='").append(beanIn.getXSXX_GXR()).append("',");//更新人
			strbufXSXX.append("     XSXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbufXSXX.append(" WHERE ");
			strbufXSXX.append("     XSXX_XSID='").append(beanIn.getXSXX_XSID()).append("'");//学生ID
			resultXSXX = db.executeSQL(strbufXSXX);
			/* 更新XSXX表数据End */
			/* 更新YHXX表数据Start */
			StringBuffer strbufYHXX = new StringBuffer();
			strbufYHXX.append(" UPDATE ");
			strbufYHXX.append("     YHXX ");
			strbufYHXX.append(" SET ");
			strbufYHXX.append("     YHXX_YHMC='").append(beanIn.getXSXX_XSXM()).append("',");//用户名称
			strbufYHXX.append("     YHXX_GXR='").append(beanIn.getXSXX_GXR()).append("',");//更新人
			strbufYHXX.append("     YHXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbufYHXX.append(" WHERE ");
			strbufYHXX.append("     YHXX_YHID='").append(beanIn.getXSXX_XSBM()).append("'");//用户ID
			resultYHXX = db.executeSQL(strbufYHXX);
			/* 更新YHXX表数据End */
			if(resultXSXX > 0 && resultYHXX > 0 && resultXSXX == resultYHXX) {
				db.commit();
				result = 1;
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