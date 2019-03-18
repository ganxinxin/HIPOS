// pages/posinfo/posconfigure.js
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
    harddisk: '',
    system: '',
    systemversion: '',
    hiversion: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    console.log('options.posid:' + options.posid)
    that.setData({
      posid: options.posid,
    })
    that.sendAndRecive();
    that.update();
  },

  /**
  * 发送接收数据(查询配置信息)
  */
  sendAndRecive: function () {
    var that = this;
    let map = new Map();
    console.log("id:---" + that.data.posid);
    map.set('id', that.data.posid);
    map.set('token', '');
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    var url = config.queryPosStatusUrl;
    console.log("url:---" + url);
    network_util._post(url, jsonData,
      function (res) {
        console.log("msg:---" + res.data.msg);
        console.log(res);
        that.setData({
          possn: res.data.posstatus.possn,
          posid: that.data.posid,
          cpu: res.data.posstatus.cpu,
          memery: res.data.posstatus.memery,
          harddisk: res.data.posstatus.harddisk,
          system: res.data.posstatus.system,
          systemversion: res.data.posstatus.systemversion,
          hiversion: res.data.posstatus.hiversion
        });
      },
      function (res) {
        console.log(res);
        wx.showModal({
          title: '查询失败',
          content: res.errMsg,
        })
      },
      function (res) {
        console.log(res);
      });
  },

  //查看基本信息
  queryPosInfo: function () {
    var that = this;
    console.log('that.data.possn:' + that.data.possn)
    wx.navigateTo({
      url: '/pages/posinfo/posinfo?possn='+that.data.possn,
    })
  },

  //查看配置信息
  queryPosConfigure: function () {
    var that = this;
    that.sendAndRecive();
    that.update();
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
    var that = this;
    if(getCurrentPages().length >2){
      let pages = getCurrentPages();
      let curPage = pages[pages.length - 2];
      console.log('pages' + pages)
      console.log('curPage' + curPage)
      let data = curPage.data;
      curPage.setData({'isBack':true});
    }

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