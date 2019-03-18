package com.point.community;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.alibaba.dubbo.demo.provider.utils.CommUtils;

/**
 * 
 * @author LangXianWei
 *
 *         May 18, 2008 2:09:38 AM
 */

public abstract class JsonAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public static final String LOGINUSER = "LOGIN_USER";
	public static final String VISITER = "VISITER";
	public static final String PAGE_MAX_RECORD_COUNT="20";
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public String getStrParam(String name)
	{
		return getParameterString(name)==null?"":getParameterString(name);
	}
	public int getIntParam(String name)
	{
		return getParameterString(name)==null?-1:getParameterInteger(name);
	}	
	public String inputStreamToString(InputStream is, String encoding) {
		try {
			byte[] b = new byte[10240];
			String res = "";
			if (is == null) {
				return "";
			}

			int bytesRead = 0;
			while (true) {
				bytesRead = is.read(b, 0, 1024); // return final read bytes
													// counts
				if (bytesRead == -1) {// end of InputStream
					return res;
				}
				res += new String(b, 0, bytesRead, encoding); // convert to
																// string using
																// bytes
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public int getParameterInt(String param) {
		return CommUtils.parseInt(request.getParameter(param));
	}

	public short getParameterShort(String param) {
		return CommUtils.parseShort(request.getParameter(param));
	}

	public Integer getParameterInteger(String param) {
		if (request.getParameter(param) == null) {
			return null;
		}
		return CommUtils.parseInt(request.getParameter(param));
	}
	
	public Float getParameterFloat(String param) {
		if (request.getParameter(param) == null) {
			return null;
		}
		return CommUtils.parseFloat(request.getParameter(param));
	}

	public BigDecimal getParameterBigDecimal(String param) {
		return CommUtils.parseBigDecimal(request.getParameter(param));
	}

	public String getParameterString(String param) {
		return request.getParameter(param);
	}

	public double getParameterDouble(String param) {
		return CommUtils.parseDouble(request.getParameter(param));
	}

	/**
	 * 从http请求中获取json字符串 add by zangdong on 20151203
	 * 
	 * @return
	 */
	public JSONObject getJsonObjFromRequest() {
		/**
		 * 获取http数据体中json数据
		 */
		ServletInputStream in;
		JSONObject jsonObj = null;
		try {
			in = request.getInputStream();

			BufferedInputStream buf = new BufferedInputStream(in);
			StringBuffer info = new StringBuffer();
			byte[] buffer = new byte[1024];
			int iRead;
			while ((iRead = buf.read(buffer)) != -1) {
				info.append(new String(buffer, 0, iRead, "UTF-8"));
			}

			if (info != null && !info.toString().equals("")) {
				String s = URLDecoder.decode(info.toString());
				System.out.println("jsonStr:" + s);
				jsonObj = JSONObject.fromObject(s);
			} else {
				System.out.println("info is null!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObj;
	}
	
	public static void httpPostWithJSON(String url, String json)
			throws Exception {
		// 将JSON进行UTF-8编码,以便传输中文
		String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		// httpPost.addHeader(HTTP.CONTENT_TYPE, "json");

		StringEntity se = new StringEntity(encoderJson);
		// se.setContentType("json");
		// se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "json"));
		httpPost.setEntity(se);
		HttpResponse responce = httpClient.execute(httpPost);
		HttpEntity httpEntity = responce.getEntity();
		System.out.println(httpEntity.getContent().toString());
		if (httpEntity != null) {
			try {
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(httpEntity.getContent(), "UTF-8"),
						8 * 1024);
				StringBuilder entityStringBuilder = new StringBuilder();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					entityStringBuilder.append(line + "/n");
				}
				System.out.println(entityStringBuilder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static byte[] ObjectToByte(java.lang.Object obj) {  
	    byte[] bytes = null;  
	    try {  
	        // object to bytearray  
	        ByteArrayOutputStream bo = new ByteArrayOutputStream();  
	        ObjectOutputStream oo = new ObjectOutputStream(bo);  
	        oo.writeObject(obj);  	  
	        bytes = bo.toByteArray();  
	        bo.close();  
	        oo.close();  
	    } catch (Exception e) {  
	        System.out.println("translation" + e.getMessage());  
	        e.printStackTrace();  
	    }  
	    return bytes;  
	}  
	
	public static String ObjectToString(java.lang.Object obj)
	{
		byte[] strByte = ObjectToByte(obj);
		String str = null;
		try {
			str = new String(strByte,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}
