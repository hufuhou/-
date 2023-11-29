import request from '@/utils/request'

// 查询进货订单列表
export function listPurchase(query) {
  return request({
    url: '/order/purchase/list',
    method: 'get',
    params: query
  })
}

// 查询进货订单详细
export function getPurchase(poId) {
  return request({
    url: '/order/purchase/' + poId,
    method: 'get'
  })
}

// 新增进货订单
export function addPurchase(data) {
  return request({
    url: '/order/purchase',
    method: 'post',
    data: data
  })
}

// 修改进货订单
export function updatePurchase(data) {
  return request({
    url: '/order/purchase',
    method: 'put',
    data: data
  })
}

// 删除进货订单
export function delPurchase(poId) {
  return request({
    url: '/order/purchase/' + poId,
    method: 'delete'
  })
}

