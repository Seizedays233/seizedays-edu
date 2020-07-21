import request from '@/utils/request'

const api_name = '/admin/edu/video'

export default {

  save(videoInfo) {
    return request({
      url: `${api_name}/save-video-info`,
      method: 'post',
      data: videoInfo
    })
  },

  getById(id) {
    return request({
      url: `${api_name}/video-info/${id}`,
      method: 'get'
    })
  },

  updateById(videoInfo) {
    return request({
      url: `${api_name}/update-video-info/${videoInfo.id}`,
      method: 'put',
      data: videoInfo
    })
  },

  removeById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'delete'
    })
  }
}
