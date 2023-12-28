import request from "@/utils/request";

// 查询入库明细列表
export function listCheckin(query) {
  return request({
    url: "/checkin/checkin/list",
    method: "get",
    params: query,
  });
}

// 查询入库明细列表带用户
export function listCheckinWithUser(query) {
  return request({
    url: "/checkin/checkin/listWithUser",
    method: "get",
    params: query,
  });
}

// 查询入库明细详细
export function getCheckin(imdId) {
  return request({
    url: "/checkin/checkin/" + imdId,
    method: "get",
  });
}

// 新增入库明细
export function addCheckin(data) {
  return request({
    url: "/checkin/checkin",
    method: "post",
    data: data,
  });
}

// 修改入库明细
export function updateCheckin(data) {
  return request({
    url: "/checkin/checkin",
    method: "put",
    data: data,
  });
}

// 删除入库明细
export function delCheckin(imdId) {
  return request({
    url: "/checkin/checkin/" + imdId,
    method: "delete",
  });
}

// 查询入库明细总数
export function findCheckNmu() {
  return request({
    url: "/checkin/checkin/count",
    method: "get",
  });
}

// 获取查询日期参数
export function selectDataParam(param) {
  return request({
    url: "/opdm/opdm/dateParam?param=" + param,
    method: "get",
  });
}

// 按照时间段查询进货信息
export function findInfoByDateCheckin(DateParams) {
  // 将数组拆分为三个独立的参数
  const [TodayOrYesterday, BeginDay, EndDay] = DateParams;
  return request({
    url: "/checkin/checkin/findInfoByDate",
    method: "get",
    params: {
      TodayOrYesterday,
      BeginDay,
      EndDay,
    },
  });
}

// 查询库存货品数总金额
export function findCountValue(wId = null) {
  // 如果 wId 为 null 或 undefined，则不包含在请求中
  const url = wId? `/checkin/checkin/findCountValue?wId=${wId}`: "/checkin/checkin/findCountValue";
  // 发起网络请求
  return request({
    url,
    method: "get",
  });
}
