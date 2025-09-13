package com.levelup.api.controller.v1.channel;

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ChannelControllerTest {

    @Test
    void test() {
        // TreeMap 생성
        TreeMap<Integer, String> treeMap = new TreeMap<>();

        // 데이터 추가
        treeMap.put(10, "Ten");
        treeMap.put(20, "Twenty");
        treeMap.put(5, "Five");
        treeMap.put(30, "Thirty");
        treeMap.put(25, "Twenty-Five");
        treeMap.put(15, "Fifteen");
        treeMap.put(1, "One");
        treeMap.put(50, "Fifty");
        treeMap.put(50, "Forty");
        treeMap.put(35, "Thirty-Five");
        treeMap.put(45, "Forty-Five");

        Set<Map.Entry<Integer, String>> entries = treeMap.entrySet();
        for (Map.Entry<Integer, String> entry : entries) {
            System.out.println(entry.getKey());
        }
    }

    @Test
    void test4() {
        LocalDate mondayDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime mondayDateTime = LocalDateTime.of(mondayDate, LocalTime.MIN);

        System.out.println(mondayDateTime.toString());
    }

    @Test
    void context() {
        List<LocalDateTime> dataSet1 = new ArrayList<>();
        findActivityScore(dataSet1);
    }

    List<Long> findDailyFrequency(List<LocalDateTime> dataSet) {
        LocalDateTime min = dataSet.stream().min(LocalDateTime::compareTo).orElse(LocalDateTime.now());

        return new ArrayList<>(dataSet.stream()
                .collect(Collectors.groupingBy(dateTime -> ChronoUnit.DAYS.between(min, dateTime), Collectors.counting()))
                .values());
    }

    void findActivityScore(List<LocalDateTime> dataSet) {
        List<Long> dailyFrequency = findDailyFrequency(dataSet);

        for (int i = 7 - dailyFrequency.size(); i > 0; i-- ) {
            dailyFrequency.add(0L);
        }

        for (Long aLong : dailyFrequency) {
            System.out.println("Frequency: " + aLong);
        }

        SummaryStatistics stats = new SummaryStatistics();
        for (Long frequency : dailyFrequency) {
            stats.addValue(frequency);
        }

        double mean = stats.getMean();
        double standardDeviation = stats.getStandardDeviation();

        System.out.println("mean: " + mean);
        System.out.println("standard deviation: " + standardDeviation);
        if (standardDeviation == 0) {
            System.out.println("A-Score: " + mean);
        } else {
            System.out.println("A-Score: " + mean/standardDeviation);
        }
    }

    @Test
    void test2() {
        List<LocalDateTime> dataSet1 = new ArrayList<>();
        dataSet1.add(LocalDateTime.of(2024, 6, 9, 0, 0));
        dataSet1.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        dataSet1.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        dataSet1.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        dataSet1.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        dataSet1.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        dataSet1.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        dataSet1.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        dataSet1.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        dataSet1.add(LocalDateTime.of(2024, 6, 15, 11, 0));
        dataSet1.add(LocalDateTime.of(2024, 6, 16, 23, 59));
        test1(dataSet1);

        List<LocalDateTime> dataSet3 = new ArrayList<>();
        dataSet3.add(LocalDateTime.of(2024, 6, 8, 10, 0));
        dataSet3.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        dataSet3.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        dataSet3.add(LocalDateTime.of(2024, 6, 15, 13, 0));
        dataSet3.add(LocalDateTime.of(2024, 6, 15, 13, 0));
        dataSet3.add(LocalDateTime.of(2024, 6, 15, 13, 0));
        dataSet3.add(LocalDateTime.of(2024, 6, 15, 14, 0));
        dataSet3.add(LocalDateTime.of(2024, 6, 15, 14, 0));
        dataSet3.add(LocalDateTime.of(2024, 6, 15, 14, 0));
        dataSet3.add(LocalDateTime.of(2024, 6, 15, 11, 0));
        dataSet3.add(LocalDateTime.of(2024, 6, 16, 23, 59));
        test1(dataSet3);

        List<LocalDateTime> dataSet5 = new ArrayList<>();
        dataSet5.add(LocalDateTime.of(2024, 6, 9, 0, 0));
        dataSet5.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        dataSet5.add(LocalDateTime.of(2024, 6, 16, 23, 59));
        test1(dataSet5);

        List<LocalDateTime> dataSet6 = new ArrayList<>();
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 0, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 0, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 0, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 0, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 0, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 0, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 0, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 0, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 1, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 1, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 1, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 2, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 2, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 3, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 3, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 3, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 3, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 3, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 4, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 4, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 4, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 5, 0));
        dataSet6.add(LocalDateTime.of(2024, 6, 10, 6, 0));
        test1(dataSet6);
    }

    @Test
    void test1(List<LocalDateTime> dataSet) {
        LocalDateTime min = dataSet.stream().min(LocalDateTime::compareTo).orElse(LocalDateTime.now());
        LocalDateTime max = dataSet.stream().max(LocalDateTime::compareTo).orElse(LocalDateTime.now());
        int betweenHours = (int)ChronoUnit.HOURS.between(min, max);
        int[] frequencies = new int[betweenHours + 1];

        Frequency frequency = new Frequency();
        for (LocalDateTime datetime : dataSet) {
            long hours = ChronoUnit.HOURS.between(min, datetime);
            frequency.addValue(hours);
        }

        int i = 0;
        Iterator<Comparable<?>> comparableIterator = frequency.valuesIterator();
        while (comparableIterator.hasNext()) {
            Comparable<?> hours = comparableIterator.next();
            frequencies[i++] = (int)frequency.getCount(hours);
        }

        SummaryStatistics stats = new SummaryStatistics();
        for (int frequency1 : frequencies) {
            stats.addValue(frequency1);
        }

        double mean = stats.getMean();
        double standardDeviation = stats.getStandardDeviation();

        System.out.println("mean: " + mean);
        System.out.println("standard deviation: " + standardDeviation);
        if (standardDeviation == 0) {
            System.out.println("m/s: " + mean);
        } else {
            System.out.println("coefficient of variation, CV: " + standardDeviation/mean);
        }
    }

    @Test
    void create() {
        LocalDateTime start = LocalDateTime.of(2024, 6, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 15, 10, 0);
        long betweenHours = ChronoUnit.HOURS.between(start, end);


        // 데이터 세트 예제
        List<LocalDateTime> datetimes = new ArrayList<>();
        datetimes.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        datetimes.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        datetimes.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        datetimes.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        datetimes.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        datetimes.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        datetimes.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        datetimes.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        datetimes.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        datetimes.add(LocalDateTime.of(2024, 6, 15, 10, 0));
        datetimes.add(LocalDateTime.of(2024, 6, 15, 11, 0));
        datetimes.add(LocalDateTime.of(2024, 6, 15, 11, 0));
        datetimes.add(LocalDateTime.of(2024, 6, 15, 11, 0));

        // 기준 시간 계산 (최소 시간)
        LocalDateTime minTime = datetimes.stream().min(LocalDateTime::compareTo).orElseThrow();

        // Frequency 객체 생성
        Frequency frequency = new Frequency();

        // 각 datetime을 1시간 단위로 나누어 도수 계산
        for (LocalDateTime datetime : datetimes) {
            long hours = ChronoUnit.HOURS.between(minTime, datetime);
            frequency.addValue(hours);
        }

        SummaryStatistics stats = new SummaryStatistics();
        Iterator<Comparable<?>> comparableIterator = frequency.valuesIterator();
        while (comparableIterator.hasNext()) {
            Comparable<?> next = comparableIterator.next();
            stats.addValue(frequency.getCount(next));
        }

        System.out.println("mean: " + stats.getMean());
        System.out.println("standard deviation: " + stats.getStandardDeviation());
    }
}
