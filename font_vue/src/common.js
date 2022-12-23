//获得当前时间 yyyy-MM-dd
export function getCurDate() {
  var now = new Date()
  var year = now.getFullYear()
  var month = now.getMonth() + 1
  var day = now.getDate()

  month = month < 10 ? '0' + month : month
  day = day < 10 ? '0' + day : day
  return year + '-' + month + '-' + day
}

//SessionStorage 关闭浏览器就不存在了。
//LocalStorage 本地（关闭浏览器数据还在）  Vuex  替代cookie

//向SessionStorage 存储一个JSON对象
export function setSessionStorage(key, value) {
  sessionStorage.setItem(key, JSON.stringify(value)) //JSON-转换-字符串
}

//取sessionStorage 中的对象
export function getSessionStorage(key) {
  var str = sessionStorage.getItem(key)
  if (str == '' || str == null || str == 'null' || str == undefined) {
    return null
  } else {
    return JSON.parse(str) //字符串转为 JSON对象
  }
}

//删除 sessionStorage 中的对象
export function removeSessionStorage(key) {
  sessionStorage.removeItem(key)
}

//向LocalStorage 存储一个JSON对象
export function setLocalStorage(key, value) {
  localStorage.setItem(key, JSON.stringify(value)) //JSON-转换-字符串
}

//取向LocalStorage中对象
export function getLocalStorage(key) {
  var str = localStorage.getItem(key)
  if (str == '' || str == null || str == 'null' || str == undefined) {
    return null
  } else {
    return JSON.parse(str) //字符串转为 JSON对象
  }
}

//删除LocalStorage中的对象
export function removeLocalStorage(key) {
  localStorage.removeItem(key)
}
