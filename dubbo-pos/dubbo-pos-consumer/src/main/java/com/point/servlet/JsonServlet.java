package com.point.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

//import com.alibaba.dubbo.common.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class JsonServlet extends GenericServlet implements Servlet {

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletInputStream in;
		JSONObject jsonObj = null;
		try {
			in = req.getInputStream();

			BufferedInputStream buf = new BufferedInputStream(in);
			StringBuffer info = new StringBuffer();
			byte[] buffer = new byte[1024];
			int iRead;
			while ((iRead = buf.read(buffer)) != -1) {
				info.append(new String(buffer, 0, iRead, "UTF-8"));
			}

			if (info != null && !info.toString().equals("")) {
				String s = URLDecoder.decode(info.toString());
				System.out.println("jsonStr" + s);
				jsonObj = JSONObject.fromObject(s);
			} else {
				System.out.println("info is null!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
