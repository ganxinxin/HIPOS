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
      'h-app-id': '1',
      'token': 'eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2luZm8iOnsidGVuYW50X2lkIjoiaGl0bXMiLCJpc19hZG1pbiI6IjAiLCJ1c2VyX2NvZGUiOiJkZW5nIiwidXNlcl9pZCI6InVzZXJJZDEyODA2MTkyNDAzMjMyODU1MDYiLCJ1c2VyX25hbWUiOiJkZW5nIiwic2V4IjoiMCIsIm1vYmlsZSI6IjEzMzMzMTMxMzEzIiwiZW1haWwiOiIxQDIuY29tIiwiYXBwX2lkIjoiMDAyIn0sImV4cCI6MTU0MjI0NTkyMzk5MywidGltZW91dCI6MjU5MjAwMH0.ZU7bbUpNzys--E60dTDzhJfEcqw6iTbulLGO9HWAj160Rt53S8tOkYp8lBUncRmAANEB0TuYyp9T8x5mruQ7B6nr8lUXUiwsYCafjz_euSbyb8_nlFHL-WKH0Z56xrENcOUrdCEGFit4g5PXwwVy9tGOSuanFt2NFPfcm0JNqzOR9hDnb0EM5kuryHRBcH6dttmN8D_SnD3ztNh5SxsstoQYTplZ_PhM8YH3fNhDJp9_RKqunIakoAi0xyR7_ZFzPXNYlq0hb4MPUSuxJEU99Z8oO5oYkdwzZLTNMiKLPXwHaVXZbiycUKTQcjRH6lMpt5qSIXNA5_bCmD4MQpSHMg'
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