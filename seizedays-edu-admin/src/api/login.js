import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: '/admin/sysuser/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/admin/sysuser/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/admin/sysuser/logout',
    method: 'post'
  })
}
