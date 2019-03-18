package com.alibaba.dubbo.demo.bean;

import java.sql.Timestamp;


/**
 * SysFault entity. @author MyEclipse Persistence Tools
 */

public class SysFault  implements java.io.Serializable {


    // Fields    

     private Integer faultid;
     private String faultcode;
     private Timestamp faultdatetime;
     private String faultinfo;
     private Integer shopid;
     private Integer posid;
     private String dealmethod;
     private Timestamp dealdatetime;
     private String dealpersion;
     private Integer isdeal;
     
     private String weixinid;
    // Constructors

    /** default constructor */
    public SysFault() {
    }

    
    /** full constructor */
    public SysFault(String faultcode, Timestamp faultdatetime, String faultinfo, Integer shopid, Integer posid, String dealmethod, Timestamp dealdatetime, String dealpersion, Integer isdeal) {
        this.faultcode = faultcode;
        this.faultdatetime = faultdatetime;
        this.faultinfo = faultinfo;
        this.shopid = shopid;
        this.posid = posid;
        this.dealmethod = dealmethod;
        this.dealdatetime = dealdatetime;
        this.dealpersion = dealpersion;
        this.isdeal = isdeal;
    }

   
    // Property accessors

    public Integer getFaultid() {
        return this.faultid;
    }
    
    public void setFaultid(Integer faultid) {
        this.faultid = faultid;
    }

    public String getFaultcode() {
        return this.faultcode;
    }
    
    public void setFaultcode(String faultcode) {
        this.faultcode = faultcode;
    }

    public Timestamp getFaultdatetime() {
        return this.faultdatetime;
    }
    
    public void setFaultdatetime(Timestamp faultdatetime) {
        this.faultdatetime = faultdatetime;
    }

    public String getFaultinfo() {
        return this.faultinfo;
    }
    
    public void setFaultinfo(String faultinfo) {
        this.faultinfo = faultinfo;
    }

    public Integer getShopid() {
        return this.shopid;
    }
    
    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public Integer getPosid() {
        return this.posid;
    }
    
    public void setPosid(Integer posid) {
        this.posid = posid;
    }

    public String getDealmethod() {
        return this.dealmethod;
    }
    
    public void setDealmethod(String dealmethod) {
        this.dealmethod = dealmethod;
    }

    public Timestamp getDealdatetime() {
        return this.dealdatetime;
    }
    
    public void setDealdatetime(Timestamp dealdatetime) {
        this.dealdatetime = dealdatetime;
    }

    public String getDealpersion() {
        return this.dealpersion;
    }
    
    public void setDealpersion(String dealpersion) {
        this.dealpersion = dealpersion;
    }

    public Integer getIsdeal() {
        return this.isdeal;
    }
    
    public void setIsdeal(Integer isdeal) {
        this.isdeal = isdeal;
    }


	public String getWeixinid() {
		return weixinid;
	}


	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}
   








}