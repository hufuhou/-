package com.cx.order.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cx.order.domain.OrderPurchaseDetails;
import com.cx.order.mapper.OrderPurchaseDetailsMapper;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.order.mapper.OrderPurchaseMapper;
import com.cx.order.domain.OrderPurchase;
import com.cx.order.service.IOrderPurchaseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 进货订单Service业务层处理
 *
 * @author LZA
 * @date 2023-11-20
 */
@Service
public class OrderPurchaseServiceImpl implements IOrderPurchaseService {
    @Autowired
    private OrderPurchaseMapper orderPurchaseMapper;

    @Autowired
    private OrderPurchaseDetailsMapper orderPurchaseDetailsMapper;

    /**
     * 查询进货订单
     *
     * @param poId 进货订单主键
     * @return 进货订单
     */
    @Override
    public OrderPurchase selectOrderPurchaseByPoId(Long poId) {
        return orderPurchaseMapper.selectOrderPurchaseByPoId(poId);
    }

    /**
     * 查询进货订单列表
     *
     * @param orderPurchase 进货订单
     * @return 进货订单
     */
    @Override
    public List<OrderPurchase> selectOrderPurchaseList(OrderPurchase orderPurchase) {
        return orderPurchaseMapper.selectOrderPurchaseList(orderPurchase);
    }

    /**
     * 新增进货订单
     *
     * @param orderPurchase 进货订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertOrderPurchase(OrderPurchase orderPurchase, List<OrderPurchaseDetails> orderPurchaseDetails) {
        orderPurchase.setCreateTime(DateUtils.getNowDate());
        orderPurchase.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        if (StringUtils.isNotNull(orderPurchaseDetails)) {
            System.out.println(orderPurchaseDetails);
            List<OrderPurchaseDetails> list = new ArrayList<>();
            for (OrderPurchaseDetails details : orderPurchaseDetails) {
                details.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
                details.setCreateTime(DateUtils.getNowDate());
                list.add(details);
            }
            if (list.size() > 0) {
                orderPurchaseDetailsMapper.insertOrderPurchaseDetails(orderPurchaseDetails);
            }
        }
        System.out.println(orderPurchase);
        return orderPurchaseMapper.insertOrderPurchase(orderPurchase);
    }

    /**
     * 修改进货订单
     *
     * @param orderPurchase 进货订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateOrderPurchase(OrderPurchase orderPurchase, List<OrderPurchaseDetails> orderPurchaseDetails, List<OrderPurchaseDetails> updateDetails) {
        orderPurchase.setUpdateTime(DateUtils.getNowDate());
        orderPurchase.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        List<Long> pdIds = new ArrayList<>();
        for (OrderPurchaseDetails details : updateDetails) {
            pdIds.add(details.getPdId());
        }
        Long[] pdIdsArray = pdIds.toArray(new Long[0]);
        if (pdIdsArray.length > 0) {
            //删除旧数据
            orderPurchaseDetailsMapper.deleteOrderPurchaseDetailsByPdIds(pdIdsArray);
        }

        //添加新数据
        if (StringUtils.isNotNull(orderPurchaseDetails)) {
            System.out.println(orderPurchaseDetails);
            List<OrderPurchaseDetails> list = new ArrayList<>();
            for (OrderPurchaseDetails details : orderPurchaseDetails) {
                details.setPoCode(orderPurchase.getPoCode());
                for (OrderPurchaseDetails updateDetail : updateDetails) {
                    if (details.getgCode().equals(updateDetail.getgCode())) {
                        details.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
                        details.setUpdateTime(DateUtils.getNowDate());
                        details.setCreateTime(updateDetail.getCreateTime());
                        System.out.println("创建人" + updateDetail.getCreateBy());
                        details.setCreateBy(updateDetail.getCreateBy());
                        break;  // 找到匹配项后跳出内层循环
                    } else {
                        details.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
                        details.setCreateTime(DateUtils.getNowDate());
                    }
                }
                list.add(details);
            }
            if (list.size() > 0) {
                System.out.println("list" + list);
                orderPurchaseDetailsMapper.insertOrderPurchaseDetails(orderPurchaseDetails);
            }
        }
        return orderPurchaseMapper.updateOrderPurchase(orderPurchase);
    }

    /**
     * 批量删除进货订单
     *
     * @param poIds 需要删除的进货订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderPurchaseByPoIds(Long[] poIds) {
        return orderPurchaseMapper.deleteOrderPurchaseByPoIds(poIds);
    }

    /**
     * 删除进货订单信息
     *
     * @param poId 进货订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderPurchaseByPoId(Long poId) {
        return orderPurchaseMapper.deleteOrderPurchaseByPoId(poId);
    }

    @Override
    public OrderPurchase getPoCode() {
        //前缀 (客户)
        String prefix = "JH";

        OrderPurchase orderPurchase = orderPurchaseMapper.getPoCode();
        System.out.println(orderPurchase);
        String code = orderPurchase.getPoCode();
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
        System.out.println(resultString);

        orderPurchase.setPoCode(resultString);

        return orderPurchase;
    }

    /**
     * 审核
     *
     * @param
     * @param status
     * @return
     */
    @Override
    public int updateAudit(Integer status,Long[] poIds,String reviewer) {
        return orderPurchaseMapper.updateAudit(status, poIds,reviewer);
    }
}
