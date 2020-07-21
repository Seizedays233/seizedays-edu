<template>
  <div class="app-container">
    <!--表单-->
    <el-form :inline="true" class="demo-form-inline">

      <el-form-item>
        <el-select v-model="searchObj.type" clearable placeholder="请选择">
          <el-option label="学员登录数统计" value="login_num"/>
          <el-option label="学员注册数统计" value="register_num"/>
          <el-option label="课程播放数统计" value="video_view_num"/>
          <el-option label="每日课程数统计" value="course_num"/>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-date-picker
          v-model="searchObj.begin"
          type="date"
          placeholder="选择开始日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchObj.end"
          type="date"
          placeholder="选择截止日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-button
        :disabled="btnDisabled"
        type="primary"
        icon="el-icon-search"
        @click="showChart()">查询</el-button>
    </el-form>

    <div class="chart-container">
      <div id="chart" class="chart" style="height:500px;width:100%" />
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'
import daily from '@/api/statistics/daily'

export default {
  data() {
    return {
      searchObj: {// 查询表单对象
        begin: '',
        end: '',
        type: ''
      },
      btnDisabled: false, // 按钮状态
      chart: null, // 图表对象
      title: '', // 图表标题
      xData: [], // x轴数据
      yData: [] // y轴数据
    }
  },

  methods: {
    // 展示图表
    showChart() {
      // 初始化数据（从数据库查询，调用api）
      this.initChartData() // 异步
    },

    // 初始化数据
    initChartData() {
      daily.showChart(this.searchObj).then(response => {
        // 横轴的日期列表数据
        this.xData = response.data.dateList
        // 纵轴的统计数据数值
        this.yData = response.data.dataList

        switch (this.searchObj.type) {
          case 'register_num':
            this.title = '学员注册数统计'
            break
          case 'login_num':
            this.title = '学员登录数统计'
            break
          case 'video_view_num':
            this.title = '课程播放数统计'
            break
          case 'course_num':
            this.title = '每日课程数统计'
            break
          default:
            break
        }

        // 渲染图表
        this.setChart()
      })
    },

    // 渲染图表
    setChart() {
      // 基于准备好的dom，初始化echarts实例
      this.chart = echarts.init(document.getElementById('chart'))

      // 指定图表的配置项和数据
      var option = {
        title: {
          text: this.title
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['数量']
        },
        xAxis: {
          data: this.xData
        },
        yAxis: {},
        series: [{
          name: '数量',
          type: 'line',
          data: this.yData
        }],

        // 区域缩放
        dataZoom: [{
          show: true,
          height: 30,
          xAxisIndex: [
            0
          ],
          bottom: 30,
          start: 10,
          end: 80,
          handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
          handleSize: '110%',
          handleStyle: {
            color: '#d3dee5'

          },
          textStyle: {
            color: '#fff'
          },
          borderColor: '#90979c'
        },
        {
          type: 'inside',
          show: true,
          height: 15,
          start: 1,
          end: 35
        }]
      }

      this.chart.setOption(option)
    }

  }
}
</script>

