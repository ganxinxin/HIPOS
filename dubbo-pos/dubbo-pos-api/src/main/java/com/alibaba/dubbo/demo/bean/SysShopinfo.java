package com.alibaba.dubbo.demo.bean;



/**
 * SysShopinfo entity. @author MyEclipse Persistence Tools
 */

public class SysShopinfo  implements java.io.Serializable {


    // Fields    

     private Integer shopid;
     private String shopcode;
     private String shopname;
     private String shopaddr;
     private Double longitude;
     private Double latitude;
     private String shopleader;
     private String leaderphone;
     private String manager;
     private String managerphone;
     private String manageremail;
     private String managerweixin;
     private String remark;

     private String weixinid;
    // Constructors

    /** default constructor */
    public SysShopinfo() {
    }

    
    /** full constructor */
    public SysShopinfo(String shopcode, String shopname, String shopaddr, Double longitude, Double latitude, String shopleader, String leaderphone, String manager, String managerphone, String manageremail, String managerweixin, String remark) {
        this.shopcode = shopcode;
        this.shopname = shopname;
        this.shopaddr = shopaddr;
        this.longitude = longitude;
        this.latitude = latitude;
        this.shopleader = shopleader;
        this.leaderphone = leaderphone;
        this.manager = manager;
        this.managerphone = managerphone;
        this.manageremail = manageremail;
        this.managerweixin = managerweixin;
        this.remark = remark;
    }

   
    // Property accessors

    public Integer getShopid() {
        return this.shopid;
    }
    
    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public String getShopcode() {
        return this.shopcode;
    }
    
    public void setShopcode(String shopcode) {
        this.shopcode = shopcode;
    }

    public String getShopname() {
        return this.shopname;
    }
    
    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopaddr() {
        return this.shopaddr;
    }
    
    public void setShopaddr(String shopaddr) {
        this.shopaddr = shopaddr;
    }

    public Double getLongitude() {
        return this.longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getShopleader() {
        return this.shopleader;
    }
    
    public void setShopleader(String shopleader) {
        this.shopleader = shopleader;
    }

    public String getLeaderphone() {
        return this.leaderphone;
    }
    
    public void setLeaderphone(String leaderphone) {
        this.leaderphone = leaderphone;
    }

    public String getManager() {
        return this.manager;
    }
    
    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerphone() {
        return this.managerphone;
    }
    
    public void setManagerphone(String managerphone) {
        this.managerphone = managerphone;
    }

    public String getManageremail() {
        return this.manageremail;
    }
    
    public void setManageremail(String manageremail) {
        this.manageremail = manageremail;
    }

    public String getManagerweixin() {
        return this.managerweixin;
    }
    
    public void setManagerweixin(String managerweixin) {
        this.managerweixin = managerweixin;
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