import request from '@/utils/request'

// 查询销售明细列表
export function listSales(query) {
  return request({
    url: '/sales/sales/list',
    method: 'get',
    params: query
  })
}

// 查询销售明细列表带用户名
export function listSalesWithUser(query) {
  return request({
    url: '/sales/sales/listWithUser',
    method: 'get',
    params: query
  })
}

// 查询销售明细详细
export function getSales(sdId) {
  return request({
    url: '/sales/sales/' + sdId,
    method: 'get'
  })
}

// 新增销售明细
export function addSales(data) {
  return request({
    url: '/sales/sales',
    method: 'post',
    data: data
  })
}

// 修改销售明细
export function updateSales(data) {
  return request({
    url: '/sales/sales',
    method: 'put',
    data: data
  })
}

// 删除销售明细
export function delSales(sdId) {
  return request({
    url: '/sales/sales/' + sdId,
    method: 'delete'
  })
}

// 查询销售明细总数
export function getNumbers() {
  return request({
    url: 'sales/sales/numbers',
    method: 'get'
  })
}

// 查询销售总金额
export function getSumSale() {
  return request({
    url: 'sales/sales/sumSale',
    method: 'get'
  })
}
