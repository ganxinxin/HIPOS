package com.alibaba.dubbo.demo.provider;

import java.util.List;

import com.alibaba.dubbo.demo.SysUserInfoInterface;
import com.alibaba.dubbo.demo.bean.PageBean;
import com.alibaba.dubbo.demo.bean.SysUserinfo;
import com.alibaba.dubbo.demo.provider.dao.impl.SysUserInfoDaoImpl;

public class SysUserInfoServiceImpl implements SysUserInfoInterface {

	SysUserInfoDaoImpl sysUserInfoDao;
	
	public SysUserInfoDaoImpl getSysUserInfoDao() {
		return sysUserInfoDao;
	}

	public void setSysUserInfoDao(SysUserInfoDaoImpl sysUserInfoDao) {
		this.sysUserInfoDao = sysUserInfoDao;
	}

	@Override
	public SysUserinfo getUserInfoByWeiXinID(String wxid) {
		// TODO Auto-generated method stub
		return sysUserInfoDao.getUserInfoByWeiXinID(wxid);
	}

	@Override
	public SysUserinfo getUserInfoByID(String userid) {
		// TODO Auto-generated method stub
		return sysUserInfoDao.getUserInfoByID(userid);
	}

	@Override
	public SysUserinfo getUserInfoByUserCode(String userCode) {
		// TODO Auto-generated method stub
		return sysUserInfoDao.getUserInfoByUserCode(userCode);
	}

}
