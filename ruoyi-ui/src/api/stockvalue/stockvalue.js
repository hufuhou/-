import request from '@/utils/request'


// 查询货品信息列表
export function listStockValue() {
  return request({
    url: '/opdm/opdm/allInfo',
    method: 'get',
  })
}
