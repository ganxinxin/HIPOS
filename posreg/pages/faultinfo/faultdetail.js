// pages/faultinfo/faultdetail.js
import config from '../../config'

var util = require('../../utils/util.js');
var network_util = require('../../utils/network_util.js');
var json_util = require('../../utils/json_util.js');
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    // 详细数据
    getFaultID: '',
    getFaultTime: '',
    getFaultContent: '',
    getMachineNumber: '',
    getStoreNumber: '',
    setDealMethod: '',
    getDealTime: '',
    setDealPerson: '',
    setDealStandard: '',
    //loading
    hidden: true,
    laodText: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 页面初始化 options 为页面跳转所带来的参数
    var that = this
    that.setData({
      getFaultID: options.id,
    });
    that.sendAndRecive();
    that.update();
  },

  //处理方法
  setDealMethodInput: function (e) {
    this.setData({
      setDealMethod: e.detail.value
    })
  },
  //处理人
  setDealPersonInput: function (e) {
    this.setData({
      setDealPerson: e.detail.value
    })
  },

  //提交处理
  submitInfos: function () {

    var that = this;
    console.log("that.data.setDealStandard:---" + that.data.setDealStandard);
    if (that.data.setDealStandard == "1") {
      wx.showToast({
        title: '已处理过故障',
        icon: 'none',
        image: '',
        duration: 1000
      })
      return false;
    }
    if (that.data.setDealMethod == null || that.data.setDealPerson == null) {
      wx.showToast({
        title: '不能为空',
        icon: 'none',
        image: '',
        duration: 1000
      })
      return false;
    }

    that.setData({
      laodText: '处理中...',
      hidden: false,
    })

    // 调用函数时，传入new Date()参数，返回值是日期和时间
    var currentDate = util.formatTime(new Date());
    let map = new Map();
    console.log("id:---" + that.data.getFaultID);
    console.log("setDealMethodInput:---" + that.data.setDealMethodInput);
    console.log("currentDate:---" + currentDate);
    console.log("setDealPerson:---" + that.data.setDealPerson);
    console.log("setDealStandard:---" + that.data.setDealStandard);

    map.set('faultid', "" + that.data.getFaultID);
    map.set('dealmethod', that.data.setDealMethod);
    map.set('dealdatetime', currentDate);
    map.set('dealpersion', that.data.setDealPerson);
    map.set('isdeal', "1");
    map.set('token', '');
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    var url = config.updateFaultDealFlagUrl;
    console.log("url:---" + url);
    network_util._post(url, jsonData,
      function (res) {
        console.log(res);
        that.setData({
          laodText: '处理完成',
          setDealStandard: '1'
        })
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
      })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
  * 发送接收数据
  */
  sendAndRecive: function (e) {
    var that = this;
    let map = new Map();
    console.log("id:---" + that.data.getFaultID);
    map.set('id', that.data.getFaultID);
    map.set('token', '');
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    var url = config.queryOneFaultUrl;
    console.log("url:---" + url);
    network_util._post(url, jsonData,
      function (res) {
        console.log("msg:---" + res.data.msg);
        console.log(res);
        that.setData({
          getFaultID: res.data.sysfault.faultid,
          getFaultTime: res.data.sysfault.faultdatetime,
          getFaultContent: res.data.sysfault.faultinfo,
          getMachineNumber: res.data.sysfault.posid,
          getStoreNumber: res.data.sysfault.shopid,
          setDealMethod: res.data.sysfault.dealmethod,
          getDealTime: res.data.sysfault.dealdatetime,
          setDealPerson: res.data.sysfault.dealpersion,
          setDealStandard: res.data.sysfault.isdeal,
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
      })
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

  }
})