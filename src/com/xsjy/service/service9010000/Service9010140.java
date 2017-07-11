package com.xsjy.service.service9010000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_MENU;
import com.xsjy.pojo.Custom.pojo_9010000.Pojo9010140;

public class Service9010140 extends BaseService {

	private DBManager db = null;

	public Service9010140() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getMenuList_TotalCount
	 * @Description: 统计菜单信息列表个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2014年10月30日 上午11:19:15
	 */
	public int getMenuList_TotalCount(Pojo_MENU beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         COUNT(MENU_CDID) ");//菜单信息个数
			strbuf.append(" FROM ");
			strbuf.append("         MENU A");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));

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
	 * @FunctionName: getMenuList_PageData
	 * @Description: 获取菜单信息列表
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo_MENU>
	 * @author ztz
	 * @date 2014年10月30日 上午11:19:00
	 */
	public List<Pojo9010140> getMenuList_PageData(Pojo9010140 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo9010140> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         A.MENU_CDID, ");//菜单ID
			strbuf.append("         A.MENU_CDMC, ");//菜单名称
			strbuf.append("         A.MENU_URL, ");//菜单URL
			strbuf.append("         A.MENU_CDCJ, ");//菜单层级
			strbuf.append("         CASE WHEN A.MENU_CDCJ = '0' THEN '父节点' ELSE '子节点' END AS CDCJ, ");//菜单层级
			strbuf.append("         A.MENU_FJDID, ");//父节点ID
			strbuf.append("         A.MENU_XH, ");//序号
			strbuf.append("         A.MENU_CDLX, ");//菜单类型（0-管理菜单，1-教师菜单，2-学生菜单）
			strbuf.append("         CASE WHEN A.MENU_CDLX = 0 THEN '管理菜单' WHEN A.MENU_CDLX = 1 THEN '教师菜单' ELSE '学生菜单' END AS CDLX, ");//菜单类型（0-管理菜单，1-教师菜单，2-学生菜单）
			strbuf.append("         B.YHXX_YHMC AS MENU_CJR, ");//创建人
			strbuf.append("		   TO_CHAR(TO_DATE(A.MENU_CJSJ, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD') AS MENU_CJSJ, ");//创建时间
			strbuf.append("         B.YHXX_YHMC AS MENU_GXR, ");//更新人
			strbuf.append("	 	   TO_CHAR(TO_DATE(A.MENU_GXSJ, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD') AS MENU_GXSJ, ");//更新时间
			strbuf.append("		   CASE WHEN A.MENU_SCBZ = '0' THEN '可用' ELSE '不可用' END MENU_SCBZ ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("         MENU A, YHXX B ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.MENU_CJR = B.YHXX_YHID ");
			strbuf.append(" AND A.MENU_GXR = B.YHXX_YHID ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("         A.MENU_CDID, A.MENU_XH ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo9010140>> rs = new BeanListHandler<Pojo9010140>(
					Pojo9010140.class);
			
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
	 * 
	 * @FunctionName: searchSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2014年10月30日 上午11:27:01
	 */
	private String searchSql(Pojo_MENU beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 菜单ID */
			if (MyStringUtils.isNotBlank(beanIn.getMENU_CDID())) {
				strbuf.append(" AND A.MENU_CDID = '").append(beanIn.getMENU_CDID())
						.append("'");
			}
			/* 菜单名称 */
			if (MyStringUtils.isNotBlank(beanIn.getMENU_CDMC())) {
				strbuf.append(" AND A.MENU_CDMC LIKE '%")
						.append(beanIn.getMENU_CDMC()).append("%'");
			}
			/* 菜单层级 */
			if (MyStringUtils.isNotBlank(beanIn.getMENU_CDCJ())) {
				strbuf.append(" AND A.MENU_CDCJ = ")
						.append(beanIn.getMENU_CDCJ());
			}
			/* 父节点ID */
			if (MyStringUtils.isNotBlank(beanIn.getMENU_FJDID())) {
				strbuf.append(" AND A.MENU_FJDID LIKE '%")
						.append(beanIn.getMENU_FJDID()).append("%'");
			}
			/* 菜单类型 */
			if (MyStringUtils.isNotBlank(beanIn.getMENU_CDLX())) {
				if (!"000".equals(beanIn.getMENU_CDLX())) {
					strbuf.append(" AND A.MENU_CDLX = ")
							.append(beanIn.getMENU_CDLX());
				}
			}
			/* 菜单状态 */
			if (MyStringUtils.isNotBlank(beanIn.getMENU_SCBZ())) {
				strbuf.append(" AND A.MENU_SCBZ = ")
						.append(beanIn.getMENU_SCBZ());
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * @FunctionName: insertUser
	 * @Description: 新增菜单信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ljg
	 * @date 2014年7月22日 上午10:45:23
	 */
	public int insertMenu(Pojo_MENU beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("         MENU ");
			strbuf.append(" ( ");
			strbuf.append("         MENU_CDID, ");//菜单ID	
			strbuf.append("         MENU_CDMC, ");//菜单名称	
			strbuf.append("         MENU_URL, ");//菜单URL	
			strbuf.append("         MENU_CDCJ, ");//菜单层级	
			strbuf.append("         MENU_FJDID, ");//父节点ID	
			strbuf.append("         MENU_XH, ");//菜单顺序号	
			strbuf.append("         MENU_CDLX, ");//菜单类型（0-管理菜单，1-教师菜单，2-学生菜单）
			strbuf.append("         MENU_CJR, ");//创建人	
			strbuf.append("         MENU_CJSJ, ");//创建时间	
			strbuf.append("         MENU_GXR, ");//更新人	
			strbuf.append("         MENU_GXSJ	, ");//更新时间	
			strbuf.append("         MENU_SCBZ ");//删除标志（0：未删除 1：已删除）	
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("         '"+beanIn.getMENU_CDID()+"', ");//菜单ID	
			strbuf.append("         '"+beanIn.getMENU_CDMC()+"', ");//菜单名称	
			strbuf.append("         '"+beanIn.getMENU_URL()+"', ");//菜单URL	
			strbuf.append("         '"+beanIn.getMENU_CDCJ()+"', ");//菜单层级	
			strbuf.append("         '"+beanIn.getMENU_FJDID()+"', ");//父节点ID	
			strbuf.append("         '"+beanIn.getMENU_XH()+"', ");//菜单顺序号	
			strbuf.append("         '"+beanIn.getMENU_CDLX()+"', ");//菜单类型（0-管理菜单，1-教师菜单，2-学生菜单）
			strbuf.append("         '1001', ");//创建人	
			strbuf.append("         TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间	
			strbuf.append("         '1001', ");//更新人	
			strbuf.append("         TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//更新时间	
			strbuf.append("         '0'  ");//删除标志（0：未删除 1：已删除）	
			strbuf.append(" ) ");

			result = db.executeSQL(strbuf);
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
	 * @FunctionName: updateMenu
	 * @Description: 修改菜单信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2014年10月30日 上午11:19:48
	 */
	public int updateMenu(Pojo_MENU beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("           MENU ");
			strbuf.append(" SET ");
			strbuf.append("           MENU_CDMC = '").append(beanIn.getMENU_CDMC()).append("', ");//菜单名称	
			strbuf.append("           MENU_URL = '").append(beanIn.getMENU_URL()).append("', ");//菜单URL	
			strbuf.append("           MENU_CDCJ = '").append(beanIn.getMENU_CDCJ()).append("', ");//菜单层级	
			strbuf.append("           MENU_FJDID = '").append(beanIn.getMENU_FJDID()).append("', ");//父节点ID	
			strbuf.append("           MENU_XH = '").append(beanIn.getMENU_XH()).append("', ");//菜单顺序号	
			strbuf.append("           MENU_CDLX = '").append(beanIn.getMENU_CDLX()).append("', ");//菜单类型（0-管理菜单，1-教师菜单，2-学生菜单）
			strbuf.append("           MENU_GXR = '1001', ");//更新人	
			strbuf.append("           MENU_GXSJ = TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS') ");//更新时间	
			strbuf.append(" WHERE ");
			strbuf.append("           MENU_CDID = '").append(beanIn.getMENU_CDID()).append("' ");//菜单代码

			result = db.executeSQL(strbuf);
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
	 * @FunctionName: deleteMenu
	 * @Description: 删除菜单信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2014年10月30日 上午11:20:00
	 */
	public int deleteMenu(Pojo_MENU beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbufJSQX = new StringBuffer();
			strbufJSQX.append(" DELETE ");
			strbufJSQX.append("          JSQX ");
			strbufJSQX.append(" WHERE ");
			strbufJSQX.append("          JSQX_CDID = '").append(beanIn.getMENU_CDID()).append("' ");//菜单ID
			db.executeSQL(strbufJSQX);

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("           MENU ");
			strbuf.append(" SET ");
			strbuf.append("          MENU_SCBZ = '1', ");//删除标志（0：未删除，1：已删除）
			strbuf.append("          MENU_GXR = '1001', ");//更新人
			strbuf.append("          MENU_GXSJ = TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS') ");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("          MENU_CDID = '").append(beanIn.getMENU_CDID()).append("' ");//菜单ID
			result = db.executeSQL(strbuf);
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
	 * @FunctionName: recoveryMenu
	 * @Description: 恢复删除的菜单信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2014年10月30日 下午6:13:23
	 */
	public int recoveryMenu(Pojo_MENU beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("           MENU ");
			strbuf.append(" SET ");
			strbuf.append("          MENU_SCBZ = '0', ");//删除标志（0：未删除，1：已删除）
			strbuf.append("          MENU_GXR = '1001', ");//更新人
			strbuf.append("          MENU_GXSJ = TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS') ");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("          MENU_CDID = '").append(beanIn.getMENU_CDID()).append("' ");//菜单ID
			result = db.executeSQL(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
}