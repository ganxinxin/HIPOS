package com.alibaba.dubbo.demo;

import java.util.List;
import com.alibaba.dubbo.demo.bean.SysFault;

public interface SysFaultInterface {
	
	/**
	 * 查询时间段内未完成的故障信息
	 * @author wliudm
	 * @param wxid：微信账号
	 * @param startdate：开始时间
	 * @param endate：结束时间
	 * @return 故障信息列表
	 */
	public List<SysFault> querySysFaultList(String wxid,String startdate,String endate);
	
	/**
	 * 获取故障详细信息
	 * @param id：故障ID
	 * @return 故障类
	 */
	public SysFault queryOneFault(int id);
	
	/**
	 * 更新故障信息
	 */
	public boolean updateFault(SysFault sysFault);
}
