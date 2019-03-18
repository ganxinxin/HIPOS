package com.point.community;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.demo.bean.PageBean;
import com.alibaba.dubbo.demo.SysUserInfoInterface;
import com.alibaba.dubbo.demo.bean.SysUserinfo;
import com.point.community.bean.dwzPageData;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONObject;

public class SysUserInfoAction extends JsonAction {
	Logger logger = Logger.getLogger(SysUserInfoAction.class);
	
	private String msg = "";
	private boolean success = false;

	private SysUserinfo sysuserinfo = new SysUserinfo();
	
	@Autowired
	private SysUserInfoInterface sysUserInfoService;

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public SysUserinfo getSysuserinfo() {
		return sysuserinfo;
	}
	public void setSysuserinfo(SysUserinfo sysuserinfo) {
		this.sysuserinfo = sysuserinfo;
	}
	
	public SysUserInfoInterface getSysUserInfoService() {
		return sysUserInfoService;
	}
	public void setSysUserInfoService(SysUserInfoInterface sysUserInfoService) {
		this.sysUserInfoService = sysUserInfoService;
	}
	
	public String checkUser()
	{
		JSONObject jsonObj = getJsonObjFromRequest();
		String wxid = jsonObj.getString("weixinid");
		SysUserinfo user = sysUserInfoService.getUserInfoByWeiXinID(wxid);
		
		if(user!=null)
		{
			success = true;
			msg = "请求成功！";
		}
		else
		{
			success = false;
			msg = "请求失败或无数据！";						
		}
		return SUCCESS;
	}
	
	public String queryUser()
	{
		JSONObject jsonObj = getJsonObjFromRequest();
		String wxid = "",userid="",usercode="";
		if(jsonObj.containsKey("weixinid"))
			wxid = jsonObj.getString("weixinid");
		if(jsonObj.containsKey(userid))
			userid = jsonObj.getString("userid");
		if(jsonObj.containsKey("usercode"))
			usercode = jsonObj.getString("usercode");
		if(!wxid.isEmpty())
			sysuserinfo = sysUserInfoService.getUserInfoByWeiXinID(wxid);
		else if(!userid.isEmpty())
			sysuserinfo = sysUserInfoService.getUserInfoByID(userid);
		else if(!usercode.isEmpty())
			sysuserinfo = sysUserInfoService.getUserInfoByUserCode(usercode);
		
		if(sysuserinfo!=null)
		{
			success = true;
			msg = "请求成功！";
		}
		else
		{
			success = false;
			msg = "请求失败或无数据！";						
		}
		return SUCCESS;
	}
}
