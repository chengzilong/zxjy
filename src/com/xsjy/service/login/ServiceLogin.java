package com.xsjy.service.login;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_MENU;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.common.Pojo_USER_INFO;


public class ServiceLogin {
	private DBManager db = null;

	public ServiceLogin() {
		db = new DBManager();
	}

	/**
	 * @FunctionName: checkUserID
	 * @Description: 验证用户ID
	 * @param UserID
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ljg
	 * @date 2014年7月18日 上午9:25:00
	 */
	public Pojo_YHXX checkUserID(String strUserId) throws Exception {
		Pojo_YHXX result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YHXX_UUID, ");//UUID
			strbuf.append("     A.YHXX_YHID, ");//用户id
			strbuf.append("     A.YHXX_YHMC, ");//用户名
			strbuf.append("     A.YHXX_YHMM, ");//密码
			strbuf.append("     A.YHXX_JSID, ");//角色id
			strbuf.append("     A.YHXX_SDZT, ");//锁定状态(0--正常 1--冻结)
			strbuf.append("     A.YHXX_CJR, ");//创建人
			strbuf.append("     A.YHXX_CJSJ, ");//创建日期
			strbuf.append("     A.YHXX_GXR, ");//修改人
			strbuf.append("     A.YHXX_GXSJ, ");//修改日期
			strbuf.append("     A.YHXX_DLSJ ");//登陆时间
			strbuf.append(" FROM ");
			strbuf.append("     YHXX A WHERE A.YHXX_SCBZ = '0' AND A.YHXX_SDZT = '0'");
			strbuf.append(" AND A.YHXX_YHID  ='").append(strUserId).append("'");

			ResultSetHandler<Pojo_YHXX> rsh = new BeanHandler<Pojo_YHXX>(
					Pojo_YHXX.class);

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
	 * @FunctionName: getUserJSLX
	 * @Description: 取得用户角色
	 * @param UserID
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ljg
	 * @date 2014年12月10日 上午9:25:00
	 */
	public String getUserJSLX(String strUserId) throws Exception {
		String result = "";

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     B.YHJS_JSLX ");//用户角色类型
			strbuf.append(" FROM ");
			strbuf.append("     YHXX A,YHJS B ");
			strbuf.append(" WHERE A.YHXX_JSID  = B.YHJS_JSID ");
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
	 * @FunctionName: getMenuInfoByTeacher
	 * @Description: 根据用户取得教师菜单信息
	 * @param strUserRole
	 * @return
	 * @throws Exception
	 * @return List<USER_MENU>
	 * @author czl
	 * @date 2017-07-13
	 */
	public List<Pojo_MENU> getMenuInfoByTeacher(String strUserId)
			throws Exception {
		List<Pojo_MENU> result = null;
		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append("SELECT C.MENU_CDID,");
			strbuf.append("  	  C.MENU_CDMC,");
			strbuf.append("  	  C.MENU_URL,");
			strbuf.append("  	  C.MENU_CDCJ,");
			strbuf.append("  	  C.MENU_FJDID,");
			strbuf.append("  	  C.MENU_XH");
			strbuf.append("  FROM YHXX A,");
			strbuf.append("  	  JSQX B,");
			strbuf.append("  	  MENU C");
			strbuf.append(" WHERE A.YHXX_JSID  =B.JSQX_JSID");
			strbuf.append("   AND B.JSQX_CDID  =C.MENU_CDID");
			strbuf.append("   AND C.MENU_SCBZ = '0' ");
			strbuf.append("   AND (C.MENU_CDLX = '1' OR C.MENU_CDLX = '3')");//教师及共通
			strbuf.append("   AND A.YHXX_YHID  ='").append(strUserId).append("'");

			strbuf.append(" ORDER BY C.MENU_CDCJ,C.MENU_XH");

			ResultSetHandler<List<Pojo_MENU>> rsh = new BeanListHandler<Pojo_MENU>(
					Pojo_MENU.class);
			result = db.queryForBeanListHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}

	/**
	 * @FunctionName: getMenuInfoByStudent
	 * @Description: 根据用户取得学生菜单信息
	 * @param strUserRole
	 * @return
	 * @throws Exception
	 * @return List<USER_MENU>
	 * @author czl
	 * @date 2017-07-26
	 */
	public List<Pojo_MENU> getMenuInfoByStudent(String strUserId)
			throws Exception {
		List<Pojo_MENU> result = null;
		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append("SELECT C.MENU_CDID,");
			strbuf.append("  	  C.MENU_CDMC,");
			strbuf.append("  	  C.MENU_URL,");
			strbuf.append("  	  C.MENU_CDCJ,");
			strbuf.append("  	  C.MENU_FJDID,");
			strbuf.append("  	  C.MENU_XH");
			strbuf.append("  FROM YHXX A,");
			strbuf.append("  	  JSQX B,");
			strbuf.append("  	  MENU C");
			strbuf.append(" WHERE A.YHXX_JSID  =B.JSQX_JSID");
			strbuf.append("   AND B.JSQX_CDID  =C.MENU_CDID");
			strbuf.append("   AND C.MENU_SCBZ = '0' ");
			strbuf.append("   AND (C.MENU_CDLX = '2' OR C.MENU_CDLX = '3')");//学生及共通
			strbuf.append("   AND A.YHXX_YHID  ='").append(strUserId).append("'");

			strbuf.append(" ORDER BY C.MENU_CDCJ,C.MENU_XH");

			ResultSetHandler<List<Pojo_MENU>> rsh = new BeanListHandler<Pojo_MENU>(
					Pojo_MENU.class);
			result = db.queryForBeanListHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}

	/**
	 * @FunctionName: getMenuInfoByUser
	 * @Description: 根据用户取得菜单信息
	 * @param strUserRole
	 * @return
	 * @throws Exception
	 * @return List<USER_MENU>
	 * @author czl
	 * @date 2017-07-13
	 */
	public List<Pojo_MENU> getMenuInfoByUser(String strUserId)
			throws Exception {
		List<Pojo_MENU> result = null;
		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append("SELECT C.MENU_CDID,");
			strbuf.append("  	  C.MENU_CDMC,");
			strbuf.append("  	  C.MENU_URL,");
			strbuf.append("  	  C.MENU_CDCJ,");
			strbuf.append("  	  C.MENU_FJDID,");
			strbuf.append("  	  C.MENU_XH");
			strbuf.append("  FROM YHXX A,");
			strbuf.append("  	  JSQX B,");
			strbuf.append("  	  MENU C");
			strbuf.append(" WHERE A.YHXX_JSID  =B.JSQX_JSID");
			strbuf.append("   AND B.JSQX_CDID  =C.MENU_CDID");
			strbuf.append("   AND C.MENU_SCBZ = '0' ");
			strbuf.append("   AND (C.MENU_CDLX = '0' OR C.MENU_CDLX = '3')");//管理员及共通
			strbuf.append("   AND A.YHXX_YHID  ='").append(strUserId).append("'");
			//strbuf.append(" ORDER BY C.MENU_CDID,C.MENU_XH");
			strbuf.append(" ORDER BY C.MENU_CDCJ,C.MENU_XH");

			ResultSetHandler<List<Pojo_MENU>> rsh = new BeanListHandler<Pojo_MENU>(
					Pojo_MENU.class);
			result = db.queryForBeanListHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}

	/**
	 * @FunctionName: getUserInfo
	 * @Description: 取得用户简短信息
	 * @param UserID
	 * @return
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2017-07-13
	 */
	public Pojo_USER_INFO getUserInfo(String strUserId,String strJSLX) throws Exception {
		Pojo_USER_INFO result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			if(strJSLX.equals("0")){//管理员
				strbuf.append(" SELECT ");
				strbuf.append("     A.YHXX_UUID AS USER_UUID, "); //UUID
				strbuf.append("     A.YHXX_YHMC AS USER_NAME, "); //用户名称
				strbuf.append("     D.PXWD_ZDMC AS INFO_MAST, "); //站点名称
				strbuf.append("     B.YHJS_JSMC AS INFO_SLAV ");  //角色名称
				strbuf.append(" FROM ");
				strbuf.append("     YHXX A ");
				strbuf.append(" INNER JOIN YHJS B ON A.YHXX_JSID = B.YHJS_JSID ");
				strbuf.append(" LEFT JOIN YHZD C ON A.YHXX_YHID = C.YHZD_YHID ");
				strbuf.append(" LEFT JOIN PXWD D ON C.YHZD_ZDID = D.PXWD_ZDID ");
				strbuf.append(" WHERE ");
				strbuf.append("   A.YHXX_YHID  ='").append(strUserId).append("'");
			}else if(strJSLX.equals("1")){//教师
				strbuf.append(" SELECT ");
				strbuf.append("     A.YHXX_UUID AS USER_UUID, "); //UUID
				strbuf.append("     CONCAT(C.JSXX_JSXM,'老师') AS USER_NAME, "); //教师姓名
				strbuf.append("     C.JSXX_BYXX AS INFO_MAST, "); //毕业学校
				strbuf.append("     C.JSXX_BYNF AS INFO_SLAV, "); //毕业年份
				strbuf.append("     '3010120' AS INFO_MSID ");  //消息菜单ID
				strbuf.append(" FROM ");
				strbuf.append("     YHXX A,YHJS B,JSXX C ");
				strbuf.append(" WHERE A.YHXX_JSID  = B.YHJS_JSID ");
				strbuf.append("   AND A.YHXX_YHID  = C.JSXX_JSBM ");
				strbuf.append("   AND A.YHXX_YHID  ='").append(strUserId).append("'");
			}else if(strJSLX.equals("2")){//学生
				strbuf.append(" SELECT ");
				strbuf.append("     A.YHXX_UUID AS USER_UUID, "); //UUID
				strbuf.append("     CONCAT(C.XSXX_XSXM,'同学') AS USER_NAME, "); //学生姓名
				strbuf.append("     D.JDJY_JDMC AS INFO_MAST, "); //教育阶段
				strbuf.append("     C.XSXX_NJ   AS INFO_SLAV, ");  //年级
				strbuf.append("     '2010110' AS INFO_MSID ");  //消息菜单ID
				strbuf.append(" FROM ");
				strbuf.append("     YHXX A ");
				strbuf.append(" INNER JOIN YHJS B ON A.YHXX_JSID = B.YHJS_JSID ");
				strbuf.append(" INNER JOIN XSXX C ON A.YHXX_YHID = C.XSXX_XSBM ");
				strbuf.append(" LEFT JOIN JDJY D ON C.XSXX_JD = D.JDJY_JDID ");
				strbuf.append(" WHERE 1=1 ");
				strbuf.append("   AND A.YHXX_YHID  ='").append(strUserId).append("'");
			}

			ResultSetHandler<Pojo_USER_INFO> rsh = new BeanHandler<Pojo_USER_INFO>(
					Pojo_USER_INFO.class);
			result = db.queryForBeanListHandler(strbuf, rsh);

			//取得当前用户消息数
			if(result!=null){
				result.setMESS_CONT(getUserMessCount(result.getUSER_UUID()));
			}

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
	 * @author czl
	 * @date 2017-07-13
	 */
	public String getUserMessCount(String strUserId) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.XXMX_XXID) AS MESS_CONT ");//用户角色类型
			strbuf.append(" FROM ");
			strbuf.append("     XXMX A ");
			strbuf.append(" WHERE A.XXMX_CKZT = '0'");
			strbuf.append("   AND A.XXMX_JSRID  ='").append(strUserId).append("'");

			result = db.queryForInteger(strbuf);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return MyStringUtils.valueOf(result);
	}
}
