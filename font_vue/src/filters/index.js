export default{
    //根据日期获得日期的周几
    getWeek(value){
        let getWeek_index = new Date(value).getDay()
        const weekArr = ['周日','周一','周二','周三','周四','周五','周六']
        return weekArr[getWeek_index]
    }
}