package com.point.community;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.demo.SysAlarmInterface;
import com.alibaba.dubbo.demo.bean.SysAlarm;
import com.alibaba.dubbo.demo.bean.SysFault;
import com.alibaba.dubbo.demo.provider.utils.CommUtils;
import com.alibaba.dubbo.demo.provider.utils.VeDate;

import net.sf.json.JSONObject;

public class SysAlarmAction extends JsonAction {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(SysPosInfoAction.class);
	private String msg = "";
	private boolean success = false;
	
	@Autowired
	SysAlarmInterface sysAlarmService;
	
	SysAlarm alarminfo;
	List<SysAlarm> alarmlist = new ArrayList<SysAlarm>();
	
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
	public SysAlarmInterface getSysAlarmService() {
		return sysAlarmService;
	}
	public void setSysAlarmService(SysAlarmInterface sysAlarmService) {
		this.sysAlarmService = sysAlarmService;
	}

	
	public SysAlarm getAlarminfo() {
		return alarminfo;
	}
	public void setAlarminfo(SysAlarm alarminfo) {
		this.alarminfo = alarminfo;
	}
	public List<SysAlarm> getAlarmlist() {
		return alarmlist;
	}
	public void setAlarmlist(List<SysAlarm> alarmlist) {
		this.alarmlist = alarmlist;
	}
	public String queryAlarmList()
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
		alarmlist = sysAlarmService.querySysAlarmList(weixinid, startdate, enddate);
		if(alarmlist==null)
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
	
	public String queryOneAlarm()
	{
		JSONObject jsonObj = getJsonObjFromRequest();
		String sid="",startdate="",enddate="";
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
		alarminfo = sysAlarmService.queryOneAlarm(id);
		if(alarminfo==null)
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
	
	public String dealAlarm()
	{
		JSONObject jsonObj = getJsonObjFromRequest();
		String alarmid="",dealmethod="",dealdatetime="",dealpersion="",isdeal="";
		if(jsonObj.containsKey("alarmid"))
		{
			alarmid = jsonObj.getString("alarmid");
		}
		int id = 0;
		if(CommUtils.isNumeric(alarmid))
		{
			id= Integer.parseInt(alarmid);
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
		alarminfo = sysAlarmService.queryOneAlarm(id);
		if(alarminfo==null)
		{
			success = false;
			msg = "无数据！";
			return SUCCESS;
		}
		alarminfo.setDealmethod(dealmethod);
		alarminfo.setDealdatetime(VeDate.Str2Timestamp(dealdatetime));
		alarminfo.setDealpersion(dealpersion);
		alarminfo.setIsdeal(1);
		if(sysAlarmService.updateAlarm(alarminfo))
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
