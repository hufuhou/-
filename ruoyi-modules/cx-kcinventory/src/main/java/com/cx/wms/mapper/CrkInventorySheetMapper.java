package com.cx.wms.mapper;

import com.cx.wms.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 库存盘点Mapper接口
 *
 * @author xrc
 * @date 2023-12-14
 */
@Mapper
public interface CrkInventorySheetMapper {
    /**
     * 查询库存盘点
     *
     * @param isId 库存盘点主键
     * @return 库存盘点
     */
    public CrkInventorySheet selectCrkInventorySheetByIsId(Long isId);

    /**
     * 查询库存盘点列表
     *
     * @param crkInventorySheet 库存盘点
     * @return 库存盘点集合
     */
    public List<CrkInventorySheet> selectCrkInventorySheetList(CrkInventorySheet crkInventorySheet);

    /**
     * 新增库存盘点
     *
     * @param crkInventorySheet 库存盘点
     * @return 结果
     */
    public int insertCrkInventorySheet(CrkInventorySheet crkInventorySheet);

    /**
     * 修改库存盘点
     *
     * @param crkInventorySheet 库存盘点
     * @return 结果
     */
    public int updateCrkInventorySheet(CrkInventorySheet crkInventorySheet);

    /**
     * 删除库存盘点
     *
     * @param isId 库存盘点主键
     * @return 结果
     */
    public int deleteCrkInventorySheetByIsId(Long isId);

    /**
     * 批量删除库存盘点
     *
     * @param isIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrkInventorySheetByIsIds(Long[] isIds);

    /**
     * 批量删除盘点明细
     *
     * @param isIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrkIsDetailsByIsIds(Long[] isIds);

    /**
     * 批量新增盘点明细
     *
     * @param crkIsDetailsList 盘点明细列表
     * @return 结果
     */
    public int batchCrkIsDetails(List<CrkIsDetails> crkIsDetailsList);


    /**
     * 通过库存盘点主键删除盘点明细信息
     *
     * @param isId 库存盘点ID
     * @return 结果
     */
    public int deleteCrkIsDetailsByIsId(Long isId);


    /**
     * 查询最新的is_code
     * @return 最新的is_code
     */
    public String findIsCode();
    public String findIsId();

    /**
     * 查询仓库name 仓库id
     * @return 仓库name 仓库id
     */
    public List<WareHouse> findWareHouse();

    public List<User> findAllUser();

    public List<HpGood> findAllHpGoods();

}
