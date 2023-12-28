package com.cx.crkgl.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.cx.crkgl.domain.CrkTransferDetails;
import com.cx.crkgl.mapper.CrkTransferMapper;
import com.cx.crkgl.domain.CrkTransfer;
import com.cx.crkgl.service.ICrkTransferService;

/**
 * 库存调拨Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-13
 */
@Service
public class CrkTransferServiceImpl implements ICrkTransferService 
{
    @Autowired
    private CrkTransferMapper crkTransferMapper;

    /**
     * 查询库存调拨
     *
     * @param tId 库存调拨主键
     * @return 库存调拨
     */
    @Override
    public CrkTransfer selectCrkTransferByTId(Long tId)
    {
        CrkTransfer crkTransfer = crkTransferMapper.selectCrkTransferByTId(tId);
//        System.out.println(crkTransfer);
        return crkTransfer;
    }

    /**
     * 查询库存调拨列表
     *
     * @param crkTransfer 库存调拨
     * @return 库存调拨
     */
    @Override
    public List<CrkTransfer> selectCrkTransferList(CrkTransfer crkTransfer)
    {
        return crkTransferMapper.selectCrkTransferList(crkTransfer);
    }

    /**
     * 新增库存调拨
     *
     * @param crkTransfer 库存调拨
     * @return 结果
     */
    @Transactional
    @Override
    public int insertCrkTransfer(CrkTransfer crkTransfer)
    {
        crkTransfer.setCreateTime(DateUtils.getNowDate());
        int rows = crkTransferMapper.insertCrkTransfer(crkTransfer);
        insertCrkTransferDetails(crkTransfer);
        return rows;
    }

    /**
     * 修改库存调拨
     *
     * @param crkTransfer 库存调拨
     * @return 结果
     */
    @Transactional
    @Override
    public int updateCrkTransfer(CrkTransfer crkTransfer)
    {
        crkTransfer.setUpdateTime(DateUtils.getNowDate());
        crkTransferMapper.deleteCrkTransferDetailsByTId(crkTransfer.gettId());
        insertCrkTransferDetails(crkTransfer);
        return crkTransferMapper.updateCrkTransfer(crkTransfer);
    }

    /**
     * 批量删除库存调拨
     *
     * @param tIds 需要删除的库存调拨主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteCrkTransferByTIds(Long[] tIds)
    {
        crkTransferMapper.deleteCrkTransferDetailsByTIds(tIds);
        return crkTransferMapper.deleteCrkTransferByTIds(tIds);
    }

    /**
     * 删除库存调拨信息
     *
     * @param tId 库存调拨主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteCrkTransferByTId(Long tId)
    {
        crkTransferMapper.deleteCrkTransferDetailsByTId(tId);
        return crkTransferMapper.deleteCrkTransferByTId(tId);
    }

    /**
     * 新增调拨明细信息
     *
     * @param crkTransfer 库存调拨对象
     */
    public void insertCrkTransferDetails(CrkTransfer crkTransfer)
    {
        List<CrkTransferDetails> crkTransferDetailsList = crkTransfer.getCrkTransferDetailsList();
        Long tId = crkTransfer.gettId();
        if (StringUtils.isNotNull(crkTransferDetailsList))
        {
            List<CrkTransferDetails> list = new ArrayList<CrkTransferDetails>();
            for (CrkTransferDetails crkTransferDetails : crkTransferDetailsList)
            {
                crkTransferDetails.settId(tId);
                crkTransferDetails.setTdCode(crkTransfer.getTdCode());
                crkTransferDetails.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
                list.add(crkTransferDetails);
            }
            if (list.size() > 0)
            {
                crkTransferMapper.batchCrkTransferDetails(list);
            }
        }
    }

    /**
     * 生成调拨编码
     */
    @Override
    public CrkTransfer getTranCode() {
        //前缀（库存调拨）
        String prefix = "KCDB";
        CrkTransfer crkTransfer =crkTransferMapper.getTranCode();
//        System.out.println(crkTransfer);
        String code = crkTransfer.getTdCode();
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        // 从字符串中提取数字
        Pattern pattern = Pattern.compile("\\d{3}$");
        Matcher matcher = pattern.matcher(code);
        String lastThreeDigits = null;
        if (matcher.find()) {
            lastThreeDigits = matcher.group();
        }
        // 将提取的数字转换为整数并加1
        int incrementedNumber = Integer.parseInt(lastThreeDigits) + 1;
        // 格式化为与原数字相同长度的字符串
        String formattedIncrementedNumber = String.format("%0" + lastThreeDigits.length() + "d", incrementedNumber);

        // 输出结果
        String resultString = prefix + timestamp + formattedIncrementedNumber;
//        System.out.println(resultString);

        crkTransfer.setTdCode(resultString);

        return crkTransfer;
    }

    @Override
    public int updateNumberofPlans(Long wId, Long goodsId, Long tQuantity) {
        return crkTransferMapper.updateNumberofPlans(wId, goodsId, tQuantity);
    }


}
