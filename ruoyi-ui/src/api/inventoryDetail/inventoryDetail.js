import request from '@/utils/request'

// 查询盘点明细列表
export function listInventoryDetail(query) {
  return request({
    url: '/cx-kcinventory/inventoryDetail/list',
    method: 'get',
    params: query
  })
}

// 查询盘点明细详细
export function getInventoryDetail(isdId) {
  return request({
    url: '/cx-kcinventory/inventoryDetail/' + isdId,
    method: 'get'
  })
}

// 新增盘点明细
export function addInventoryDetail(data) {
  return request({
    url: '/cx-kcinventory/inventoryDetail',
    method: 'post',
    data: data
  })
}

// 修改盘点明细
export function updateInventoryDetail(data) {
  return request({
    url: '/cx-kcinventory/inventoryDetail',
    method: 'put',
    data: data
  })
}

// 删除盘点明细
export function delInventoryDetail(isdId) {
  return request({
    url: '/cx-kcinventory/inventoryDetail/' + isdId,
    method: 'delete'
  })
}
