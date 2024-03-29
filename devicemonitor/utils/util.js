const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

const getNowDate = date1 => {
  const year = date1.getFullYear()
  const month = date1.getMonth() + 1
  const day = date1.getDate()

  return [year, month, day].map(formatNumber).join('-')
}

//获取当前页url/
function getCurrentPageUrl() {
  var pages = getCurrentPages() //获取加载的页面 
  var currentPage = pages[pages.length - 1] //获取当前页面的对象 
  var url = currentPage.route //当前页面url 
  return url
}

module.exports = {
  formatTime: formatTime,
  getNowDate: getNowDate,
  getCurrentPageUrl: getCurrentPageUrl
}