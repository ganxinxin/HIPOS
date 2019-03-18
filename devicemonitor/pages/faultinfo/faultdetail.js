//获取应用实例
import config from '../../utils/config.js'
var network_util = require('../../utils/network_util.js');
var json_util = require('../../utils/json_util.js');
var util = require('../../utils/util.js');
const app = getApp();

Page({
  // 页面初始数据
  data: {
    // 设备高度
    windowHeight: 0,
 
    //  数据源
    feed: [],
    // feed_length: 0,
    // hot: [],
    // discovery: [],
    // // 更多按钮 触发弹窗
    // showModalStatus: false,

    // indicatorDots: false,
    // autoplay: true,
    // interval: 5000,
    // duration: 1000,
     uuid: '',
  },

  // // 弹窗触发事件
  // powerDrawer: function (e) {
  //   console.log(e);
  //   console.log(this.data.feed);
  //   // 获取数据源
  //   let feed = this.data.feed;
  //   // 得到按钮点击时设置的数据值 data-answerId
  //   let answer_id = e.currentTarget.dataset.answerid;
  //   console.log(answer_id);
  //   // let question_id = e.currentTarget.dataset.questionid;
  //   // 得到按钮点击时设置的数据值 data-statu
  //   let currentStatu = e.currentTarget.dataset.statu;
  //   console.log(111);
  //   console.log(feed);
  //   // 遍历数据源
  //   for (let key of feed) {
  //     if (key.answer_id === answer_id) {
  //       console.log(key.isSelected);
  //       console.log("sss ");
  //       // 
  //       key.isSelected = true;
  //     }
  //   }
  //   console.log(feed);
  //   this.setData({
  //     feed: feed,
  //   });
  //   // const centity = feed.filter((item) => {
  //   //   return answer_id == item.answer_id
  //   // });
  //   // console.log(centity);
  //   // console.log(centity[0].answer_id);
  //   // let count = [];
  //   // const name = feed.map(item =>{
  //   //   // return item.answer_id;
  //   //   return count.push(item.answer_id);
  //   // });
  //   // console.log(count);
  //   // count.map((item2)=>{
  //   //   if(answer_id == item2){

  //   //   }
  //   // });
  //   this.util(currentStatu);
  //   // if(answer_id == centity[0].answer_id){
  //   // this.util(currentStatu); 
  //   // }

  //   // console.log(currentStatu);
  // },
  // // 点击 弹窗关闭
  // hide: function () {
  //   var feed = this.data.feed;
  //   for (let key of feed) {
  //     key.isSelected = false;
  //   }
  //   this.setData({
  //     feed: feed,
  //     showModalStatus: false,
  //   });
  // },

  // // 更多按钮 弹窗
  // util: function (currentStatu) {
  //   /* 动画部分 */
  //   // 第1步：创建动画实例   
  //   var animation = wx.createAnimation({
  //     duration: 200,  //动画时长  
  //     timingFunction: "linear", //动画效果线性  
  //     delay: 0  //0则不延迟  
  //   });
  //   // console.log(this.animation);
  //   // 第2步：这个动画实例赋给当前的动画实例  
  //   this.animation = animation;
  //   // console.log(this);
  //   console.log(this.animation);
  //   // 第3步：执行第一组动画  
  //   // 调用step()来表示一组动画完成
  //   animation.opacity(0).rotateX(-100).step();

  //   // 第4步：导出动画对象赋给数据对象储存  
  //   this.setData({
  //     // 动画实例的export()方法导出动画数据传递给组件的animation属性
  //     animationData: animation.export()
  //   })

  //   // 第5步：设置定时器到指定时候后，执行第二组动画  
  //   setTimeout(function () {
  //     // 执行第二组动画  
  //     animation.opacity(1).rotateX(0).step();
  //     // 给数据对象储存的第一组动画，更替为执行完第二组动画的动画对象  
  //     this.setData({
  //       animationData: animation
  //     })

  //     //关闭  
  //     if (currentStatu == "close") {
  //       this.setData(
  //         {
  //           showModalStatus: false
  //         }
  //       );
  //     }
  //   }.bind(this), 200)

  //   // 显示  
  //   if (currentStatu == "open") {
  //     this.setData(
  //       {
  //         showModalStatus: true
  //       }
  //     );
  //   }
  // },

  onLoad: function (options) {
    console.log(options);
    // 小程序来自微信API
    // 硬件和软件系统的基本信息
    var that = this;
    wx.getSystemInfo({
      success: (res) => {
        this.setData({
          windowHeight: res.windowHeight,
          uuid: options.uuid,
        });
      }
    })
    console.log(that.data.windowHeight);
    that.sendFeedAndRecive();
  },
  /**
    * 请求数据
    */
  sendFeedAndRecive: function () {
    var that = this;
    let map = new Map();
    map.set('uuid', that.data.uuid);
    map.set('start', '0');
    map.set('pageSize', '100');
    let jsonData = json_util.mapToJson(map);
    console.log("jsonData:---" + jsonData);
    var url = config.queryEqtFault;
    console.log("url:---" + url);
    network_util._post(url, jsonData,
      function (res) {
        console.log(res.data.data)
        that.setData({
          feed: res.data.data.page,
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
      });
  },

  // // 回答详情页面跳转
  // bindItemTap: function (e) {
  //   console.log(e);
  //   let answer_id = e.currentTarget.dataset.answerid;
  //   console.log(answer_id);
  //   console.log(`/pages/answer/answer?answer_id=${answer_id}`);
  //   wx.navigateTo({
  //     url: `/pages/answer/answer?answer_id=${answer_id}`
  //   })
  // },
  // questionDetailTap: function (e) {
  //   console.log(e);
  //   let question_id = e.currentTarget.dataset.questionid;
  //   console.log(question_id);
  //   console.log(`/pages/questionDetail/questionDetail?question_id=${question_id}`);
  //   wx.navigateTo({
  //     url: `/pages/questionDetail/questionDetail?question_id=${question_id}`
  //   })
  // },
  // // 评论页面跳转
  // commentTap: function (e) {
  //   let question_id = e.currentTarget.dataset.questionid;
  //   wx.navigateTo({
  //     url: `/pages/comment/comment?question_id=${question_id}`
  //   })
  // },
  // followQuestion: function (e) {
  //   console.log(e);
  //   let question_id = e.currentTarget.dataset.questionid;
  //   console.log(question_id);
  //   let feed = this.data.feed;
  //   console.log(feed);

  //   for (let key of feed) {
  //     if (question_id == key.question_id) {
  //       console.log(key.question_id);
  //       if (key.follow == '已关注') {
  //         key.follow = '关注问题';
  //       } else {
  //         key.follow = '已关注';
  //       }
  //     }
  //   }
  //   this.setData({
  //     feed: feed,
  //   })

  // },

})
