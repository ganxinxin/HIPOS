// pages/posinfo/posreg.js
import foot from '../common/foot'
var app = getApp()
var config = require("../../config.js")
var util = require('../../utils/util.js');
var network = require('../../utils/network_util.js');
var json = require('../../utils/json_util.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    weixinid:"",
    possn:'',
    poscode:'',
    posdate:'',
    setupaddr:'',
    shopid:0,
    shopcode:'',
    sopename:'',
    longitude:0.0,
    latitude:0.0,
    shopid:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      weixinid: app.globalData.weixinid
    });
    
    console.log('options.qr=' + options.qr);
    if (options.qr !=null && options.qr!='')
    {  
       var qrcode = options.qr;
      var fields = qrcode.split("||");
        if (fields.length==3)
        {
          this.setData({
            possn: fields[0],
            posdate: fields[1]
          });
        }
    }
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
    this.data.weixinid = app.globalData.weixinid
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
  formSubmit: function (e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    //机器注册
    this.sendposinfo(e.detail.value);
  },
  formReset: function (e) {
    console.log('form发生了reset事件，携带数据为：', e.detail.value)
  },
  gototabreg: function () {
    foot.gototabreg();
  },
  gototabmyshop: function () {
    foot.gototabmyshop();
  },

  
  sendposinfo:function(json)
  {    
    var url = config.registerPosInfoUrl;
    console.log('sendposinfo', config.registerPosInfoUrl)
    network._post(url, json,
      function (result)
      {
        console.log('request success', result)
        if (result.data.success)
        {
        wx.showToast({
          title: '机器信息注册成功',
          icon: 'success',
          })
        }
        else
        {
          wx.showToast({
            title: '机器信息注册失败[' + result.data.msg+']',
            icon: 'fail',
          }) 
        }
      },
      function ({ errMsg }) {
        console.log('request fail', errMsg)
        wx.showToast({
          title: '机器信息注册失败' + errMsg,
          icon: 'fail',
        })
      },
      function (res) {
        console.log('complete')
      }
    )
  }
})