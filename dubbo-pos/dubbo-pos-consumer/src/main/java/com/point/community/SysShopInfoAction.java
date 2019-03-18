package com.point.community;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.demo.SysShopInfoInterface;
import com.alibaba.dubbo.demo.bean.SysShopinfo;

import net.sf.json.JSONObject;

public class SysShopInfoAction extends JsonAction {

	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(SysShopInfoAction.class);
	private String msg = "";
	private boolean success = false;
	
	@Autowired
	SysShopInfoInterface sysShopInfoService;
	
	SysShopinfo shopinfo;
	List<SysShopinfo> shopinfolist = new ArrayList<SysShopinfo>();
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
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
	public SysShopInfoInterface getSysShopInfoService() {
		return sysShopInfoService;
	}
	public void setSysShopInfoService(SysShopInfoInterface sysShopInfoService) {
		this.sysShopInfoService = sysShopInfoService;
	}
	
	public SysShopinfo getShopinfo() {
		return shopinfo;
	}
	public void setShopinfo(SysShopinfo shopinfo) {
		this.shopinfo = shopinfo;
	}
	public List<SysShopinfo> getShopinfolist() {
		return shopinfolist;
	}
	public void setShopinfolist(List<SysShopinfo> shopinfolist) {
		this.shopinfolist = shopinfolist;
	}
	public String queryShopList()
	{
		JSONObject jsonObj = getJsonObjFromRequest();
		String weixin="";
		if(jsonObj.containsKey("weixinid"))
		{
			weixin = jsonObj.getString("weixinid");
		}
		if(weixin.isEmpty())
		{
			success = false;
			msg = "weixin不能为空！";						
			return SUCCESS;
		}		
		shopinfolist = sysShopInfoService.querySysShopInfoList(weixin);
		if(shopinfolist==null)
		{
			success = false;
			msg = "无数据！";
		}
		else
		{
			success = true;
			msg = "查询数据成功！";		
		}
		return SUCCESS;
	}
	public String queryShopInfo()
	{
		JSONObject jsonObj = getJsonObjFromRequest();
		String shopid="";
		if(jsonObj.containsKey("shopid"))
		{
			shopid = jsonObj.getString("shopid");
		}
		shopinfo = sysShopInfoService.queryOneSysShopinfo(Integer.parseInt(shopid));
		if(shopinfo!=null)
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
