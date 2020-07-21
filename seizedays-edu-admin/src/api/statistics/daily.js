import request from '@/utils/request'
const api_name = '/admin/statistics/daily'

export default{

  createStatistics(day) {
    return request({// 封装axios
      url: `${api_name}/${day}`,
      method: 'get'
    })
  },

  showChart(searchObj) {
    return request({// 封装axios
      url: `${api_name}/show-chart/${searchObj.begin}/${searchObj.end}/${searchObj.type}`,
      method: 'get'
    })
  }

}
