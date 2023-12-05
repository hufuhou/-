import request from '@/utils/request'

// 查询出库明细列表
export function listDetails(query) {
  return request({
    url: '/out_details/details/list',
    method: 'get',
    params: query
  })
}

// 查询出库明细列表带用户名
export function listDetailsWithUser(query) {
  return request({
    url: '/out_details/details/listAndUser',
    method: 'get',
    params: query
  })
}

// 查询出库明细详细

export function getDetails(omId) {
  return request({
    url: '/out_details/details/' + omId,
    method: 'get'
  })
}

// 新增出库明细

export function addDetails(data) {
  return request({
    url: '/out_details/details',
    method: 'post',
    data: data
  })
}

// 修改出库明细

export function updateDetails(data) {
  return request({
    url: '/out_details/details',
    method: 'put',
    data: data
  })
}

// 删除出库明细

export function delDetails(omId) {
  return request({
    url: '/out_details/details/' + omId,
    method: 'delete'
  })
}

//查询出库订单数
export function getNumber() {
  return request({
    url: '/out_details/details/numbers',
    method: 'get'
  })
}
