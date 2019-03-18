package com.alibaba.dubbo.demo.provider.dao.impl;

import java.util.List;

import com.alibaba.dubbo.demo.SysFaultInterface;
import com.alibaba.dubbo.demo.bean.SysFault;
import com.alibaba.dubbo.demo.bean.SysShopinfo;

public class SysFaultDaoImpl extends BaseDAO implements SysFaultInterface {

	
	@Override
	public List<SysFault> querySysFaultList(String wxid, String startdate, String endate) {
		// TODO Auto-generated method stub
		String msql = "from SysFault  where 1=1 ";
		msql +=" and weixinid ='"+wxid+"'";
		
		if(!startdate.isEmpty())
			msql +=" and datetime >= "+startdate;
		if(!endate.isEmpty())
			msql +=" and datetime <= "+endate;
		
		return queryBean(msql);
	}

	@Override
	public SysFault queryOneFault(int id) {
		// TODO Auto-generated method stub
		String msql = "from SysFault where faultid="+id;
		return (SysFault) queryBean(msql).get(0);
	}

	@Override
	public boolean updateFault(SysFault sysFault) {
		// TODO Auto-generated method stub
		try
		{
			getHibernateTemplate().update(sysFault);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

}
