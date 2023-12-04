import request from '@/utils/request'

// 查询库存预警列表
export function listInformation(query) {
  return request({
    url: '/cx-ledger/information/list',
    method: 'get',
    params: query
  })
}

// 查询库存预警详细
export function getInformation(alertId) {
  return request({
    url: '/cx-ledger/information/' + alertId,
    method: 'get'
  })
}

// 新增库存预警
export function addInformation(data) {
  return request({
    url: '/cx-ledger/information',
    method: 'post',
    data: data
  })
}

// 修改库存预警
export function updateInformation(data) {
  return request({
    url: '/cx-ledger/information',
    method: 'put',
    data: data
  })
}

// 删除库存预警
export function delInformation(alertId) {
  return request({
    url: '/cx-ledger/information/' + alertId,
    method: 'delete'
  })
}
