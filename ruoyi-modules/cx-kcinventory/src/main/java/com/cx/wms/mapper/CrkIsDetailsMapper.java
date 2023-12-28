package com.cx.wms.mapper;

import com.cx.wms.domain.CrkIsDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 盘点明细Mapper接口
 *
 * @author xrc
 * @date 2023-12-14
 */
@Mapper
public interface CrkIsDetailsMapper {
    /**
     * 查询盘点明细
     *
     * @param isdId 盘点明细主键
     * @return 盘点明细
     */
    public CrkIsDetails selectCrkIsDetailsByIsdId(Long isdId);

    /**
     * 查询盘点明细列表
     *
     * @param crkIsDetails 盘点明细
     * @return 盘点明细集合
     */
    public List<CrkIsDetails> selectCrkIsDetailsList(CrkIsDetails crkIsDetails);

    /**
     * 新增盘点明细
     *
     * @param crkIsDetails 盘点明细
     * @return 结果
     */
    public int insertCrkIsDetails(CrkIsDetails crkIsDetails);

    /**
     * 修改盘点明细
     *
     * @param crkIsDetails 盘点明细
     * @return 结果
     */
    public int updateCrkIsDetails(CrkIsDetails crkIsDetails);

    /**
     * 删除盘点明细
     *
     * @param isdId 盘点明细主键
     * @return 结果
     */
    public int deleteCrkIsDetailsByIsdId(Long isdId);

    /**
     * 批量删除盘点明细
     *
     * @param isdIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrkIsDetailsByIsdIds(Long[] isdIds);

    /*
      TODO :{
       明细表根据主表选择的仓库位置与明细表中选择的仓位查询该仓库仓位中货品信息,然后再选择货品来盘点
       盘点主表审核流程基本完成,应该不需要做什么改动了 未知状态...只要不瞎动数据库应该不会搞出
       什么乱子来
       主表审核 : 审核通过 已完成单 不需要再审核了  已驳回 待审核单 需要审核
       审核通过的主表展示盘点主要的数据 : 盘点哪个仓库, 盘点开始-结束时间, 经办人,盘点类型
       出入库状态看看什么时候放进盘点明细里面负责
       审核通过单应该默认盘点结果为 暂未盘点 的 结果也要与明细表一致
       (库存表有字段 number_plans: 计划数量  item_quantity:货品数量) 可以试试从这入手计算
       下拉框只显示key值不显示文字label
       }
    */
}
