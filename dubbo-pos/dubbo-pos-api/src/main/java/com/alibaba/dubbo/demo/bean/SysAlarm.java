package com.alibaba.dubbo.demo.bean;

import java.sql.Timestamp;


/**
 * SysAlarm entity. @author MyEclipse Persistence Tools
 */

public class SysAlarm  implements java.io.Serializable {


    // Fields    

     private Integer alarmid;
     private String alarmcode;
     private Timestamp alarmdatetime;
     private String alarminfo;
     private Integer shopid;
     private Integer posid;
     private String dealmethod;
     private Timestamp dealdatetime;
     private String dealpersion;
     private Integer isdeal;

     private String weixinid;
    // Constructors

    /** default constructor */
    public SysAlarm() {
    }
    
    /** full constructor */
    public SysAlarm(String alarmcode, Timestamp alarmdatetime, String alarminfo, Integer shopid, Integer posid, String dealmethod, Timestamp dealdatetime, String dealpersion, Integer isdeal) {
        this.alarmcode = alarmcode;
        this.alarmdatetime = alarmdatetime;
        this.alarminfo = alarminfo;
        this.shopid = shopid;
        this.posid = posid;
        this.dealmethod = dealmethod;
        this.dealdatetime = dealdatetime;
        this.dealpersion = dealpersion;
        this.isdeal = isdeal;
    }

   
    // Property accessors

    public Integer getAlarmid() {
        return this.alarmid;
    }
    
    public void setAlarmid(Integer alarmid) {
        this.alarmid = alarmid;
    }

    public String getAlarmcode() {
        return this.alarmcode;
    }
    
    public void setAlarmcode(String alarmcode) {
        this.alarmcode = alarmcode;
    }

    public Timestamp getAlarmdatetime() {
        return this.alarmdatetime;
    }
    
    public void setAlarmdatetime(Timestamp alarmdatetime) {
        this.alarmdatetime = alarmdatetime;
    }

    public String getAlarminfo() {
        return this.alarminfo;
    }
    
    public void setAlarminfo(String alarminfo) {
        this.alarminfo = alarminfo;
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