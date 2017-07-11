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
import com.xsjy.pojo.Custom.pojo_school.PojoLession;

public class ServiceLession extends BaseService {
	private DBManager db = null;

	public ServiceLession() {
		db = new DBManager();
	}

	/**
	 * 
	 * @FunctionName: getCourseCount
	 * @Description: 统计列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2014年12月17日 上午10:30:29
	 */
	public int getDataCount(PojoLession beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.KCFY_FYID) ");// 数据个数
			strbuf.append(" FROM ");
			strbuf.append("     KCFY A, KCXX B, KCLX C, SKSD D, JSXX E, ");
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
			strbuf.append("     F.JSQY_JSID) F ");
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
	 * @return List<PojoLession>
	 * @author ztz
	 * @date 2014年12月17日 上午10:30:39
	 */
	public List<PojoLession> getDataList(PojoLession beanIn, int page,
			int limit, String sort, String userid) throws Exception {
		List<PojoLession> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.KCFY_FYID, ");// 费用ID
			strbuf.append("     A.KCFY_XXID, ");// 课程信息ID
			strbuf.append("     B.KCXX_KCMC AS KCMC, ");// 课程名称
			strbuf.append("     A.KCFY_LXID, ");// 课程类型ID
			strbuf.append("     C.KCLX_LXMC AS LXMC, ");// 类型名称
			strbuf.append("     A.KCFY_SDID, ");// 上课时段ID
			strbuf.append("     D.SKSD_SDMC AS SDMC, ");// 时段名称
			strbuf.append("     F.SKQYMC AS SKQY, ");// 授课区域
			strbuf.append("     A.KCFY_XS, ");// 学时
			strbuf.append("     A.KCFY_JSID, ");// 教师ID
			strbuf.append("     E.JSXX_JSXM AS JSXM, ");// 教师姓名
			strbuf.append("     A.KCFY_FY, ");// 费用
			strbuf.append("     A.KCFY_SCBZ, ");// 删除标志
			strbuf.append("     A.KCFY_CJR, ");// 创建人
			strbuf.append("     A.KCFY_CJSJ, ");// 创建时间
			if(!"".equals(userid)){
				strbuf.append(" DECODE(G.BMXX_KCFYID,NULL,'报名','已报名') AS BMED, ");// 是否报名
			}else{
				//BMXX_KCFYID
				strbuf.append("     '报名' AS BMED, ");// 是否报名
			}
			strbuf.append("     A.KCFY_GXR, ");// 更新人
			strbuf.append("     A.KCFY_GXSJ  ");// 更新时间
			strbuf.append(" FROM ");
			strbuf.append("     KCFY A, KCXX B, KCLX C, SKSD D, JSXX E, ");
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
			strbuf.append("     F.JSQY_JSID) F ");
//
			if(!"".equals(userid)){
				strbuf.append(" ,(SELECT ");
				strbuf.append("    BMXX_KCFYID");// 课程费用ID
				strbuf.append(" FROM ");
				strbuf.append("    BMXX");
				strbuf.append(" WHERE ");
				strbuf.append("    BMXX_SCBZ = '0'");
				strbuf.append(" AND BMXX_XSID = '").append(this.getStudentId(userid)).append("') G");				
			}

			//strbuf.append(" AND BMXX_KCFYID = '").append(lessionID).append("'");			
//

			strbuf.append(" WHERE 1=1 ");
			if(!"".equals(userid)){
				strbuf.append("AND A.KCFY_FYID=G.BMXX_KCFYID(+) ");// 课程信息ID	
			}
			
			strbuf.append(this.searchCourseSql(beanIn));

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page,limit, sort);
			ResultSetHandler<List<PojoLession>> rs = new BeanListHandler<PojoLession>(PojoLession.class);
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
	 */
	private String searchCourseSql(PojoLession beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 公共部分 */
			strbuf.append(" AND A.KCFY_XXID = B.KCXX_KCID");
			strbuf.append(" AND A.KCFY_LXID = C.KCLX_LXID");
			strbuf.append(" AND A.KCFY_SDID = D.SKSD_SDID");
			strbuf.append(" AND A.KCFY_JSID = E.JSXX_JSID(+)");
			strbuf.append(" AND E.JSXX_JSID = F.QY_JSID(+) ");
			strbuf.append(" AND A.KCFY_SCBZ = '0'");
			strbuf.append(" AND B.KCXX_SCBZ = '0'");
			strbuf.append(" AND C.KCLX_SCBZ = '0'");
			strbuf.append(" AND D.SKSD_SCBZ = '0'");
			
			/* 教学学段 */
			if (MyStringUtils.isNotBlank(beanIn.getJXXD()) && !("000").equals(beanIn.getJXXD())) {
				strbuf.append("  AND B.KCXX_KCJD = '").append(beanIn.getJXXD()).append("'");
			}
			
			/* 求教学科 */
			if (MyStringUtils.isNotBlank(beanIn.getQJXK())
					&& !("000").equals(beanIn.getQJXK())) {
				strbuf.append("  AND B.KCXX_KMID = '").append(beanIn.getQJXK()).append("'");
			}
			
			/* 课程类型 */
			if (MyStringUtils.isNotBlank(beanIn.getKCLX()) && !("000").equals(beanIn.getKCLX())) {
				strbuf.append("  AND A.KCFY_LXID = '").append(beanIn.getKCLX()).append("'");
			}
			
			/* 上课时段 */
			if (MyStringUtils.isNotBlank(beanIn.getSKSD()) && !("000").equals(beanIn.getSKSD())) {
				strbuf.append("  AND A.KCFY_SDID = '").append(beanIn.getSKSD()).append("'");
			}
			
			/* 授课区域 */
			if (MyStringUtils.isNotBlank(beanIn.getSKQY()) && !("000").equals(beanIn.getSKQY())) {
				strbuf.append("  AND F.SKQYID LIKE '%").append(beanIn.getSKQY()).append("%'");
			}
			
			/* 课程费用 */
			if (MyStringUtils.isNotBlank(beanIn.getKCFY()) && !("000").equals(beanIn.getKCFY())) {
				if(beanIn.getKCFY().equals("1")){
					strbuf.append("  AND (A.KCFY_FY >= 0 AND A.KCFY_FY <=100) ");
				}else if(beanIn.getKCFY().equals("2")){
					strbuf.append("  AND (A.KCFY_FY > 100 AND A.KCFY_FY <=500) ");
				}else if(beanIn.getKCFY().equals("3")){
					strbuf.append("  AND A.KCFY_FY > 500 ");
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
	 * @param lessionID
	 * @param yhid
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月5日 下午3:55:16
	 */
	public boolean isDataExist(String lessionID, String yhid) throws Exception {
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
			strbuf.append(" AND BMXX_KCFYID = '").append(lessionID).append("'");
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
	 * @date 2015年1月5日 下午3:55:29
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
	 * @FunctionName: getLessionData
	 * @Description: 获取课程费用信息
	 * @param lessionID
	 * @return
	 * @throws Exception
	 * @return Pojo_BMXX
	 * @author ztz
	 * @date 2015年1月5日 下午4:00:06
	 */
	private Pojo_BMXX getLessionData(String lessionID) throws Exception {
		Pojo_BMXX beanIn = new Pojo_BMXX();
		
		try {
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("    A.KCFY_XXID AS BMXX_XXID, ");
			strbuf.append("    A.KCFY_FYID AS BMXX_KCFYID, ");
			strbuf.append("    A.KCFY_LXID AS BMXX_LXID, ");
			strbuf.append("    A.KCFY_SDID AS BMXX_SDID, ");
			strbuf.append("    A.KCFY_XS AS BMXX_XS, ");
			strbuf.append("    A.KCFY_JSID AS BMXX_JSID, ");
			strbuf.append("    A.KCFY_FY AS BMXX_FY");
			strbuf.append(" FROM ");
			strbuf.append("    KCFY A");
			strbuf.append(" WHERE ");
			strbuf.append("    A.KCFY_SCBZ = '0'");
			strbuf.append(" AND A.KCFY_FYID = '").append(lessionID).append("'");
			
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
	 * @param lessionID
	 * @param beanUser
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月5日 下午3:59:37
	 */
	public boolean insertData(String lessionID, Pojo_YHXX beanUser) throws Exception {
		boolean result = false;
		int countBMXX = 0;
		String strBmxxId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入ID
		Pojo_BMXX beanIn = null;

		try {
			db.openConnection();
			db.beginTran();
			
			beanIn = this.getLessionData(lessionID);
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
			strbufBMXX.append("     '"+MyStringUtils.safeToString(beanIn.getBMXX_SSBC())+"', ");//所属班次
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
			
			if (countBMXX > 0) {
				db.commit();
				result = true;
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
