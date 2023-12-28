import request from '@/utils/request'

// 查询库存调拨列表
export function listTransfer(query) {
  return request({
    url: '/cx-crkgl/transfer/list',
    method: 'get',
    params: query
  })
}

// 查询库存调拨详细
export function getTransfer(tId) {
  return request({
    url: '/cx-crkgl/transfer/' + tId,
    method: 'get'
  })
}

// 新增库存调拨
export function addTransfer(data) {
  return request({
    url: '/cx-crkgl/transfer',
    method: 'post',
    data: data
  })
}

// 修改库存调拨
export function updateTransfer(data) {
  return request({
    url: '/cx-crkgl/transfer',
    method: 'put',
    data: data
  })
}

// 删除库存调拨
export function delTransfer(tId) {
  return request({
    url: '/cx-crkgl/transfer/' + tId,
    method: 'delete'
  })
}
//修改计划数量
export function updateNumberofPlans(wId, goodsId, tQuantity) {
  return request({
    url: '/cx-crkgl/transfer/' + wId, goodsId, tQuantity,
    method: 'get'
  })
}
//获取调拨编码
export function getTdCode() {
  return request({
    url: '/cx-crkgl/transfer/getTranCode',
    method: 'get'
  })
}
