package com.alibaba.dubbo.demo.provider.dao.impl;

import java.util.List;

import com.alibaba.dubbo.demo.SysShopInfoInterface;
import com.alibaba.dubbo.demo.bean.SysShopinfo;

public class SysShopInfoDaoImpl extends BaseDAO implements SysShopInfoInterface {

	@SuppressWarnings("unchecked")
	@Override
	public List<SysShopinfo> querySysShopInfoList(String wxid) {
		// TODO Auto-generated method stub
		String msql = "from SysShopinfo where managerweixin='"+wxid+"'";
		return queryBean(msql);
	}

	@Override
	public SysShopinfo queryOneSysShopinfo(int id) {
		// TODO Auto-generated method stub
		String msql = "from SysShopinfo where shopid="+id;
		return (SysShopinfo) queryBean(msql).get(0);
	}
}
