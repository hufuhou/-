package com.cx.wms.service.impl;

import com.cx.wms.domain.*;
import com.cx.wms.mapper.CrkInventorySheetMapper;
import com.cx.wms.service.ICrkInventorySheetService;
import com.cx.wms.utils.NumberGenerator;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 库存盘点Service业务层处理
 *
 * @author xrc
 * @date 2023-12-14
 */
@Service
@Transactional
public class CrkInventorySheetServiceImpl implements ICrkInventorySheetService {
    @Autowired
    private CrkInventorySheetMapper crkInventorySheetMapper;

    //private String UpdateBy = String.valueOf(SecurityUtils.getUserId());

    //private String CreateBy = String.valueOf(SecurityUtils.getUserId());

    private static String code = null;

    /**
     * 查询库存盘点
     *
     * @param isId 库存盘点主键
     * @return 库存盘点
     */
    @Override
    public CrkInventorySheet selectCrkInventorySheetByIsId(Long isId) {
        return crkInventorySheetMapper.selectCrkInventorySheetByIsId(isId);
    }

    /**
     * 查询库存盘点列表
     *
     * @param crkInventorySheet 库存盘点
     * @return 库存盘点
     */
    @Override
    public List<CrkInventorySheet> selectCrkInventorySheetList(CrkInventorySheet crkInventorySheet) {
        return crkInventorySheetMapper.selectCrkInventorySheetList(crkInventorySheet);
    }

    /**
     * 新增库存盘点
     *
     * @param crkInventorySheet 库存盘点
     * @return 结果
     */
    @Transactional
    @Override
    public int insertCrkInventorySheet(CrkInventorySheet crkInventorySheet) {
        crkInventorySheet.setCreateTime(DateUtils.getNowDate());
        crkInventorySheet.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        int rows = crkInventorySheetMapper.insertCrkInventorySheet(crkInventorySheet);
        insertCrkIsDetails(crkInventorySheet);
        return rows;
    }

    /**
     * 修改库存盘点
     *
     * @param crkInventorySheet 库存盘点
     * @return 结果
     */
    @Transactional
    @Override
    public int updateCrkInventorySheet(CrkInventorySheet crkInventorySheet) {
        crkInventorySheet.setUpdateTime(DateUtils.getNowDate());
        crkInventorySheet.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        crkInventorySheetMapper.deleteCrkIsDetailsByIsId(crkInventorySheet.getIsId());
        insertCrkIsDetails(crkInventorySheet);
        return crkInventorySheetMapper.updateCrkInventorySheet(crkInventorySheet);
    }

    /**
     * 批量删除库存盘点
     *
     * @param isIds 需要删除的库存盘点主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteCrkInventorySheetByIsIds(Long[] isIds) {
        crkInventorySheetMapper.deleteCrkIsDetailsByIsIds(isIds);
        return crkInventorySheetMapper.deleteCrkInventorySheetByIsIds(isIds);
    }

    /**
     * 删除库存盘点信息
     *
     * @param isId 库存盘点主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteCrkInventorySheetByIsId(Long isId) {
        crkInventorySheetMapper.deleteCrkIsDetailsByIsId(isId);
        return crkInventorySheetMapper.deleteCrkInventorySheetByIsId(isId);
    }

    /**
     * 查询最新的is_code
     *
     * @return 最新的is_code
     */
    @Override
    public String findIsCode() {
        code = crkInventorySheetMapper.findIsCode();
        System.out.println(genIsCode());
        return code;
    }

    @Override
    public String findIsId() {
        return crkInventorySheetMapper.findIsId();
    }

    /**
     * 新增盘点明细信息
     *
     * @param crkInventorySheet 库存盘点对象
     */
    public void insertCrkIsDetails(CrkInventorySheet crkInventorySheet) {
        List<CrkIsDetails> crkIsDetailsList = crkInventorySheet.getCrkIsDetailsList();
        Long isId = crkInventorySheet.getIsId();
        if (StringUtils.isNotNull(crkIsDetailsList)) {
            List<CrkIsDetails> list = new ArrayList<CrkIsDetails>();
            for (CrkIsDetails crkIsDetails : crkIsDetailsList) {
                crkIsDetails.setIsId(isId);
                crkIsDetails.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
                crkIsDetails.setCreateTime(DateUtils.getNowDate());
                crkIsDetails.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
                crkIsDetails.setUpdateTime(DateUtils.getNowDate());
                list.add(crkIsDetails);
            }
            if (list.size() > 0) {
                crkInventorySheetMapper.batchCrkIsDetails(list);
            }
        }
    }

    public String genIsCode() {
        code = crkInventorySheetMapper.findIsCode();
        NumberGenerator numberGenerator = new NumberGenerator("CKPD");
        return numberGenerator.generateNumber(code);
    }

    /**
     * 查询仓库name 仓库id
     *
     * @return 仓库name 仓库id
     */
    @Override
    public List<WareHouse> findWareHouse() {
        return crkInventorySheetMapper.findWareHouse();
    }

    @Override
    public List<User> findAllUser() {
        return crkInventorySheetMapper.findAllUser();
    }

    @Override
    public List<HpGood> findAllHpGoods() {
        return crkInventorySheetMapper.findAllHpGoods();
    }

    //查询盘点单状态
    @Override
    public Integer findSheetStatus(Integer isId) {
        return crkInventorySheetMapper.findSheetStatus(isId);
    }

    //更新盘点单状态
    @Override
    public Integer updateSheetStatus(@Param("isId")Integer isId, @Param("stateCode")Integer stateCode) {
        return crkInventorySheetMapper.updateSheetStatus(isId,stateCode);
    }
}
