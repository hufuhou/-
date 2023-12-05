import request from '@/utils/request'

// 查询进货明细列表
export function listOpdm(query) {
  return request({
    url: '/opdm/opdm/list',
    method: 'get',
    params: query
  })
}

// 查询进货明细详细
export function getOpdm(pdId) {
  return request({
    url: '/opdm/opdm/' + pdId,
    method: 'get'
  })
}

// 新增进货明细
export function addOpdm(data) {
  return request({
    url: '/opdm/opdm',
    method: 'post',
    data: data
  })
}

// 修改进货明细
export function updateOpdm(data) {
  return request({
    url: '/opdm/opdm',
    method: 'put',
    data: data
  })
}

// 删除进货明细
export function delOpdm(pdId) {
  return request({
    url: '/opdm/opdm/' + pdId,
    method: 'delete'
  })
}

// 查询进货信息数
export function selectNum() {
  return request({
    url: '/opdm/opdm/count',
    method: 'get'
  })
}

// 查询进货信息明细列表带用户名商品名
export function selectListUG() {
  return request({
    url: '/opdm/opdm/listUG',
    method: 'get'
  })
}
