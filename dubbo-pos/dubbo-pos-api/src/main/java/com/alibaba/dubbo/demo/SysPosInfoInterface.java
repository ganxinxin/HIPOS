package com.alibaba.dubbo.demo;

import java.util.List;

import com.alibaba.dubbo.demo.bean.SysPosinfo;

public interface SysPosInfoInterface {

	/**
	 * 查询门店内的机器列表
	 * @author wliudm
	 * @param shopid：门店ID
	 * @return 机器列表
	 */
	public List<SysPosinfo> querySysPosinfoList(int shopid);
	public List<SysPosinfo> querySysPosinfoListByWeiXin(String wxid);
	/**
	 * 根据机器ID查询机器详细信息
	 * @author wliudm
	 * @param id ：机器ID
	 */
	SysPosinfo queryOneSysPosinfoByID(int id);		
	SysPosinfo queryOneSysPosinfo(String possn);		
	/**
	 * 保存机器信息
	 * @param sysPosInfo
	 * @return
	 */
	boolean saveSysPosinfo(SysPosinfo sysPosInfo);
}
