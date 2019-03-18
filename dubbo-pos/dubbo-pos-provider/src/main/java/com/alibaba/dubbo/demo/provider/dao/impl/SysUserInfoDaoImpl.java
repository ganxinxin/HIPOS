package com.alibaba.dubbo.demo.provider.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.dubbo.demo.SysUserInfoInterface;
import com.alibaba.dubbo.demo.bean.PageBean;
import com.alibaba.dubbo.demo.bean.SysUserinfo;

public class SysUserInfoDaoImpl extends BaseDAO implements SysUserInfoInterface {

	Logger logger = Logger.getLogger(SysUserInfoDaoImpl.class);

	@Override
	public SysUserinfo getUserInfoByWeiXinID(String wxid) {
		// TODO Auto-generated method stub
		String msql = "from SysUserinfo where weixinid='"+wxid+"'";
		System.out.print("getUserInfoByWeiXinID:"+msql);
		return (SysUserinfo) queryBean(msql).get(0);
	}

	@Override
	public SysUserinfo getUserInfoByID(String userid) {
		// TODO Auto-generated method stub
		String msql = "from SysUserinfo where userid="+userid;
		return (SysUserinfo) queryBean(msql).get(0);
	}

	@Override
	public SysUserinfo getUserInfoByUserCode(String userCode) {
		// TODO Auto-generated method stub
		String msql = "from SysUserinfo where usercode='"+userCode+"'";
		return (SysUserinfo) queryBean(msql).get(0);
	}
}
