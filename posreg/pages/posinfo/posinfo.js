// pages/posinfo/posinfo.js
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
    weixinid: app.globalData.weixinid,
    hidden: 'false',
    possn: '',
    posid: '',

    poscode: '',
    setupaddr: '',
    managerphone: '',
    shopcode: '',
    shopname: '',
    longitude: 0.0,
    latitude: 0.0,
    
    cup: '',
    memery: '',
    harddisk:'',
    system: '',
    systemversion: '',
    hiversion: ''

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    if(options.possn == null)
    {
      var list = JSON.parse(options.itemName);
      that.setData({
        possn: list.possn,
      })
      console.log("list.possn:" + list.possn)
    }
    else
    {
      console.log("options.possn:" + options.possn)
      that.setData({
        possn: options.possn
      })
      console.log("options.possn:" + options.possn)
    }
    console.log('possn:' + that.data.possn)
    that.sendAndRecive();
    that.update();
  },

  /**
  * 发送接收数据(查询基本信息)
  */
  sendAndRecive: function () {
    var that = this;
    let map = new Map();
    console.log("possn:---" + that.data.possn);
    map.set('possn', that.data.possn);
    map.set('token', '');
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    var url = config.queryPosInfoUrl;
    console.log("url:---" + url);
    network_util._post(url, jsonData,
      function (res) {
        console.log("msg:---" + res.data.msg);
        console.log(res);
        that.setData({
          possn: that.data.possn,
          poscode: res.data.posinfo.poscode,
          posid:res.data.posinfo.posid,
          setupaddr: res.data.posinfo.setupaddr,
          managerphone: res.data.shopinfo.managerphone,
          shopcode: res.data.shopinfo.shopcode,
          shopname: res.data.shopinfo.shopname,
          longitude: res.data.shopinfo.longitude,
          latitude: res.data.shopinfo.latitude
        });
      },
      function (res) {
        console.log(res);
      },
      function(res){
        console.log(res);
      })
  },

  //查看基本信息
  queryPosInfo: function () {
    var that = this;
    that.sendAndRecive();
    that.update();
  },

  //查看配置信息
  queryPosConfigure: function () {
    var that = this;
    console.log('posid:'+that.data.posid)
    wx.navigateTo({
      url: '/pages/posinfo/posconfigure?posid='+that.data.posid,
    })
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
    var that = this
    if(that.data.isBack){
      that.gototabmyshop()
    }
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