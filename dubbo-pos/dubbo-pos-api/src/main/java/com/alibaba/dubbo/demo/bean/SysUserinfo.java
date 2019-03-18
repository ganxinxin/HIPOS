package com.alibaba.dubbo.demo.bean;



/**
 * SysUserinfo entity. @author MyEclipse Persistence Tools
 */

public class SysUserinfo implements java.io.Serializable {


    // Fields    

     private Integer userid;
     private Integer deptid;
     private String username;
     private String usercode;
     private String userpwd;
     private String phone;
     private String remark;
     private String weixinid;


    // Constructors

    /** default constructor */
    public SysUserinfo() {
    }

	/** minimal constructor */
    public SysUserinfo(Integer deptid, String username, String usercode, String userpwd) {
        this.deptid = deptid;
        this.username = username;
        this.usercode = usercode;
        this.userpwd = userpwd;
    }
    
    /** full constructor */
    public SysUserinfo(Integer deptid, String username, String usercode, String userpwd, String phone, String remark, String weixinid) {
        this.deptid = deptid;
        this.username = username;
        this.usercode = usercode;
        this.userpwd = userpwd;
        this.phone = phone;
        this.remark = remark;
        this.weixinid = weixinid;
    }

   
    // Property accessors

    public Integer getUserid() {
        return this.userid;
    }
    
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getDeptid() {
        return this.deptid;
    }
    
    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsercode() {
        return this.usercode;
    }
    
    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getUserpwd() {
        return this.userpwd;
    }
    
    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWeixinid() {
        return this.weixinid;
    }
    
    public void setWeixinid(String weixinid) {
        this.weixinid = weixinid;
    }
   








}