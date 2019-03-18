package com.alibaba.dubbo.demo.bean;



/**
 * SysPosinfo entity. @author MyEclipse Persistence Tools
 */

public class SysPosinfo  implements java.io.Serializable {


    // Fields    

     private Integer posid;
     private String possn;
     private String posname;
     private String setupaddr;
     private String poscode;
     private Integer shopid;
     private String manager;
     private String phone;
     private String email;
     private String weixin;
     private String postype;
     private String setuptype;
     private String cpu;
     private String memery;
     private String harddisk;
     private String system;
     private String systemversion;
     private String hiversion;
     private String remark;

     private String weixinid;
    // Constructors

    /** default constructor */
    public SysPosinfo() {
    }

    
    /** full constructor */
    public SysPosinfo(String possn, String posname, String setupaddr, String poscode, Integer shopid, String manager, String phone, String email, String weixin, String postype, String setuptype, String cpu, String memery, String harddisk, String system, String systemversion, String hiversion, String remark) {
        this.possn = possn;
        this.posname = posname;
        this.setupaddr = setupaddr;
        this.poscode = poscode;
        this.shopid = shopid;
        this.manager = manager;
        this.phone = phone;
        this.email = email;
        this.weixin = weixin;
        this.postype = postype;
        this.setuptype = setuptype;
        this.cpu = cpu;
        this.memery = memery;
        this.harddisk = harddisk;
        this.system = system;
        this.systemversion = systemversion;
        this.hiversion = hiversion;
        this.remark = remark;
    }

   
    // Property accessors

    public Integer getPosid() {
        return this.posid;
    }
    
    public void setPosid(Integer posid) {
        this.posid = posid;
    }

    public String getPossn() {
        return this.possn;
    }
    
    public void setPossn(String possn) {
        this.possn = possn;
    }

    public String getPosname() {
        return this.posname;
    }
    
    public void setPosname(String posname) {
        this.posname = posname;
    }

    public String getSetupaddr() {
        return this.setupaddr;
    }
    
    public void setSetupaddr(String setupaddr) {
        this.setupaddr = setupaddr;
    }

    public String getPoscode() {
        return this.poscode;
    }
    
    public void setPoscode(String poscode) {
        this.poscode = poscode;
    }

    public Integer getShopid() {
        return this.shopid;
    }
    
    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public String getManager() {
        return this.manager;
    }
    
    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeixin() {
        return this.weixin;
    }
    
    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getPostype() {
        return this.postype;
    }
    
    public void setPostype(String postype) {
        this.postype = postype;
    }

    public String getSetuptype() {
        return this.setuptype;
    }
    
    public void setSetuptype(String setuptype) {
        this.setuptype = setuptype;
    }

    public String getCpu() {
        return this.cpu;
    }
    
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMemery() {
        return this.memery;
    }
    
    public void setMemery(String memery) {
        this.memery = memery;
    }

    public String getHarddisk() {
        return this.harddisk;
    }
    
    public void setHarddisk(String harddisk) {
        this.harddisk = harddisk;
    }

    public String getSystem() {
        return this.system;
    }
    
    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystemversion() {
        return this.systemversion;
    }
    
    public void setSystemversion(String systemversion) {
        this.systemversion = systemversion;
    }

    public String getHiversion() {
        return this.hiversion;
    }
    
    public void setHiversion(String hiversion) {
        this.hiversion = hiversion;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }


	public String getWeixinid() {
		return weixinid;
	}


	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}
   








}