package com.xsjy.service.service1110000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070111;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070112;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070113;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070114;
import com.xsjy.pojo.Custom.pojo_1110000.Pojo1110120;

public class Service1110120 extends BaseService {

	private DBManager db = null;

	public Service1110120() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2015-01-07
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
		String date = dateFormat.format( now );
		return date;
	}
	/**
	 * @FunctionName: getTeacherInfo
	 * @Description: 获取教师信息
	 * @param beanIn
	 * @throws Exception
	 * @return Pojo1110110
	 * @author czl
	 * @date 2017-07-28
	 */
	public Pojo1110120 getTeacherInfo(String yhid) throws Exception {
		Pojo1110120 result = null;
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
			if (MyStringUtils.isNotBlank(yhid)) {
				sql.append(" AND A.JSXX_JSBM ='").append(yhid).append("'");

			}
			ResultSetHandler<Pojo1110120> rsh = new BeanHandler<Pojo1110120>(
					Pojo1110120.class);
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
	 * @FunctionName: updateTeacher
	 * @Description: 修改教师信息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-28
	 */
	public int updateTeacher(Pojo1110120 beanIn,String kcmcs,String qymcs) throws Exception {
		int result = 0;
		try {
			db.openConnection();
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
			strbuf.append("     JSXX_GXSJ=NOW()");// 修改时间
			strbuf.append(" WHERE 1 = 1 AND JSXX_SCBZ = '0'");
			strbuf.append("  AND   JSXX_JSID='").append(beanIn.getJSXX_JSID()).append("'");// 教师ID
			result = db.executeSQL(strbuf);
			//更新用户表
			StringBuffer strbufyh = new StringBuffer();
			strbufyh.append(" UPDATE ");
			strbufyh.append("     YHXX ");
			strbufyh.append(" SET ");
			strbufyh.append("     YHXX_YHMC='").append(beanIn.getJSXX_JSXM()).append("',");//用户名称
			strbufyh.append("     YHXX_GXR='").append(beanIn.getJSXX_GXR()).append("',");//更新人
			strbufyh.append("     YHXX_GXSJ=NOW()");//更新时间
			strbufyh.append(" WHERE ");
			strbufyh.append("     YHXX_UUID='").append(beanIn.getJSXX_JSID()).append("'");//用户ID
			db.executeSQL(strbufyh);
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
					strbufTP.append("     NOW(), ");
					strbufTP.append("     '" + beanIn.getJSXX_GXR() + "', ");
					strbufTP.append("     NOW() ");
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
					strbufTP.append("     JSTP_GXSJ=NOW()");// 修改时间
					strbufTP.append(" WHERE 1 = 1 ");
					strbufTP.append("  AND   JSTP_JSID='").append(beanIn.getJSXX_JSID()).append("'");// 教师ID
					db.executeSQL(strbufTP);
				}
			}
			//删除教师课程数据
			StringBuffer strbufdel = new StringBuffer();
			strbufdel.append(" DELETE FROM JSKM ");
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
					strbufup.append("     NOW(), ");
					strbufup.append("     '" + beanIn.getJSXX_GXR() + "', ");
					strbufup.append("     NOW() ");
					strbufup.append(" ) ");
					db.executeSQL(strbufup);
				}
			}
			//删除教师区域数据
			StringBuffer strbufdel1 = new StringBuffer();
			strbufdel1.append(" DELETE FROM JSQY ");
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
					strbufup.append("     NOW(), ");
					strbufup.append("     '" + beanIn.getJSXX_GXR() + "', ");
					strbufup.append("     NOW() ");
					strbufup.append(" ) ");
					db.executeSQL(strbufup);
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
	 * @FunctionName: getJSTP
	 * @Description: 查询教师图片
	 * @param beanIn
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2017-07-28
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
	 * @FunctionName: getAllKmList
	 * @Description: 获取所有科目列表
	 * @param Pojo1070111 beanIn
	 * @throws Exception
	 * @return List<Pojo1070111>
	 * @author czl
	 * @date 2015-01-07
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
	 * @date 2015-01-07
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
	 * @date 2015-01-07
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
	 * @date 2015-01-07
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
	 * @date 2015-01-07
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
}
