package com.xsjy.service.service1070000;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_1070000.Pojo1070130;

public class Service1070130 extends BaseService {

	private DBManager db = null;

	public Service1070130() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getSystemdate
	 * @Description: 获得系统时间
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2014-12-13
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
	public String getDatenext() throws Exception{
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");//可以方便地修改日期格式
		Calendar calendar = Calendar.getInstance();//日历对象
		calendar.setTime(now);//设置当前日期
		calendar.add(Calendar.MONTH, +1);//月份加一
		return dateFormat.format(calendar.getTime());
	}
	/**
	 * @FunctionName: getTeacherList_TotalCount
	 * @Description: 取得查询结果统计数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-08-01
	 */
	public int getTeacherList_TotalCount(Pojo1070130 beanIn) throws Exception {
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
			if (MyStringUtils.isNotBlank(beanIn.getJSXX_SFRZ())&&!("000").equals(beanIn.getJSXX_SFRZ())) {
				sql.append(" AND A.JSXX_SFRZ = '").append(beanIn.getJSXX_SFRZ())
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
	 * @FunctionName: getTeacherList_PageData
	 * @Description: 取得查询结果
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1070130>
	 * @author czl
	 * @date 2014-12-13
	 */
	public List<Pojo1070130> getTeacherList_PageData(Pojo1070130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1070130> result = null;
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
			sql.append("        CASE WHEN A.JSXX_SFRZ = 1 THEN '已认证'  ELSE '未认证' END AS JSXX_SFRZ,");//是否认证
			sql.append("        A.JSXX_ZY,");// 专业
			sql.append("        A.JSXX_NJ,");// 年级
			sql.append("        CASE WHEN A.JSXX_JNJY='11' THEN '大于10' ELSE JSXX_JNJY END AS JSXX_JNJY,");// 几年经验
			sql.append("        B.JSZG_ZGMC AS JSXX_JSZG,");// 教师资格
			sql.append("        A.JSXX_SCLY,");// 擅长领域
			sql.append("        A.JSXX_XQAH,");// 兴趣爱好
			sql.append("        A.JSXX_GRJJ");// 个人简介
			sql.append("   FROM JSXX A LEFT JOIN JSZG B ON A.JSXX_JSZG = B.JSZG_ZGID");
			sql.append("  WHERE   A.JSXX_SCBZ = '0' ");
			if (MyStringUtils.isNotBlank(beanIn.getJSXX_JSXM())) {
				sql.append("  AND A.JSXX_JSXM like '%").append(beanIn.getJSXX_JSXM())
					.append("%'");
			}
			if (MyStringUtils.isNotBlank(beanIn.getJSXX_SFRZ())&&!("000").equals(beanIn.getJSXX_SFRZ())) {
				sql.append("  AND A.JSXX_SFRZ = '").append(beanIn.getJSXX_SFRZ())
					.append("'");
			}
			sql.append(" ORDER BY ");
			sql.append("        A.JSXX_SFRZ ASC,A.JSXX_CJSJ DESC");
			StringBuffer execSql = this.getPageSqL(sql.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1070130>> rs = new BeanListHandler<Pojo1070130>(
					Pojo1070130.class);
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
	 * @FunctionName: identificateTeacher
	 * @Description: 教师认证
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-08-01
	 */
	public int identificateTeacher(Pojo1070130 beanIn,String uuid) throws Exception {
		int result = 0;
		int resultrz = 0;
		int resultjs = 0;
		String date = getDate();
		String nextdate = getDatenext();
		try {
			db.openConnection();
			db.beginTran();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     JSXX ");
			strbuf.append(" SET ");
			strbuf.append("     JSXX_SFRZ='1',");// 是否认证
			strbuf.append("     JSXX_GXR='").append(beanIn.getJSXX_GXR()).append("',");// 修改人
			strbuf.append("     JSXX_GXSJ=NOW()");// 修改时间
			strbuf.append(" WHERE 1 = 1 AND JSXX_SCBZ = '0'");
			strbuf.append("  AND   JSXX_JSID='").append(beanIn.getJSXX_JSID()).append("'");// 教师ID
			resultrz = db.executeSQL(strbuf);
			StringBuffer strbufjs = new StringBuffer();
			strbufjs.append(" UPDATE ");
			strbufjs.append("     YHXX ");
			strbufjs.append(" SET ");
			strbufjs.append("     YHXX_JSID='105',");// 是否认证
			strbufjs.append("     YHXX_GXR='").append(beanIn.getJSXX_GXR()).append("',");// 修改人
			strbufjs.append("     YHXX_GXSJ=NOW()");// 修改时间
			strbufjs.append(" WHERE 1 = 1 AND YHXX_SCBZ = '0'");
			strbufjs.append("  AND   YHXX_UUID='").append(beanIn.getJSXX_JSID()).append("'");// 教师ID
			resultjs = db.executeSQL(strbufjs);
			//教师认证后系统自动发布认证通过的消息
			StringBuffer strbufxx = new StringBuffer();
			String xxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			strbufxx.append(" INSERT INTO ");
			strbufxx.append("     XXXX ");
			strbufxx.append(" ( ");
			strbufxx.append("     XXXX_XXID, ");// 消息ID
			strbufxx.append("     XXXX_FBR, ");// 发布人
			strbufxx.append("     XXXX_FBZT, ");// 发布主题
			strbufxx.append("     XXXX_FBNR, ");// 发布内容
			strbufxx.append("     XXXX_FBSJ, ");// 发布时间
			strbufxx.append("     XXXX_YXSJ, ");// 有效时间
			strbufxx.append("     XXXX_CJR, ");// 创建人
			strbufxx.append("     XXXX_CJSJ, ");// 创建时间
			strbufxx.append("     XXXX_GXR, ");// 更新人
			strbufxx.append("     XXXX_GXSJ ");// 修改时间
			strbufxx.append(" ) ");
			strbufxx.append(" VALUES ");
			strbufxx.append(" ( ");
			strbufxx.append("     '" + xxid + "', ");
			strbufxx.append("     '" + uuid + "', ");
			strbufxx.append("     '<系统消息>认证通过', ");
			strbufxx.append("     '恭喜您！您的教师资格认证已通过，请不要随意更新您的个人资料！', ");
			strbufxx.append("     '" + date + "', ");
			strbufxx.append("     '" + nextdate + "', ");
			strbufxx.append("     '" + beanIn.getJSXX_GXR() + "', ");
			strbufxx.append("     NOW(), ");
			strbufxx.append("     '" + beanIn.getJSXX_GXR() + "', ");
			strbufxx.append("     NOW()");
			strbufxx.append(" ) ");
			db.executeSQL(strbufxx);
			//插入消息明细
			String mxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			StringBuffer strbufmx = new StringBuffer();
			strbufmx.append(" INSERT INTO ");
			strbufmx.append("     XXMX ");
			strbufmx.append(" ( ");
			strbufmx.append("     XXMX_MXID, ");//消息明细ID
			strbufmx.append("     XXMX_XXID, ");// 消息ID
			strbufmx.append("     XXMX_JSRID, ");// 接收人ID
			strbufmx.append("     XXMX_JSR, ");// 接收人
			strbufmx.append("     XXMX_CKZT, ");// 查看状态
			strbufmx.append("     XXMX_CJR, ");// 创建人
			strbufmx.append("     XXMX_CJSJ, ");// 创建时间
			strbufmx.append("     XXMX_GXR, ");// 更新人
			strbufmx.append("     XXMX_GXSJ ");// 修改时间
			strbufmx.append(" ) ");
			strbufmx.append(" VALUES ");
			strbufmx.append(" ( ");
			strbufmx.append("     '" + mxid + "', ");
			strbufmx.append("     '" + xxid + "', ");
			strbufmx.append("     '" + beanIn.getJSXX_JSID() + "', ");
			strbufmx.append("     '" + beanIn.getJSXX_JSXM() + "', ");
			strbufmx.append("     '0', ");
			strbufmx.append("     '" + beanIn.getJSXX_GXR() + "', ");
			strbufmx.append("     NOW(), ");
			strbufmx.append("     '" + beanIn.getJSXX_GXR() + "', ");
			strbufmx.append("     NOW()");
			strbufmx.append(" ) ");
			db.executeSQL(strbufmx);
			if(resultrz>0&&resultjs>0){
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
}
