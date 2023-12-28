import request from '@/utils/request'

// 查询保质期查询列表
export function listShelflife(query) {
  return request({
    url: '/cx-ledger/shelflife/list',
    method: 'get',
    params: query
  })
}

// 查询保质期查询详细
export function getShelflife(gId) {
  return request({
    url: '/cx-ledger/shelflife/' + gId,
    method: 'get'
  })
}

// 新增保质期查询
export function addShelflife(data) {
  return request({
    url: '/cx-ledger/shelflife',
    method: 'post',
    data: data
  })
}

// 修改保质期查询
export function updateShelflife(data) {
  return request({
    url: '/cx-ledger/shelflife',
    method: 'put',
    data: data
  })
}

// 删除保质期查询
export function delShelflife(gId) {
  return request({
    url: '/cx-ledger/shelflife/' + gId,
    method: 'delete'
  })
}
