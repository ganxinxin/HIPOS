package com.alibaba.dubbo.demo.provider;

import java.util.List;

import com.alibaba.dubbo.demo.SysPosInfoInterface;
import com.alibaba.dubbo.demo.bean.SysPosinfo;
import com.alibaba.dubbo.demo.provider.dao.impl.SysPosInfoDaoImpl;

public class SysPosInfoServiceImpl implements SysPosInfoInterface {

	SysPosInfoDaoImpl sysPosInfoDao;
	
	
	public SysPosInfoDaoImpl getSysPosInfoDao() {
		return sysPosInfoDao;
	}

	public void setSysPosInfoDao(SysPosInfoDaoImpl sysPosInfoDao) {
		this.sysPosInfoDao = sysPosInfoDao;
	}

	@Override
	public List<SysPosinfo> querySysPosinfoList(int id) {
		// TODO Auto-generated method stub
		return sysPosInfoDao.querySysPosinfoList(id);
	}

	@Override
	public boolean saveSysPosinfo(SysPosinfo sysPosInfo) {
		// TODO Auto-generated method stub
		return sysPosInfoDao.saveSysPosinfo(sysPosInfo);
	}

	@Override
	public List<SysPosinfo> querySysPosinfoListByWeiXin(String wxid) {
		// TODO Auto-generated method stub
		return sysPosInfoDao.querySysPosinfoListByWeiXin(wxid);
	}

	@Override
	public SysPosinfo queryOneSysPosinfoByID(int id) {
		// TODO Auto-generated method stub
		return sysPosInfoDao.queryOneSysPosinfoByID(id);
	}

	@Override
	public SysPosinfo queryOneSysPosinfo(String possn) {
		// TODO Auto-generated method stub
		return sysPosInfoDao.queryOneSysPosinfo(possn);
	}

	
}
