package com.cx.hpxx;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NumberGenerator {
    private String prefix;
    private int sequence=0;
    private LocalDate currentDate = LocalDate.now();
    public NumberGenerator(String prefix) {
        this.prefix = prefix;
    }
    public String generateNumber() {
        LocalDate nowDate=LocalDate.now();
        if (!nowDate.isEqual(currentDate)){
            sequence=0;
            currentDate=nowDate;
        }
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        sequence++;
        String number1 = prefix + timestamp +String.format("%03d",sequence);
        return number1;
    }

    public static void main(String[] args) {
        NumberGenerator num1 =new NumberGenerator("hpxx");
        System.out.println(num1.generateNumber());
        System.out.println();
        NumberGenerator num2 =new NumberGenerator("DB");
        System.out.println(num1.generateNumber());
    }
}
