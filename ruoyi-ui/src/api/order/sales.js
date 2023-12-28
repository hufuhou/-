import request from '@/utils/request'

// 查询销售订单列表
export function listSales(query) {
  return request({
    url: '/order/sales/list',
    method: 'get',
    params: query
  })
}

// 查询销售订单详细
export function getSales(sId) {
  return request({
    url: '/order/sales/' + sId,
    method: 'get'
  })
}

// 新增销售订单
export function addSales(data) {
  return request({
    url: '/order/sales',
    method: 'post',
    data: data
  })
}

// 修改销售订单
export function updateSales(data) {
  return request({
    url: '/order/sales',
    method: 'put',
    data: data
  })
}

// 删除销售订单
export function delSales(sId) {
  return request({
    url: '/order/sales/' + sId,
    method: 'delete'
  })
}

// 获取销售编号
export function getCode() {
  return request({
    url: '/order/sales/getsCode',
    method: 'get'
  })
}

// 审核状态修改
export function updateAudit(status, sId) {
  return request({
    url: '/order/sales/' + status + '/' + sId,
    method: 'get'
  })
}
