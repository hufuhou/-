import request from '@/utils/request'

// 查询销售明细列表
export function listSalesDetails(query) {
  return request({
    url: '/order/salesDetails/list',
    method: 'get',
    params: query
  })
}

// 查询销售明细详细
export function getSalesDetails(sdId) {
  return request({
    url: '/order/salesDetails/' + sdId,
    method: 'get'
  })
}

// 新增销售明细
export function addSalesDetails(data) {
  return request({
    url: '/order/salesDetails',
    method: 'post',
    data: data
  })
}

// 修改销售明细
export function updateSalesDetails(data) {
  return request({
    url: '/order/salesDetails',
    method: 'put',
    data: data
  })
}

// 删除销售明细
export function delSalesDetails(sdId) {
  return request({
    url: '/order/salesDetails/' + sdId,
    method: 'delete'
  })
}
