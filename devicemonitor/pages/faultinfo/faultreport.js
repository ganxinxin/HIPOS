import config from '../../utils/config.js'
var network_util = require('../../utils/network_util.js');
var json_util = require('../../utils/json_util.js');
var util = require('../../utils/util.js');
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    setMachineIDInput:"",
    setFaultSrc:"",
    setFaultLevel:"",
    setFaultContent:"",
    //loading
    hidden: true,
    laodText: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
  
  },
  //设备ID
  setMachineIDInput: function (e) {
    this.setData({
      setMachineID: e.detail.value
    })
  },
  //故障来源
  setFaultSrcInput: function (e) {
    this.setData({
      setFaultSrc: e.detail.value
    })
  },
  //严重程度
  setFaultLevelInput: function (e) {
    this.setData({
      setFaultLevel: e.detail.value
    })
  },
  //故障内容
  setFaultContentInput: function (e) {
    this.setData({
      setFaultContent: e.detail.value
    })
  },


  //提交处理
  submitInfos: function () {

    var that = this;
    if (that.data.setMachineID == ""||that.data.setFaultSrc == ""||that.data.setFaultLevel == ""||that.data.setFaultContent == "") {
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
    let map = new Map();
    map.set('uuid', that.data.setMachineID);
    if (that.data.setFaultSrc == "阈值") {
      map.set('faultSrc', '0');
    }
    else if (that.data.setFaultLevel == "硬件心跳") {
      map.set('faultSrc', '1');
    }
    else if (that.data.setFaultLevel == "软件心跳") {
      map.set('faultSrc', '2');
    }
    else if (that.data.setFaultLevel == "软件白名单") {
      map.set('faultSrc', '3');
    }
    else if (that.data.setFaultLevel == "软件黑名单") {
      map.set('faultSrc', '4');
    }
    else {
      map.set('faultSrc', '5');
    }
    if (that.data.setFaultLevel == "一般"){
      map.set('faultLevel','0' );
    }
    else if (that.data.setFaultLevel == "警告"){
      map.set('faultLevel', '1');
    }
    else if (that.data.setFaultLevel == "严重"){
      map.set('faultLevel', '2');
    }
    else{
      map.set('faultLevel','3' );
    }

    map.set('faultContent', that.data.setFaultContent);
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    var url = config.queryFaultCreate;
    console.log("url:---" + url);

    network_util._post(url, jsonData,
      function (res) {
        console.log(res);
        that.setData({
          laodText: '处理完成',
          setMachineID: "",
          setFaultSrc: "",
          setFaultLevel: "",
          setFaultContent: "",
        })
        setTimeout(function () {
          that.setData({
            hidden: true
          })
        }, 500);
      },
      function (res) {
        console.log('fail');
        wx.showModal({
          title: '查询失败',
          content: res.errMsg,
          success: function (res) {
          },
          fail: function (res) {
          },
          complete: function (res) {
          }
        });
      },
      function (res) {
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