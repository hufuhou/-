import request from '@/utils/request'

// 查询货品信息列表
export function listGoods(query) {
  return request({
    url: '/cx-hpxx/goods/list',
    method: 'get',
    params: query
  })
}
// 查询货品类型列表
export function listType(query) {
  return request({
    url: '/cx-hpxx/type/list',
    method: 'get',
    params: query
  })
}
// 查询供应商列表列表
export function listSupplier(query) {
  return request({
    url: '/units/supplier/list',
    method: 'get',
    params: query
  })
}
// 查询货品信息详细
export function getGoods(gId) {
  return request({
    url: '/cx-hpxx/goods/' + gId,
    method: 'get'
  })
}

// 新增货品信息
export function addGoods(data) {
  return request({
    url: '/cx-hpxx/goods',
    method: 'post',
    data: data
  })
}

// 修改货品信息
export function updateGoods(data) {
  return request({
    url: '/cx-hpxx/goods',
    method: 'put',
    data: data
  })
}

// 删除货品信息
export function delGoods(gId) {
  return request({
    url: '/cx-hpxx/goods/' + gId,
    method: 'delete'
  })
}
