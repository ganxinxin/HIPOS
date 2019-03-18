package com.point.community.bean;

public class DeptNode {
	
	//节点id  
    private Integer id;  
    //父节点id  
    private Integer pId;  
    //节点名称  
    private String name;  
    //rel  
    private String rel;  
    //url  
    private String url; 
    //是否有子节点
    private boolean haschild;
    //节点类型   0:部门  1:设备
    private int type;
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isHaschild() {
		return haschild;
	}
	public void setHaschild(boolean haschild) {
		this.haschild = haschild;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public DeptNode(Integer id, Integer pId, String name, String rel, String url, boolean haschild, int type) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.rel = rel;
		this.url = url;
		this.haschild = haschild;
		this.type = type;
	}
	public DeptNode() {
		// TODO Auto-generated constructor stub
	}
}
