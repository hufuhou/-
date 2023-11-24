import request from '@/utils/request'

// 查询货品类型列表
export function listType(query) {
  return request({
    url: '/cx-hpxx/type/list',
    method: 'get',
    params: query
  })
}

// 查询货品类型详细
export function getType(gtId) {
  return request({
    url: '/cx-hpxx/type/' + gtId,
    method: 'get'
  })
}

// 新增货品类型
export function addType(data) {
  return request({
    url: '/cx-hpxx/type',
    method: 'post',
    data: data
  })
}

// 修改货品类型
export function updateType(data) {
  return request({
    url: '/cx-hpxx/type',
    method: 'put',
    data: data
  })
}

// 删除货品类型
export function delType(gtId) {
  return request({
    url: '/cx-hpxx/type/' + gtId,
    method: 'delete'
  })
}
