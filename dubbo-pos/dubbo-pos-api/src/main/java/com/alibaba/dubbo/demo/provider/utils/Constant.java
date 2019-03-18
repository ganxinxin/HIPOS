package com.alibaba.dubbo.demo.provider.utils;

public class Constant {

	// 任务代码 cmdid
	public static final int sysUpgrade = 10001;
	public static final int invUpgrade = 10002;
	public static final int payUpgrade = 10003;
	public static final int lock = 10006;
	public static final int unlock = 10007;

	// 执行类型 runtype
	public static final int immediately = 0;
	public static final int delay = 1;

	// 设备锁定状态 lockStatus
	public static final String status_lock = "1";
	public static final String status_unlock = "0";

	// 通知阅读状态 cmdstatus
	public static final String status_valid = "0";
	public static final String status_invalid = "1";

	// 升级类型 softwareType
	public static final String software_type_sys = "sys";
	public static final String software_type_inv = "inv";
	public static final String software_type_pay = "pay";

	// 通知类型 groupType
	public static final String group_type_taxsubstation = "taxsubstation";
	public static final String group_type_machine_no = "machineNo";
	public static final String group_type_machine_type = "machineType";

	// 分页大小
	public static final String page_size = "25";

	// 运行状态
	public static final String run_status_uninit = "0";//未初始化
	public static final String run_status_normal = "2";//正常
	public static final String run_status_lock = "1";//锁机

}
