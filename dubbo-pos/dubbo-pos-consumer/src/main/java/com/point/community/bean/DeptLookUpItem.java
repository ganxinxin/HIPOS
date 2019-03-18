package com.point.community.bean;

public class DeptLookUpItem {
	
	private int id;
	private int deptid;
	private String deptname;
	private String manager;
	private int lookupUpperNum;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public int getLookupUpperNum() {
		return lookupUpperNum;
	}
	public void setLookupUpperNum(int lookupUpperNum) {
		this.lookupUpperNum = lookupUpperNum;
	}
	
	public void DeptLookUpItem()
	{
		id = 0;
		deptid = 0;
		deptname = "";
		manager = "";
		lookupUpperNum = 0;
	}
	public void DeptLookUpItem(int id,int deptid,String deptname,String manager,int lookupUpperNum)
	{
		this.id = id;
		this.deptid = deptid;
		this.deptname = deptname;
		this.manager = manager;
		this.lookupUpperNum = lookupUpperNum;
	}
}
