import request from '@/utils/request'

// 查询入库明细列表
export function listCheckin(query) {
  return request({
    url: '/checkin/checkin/list',
    method: 'get',
    params: query
  })
}

// 查询入库明细列表带用户
export function listCheckinWithUser(query) {
  return request({
    url: '/checkin/checkin/listWithUser',
    method: 'get',
    params: query
  })
}

// 查询入库明细详细
export function getCheckin(imdId) {
  return request({
    url: '/checkin/checkin/' + imdId,
    method: 'get'
  })
}

// 新增入库明细
export function addCheckin(data) {
  return request({
    url: '/checkin/checkin',
    method: 'post',
    data: data
  })
}

// 修改入库明细
export function updateCheckin(data) {
  return request({
    url: '/checkin/checkin',
    method: 'put',
    data: data
  })
}

// 删除入库明细
export function delCheckin(imdId) {
  return request({
    url: '/checkin/checkin/' + imdId,
    method: 'delete'
  })
}

// 查询入库明细总数
export function findCheckNmu() {
  return request({
    url: '/checkin/checkin/count',
    method: 'get'
  })
}
