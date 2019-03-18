package com.alibaba.dubbo.demo.provider;

import java.util.List;

import com.alibaba.dubbo.demo.SysAlarmInterface;
import com.alibaba.dubbo.demo.bean.SysAlarm;
import com.alibaba.dubbo.demo.provider.dao.impl.SysAlarmDaoImpl;

public class SysAlarmServiceImpl implements SysAlarmInterface {

	SysAlarmDaoImpl sysAlarmDao;
	
	public SysAlarmDaoImpl getSysAlarmDao() {
		return sysAlarmDao;
	}

	public void setSysAlarmDao(SysAlarmDaoImpl sysAlarmDao) {
		this.sysAlarmDao = sysAlarmDao;
	}

	@Override
	public List<SysAlarm> querySysAlarmList(String wxid, String startdate, String endate) {
		// TODO Auto-generated method stub
		return sysAlarmDao.querySysAlarmList(wxid, startdate, endate);
	}

	@Override
	public SysAlarm queryOneAlarm(int id) {
		// TODO Auto-generated method stub
		return sysAlarmDao.queryOneAlarm(id);
	}

	@Override
	public boolean updateAlarm(SysAlarm sysAlarm) {
		// TODO Auto-generated method stub
		return sysAlarmDao.updateAlarm(sysAlarm);
	}
	
}
