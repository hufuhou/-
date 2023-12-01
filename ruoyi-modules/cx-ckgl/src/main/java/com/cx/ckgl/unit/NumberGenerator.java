package com.cx.ckgl.unit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberGenerator {
    private String prefix;
    private int sequence=0;
    private LocalDate currentDate = LocalDate.now();
    public NumberGenerator(String prefix) {
        this.prefix = prefix;
    }
    public String generateNumber(String code) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        if (code==null){
            return prefix+timestamp+"001";
        }
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
        return resultString;
    }

}