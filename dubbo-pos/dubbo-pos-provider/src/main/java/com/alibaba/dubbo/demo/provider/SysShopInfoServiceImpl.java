package com.alibaba.dubbo.demo.provider;

import java.util.List;

import com.alibaba.dubbo.demo.SysShopInfoInterface;
import com.alibaba.dubbo.demo.bean.SysShopinfo;
import com.alibaba.dubbo.demo.provider.dao.impl.SysShopInfoDaoImpl;

public class SysShopInfoServiceImpl implements SysShopInfoInterface {

	SysShopInfoDaoImpl sysShopInfoDao;
	
	
	public SysShopInfoDaoImpl getSysShopInfoDao() {
		return sysShopInfoDao;
	}

	public void setSysShopInfoDao(SysShopInfoDaoImpl sysShopInfoDao) {
		this.sysShopInfoDao = sysShopInfoDao;
	}

	@Override
	public List<SysShopinfo> querySysShopInfoList(String wxid) {
		// TODO Auto-generated method stub
		return sysShopInfoDao.querySysShopInfoList(wxid);
	}

	@Override
	public SysShopinfo queryOneSysShopinfo(int id) {
		// TODO Auto-generated method stub
		return sysShopInfoDao.queryOneSysShopinfo(id);
	}

}
