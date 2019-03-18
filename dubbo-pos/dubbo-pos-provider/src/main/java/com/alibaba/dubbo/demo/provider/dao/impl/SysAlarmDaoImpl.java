package com.alibaba.dubbo.demo.provider.dao.impl;

import java.util.List;

import com.alibaba.dubbo.demo.SysAlarmInterface;
import com.alibaba.dubbo.demo.bean.SysAlarm;
import com.alibaba.dubbo.demo.bean.SysFault;

public class SysAlarmDaoImpl extends BaseDAO implements SysAlarmInterface {

	@Override
	public List<SysAlarm> querySysAlarmList(String wxid, String startdate, String endate) {
		// TODO Auto-generated method stub
		String msql = "from SysAlarm  where 1=1 ";
		msql +=" and weixinid ='"+wxid+"'";
		
		if(!startdate.isEmpty())
			msql +=" and datetime >= "+startdate;
		if(!endate.isEmpty())
			msql +=" and datetime <= "+endate;
		
		return queryBean(msql);
	}

	@Override
	public SysAlarm queryOneAlarm(int id) {
		// TODO Auto-generated method stub
		String msql = "from SysAlarm where alarmid="+id;
		return (SysAlarm) queryBean(msql).get(0);
	}

	@Override
	public boolean updateAlarm(SysAlarm sysAlarm) {
		// TODO Auto-generated method stub
		try
		{
			getHibernateTemplate().update(sysAlarm);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	

}
