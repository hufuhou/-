import request from '@/utils/request'

// 查询库存盘点列表
export function listKcinventory(query) {
  return request({
    url: '/kcinventory/kcinventory/list',
    method: 'get',
    params: query
  })
}

// 查询库存盘点详细
export function getKcinventory(isId) {
  return request({
    url: '/kcinventory/kcinventory/' + isId,
    method: 'get'
  })
}

// 新增库存盘点
export function addKcinventory(data) {
  return request({
    url: '/kcinventory/kcinventory',
    method: 'post',
    data: data
  })
}

// 修改库存盘点
export function updateKcinventory(data) {
  return request({
    url: '/kcinventory/kcinventory',
    method: 'put',
    data: data
  })
}

// 删除库存盘点
export function delKcinventory(isId) {
  return request({
    url: '/kcinventory/kcinventory/' + isId,
    method: 'delete'
  })
}

// 生成IsCode
export function genIsCode() {
  return request({
    url: '/kcinventory/kcinventory/genIsCode',
    method: 'get'
  })
}

// 获取w_id,w_name
export function findWareHouse() {
  return request({
    url: '/kcinventory/kcinventory/findWareHouse',
    method: 'get'
  })
}

// 获取user_id,user_name
export function findAllUser() {
  return request({
    url: '/kcinventory/kcinventory/findAllUser',
    method: 'get'
  })
}

// 获取货品id,货品name
export function findAllHpGoods() {
  return request({
    url: '/kcinventory/kcinventory/findAllHpGoods',
    method: 'get'
  })
}

// 获取IsId
export function findIsId() {
  return request({
    url: '/kcinventory/kcinventory/findIsId',
    method: 'get'
  })
}
