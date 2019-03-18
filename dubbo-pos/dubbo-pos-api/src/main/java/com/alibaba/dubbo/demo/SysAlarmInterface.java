package com.alibaba.dubbo.demo;

import java.util.List;

import com.alibaba.dubbo.demo.bean.SysAlarm;

public interface SysAlarmInterface {
	/**
	 * 查询时间段内未完成的报警信息
	 * @author wliudm
	 * @param wxid：微信账号
	 * @param startdate：开始时间
	 * @param endate：结束时间
	 * @return 报警信息列表
	 */
	public List<SysAlarm> querySysAlarmList(String wxid,String startdate,String endate);
	
	/**
	 * 获取报警详细信息
	 * @param id：报警ID
	 * @return 报警类
	 */
	public SysAlarm queryOneAlarm(int id);
	
	/**
	 * 更新报警信息
	 */
	public boolean updateAlarm(SysAlarm sysAlarm);
}
