import request from '@/utils/request'

const api_name = '/admin/edu/teacher'

export default{
  getList() {
    return request({// 封装axios
      url: '/admin/edu/teacher',
      method: 'get'
    })
  },

  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj // 在url后面追加查询字符串
    })
  },

  removeById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'delete'
    })
  },

  save(teacher) {
    return request({
      url: api_name,
      method: 'post',
      data: teacher // 作为请求报文体中的json数据传输
    })
  },

  getById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'get'
    })
  },

  updateById(teacher) {
    return request({
      url: `${api_name}/${teacher.id}`,
      method: 'put',
      data: teacher // 作为请求报文体中的json数据传输
    })
  }
}
