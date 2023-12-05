package com.cx.wms.service;

import com.cx.wms.domain.CrkImDetails;

import java.util.List;

/**
 * 入库明细Service接口
 *
 * @author XRC
 * @date 2023-11-21
 */
public interface ICrkImDetailsService {
    /**
     * 查询入库明细
     *
     * @param imdId 入库明细主键
     * @return 入库明细
     */
    public CrkImDetails selectCrkImDetailsByImdId(Long imdId);

    /**
     * 查询入库明细列表
     *
     * @param crkImDetails 入库明细
     * @return 入库明细集合
     */
    public List<CrkImDetails> selectCrkImDetailsList(CrkImDetails crkImDetails);

    /**
     * 查询入库明细列表带用户名
     *
     * @param crkImDetails 入库明细
     * @return 入库明细集合
     */
    public List<CrkImDetails> findListWithUser(CrkImDetails crkImDetails);

    /**
     * 新增入库明细
     *
     * @param crkImDetails 入库明细
     * @return 结果
     */
    public int insertCrkImDetails(CrkImDetails crkImDetails);

    /**
     * 修改入库明细
     *
     * @param crkImDetails 入库明细
     * @return 结果
     */
    public int updateCrkImDetails(CrkImDetails crkImDetails);

    /**
     * 批量删除入库明细
     *
     * @param imdIds 需要删除的入库明细主键集合
     * @return 结果
     */
    public int deleteCrkImDetailsByImdIds(Long[] imdIds);

    /**
     * 删除入库明细信息
     *
     * @param imdId 入库明细主键
     * @return 结果
     */
    public int deleteCrkImDetailsByImdId(Long imdId);


    /**
     * 入库明细订货总数
     * @return 入库明细订货总数
     */
    public Integer findCheckInNumber();
}
