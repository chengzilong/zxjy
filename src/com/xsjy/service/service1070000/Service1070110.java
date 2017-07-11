package com.xsjy.service.service1070000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070110;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070111;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070112;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070113;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070114;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070115;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;

public class Service1070110 extends BaseService {

	private DBManager db = null;

	public Service1070110() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2014-12-10
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
		String date = dateFormat.format( now ); 
		return date;
	}
	/**
	 * @FunctionName: getTeacherList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-10
	 */
	public int getTeacherList_TotalCount(Pojo1070110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(JSXX_JSID)");
			sql.append("   FROM JSXX A ");
			sql.append("  WHERE 1 = 1  AND  JSXX_SCBZ = '0' ");
			if (MyStringUtils.isNotBlank(beanIn.getJSXX_JSXM())) {
				sql.append(" AND A.JSXX_JSXM LIKE '%").append(beanIn.getJSXX_JSXM())
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
	 * @FunctionName: getTeacherList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1070110>
	 * @author czl
	 * @date 2014-12-10
	 */
	public List<Pojo1070110> getTeacherList_PageData(Pojo1070110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1070110> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.JSXX_JSID,");// 教师ID
			sql.append("        A.JSXX_JSBM,");// 教师编码
			sql.append("        A.JSXX_JSXM,");// 教师姓名
			sql.append("        A.JSXX_SFZH,");// 身份证号
			sql.append("        A.JSXX_XB,");
			sql.append("        CASE WHEN A.JSXX_XB = 1 THEN '男' WHEN A.JSXX_XB = '2' THEN '女' ELSE '' END AS JSXX_XBMC,");
			sql.append("        A.JSXX_CSRQ,");// 出生日期
			sql.append("        A.JSXX_LXFS,");// 联系方式
			sql.append("        A.JSXX_ZZ,");// 住址
			sql.append("        A.JSXX_XL,");// 学历
			sql.append("        A.JSXX_BYXX,");// 毕业学校
			sql.append("        A.JSXX_BYNF,");// 毕业年份
			sql.append("        A.JSXX_GRJJ,");// 个人简介
			sql.append("        A.JSXX_ZY,");// 专业
			sql.append("        A.JSXX_NJ,");// 哪届
			sql.append("        A.JSXX_JNJY,");// 几年经验
			sql.append("        A.JSXX_JSZG,");// 教师资格
			sql.append("        B.JSZG_ZGMC AS JSXX_ZGMC,");// 教师资格
			sql.append("        A.JSXX_SCLY");// 擅长领域
			sql.append("   FROM JSXX A LEFT JOIN JSZG B ON A.JSXX_JSZG = B.JSZG_ZGID");
			sql.append("  WHERE   A.JSXX_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(beanIn.getJSXX_JSXM())) {		
				sql.append("  AND A.JSXX_JSXM like '%").append(beanIn.getJSXX_JSXM())
					.append("%'");
			}
			sql.append(" ORDER BY ");
			sql.append("        A.JSXX_CJSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1070110>> rs = new BeanListHandler<Pojo1070110>(
					Pojo1070110.class);
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
	 * @FunctionName: checkUserexist
	 * @Description: 验证用户是否已经注册
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-01-14
	 */
	public int checkUserexist(String strSJHM) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(YHXX_YHID)");
			sql.append("   FROM YHXX A ");
			sql.append("  WHERE 1 = 1 AND YHXX_SCBZ = '0' ");
			if (MyStringUtils.isNotBlank(strSJHM)) {	
				sql.append("  AND A.YHXX_YHID = '").append(strSJHM)
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
	 * @FunctionName: insertTeacher
	 * @Description: 新增教师信息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-10
	 */
	public int insertTeacher(Pojo1070110 beanIn,String kcmcs,String qymcs) throws Exception {
		int result = 0;
		int resultjs = 0;
		int resultyh = 0;
		int resulttp = 0;
		String sysdate = getSystemdate();	
		try {
			db.openConnection();
			db.beginTran();
			//插入教师信息表
			StringBuffer strbuf = new StringBuffer();		
			String jsid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			strbuf.append(" INSERT INTO ");
			strbuf.append("     JSXX ");
			strbuf.append(" ( ");
			strbuf.append("     JSXX_JSID, ");// 教师ID
			strbuf.append("     JSXX_JSBM, ");// 教师编码
			strbuf.append("     JSXX_JSXM, ");// 教师姓名
			strbuf.append("     JSXX_SFZH, ");// 身份证号
			strbuf.append("     JSXX_XB, ");// 性别
			strbuf.append("     JSXX_CSRQ, ");// 出生日期
			strbuf.append("     JSXX_LXFS, ");// 联系方式
			strbuf.append("     JSXX_BYXX, ");// 毕业学校
			strbuf.append("     JSXX_BYNF, ");// 毕业年份
			strbuf.append("     JSXX_ZZ, ");// 住址
			strbuf.append("     JSXX_XL, ");// 学历
			strbuf.append("     JSXX_GRJJ, ");// 个人简介
			strbuf.append("     JSXX_ZY, ");// 专业
			strbuf.append("     JSXX_NJ, ");// 哪届
			strbuf.append("     JSXX_JNJY, ");// 几年经验
			strbuf.append("     JSXX_JSZG, ");// 教师资格
			strbuf.append("     JSXX_SCLY, ");// 擅长领域
			strbuf.append("     JSXX_CJR, ");// 创建人
			strbuf.append("     JSXX_CJSJ, ");// 创建时间
			strbuf.append("     JSXX_GXR, ");// 更新人
			strbuf.append("     JSXX_GXSJ ");// 修改时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '" + jsid + "', ");
			strbuf.append("     '" + beanIn.getJSXX_LXFS() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_JSXM() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_SFZH() + "', ");
			if(MyStringUtils.isNotBlank(beanIn.getJSXX_XB())){
				strbuf.append("     '" + beanIn.getJSXX_XB() + "', ");
			}else{
				strbuf.append("     '', ");
			}	
			strbuf.append("     '" + beanIn.getJSXX_CSRQ() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_LXFS() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_BYXX() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_BYNF() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_ZZ() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_XL() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_GRJJ() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_ZY() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_NJ() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_JNJY() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_JSZG() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_SCLY() + "', ");
			strbuf.append("     '" + beanIn.getJSXX_CJR() + "', ");
			strbuf.append("     '" + sysdate + "', ");
			strbuf.append("     '" + beanIn.getJSXX_GXR() + "', ");
			strbuf.append("     '" + sysdate + "' ");
			strbuf.append(" ) ");
			resultjs = db.executeSQL(strbuf);
			
			//插入用户信息表
			StringBuffer strbufYH = new StringBuffer();		
			strbufYH.append(" INSERT INTO ");
			strbufYH.append("     YHXX ");
			strbufYH.append(" ( ");
			strbufYH.append("     YHXX_UUID, ");// UUID
			strbufYH.append("     YHXX_YHID, ");// 用户ID
			strbufYH.append("     YHXX_YHMC, ");// 用户名称
			strbufYH.append("     YHXX_YHMM, ");// 用户密码
			strbufYH.append("     YHXX_JSID, ");// 角色ID
			strbufYH.append("     YHXX_CJR, ");// 创建人
			strbufYH.append("     YHXX_CJSJ, ");// 创建时间
			strbufYH.append("     YHXX_GXR, ");// 更新人
			strbufYH.append("     YHXX_GXSJ ");// 修改时间
			strbufYH.append(" ) ");
			strbufYH.append(" VALUES ");
			strbufYH.append(" ( ");
			strbufYH.append("     '" + jsid + "', ");
			strbufYH.append("     '" + beanIn.getJSXX_LXFS() + "', ");
			strbufYH.append("     '" + beanIn.getJSXX_JSXM() + "', ");
			strbufYH.append("     '000000', ");
			strbufYH.append("     '104', ");
			strbufYH.append("     '" + beanIn.getJSXX_CJR() + "', ");
			strbufYH.append("     '" + sysdate + "', ");
			strbufYH.append("     '" + beanIn.getJSXX_GXR() + "', ");
			strbufYH.append("     '" + sysdate + "' ");
			strbufYH.append(" ) ");
			resultyh = db.executeSQL(strbufYH);		
			if(MyStringUtils.isNotBlank(beanIn.getSFZ())||MyStringUtils.isNotBlank(beanIn.getZGZ())){
				//插入图片
				StringBuffer strbufTP = new StringBuffer();	
				String jstpid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
				strbufTP.append(" INSERT INTO ");
				strbufTP.append("     JSTP ");
				strbufTP.append(" ( ");
				strbufTP.append("     JSTP_JSTPID, ");// 教师图片ID
				strbufTP.append("     JSTP_JSID, ");// 教师ID
				strbufTP.append("     JSTP_SFZ, ");// 身份证
				strbufTP.append("     JSTP_ZGZ, ");// 资格证
				strbufTP.append("     JSTP_CJR, ");// 创建人
				strbufTP.append("     JSTP_CJSJ, ");// 创建时间
				strbufTP.append("     JSTP_GXR, ");// 更新人
				strbufTP.append("     JSTP_GXSJ ");// 修改时间
				strbufTP.append(" ) ");
				strbufTP.append(" VALUES ");
				strbufTP.append(" ( ");
				strbufTP.append("     '" + jstpid + "', ");
				strbufTP.append("     '" + jsid + "', ");
				strbufTP.append("     '" + beanIn.getSFZ() + "', ");
				strbufTP.append("     '" + beanIn.getZGZ() + "', ");
				strbufTP.append("     '" + beanIn.getJSXX_CJR() + "', ");
				strbufTP.append("     '" + sysdate + "', ");
				strbufTP.append("     '" + beanIn.getJSXX_GXR() + "', ");
				strbufTP.append("     '" + sysdate + "' ");
				strbufTP.append(" ) ");
				resulttp = db.executeSQL(strbufTP);		
			}else{
				resulttp = 1;
			}
			if(!kcmcs.equals("")){
				String[] kcmc = kcmcs.split(",");
				for(int i = 0; i < kcmc.length; i++) {
					//添加教师科目表
					String jskmid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
					StringBuffer strbufup = new StringBuffer();		
					strbufup.append(" INSERT INTO ");
					strbufup.append("     JSKM ");
					strbufup.append(" ( ");
					strbufup.append("     JSKM_JSKMID, ");// 教师科目ID
					strbufup.append("     JSKM_JSID, ");// 教师ID
					strbufup.append("     JSKM_KCMC, ");// 课程名称
					strbufup.append("     JSKM_CJR, ");// 创建人
					strbufup.append("     JSKM_CJSJ, ");// 创建时间	
					strbufup.append("     JSKM_GXR, ");// 更新人
					strbufup.append("     JSKM_GXSJ ");// 更新时间
					strbufup.append(" ) ");
					strbufup.append(" VALUES ");
					strbufup.append(" ( ");
					strbufup.append("     '" + jskmid + "', ");
					strbufup.append("     '" + jsid + "', ");
					strbufup.append("     '" + kcmc[i] + "', ");
					strbufup.append("     '" + beanIn.getJSXX_CJR() + "', ");
					strbufup.append("     '" + sysdate + "', ");
					strbufup.append("     '" + beanIn.getJSXX_GXR() + "', ");
					strbufup.append("     '" + sysdate + "' ");
					strbufup.append(" ) ");
					db.executeSQL(strbufup);
				}	
			}
			if(!qymcs.equals("")){
				String[] qymc = qymcs.split(",");
				for(int i = 0; i < qymc.length; i++) {
					//添加教师区域表
					String jsqyid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
					StringBuffer strbufup = new StringBuffer();		
					strbufup.append(" INSERT INTO ");
					strbufup.append("     JSQY ");
					strbufup.append(" ( ");
					strbufup.append("     JSQY_JSQYID, ");// 教师区域ID
					strbufup.append("     JSQY_JSID, ");// 教师ID
					strbufup.append("     JSQY_QYID, ");// 区域ID
					strbufup.append("     JSQY_CJR, ");// 创建人
					strbufup.append("     JSQY_CJSJ, ");// 创建时间	
					strbufup.append("     JSQY_GXR, ");// 更新人
					strbufup.append("     JSQY_GXSJ ");// 更新时间
					strbufup.append(" ) ");
					strbufup.append(" VALUES ");
					strbufup.append(" ( ");
					strbufup.append("     '" + jsqyid + "', ");
					strbufup.append("     '" + jsid + "', ");
					strbufup.append("     '" + qymc[i] + "', ");
					strbufup.append("     '" + beanIn.getJSXX_CJR() + "', ");
					strbufup.append("     '" + sysdate + "', ");
					strbufup.append("     '" + beanIn.getJSXX_GXR() + "', ");
					strbufup.append("     '" + sysdate + "' ");
					strbufup.append(" ) ");
					db.executeSQL(strbufup);
				}	
			}
			if(resultjs>0&&resultyh>0&&resulttp>0){
				db.commit();
				result = 1;
			}else{
				db.rollback();
				result = 0;
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
	 * @FunctionName: updateTeacher
	 * @Description: 修改教师信息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-10
	 */
	public int updateTeacher(Pojo1070110 beanIn,String kcmcs,String qymcs) throws Exception {
		int result = 0;
		int resultjs = 0;
		int resultyh = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			db.beginTran();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     JSXX ");
			strbuf.append(" SET ");
			strbuf.append("     JSXX_JSXM='").append(beanIn.getJSXX_JSXM()).append("',");// 教师姓名
			strbuf.append("     JSXX_SFZH='").append(beanIn.getJSXX_SFZH()).append("',");// 身份证号
			if(MyStringUtils.isNotBlank(beanIn.getJSXX_XB())){
				strbuf.append("     JSXX_XB='").append(beanIn.getJSXX_XB()).append("',");// 性别
			}else{
				strbuf.append("     JSXX_XB='").append("").append("',");// 性别
			}
			strbuf.append("     JSXX_CSRQ='").append(beanIn.getJSXX_CSRQ()).append("',");// 出生日期
			strbuf.append("     JSXX_LXFS='").append(beanIn.getJSXX_LXFS()).append("',");// 联系方式
			strbuf.append("     JSXX_BYXX='").append(beanIn.getJSXX_BYXX()).append("',");// 毕业学校
			strbuf.append("     JSXX_BYNF='").append(beanIn.getJSXX_BYNF()).append("',");// 毕业年份
			strbuf.append("     JSXX_ZZ='").append(beanIn.getJSXX_ZZ()).append("',");// 住址
			strbuf.append("     JSXX_XL='").append(beanIn.getJSXX_XL()).append("',");// 学历
			strbuf.append("     JSXX_GRJJ='").append(beanIn.getJSXX_GRJJ()).append("',");// 个人简介
			strbuf.append("     JSXX_ZY='").append(beanIn.getJSXX_ZY()).append("',");// 专业
			strbuf.append("     JSXX_NJ='").append(beanIn.getJSXX_NJ()).append("',");// 哪届
			strbuf.append("     JSXX_JNJY='").append(beanIn.getJSXX_JNJY()).append("',");// 几年经验
			strbuf.append("     JSXX_JSZG='").append(beanIn.getJSXX_JSZG()).append("',");// 教师资格
			strbuf.append("     JSXX_SCLY='").append(beanIn.getJSXX_SCLY()).append("',");// 擅长领域
			strbuf.append("     JSXX_GXR='").append(beanIn.getJSXX_GXR()).append("',");// 修改人
			strbuf.append("     JSXX_GXSJ='" + sysdate + "'");// 修改时间
			strbuf.append(" WHERE 1 = 1 AND JSXX_SCBZ = '0'");
			strbuf.append("  AND   JSXX_JSID='").append(beanIn.getJSXX_JSID()).append("'");// 教师ID
			resultjs = db.executeSQL(strbuf);
			//更新用户表
			StringBuffer strbufyh = new StringBuffer();
			strbufyh.append(" UPDATE ");
			strbufyh.append("     YHXX ");
			strbufyh.append(" SET ");
			strbufyh.append("     YHXX_YHMC='").append(beanIn.getJSXX_JSXM()).append("',");//用户名称
			strbufyh.append("     YHXX_GXR='").append(beanIn.getJSXX_GXR()).append("',");//更新人
			strbufyh.append("     YHXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbufyh.append(" WHERE ");
			strbufyh.append("     YHXX_UUID='").append(beanIn.getJSXX_JSID()).append("'");//用户ID
			resultyh = db.executeSQL(strbufyh);
			if(MyStringUtils.isNotBlank(beanIn.getSFZ())||MyStringUtils.isNotBlank(beanIn.getZGZ())){
				int temp = getJSTP(beanIn.getJSXX_JSID());
				if(temp<=0){
					//插入图片
					StringBuffer strbufTP = new StringBuffer();	
					String jstpid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
					strbufTP.append(" INSERT INTO ");
					strbufTP.append("     JSTP ");
					strbufTP.append(" ( ");
					strbufTP.append("     JSTP_JSTPID, ");// 教师图片ID
					strbufTP.append("     JSTP_JSID, ");// 教师ID
					strbufTP.append("     JSTP_SFZ, ");// 身份证
					strbufTP.append("     JSTP_ZGZ, ");// 资格证
					strbufTP.append("     JSTP_CJR, ");// 创建人
					strbufTP.append("     JSTP_CJSJ, ");// 创建时间
					strbufTP.append("     JSTP_GXR, ");// 更新人
					strbufTP.append("     JSTP_GXSJ ");// 修改时间
					strbufTP.append(" ) ");
					strbufTP.append(" VALUES ");
					strbufTP.append(" ( ");
					strbufTP.append("     '" + jstpid + "', ");
					strbufTP.append("     '" + beanIn.getJSXX_JSID() + "', ");
					strbufTP.append("     '" + beanIn.getSFZ() + "', ");
					strbufTP.append("     '" + beanIn.getZGZ() + "', ");
					strbufTP.append("     '" + beanIn.getJSXX_CJR() + "', ");
					strbufTP.append("     '" + sysdate + "', ");
					strbufTP.append("     '" + beanIn.getJSXX_GXR() + "', ");
					strbufTP.append("     '" + sysdate + "' ");
					strbufTP.append(" ) ");
					db.executeSQL(strbufTP);		
				}else{
					//修改图片
					StringBuffer strbufTP = new StringBuffer();
					strbufTP.append(" UPDATE ");
					strbufTP.append("     JSTP ");
					strbufTP.append(" SET ");
					strbufTP.append("     JSTP_SFZ='").append(beanIn.getSFZ()).append("',");// 身份证
					strbufTP.append("     JSTP_ZGZ='").append(beanIn.getZGZ()).append("',");// 资格证
					strbufTP.append("     JSTP_GXR='").append(beanIn.getJSXX_GXR()).append("',");// 修改人
					strbufTP.append("     JSTP_GXSJ='" + sysdate + "'");// 修改时间
					strbufTP.append(" WHERE 1 = 1 ");
					strbufTP.append("  AND   JSTP_JSID='").append(beanIn.getJSXX_JSID()).append("'");// 教师ID
					db.executeSQL(strbufTP);
				}				
			}	
			//删除教师课程数据
			StringBuffer strbufdel = new StringBuffer();
			strbufdel.append(" DELETE  JSKM ");
			strbufdel.append("  WHERE JSKM_JSID='").append(beanIn.getJSXX_JSID()).append("'");// 教师ID
			db.executeSQL(strbufdel);	
			if(!kcmcs.equals("")){
				String[] kcmc = kcmcs.split(",");
				for(int i = 0; i < kcmc.length; i++) {
					//修改教师科目表
					String jskmid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
					StringBuffer strbufup = new StringBuffer();		
					strbufup.append(" INSERT INTO ");
					strbufup.append("     JSKM ");
					strbufup.append(" ( ");
					strbufup.append("     JSKM_JSKMID, ");// 教师科目ID
					strbufup.append("     JSKM_JSID, ");// 教师ID
					strbufup.append("     JSKM_KCMC, ");// 课程名称
					strbufup.append("     JSKM_CJR, ");// 创建人
					strbufup.append("     JSKM_CJSJ, ");// 创建时间	
					strbufup.append("     JSKM_GXR, ");// 更新人
					strbufup.append("     JSKM_GXSJ ");// 更新时间
					strbufup.append(" ) ");
					strbufup.append(" VALUES ");
					strbufup.append(" ( ");
					strbufup.append("     '" + jskmid + "', ");
					strbufup.append("     '" + beanIn.getJSXX_JSID() + "', ");
					strbufup.append("     '" + kcmc[i] + "', ");
					strbufup.append("     '" + beanIn.getJSXX_CJR() + "', ");
					strbufup.append("     '" + sysdate + "', ");
					strbufup.append("     '" + beanIn.getJSXX_GXR() + "', ");
					strbufup.append("     '" + sysdate + "' ");
					strbufup.append(" ) ");
					db.executeSQL(strbufup);
				}
			}	
			//删除教师区域数据
			StringBuffer strbufdel1 = new StringBuffer();
			strbufdel1.append(" DELETE  JSQY ");
			strbufdel1.append("  WHERE JSQY_JSID='").append(beanIn.getJSXX_JSID()).append("'");// 教师ID
			db.executeSQL(strbufdel1);	
			if(!qymcs.equals("")){
				String[] qymc = qymcs.split(",");
				for(int i = 0; i < qymc.length; i++) {
					//修改教师科目表
					String jsqyid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
					StringBuffer strbufup = new StringBuffer();		
					strbufup.append(" INSERT INTO ");
					strbufup.append("     JSQY ");
					strbufup.append(" ( ");
					strbufup.append("     JSQY_JSQYID, ");// 教师区域ID
					strbufup.append("     JSQY_JSID, ");// 教师ID
					strbufup.append("     JSQY_QYID, ");// 区域ID
					strbufup.append("     JSQY_CJR, ");// 创建人
					strbufup.append("     JSQY_CJSJ, ");// 创建时间	
					strbufup.append("     JSQY_GXR, ");// 更新人
					strbufup.append("     JSQY_GXSJ ");// 更新时间
					strbufup.append(" ) ");
					strbufup.append(" VALUES ");
					strbufup.append(" ( ");
					strbufup.append("     '" + jsqyid + "', ");
					strbufup.append("     '" + beanIn.getJSXX_JSID() + "', ");
					strbufup.append("     '" + qymc[i] + "', ");
					strbufup.append("     '" + beanIn.getJSXX_CJR() + "', ");
					strbufup.append("     '" + sysdate + "', ");
					strbufup.append("     '" + beanIn.getJSXX_GXR() + "', ");
					strbufup.append("     '" + sysdate + "' ");
					strbufup.append(" ) ");
					db.executeSQL(strbufup);
				}
			}
			if(resultjs>0&&resultyh>0){
				result = 1;
				db.commit();
			}else{
				result = 0;
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
	 * @FunctionName: getJSTP
	 * @Description: 查询教师图片
	 * @param beanIn
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2015-02-12
	 */
	public int getJSTP(String jsid) throws Exception {
		int result = 0;
		try {	
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT COUNT(JSTP_JSTPID)");
			sql.append("   FROM JSTP A ");
			sql.append("  WHERE 1 = 1 ");
			if (MyStringUtils.isNotBlank(jsid)) {	
				sql.append("  AND A.JSTP_JSID = '").append(jsid)
					.append("'");	
			}
			result = db.queryForInteger(sql);			
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			
		}
		return result;
	}
	/**
	 * @FunctionName: deleteTeacher
	 * @Description: 删除教师信息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-10
	 */
	public int deleteTeacher(Pojo1070110 beanIn) throws Exception {
		int result = 0;
		int resultjs = 0;
		int resultyh = 0;
		try {
			db.openConnection();
			db.beginTran();
			//删除教师信息表
			StringBuffer strbufJX = new StringBuffer();
			strbufJX.append(" UPDATE ");
			strbufJX.append("     JSXX ");
			strbufJX.append(" SET ");
			strbufJX.append("     JSXX_SCBZ='1',");// 删除标志
			strbufJX.append("     JSXX_GXR='").append(beanIn.getJSXX_GXR()).append("',");//更新人
			strbufJX.append("     JSXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbufJX.append(" WHERE 1 = 1 AND JSXX_JSID='").append(beanIn.getJSXX_JSID())
					.append("'");
			resultjs = db.executeSQL(strbufJX);
			//删除YHXX表数据
			StringBuffer strbufYH = new StringBuffer();
			strbufYH.append(" UPDATE ");
			strbufYH.append("     YHXX ");
			strbufYH.append(" SET ");
			strbufYH.append("     YHXX_SCBZ='1',");//删除标志（0：未删除，1：已删除）
			strbufYH.append("     YHXX_GXR='").append(beanIn.getJSXX_GXR()).append("',");//更新人
			strbufYH.append("     YHXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbufYH.append(" WHERE ");
			strbufYH.append("     YHXX_UUID='").append(beanIn.getJSXX_JSID()).append("'");//UUID
			resultyh = db.executeSQL(strbufYH);
			if(resultjs>0&&resultyh>0){
				result = 1;
				db.commit();
			}else{
				result = 0;
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
	 * @FunctionName: getAllKmList
	 * @Description: 获取所有科目列表
	 * @param Pojo1070111 beanIn
	 * @throws Exception
	 * @return List<Pojo1070111>
	 * @author czl
	 * @date 2014-12-11
	 */
	public List<Pojo1070111> getAllKmList() throws Exception {
		List<Pojo1070111> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT A.KCXX_KCID,");//课程ID
			sql.append("  	  A.KCXX_KCMC");//课程名称
			sql.append("  FROM KCXX A");
			sql.append("  WHERE A.KCXX_SCBZ = 0");
			sql.append("  ORDER BY A.KCXX_KCJD");

			ResultSetHandler<List<Pojo1070111>> rsh = new BeanListHandler<Pojo1070111>(
					Pojo1070111.class);
			result = db.queryForBeanListHandler(sql, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
	/**
	 * @FunctionName: getOwnKmList
	 * @Description: 获取擅长科目列表
	 * @param Pojo1070111 beanIn
	 * @throws Exception
	 * @return List<Pojo1070112>
	 * @author czl
	 * @date 2014-12-11
	 */
	public List<Pojo1070112> getOwnKmList(String strJSID) throws Exception {
		List<Pojo1070112> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT A.JSKM_JSKMID,");//教师科目ID
			sql.append("  	  A.JSKM_KCMC");//课程名称
			sql.append("  FROM JSKM A");
			sql.append("  WHERE A.JSKM_SCBZ = 0 AND A.JSKM_JSID = '").append(strJSID).append("'");

			ResultSetHandler<List<Pojo1070112>> rsh = new BeanListHandler<Pojo1070112>(
					Pojo1070112.class);
			result = db.queryForBeanListHandler(sql, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
	/**
	 * @FunctionName: getOwnKmListinfo
	 * @Description: 获取擅长科目列表
	 * @param Pojo1070111 beanIn
	 * @throws Exception
	 * @return List<Pojo1070112>
	 * @author czl
	 * @date 2014-12-25
	 */
	public List<Pojo1070112> getOwnKmListinfo(String strJSID) throws Exception {
		List<Pojo1070112> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT A.JSKM_JSKMID,");//教师科目ID
			sql.append("  	  B.KCXX_KCMC AS JSKM_KCMC");//课程名称
			sql.append("  FROM JSKM A,KCXX B");
			sql.append("  WHERE A.JSKM_SCBZ = 0 AND A.JSKM_KCMC = B.KCXX_KCID AND A.JSKM_JSID = '").append(strJSID).append("'");

			ResultSetHandler<List<Pojo1070112>> rsh = new BeanListHandler<Pojo1070112>(
					Pojo1070112.class);
			result = db.queryForBeanListHandler(sql, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
	/**
	 * @FunctionName: getAllQyList
	 * @Description: 获取所有区域列表
	 * @param Pojo1070113 beanIn
	 * @throws Exception
	 * @return List<Pojo1070113>
	 * @author czl
	 * @date 2014-12-29
	 */
	public List<Pojo1070113> getAllQyList() throws Exception {
		List<Pojo1070113> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT A.XZQY_QYID,");//区域ID
			sql.append("  	  A.XZQY_QYMC");//区域名称
			sql.append("  FROM XZQY A");
			sql.append("  WHERE A.XZQY_SCBZ = 0 AND A.XZQY_QYJB='3'");

			ResultSetHandler<List<Pojo1070113>> rsh = new BeanListHandler<Pojo1070113>(
					Pojo1070113.class);
			result = db.queryForBeanListHandler(sql, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
	/**
	 * @FunctionName: getOwnQyList
	 * @Description: 获取授课区域列表
	 * @param Pojo1070114 beanIn
	 * @throws Exception
	 * @return List<Pojo1070114>
	 * @author czl
	 * @date 2014-12-29
	 */
	public List<Pojo1070114> getOwnQyList(String strJSID) throws Exception {
		List<Pojo1070114> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT A.JSQY_JSQYID,");//教师区域ID
			sql.append("  	  A.JSQY_QYID");//区域ID
			sql.append("  FROM JSQY A");
			sql.append("  WHERE A.JSQY_SCBZ = 0 AND A.JSQY_JSID = '").append(strJSID).append("'");

			ResultSetHandler<List<Pojo1070114>> rsh = new BeanListHandler<Pojo1070114>(
					Pojo1070114.class);
			result = db.queryForBeanListHandler(sql, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
	/**
	 * @FunctionName: getJstp
	 * @Description: 获得教师图片
	 * @param beanIn
	 * @throws Exception
	 * @return Pojo1110150
	 * @author czl
	 * @date 2015-02-05
	 */
	public Pojo1070115 getJstp(String jsid) throws Exception {
		Pojo1070115 result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.JSTP_JSTPID, ");//教师图片ID
			sql.append("     A.JSTP_JSID, ");//教师ID
			sql.append("     A.JSTP_SFZ, ");//身份证
			sql.append("     A.JSTP_ZGZ ");//资格证
			sql.append("     FROM ");
			sql.append("     JSTP A WHERE 1=1");
			if (MyStringUtils.isNotBlank(jsid)) {
				sql.append(" AND A.JSTP_JSID ='").append(jsid).append("'");	
						
			}
			ResultSetHandler<Pojo1070115> rsh = new BeanHandler<Pojo1070115>(
					Pojo1070115.class);
			result = db.queryForBeanHandler(sql, rsh);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
}
