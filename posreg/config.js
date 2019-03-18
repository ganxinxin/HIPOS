/**
 * 小程序配置文件
 */

//服务器地址
//var host = "https://positms.hisense.com:8443/positms"
//var host = "http://103.94.200.141:8080/positms"
   var host = "http://172.16.45.250:8080/positms"

var config = {

    //服务器地址
    host,
   
    //微信用户合法性检测
  checkUserUrl: host +'/checkUser',
   
    //3.2 用户信息查询
  queryUserUrl: host +'/queryUser',

    //3.3	机器信息注册
    registerPosInfoUrl: host+'/registerPosInfo',

    //3.4	机器信息查询
  queryPosInfoUrl: host +'/queryPosInfo',

    //3.5	机器列表查询
  queryPosListUrl: host +'/queryPosList',

    //3.6	机器运行信息查询
  queryPosStatusUrl: host +'/queryPosStatus',

    //3.7	门店信息查询
  queryShopInfoUrl: host +'/queryShopInfo',

    //3.8	门店列表查询
  queryShopListUrl: host +'/queryShopList',

    //3.9	报警信息查询
  queryAlarmListUrl: host +'/queryAlarmList',

    //3.10	报警详情查询
  queryOneAlarmUrl: host +'/queryOneAlarm',

    //3.11	报警处理
  dealAlarmUrl: host +'/dealAlarm',

    //3.12	故障信息查询
  queryFaultListUrl: host +'/queryFaultList',

    //3.13	故障详情查询
  queryOneFaultUrl: host +'/queryOneFault',

    //3.14	更新故障处理标志
  updateFaultDealFlagUrl: host +'/updateFaultDealFlag'
};

module.exports = config
