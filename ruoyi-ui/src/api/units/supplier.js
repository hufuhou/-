import request from '@/utils/request'

// 查询供应商列表列表
export function listSupplier(query) {
  return request({
    url: '/units/supplier/list',
    method: 'get',
    params: query
  })
}

// 查询供应商列表详细
export function getSupplier(sId) {
  return request({
    url: '/units/supplier/' + sId,
    method: 'get'
  })
}

// 新增供应商列表
export function addSupplier(data) {
  return request({
    url: '/units/supplier',
    method: 'post',
    data: data
  })
}

// 修改供应商列表
export function updateSupplier(data) {
  return request({
    url: '/units/supplier',
    method: 'put',
    data: data
  })
}

// 修改财务信息
export function updateFinance(data) {
  return request({
    url: '/units/supplier/updateFinance',
    method: 'put',
    data: data
  })
}

// 删除供应商列表
export function delSupplier(sId, sCode) {
  return request({
    url: '/units/supplier/' + sId + '/' + sCode,
    method: 'delete'
  })
}

// 获取供应商编号
export function getCode() {
  return request({
    url: '/units/supplier/getSupplierCode',
    method: 'get'
  })
}

// 新增财务信息
export function addFinance(data) {
  return request({
    url: '/units/supplier/addFinance',
    method: 'post',
    data: data
  })
}
