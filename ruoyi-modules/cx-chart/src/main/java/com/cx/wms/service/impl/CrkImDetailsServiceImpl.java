package com.cx.wms.service.impl;

import com.cx.wms.domain.CrkImDetails;
import com.cx.wms.mapper.CrkImDetailsMapper;
import com.cx.wms.service.ICrkImDetailsService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 入库明细Service业务层处理
 *
 * @author XRC
 * @date 2023-11-21
 */
@Service
public class CrkImDetailsServiceImpl implements ICrkImDetailsService {
    @Autowired
    private CrkImDetailsMapper crkImDetailsMapper;

    /**
     * 查询入库明细
     *
     * @param imdId 入库明细主键
     * @return 入库明细
     */
    @Override
    public CrkImDetails selectCrkImDetailsByImdId(Long imdId) {
        return crkImDetailsMapper.selectCrkImDetailsByImdId(imdId);
    }

    /**
     * 查询入库明细列表
     *
     * @param crkImDetails 入库明细
     * @return 入库明细
     */
    @Override
    public List<CrkImDetails> selectCrkImDetailsList(CrkImDetails crkImDetails) {
        return crkImDetailsMapper.selectCrkImDetailsList(crkImDetails);
    }

    /**
     * 查询入库明细列表带用户名
     *
     * @param crkImDetails 入库明细
     * @return 入库明细集合
     */
    @Override
    public List<CrkImDetails> findListWithUser(CrkImDetails crkImDetails) {
        return crkImDetailsMapper.findListWithUser(crkImDetails);
    }

    /**
     * 新增入库明细
     *
     * @param crkImDetails 入库明细
     * @return 结果
     */
    @Override
    public int insertCrkImDetails(CrkImDetails crkImDetails) {
        crkImDetails.setCreateTime(DateUtils.getNowDate());
        crkImDetails.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        return crkImDetailsMapper.insertCrkImDetails(crkImDetails);
    }

    /**
     * 修改入库明细
     *
     * @param crkImDetails 入库明细
     * @return 结果
     */
    @Override
    public int updateCrkImDetails(CrkImDetails crkImDetails) {
        crkImDetails.setUpdateTime(DateUtils.getNowDate());
        crkImDetails.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        return crkImDetailsMapper.updateCrkImDetails(crkImDetails);
    }

    /**
     * 批量删除入库明细
     *
     * @param imdIds 需要删除的入库明细主键
     * @return 结果
     */
    @Override
    public int deleteCrkImDetailsByImdIds(Long[] imdIds) {
        return crkImDetailsMapper.deleteCrkImDetailsByImdIds(imdIds);
    }

    /**
     * 删除入库明细信息
     *
     * @param imdId 入库明细主键
     * @return 结果
     */
    @Override
    public int deleteCrkImDetailsByImdId(Long imdId) {
        return crkImDetailsMapper.deleteCrkImDetailsByImdId(imdId);
    }

    /**
     * 入库明细订货总数
     *
     * @return 入库明细订货总数
     */
    @Override
    public Integer findCheckInNumber() {
        return crkImDetailsMapper.findCheckInNumber();
    }

    /**
     * @param TodayOrYesterday 今日/昨日
     * @param BeginDay         开始日
     * @param EndDay           结束日
     * @return 按时间段进货列表
     */
    @Override
    public List<CrkImDetails> findInfoByDate(String TodayOrYesterday, String BeginDay, String EndDay) {
        return crkImDetailsMapper.findInfoByDate(TodayOrYesterday, BeginDay, EndDay);
    }
}
