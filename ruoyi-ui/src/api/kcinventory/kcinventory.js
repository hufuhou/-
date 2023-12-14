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
