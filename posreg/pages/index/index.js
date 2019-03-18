//index.js
var config = require("../../config.js")
var network_util = require('../../utils/network_util.js');
var json_util = require('../../utils/json_util.js');
//获取应用实例
var app = getApp()
Page({
  onLoad: function () {
    var storageData = wx.getStorageSync('weixinid');
    console.log('storageData ' + storageData);
    wx.login({
      success: res => {
        this.setData({
          hasLogin: true
        })
        app.globalData.hasLogin = this.data.hasLogin;
        console.log('this.data.hasLogin ' + this.data.hasLogin);
        console.log('index onload weixinid ' + this.data.weixinid);
      }
    });
  },
  data: {
    hasLogin: false,
    userInfo: {},
    weixinid: '',
    qr:''
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      weixinid: app.globalData.weixinid
    });
    console.log('index onShow weixinid=' + this.weixinid)
    this.weixinid = app.globalData.weixinid
  },
  /** 
   * 判断微信用户是否合法
  * weixinid 微信id
  * success 成功的回调
  * fail 失败的回调
  */
  checkuservalid: function (weixinid, success, fail) {
    var url = config.checkUserUrl;
    let map = new Map();
    map.set('weixinid', weixinid);
    map.set('token', '');
    let sendData = json_util.mapToJson(map);
    network_util._post(url, sendData,
      function (res) {
        success(res);
      },
      function (res) {
        fail(res);
      }
    )
  },
  onGotUserInfo: function (e) {
    var that = this;
    console.log(e.detail.userInfo)
    //检测微信用户是否存在
    var wxid = e.detail.userInfo.nickName
    that.checkuservalid(wxid,
      function(res) //成功
      {
        if(res.data.success)
        {
          that.setData({
            hasUserInfo: true,
            userInfo: e.detail.userInfo,
            weixinid: e.detail.userInfo.nickName
          })
          wx.setStorageSync('weixinid', that.data.weixinid)
          app.globalData.weixinid = that.data.weixinid
        }
        else
        {
          wx.showToast({
            title: '非法用户',
            icon: 'fail',
          }) 
        }
      },
      function (res)//失败
      {
        wx.showToast({
          title: '非法用户',
          icon: 'fail',
        }) 
      }
    )
    console.log('index onGotUserInfo that.weixinid' + this.data.weixinid);
    console.log('index onGotUserInfo app.globalData.weixinid' + app.globalData.weixinid);
  },
  login:function() {
    console.log('call login');
    wx.login({
      success: res => {
        this.setData({
          hasLogin: true
        })
        console.log('this.data.hasLogin ' + this.data.hasLogin);
        console.log('Page weixinid ' + this.data.weixinid);
      }
    });
  },
  scanCode: function () {
    var that = this
    wx.scanCode({
      success: function (res) {
        //that.setData({
        //  result: res.result
        //})
        wx.navigateTo({
          url: '../posinfo/posreg?qr=' + res.result,
        })
        console.log('scanCode  code='+res.result);
      },
      fail: function (res) {
      }
    })
  },
  openinput:function ()
  {
    wx.navigateTo({
      url: '../posinfo/posreg',
    })
  }
})

