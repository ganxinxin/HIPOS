//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    this.globalData.weixinid = wx.getStorageSync('weixinid') 
    console.log('this.globalData.weixinid=', this.globalData.weixinid);
  },
  globalData: {
    userInfo: null,
    openid: null,
    weixinid:null,
    hasLogin:false
  }
})