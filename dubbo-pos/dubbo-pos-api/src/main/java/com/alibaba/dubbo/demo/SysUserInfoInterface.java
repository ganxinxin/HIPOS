package com.alibaba.dubbo.demo;

import java.util.List;

import com.alibaba.dubbo.demo.bean.SysUserinfo;
import com.alibaba.dubbo.demo.bean.PageBean;

public interface SysUserInfoInterface {
	
	/**
	 * 获取用户信息
	 */
	public SysUserinfo getUserInfoByWeiXinID(String wxid);
	public SysUserinfo getUserInfoByID(String userid);
	public SysUserinfo getUserInfoByUserCode(String userCode);
}
