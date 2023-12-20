import request from '@/utils/request'

// 查询出库管理列表
export function listOutbound(query) {
  return request({
    url: '/cx-crkgl/outbound/list',
    method: 'get',
    params: query
  })
}

// 查询出库管理详细
export function getOutbound(outId) {
  return request({
    url: '/cx-crkgl/outbound/' + outId,
    method: 'get'
  })
}

// 新增出库管理
export function addOutbound(data) {
  return request({
    url: '/cx-crkgl/outbound',
    method: 'post',
    data: data
  })
}

// 修改出库管理
export function updateOutbound(data) {
  return request({
    url: '/cx-crkgl/outbound',
    method: 'put',
    data: data
  })
}

// 删除出库管理
export function delOutbound(outId) {
  return request({
    url: '/cx-crkgl/outbound/' + outId,
    method: 'delete'
  })
}
