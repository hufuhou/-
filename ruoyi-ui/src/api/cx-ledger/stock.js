import request from '@/utils/request'

// 查询库存查询列表
export function listStock(query) {
  return request({
    url: '/cx-ledger/stock/list',
    method: 'get',
    params: query
  })
}

// 查询库存查询详细
export function getStock(sId) {
  return request({
    url: '/cx-ledger/stock/' + sId,
    method: 'get'
  })
}

// 新增库存查询
export function addStock(data) {
  return request({
    url: '/cx-ledger/stock',
    method: 'post',
    data: data
  })
}

// 修改库存查询
export function updateStock(data) {
  return request({
    url: '/cx-ledger/stock',
    method: 'put',
    data: data
  })
}

// 删除库存查询
export function delStock(sId) {
  return request({
    url: '/cx-ledger/stock/' + sId,
    method: 'delete'
  })
}
