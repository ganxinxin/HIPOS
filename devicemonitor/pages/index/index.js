//index.js
//获取应用实例
import config from '../../utils/config.js'
import imageUrl from '../../utils/picture.js'

var network_util = require('../../utils/network_util.js');
var json_util = require('../../utils/json_util.js');
var wxCharts = require('../../utils/wxcharts.js');
const app = getApp();
var posstatechart = null;
var postypechart = null;
var totalnum = 0;
var regularnum = 0;
var unregularnum = 0;
var faultnum = 0;
var count616 = 0;
var count618 = 0;
var count716 = 0;
var count718 = 0;
var count950 = 0;
var countHI98 = 0;
var countWINPOS = 0;
var countOTHER = 0;
var categories;
var activeCategoryId;
var detail;
var detailList;
var faultList;
var detailListCurrent;
var pieChart = null;

Page({
  data: {
    totalcount: '',
    regularcount: '',
    unregularcount: '',
    faultcount: '',
    orgId: '',
    start: '',
    pageSize: '100',
    id: '',
    uuid: '',
    backgroundcanvas: [, ],
    chartname: '机器运行状态图',
    indicatorDots: true,
    autoplay: false,
    circular: false,
    duration: 300,
    current: 0,
    canvasId: 'posstate',
    categories: [],
    activeCategoryId: null,
    detail: [],
    detailList: [],
    detailListCurrent: [],
    faultList: [],
    scrollTop: 0,
    classifyViewed: null,
    isFold: false,
    swiperheight: 580,
    canvasrheight: 540,
    chartwidth: 320,
    chartheight: 240,
    pageindex: 0,
    pagelastindex: 0
  },

  /**
   * 发送接收数据(状态统计)
   */
  sendEqtStatusAndRecive: function() {
    var that = this;
    wx.showLoading({
      title: '加载中...',
    })

    let map = new Map();
    map.set('orgId', '');
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    var url = config.queryEqtStatus;
    console.log("url:---" + url);

    network_util._post(url, jsonData,
      function(res) {
        console.log(res);
        totalnum = res.data.data.eqtNum;
        regularnum = res.data.data.eqtOnlineNum;
        unregularnum = res.data.data.eqtNum - res.data.data.eqtOnlineNum;
        faultnum = res.data.data.faultEqtNum;
        console.log("totalnum:" + totalnum + ", regularnum:" + regularnum + ", unregularnum:" + unregularnum + ", faultnum:" + faultnum);
        wx.setStorageSync("totalnum", totalnum);
        wx.setStorageSync("regularcount", regularnum);
        wx.setStorageSync("unregularcount", unregularnum);
        wx.setStorageSync("faultcount", faultnum);
        // 数据加载完成后 延迟隐藏loading
        setTimeout(function() {
          that.setData({
            totalcount: totalnum,
            regularcount: regularnum,
            unregularcount: unregularnum,
            faultcount: faultnum
          })
        }, 1000);
      },
      function(res) {
        console.log('fail');
        console.log(res);
        wx.showModal({
          title: '查询失败',
          content: res.errMsg,
          success: function(res) {},
          fail: function(res) {

          }
        });
      },
      function(res) {
        wx.hideLoading();
      });
  },

  /**
   * 发送接收数据(列表查询)
   */
  sendEqtListAndRecive: function() {
    var that = this;
    wx.showLoading({
      title: '加载中...',
    })

    let map = new Map();
    map.set('orgId', '');
    map.set('start', '0');
    map.set('pageSize', that.data.pageSize);
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    var url = config.queryEqtList;
    console.log("url:---" + url);

    network_util._post(url, jsonData,
      function(res) {
        console.log(res);

        that.resolveEqtListRes(res);

        that.setData({
          categories: categories,
          detail: detail,
          detailList: detailList,
        })
      },
      function(res) {
        console.log('fail');
        console.log(res);
        wx.showModal({
          title: '查询失败',
          content: res.errMsg,
          success: function(res) {},
          fail: function(res) {

          }
        });
      },
      function(res) {
        wx.hideLoading();
      });
  },

  /**
   * 发送接收数据(故障设备列表)
   */
  sendFaultListAndRecive: function() {
    var that = this;
    wx.showLoading({
      title: '加载中...',
    })
    let map = new Map();
    map.set('orgId', '');
    map.set('start', '0');
    map.set('pageSize', that.data.pageSize);
    var url = config.queryFaultList;
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    console.log("url:---" + url);

    network_util._post(url, jsonData,
      function(res) {
        console.log(res);

        that.resolveFaultListRes(res);
      },
      function(res) {
        console.log('fail');
        console.log(res);
        wx.showModal({
          title: '查询失败',
          content: res.errMsg,
          success: function(res) {},
          fail: function(res) {

          }
        });
      },
      function(res) {
        wx.hideLoading();
      });
  },

  genCategories: function() {
    var that = this;
    categories = that.data.categories;

    var tempcate = {};
    tempcate.id = 0;
    tempcate.name = "在线";
    tempcate.key = "001";
    tempcate.userid = "7516";
    categories.push(tempcate);

    var tempcate = {};
    tempcate.id = 1;
    tempcate.name = "离线";
    tempcate.key = "002";
    tempcate.userid = "7516";
    categories.push(tempcate);

    var tempcate = {};
    tempcate.id = 2;
    tempcate.name = "全部";
    tempcate.key = "003";
    tempcate.userid = "7516";
    categories.push(tempcate);

    var tempcate = {};
    tempcate.id = 3;
    tempcate.name = "异常";
    tempcate.key = "004";
    tempcate.userid = "7516";
    categories.push(tempcate);

    console.log("categories---------");
    console.log(categories);
  },

  resolveEqtListRes: function(res) {
    var that = this;
    var total = res.data.data.size;
    var showlist = res.data.data.page;
    var length = showlist.length;
    console.log("total:" + total);
    console.log("length:" + length);

    // list列表
    var temp;
    detail = that.data.detail;
    detailList = that.data.detailList;
    for (var idx = 0; idx < length; idx++) {
      temp = showlist[idx];
      // console.log("list-----");
      // console.log(showlist[idx]);
      if (showlist[idx].eqtTypeCode == 'HI98') {
        temp.imgUrl = imageUrl.HI98Url;
        countHI98++;
      } else if (showlist[idx].eqtTypeCode == 'HK500E') {
        temp.imgUrl = imageUrl.HK500Url;
        countOTHER++;
      } else if (showlist[idx].eqtTypeCode == 'HK600') {
        temp.imgUrl = imageUrl.HK600Url;
        countOTHER++;
      } else if (showlist[idx].eqtTypeCode == 'HK616' || showlist[idx].eqtTypeCode == 'HM616') {
        temp.imgUrl = imageUrl.HK616Url;
        count616++;
      } else if (showlist[idx].eqtTypeCode == 'HK618' || showlist[idx].eqtTypeCode == 'HM618') {
        temp.imgUrl = imageUrl.HM618Url;
        count618++;
      } else if (showlist[idx].eqtTypeCode == 'HK716R') {
        temp.imgUrl = imageUrl.HK716Url;
        count716++;
      } else if (showlist[idx].eqtTypeCode == 'HK718') {
        temp.imgUrl = imageUrl.HK718Url;
        count718++;
      } else if (showlist[idx].eqtTypeCode == 'HK950S') {
        temp.imgUrl = imageUrl.HK950Url;
        count950++;
      } else if (showlist[idx].eqtTypeCode == 'HS5500R') {
        temp.imgUrl = imageUrl.HS5500Url;
        countOTHER++;
      } else if (showlist[idx].eqtTypeCode == 'HS6500R') {
        temp.imgUrl = imageUrl.HS6500Url;
        countOTHER++;
      } else if (showlist[idx].eqtTypeCode == 'HT5000') {
        temp.imgUrl = imageUrl.HT5000Url;
        countOTHER++;
      } else {
        temp.imgUrl = imageUrl.DefaultUrl;
        countOTHER++;
      }


      wx.setStorageSync("count616", count616);
      wx.setStorageSync("count618", count618);
      wx.setStorageSync("count716", count716);
      wx.setStorageSync("count718", count718);
      wx.setStorageSync("count950", count950);
      wx.setStorageSync("countHI98", countHI98);
      wx.setStorageSync("countOTHER", countOTHER);
      wx.setStorageSync("countWINPOS", countWINPOS);

      if (showlist[idx].eqtState == 0) {
        temp.otherUrl = imageUrl.onlineUrl;
        temp.property = "";
      } else if (showlist[idx].eqtState == 1) {
        temp.otherUrl = imageUrl.offlineUrl;
        // temp.property = "离线";
      }

      detail.push(temp);
    }

    console.log("eqtlist detail---------");
    console.log(detail);
    var
      id,
      key,
      name,
      detailTemp = []

    for (let i = 0; i < categories.length; i++) {
      id = categories[i].id;
      name = categories[i].name;
      key = categories[i].key;
      detailTemp = [];
      for (let j = 0; j < detail.length; j++) {
        if (id == 2) {
          detailTemp.push(detail[j]);
        } else if (detail[j].eqtState == id) {
          detailTemp.push(detail[j]);
        }
      }

      for (let m = 0; m < faultList.length; m++) {
        if (3 == id) {
          detailTemp.push(faultList[m]);
        }
      }

      if ((that.data.activeCategoryId === null) & (detailTemp.length > 0)) {

        that.data.activeCategoryId = categories[i].id
      }
      detailList.push({
        'id': id,
        'name': name,
        'key': key,
        'detail': detailTemp
      })
    }

    console.log("eqt detailList:-----------");
    console.log(detailList);

    that.setData({
      classifyViewed: that.data.categories[0].id,
      scrolltop: 0,
      detailListCurrent: detailList[0],
    })

  },

  resolveFaultListRes: function(res) {
    var that = this;
    var total = res.data.data.size;
    var showlist = res.data.data.page;
    faultList = that.data.faultList;
    var length = showlist.length;
    console.log("total:" + total);
    console.log("length:" + length);


    // list列表
    var temp;
    for (var idx = 0; idx < length; idx++) {
      temp = showlist[idx];
      if (showlist[idx].eqtTypeCode == "HI98") {
        temp.imgUrl = imageUrl.HI98Url;
      } else if (showlist[idx].eqtTypeCode == "HK500E") {
        temp.imgUrl = imageUrl.HK500Url;
      } else if (showlist[idx].eqtTypeCode == "HK600") {
        temp.imgUrl = imageUrl.HK600Url;
      } else if (showlist[idx].eqtTypeCode == "HK616" || showlist[idx].eqtTypeCode == "HM616") {
        temp.imgUrl = imageUrl.HK616Url;
      } else if (showlist[idx].eqtTypeCode == "HK618" || showlist[idx].eqtTypeCode == "HM618") {
        temp.imgUrl = imageUrl.HM618Url;
      } else if (showlist[idx].eqtTypeCode == "HK716R") {
        temp.imgUrl = imageUrl.HK716Url;
      } else if (showlist[idx].eqtTypeCode == "HK718") {
        temp.imgUrl = imageUrl.HK718Url;
      } else if (showlist[idx].eqtTypeCode == "HK950S") {
        temp.imgUrl = imageUrl.HK950Url;
      } else if (showlist[idx].eqtTypeCode == "HS5500R") {
        temp.imgUrl = imageUrl.HS5500Url;
      } else if (showlist[idx].eqtTypeCode == "HS6500R") {
        temp.imgUrl = imageUrl.HS6500Url;
      } else if (showlist[idx].eqtTypeCode == "HT5000") {
        temp.imgUrl = imageUrl.HT5000Url;
      } else {
        temp.imgUrl = imageUrl.DefaultUrl;
      }
      // temp.property = "异常";
      temp.otherUrl = imageUrl.faultUrl;
      faultList.push(temp);
    }
    console.log("faultList:-----");
    console.log(faultList);
  },

  /** 设备状态统计*/
  queryEqtStatus: function() {
    var that = this;
    that.sendEqtStatusAndRecive();
  },

  /** 获取设备列表*/
  queryEqtList: function() {
    var that = this;
    that.sendEqtListAndRecive();
  },

  /**获取故障设备列表 */
  queryFaultList: function() {
    var that = this;
    that.sendFaultListAndRecive();
  },

  createpiestatecanvaschart: function() {
    var that = this;
    var windowWidth = 320;
    try {
      var res = wx.getSystemInfoSync();
      windowWidth = res.windowWidth;
    } catch (e) {
      console.error('getSystemInfoSync failed!');
    }

    console.error('that.data.pageindex == 0');
    that.setData({
      chartname: '机器运行状态图(滑动)',
    })
    pieChart = new wxCharts({
      animation: true,
      canvasId: 'pieCanvas',
      type: 'pie',
      series: [{
        name: '在线',
        data: wx.getStorageSync("regularcount"),
      }, {
        name: '离线',
        data: wx.getStorageSync("unregularcount"),
      }, {
        name: '异常',
        data: wx.getStorageSync("faultcount"),
      }],
      width: that.data.chartwidth,
      height: that.data.chartheight,
      dataLabel: true,
      legend: true,
      legendPositon: 'bottom'
    });
    that.data.pagelastindex = 0;
    that.data.pageindex = 1;
  },
  createpietypecanvaschart: function() {
    var that = this;
    var windowWidth = 320;
    try {
      var res = wx.getSystemInfoSync();
      windowWidth = res.windowWidth;
    } catch (e) {
      console.error('getSystemInfoSync failed!');
    }
    that.setData({
      chartname: '机器类型图(滑动)',
    })
    console.error('that.data.pageindex == 1');
    console.log("HK718-move:" + wx.getStorageSync("count718"));
    console.log("HK618-move:" + wx.getStorageSync("count618"));

    pieChart = new wxCharts({
      animation: true,
      canvasId: 'pieCanvas',
      type: 'pie',
      series: [{
        name: 'HK98',
        data: wx.getStorageSync("countHI98"),
      }, {
        name: 'HK716',
        data: wx.getStorageSync("count716"),
      }, {
        name: 'HK718',
        data: wx.getStorageSync("count718"),
      }, {
        name: 'HM618',
        data: wx.getStorageSync("count618"),
      }, {
        name: 'HK616',
        data: wx.getStorageSync("count616"),
      }, {
        name: 'HK950',
        data: wx.getStorageSync("count950"),
      }, {
        name: 'WINPOS',
        data: wx.getStorageSync("countWINPOS"),
      }, {
        name: '其他',
        data: wx.getStorageSync("countOTHER"),
      }],
      width: that.data.chartwidth,
      height: that.data.chartheight,
      dataLabel: true,
      legend: true,
      legendPositon: 'bottom'
    });
    that.data.pagelastindex = 1;
    that.data.pageindex = 0;
  },
  onReady: function() {
    // 生命周期函数--监听页面初次渲染完成
    var that = this;
    that.createpiestatecanvaschart();

    that.data.pagelastindex = 1;
    that.data.pageindex = 1;

  },

  /* flodFn: function() {
     this.isFold = !this.isFold
     console.log(this.isFold);
     this.setData({
       isFold: this.isFold,
     });
   },*/

  tapClassify: function(e) {
    var that = this;
    var id = e.target.dataset.id;
    if (id === that.data.classifyViewed) {
      that.setData({
        scrolltop: 0,
      })
    } else {
      that.setData({
        classifyViewed: id,
      });
      console.log('id:', that.data.classifyViewed)
      for (var i = 0; i < that.data.categories.length; i++) {
        if (id === that.data.categories[i].id) {
          that.setData({
            classifyViewed: that.data.categories[i].id,
            scrolltop: 0,
            detailListCurrent: that.data.detailList[i]
          })
        }
      }
    }

  },

  //事件处理函数
  toDetailsTap: function(e) {
    var that = this;
    console.log("classifyViewed:" + that.data.classifyViewed);
    console.log("e.currentTarget.dataset.id:" + e.currentTarget.dataset.id);
    console.log("e.currentTarget.dataset.uuid:" + e.currentTarget.dataset.uuid);
    // console.log("e.currentTarget.dataset.eqtstate:" + e.currentTarget.dataset.eqtstate);
    if (that.data.classifyViewed == 3) {
      wx.navigateTo({
        url: "/pages/faultinfo/faultdetail?uuid=" + e.currentTarget.dataset.uuid
      })
    } else {
      wx.navigateTo({
        url: "/pages/posinfo/posconfigure?id=" + e.currentTarget.dataset.id
      })
    }
  },

  moveHandler: function(e) {
    var that = this;
  

    console.log("lastindex=%d,pageindex=%d", that.data.pagelastindex, that.data.pageindex);

    if (that.data.pagelastindex != that.data.pageindex) {
      return;
    }
    console.log("lastindex=%d,pageindex=%d", that.data.pagelastindex, that.data.pageindex);
    if (that.data.pageindex == 0) {

      that.createpiestatecanvaschart();

    } else {
      that.createpietypecanvaschart();
    }
  },

  touchEndHandler: function(e) {

    console.log("touchEndHandler!!!\n");
    this.data.pagelastindex = this.data.pageindex;
  },

  onLoad: function(e) {
    var that = this;
    console.log("onLoad--------------");
    var windowWidth = 320;
    try {
      var res = wx.getSystemInfoSync();
      windowWidth = res.windowWidth * 0.9;
    } catch (e) {
      console.error('getSystemInfoSync failed!');
    }

    that.setData({
      totalcount: '',
      regularcount: '',
      unregularcount: '',
      faultcount: '',
    })
    that.queryEqtStatus();
    setTimeout(function() {
      that.setData({})
    }, 1500);
    that.genCategories();
    that.queryFaultList();
    setTimeout(function() {
      that.setData({})
    }, 1500);
    that.queryEqtList();
    setTimeout(function() {
      that.setData({})
    }, 1500);
    console.log("regularcount2:" + wx.getStorageSync("regularcount"));
    console.log("unregularcount2:" + wx.getStorageSync("unregularcount"));
    console.log("faultcount2:" + wx.getStorageSync("faultcount"));
  },

  onUnload: function(e) {
    var that = this;
    that.setData({
      totalcount: '',
      regularcount: '',
      unregularcount: '',
      faultcount: '',
      orgId: '',
      start: '',
      pageSize: '100',
      id: '',
      uuid: '',
      backgroundcanvas: [, ],
      chartname: '机器运行状态图(滑动)',
      indicatorDots: true,
      autoplay: false,
      circular: false,
      duration: 300,
      current: 0,
      canvasId: 'posstate',
      categories: [],
      activeCategoryId: null,
      detail: [],
      detailList: [],
      detailListCurrent: [],
      faultList: [],
      scrollTop: 0,
      classifyViewed: null,
      isFold: false,
      swiperheight: 580,
      canvasrheight: 540,
      chartwidth: 320,
      chartheight: 240
    })
  }

})