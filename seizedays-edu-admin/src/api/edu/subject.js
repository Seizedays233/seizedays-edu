import request from '@/utils/request'
const api_name = '/admin/edu/subject'

export default{

  getNestedTreeList() {
    return request({// 封装axios
      url: `${api_name}`,
      method: 'get'
    })
  }
}
