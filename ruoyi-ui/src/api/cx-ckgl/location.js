import request from '@/utils/request'

// 查询仓库详情列表
export function listLocation(query) {
  return request({
    url: '/cx-ckgl/location/list',
    method: 'get',
    params: query
  })
}
// 查询用户列表
export function listUser(query) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  })
}
// 查询仓库管理列表
export function listWarehouse(query) {
  return request({
    url: '/cx-ckgl/warehouse/list',
    method: 'get',
    params: query
  })
}
// 查询部门列表
export function listDept(query) {
  return request({
    url: '/system/dept/list',
    method: 'get',
    params: query
  })
}
// 查询仓库详情详细
export function getLocation(slId) {
  return request({
    url: '/cx-ckgl/location/' + slId,
    method: 'get'
  })
}
//根据仓库ID查询仓位
export function getLocas(wId) {
  return request({
    url: '/cx-ckgl/location/getLocas/' + wId,
    method: 'get'
  })
}
// 新增仓库详情
export function addLocation(data) {
  return request({
    url: '/cx-ckgl/location',
    method: 'post',
    data: data
  })
}


// 修改仓库详情
export function updateLocation(data) {
  return request({
    url: '/cx-ckgl/location',
    method: 'put',
    data: data
  })
}

// 删除仓库详情
export function delLocation(slId) {
  return request({
    url: '/cx-ckgl/location/' + slId,
    method: 'delete'
  })
}
