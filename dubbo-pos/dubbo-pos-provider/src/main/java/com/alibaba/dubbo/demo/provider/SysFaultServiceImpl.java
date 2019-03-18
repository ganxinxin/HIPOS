package com.alibaba.dubbo.demo.provider;

import java.util.List;

import com.alibaba.dubbo.demo.SysFaultInterface;
import com.alibaba.dubbo.demo.bean.SysFault;
import com.alibaba.dubbo.demo.provider.dao.impl.SysFaultDaoImpl;

public class SysFaultServiceImpl implements SysFaultInterface {

	SysFaultDaoImpl sysFaultDao;
	
	
	public SysFaultDaoImpl getSysFaultDao() {
		return sysFaultDao;
	}

	public void setSysFaultDao(SysFaultDaoImpl sysFaultDao) {
		this.sysFaultDao = sysFaultDao;
	}

	@Override
	public List<SysFault> querySysFaultList(String wxid, String startdate, String endate) {
		// TODO Auto-generated method stub
		return sysFaultDao.querySysFaultList(wxid, startdate, endate);
	}

	@Override
	public SysFault queryOneFault(int id) {
		// TODO Auto-generated method stub
		return sysFaultDao.queryOneFault(id);
	}

	@Override
	public boolean updateFault(SysFault sysFault) {
		// TODO Auto-generated method stub
		return sysFaultDao.updateFault(sysFault);
	}

	

}
