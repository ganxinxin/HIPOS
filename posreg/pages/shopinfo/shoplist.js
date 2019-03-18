// pages/posinfo/poslist.js
import foot from '../common/foot'
import config from '../../config.js'

var network_util = require('../../utils/network_util.js');
var json_util = require('../../utils/json_util.js');

var app = getApp()
/**获取list列表 */
var GetList = function (that) {
  that.setData({
    hidden: false
  });
  that.sendAndRecive();
}

Page({

  /**
   * 页面的初始数据
   */
  data: {
    hidden:'false',
    weixinid: app.globalData.weixinid,
    list: []
  },

  btnDetail:function (event) {
    wx.navigateTo({
      url: '../shopinfo/shopinfo' 
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this//不要漏了这句，很重要//在回调函数中貌似不能使用this
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          scrollHeight: res.windowHeight
        });
      }
    });
    that.setData({
      weixinid:'liudm'
    });
    that.update();
  },

  /**
   * 发送接收数据
   */
  sendAndRecive: function (e) {
    var that = this;
    let map = new Map();

    map.set('weixinid', that.data.weixinid);
    map.set('token', '');
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    var url = config.queryShopListUrl;
    console.log("url:---" + url);
    network_util._post(url, jsonData,
      function (res) {
        console.log(res)
        console.log("msg:---" + res.data.msg)
        that.setData({
          list: res.data.shopinfolist,//posinfolist,
          hidden: true
        });
        // 数据加载完成后 延迟隐藏loading
        setTimeout(function () {
          that.setData({
            hidden: true
          })
        }, 500);
      },
      function (res) {
        console.log(res);
        // 数据加载完成后 延迟隐藏loading
        setTimeout(function () {
          that.setData({
            hidden: true
          })
        }, 500);
      },
      function (res) {
        console.log(res);
        // 数据加载完成后 延迟隐藏loading
        setTimeout(function () {
          that.setData({
            hidden: true
          })
        }, 500);
      }
      )

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
    var that = this;
    GetList(that);
    that.update();
  },

  bindDownLoad: function () {
    var that = this;
    GetList(that);
    that.update();
  },

  /** 跳转到机器信息的详细界面*/
  gotoShopInfoDetail: function (event) {
    var that = this;
    var idx = parseInt(event.currentTarget.dataset.index);
    var itemName = JSON.stringify(that.data.list[idx]);
    wx.navigateTo({
      url: '../shopinfo/shopinfo?itemName=' + itemName
    })
  },

  scroll: function (event) {
    this.setData({
      scrollTop: event.detail.scrollTop
    });
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

  gototabreg: function () {
    foot.gototabreg();
  },
  gototabmyshop: function () {
    foot.gototabmyshop();
  }
})