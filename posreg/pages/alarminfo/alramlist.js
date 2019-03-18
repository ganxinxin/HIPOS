// pages/alarminfo/alramlist.js
import foot from '../common/foot'
import config from '../../config'

var util = require('../../utils/util.js');
var network_util = require('../../utils/network_util.js');
var json_util = require('../../utils/json_util.js');
var app = getApp();

Page({

  /**
   * 初始数据
   */
  data: {
    // 列表数据
    dataThemes: [],
    //loading
    hidden: true,
    //微信id
    weixinid: '',
    //默认起始时间 
    date: '', 
    //默认结束时间 
    date2: ''
  },

  /**
   * 时间段选择
   */ 
  bindDateChange(e) {
    let that = this;
    console.log(e.detail.value)
    that.setData({
      date: e.detail.value,
    })
  },

  bindDateChange2(e) {
    let that = this;
    that.setData({
      date2: e.detail.value,
    })
  },

  /**
   * 页面初始化
   * options 为页面跳转所带来的参数
   */
  onLoad: function (options) {

    var that = this;

    // 调用函数时，传入new Date()参数，返回值是日期和时间
    var currentDate = util.getNowDate(new Date());
    // 再通过setData更改Page()里面的data，动态更新页面的数据
    that.setData({
      hidden: false,
      weixinid: app.globalData.weixinid,
      date: currentDate,
      date2: currentDate,
    });
    that.update();
    that.sendAndRecive();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    // 页面渲染完成
    var that = this;
    
  },

  /**
   * 发送接收数据
   */
  sendAndRecive:function(e) {
    var that = this;
    var datetime = this.data.date + " 00:00:00";
    var datetime2 = this.data.date2 + " 23:59:59";
    let map = new Map();
    console.log("weixinid:---" + app.globalData.weixinid);
    console.log("startdate:---" + datetime);
    console.log("enddate:---" + datetime2);
    map.set('weixinid', 'liudm');
    map.set('startdate', datetime);
    map.set('enddate', datetime2);
    map.set('token', '');
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    var url = config.queryAlarmListUrl;
    console.log("url:---" + url);
    network_util._post(url, jsonData,
      function (res) {
        console.log("msg:---" + res.data.msg);
        console.log(res);
        that.setData({
          dataThemes: res.data.alarmlist,
          hidden: true,
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
      })

  },

  /**
   * 立即搜索
   */
  bindSearch: function(e) {

    var that = this;
    that.setData({
      hidden: false,
    });
    that.sendAndRecive();
    that.update();
  },



  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({

    });
    this.data.weixinid = app.globalData.weixinid
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

  gototabreg: function () {
    foot.gototabreg();
  },
  gototabmyshop: function () {
    foot.gototabmyshop();
  }
})