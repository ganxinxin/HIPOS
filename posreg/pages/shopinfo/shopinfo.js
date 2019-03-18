// pages/shopinfo/shopinfo.js
import foot from '../common/foot'
var app = getApp()
var config = require("../../config.js")

Page({

  /**
   * 页面的初始数据
   */
  data: {
    weixinid: app.globalData.weixinid,
    shopcode: '',
    shopname: '',
    shopaddr: '',
    leaderphone: '',
    managerphone: '',
    longitude: 0.0,
    latitude: 0.0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    var list = JSON.parse(options.itemName);
    console.log(list)
    console.log(list.shopcode)
    console.log(list.shopname)
    console.log(list.shopaddr)
    console.log(list.leaderphone)
    console.log(list.managerphone)
    this.setData({
      shopcode: list.shopcode,
      shopname: list.shopname,
      shopaddr: list.shopaddr,
      leaderphone: list.leaderphone,
      managerphone: list.managerphone,
      longitude: list.longitude,
      latitude: list.latitude,
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