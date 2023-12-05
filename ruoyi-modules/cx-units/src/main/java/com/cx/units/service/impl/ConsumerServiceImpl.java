package com.cx.units.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.cx.units.domain.Supplier;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.cx.units.domain.Finance;
import com.cx.units.mapper.ConsumerMapper;
import com.cx.units.domain.Consumer;
import com.cx.units.service.IConsumerService;

/**
 * 客户Service业务层处理
 *
 * @author 刘志昂
 * @date 2023-11-29
 */
@Service
public class ConsumerServiceImpl implements IConsumerService {
    @Autowired
    private ConsumerMapper consumerMapper;

    /**
     * 查询客户
     *
     * @param cId 客户主键
     * @return 客户
     */
    @Override
    public Consumer selectConsumerByCId(Long cId) {
        return consumerMapper.selectConsumerByCId(cId);
    }

    /**
     * 查询客户列表
     *
     * @param consumer 客户
     * @return 客户
     */
    @Override
    public List<Consumer> selectConsumerList(Consumer consumer) {
        return consumerMapper.selectConsumerList(consumer);
    }

    /**
     * 新增客户
     *
     * @param consumer 客户
     * @return 结果
     */
    @Transactional
    @Override
    public int insertConsumer(Consumer consumer, Finance finance) {
        consumer.setCreateTime(DateUtils.getNowDate());
        consumer.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        System.out.println(consumer + " " + finance);
        consumerMapper.insertFinance(finance);
        consumer.setfId(finance.getfId());
        int rows = consumerMapper.insertConsumer(consumer);
        //insertFinance(consumer);
        return rows;
    }

    /**
     * 修改客户
     *
     * @param consumer 客户
     * @return 结果
     */
    @Transactional
    @Override
    public int updateConsumer(Consumer consumer, Finance finance) {
        consumer.setUpdateTime(DateUtils.getNowDate());
        consumer.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        consumerMapper.updateConsumer(consumer);
        consumerMapper.updateFinance(finance);
        return consumerMapper.updateFinance(finance);
    }

    /**
     * 批量删除客户
     *
     * @param cIds 需要删除的客户主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteConsumerByCIds(Long[] cIds,Long[] fIds) {
        consumerMapper.deleteFinanceByFIds(fIds);
        return consumerMapper.deleteConsumerByCIds(cIds);
    }

    /**
     * 删除客户信息
     *
     * @param cId 客户主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteConsumerByCId(Long cId) {
        consumerMapper.deleteFinanceByFId(cId);
        return consumerMapper.deleteConsumerByCId(cId);
    }

    /**
     * 生成客户代码
     *
     * @return
     */
    @Override
    public Consumer getConsumerCode() {
        //前缀 (客户)
        String prefix = "KH";

        Consumer consumer = consumerMapper.getConsumerCode();
        System.out.println(consumer);
        String code = consumer.getcCode();
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

        consumer.setcCode(resultString);

        return consumer;
    }

    /**
     * 修改财务信息
     *
     * @param finance
     * @return
     */
    @Override
    public int updateFinance(Finance finance) {
        return consumerMapper.updateFinance(finance);
    }

    /**
     * 新增财务信息
     *
     * @param consumer 客户对象
     */
//    public void insertFinance(Consumer consumer)
//    {
//        Finance finance = consumer.getFinance();
//        Long cId = consumer.getcId();
//        if (StringUtils.isNotNull(financeList))
//        {
//            List<Finance> list = new ArrayList<Finance>();
//            for (Finance finance : financeList)
//            {
//                finance.setfId(cId);
//                list.add(finance);
//            }
//            if (list.size() > 0)
//            {
//                consumerMapper.batchFinance(list);
//            }
//        }
//    }
}
