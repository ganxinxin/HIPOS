// pages/myshops/myshop.js
var config = require("../../config.js")
var network_util = require('../../utils/network_util.js');
var json_util = require('../../utils/json_util.js');
//获取应用实例
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    weixinid: '',
    hasLogin: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      this.setData({
        weixinid : app.globalData.weixinid,
        hasLogin :app.globalData.hasLogin
      });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      
    });
    this.data.weixinid =  app.globalData.weixinid
    this.update()
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  /** 
   * 判断微信用户是否合法
  * weixinid 微信id
  * success 成功的回调
  * fail 失败的回调
  */
  checkuservalid: function (weixinid, success, fail, complete) {
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
      },
      function(res){
        complete(res);
      }
    )
  },
  onGotUserInfo: function (e) {
    var that = this;
    console.log(e.detail.userInfo)
    //检测微信用户是否存在
    var wxid = e.detail.userInfo.nickName
    that.checkuservalid(wxid,
      function (res) //成功
      {
        if (res.data.success) {
          that.setData({
            hasUserInfo: true,
            userInfo: e.detail.userInfo,
            weixinid: e.detail.userInfo.nickName
          })
          wx.setStorageSync('weixinid', that.data.weixinid)
          app.globalData.weixinid = that.data.weixinid
        }
        else {
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
      },
      function(res)//完成
      {
      }
    )
    console.log('index onGotUserInfo that.weixinid' + this.data.weixinid);
    console.log('index onGotUserInfo app.globalData.weixinid' + app.globalData.weixinid);
  },
  login: function () {
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
  }
})