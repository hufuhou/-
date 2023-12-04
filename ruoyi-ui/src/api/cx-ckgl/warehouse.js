import request from '@/utils/request'

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
// 查询用户列表
export function listUser(query) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  })
}
// 查询仓库详情列表
export function listLocation(query) {
  return request({
    url: '/cx-ckgl/location/list',
    method: 'get',
    params: query
  })
}
// 查询仓库管理详细
export function getWarehouse(wId) {
  return request({
    url: '/cx-ckgl/warehouse/' + wId,
    method: 'get'
  })
}

// 新增仓库管理
export function addWarehouse(data) {
  return request({
    url: '/cx-ckgl/warehouse',
    method: 'post',
    data: data
  })
}

// 修改仓库管理
export function updateWarehouse(data) {
  return request({
    url: '/cx-ckgl/warehouse',
    method: 'put',
    data: data
  })
}

// 删除仓库管理
export function delWarehouse(wId) {
  return request({
    url: '/cx-ckgl/warehouse/' + wId,
    method: 'delete'
  })

}
// 启用仓库
export function enableWarehouse(wIds) {
  return request({
    url: '/cx-ckgl/warehouse/qy/' + wIds,
    method: 'get'
  })
}
// 停用仓库
  export function deactivateWarehouse(wIds) {
    return request({
      url: '/cx-ckgl/warehouse/ty/' + wIds,
      method: 'get'
    })
}
// 锁定仓库
export function lockWarehouse(wIds) {
  return request({
    url: '/cx-ckgl/warehouse/sd/' + wIds,
    method: 'get'
  })
}
