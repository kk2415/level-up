package com.levelup;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class StringTest {

    private static final String HANGUL_CHARS = "가나다라마바사아자차카타파하";
    private static final String ENGLISH_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String ALL_CHARS = HANGUL_CHARS + ENGLISH_CHARS;
    private static final Random RANDOM = new Random();

    @Test
    void test() {
        String string = "가";
        System.out.println(string);
    }

    @Test
    void test1() {
        int n = 100; // 생성할 문자열의 개수
        int minStringLength = 3; // 문자열의 최소 길이
        int maxStringLength = 15; // 문자열의 최대 길이
        List<String> randomStrings = generateRandomStrings(n, minStringLength, maxStringLength);
//        List<String> randomStrings = new ArrayList<>(List.of("NV타가XF카", "OZFB", "U카NiPy", "uR타kYy", "tp타", "D자cwiiL카", "WTuAIOqolPtr", "다TPs", "nbwyf차oQQ"));

        test2(randomStrings);
        for (String randomString : randomStrings) {
            System.out.println(randomString);
        }
    }

    void test2(List<String> randomStrings) {
        long startTime = System.nanoTime();
        randomStrings.sort(Comparator.naturalOrder());
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        System.out.println("Execution time: " + duration + " nanoseconds");
    }

    public static List<String> generateRandomStrings(int n, int minStringLength, int maxStringLength) {
        List<String> randomStrings = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int stringLength = minStringLength + RANDOM.nextInt(maxStringLength - minStringLength + 1);
            StringBuilder sb = new StringBuilder(stringLength);
            for (int j = 0; j < stringLength; j++) {
                int randomIndex = RANDOM.nextInt(ALL_CHARS.length());
                sb.append(ALL_CHARS.charAt(randomIndex));
            }
            randomStrings.add(sb.toString());
        }

        return randomStrings;
    }
}
