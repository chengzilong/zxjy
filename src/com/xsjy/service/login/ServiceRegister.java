package com.xsjy.service.login;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;

public class ServiceRegister extends BaseService {

	private DBManager db = null;

	public ServiceRegister() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2014-12-09
	 */
	public String getSystemdate() throws Exception{
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//可以方便地修改日期格式
		String date = dateFormat.format( now );
		return date;
	}
	/**
	 * @FunctionName: checkUserexist
	 * @Description: 验证用户是否已经注册
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-7-11
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
	 * @FunctionName: registUser
	 * @Description: 用户注册
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2014-12-09
	 */
	public int registUser(String strSJHM,String strNC,String strDLMM,String strYHQF,String code) throws Exception {
		int result = 0;
		String sysdate = getSystemdate();
		try {
			db.openConnection();
			db.beginTran();
			if(strYHQF.equals("0")){
				StringBuffer strbuf = new StringBuffer();
				String jsid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
				strbuf.append(" INSERT INTO ");
				strbuf.append("     JSXX ");
				strbuf.append(" ( ");
				strbuf.append("     JSXX_JSID, ");// 教师ID
				strbuf.append("     JSXX_JSBM, ");// 教师编码
				strbuf.append("     JSXX_JSXM, ");// 教师姓名
				strbuf.append("     JSXX_LXFS, ");// 联系方式
				strbuf.append("     JSXX_CJR, ");// 创建人
				strbuf.append("     JSXX_CJSJ, ");// 创建时间
				strbuf.append("     JSXX_GXR, ");// 更新人
				strbuf.append("     JSXX_GXSJ ");// 修改时间
				strbuf.append(" ) ");
				strbuf.append(" VALUES ");
				strbuf.append(" ( ");
				strbuf.append("     '" + jsid + "', ");
				strbuf.append("     '" + strSJHM + "', ");
				strbuf.append("     '" + strNC + "', ");
				strbuf.append("     '" + strSJHM + "', ");
				strbuf.append("     '" + strSJHM + "', ");
				strbuf.append("     '" + sysdate + "', ");
				strbuf.append("     '" + strSJHM + "', ");
				strbuf.append("     '" + sysdate + "' ");
				strbuf.append(" ) ");
				db.executeSQL(strbuf);

				StringBuffer strbufYH = new StringBuffer();
				strbufYH.append(" INSERT INTO ");
				strbufYH.append("     YHXX ");
				strbufYH.append(" ( ");
				strbufYH.append("     YHXX_UUID, ");// UUID
				strbufYH.append("     YHXX_YHID, ");// 用户ID
				strbufYH.append("     YHXX_YHMC, ");// 用户名称
				strbufYH.append("     YHXX_YHMM, ");// 用户密码
				strbufYH.append("     YHXX_JSID, ");// 角色ID
				strbufYH.append("     YHXX_CODE, ");// 激活码
				strbufYH.append("     YHXX_CJR, ");// 创建人
				strbufYH.append("     YHXX_CJSJ, ");// 创建时间
				strbufYH.append("     YHXX_GXR, ");// 更新人
				strbufYH.append("     YHXX_GXSJ ");// 修改时间
				strbufYH.append(" ) ");
				strbufYH.append(" VALUES ");
				strbufYH.append(" ( ");
				strbufYH.append("     '" + jsid + "', ");
				strbufYH.append("     '" + strSJHM + "', ");
				strbufYH.append("     '" + strNC + "', ");
				strbufYH.append("     '" + strDLMM + "', ");
				strbufYH.append("     '104', ");
				strbufYH.append("     '" + code + "', ");
				strbufYH.append("     '" + strSJHM + "', ");
				strbufYH.append("     '" + sysdate + "', ");
				strbufYH.append("     '" + strSJHM + "', ");
				strbufYH.append("     '" + sysdate + "' ");
				strbufYH.append(" ) ");
				result = db.executeSQL(strbufYH);
			}else{
				StringBuffer strbuf = new StringBuffer();
				String xsid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
				strbuf.append(" INSERT INTO ");
				strbuf.append("     XSXX ");
				strbuf.append(" ( ");
				strbuf.append("     XSXX_XSID, ");// 学生ID
				strbuf.append("     XSXX_XSBM, ");// 学生编码
				strbuf.append("     XSXX_XSXM, ");// 学生姓名
				strbuf.append("     XSXX_LXFS, ");// 联系方式
				strbuf.append("     XSXX_CJR, ");// 创建人
				strbuf.append("     XSXX_CJSJ, ");// 创建时间
				strbuf.append("     XSXX_GXR, ");// 更新人
				strbuf.append("     XSXX_GXSJ ");// 修改时间
				strbuf.append(" ) ");
				strbuf.append(" VALUES ");
				strbuf.append(" ( ");
				strbuf.append("     '" + xsid + "', ");
				strbuf.append("     '" + strSJHM + "', ");
				strbuf.append("     '" + strNC + "', ");
				strbuf.append("     '" + strSJHM + "', ");
				strbuf.append("     '" + strSJHM + "', ");
				strbuf.append("     '" + sysdate + "', ");
				strbuf.append("     '" + strSJHM + "', ");
				strbuf.append("     '" + sysdate + "' ");
				strbuf.append(" ) ");
				db.executeSQL(strbuf);

				StringBuffer strbufYH = new StringBuffer();
				strbufYH.append(" INSERT INTO ");
				strbufYH.append("     YHXX ");
				strbufYH.append(" ( ");
				strbufYH.append("     YHXX_UUID, ");// UUID
				strbufYH.append("     YHXX_YHID, ");// 用户ID
				strbufYH.append("     YHXX_YHMC, ");// 用户名称
				strbufYH.append("     YHXX_YHMM, ");// 用户密码
				strbufYH.append("     YHXX_JSID, ");// 角色ID
				strbufYH.append("     YHXX_CODE, ");// 激活码
				strbufYH.append("     YHXX_CJR, ");// 创建人
				strbufYH.append("     YHXX_CJSJ, ");// 创建时间
				strbufYH.append("     YHXX_GXR, ");// 更新人
				strbufYH.append("     YHXX_GXSJ ");// 修改时间
				strbufYH.append(" ) ");
				strbufYH.append(" VALUES ");
				strbufYH.append(" ( ");
				strbufYH.append("     '" + xsid + "', ");
				strbufYH.append("     '" + strSJHM + "', ");
				strbufYH.append("     '" + strNC + "', ");
				strbufYH.append("     '" + strDLMM + "', ");
				strbufYH.append("     '106', ");
				strbufYH.append("     '" + code + "', ");
				strbufYH.append("     '" + strSJHM + "', ");
				strbufYH.append("     '" + sysdate + "', ");
				strbufYH.append("     '" + strSJHM + "', ");
				strbufYH.append("     '" + sysdate + "' ");
				strbufYH.append(" ) ");
				result = db.executeSQL(strbufYH);
			}
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
     * @FunctionName: getActive
     * @Description: 验证用户是否激活
     * @param beanIn
     * @throws Exception
     * @return int
     * @author czl
     * @date 2017-7-12
     */
    public int getActive(String code) throws Exception {
        int result = 0;
        String sysdate = getSystemdate();
        try {
            db.openConnection();
            db.beginTran();
            int count = 0;
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT COUNT(YHXX_YHID)");
            sql.append("   FROM YHXX A ");
            sql.append("  WHERE 1 = 1 AND YHXX_SCBZ = '0' AND YHXX_SDZT = '1'");
            if (MyStringUtils.isNotBlank(code)) {
                sql.append("  AND A.YHXX_CODE = '").append(code)
                    .append("'");
            }
            count = db.queryForInteger(sql);
            if (count == 0) {
                int count_s = 0;
                StringBuffer sql_s = new StringBuffer();
                sql_s.append(" SELECT COUNT(YHXX_YHID)");
                sql_s.append("   FROM YHXX A ");
                sql_s.append("  WHERE 1 = 1 AND YHXX_SCBZ = '0' AND YHXX_SDZT = '0'");
                if (MyStringUtils.isNotBlank(code)) {
                    sql_s.append("  AND A.YHXX_CODE = '").append(code)
                        .append("'");
                }
                count_s = db.queryForInteger(sql_s);
                if (count_s == 1) {
                    result = 1;//已经注册过了
                } else {
                    result = 0;//没有对应的code
                }
            } else {
                StringBuffer sql_u = new StringBuffer();
                sql_u.append(" UPDATE ");
                sql_u.append("     YHXX ");
                sql_u.append(" SET ");
                sql_u.append("     YHXX_SDZT='0',");// 发布主题
                sql_u.append("     YHXX_GXSJ='" + sysdate + "'");// 更新时间
                sql_u.append(" WHERE 1 = 1 AND YHXX_SCBZ = '0'");
                sql_u.append("  AND   YHXX_CODE='").append(code).append("'");// 激活码
                db.executeSQL(sql_u);
                result = 2;//激活成功
            }
            db.commit();
        } catch (Exception e) {
            result = -1;
            db.rollback();
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
        return result;
    }
}
