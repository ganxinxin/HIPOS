// pages/posinfo/posconfigure.js

import config from '../../utils/config.js'
var network_util = require('../../utils/network_util.js');
var json_util = require('../../utils/json_util.js');
var util = require('../../utils/util.js');
var wxCharts = require('../../utils/wxcharts.js');
const app = getApp();
var lineChart = null;
var startPos = null;
var timer; // 计时器
var timeInterval = 20000;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    //详情
    popupNet: false,
    popupCPU: false,
    popupHD: false,
    popupMemory:false,
    popupMainBoard: false,
    popupSys: false,
    popupSoft: false,
    popupProcess: false,
    canvashidden: '',
    //设备信息
    eqtName: "",
    eqtTypeName: "",
    eqtIp: "",
    depName: "",
    orgName: "",
    eqtMac: "",
    eqtPlatform: "",
    eqtState: "",
    eqtId:'',
   
    dataCPU: [],           // cpu信息
    dataMainBoard: [],     // 主板信息
    dataMemory: [],        // 内存信息
    dataContext: [],        // 设备场景表
    dataHD: [],            // 硬盘信息
    dataSoft: [],          // 安装软件信息
    dataSys: [],           // 操作系统信息信息
    dataProcess: [],          // 系统进程信息
    dataListShow: [],      // list显示信息


    // lineChart
    categories: [],
    cpuuserates: [],
    memuserates: []
  },
  //详细信息
  handleClose() {
    var that = this;
    that.setData({
      popupNet: false,
      popupCPU: false,
      popupHD:false,
      popupMemory:false,
      popupMainBoard:false,
      popupSys:false,
      popupSoft:false,
      popupProcess: false,
    });
    setTimeout(() => {
      that.setData({
        canvashidden: ''
      });
    }, 50)
  },
  NetDetailInfo() {
    var that = this;
    that.setData({ popupNet: true, 
      canvashidden: 'hidden'
    });
  },
  CPUDetailInfo() {
    var that = this;
    that.setData({
      popupCPU: true,
      canvashidden: 'hidden'
    });
  },
  HDDetailInfo() {
    var that = this;
    that.setData({
      popupHD: true,
      canvashidden: 'hidden'
    });
  },
  MemoryDetailInfo() {
    var that = this;
    that.setData({
      popupMemory: true,
      canvashidden: 'hidden'
    });
  },
  MainBoardDetailInfo() {
    var that = this;
    that.setData({
      popupMainBoard: true,
      canvashidden: 'hidden'
    });
  },
  SysDetailInfo() {
    var that = this;
    that.setData({
      popupSys: true,
      canvashidden: 'hidden'
    });
  },
  SoftDetailInfo() {
    var that = this;
    that.setData({
      popupSoft: true,
      canvashidden: 'hidden'
    });
  },
  ProcessDetailInfo() {
    var that = this;
    that.setData({
      popupProcess: true,
      canvashidden: 'hidden'
    });
  },

  // lineChart
  touchHandler: function (e) {
    lineChart.scrollStart(e);
  },
  moveHandler: function (e) {
  },

  touchEndHandler: function (e) {
    lineChart.scrollEnd(e);
    lineChart.showToolTip(e, {
      format: function (item, category) {
        return category + ' ' + item.name + ':' + item.data
      }
    });
  },

  formatDate: function (date) {
    return date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
  },

  dateminussec: function (date, sec) {
    var tempdate = date;
    var t_s = tempdate.getTime();
    tempdate.setTime(t_s - sec);
    console.log(tempdate)
    return tempdate;
  },

  //初始化图标数据，前面300秒和当前数据一致
  initChartData: function (res) {
    var that = this;
    for (var i = 0; i < 10; i++) {
      var time = new Date();
      var strDate = that.formatDate(that.dateminussec(time, (10 - i) * timeInterval));
      that.data.categories.splice(0, 0, strDate);
      if (that.data.eqtState =="0"){
        that.data.cpuuserates.splice(0, 0, Math.random() * (60 - 15) + 15);
        that.data.memuserates.splice(0, 0, Math.random() * (50 - 30) + 30);
      }
      else{
        that.data.cpuuserates.splice(0, 0, 0);
        that.data.memuserates.splice(0, 0, 0);
      }
    }
  },
  //更新图标数据
  updateChartData: function (res) {
    var that = this;
    that.data.categories.splice(that.data.categories.length - 1, 1);
    that.data.cpuuserates.splice(that.data.categories.length - 1, 1);
    that.data.memuserates.splice(that.data.categories.length - 1, 1);
    that.data.categories.splice(0, 0, that.formatDate(new Date()));
    if (that.data.eqtState == "0") {
      that.data.cpuuserates.splice(0, 0, Math.random() * (60 - 15) + 15);
      that.data.memuserates.splice(0, 0, Math.random() * (50 - 30) + 30);
    }
    else {
      that.data.cpuuserates.splice(0, 0, 0);
      that.data.memuserates.splice(0, 0, 0);
    }
  },
  //采集CPU和内存信息的数据 
  CollectingData: function () {
    var that = this;
    timer = setTimeout(function () {
      //var currurl = util.getCurrentPageUrl();
      //if (currurl.search("psoconfigure") != -1)
      {
        console.log("----CollectingData----");
        // that.sendLineChartAndRecive();
        if (that.data.categories.length < 1)
          that.initChartData(that.data.categories);
        else
          that.updateChartData(that.data.categories);
        that.updateChart();
        that.CollectingData();
      }
      
    }, timeInterval);
    //timeInterval = timeInterval;
  },
  updateChart: function () {
    var that = this;
    var windowWidth = 2000;
    try {
      var res = wx.getSystemInfoSync();
      windowWidth = res.windowWidth;
    } catch (e) {
      console.error('getSystemInfoSync failed!');
    }
    lineChart = new wxCharts({
      canvasId: 'lineCanvas',
      type: 'line',
      categories: that.data.categories,
      animation: false,
      series: [{
        name: 'CPU',
        data: that.data.cpuuserates,
      },
      {
        name: '内存',
        data: that.data.memuserates,
      }
      ],
      xAxis: {
        disableGrid: false,
        rotate: 0
      },
      yAxis: {
        title: '占用率',
        min: 0,
        max: 100
      },
      width: windowWidth,
      height: 160,
      dataLabel: false,
      dataPointShape: false,
      enableScroll: false,
      extra: {
        lineStyle: 'curve'
      }
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    that.setData({
      eqtId: options.id,
    })
    that.sendEqtDetailAndRecive();
    that.CollectingData();
    console.log("id:"+options.id);
    that.setData({
      eqtId:options.id,
    })
    that.sendEqtInfoAndRecive();
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
  * 请求LineCHart数据
  */
  // sendLineChartAndRecive: function () {
  //   var that = this;
  //   let map = new Map();
  //   map.set('eqtId', that.data.eqtId);
  //   let jsonData = json_util.mapToJson(map);
  //   console.log("jsonData:---" + jsonData);
  //   var url = config.queryEqtCurrentInfo;
  //   console.log("url:---" + url);
  //   network_util._post(url, jsonData,
  //     function (res) {
  //       console.log(res)
  //       if (that.data.categories.length < 1)
  //         that.initChartData(res.data.data);
  //       else
  //         that.updateChartData(res.data.data);
  //       that.updateChart();
  //     },
  //     function (res) {
  //       console.log('fail');
  //       console.log(res);
  //       wx.showModal({
  //         title: '查询失败',
  //         content: res.errMsg,
  //         success: function (res) {
  //         },
  //         fail: function (res) {

  //         },
  //         complete: function (res) {

  //         }
  //       });
  //     },
  //     function (res) {
      
  //     });
  // },
  /**
    * 查询设备信息
    */
  sendEqtInfoAndRecive: function () {
    var that = this;
    let map = new Map();
    wx.showLoading({
      title: '加载中...',
    })
    map.set('eqtId', that.data.eqtId);
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    var url = config.queryEqtInfo;
    console.log("url:---" + url);
    network_util._post(url, jsonData,
      function (res) {
        console.log(res)
        that.setData({
          eqtName: res.data.data.eqtName,
          eqtTypeName: res.data.data.eqtTypeName,
          eqtIp: res.data.data.eqtIp,
          depName: res.data.data.depName,
          orgName: res.data.data.orgName,
          eqtMac: res.data.data.eqtMac,
          eqtPlatform: res.data.data.eqtPlatform,
          eqtState: res.data.data.eqtState
        });
        // //防止图标空白，首先请求数据
        // that.sendLineChartAndRecive();
        // //绘制图表
        // that.updateChart();
        if (that.data.categories.length < 1)
          that.initChartData(that.data.categories);
        else
          that.updateChartData(that.data.categories);
        that.updateChart();
      },
      function (res) {
        console.log('fail');
        console.log(res);
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
   * 查询配置信息
   */
  sendEqtDetailAndRecive: function () {
    var that = this;
    let map = new Map();
    wx.showLoading({
      title: '加载中...',
    })
    map.set('id', that.data.eqtId);
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    var url = config.queryEqtDetail;
    console.log("url:---" + url);
    network_util._post(url, jsonData,
      function (res) {
        console.log(res)
        // list列表
        var obj = {};

        obj.ItemIndex = 1;
        obj.imageUrl = "http://hitms-web.oss-cn-beijing.aliyuncs.com/miniprog/context.png";
        obj.textItem1 = "登录用户：" + res.data.context[0].user;
        obj.textItem2 = "网络状态：" + res.data.context[0].netStatus;
        obj.textItem3 = "IMEI号：" + res.data.context[0].imei;
        obj.textItem4 = "IMSI号：" + res.data.context[0].imsi;
        let dataListShow = that.data.dataListShow;
        dataListShow.push(obj);

        obj = {};
        obj.ItemIndex = 2;
        obj.imageUrl = "http://hitms-web.oss-cn-beijing.aliyuncs.com/miniprog/cpu.png";
        obj.textItem1 = "CPU厂商：" + res.data.cpu[0].cpuProdby;
        obj.textItem2 = "CPU型号：" + res.data.cpu[0].cpuType;
        obj.textItem3 = "CPU主频：" + res.data.cpu[0].cpuSpeed;
        obj.textItem4 = "核心数：" + res.data.cpu[0].dieNum;
        dataListShow.push(obj);

        obj = {};
        obj.ItemIndex = 3;
        obj.imageUrl = "http://hitms-web.oss-cn-beijing.aliyuncs.com/miniprog/hd.png";
        obj.textItem1 = "硬盘厂商：" + res.data.hd[0].hdProdby;
        obj.textItem2 = "硬盘型号：" + res.data.hd[0].hdModel;
        obj.textItem3 = "硬盘大小：" + res.data.hd[0].hdSize;
        obj.textItem4 = "接口类型：" + res.data.hd[0].hdInterface;
        dataListShow.push(obj);

        obj = {};
        obj.ItemIndex = 4;
        obj.imageUrl = "http://hitms-web.oss-cn-beijing.aliyuncs.com/miniprog/mem.png";
        obj.textItem1 = "内存厂商：" + res.data.memory[0].prodBy;
        obj.textItem2 = "内存类型：" + res.data.memory[0].memType;
        obj.textItem3 = "内存大小：" + res.data.memory[0].memSize;
        obj.textItem4 = "型号：" + res.data.memory[0].modelNum;
        dataListShow.push(obj);

        obj = {};
        obj.ItemIndex = 5;
        obj.imageUrl = "http://hitms-web.oss-cn-beijing.aliyuncs.com/miniprog/mainboard.png";
        obj.textItem1 = "生产商：" + res.data.mainboard[0].manufacturer;
        obj.textItem2 = "主板型号：" + res.data.mainboard[0].boardType;
        obj.textItem3 = "芯片组：" + res.data.mainboard[0].chipSet;
        obj.textItem4 = "BIOS版本：" + res.data.mainboard[0].biosVer;
        dataListShow.push(obj);

        obj = {};
        obj.ItemIndex = 6;
        obj.imageUrl = "http://hitms-web.oss-cn-beijing.aliyuncs.com/miniprog/os.png";
        obj.textItem1 = "计算机厂商：" + res.data.sys[0].computerMaker;
        obj.textItem2 = "计算机名：" + res.data.sys[0].computerName;
        obj.textItem3 = "计算机型号：" + res.data.sys[0].computerModel;
        obj.textItem4 = "操作系统：" + res.data.sys[0].osName;
        dataListShow.push(obj);

        obj = {};
        obj.ItemIndex = 7;
        obj.imageUrl = "http://hitms-web.oss-cn-beijing.aliyuncs.com/miniprog/software.png";
        obj.textItem1 = "软件名称：" + res.data.soft[0].softName;
        obj.textItem2 = "软件厂商：" + res.data.soft[0].softProd;
        obj.textItem3 = "软件安装位置：" + res.data.soft[0].softPath;
        obj.textItem4 = "软件版本：" + res.data.soft[0].softVersion;
        dataListShow.push(obj);

        obj = {};
        obj.ItemIndex = 8;
        obj.imageUrl = "http://hitms-web.oss-cn-beijing.aliyuncs.com/miniprog/process.png";
        obj.textItem1 = "进程名称：" + res.data.process[0].processName;
        obj.textItem2 = "进程ID：" + res.data.process[0].processId;
        obj.textItem3 = "路径：" + res.data.process[0].processUser;
        obj.textItem4 = "描述：" + res.data.process[0].description;
        dataListShow.push(obj);

        // setdata
        that.setData({
          dataListShow: dataListShow,
          dataContext: res.data.context,       // 设备场景
          dataCPU: res.data.cpu,               // cpu信息
          dataHD: res.data.hd,                 // 硬盘信息
          dataMemory: res.data.memory,         // 内存信息
          dataMainBoard: res.data.mainboard,   // 主板信息
          dataSys: res.data.sys,               // 操作系统信息信息
          dataSoft: res.data.soft,             // 安装软件信息
          dataProcess: res.data.process,          // 系统进程信息
        });
      },
      function (res) {
        console.log('fail');
        console.log(res);
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
        setTimeout(function () {
          wx.hideLoading();
        }, 2000);
      });
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
    clearTimeout(timer);
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
})