import request from '@/utils/request'

// 查询进货明细列表
export function listDetails(query) {
  return request({
    url: '/order/details/list',
    method: 'get',
    params: query
  })
}

// 查询进货明细详细
export function getDetails(pdId) {
  return request({
    url: '/order/details/' + pdId,
    method: 'get'
  })
}

// 新增进货明细
export function addDetails(data) {
  return request({
    url: '/order/details',
    method: 'post',
    data: data
  })
}

// 修改进货明细
export function updateDetails(data) {
  return request({
    url: '/order/details',
    method: 'put',
    data: data
  })
}

// 删除进货明细
export function delDetails(pdId) {
  return request({
    url: '/order/details/' + pdId,
    method: 'delete'
  })
}

//查询进货明细
export function detailAndProduct(code,oldCode){
  return request({
    url: '/order/details/detailAndProduct',
    method: 'get',
    params: {
      poCode: code,
      oldPoCode: oldCode
    }
  })
}
