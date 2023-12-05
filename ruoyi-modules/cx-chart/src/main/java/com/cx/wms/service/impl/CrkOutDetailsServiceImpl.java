package com.cx.wms.service.impl;

import com.cx.wms.domain.CrkOutDetails;
import com.cx.wms.mapper.CrkOutDetailsMapper;
import com.cx.wms.service.ICrkOutDetailsService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出库明细
 * Service业务层处理
 *
 * @author XRC
 * @date 2023-11-20
 */
@Service
public class CrkOutDetailsServiceImpl implements ICrkOutDetailsService {
    @Autowired
    private CrkOutDetailsMapper crkOutDetailsMapper;

    /**
     * 查询出库明细
     *
     * @param omId 出库明细
     *             主键
     * @return 出库明细
     */
    @Override
    public CrkOutDetails selectCrkOutDetailsByOmId(Long omId) {
        return crkOutDetailsMapper.selectCrkOutDetailsByOmId(omId);
    }

    /**
     * 查询出库明细
     * 列表
     *
     * @param crkOutDetails 出库明细
     * @return 出库明细
     */
    @Override
    public List<CrkOutDetails> selectCrkOutDetailsList(CrkOutDetails crkOutDetails) {
        return crkOutDetailsMapper.selectCrkOutDetailsList(crkOutDetails);
    }

    /**
     * 查询出库明细带用户名
     * 列表
     *
     * @param crkOutDetails 出库明细
     * @return 出库明细
     * 集合
     */
    @Override
    public List<CrkOutDetails> selectAllWithUser(CrkOutDetails crkOutDetails) {
        return crkOutDetailsMapper.selectAllWithUser(crkOutDetails);
    }

    /**
     * 新增出库明细
     *
     * @param crkOutDetails 出库明细
     * @return 结果
     */
    @Override
    public int insertCrkOutDetails(CrkOutDetails crkOutDetails) {
        crkOutDetails.setCreateTime(DateUtils.getNowDate());
        crkOutDetails.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        return crkOutDetailsMapper.insertCrkOutDetails(crkOutDetails);
    }

    /**
     * 修改出库明细
     *
     * @param crkOutDetails 出库明细
     * @return 结果
     */
    @Override
    public int updateCrkOutDetails(CrkOutDetails crkOutDetails) {
        crkOutDetails.setUpdateTime(DateUtils.getNowDate());
        crkOutDetails.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        return crkOutDetailsMapper.updateCrkOutDetails(crkOutDetails);
    }

    /**
     * 批量删除出库明细
     *
     * @param omIds 需要删除的出库明细
     *              主键
     * @return 结果
     */
    @Override
    public int deleteCrkOutDetailsByOmIds(Long[] omIds) {
        return crkOutDetailsMapper.deleteCrkOutDetailsByOmIds(omIds);
    }

    /**
     * 删除出库明细
     * 信息
     *
     * @param omId 出库明细
     *             主键
     * @return 结果
     */
    @Override
    public int deleteCrkOutDetailsByOmId(Long omId) {
        return crkOutDetailsMapper.deleteCrkOutDetailsByOmId(omId);
    }

    /**
     * 查询出库订单数
     *
     * @return 未删除出库订单数
     */
    @Override
    public Integer findCrkOutNumber() {
        return crkOutDetailsMapper.findCrkOutNumber();
    }
}
