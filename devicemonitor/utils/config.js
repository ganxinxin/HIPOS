/**
 * 小程序配置文件
 */

//服务器地址
var host = "https://tst.gateway.hisensehics.com/hitms/mobile"; //"http://10.16.8.97:8899/hitms/mobile"

var config = {

  //服务器地址
  host,

  //微信用户合法性检测
  checkUserUrl: host + '/checkUser',

  // 获取设备CPU和内存使用率
  queryEqtCurrentInfo: host + '/eqt/current/info',

  //获取设备配置详情
  queryEqtDetail: host + '/eqt/detail',

  //设备故障信息查询
  queryEqtFault: host + '/eqt/fault',

  //设备信息查询
  queryEqtInfo: host + '/eqt/info',

  //获取设备列表
  queryEqtList: host + '/eqt/list',

  //设备状态统计
  queryEqtStatus: host + '/eqt/status',

  //创建故障
  queryFaultCreate: host + '/fault/create',

  //获取故障设备列表
  queryFaultList: host + '/fault/list',

  //获取末级组织列表
  queryOrgList: host + '/org/list'
};

module.exports = config