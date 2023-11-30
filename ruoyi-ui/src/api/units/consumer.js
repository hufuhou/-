import request from '@/utils/request'

// 查询客户列表
export function listConsumer(query) {
  return request({
    url: '/units/consumer/list',
    method: 'get',
    params: query
  })
}

// 查询客户详细
export function getConsumer(cId) {
  return request({
    url: '/units/consumer/' + cId,
    method: 'get'
  })
}

// 新增客户
export function addConsumer(data, data2) {
  return request({
    url: '/units/consumer',
    method: 'post',
    data: {
      consumer: data,
      finance: data2
    }
  })
}

// 修改客户
export function updateConsumer(data,data2) {
  return request({
    url: '/units/consumer',
    method: 'put',
    data: {
      consumer: data,
      finance: data2
    }
  })
}

// 删除客户
export function delConsumer(cId) {
  return request({
    url: '/units/consumer/' + cId,
    method: 'delete'
  })
}

// 获取客户编号
export function getCode() {
  return request({
    url: '/units/consumer/getConsumerCode',
    method: 'get'
  })
}
