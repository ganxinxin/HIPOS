// pages/posinfo/poslist.js
import foot from '../common/foot'
import config from '../../config.js'

var network_util = require('../../utils/network_util.js');
var json_util = require('../../utils/json_util.js');
var app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    weixinid: 'liudm',
    list: []
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this //不要漏了这句，很重要//在回调函数中貌似不能使用this
    wx.getSystemInfo({
      success: function(res) {
        that.setData({
          scrollHeight: res.windowHeight
        });
      }
    });
    that.update();
  },

  /**
   * 发送接收数据
   */
  sendAndRecive: function() {
    var that = this;
    let map = new Map();
    wx.showLoading({
      title: '加载中...',
    })
    map.set('weixinid', that.data.weixinid);
    map.set('token', '');
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    var url = config.queryPosListUrl;
    console.log("url:---" + url);
    network_util._post(url, jsonData,
      function(res) {
        console.log(res)
        console.log("msg:---" + res.data.msg)

        that.setData({
          list: res.data.posinfolist,
        });
      },
      function(res) {
        console.log('fail');
        console.log(res);
        wx.showModal({
          title: '查询失败',
          content: res.errMsg,
          success: function(res){
            if(res.cancel){
              that.gototabmyshop()
            }else{
              that.gototabmyshop()
            }
          },
          fail: function(res){

          },
          complete:function(res){

          }
        });
      },
      function(res){
        wx.hideLoading();
        
      });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    var that = this;
    that.sendAndRecive();
    that.update();
  },

  bindDownLoad: function() {
    var that = this;
    that.sendAndRecive();
    that.update();
  },
  /** 跳转到机器信息的详细界面*/
  gotoPosInfoDetail: function(event) {
    var that = this;
    var idx = parseInt(event.currentTarget.dataset.index);
    var itemName = JSON.stringify(that.data.list[idx]);
    wx.navigateTo({
      url: '../posinfo/posinfo?itemName=' + itemName
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },

  gototabreg: function() {
    foot.gototabreg();
  },
  gototabmyshop: function() {
    foot.gototabmyshop();
  }
})