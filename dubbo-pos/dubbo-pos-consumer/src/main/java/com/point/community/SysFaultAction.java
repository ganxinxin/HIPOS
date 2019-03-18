package com.point.community;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.demo.SysFaultInterface;
import com.alibaba.dubbo.demo.bean.SysFault;
import com.alibaba.dubbo.demo.provider.utils.CommUtils;
import com.alibaba.dubbo.demo.provider.utils.VeDate;

import net.sf.json.JSONObject;

public class SysFaultAction extends JsonAction {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(SysFaultAction.class);
	private String msg = "";
	private boolean success = false;

	@Autowired
	SysFaultInterface sysFaultService;
	
	SysFault sysfault;
	List<SysFault> sysfaultlist = new ArrayList<SysFault>();
	
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
	public SysFaultInterface getSysFaultService() {
		return sysFaultService;
	}
	public void setSysFaultService(SysFaultInterface sysFaultService) {
		this.sysFaultService = sysFaultService;
	}
	public SysFault getSysfault() {
		return sysfault;
	}
	public void setSysfault(SysFault sysfault) {
		this.sysfault = sysfault;
	}
	public List<SysFault> getSysfaultlist() {
		return sysfaultlist;
	}
	public void setSysfaultlist(List<SysFault> sysfaultlist) {
		this.sysfaultlist = sysfaultlist;
	}
	
	public String queryFaultList()
	{
		JSONObject jsonObj = getJsonObjFromRequest();
		String weixinid="",startdate="",enddate="";
		if(jsonObj.containsKey("weixinid"))
		{
			weixinid = jsonObj.getString("weixinid");
		}
		if(jsonObj.containsKey("startdate"))
		{
			startdate = jsonObj.getString("startdate");
		}
		if(jsonObj.containsKey("enddate"))
		{
			enddate = jsonObj.getString("enddate");
		}
		sysfaultlist = sysFaultService.querySysFaultList(weixinid, startdate, enddate);
		if(sysfaultlist==null)
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
	
	public String queryOneFault()
	{
		JSONObject jsonObj = getJsonObjFromRequest();
		String sid="";
		if(jsonObj.containsKey("id"))
		{
			sid = jsonObj.getString("id");
		}
		int id = 0;
		if(CommUtils.isNumeric(sid))
		{
			id= Integer.parseInt(sid);
		}
		else
		{
			success = false;
			msg = "id不合法！";
			return SUCCESS;
		}
		sysfault = sysFaultService.queryOneFault(id);
		if(sysfault==null)
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
	
	public String updateFaultDealFlag()
	{
		JSONObject jsonObj = getJsonObjFromRequest();
		String faultid="",dealmethod="",dealdatetime="",dealpersion="",isdeal="";
		if(jsonObj.containsKey("faultid"))
		{
			faultid = jsonObj.getString("faultid");
		}
		int id = 0;
		if(CommUtils.isNumeric(faultid))
		{
			id= Integer.parseInt(faultid);
		}
		else
		{
			success = false;
			msg = "id不合法！";
			return SUCCESS;
		}
		if(jsonObj.containsKey("dealmethod"))
		{
			dealmethod = jsonObj.getString("dealmethod");
		}
		if(jsonObj.containsKey("dealdatetime"))
		{
			dealdatetime = jsonObj.getString("dealdatetime");
		}
		if(jsonObj.containsKey("dealpersion"))
		{
			dealpersion = jsonObj.getString("dealpersion");
		}
		sysfault = sysFaultService.queryOneFault(id);
		if(sysfault==null)
		{
			success = false;
			msg = "无数据！";
			return SUCCESS;
		}
		sysfault.setDealmethod(dealmethod);
		sysfault.setDealdatetime(VeDate.Str2Timestamp(dealdatetime));
		sysfault.setDealpersion(dealpersion);
		sysfault.setIsdeal(1);
		if(sysFaultService.updateFault(sysfault))
		{
			success = true;
			msg = "更新数据成功！";		
		}
		else
		{
			success = false;
			msg = "无数据！";
		}
		return SUCCESS;
	}
}
