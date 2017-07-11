package com.xsjy.servlet.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.framework.core.BaseServlet;
import com.xsjy.common.FileService;

/**
 * Servlet implementation class ImgUploadServlet
 */
@WebServlet("/ImgUploadServlet")
public class ImgUploadServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public static final String JSON = "application/json";

	/**
	 * @see BaseServlet#BaseServlet()
	 */
	public ImgUploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		Date now = new Date(); 
		String name = request.getParameter("name");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
		String date = dateFormat.format( now ); 
		String filename = date + ".JPG";
		String FileDir = "";
		String projpath = "";
		//String returnpath = "";
		FileDir = "/project/upload/"+name+"/";// 文件路径结构
		projpath = request.getSession().getServletContext().getRealPath("/bin/upload/"+name+"/");
		//returnpath = "/bin/upload/"+name+"/"+filename;
		String responseString = FileDir + "/" + filename;
		if (ServletFileUpload.isMultipartContent(request)) {
			ServletFileUpload upload = new ServletFileUpload();
			try {

				FileItemIterator iter = upload.getItemIterator(request);
				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					InputStream input = item.openStream();
					if (item.isFormField()) {
						// String fileName = item.getFieldName();
						// String value = filename; //
						// 上传文件原名:Streams.asString(input);
					} else {
						// String fileDir = this.getUploadPath() + FileDir;
						String fileDir = projpath;
						File dstFile = new File(fileDir);
						if (!dstFile.exists()) {
							//dstFile.delete();
							dstFile.mkdirs();
						}
						File dst = new File(dstFile.getPath() + "/" + filename);
						new FileService().saveUploadFile(input, dst);
						responseString = filename;
					}
				}

			} catch (Exception e) {
				// responseString = RESP_ERROR;
				e.printStackTrace();
			}

			//double randomNum = Math.random();
			//responseString = responseString + "?id=" + randomNum;
			response.setContentType(JSON);
			byte[] responseBytes = responseString.getBytes();
			response.setContentLength(responseBytes.length);
			ServletOutputStream output = response.getOutputStream();
			output.write(responseBytes);
			output.flush();

		}
	}

}
