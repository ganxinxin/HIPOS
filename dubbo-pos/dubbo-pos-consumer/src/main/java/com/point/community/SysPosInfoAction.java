package com.point.community;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.demo.SysPosInfoInterface;
import com.alibaba.dubbo.demo.SysShopInfoInterface;
import com.alibaba.dubbo.demo.bean.SysPosinfo;
import com.alibaba.dubbo.demo.bean.SysShopinfo;

import net.sf.json.JSONObject;

public class SysPosInfoAction extends JsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(SysPosInfoAction.class);
	private String msg = "";
	private boolean success = false;
	
	@Autowired
	SysPosInfoInterface sysPosInfoService;
	
	@Autowired
	SysShopInfoInterface sysShopInfoService;
	
	SysPosinfo posinfo = new SysPosinfo();
	SysPosinfo posstatus = new SysPosinfo();
	SysShopinfo shopinfo = new SysShopinfo();
	List<SysPosinfo> posinfolist = new ArrayList<SysPosinfo>();
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
	
	public SysPosInfoInterface getSysPosInfoService() {
		return sysPosInfoService;
	}
	public void setSysPosInfoService(SysPosInfoInterface sysPosInfoService) {
		this.sysPosInfoService = sysPosInfoService;
	}
		
	public SysPosinfo getPosinfo() {
		return posinfo;
	}
	public void setPosinfo(SysPosinfo posinfo) {
		this.posinfo = posinfo;
	}
	public SysShopinfo getShopinfo() {
		return shopinfo;
	}
	public void setShopinfo(SysShopinfo shopinfo) {
		this.shopinfo = shopinfo;
	}
	public SysPosinfo getPosstatus() {
		return posstatus;
	}
	public void setPosstatus(SysPosinfo posstatus) {
		this.posstatus = posstatus;
	}
	public List<SysPosinfo> getPosinfolist() {
		return posinfolist;
	}
	public void setPosinfolist(List<SysPosinfo> posinfolist) {
		this.posinfolist = posinfolist;
	}
	public String registerPosInfo()
	{
		JSONObject jsonObj = getJsonObjFromRequest();
		String possn="",posname="",setupaddr="",poscode="",shopid="",weixinid="";
		if(jsonObj.containsKey("possn"))
		{
			possn = jsonObj.getString("possn");
		}
		if(jsonObj.containsKey("posname"))
		{
			posname = jsonObj.getString("posname");
		}
		if(jsonObj.containsKey("setupaddr"))
		{
			setupaddr = jsonObj.getString("setupaddr");
		}
		if(jsonObj.containsKey("poscode"))
		{
			poscode = jsonObj.getString("poscode");
		}
		if(jsonObj.containsKey("shopid"))
		{
			shopid = jsonObj.getString("shopid");
		}
		if(jsonObj.containsKey("weixinid"))
		{
			weixinid = jsonObj.getString("weixinid");
		}
		if(possn.isEmpty())
		{
			success = false;
			msg = "possn不能为空！";						
			return SUCCESS;
		}
		if(shopid.isEmpty())
		{
			success = false;
			msg = "shopid不能为空！";						
			return SUCCESS;
		}
		if(weixinid.isEmpty())
		{
			success = false;
			msg = "weixin不能为空！";						
			return SUCCESS;
		}
		
		SysPosinfo pos = new SysPosinfo();
		pos.setPossn(possn);
		pos.setPosname(posname);
		pos.setPoscode(poscode);
		pos.setSetupaddr(setupaddr);
		pos.setShopid(Integer.parseInt(shopid));
		pos.setWeixin(weixinid);
		if(sysPosInfoService.saveSysPosinfo(pos))
		{
			success = true;
			msg = "机器注册成功！";
		}
		else
		{
			success = false;
			msg = "机器注册失败！";
		}
		return SUCCESS;
	}

	public String queryPosInfo()
	{
		JSONObject jsonObj = getJsonObjFromRequest();
		String possn="";
		if(jsonObj.containsKey("possn"))
		{
			possn = jsonObj.getString("possn");
		}
		if(possn.isEmpty())
		{
			success = false;
			msg = "possn不能为空！";						
			return SUCCESS;
		}
		success = true;
		msg = "查询数据成功！";		
		posinfo = sysPosInfoService.queryOneSysPosinfo(possn);
		shopinfo = sysShopInfoService.queryOneSysShopinfo(posinfo.getShopid());
		posstatus = posinfo;
		return SUCCESS;
	}
	
	public String queryPosList()
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
		posinfolist = sysPosInfoService.querySysPosinfoListByWeiXin(weixin);
		if(posinfolist==null)
		{
			success = false;
			msg = "无数据！";						
			return SUCCESS;
		}
		success = true;
		msg = "查询数据成功！";		
		return SUCCESS;
	} 
	
	public String queryPosStatus()
	{
		JSONObject jsonObj = getJsonObjFromRequest();
		String id="";
		if(jsonObj.containsKey("id"))
		{
			id = jsonObj.getString("id");
		}
		if(id.isEmpty())
		{
			success = false;
			msg = "id不能为空！";						
			return SUCCESS;
		}		
		posstatus = sysPosInfoService.queryOneSysPosinfoByID(Integer.parseInt(id));
		success = true;
		msg = "查询数据成功！";	
		return SUCCESS;
	}
}
