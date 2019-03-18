package com.alibaba.dubbo.demo.provider.dao.impl;

import java.util.List;

import com.alibaba.dubbo.demo.bean.SysPosinfo;
import com.alibaba.dubbo.demo.SysPosInfoInterface;

public class SysPosInfoDaoImpl extends BaseDAO implements SysPosInfoInterface {

	@Override
	public List<SysPosinfo> querySysPosinfoList(int shopid) {
		// TODO Auto-generated method stub
		String msql = "from SysPosinfo where shopid="+shopid;
		return queryBean(msql);
	}

	@Override
	public SysPosinfo queryOneSysPosinfoByID(int id) {
		// TODO Auto-generated method stub
		String msql = "from SysPosinfo where posid="+id;
		return (SysPosinfo) queryBean(msql).get(0);
	}

	@Override
	public boolean saveSysPosinfo(SysPosinfo sysPosInfo) {
		// TODO Auto-generated method stub
		try
		{
			getHibernateTemplate().save(sysPosInfo);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	@Override
	public List<SysPosinfo> querySysPosinfoListByWeiXin(String wxid) {
		// TODO Auto-generated method stub
		String msql = "from SysPosinfo where weixinid='"+wxid+"'";
		return queryBean(msql);
	}

	@Override
	public SysPosinfo queryOneSysPosinfo(String possn) {
		// TODO Auto-generated method stub
		String msql = "from SysPosinfo where possn='"+possn+"'";
		return (SysPosinfo) queryBean(msql).get(0);
	}
}
