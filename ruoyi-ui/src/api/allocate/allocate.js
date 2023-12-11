import request from '@/utils/request'

// 查询调拨明细列表
export function listAllocate(query) {
  return request({
    url: '/allocate/allocate/list',
    method: 'get',
    params: query
  })
}

// 查询调拨明细详细
export function getAllocate(tId) {
  return request({
    url: '/allocate/allocate/' + tId,
    method: 'get'
  })
}

// 新增调拨明细
export function addAllocate(data) {
  return request({
    url: '/allocate/allocate',
    method: 'post',
    data: data
  })
}

// 修改调拨明细
export function updateAllocate(data) {
  return request({
    url: '/allocate/allocate',
    method: 'put',
    data: data
  })
}

// 删除调拨明细
export function delAllocate(tId) {
  return request({
    url: '/allocate/allocate/' + tId,
    method: 'delete'
  })
}

// 获取查询日期参数
export function selectDataParam(param) {
  return request({
    url: '/opdm/opdm/dateParam?param='+ param,
    method: 'get'
  })
}
