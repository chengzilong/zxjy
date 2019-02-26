package com.xsjy.service.service9010000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsjy.pojo.BaseTable.Pojo_MENU;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_9010000.Pojo9010120;
import com.xsjy.pojo.Custom.pojo_9010000.Pojo9010130;

public class Service9010130 extends BaseService {

	private DBManager db;

	public Service9010130() {
		db = new DBManager();
	}
	/**
	 *
	 * @FunctionName: getJsxxDataCount
	 * @Description: 获取DB中角色信息记录个数
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-27
	 */
	public int getJsxxDataCount() throws Exception {
		int count = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         COUNT(A.YHJS_JSID)  ");//角色信息个数
			strbuf.append(" FROM ");
			strbuf.append("         YHJS A ");

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
	 * @FunctionName: getJsxxData
	 * @Description: 获取角色信息列表
	 * @return
	 * @throws Exception
	 * @return List<Pojo9010130>
	 * @author czl
	 * @date 2017-07-27
	 */
	public List<Pojo9010120> getJsxxData(int page,
			int limit, String sort) throws Exception {
		List<Pojo9010120> jsxxList = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YHJS_JSID, ");//角色ID
			strbuf.append("     A.YHJS_JSMC, ");//角色名称
			strbuf.append("     A.YHJS_JSLX, ");//角色类型（0-工作人员，1-教师，2-学生）
			strbuf.append("     CASE WHEN A.YHJS_JSLX = 0 THEN '工作人员' WHEN A.YHJS_JSLX = 1 THEN '教师' ELSE '学生' END AS JSLX, ");//角色类型
			strbuf.append("     A.YHJS_JSMS, ");//角色描述
			strbuf.append("     B.YHXX_YHMC AS YHJS_CJR, ");//创建人
			strbuf.append("     LEFT(YHJS_CJSJ,10) AS YHJS_CJSJ,  ");//创建时间
			strbuf.append("     B.YHXX_YHMC AS YHJS_GXR, ");//更新人
			strbuf.append("     LEFT(YHJS_GXSJ,10) AS YHJS_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     YHJS A, YHXX B ");
			strbuf.append(" WHERE  ");
			strbuf.append("     A.YHJS_CJR = B.YHXX_YHID ");
			strbuf.append(" AND A.YHJS_GXR = B.YHXX_YHID ");

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);

			ResultSetHandler<List<Pojo9010120>> rsh = new BeanListHandler<Pojo9010120>(
					Pojo9010120.class);

			jsxxList = db.queryForBeanListHandler(execSql, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return jsxxList;
	}
	/**
	 *
	 * @FunctionName: getMenuDataCount
	 * @Description: 获取DB中菜单信息记录个数
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2017-07-27
	 */
	public int getMenuDataCount(String jslx) throws Exception {
		int count = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         COUNT(A.MENU_CDID)  ");//菜单信息个数
			strbuf.append(" FROM ");
			strbuf.append("         MENU A ");
			strbuf.append(" WHERE ");
			strbuf.append("         A.MENU_SCBZ = '0' ");
			strbuf.append(" AND (A.MENU_CDLX = '").append(jslx).append("' OR A.MENU_CDLX = '3')");

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
	 * @FunctionName: getMenuData
	 * @Description: 获取菜单信息列表
	 * @return
	 * @throws Exception
	 * @return List<Pojo_MENU>
	 * @author czl
	 * @date 2017-07-27
	 */
	public List<Pojo9010130> getMenuData(String jsid, String jslx, int page,
			int limit, String sort) throws Exception {
		List<Pojo9010130> menuList = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         A.MENU_CDID, ");//菜单ID
			strbuf.append("         A.MENU_CDMC, ");//菜单名称
			strbuf.append("         A.MENU_URL, ");//菜单URL
			strbuf.append("         CASE WHEN A.MENU_CDCJ = '0' THEN '父节点' ELSE '子节点' END AS MENU_CDCJ, ");//菜单层级
			strbuf.append("         A.MENU_FJDID, ");//父节点ID
			strbuf.append("         A.MENU_XH, ");//菜单顺序号
			strbuf.append("         A.MENU_CDLX, ");//菜单类型（0-管理菜单，1-教师菜单，2-学生菜单）
			strbuf.append("         CASE WHEN A.MENU_CDLX = 0 THEN '管理菜单' WHEN A.MENU_CDLX = 1 THEN '教师菜单' ELSE '学生菜单' END AS CDLX, ");//菜单类型（0-管理菜单，1-教师菜单，2-学生菜单）
			strbuf.append("         B.YHXX_YHMC AS MENU_CJR, ");//创建人
			strbuf.append("         A.MENU_CJSJ,  ");//创建时间
			strbuf.append("         B.YHXX_YHMC AS MENU_GXR, ");//更新人
			strbuf.append("         A.MENU_GXSJ,  ");//更新时间
			strbuf.append("         CASE WHEN YHJS_JSID IS NULL THEN '未关联' ELSE '已关联' END AS MENU_SFGL, ");//是否关联
			strbuf.append("         CASE WHEN YHJS_JSID IS NULL THEN '0' ELSE '1' END AS MENU_GLZT ");//关联状态
			strbuf.append(" FROM ");
			strbuf.append("         MENU A ");
			strbuf.append(" INNER JOIN YHXX B ON A.MENU_CJR = B.YHXX_YHID AND A.MENU_GXR = B.YHXX_YHID ");
			strbuf.append(" LEFT JOIN JSQX C ON A.MENU_CDID = C.JSQX_CDID AND C.JSQX_JSID = '").append(jsid).append("'");
			strbuf.append(" LEFT JOIN YHJS D ON C.JSQX_JSID = D.YHJS_JSID ");
			strbuf.append(" WHERE  ");
			strbuf.append(" A.MENU_SCBZ = '0' ");
			strbuf.append(" AND (A.MENU_CDLX = '").append(jslx).append("' OR A.MENU_CDLX = '3')");
			strbuf.append(" ORDER BY ");
			strbuf.append("         MENU_GLZT DESC,A.MENU_CDID ");

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);

			ResultSetHandler<List<Pojo9010130>> rsh = new BeanListHandler<Pojo9010130>(
					Pojo9010130.class);

			menuList = db.queryForBeanListHandler(execSql, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return menuList;
	}
	/**
	 *
	 * @FunctionName: relationMenu
	 * @Description: 角色关联菜单
	 * @param cdids
	 * @param beanUser
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2017-07-27
	 */
	public boolean relationMenu(String jsid, String cdids, Pojo_YHXX beanUser) throws Exception {
		boolean result = false;
		int count = 0;
		StringBuffer strbuf = null;
		boolean flag = false;
		String fjdid = "";

		try {
			db.openConnection();
			db.beginTran();

			String[] cdid = cdids.split(",");
			for(int i = 0; i < cdid.length; i++) {
				strbuf = new StringBuffer();
				strbuf.append(" INSERT INTO ");
				strbuf.append("         JSQX ");
				strbuf.append(" ( ");
				strbuf.append("         JSQX_JSID, ");//角色ID
				strbuf.append("         JSQX_CDID, ");//菜单ID
				strbuf.append("         JSQX_CJR, ");//创建人
				strbuf.append("         JSQX_CJSJ, ");//创建时间
				strbuf.append("         JSQX_GXR, ");//更新人
				strbuf.append("         JSQX_GXSJ  ");//更新时间
				strbuf.append(" ) ");
				strbuf.append(" VALUES ");
				strbuf.append(" ( ");
				strbuf.append("         '"+jsid+"', ");//角色ID
				strbuf.append("         "+cdid[i]+", ");//菜单ID
				strbuf.append("         '"+beanUser.getYHXX_YHID()+"', ");//创建人
				strbuf.append("         NOW(), ");//创建时间
				strbuf.append("         '"+beanUser.getYHXX_YHID()+"', ");//更新人
				strbuf.append("         NOW() ");//更新时间
				strbuf.append(" ) ");
				count += db.executeSQL(strbuf);
				Pojo_MENU menu = this.getCDCJ(cdid[i]);
				if(menu.getMENU_CDCJ().equals("0")) {//判断关联的菜单中是否存在父节点菜单
					flag = true;
				} else {
					fjdid = menu.getMENU_FJDID();
				}
			}

			if(!flag &&  this.isRelation(jsid, fjdid)) {
				StringBuffer strbufFJD = new StringBuffer();
				strbufFJD.append(" INSERT INTO ");
				strbufFJD.append("         JSQX ");
				strbufFJD.append(" ( ");
				strbufFJD.append("         JSQX_JSID, ");//角色ID
				strbufFJD.append("         JSQX_CDID, ");//菜单ID
				strbufFJD.append("         JSQX_CJR, ");//创建人
				strbufFJD.append("         JSQX_CJSJ, ");//创建时间
				strbufFJD.append("         JSQX_GXR, ");//更新人
				strbufFJD.append("         JSQX_GXSJ  ");//更新时间
				strbufFJD.append(" ) ");
				strbufFJD.append(" VALUES ");
				strbufFJD.append(" ( ");
				strbufFJD.append("         '"+jsid+"', ");//角色ID
				strbufFJD.append("         "+fjdid+", ");//菜单ID
				strbufFJD.append("         '"+beanUser.getYHXX_YHID()+"', ");//创建人
				strbufFJD.append("         NOW(), ");//创建时间
				strbufFJD.append("         '"+beanUser.getYHXX_YHID()+"', ");//更新人
				strbufFJD.append("         NOW() ");//更新时间
				strbufFJD.append(" ) ");
				db.executeSQL(strbufFJD);
			}

			if(count == cdid.length) {
				result = true;
				db.commit();
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
	/**
	 *
	 * @FunctionName: cancelRelationMenu
	 * @Description: 取消角色关联菜单
	 * @param cdids
	 * @param beanUser
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2017-07-27
	 */
	public boolean cancelRelationMenu(String jsid, String cdids, Pojo_YHXX beanUser) throws Exception {
		boolean result = false;
		int count = 0;
		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE FROM ");
			strbuf.append("          JSQX ");
			strbuf.append(" WHERE ");
			strbuf.append("          JSQX_JSID = '").append(jsid).append("'");
			strbuf.append(" AND JSQX_CDID IN (").append(cdids).append(")");
			count = db.executeSQL(strbuf);

			String[] cdid = cdids.split(",");
			for(int i = 0; i < cdid.length; i++) {
				Pojo_MENU menu = this.getCDCJ(cdid[i]);
				if(menu.getMENU_CDCJ().equals("0")) {//判断取消关联的菜单中是否存在父节点菜单
					StringBuffer strbufOther = new StringBuffer();
					strbufOther.append(" DELETE FROM ");
					strbufOther.append("          JSQX");
					strbufOther.append(" WHERE ");
					strbufOther.append("          JSQX_JSID = '").append(jsid).append("'");
					strbufOther.append(" AND EXISTS(SELECT T.MENU_CDID FROM MENU T WHERE T.MENU_CDID = JSQX.JSQX_CDID AND T.MENU_FJDID = ").append(cdid[i]).append(")");
					db.executeSQL(strbufOther);
					break;
				}
			}
			if(count == cdid.length) {
				result = true;
				db.commit();
			} else {
				db.rollback();
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
	 *
	 * @FunctionName: getCDCJ
	 * @Description: 根据菜单ID获取菜单层级
	 * @param cdid
	 * @return
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2017-07-27
	 */
	private Pojo_MENU getCDCJ(String cdid) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		Pojo_MENU menu = new Pojo_MENU();

		try {
			strbuf.append(" SELECT ");
			strbuf.append("         MENU_CDCJ, ");//菜单层级
			strbuf.append("         MENU_FJDID ");//父节点ID
			strbuf.append(" FROM ");
			strbuf.append("         MENU ");
			strbuf.append(" WHERE ");
			strbuf.append("         MENU_CDID = ").append(cdid);//菜单ID

			ResultSetHandler<Pojo_MENU> rsh = new BeanHandler<Pojo_MENU>(
					Pojo_MENU.class);

			menu = db.queryForBeanHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return menu;
	}
	/**
	 *
	 * @FunctionName: isRelation
	 * @Description: 根据角色ID和菜单ID判断菜单是否已经关联
	 * @param cdid
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2017-07-27
	 */
	private boolean isRelation(String jsid, String cdid) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		boolean result = false;

		try {
			strbuf.append(" SELECT ");
			strbuf.append("         COUNT(JSQX_CDID) ");
			strbuf.append(" FROM ");
			strbuf.append("         JSQX ");
			strbuf.append(" WHERE ");
			strbuf.append("         JSQX_JSID = '").append(jsid).append("'");
			strbuf.append(" AND JSQX_CDID = '").append(cdid).append("'");

			if (db.queryForInteger(strbuf) == 0) {
				result = true;
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return result;
	}
}