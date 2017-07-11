package com.xsjy.service.service3020000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsjy.pojo.BaseTable.Pojo_XXXX;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020140;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020141;
import com.xsjy.pojo.Custom.pojo_3020000.Pojo3020142;

public class Service3020140 extends BaseService {

	private DBManager db = null;

	public Service3020140() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getIndexDataCount
	 * @Description: 统计列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月13日 上午11:31:44
	 */
	public int getIndexDataCount(Pojo_XXXX beanIn) throws Exception {
		int count = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("    COUNT(A.XXXX_XXID)");
			strbuf.append(" FROM ");
			strbuf.append("     XXXX A, YHXX B ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.indexSearchSql(beanIn));
			
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
	 * @FunctionName: getIndexDataList
	 * @Description: 获取列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo_XXXX>
	 * @author ztz
	 * @date 2015年1月13日 上午11:32:31
	 */
	public List<Pojo_XXXX> getIndexDataList(Pojo_XXXX beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo_XXXX> dataList = null;
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.XXXX_XXID, ");//消息ID
			strbuf.append("     A.XXXX_FBZT, ");//发布主题
			strbuf.append("     A.XXXX_FBNR, ");//发布内容
			strbuf.append("     A.XXXX_FBSJ, ");//发布时间
			strbuf.append("     A.XXXX_YXSJ, ");//有效时间
			strbuf.append("     A.XXXX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     A.XXXX_CJR, ");//创建人
			strbuf.append("     A.XXXX_CJSJ, ");//创建时间
			strbuf.append("     A.XXXX_GXR, ");//更新人
			strbuf.append("     A.XXXX_GXSJ  ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     XXXX A, YHXX B ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.indexSearchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.XXXX_FBSJ DESC");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo_XXXX>> rs = new BeanListHandler<Pojo_XXXX>(
					Pojo_XXXX.class);
			dataList = db.queryForBeanListHandler(execSql, rs);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return dataList;
	}
	/**
	 * 
	 * @FunctionName: indexSearchSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年1月13日 上午11:34:19
	 */
	private String indexSearchSql(Pojo_XXXX beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.XXXX_FBR = B.YHXX_UUID");
			strbuf.append(" AND A.XXXX_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND B.YHXX_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND A.XXXX_FBR = '").append(beanIn.getXXXX_FBR()).append("'");
			/* 有效时间 */
			if (MyStringUtils.isNotBlank(beanIn.getXXXX_YXSJ())) {
				strbuf.append(" AND to_date(A.XXXX_YXSJ,'YYYY-MM-DD') <=").append("to_date('").append(beanIn.getXXXX_YXSJ()).append("','YYYY-MM-DD')");	
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: getIndexDetailDataCount
	 * @Description: 统计列表数据个数
	 * @param xxid
	 * @param option
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月13日 下午12:05:21
	 * @update ztz at 2015年1月14日 上午9:52:43
	 */
	public int getIndexDetailDataCount(String xxid, String option) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.XXMX_MXID) ");//数据个数
			strbuf.append(" FROM ");
			strbuf.append("     XXMX A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.indexDetailSearchSql(xxid));
			if ("DELETE".equals(option)) {
				strbuf.append(" AND A.XXMX_CKZT = '0'");
			}
			
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
	 * @FunctionName: getIndexDetailDataList
	 * @Description: 获取列表数据明细
	 * @param xxid
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo3020141>
	 * @author ztz
	 * @date 2015年1月13日 下午12:05:32
	 */
	public List<Pojo3020141> getIndexDetailDataList(String xxid, int page,
			int limit, String sort) throws Exception {
		List<Pojo3020141> dataList = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.XXMX_MXID, ");//消息明细ID
			strbuf.append("     A.XXMX_XXID, ");//消息ID
			strbuf.append("     A.XXMX_JSRID, ");//接收人ID
			strbuf.append("     A.XXMX_JSR, ");//接收人
			strbuf.append("     A.XXMX_LXFS, ");//联系方式
			strbuf.append("     A.XXMX_CKZT, ");//查看状态（0-未查看 1-已查看）
			strbuf.append("     CASE WHEN A.XXMX_CKZT = 0 THEN '未查看' ELSE '已查看' END AS CKZTMC, ");//查看状态名称
			strbuf.append("     A.XXMX_SCBZ, ");//删除标志（0-正常 1-删除）
			strbuf.append("     A.XXMX_CJR, ");//创建人
			strbuf.append("     A.XXMX_CJSJ, ");//创建时间
			strbuf.append("     A.XXMX_GXR, ");//更新人
			strbuf.append("     A.XXMX_GXSJ ");//更新时间
			strbuf.append(" FROM ");
			strbuf.append("     XXMX A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.indexDetailSearchSql(xxid));
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3020141>> rs = new BeanListHandler<Pojo3020141>(
					Pojo3020141.class);
			dataList = db.queryForBeanListHandler(execSql, rs);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return dataList;
	}
	/**
	 * 
	 * @FunctionName: indexDetailSearchSql
	 * @Description: 查询条件部分
	 * @param xxid
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年1月13日 下午12:05:42
	 */
	private String indexDetailSearchSql(String xxid) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.XXMX_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND A.XXMX_XXID = '").append(xxid).append("'");
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: getStuDataCount
	 * @Description: 获取学生列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月13日 下午4:13:42
	 * @update ztz at 2015年1月14日 下午12:19:15
	 */
	public int getStuDataCount(Pojo3020142 beanIn) throws Exception {
		int count = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT (DISTINCT(A.XSXX_XSID)) ");//学生ID
			strbuf.append(" FROM ");
			strbuf.append("     XSXX A, BCXS B, BCXX C, KCFY D, JSXX E, XXMX F ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.stuSearchSql(beanIn));
			
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
	 * @FunctionName: getStuDataList
	 * @Description: 获取学生列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo3020142>
	 * @author ztz
	 * @date 2015年1月13日 下午4:15:26
	 * @update ztz at 2015年1月14日 下午12:19:24
	 */
	public List<Pojo3020142> getStuDataList(Pojo3020142 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo3020142> result = null;
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.XSXX_XSID, ");//学生ID
			strbuf.append("     A.XSXX_XSBM, ");//学生编码
			strbuf.append("     A.XSXX_XSXM, ");//学生姓名
			strbuf.append("     A.XSXX_LXFS, ");//联系方式
			strbuf.append("     A.XSXX_ZZ, ");//住址
			strbuf.append("     F.XXMX_JSRID AS JSRID ");//住址
			strbuf.append(" FROM ");
			strbuf.append("     XSXX A, BCXS B, BCXX C, KCFY D, JSXX E, XXMX F ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.stuSearchSql(beanIn));
			strbuf.append(" GROUP BY ");
			strbuf.append("   A.XSXX_XSID, A.XSXX_XSBM, A.XSXX_XSXM, A.XSXX_LXFS, A.XSXX_ZZ, F.XXMX_JSRID");

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo3020142>> rs = new BeanListHandler<Pojo3020142>(
					Pojo3020142.class);
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
	 * @FunctionName: stuSearchSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年1月13日 下午4:17:34
	 * @update ztz at 2015年1月14日 下午12:19:34
	 */
	private String stuSearchSql(Pojo3020142 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.XSXX_XSID = B.BCXS_XSID");
			strbuf.append(" AND B.BCXS_BCID = C.BCXX_BCID");
			strbuf.append(" AND C.BCXX_KCFYID = D.KCFY_FYID");
			strbuf.append(" AND D.KCFY_JSID = E.JSXX_JSID");
			strbuf.append(" AND A.XSXX_XSID = F.XXMX_JSRID(+)");
			strbuf.append(" AND A.XSXX_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND B.BCXS_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND C.BCXX_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND D.KCFY_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND E.JSXX_SCBZ = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND F.XXMX_SCBZ(+) = '0'");//删除标志（0-正常 1-删除）
			strbuf.append(" AND E.JSXX_JSID = '").append(beanIn.getJSBM()).append("'");//教师编码
			strbuf.append(" AND F.XXMX_XXID(+) = '").append(MyStringUtils.safeToString(beanIn.getXXID())).append("'");//信息ID
			if (MyStringUtils.isNotBlank(beanIn.getBCID())) {
				if (!"000".equals(beanIn.getBCID())) {
					strbuf.append(" AND C.BCXX_BCID = '").append(beanIn.getBCID()).append("'");
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
	 * @FunctionName: insertData
	 * @Description: 新增发布消息、消息明细数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月14日 下午12:38:24
	 */
	public boolean insertData(Pojo3020140 beanIn,String uuid) throws Exception {
		boolean result = false;
		int count_XXXX = 0;
		int count_XXMX = 0;
		String xxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，生成ID
		StringBuffer strbuf_XXMX = null;
		String mxid = null;
		
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
			strbuf_XXXX.append("     '"+xxid+"', ");//消息ID
			strbuf_XXXX.append("     '"+uuid+"', ");//发布人
			strbuf_XXXX.append("     '"+beanIn.getXXXX_FBZT()+"', ");//发布主题
			strbuf_XXXX.append("     '"+beanIn.getXXXX_FBNR()+"', ");//发布内容
			strbuf_XXXX.append("     '"+beanIn.getXXXX_FBSJ()+"', ");//发布时间
			strbuf_XXXX.append("     '"+beanIn.getXXXX_YXSJ()+"', ");//有效时间
			strbuf_XXXX.append("     '0', ");//删除标志（0-正常 1-删除）
			strbuf_XXXX.append("     '"+beanIn.getXXXX_CJR()+"', ");//创建人
			strbuf_XXXX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
			strbuf_XXXX.append("     '"+beanIn.getXXXX_CJR()+"', ");//更新人
			strbuf_XXXX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
			strbuf_XXXX.append(" ) ");
			count_XXXX = db.executeSQL(strbuf_XXXX);	
			/* 向XXXX表插入数据End */
			/* 向XXMX表插入数据Start */
			String stuInfos = beanIn.getSTUINFO().substring(0, beanIn.getSTUINFO().length() - 1);
			String[] stuInfo = stuInfos.split(",");
			for(int i = 0; i < stuInfo.length; i++) {
				strbuf_XXMX = new StringBuffer();
				mxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，生成ID
				strbuf_XXMX.append(" INSERT INTO ");
				strbuf_XXMX.append("     XXMX ");
				strbuf_XXMX.append(" ( ");
				strbuf_XXMX.append("     XXMX_MXID, ");//消息明细ID
				strbuf_XXMX.append("     XXMX_XXID, ");//消息ID
				strbuf_XXMX.append("     XXMX_JSRID, ");//接收人ID
				strbuf_XXMX.append("     XXMX_JSR, ");//接收人
				strbuf_XXMX.append("     XXMX_LXFS, ");//联系方式
				strbuf_XXMX.append("     XXMX_CKZT, ");//查看状态（0-未查看 1-已查看）
				strbuf_XXMX.append("     XXMX_SCBZ, ");//删除标志（0-正常 1-删除）
				strbuf_XXMX.append("     XXMX_CJR, ");//创建人
				strbuf_XXMX.append("     XXMX_CJSJ, ");//创建时间
				strbuf_XXMX.append("     XXMX_GXR, ");//更新人
				strbuf_XXMX.append("     XXMX_GXSJ  ");//更新时间
				strbuf_XXMX.append(" ) ");
				strbuf_XXMX.append(" VALUES ");
				strbuf_XXMX.append(" ( ");
				strbuf_XXMX.append("     '"+mxid+"', ");//消息明细ID
				strbuf_XXMX.append("     '"+xxid+"', ");//消息ID
				strbuf_XXMX.append("     '"+stuInfo[i].split("-")[0]+"', ");//接收人ID
				strbuf_XXMX.append("     '"+stuInfo[i].split("-")[1]+"', ");//接收人
				strbuf_XXMX.append("     '"+stuInfo[i].split("-")[2]+"', ");//联系方式
				strbuf_XXMX.append("     '0', ");//查看状态（0-未查看 1-已查看）
				strbuf_XXMX.append("     '0', ");//删除标志（0-正常 1-删除）
				strbuf_XXMX.append("     '"+beanIn.getXXXX_CJR()+"', ");//创建人
				strbuf_XXMX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
				strbuf_XXMX.append("     '"+beanIn.getXXXX_CJR()+"', ");//更新人
				strbuf_XXMX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
				strbuf_XXMX.append(" ) ");
				count_XXMX += db.executeSQL(strbuf_XXMX);
			}
			/* 向XXMX表插入数据End */
			if(count_XXXX > 0 && count_XXMX > 0 && stuInfo.length == count_XXMX) {
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
	 * @FunctionName: updateData
	 * @Description: 更新发布消息、消息明细数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月14日 下午1:29:56
	 */
	public boolean updateData(Pojo3020140 beanIn) throws Exception {
		boolean result = false;
		int count_XXXX = 0;
		int count_XXMX = 0;
		StringBuffer strbuf_XXMX = null;
		String mxid = null;
		
		try {
			db.openConnection();
			/* 更新XXXX表数据Start */
			StringBuffer strbuf_XXXX = new StringBuffer();
			strbuf_XXXX.append(" UPDATE ");
			strbuf_XXXX.append("     XXXX ");
			strbuf_XXXX.append(" SET ");
			strbuf_XXXX.append("     XXXX_FBZT='").append(beanIn.getXXXX_FBZT()).append("',");// 发布主题
			strbuf_XXXX.append("     XXXX_FBNR='").append(beanIn.getXXXX_FBNR()).append("',");// 发布内容
			strbuf_XXXX.append("     XXXX_FBSJ='").append(beanIn.getXXXX_FBSJ()).append("',");// 发布时间
			strbuf_XXXX.append("     XXXX_YXSJ='").append(beanIn.getXXXX_YXSJ()).append("',");// 有效时间
			strbuf_XXXX.append("     XXXX_GXR='").append(beanIn.getXXXX_GXR()).append("',");// 更新人
			strbuf_XXXX.append("     XXXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");// 更新时间
			strbuf_XXXX.append(" WHERE 1 = 1");
			strbuf_XXXX.append(" AND XXXX_SCBZ = '0'");//删除标志（0-正常 1-删除） 
			strbuf_XXXX.append(" AND XXXX_XXID='").append(beanIn.getXXXX_XXID()).append("'");// 消息ID
			count_XXXX = db.executeSQL(strbuf_XXXX);		
			/* 更新XXXX表数据End */
			/* 删除XXMX表原数据Start */
			StringBuffer strbufDel = new StringBuffer();
			strbufDel.append(" DELETE ");
			strbufDel.append("     XXMX ");
			strbufDel.append(" WHERE ");
			strbufDel.append("     XXMX_XXID = '").append(beanIn.getXXXX_XXID()).append("'");
			db.executeSQL(strbufDel);
			/* 删除XXMX表原数据End */
			/* 向XXMX表插入新数据Start */
			String stuInfos = beanIn.getSTUINFO().substring(0, beanIn.getSTUINFO().length() - 1);
			String[] stuInfo = stuInfos.split(",");
			for(int i = 0; i < stuInfo.length; i++) {
				strbuf_XXMX = new StringBuffer();
				mxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，生成ID
				strbuf_XXMX.append(" INSERT INTO ");
				strbuf_XXMX.append("     XXMX ");
				strbuf_XXMX.append(" ( ");
				strbuf_XXMX.append("     XXMX_MXID, ");//消息明细ID
				strbuf_XXMX.append("     XXMX_XXID, ");//消息ID
				strbuf_XXMX.append("     XXMX_JSRID, ");//接收人ID
				strbuf_XXMX.append("     XXMX_JSR, ");//接收人
				strbuf_XXMX.append("     XXMX_LXFS, ");//联系方式
				strbuf_XXMX.append("     XXMX_CKZT, ");//查看状态（0-未查看 1-已查看）
				strbuf_XXMX.append("     XXMX_SCBZ, ");//删除标志（0-正常 1-删除）
				strbuf_XXMX.append("     XXMX_CJR, ");//创建人
				strbuf_XXMX.append("     XXMX_CJSJ, ");//创建时间
				strbuf_XXMX.append("     XXMX_GXR, ");//更新人
				strbuf_XXMX.append("     XXMX_GXSJ  ");//更新时间
				strbuf_XXMX.append(" ) ");
				strbuf_XXMX.append(" VALUES ");
				strbuf_XXMX.append(" ( ");
				strbuf_XXMX.append("     '"+mxid+"', ");//消息明细ID
				strbuf_XXMX.append("     '"+beanIn.getXXXX_XXID()+"', ");//消息ID
				strbuf_XXMX.append("     '"+stuInfo[i].split("-")[0]+"', ");//接收人ID
				strbuf_XXMX.append("     '"+stuInfo[i].split("-")[1]+"', ");//接收人
				strbuf_XXMX.append("     '"+stuInfo[i].split("-")[2]+"', ");//联系方式
				strbuf_XXMX.append("     '0', ");//查看状态（0-未查看 1-已查看）
				strbuf_XXMX.append("     '0', ");//删除标志（0-正常 1-删除）
				strbuf_XXMX.append("     '"+beanIn.getXXXX_GXR()+"', ");//创建人
				strbuf_XXMX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS'), ");//创建时间
				strbuf_XXMX.append("     '"+beanIn.getXXXX_GXR()+"', ");//更新人
				strbuf_XXMX.append("     TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')  ");//更新时间
				strbuf_XXMX.append(" ) ");
				count_XXMX += db.executeSQL(strbuf_XXMX);
			}
			/* 向XXMX表插入新数据End */
			if(count_XXXX > 0 && count_XXMX > 0 && stuInfo.length == count_XXMX) {
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
	 * @FunctionName: deleteData
	 * @Description: 删除发布消息、消息明细数据
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月13日 下午3:33:58
	 */
	public boolean deleteData(Pojo_XXXX beanIn) throws Exception {
		boolean result = false;
		int searchCount = 0;
		int delCount_XXXX = 0;
		int delCount_XXMX = 0;
		
		try {
			searchCount = this.getIndexDetailDataCount(beanIn.getXXXX_XXID(), "DELETE");//获取当前已发送的信息总条数
			db.openConnection();
			
			/* 删除XXXX表数据Start */
			StringBuffer strbuf_XXXX = new StringBuffer();
			strbuf_XXXX.append(" UPDATE ");
			strbuf_XXXX.append("     XXXX ");
			strbuf_XXXX.append(" SET ");
			strbuf_XXXX.append("     XXXX_SCBZ='1',");//删除标志（0-正常 1-删除） 
			strbuf_XXXX.append("     XXXX_GXR='").append(beanIn.getXXXX_GXR()).append("',");// 更新人
			strbuf_XXXX.append("     XXXX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");// 更新时间
			strbuf_XXXX.append(" WHERE ");
			strbuf_XXXX.append("     XXXX_XXID='").append(beanIn.getXXXX_XXID()).append("'");//消息ID
			delCount_XXXX = db.executeSQL(strbuf_XXXX);
			/* 删除XXXX表数据End */
			/* 删除XXMX表数据Start */
			StringBuffer strbuf_XXMX = new StringBuffer();
			strbuf_XXMX.append(" UPDATE ");
			strbuf_XXMX.append("     XXMX ");
			strbuf_XXMX.append(" SET ");
			strbuf_XXMX.append("     XXMX_SCBZ='1',");//删除标志（0-正常 1-删除） 
			strbuf_XXMX.append("     XXMX_GXR='").append(beanIn.getXXXX_GXR()).append("',");// 更新人
			strbuf_XXMX.append("     XXMX_GXSJ=TO_CHAR(SYSDATE, 'YYYYMMDD HH24:MI:SS')");// 更新时间
			strbuf_XXMX.append(" WHERE ");
			strbuf_XXMX.append("     XXMX_XXID='").append(beanIn.getXXXX_XXID()).append("'");//消息ID
			delCount_XXMX = db.executeSQL(strbuf_XXMX);
			/* 删除XXMX表数据End */
			if (delCount_XXXX > 0 && delCount_XXMX > 0 && delCount_XXMX == searchCount) {
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