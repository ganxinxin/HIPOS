//foot.js
var foot = 
{
    /**
      * 跳转到机器注册页面
      */
    gototabreg: function () {
      wx.switchTab({
        url: '/pages/index/index',
      })
    },
    /**
    * 跳转到我的门店页面
    */
    gototabmyshop: function () {
      wx.switchTab({
        url: '/pages/myshop/myshop'
      })
    }
  }

//导出，供外部使用
export default foot