<template>
  <div>
    <div>
      <el-row :gutter="20">

        <el-col :span="6">
          <el-card>
            <div>
              <i class="el-icon-user-solid" style="margin-left:45%"></i>
              <el-statistic group-separator="," :value="value1" :title="title1" style="text-align:">
              </el-statistic>
            </div>
          </el-card>
        </el-col>

        <el-col :span="6">
          <el-card>
            <div>
              <i class="el-icon-data-analysis" style="margin-left:45%"></i>
              <el-statistic title="男女比">
                <template slot="formatter">
                  456/2
                </template>

              </el-statistic>
            </div>
          </el-card>
        </el-col>

        <el-col :span="6">
          <el-card>
            <div>
              <i class="el-icon-data-line" style="margin-left:45%"></i>
              <el-statistic group-separator="," decimal-separator="." :value="value2" :title="title2">
              </el-statistic>
            </div>
          </el-card>
        </el-col>

        <el-col :span="6">
          <el-card>
            <div>
              <i class="el-icon-toilet-paper" style="margin-left:45%"></i>
              <el-statistic group-separator="," decimal-separator="." :value="value3" :title="title3">
              </el-statistic>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <el-divider></el-divider>

    <div>
      <el-row :gutter="20">

        <el-col :span="14">
          <el-card style="width:100%;height:376px">
            <div slot="header" class="comment" style="text-align:center">订单统计</div>
            <div ref="chart4" style="width:100%;height:340px"></div>
          </el-card>
        </el-col>
        <el-col :span="9">
          <el-card style="width:100%;height:376px">
            <div slot="header" class="comment" style="text-align:center">电影地区统计</div>
            <div ref="chart2" style="width:100%;height:340px"></div>
          </el-card>
        </el-col>
      </el-row>
      <el-divider></el-divider>
<!-- 
      <el-row :gutter="20">
        <el-col :span="9">
          <el-card style="width:100%;height:376px">
            <div ref="chart3" style="width:100%;height:376px"></div>
          </el-card>
        </el-col>

        <el-col :span="14">
          <el-card style="width:100%;height:376px">
            <div ref="chart1" style="width:100%;height:376px"></div>
          </el-card>
        </el-col>
      </el-row> -->
    </div>

  </div>

</template>
  
<script>
export default {
  data() {
    return {
      like: true,
      value1: 4154,
      value2: 5,
      value3: 10,
      title1: "总访问量",
      title2: "当日访问量",
      title3: "当日订单数",
      input: "Hello Element UI!",
      chart2Value: [],
      chart4Value: []
    };
  },
  mounted() {
    this.getEchartData1(),
      this.getEchartData2(),
      this.getEchartData4()
  },
  created() {

    this.getChart1()
    this.getChart4()
  },
  methods: {
    getChart1() {
      this.$axios.get('/system/film/getFilmByRegion').then((response) => {
        this.chart2Value = response.data.data
        this.getEchartData2()
      })
    },
    getChart4() {
      this.$axios.get('/system/orders/getTime').then((response) => {
        this.chart4Value = response.data.data
        console.log(this.chart4Value)
        this.getEchartData4()
      })
    },
    getEchartData1() {
      const chart = this.$refs.chart1
      if (chart) {
        var myChart = this.$echarts.init(chart);
        var option;
        // prettier-ignore
        const data = [["2000-06-05", 116], ["2000-06-06", 129], ["2000-06-07", 135], ["2000-06-08", 86], ["2000-06-09", 73], ["2000-06-10", 85], ["2000-06-11", 73], ["2000-06-12", 68], ["2000-06-13", 92], ["2000-06-14", 130], ["2000-06-15", 245], ["2000-06-16", 139], ["2000-06-17", 115], ["2000-06-18", 111], ["2000-06-19", 309], ["2000-06-20", 206], ["2000-06-21", 137], ["2000-06-22", 128], ["2000-06-23", 85], ["2000-06-24", 94], ["2000-06-25", 71], ["2000-06-26", 106], ["2000-06-27", 84], ["2000-06-28", 93], ["2000-06-29", 85], ["2000-06-30", 73], ["2000-07-01", 83], ["2000-07-02", 125], ["2000-07-03", 107], ["2000-07-04", 82], ["2000-07-05", 44], ["2000-07-06", 72], ["2000-07-07", 106], ["2000-07-08", 107], ["2000-07-09", 66], ["2000-07-10", 91], ["2000-07-11", 92], ["2000-07-12", 113], ["2000-07-13", 107], ["2000-07-14", 131], ["2000-07-15", 111], ["2000-07-16", 64], ["2000-07-17", 69], ["2000-07-18", 88], ["2000-07-19", 77], ["2000-07-20", 83], ["2000-07-21", 111], ["2000-07-22", 57], ["2000-07-23", 55], ["2000-07-24", 60]];
        const dateList = data.map(function (item) {
          return item[0];
        });
        const valueList = data.map(function (item) {
          return item[1];
        });
        option = {
          // Make gradient line here
          visualMap: [
            {
              show: false,
              type: 'continuous',
              seriesIndex: 0,
              min: 0,
              max: 400
            },
            {
              show: false,
              type: 'continuous',
              seriesIndex: 1,
              dimension: 0,
              min: 0,
              max: dateList.length - 1
            }
          ],
          title: [
            {
              left: 'center',
              text: 'Gradient along the y axis'
            },
            {
              top: '55%',
              left: 'center',
              text: 'Gradient along the x axis'
            }
          ],
          tooltip: {
            trigger: 'axis'
          },
          xAxis: [
            {
              data: dateList
            },
            {
              data: dateList,
              gridIndex: 1
            }
          ],
          yAxis: [
            {},
            {
              gridIndex: 1
            }
          ],
          grid: [
            {
              bottom: '60%'
            },
            {
              top: '60%'
            }
          ],
          series: [
            {
              type: 'line',
              showSymbol: false,
              data: valueList
            },
            {
              type: 'line',
              showSymbol: false,
              data: valueList,
              xAxisIndex: 1,
              yAxisIndex: 1
            }
          ]
        };
        option && myChart.setOption(option);
      }
    },
    getEchartData2() {

      const chart = this.$refs.chart2
      if (chart) {
        let arr = []
        console.log(this.chart2Value)
        this.chart2Value.forEach(element => {
          arr.push({
            value: element.value,
            name: element.name
          })
        })

        var myChart = this.$echarts.init(chart);
        var option;
        // prettier-ignore
        option = {

          tooltip: {
            trigger: 'item'
          },
          legend: {
            top: '5%',
            left: 'center',
          },
          series: [
            {

              name: 'Access From',
              type: 'pie',
              radius: ['40%', '70%'],
              avoidLabelOverlap: false,
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: '40',
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data: arr
              // [{"name":"中国",value:3}]
            }
          ]
        };
        option && myChart.setOption(option);
      }
    },


    getEchartData4() {
      const chart = this.$refs.chart4
      if (chart) {
        let arr = []
        this.chart4Value.forEach(element => {
          let newArr = {
            name: element.name,
            value: element.value
          }
          const res = arr.findIndex(ol=>{
            return element.name === ol.name
          })
          console.log(res)
          if(res !== -1){
            arr[res].value = arr[res].value + 1
          }else{
            arr.push(newArr)
          }
          console.log(arr)

        })
        let timeArr = []
        arr.forEach(a=>{
          timeArr.push(a.name)
        })

        let valueArr = []
        arr.forEach(a=>{
          valueArr.push(a.value)
        })

        var myChart = this.$echarts.init(chart);
        var option;
        // prettier-ignore
        option = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              crossStyle: {
                color: '#999'
              }
            }
          },
          toolbox: {
            feature: {
              dataView: { show: true, readOnly: false },
              magicType: { show: true, type: ['line', 'bar'] },
              restore: { show: true },
              saveAsImage: { show: true }
            }
          },

          xAxis: [
            {
              type: 'category',
              data: timeArr,
              axisPointer: {
                type: 'shadow'
              }
            }
          ],
          yAxis: [
            {
              type: 'value',

              min: 0,
              max: 20,
              interval: 50,
              axisLabel: {
                formatter: '{value} 单'
              }
            },
            {
              type: 'value',
              min: 0,
              max: 20,
              interval: 5,
              axisLabel: {
                formatter: '{value} 单'
              }
            }
          ],
          series: [
            {
              name: 'Evaporation',
              type: 'bar',
              tooltip: {
                valueFormatter: function (value) {
                  return value + ' ml';
                }
              },
              data: arr.value
            },

            {
              name: 'Temperature',
              type: 'line',
              yAxisIndex: 1,
              tooltip: {
                valueFormatter: function (value) {
                  return  '当日订单数';
                }
              },
              data: valueArr
            }
          ]
        };
        option && myChart.setOption(option);
      }
    }

  }
};
</script>
<style  >
.like {
  cursor: pointer;
  font-size: 25px;
  display: inline-block;
}

.el-card {
  width: 200px;
  height: 100px;
  margin-left: 50px;
  border-radius: 4px
}
</style>