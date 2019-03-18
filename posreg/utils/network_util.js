import util from 'util.js';
/**
 * url 请求地址
 * success 成功的回调
 * fail 失败的回调
 */
function _get(url, success, fail, complete) {

  console.log("------start---_get----");
  wx.request({
    url: url,
    header: {
      // 'Content-Type': 'application/json'
    },
    success: function(res) {
      success(res);
    },
    fail: function(res) {
      fail(res);
    },
    complete: function(res) {
      complete(res);
    }
  });
  console.log("----end-----_get----");
}

/**
 * url 请求地址
 * success 成功的回调
 * fail 失败的回调
 */
function _post(url, data, success, fail, complete) {
  console.log("----_post--start-------");
  wx.request({
    url: url,
    header: {
      'content-type': 'application/json',
    },
    method: 'POST',
    data: data,
    success: function(res) {
      success(res);
    },
    fail: function(res) {
      fail(res);
    },
    complete: function(res) {
      complete(res);
    }
  });
  console.log("----end-----_get----");
}


/**
 * url 请求地址
 * success 成功的回调
 * fail 失败的回调
 */
function _post1(url, data, success, fail,complete) {
  console.log("----_post--start-------");
  wx.request({
    url: url,
    header: {
      'content-type': 'application/x-www-form-urlencoded',
    },
    method: 'POST',
    data: data,
    success: function(res) {
      success(res);
    },
    fail: function(res) {
      fail(res);
    },
    complete: function (res) {
      complete(res);
    }
  });
  console.log("----end-----_get----");
}

/**
 * url 请求地址
 * success 成功的回调
 * fail 失败的回调
 */
function _post_json(url, data, success, fail,complete) {
  console.log("----_post--start-------");
  wx.request({
    url: url,
    // header: {
    //     'content-type': 'application/json',
    // },
    method: 'POST',
    data: data,
    success: function(res) {
      success(res);
    },
    fail: function(res) {
      fail(res);
    },
    complete: function (res) {
      complete(res);
    }
  });

  console.log("----end----_post-----");
}
module.exports = {
  _get: _get,
  _post: _post,
  _post1: _post1,
  _post_json: _post_json
}