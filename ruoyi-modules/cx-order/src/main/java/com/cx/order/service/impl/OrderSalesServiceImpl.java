package com.cx.order.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cx.order.domain.OrderPurchase;
import com.cx.order.domain.OrderPurchaseDetails;
import com.cx.order.domain.OrderSalesDetails;
import com.cx.order.mapper.OrderSalesDetailsMapper;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.order.mapper.OrderSalesMapper;
import com.cx.order.domain.OrderSales;
import com.cx.order.service.IOrderSalesService;

/**
 * 销售订单Service业务层处理
 *
 * @author LZA
 * @date 2023-12-19
 */
@Service
public class OrderSalesServiceImpl implements IOrderSalesService {
    @Autowired
    private OrderSalesMapper orderSalesMapper;

    @Autowired
    private OrderSalesDetailsMapper orderSalesDetailsMapper;

    /**
     * 查询销售订单
     *
     * @param sId 销售订单主键
     * @return 销售订单
     */
    @Override
    public OrderSales selectOrderSalesBySId(Long sId) {
        return orderSalesMapper.selectOrderSalesBySId(sId);
    }

    /**
     * 查询销售订单列表
     *
     * @param orderSales 销售订单
     * @return 销售订单
     */
    @Override
    public List<OrderSales> selectOrderSalesList(OrderSales orderSales) {
        return orderSalesMapper.selectOrderSalesList(orderSales);
    }

    /**
     * 新增销售订单
     *
     * @param orderSales 销售订单
     * @return 结果
     */
    @Override
    public int insertOrderSales(OrderSales orderSales, List<OrderSalesDetails> orderSalesDetails) {
        orderSales.setCreateTime(DateUtils.getNowDate());
        orderSales.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        if (StringUtils.isNotNull(orderSalesDetails)) {
            System.out.println(orderSalesDetails);
            List<OrderSalesDetails> list = new ArrayList<>();
            for (OrderSalesDetails details : orderSalesDetails) {
                details.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
                details.setCreateTime(DateUtils.getNowDate());
                list.add(details);
            }
            if (list.size() > 0) {
                orderSalesDetailsMapper.insertOrderSalesDetails(orderSalesDetails);
            }
        }
        System.out.println(orderSales);
        return orderSalesMapper.insertOrderSales(orderSales);
    }

    /**
     * 修改销售订单
     *
     * @param orderSales 销售订单
     * @return 结果
     */
    @Override
    public int updateOrderSales(OrderSales orderSales) {
        orderSales.setUpdateTime(DateUtils.getNowDate());
        return orderSalesMapper.updateOrderSales(orderSales);
    }

    /**
     * 批量删除销售订单
     *
     * @param sIds 需要删除的销售订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderSalesBySIds(Long[] sIds) {
        return orderSalesMapper.deleteOrderSalesBySIds(sIds);
    }

    /**
     * 删除销售订单信息
     *
     * @param sId 销售订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderSalesBySId(Long sId) {
        return orderSalesMapper.deleteOrderSalesBySId(sId);
    }

    @Override
    public OrderSales getsCode() {
        //前缀 (进货)
        String prefix = "XS";
        //输出结果
        String resultString = null;

        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        OrderSales orderSales = orderSalesMapper.getsCode();
        System.out.println(orderSales);

        if (orderSales == null) {
            resultString = prefix + timestamp + "001";  // 直接使用字符串 "001"
        } else {
            String code = orderSales.getsCode();
            // 从字符串中提取数字
            Pattern pattern = Pattern.compile("\\d{3}$");
            Matcher matcher = pattern.matcher(code);
            String lastThreeDigits = null;

            if (matcher.find()) {
                lastThreeDigits = matcher.group();
            }

            // 将提取的数字转换为整数并加1
            int incrementedNumber = Integer.parseInt(lastThreeDigits) + 1;

            // 格式化为字符串，保证长度为3，不足前面补零
            String formattedIncrementedNumber = String.format("%03d", incrementedNumber);

            // 构造最终结果
            resultString = prefix + timestamp + formattedIncrementedNumber;
            orderSales.setsCode(resultString);
        }

        System.out.println(resultString);
        return orderSales;
    }
}
