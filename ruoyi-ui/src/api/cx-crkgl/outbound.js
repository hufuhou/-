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
// 查询销售明细
export function getOutDetail(query) {
  return request({
    url: '/cx-crkgl/outbound/mx/',
    method: 'get',
    params: query
  })
}
// 查询入库数量
export function getKcsl(slId,gId) {
  return request({
    url: '/cx-crkgl/outbound/kcsl/' + slId+'/'+gId,
    method: 'get'
  })
}
// 查询出库明细
export function listOutDetails(outId) {
  return request({
    url: '/cx-crkgl/outbound/hp/'+outId,
    method: 'get',
  })
}
// 审核出库
export function AuditOutbound(outCode,outIds, isApproved) {
  const reviewStatus = isApproved ? 'true' : 'false';
  return request({
    url: '/cx-crkgl/outbound/sh/',
    method: 'post',
    data: {
      outCode:outCode,
      outIds:outIds,
      reviewStatus: reviewStatus
    }
  });
}
// 撤销出库
export function RevokeOutbound(outIds) {
  return request({
    url: '/cx-crkgl/outbound/zx/',
    method: 'post',
    data:{
      outIds:outIds
    }
  });
}
