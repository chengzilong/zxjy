package com.xsjy.service.school;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_BMXX;
import com.xsjy.pojo.BaseTable.Pojo_YHXX;
import com.xsjy.pojo.Custom.pojo_school.PojoClass;

public class ServiceClass extends BaseService {
	private DBManager db = null;

	public ServiceClass() {
		db = new DBManager();
	}

	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 统计列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2014年12月17日 上午10:30:29
	 */
	public int getDataCount(PojoClass beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.BCXX_BCID) ");// 数据个数
			strbuf.append(" FROM ");
			strbuf.append("     BCXX A,KCFY B,KCXX C,SKSD D,JSXX E,KCLX F, ");
			strbuf.append(" (SELECT ");
			strbuf.append("     F.JSQY_JSID AS QY_JSID, ");
			strbuf.append("     WMSYS.WM_CONCAT (G.XZQY_QYID) AS SKQYID, ");
			strbuf.append("     WMSYS.WM_CONCAT (G.XZQY_QYMC) AS SKQYMC ");
			strbuf.append(" FROM ");
			strbuf.append("     JSQY F, XZQY G ");
			strbuf.append(" WHERE ");
			strbuf.append("     F.JSQY_QYID = G.XZQY_QYID ");
			strbuf.append(" AND F.JSQY_SCBZ = 0 ");
			strbuf.append(" AND G.XZQY_SCBZ = 0 ");
			strbuf.append(" GROUP BY ");
			strbuf.append("     F.JSQY_JSID) G ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchCourseSql(beanIn));

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
	 * @FunctionName: getCourseList
	 * @Description: 获取列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<PojoClass>
	 * @author ztz
	 * @date 2014年12月17日 上午10:30:39
	 */
	public List<PojoClass> getDataList(PojoClass beanIn, int page,
			int limit, String sort, String userid) throws Exception {
		List<PojoClass> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.BCXX_BCID AS BCID, ");//班次ID
			strbuf.append("     A.BCXX_BCMC AS BCMC, ");//班次名称
			strbuf.append("     A.BCXX_KCID AS KCID, ");//课程ID
			strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
			strbuf.append("     B.KCFY_XS || '学时' AS XS, ");//学时
			strbuf.append("     A.BCXX_KSSJ AS KSSJ, ");//开课时间
			strbuf.append("     A.BCXX_JSSJ AS JSSJ, ");//结束时间
			strbuf.append("     B.KCFY_SDID AS SDID, ");//时段ID
			strbuf.append("     D.SKSD_SDMC AS SDMC, ");//时段名称
			strbuf.append("     A.BCXX_FY AS FY, ");//费用
			strbuf.append("     B.KCFY_JSID AS JSID, ");//教师ID
			strbuf.append("     E.JSXX_JSXM AS JSXM, ");//教师姓名
			strbuf.append("     G.SKQYID, ");//区域ID
			strbuf.append("     G.SKQYMC, ");//区域名称
			strbuf.append("     B.KCFY_LXID AS LXID, ");//类型ID
			strbuf.append("     F.KCLX_LXMC AS LXMC, ");//类型名称
			strbuf.append("     A.BCXX_BCZT AS BCZT, ");//班次状态
			
			if(!"".equals(userid)){
				strbuf.append(" DECODE(G.BCXS_BCID,NULL,'报名','已报名') AS BMED, ");// 是否报名
			}else{
				//BMXX_KCFYID
				strbuf.append("     '报名' AS BMED, ");// 是否报名
			}
			
			strbuf.append("     A.BCXX_CJR, ");
			strbuf.append("     A.BCXX_CJSJ, ");
			strbuf.append("     A.BCXX_GXR, ");
			strbuf.append("     A.BCXX_GXSJ ");
			
			strbuf.append(" FROM ");
			strbuf.append("     BCXX A,KCFY B,KCXX C,SKSD D,JSXX E,KCLX F, ");
			strbuf.append(" (SELECT ");
			strbuf.append("     F.JSQY_JSID AS QY_JSID, ");
			strbuf.append("     WMSYS.WM_CONCAT (G.XZQY_QYID) AS SKQYID, ");
			strbuf.append("     WMSYS.WM_CONCAT (G.XZQY_QYMC) AS SKQYMC ");
			strbuf.append(" FROM ");
			strbuf.append("     JSQY F, XZQY G ");
			strbuf.append(" WHERE ");
			strbuf.append("     F.JSQY_QYID = G.XZQY_QYID ");
			strbuf.append(" AND F.JSQY_SCBZ = 0 ");
			strbuf.append(" AND G.XZQY_SCBZ = 0 ");
			strbuf.append(" GROUP BY ");
			strbuf.append("     F.JSQY_JSID) G ");
			if(!"".equals(userid)){
				strbuf.append(" ,(SELECT ");
				strbuf.append("    BCXS_BCID");// 课程费用ID
				strbuf.append(" FROM ");
				strbuf.append("    BCXS");
				strbuf.append(" WHERE ");
				strbuf.append("    BCXS_SCBZ = '0'");
				strbuf.append(" AND BCXS_XSID = '").append(this.getStudentId(userid)).append("') G");				
			}
			strbuf.append(" WHERE 1=1 ");
			if(!"".equals(userid)){
				strbuf.append("AND A.BCXX_BCID=G.BCXS_BCID(+) ");// 课程信息ID	
			}
			strbuf.append(this.searchCourseSql(beanIn));

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page,limit, sort);
			ResultSetHandler<List<PojoClass>> rs = new BeanListHandler<PojoClass>(PojoClass.class);
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
	 * @FunctionName: searchCourseSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2014年12月17日 上午10:30:52
	 * @update ztz at 2015年1月20日 下午4:01:05
	 */
	private String searchCourseSql(PojoClass beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 公共部分 */
			strbuf.append(" AND A.BCXX_KCFYID=B.KCFY_FYID");
			strbuf.append(" AND A.BCXX_KCID=C.KCXX_KCID");
			strbuf.append(" AND B.KCFY_SDID=D.SKSD_SDID");
			strbuf.append(" AND B.KCFY_JSID=E.JSXX_JSID");
			strbuf.append(" AND B.KCFY_LXID=F.KCLX_LXID ");
			strbuf.append(" AND E.JSXX_JSID = G.QY_JSID(+) ");
			strbuf.append(" AND A.BCXX_SCBZ='0'");
			strbuf.append(" AND B.KCFY_SCBZ='0'");
			strbuf.append(" AND (A.BCXX_BCZT='0' OR A.BCXX_BCZT='1')");
			strbuf.append(" AND A.BCXX_SFYZ = '1'");
			
			/* 教学学段 */
			if (MyStringUtils.isNotBlank(beanIn.getPARAJXXD()) && !("000").equals(beanIn.getPARAJXXD())) {
				strbuf.append("  AND C.KCXX_KCJD = '").append(beanIn.getPARAJXXD()).append("'");
			}
			
			/* 求教学科 */
			if (MyStringUtils.isNotBlank(beanIn.getPARAQJXK())
					&& !("000").equals(beanIn.getPARAQJXK())) {
				strbuf.append("  AND C.KCXX_KMID = '").append(beanIn.getPARAQJXK()).append("'");
			}
			
			/* 课程类型 */
			if (MyStringUtils.isNotBlank(beanIn.getPARAKCLX()) && !("000").equals(beanIn.getPARAKCLX())) {
				strbuf.append("  AND B.KCFY_LXID = '").append(beanIn.getPARAKCLX()).append("'");
			}
			
			/* 上课时段 */
			if (MyStringUtils.isNotBlank(beanIn.getPARASKSD()) && !("000").equals(beanIn.getPARASKSD())) {
				strbuf.append("  AND B.KCFY_SDID = '").append(beanIn.getPARASKSD()).append("'");
			}
			
			/* 授课区域 */
			if (MyStringUtils.isNotBlank(beanIn.getPARASKQY()) && !("000").equals(beanIn.getPARASKQY())) {
				strbuf.append("  AND G.SKQYID LIKE '%").append(beanIn.getPARASKQY()).append("%'");
			}
			
			/* 课程费用 */
			if (MyStringUtils.isNotBlank(beanIn.getPARAKCFY()) && !("000").equals(beanIn.getPARAKCFY())) {
				if(beanIn.getPARAKCFY().equals("1")){
					strbuf.append("  AND (B.KCFY_FY >= 0 AND B.KCFY_FY <=100) ");
				}else if(beanIn.getPARAKCFY().equals("2")){
					strbuf.append("  AND (B.KCFY_FY > 100 AND B.KCFY_FY <=500) ");
				}else if(beanIn.getPARAKCFY().equals("3")){
					strbuf.append("  AND B.KCFY_FY > 500 ");
				}
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: isDataExist
	 * @Description: 判断数据是否存在
	 * @param classId
	 * @param yhid
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月5日 下午2:50:08
	 */
	public boolean isDataExist(String classId, String yhid) throws Exception {
		boolean result = false;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("    COUNT(BMXX_BMID)");//报名ID
			strbuf.append(" FROM ");
			strbuf.append("    BMXX");
			strbuf.append(" WHERE ");
			strbuf.append("    BMXX_SCBZ = '0'");
			strbuf.append(" AND BMXX_XSID = '").append(this.getStudentId(yhid)).append("'");
			strbuf.append(" AND BMXX_SSBC = '").append(classId).append("'");
			int count = db.queryForInteger(strbuf);
			
			if (count > 0) result = true;
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
	 * @FunctionName: getStudentId
	 * @Description: 获取学生信息表ID
	 * @param yhid
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年1月5日 下午3:11:02
	 * @update ztz at 2015年1月5日 下午3:11:02
	 */
	private String getStudentId(String yhid) throws Exception {
		String stuId = "";
		
		try {
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("    B.XSXX_XSID ");//学生ID
			strbuf.append(" FROM ");
			strbuf.append("    YHXX A, XSXX B");
			strbuf.append(" WHERE ");
			strbuf.append("    A.YHXX_YHID = B.XSXX_XSBM");
			strbuf.append(" AND A.YHXX_SCBZ = '0'");
			strbuf.append(" AND B.XSXX_SCBZ = '0'");
			strbuf.append(" AND A.YHXX_YHID = '").append(yhid).append("'");
			
			stuId = db.queryForString(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return stuId;
	}
	/**
	 * 
	 * @FunctionName: getClassData
	 * @Description: 获取班次信息及课程费用信息
	 * @param classId
	 * @return
	 * @throws Exception
	 * @return Pojo_BMXX
	 * @author ztz
	 * @date 2015年1月5日 下午3:37:03
	 */
	private Pojo_BMXX getClassData(String classId) throws Exception {
		Pojo_BMXX beanIn = new Pojo_BMXX();
		
		try {
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("    A.BCXX_BCID AS BMXX_SSBC, ");
			strbuf.append("    A.BCXX_KCID AS BMXX_XXID, ");
			strbuf.append("    A.BCXX_KCFYID AS BMXX_KCFYID, ");
			strbuf.append("    B.KCFY_LXID AS BMXX_LXID, ");
			strbuf.append("    B.KCFY_SDID AS BMXX_SDID, ");
			strbuf.append("    B.KCFY_XS AS BMXX_XS, ");
			strbuf.append("    B.KCFY_JSID AS BMXX_JSID, ");
			strbuf.append("    B.KCFY_FY AS BMXX_FY");
			strbuf.append(" FROM ");
			strbuf.append("    BCXX A, KCFY B");
			strbuf.append(" WHERE ");
			strbuf.append("    A.BCXX_KCFYID = B.KCFY_FYID");
			strbuf.append(" AND A.BCXX_SCBZ = '0'");
			strbuf.append(" AND B.KCFY_SCBZ = '0'");
			strbuf.append(" AND A.BCXX_BCID = '").append(classId).append("'");
			
			ResultSetHandler<Pojo_BMXX> rs = new BeanHandler<Pojo_BMXX>(Pojo_BMXX.class);
			
			beanIn = db.queryForBeanHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return beanIn;
	}
	/**
	 * 
	 * @FunctionName: insertData
	 * @Description: 新增数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2014年12月16日 下午12:14:05
	 */
	public boolean insertData(String classId, Pojo_YHXX beanUser) throws Exception {
		boolean result = false;
		int countBMXX = 0;
		int countBCXS = 0;
		String strBmxxId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入ID
		String strBcxsId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入ID
		Pojo_BMXX beanIn = null;

		try {
			db.openConnection();
			db.beginTran();
			
			beanIn = this.getClassData(classId);
			beanIn.setBMXX_XSID(this.getStudentId(beanUser.getYHXX_YHID()));
			
			StringBuffer strbufBMXX = new StringBuffer();
			strbufBMXX.append(" INSERT INTO ");
			strbufBMXX.append("     BMXX ");
			strbufBMXX.append(" ( ");
			strbufBMXX.append("     BMXX_BMID, ");//报名ID
			strbufBMXX.append("     BMXX_XSID, ");//学生ID
			strbufBMXX.append("     BMXX_XXID, ");//课程信息ID
			strbufBMXX.append("     BMXX_SDID, ");//上课时段ID
			strbufBMXX.append("     BMXX_XS, ");//学时
			strbufBMXX.append("     BMXX_JSID, ");//教师ID
			strbufBMXX.append("     BMXX_FY, ");//费用
			strbufBMXX.append("     BMXX_YJFY, ");//已交费用
			strbufBMXX.append("     BMXX_WJFY, ");//未交费用
			strbufBMXX.append("     BMXX_LXID, ");//课程类型ID
			strbufBMXX.append("     BMXX_KCFYID, ");//课程费用ID
			strbufBMXX.append("     BMXX_SSBC, ");//所属班次
			strbufBMXX.append("     BMXX_TJJSID, ");//推荐教师ID
			strbufBMXX.append("     BMXX_BMFS, ");//报名方式
			strbufBMXX.append("     BMXX_BMZT, ");//报名状态
			strbufBMXX.append("     BMXX_BZXX, ");//备注信息
			strbufBMXX.append("     BMXX_SCBZ, ");//删除标志
			strbufBMXX.append("     BMXX_CJR, ");//创建人
			strbufBMXX.append("     BMXX_CJSJ, ");//创建时间
			strbufBMXX.append("     BMXX_GXR, ");//更新人
			strbufBMXX.append("     BMXX_GXSJ  ");//更新时间
			strbufBMXX.append(" ) ");
			strbufBMXX.append(" VALUES ");
			strbufBMXX.append(" ( ");
			strbufBMXX.append("     '"+strBmxxId+"', ");//报名ID
			strbufBMXX.append("     '"+beanIn.getBMXX_XSID()+"', ");//学生ID
			strbufBMXX.append("     '"+beanIn.getBMXX_XXID()+"', ");//课程信息ID
			strbufBMXX.append("     '"+beanIn.getBMXX_SDID()+"', ");//上课时段ID
			strbufBMXX.append("     '"+beanIn.getBMXX_XS()+"', ");//学时
			strbufBMXX.append("     '"+beanIn.getBMXX_JSID()+"', ");//教师ID
			strbufBMXX.append("     '"+beanIn.getBMXX_FY()+"', ");//费用
			strbufBMXX.append("     '0', ");//已交费用（默认：0）
			strbufBMXX.append("     '"+beanIn.getBMXX_FY()+"', ");//未交费用=费用-已交费用
			strbufBMXX.append("     '"+beanIn.getBMXX_LXID()+"', ");//课程类型ID
			strbufBMXX.append("     '"+beanIn.getBMXX_KCFYID()+"', ");//课程费用ID
			strbufBMXX.append("     '"+beanIn.getBMXX_SSBC()+"', ");//所属班次
			strbufBMXX.append("     '', ");//推荐教师ID
			strbufBMXX.append("     '3', ");//报名方式
			if ("0".equals(beanIn.getBMXX_FY())) {//公开课算作已交费，报名状态直接变为“已交费”状态
				strbufBMXX.append("     '2', ");//报名状态
			} else {
				strbufBMXX.append("     '1', ");//报名状态
			}
			strbufBMXX.append("     '"+MyStringUtils.safeToString(beanIn.getBMXX_BZXX())+"', ");//备注信息
			strbufBMXX.append("     '0', ");//删除标志
			strbufBMXX.append("     '"+beanUser.getYHXX_YHID()+"', ");//创建人
			strbufBMXX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbufBMXX.append("     '"+beanUser.getYHXX_YHID()+"', ");//更新人
			strbufBMXX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
			strbufBMXX.append(" ) ");
			countBMXX = db.executeSQL(strbufBMXX);
			/* 如果是选班，则向BCXS表插入记录Start */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_SSBC())) {
				StringBuffer strbufBCXS = new StringBuffer();
				strbufBCXS.append(" INSERT INTO ");
				strbufBCXS.append("     BCXS ");
				strbufBCXS.append(" ( ");
				strbufBCXS.append("     BCXS_BCXSID, ");//班次学生ID
				strbufBCXS.append("     BCXS_BCID, ");//班次ID
				strbufBCXS.append("     BCXS_XSID, ");//学生ID
				strbufBCXS.append("     BCXS_SCBZ, ");//删除标志（0-正常 1-删除）
				strbufBCXS.append("     BCXS_CJR, ");//创建人
				strbufBCXS.append("     BCXS_CJSJ, ");//创建时间
				strbufBCXS.append("     BCXS_GXR, ");//更新人
				strbufBCXS.append("     BCXS_GXSJ  ");//更新时间
				strbufBCXS.append(" ) ");
				strbufBCXS.append(" VALUES ");
				strbufBCXS.append(" ( ");
				strbufBCXS.append("     '"+strBcxsId+"', ");//班次学生ID
				strbufBCXS.append("     '"+beanIn.getBMXX_SSBC()+"', ");//班次ID
				strbufBCXS.append("     '"+beanIn.getBMXX_XSID()+"', ");//学生ID
				strbufBCXS.append("     '0', ");//删除标志（0-正常 1-删除）
				strbufBCXS.append("     '"+beanUser.getYHXX_YHID()+"', ");//创建人
				strbufBCXS.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
				strbufBCXS.append("     '"+beanUser.getYHXX_YHID()+"', ");//更新人
				strbufBCXS.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
				strbufBCXS.append(" ) ");
				countBCXS = db.executeSQL(strbufBCXS);
			}
			/* 如果是选班，则向BCXS表插入记录End */
			if (countBMXX > 0) {
				if (MyStringUtils.isNotBlank(beanIn.getBMXX_SSBC()) && countBCXS > 0 && countBMXX == countBCXS) {
					db.commit();
					result = true;
				} else if (!MyStringUtils.isNotBlank(beanIn.getBMXX_SSBC()) && countBCXS == 0) {
					db.commit();
					result = true;
				} else {
					db.rollback();
				}
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
