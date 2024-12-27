package com.project.ihealth.util;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WeightUtil {



    private static final String stomachAche = "肚子痛医生";
    private static final String anxiety = "焦虑医生";

    private static List<Integer> stomachAcheHeight = new ArrayList<>();
    private static List<Integer> stomachAcheNormal = new ArrayList<>();
    private static List<Integer> stomachAcheLow = new ArrayList<>();

    private static List<Integer> anxietyHeight = new ArrayList<>();
    private static List<Integer> anxietyNormal = new ArrayList<>();
    private static List<Integer> anxietyLow = new ArrayList<>();


    private static final BigDecimal stomachAcheSickWeight = new BigDecimal("35");
    private static final BigDecimal anxietySickWeight = new BigDecimal("30");
    private static final BigDecimal stomachAcheGradeWeight = new BigDecimal("30");
    private static final BigDecimal anxietyGradeWeight = new BigDecimal("35");
    private static final BigDecimal importantSickWeight = new BigDecimal("20");
    private static final BigDecimal medicineWeight = new BigDecimal("15");

    static {
        stomachAcheHeight.add(1);
        stomachAcheHeight.add(3);
        stomachAcheHeight.add(5);
        stomachAcheHeight.add(7);
        stomachAcheNormal.add(2);
        stomachAcheNormal.add(9);
        stomachAcheNormal.add(11);
        stomachAcheNormal.add(12);
        stomachAcheNormal.add(13);
        stomachAcheLow.add(4);
        stomachAcheLow.add(6);
        stomachAcheLow.add(10);
        stomachAcheLow.add(8);

        anxietyHeight.add(1);
        anxietyHeight.add(3);
        anxietyHeight.add(4);
        anxietyHeight.add(5);
        anxietyHeight.add(9);
        anxietyHeight.add(18);
        anxietyNormal.add(6);
        anxietyNormal.add(8);
        anxietyNormal.add(10);
        anxietyNormal.add(11);
        anxietyNormal.add(12);
        anxietyNormal.add(17);
        anxietyNormal.add(16);
        anxietyLow.add(7);
        anxietyLow.add(2);
        anxietyLow.add(13);
        anxietyLow.add(14);
        anxietyLow.add(15);
        anxietyLow.add(20);
    }

    public static String getWeight(List<String> list) {
        BigDecimal bigDecimal = new BigDecimal("0");
        boolean isAnxiety = false;
        for (int i = 0; i < list.size(); i++) {
            if (StringUtils.isEmpty(list.get(i))) {
                continue;
            }
            if (list.get(i).contains("焦虑")) {
                isAnxiety = true;
            }
            if (i > 0){
                BigDecimal weight = getWeight(list.get(i), i, isAnxiety);
                bigDecimal = addWeight(bigDecimal, weight);
            }
        }
        // 映射对应的图片
        if (bigDecimal.compareTo(new BigDecimal("80")) >= 0) {
            return isAnxiety ? stomachAche + count(stomachAcheHeight) : anxiety + count(anxietyHeight);
        } else if (bigDecimal.compareTo(new BigDecimal("70")) >= 0) {
            return isAnxiety ? stomachAche + count(stomachAcheNormal) : anxiety + count(anxietyNormal);
        }else if (bigDecimal.compareTo(new BigDecimal("60")) >= 0) {
            return isAnxiety ? stomachAche + count(stomachAcheLow) : anxiety + count(anxietyLow);
        }
        return isAnxiety ? stomachAche : anxiety;
    }
    private static Integer count(List<Integer> list){
        // 生成一个随机索引
        int randomIndex = ThreadLocalRandom.current().nextInt(0, list.size());

        // 使用随机索引从集合中获取一个随机元素
        return list.get(randomIndex);
    }

    private static BigDecimal getWeight(String s, int index, boolean isAnxiety) {
        switch (index){
            case 1:
                return isAnxiety ? multiply(anxietySickWeight, sickCountForChoice(s)) : multiply(stomachAcheSickWeight, sickCountForChoice(s));
            case 2:
                return isAnxiety ? multiply(anxietyGradeWeight, gradeCountForChoice(s)) : multiply(stomachAcheGradeWeight, gradeCountForStomachAcheChoice(s));
            case 3:
                return multiply(importantSickWeight, importantSickCountForChoice(s));
            case 4:
                return multiply(medicineWeight, medicineCountForChoice(s));
            default:
                return new BigDecimal("0");
        }
    }


    private static BigDecimal multiply(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        BigDecimal product = bigDecimal1.multiply(bigDecimal2);

        // 四舍五入保留整数
        return product.setScale(0, RoundingMode.HALF_UP);
    }

    private static BigDecimal addWeight(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        BigDecimal result = bigDecimal1.add(bigDecimal2);

        // 四舍五入保留整数
        return result.setScale(0, RoundingMode.HALF_UP);
    }

    public static void main(String[] args) {

    }


    private static final String oneDay = "一天内";
    private static final BigDecimal oneDayCount = new BigDecimal("0.6");
    private static final String oneWeek = "一周内";
    private static final BigDecimal oneWeekCount = new BigDecimal("0.7");
    private static final String oneMonth = "一个月内";
    private static final BigDecimal oneMonthCount = new BigDecimal("0.8");
    private static final String halfYear = "半年内";
    private static final BigDecimal halfYearCount = new BigDecimal("0.9");
    private static final String longTime = "长期";
    private static final BigDecimal longTimeCount = new BigDecimal("0.9");
    private static final String shortTime = "短期";
    private static final BigDecimal shortTimeCount = new BigDecimal("0.7");
    private static final String importantSickYes = "有";
    private static final BigDecimal importantSickYesCount = new BigDecimal("0.9");
    private static final String stomachAcheVery = "刺痛";
    private static final BigDecimal importantSickNoCount = new BigDecimal("0.6");
    private static final String medicineYes = "有";
    private static final BigDecimal medicineYesCount = new BigDecimal("0.65");
    private static final String stomachAcheLittle = "微痛";
    private static final BigDecimal medicineNoCount = new BigDecimal("0.8");


    private static BigDecimal sickCountForChoice(String value) {
        if (StringUtils.isEmpty(value)) {
            return oneDayCount;
        }
        if (value.contains(oneDay)) {
            return oneDayCount;
        }
        if (value.contains(oneWeek)) {
            return oneWeekCount;
        }
        if (value.contains(oneMonth)) {
            return oneMonthCount;
        }
        if (value.contains(halfYear)) {
            return halfYearCount;
        }
        // 默认返回一天内
        return oneDayCount;
    }
    private static BigDecimal gradeCountForChoice(String value) {
        if (StringUtils.isEmpty(value)) {
            return shortTimeCount;
        }
        if (value.contains(shortTime)) {
            return shortTimeCount;
        }
        if (value.contains(longTime)) {
            return longTimeCount;
        }
        // 默认返回短期
        return shortTimeCount;
    }
    private static BigDecimal gradeCountForStomachAcheChoice(String value) {
        if (StringUtils.isEmpty(value)) {
            return shortTimeCount;
        }
        if (value.contains(stomachAcheLittle)) {
            return shortTimeCount;
        }
        if (value.contains(stomachAcheVery)) {
            return longTimeCount;
        }
        // 默认返回短期
        return shortTimeCount;
    }
    private static BigDecimal importantSickCountForChoice(String value) {
        if (StringUtils.isEmpty(value)) {
            return importantSickNoCount;
        }
        if (value.contains(importantSickYes)) {
            return importantSickYesCount;
        }
        // 默认返回短期
        return importantSickNoCount;
    }
    private static BigDecimal medicineCountForChoice(String value) {
        if (StringUtils.isEmpty(value)) {
            return medicineNoCount;
        }
        if (value.contains(medicineYes)) {
            return medicineYesCount;
        }
        // 默认返回短期
        return medicineNoCount;
    }


//    患病多久了 （35%）：一天内（60%），一周内（70%），一个月内（80%），半年内（90%）
//    病情严重度（30%）：长期（90%），短期（70%）
//    重大患病记录（20%）：有（90%），无（60%）
//    药物使用情况（15%）：有（65%），无（80%）
}
