package com.alibaba.dubbo.demo;

import java.util.List;

import com.alibaba.dubbo.demo.bean.SysShopinfo;

public interface SysShopInfoInterface {

	/**
	 * 查询微信号对应的门店列表
	 * @author wliudm
	 * @param wxid：微信账号
	 * @return 门店列表
	 */
	public List<SysShopinfo> querySysShopInfoList(String wxid);
	
	/**
	 * 根据门店ID查询门店详细信息
	 * @author wliudm
	 * @param id ：门店ID
	 */
	SysShopinfo queryOneSysShopinfo(int id);
}
