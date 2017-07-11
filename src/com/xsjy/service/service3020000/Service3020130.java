package com.xsjy.service.service3020000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020130;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020131;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020132;

public class Service3020130 extends BaseService {
	private DBManager db = null;

	public Service3020130() {
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
	 * @date 2015年1月9日 下午3:23:37
	 */
	public int getDataCount(Pojo3020130 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.XSXX_XSID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     XSXX A, JDJY B ");
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
	 * @FunctionName: getDataList
	 * @Description: 获取列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo3020130>
	 * @author ztz
	 * @date 2015年1月9日 下午3:23:46
	 */
	public List<Pojo3020130> getDataList(Pojo3020130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3020130> result = null;
		
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
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.XSXX_XSBM ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3020130>> rs = new BeanListHandler<Pojo3020130>(
					Pojo3020130.class);
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
	 * @date 2015年1月9日 下午3:23:59
	 */
	private String searchSql(Pojo3020130 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.XSXX_JD = B.JDJY_JDID(+)");
			strbuf.append(" AND A.XSXX_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND EXISTS( ");
			strbuf.append(" SELECT ");
			strbuf.append("   C.BCXS_XSID ");
			strbuf.append(" FROM ");
			strbuf.append("   BCXS C, BCXX D, KCFY E, JSXX F ");
			strbuf.append(" WHERE 1=1");
			strbuf.append(" AND C.BCXS_BCID = D.BCXX_BCID");
			strbuf.append(" AND D.BCXX_KCFYID = E.KCFY_FYID");
			strbuf.append(" AND E.KCFY_JSID = F.JSXX_JSID");
			strbuf.append(" AND C.BCXS_XSID = A.XSXX_XSID");
			strbuf.append(" AND F.JSXX_JSBM = '").append(beanIn.getJSBM()).append("'");
			strbuf.append(" )");
			/* 学生编码 */
			if (MyStringUtils.isNotBlank(beanIn.getXSXX_XSBM())) {
				strbuf.append(" AND A.XSXX_XSBM = '").append(beanIn.getXSXX_XSBM())
						.append("'");
			}
			/* 学生姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getXSXX_XSXM())) {
				strbuf.append(" AND A.XSXX_XSXM LIKE '%").append(beanIn.getXSXX_XSXM())
						.append("%'");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: getDataDetailCount
	 * @Description: 统计详情列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月9日 下午3:29:40
	 */
	public int getDetailDataCount(Pojo3020130 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.XSXX_XSID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     XSXX A, JDJY B, BCXS C, BCXX D, KCFY E, JSXX F, SKSD G, KCXX H ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.detailSearchSql(beanIn));
			
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
	 * @FunctionName: getDetailDataList
	 * @Description: 获取详情列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo3020130>
	 * @author ztz
	 * @date 2015年1月9日 下午3:31:17
	 */
	public List<Pojo3020130> getDetailDataList(Pojo3020130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3020130> result = null;
		
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
			strbuf.append("     D.BCXX_BCMC AS BCMC, ");//班次名称
			strbuf.append("     D.BCXX_SKDD AS SKDD, ");//上课地点
			strbuf.append("     D.BCXX_BCZT, ");//班次状态
			strbuf.append("     CASE WHEN D.BCXX_BCZT = 0 THEN '未开课' WHEN D.BCXX_BCZT = 1 THEN '已开课' ELSE '已结束' END AS BCZTMC, ");//班次状态名称
			strbuf.append("     H.KCXX_KCMC AS KCMC, ");//课程名称
			strbuf.append("     E.KCFY_SDID, ");//上课时段ID
			strbuf.append("     G.SKSD_SDMC AS SKSDMC, ");//上课时段名称
			strbuf.append("     E.KCFY_XS AS XS");//学时
			strbuf.append(" FROM ");
			strbuf.append("     XSXX A, JDJY B, BCXS C, BCXX D, KCFY E, JSXX F, SKSD G, KCXX H ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.detailSearchSql(beanIn));
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3020130>> rs = new BeanListHandler<Pojo3020130>(
					Pojo3020130.class);
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
	 * @FunctionName: detailSearchSql
	 * @Description: 详情查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年1月9日 下午3:32:04
	 */
	private String detailSearchSql(Pojo3020130 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.XSXX_JD = B.JDJY_JDID(+)");
			strbuf.append(" AND A.XSXX_XSID = C.BCXS_XSID");
			strbuf.append(" AND C.BCXS_BCID = D.BCXX_BCID");
			strbuf.append(" AND D.BCXX_KCFYID = E.KCFY_FYID");
			strbuf.append(" AND E.KCFY_JSID = F.JSXX_JSID");
			strbuf.append(" AND E.KCFY_SDID = G.SKSD_SDID");
			strbuf.append(" AND E.KCFY_XXID = H.KCXX_KCID");
			strbuf.append(" AND A.XSXX_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND B.JDJY_SCBZ(+) = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND C.BCXS_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND D.BCXX_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND E.KCFY_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND F.JSXX_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND G.SKSD_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND F.JSXX_JSBM = '").append(beanIn.getJSBM()).append("'");
			strbuf.append(" AND A.XSXX_XSID = '").append(beanIn.getXSXX_XSID()).append("'");
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: getSendDataCount
	 * @Description: 统计发布信息列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月12日 上午10:58:53
	 */
	public int getSendDataCount(Pojo3020131 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.XXXX_XXID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     XXXX A, XXMX B, XSXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSendSql(beanIn));
			
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
	 * @FunctionName: getSendDataList
	 * @Description: 获取发布信息列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo3020131>
	 * @author ztz
	 * @date 2015年1月12日 上午10:59:14
	 */
	public List<Pojo3020131> getSendDataList(Pojo3020131 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3020131> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.XXXX_XXID, ");//消息ID
			strbuf.append("     A.XXXX_FBR, ");//发布人
			strbuf.append("     A.XXXX_FBZT, ");//发布主题
			strbuf.append("     A.XXXX_FBNR, ");//发布内容
			strbuf.append("     A.XXXX_FBSJ, ");//发布时间
			strbuf.append("     A.XXXX_YXSJ, ");//有效时间
			strbuf.append("     A.XXXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     A.XXXX_CJR, ");//创建人
			strbuf.append("     A.XXXX_CJSJ, ");//创建时间
			strbuf.append("     A.XXXX_GXR, ");//更新人
			strbuf.append("     A.XXXX_GXSJ,  ");//更新时间
			strbuf.append("     B.XXMX_MXID, ");//消息明细ID
			strbuf.append("     B.XXMX_XXID, ");//消息ID
			strbuf.append("     B.XXMX_JSR, ");//接收人
			strbuf.append("     B.XXMX_CKZT, ");//查看状态（0-未查看 1-已查看）
			strbuf.append("     CASE WHEN B.XXMX_CKZT = 0 THEN '未查看' ELSE '查看' END AS CKZT, ");//查看状态（0-未查看 1-已查看）
			strbuf.append("     B.XXMX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     B.XXMX_CJR, ");//创建人
			strbuf.append("     B.XXMX_CJSJ, ");//创建时间
			strbuf.append("     B.XXMX_GXR, ");//更新人
			strbuf.append("     B.XXMX_GXSJ, ");//更新时间
			strbuf.append("     B.XXMX_JSRID, ");//接收人ID
			strbuf.append("     B.XXMX_LXFS  ");//联系方式
			strbuf.append(" FROM ");
			strbuf.append("     XXXX A, XXMX B, XSXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSendSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.XXXX_CJSJ DESC ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3020131>> rs = new BeanListHandler<Pojo3020131>(
					Pojo3020131.class);
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
	 * @FunctionName: searchSendSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年1月12日 上午11:01:38
	 */
	private String searchSendSql(Pojo3020131 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.XXXX_XXID = B.XXMX_XXID");
			strbuf.append(" AND B.XXMX_JSRID = C.XSXX_XSID");
			strbuf.append(" AND A.XXXX_SCBZ = '0'");
			strbuf.append(" AND B.XXMX_SCBZ = '0'");
			strbuf.append(" AND C.XSXX_SCBZ = '0'");
			strbuf.append(" AND A.XXXX_FBR = '").append(beanIn.getXXXX_FBR()).append("'");
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: insertSendData
	 * @Description: 新增发布消息数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月12日 下午2:20:54
	 */
	public boolean insertSendData(Pojo3020131 beanIn,String uuid) throws Exception {
		boolean result = false;
		int count_XXXX = 0;
		int count_XXMX = 0;
		String xxId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入ID
		String mxId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入ID

		try {
			db.openConnection();
			db.beginTran();
			/* 向XXXX表插入数据Start */
			StringBuffer strbuf_XXXX = new StringBuffer();
			strbuf_XXXX.append(" INSERT INTO ");
			strbuf_XXXX.append("     XXXX ");
			strbuf_XXXX.append(" ( ");
			strbuf_XXXX.append("     XXXX_XXID, ");//消息ID
			strbuf_XXXX.append("     XXXX_FBR, ");//发布人
			strbuf_XXXX.append("     XXXX_FBZT, ");//发布主题
			strbuf_XXXX.append("     XXXX_FBNR, ");//发布内容
			strbuf_XXXX.append("     XXXX_FBSJ, ");//发布时间
			strbuf_XXXX.append("     XXXX_YXSJ, ");//有效时间
			strbuf_XXXX.append("     XXXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf_XXXX.append("     XXXX_CJR, ");//创建人
			strbuf_XXXX.append("     XXXX_CJSJ, ");//创建时间
			strbuf_XXXX.append("     XXXX_GXR, ");//更新人
			strbuf_XXXX.append("     XXXX_GXSJ  ");//更新时间
			strbuf_XXXX.append(" ) ");
			strbuf_XXXX.append(" VALUES ");
			strbuf_XXXX.append(" ( ");
			strbuf_XXXX.append("     '"+xxId+"', ");//消息ID
			strbuf_XXXX.append("     '"+uuid+"', ");//发布人
			strbuf_XXXX.append("     '"+beanIn.getXXXX_FBZT()+"', ");//发布主题
			strbuf_XXXX.append("     '"+beanIn.getXXXX_FBNR()+"', ");//发布内容
			strbuf_XXXX.append("     '"+beanIn.getXXXX_FBSJ()+"', ");//发布时间
			strbuf_XXXX.append("     '"+beanIn.getXXXX_YXSJ()+"', ");//有效时间
			strbuf_XXXX.append("     '0', ");//删除标志（0-正常 1-删除）
			strbuf_XXXX.append("     '"+beanIn.getXXXX_GXR()+"', ");//创建人
			strbuf_XXXX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbuf_XXXX.append("     '"+beanIn.getXXXX_GXR()+"', ");//更新人
			strbuf_XXXX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
			strbuf_XXXX.append(" ) ");
			count_XXXX = db.executeSQL(strbuf_XXXX);
			/* 向XXXX表插入数据End */
			/* 向XXMX表插入数据Start */
			StringBuffer strbuf_XXMX = new StringBuffer();
			strbuf_XXMX.append(" INSERT INTO ");
			strbuf_XXMX.append("     XXMX ");
			strbuf_XXMX.append(" ( ");
			strbuf_XXMX.append("     XXMX_MXID, ");//消息明细ID
			strbuf_XXMX.append("     XXMX_XXID, ");//消息ID
			strbuf_XXMX.append("     XXMX_JSR, ");//接收人
			strbuf_XXMX.append("     XXMX_CKZT, ");//查看状态（0-未查看 1-已查看）
			strbuf_XXMX.append("     XXMX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf_XXMX.append("     XXMX_CJR, ");//创建人
			strbuf_XXMX.append("     XXMX_CJSJ, ");//创建时间
			strbuf_XXMX.append("     XXMX_GXR, ");//更新人
			strbuf_XXMX.append("     XXMX_GXSJ, ");//更新时间
			strbuf_XXMX.append("     XXMX_JSRID, ");//接收人ID
			strbuf_XXMX.append("     XXMX_LXFS  ");//联系方式
			strbuf_XXMX.append(" ) ");
			strbuf_XXMX.append(" VALUES ");
			strbuf_XXMX.append(" ( ");
			strbuf_XXMX.append("     '"+mxId+"', ");//消息明细ID
			strbuf_XXMX.append("     '"+xxId+"', ");//消息ID
			strbuf_XXMX.append("     '"+beanIn.getXXMX_JSR()+"', ");//接收人
			strbuf_XXMX.append("     '0', ");//查看状态（0-未查看 1-已查看）
			strbuf_XXMX.append("     '0', ");//删除标志（0-正常 1-删除）
			strbuf_XXMX.append("     '"+beanIn.getXXXX_GXR()+"', ");//创建人
			strbuf_XXMX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbuf_XXMX.append("     '"+beanIn.getXXXX_GXR()+"', ");//更新人
			strbuf_XXMX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//更新时间
			strbuf_XXMX.append("     '"+beanIn.getXXMX_JSRID()+"', ");//接收人ID
			strbuf_XXMX.append("     '"+beanIn.getXXMX_LXFS()+"'  ");//联系方式
			strbuf_XXMX.append(" ) ");
			count_XXMX = db.executeSQL(strbuf_XXMX);
			/* 向XXMX表插入数据End */
			if (count_XXXX > 0 && count_XXMX > 0 && count_XXXX == count_XXMX) {
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
	/**
	 * 
	 * @FunctionName: updateSendData
	 * @Description: 更新发布信息数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月12日 下午2:22:51
	 */
	public boolean updateSendData(Pojo3020131 beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			/* 更新XXXX表数据Start */
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     XXXX ");
			strbuf.append(" SET ");
			strbuf.append("     XXXX_FBZT='").append(beanIn.getXXXX_FBZT()).append("',");//发布主题
			strbuf.append("     XXXX_FBNR='").append(beanIn.getXXXX_FBNR()).append("',");//发布内容
			strbuf.append("     XXXX_FBSJ='").append(beanIn.getXXXX_FBSJ()).append("',");//发布时间
			strbuf.append("     XXXX_YXSJ='").append(beanIn.getXXXX_YXSJ()).append("',");//有效时间
			strbuf.append("     XXXX_GXR='").append(beanIn.getXXXX_GXR()).append("',");//更新人
			strbuf.append("     XXXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     XXXX_XXID='").append(beanIn.getXXXX_XXID()).append("'");//消息ID
			count = db.executeSQL(strbuf);
			/* 更新XXXX表数据End */
			if (count > 0) {
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
	/**
	 * 
	 * @FunctionName: deleteSendData
	 * @Description: 删除发布信息数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月12日 下午2:33:05
	 */
	public boolean deleteSendData(Pojo3020131 beanIn) throws Exception {
		boolean result = false;
		int count_XXXX = 0;
		int count_XXMX = 0;

		try {
			db.openConnection();
			db.beginTran();
			/* 删除XXXX表数据Start */
			StringBuffer strbuf_XXXX = new StringBuffer();
			strbuf_XXXX.append(" DELETE ");
			strbuf_XXXX.append("     XXXX ");
			strbuf_XXXX.append(" WHERE ");
			strbuf_XXXX.append("     XXXX_XXID='").append(beanIn.getXXXX_XXID()).append("'");//消息ID
			count_XXXX = db.executeSQL(strbuf_XXXX);
			/* 删除XXXX表数据End */
			/* 删除XXMX表数据Start */
			StringBuffer strbuf_XXMX = new StringBuffer();
			strbuf_XXMX.append(" DELETE ");
			strbuf_XXMX.append("     XXMX ");
			strbuf_XXMX.append(" WHERE 1=1");
			strbuf_XXMX.append(" AND XXMX_MXID='").append(beanIn.getXXMX_MXID()).append("'");//消息明细ID
			strbuf_XXMX.append(" AND XXMX_XXID='").append(beanIn.getXXXX_XXID()).append("'");//消息ID
			count_XXMX = db.executeSQL(strbuf_XXMX);
			/* 删除XXMX表数据End */
			if (count_XXXX > 0 && count_XXMX > 0 && count_XXXX == count_XXMX) {
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
	/**
	 * 
	 * @FunctionName: getTalkDataCount
	 * @Description: 统计评价信息列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月12日 下午3:48:44
	 */
	public int getTalkDataCount(Pojo3020132 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(B.BCXX_BCID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     PJXX A, BCXX B, BCXS C, XSXX D, KCFY E, JSXX F ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchTalkSql(beanIn));
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
	 * @FunctionName: getTalkDataList
	 * @Description: 获取评价信息列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo3020132>
	 * @author ztz
	 * @date 2015年1月12日 下午3:49:01
	 */
	public List<Pojo3020132> getTalkDataList(Pojo3020132 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3020132> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     D.XSXX_XSID, ");//学生ID
			strbuf.append("     D.XSXX_XSBM, ");//学生编码
			strbuf.append("     D.XSXX_XSXM, ");//学生姓名
			strbuf.append("     B.BCXX_BCID, ");//班次ID
			strbuf.append("     B.BCXX_BCMC, ");//班次名称
			strbuf.append("     A.PJXX_PJID, ");//评价ID
			strbuf.append("     A.PJXX_BCID, ");//班次ID
			strbuf.append("     A.PJXX_PJR, ");//评价人
			strbuf.append("     A.PJXX_BPR, ");//被评人
			strbuf.append("     A.PJXX_PJNR, ");//评价内容
			strbuf.append("     A.PJXX_PJJF, ");//评价积分
			strbuf.append("     A.PJXX_PJSJ, ");//评价时间
			strbuf.append("     A.PJXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     A.PJXX_CJR, ");//创建人
			strbuf.append("     A.PJXX_CJSJ, ");//创建时间
			strbuf.append("     A.PJXX_GXR, ");//更新人
			strbuf.append("     A.PJXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     PJXX A, BCXX B, BCXS C, XSXX D, KCFY E, JSXX F ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchTalkSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.PJXX_CJSJ DESC ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3020132>> rs = new BeanListHandler<Pojo3020132>(
					Pojo3020132.class);
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
	 * @FunctionName: searchTalkSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年1月12日 下午3:49:27
	 */
	private String searchTalkSql(Pojo3020132 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.PJXX_BCID(+) = B.BCXX_BCID");
			strbuf.append(" AND B.BCXX_BCID = C.BCXS_BCID");
			strbuf.append(" AND C.BCXS_XSID = D.XSXX_XSID");
			strbuf.append(" AND B.BCXX_KCFYID = E.KCFY_FYID");
			strbuf.append(" AND E.KCFY_JSID = F.JSXX_JSID");
			strbuf.append(" AND A.PJXX_SCBZ(+) = '0'");//删除标志(0-正常 1-删除)
			strbuf.append(" AND B.BCXX_SCBZ = '0'");//删除标志(0-正常 1-删除)
			strbuf.append(" AND C.BCXS_SCBZ = '0'");//删除标志(0-正常 1-删除)
			strbuf.append(" AND D.XSXX_SCBZ = '0'");//删除标志(0-正常 1-删除)
			strbuf.append(" AND E.KCFY_SCBZ = '0'");//删除标志(0-正常 1-删除)
			strbuf.append(" AND F.JSXX_SCBZ = '0'");//删除标志(0-正常 1-删除)
			strbuf.append(" AND F.JSXX_JSID = '").append(beanIn.getPJXX_PJR()).append("'");
			strbuf.append(" AND D.XSXX_XSID = '").append(beanIn.getXSXX_XSID()).append("'");
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: insertTalkData
	 * @Description: 新增评价信息数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月12日 下午3:42:52
	 */
	public boolean insertTalkData(Pojo3020132 beanIn,String uuid) throws Exception {
		boolean result = false;
		int count = 0;
		String pjId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入站点ID

		try {
			db.openConnection();
			db.beginTran();
			/* 向PJXX表插入数据Start */
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     PJXX ");
			strbuf.append(" ( ");
			strbuf.append("     PJXX_PJID, ");//评价ID
			strbuf.append("     PJXX_BCID, ");//班次ID
			strbuf.append("     PJXX_PJR, ");//评价人
			strbuf.append("     PJXX_BPR, ");//被评人
			strbuf.append("     PJXX_PJNR, ");//评价内容
			strbuf.append("     PJXX_PJJF, ");//评价积分
			strbuf.append("     PJXX_PJSJ, ");//评价时间
			strbuf.append("     PJXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     PJXX_CJR, ");//创建人
			strbuf.append("     PJXX_CJSJ, ");//创建时间
			strbuf.append("     PJXX_GXR, ");//更新人
			strbuf.append("     PJXX_GXSJ  ");//更新时间
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+pjId+"', ");//评价ID
			strbuf.append("     '"+beanIn.getBCXX_BCID()+"', ");//班次ID
			strbuf.append("     '"+uuid+"', ");//评价人
			strbuf.append("     '"+beanIn.getXSXX_XSID()+"', ");//被评人
			strbuf.append("     '"+beanIn.getPJXX_PJNR()+"', ");//评价内容
			strbuf.append("     '"+beanIn.getPJXX_PJJF()+"', ");//评价积分
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYY-MM-DD'), ");//评价时间
			strbuf.append("     '0', ");//删除标志（0-正常 1-删除）
			strbuf.append("     '"+beanIn.getPJXX_GXR()+"', ");//创建人
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbuf.append("     '"+beanIn.getPJXX_GXR()+"', ");//更新人
			strbuf.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
			strbuf.append(" ) ");
			count = db.executeSQL(strbuf);
			/* 向PJXX表插入数据End */
			if(count > 0) {
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
	/**
	 * 
	 * @FunctionName: updateTalkData
	 * @Description: 更新评价信息数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月12日 下午3:43:08
	 */
	public boolean updateTalkData(Pojo3020132 beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     PJXX ");
			strbuf.append(" SET ");
			strbuf.append("     PJXX_PJNR='").append(beanIn.getPJXX_PJNR()).append("',");//评价内容
			strbuf.append("     PJXX_PJJF='").append(beanIn.getPJXX_PJJF()).append("',");//评价积分
			strbuf.append("     PJXX_PJSJ=TO_CHAR(SYSDATE, 'YYYY-MM-DD'),");//评价时间
			strbuf.append("     PJXX_GXR='").append(beanIn.getPJXX_GXR()).append("',");//更新人
			strbuf.append("     PJXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     PJXX_PJID='").append(beanIn.getPJXX_PJID()).append("'");//评价ID
			count = db.executeSQL(strbuf);
			
			if(count > 0) {
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
	/**
	 * 
	 * @FunctionName: deleteTalkData
	 * @Description: 删除评价信息数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月12日 下午3:43:25
	 */
	public boolean deleteTalkData(Pojo3020132 beanIn) throws Exception {
		boolean result = false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE ");
			strbuf.append("     PJXX ");
			strbuf.append(" WHERE ");
			strbuf.append("     PJXX_PJID='").append(beanIn.getPJXX_PJID()).append("'");//评价ID
			count = db.executeSQL(strbuf);

			if(count > 0) {
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