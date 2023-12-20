import request from '@/utils/request'

// 查询入库管理列表
export function listManagement(query) {
  return request({
    url: '/cx-crkgl/management/list',
    method: 'get',
    params: query
  })
}

// 查询入库管理列表1
export function listManagement1(inCodes) {
  return request({
    url: '/cx-crkgl/management/list1/'+inCodes,
    method: 'get',
  });
}

// 查询入库明细
export function listDetails(inId) {
  return request({
    url: '/cx-crkgl/management/hp/'+inId,
    method: 'get',
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
// 查询用户列表
export function listUser(query) {
  return request({
    url: '/system/user/list',
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
// 查询进货订单列表
export function listPurchase(query) {
  return request({
    url: '/order/purchase/list',
    method: 'get',
    params: query
  })
}

// 查询入库管理详细
export function getManagement(inId) {
  return request({
    url: '/cx-crkgl/management/' + inId,
    method: 'get'
  })
}

// 查询入库数量
export function getRksl(gCode) {
  return request({
    url: '/cx-crkgl/management/rksl/' + gCode,
    method: 'get'
  })
}

// 查询进货明细
export function getInventoryDetail(query) {
  return request({
    url: '/cx-crkgl/management/mx/',
    method: 'get',
    params: query
  })
}

// 新增入库管理
export function addManagement(data) {
  return request({
    url: '/cx-crkgl/management',
    method: 'post',
    data: data
  })
}

// 修改入库管理
export function updateManagement(data) {
  return request({
    url: '/cx-crkgl/management',
    method: 'put',
    data: data
  })
}

// 删除入库管理
export function delManagement(inId) {
  return request({
    url: '/cx-crkgl/management/' + inId,
    method: 'delete'
  })
}
// 审核入库
export function InventoryReview(inCode,inIds, isApproved) {
  const reviewStatus = isApproved ? 'true' : 'false';
  return request({
    url: '/cx-crkgl/management/sh/',
    method: 'post',
    data: {
      inCode:inCode,
      inIds:inIds,
      reviewStatus: reviewStatus
    }
  });
}
// 撤销入库
export function WithdrawalStorage(inIds) {
  return request({
    url: '/cx-crkgl/management/zx/',
    method: 'post',
    data:{
      inIds:inIds
    }
  });
}
// 查询货品信息列表
export function listGoods(query) {
  return request({
    url: '/cx-hpxx/goods/list',
    method: 'get',
    params: query
  })
}
