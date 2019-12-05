package com.ron.javaFeatures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * In Java 8 interfaces may contain implementations.
 *
 * For utility classes, you could create a class with static members only
 * (and have a private constructor to prevent instantiation).
 * This is not regarded as outstanding class design but is handy for some utility methods.
 */
public interface Utils {

    Logger logger = Logger.getLogger(Utils.class.getName());

    /**
     * create and populate a list with some random values
     */
    static List<Integer> getRandomList(int maxItems, int minValue, int maxValue) {
        final int numItems = (int) (Math.random() * maxItems);
        logger.fine("list has " + numItems + " items");
        List<Integer> myList = new ArrayList<>();
        for (int i = 0; i < numItems; i++)
            myList.add(getRandomInt(minValue, maxValue));
        return myList;
    }

    static List<Integer> getRandomListByStream(int maxItems, int minValue, int maxValue) {
        return IntStream.iterate(0, i -> i < getRandomInt(minValue, maxValue), i -> getRandomInt(minValue, maxValue)).mapToObj(Integer::valueOf).collect(Collectors.toList());
    }

    static Map<Integer, Integer> getRandomMap(int maxValue, int maxItems) {
        Map<Integer, Integer> map = new HashMap<>();
        int min = Utils.getRandomInt(1, maxValue - maxItems);
        int max = min + Utils.getRandomInt(1, maxItems);
        for (int i = min; i < max; i++) {
            map.put(i, i * i);
        }
        logger.fine("map has " + map.size() + " items");
        return map;
    }

    static int getRandomInt(int min, int max) {
        int range = max - min;
        return (int) (Math.random() * range + 0.5) + min;
    }


    // ----------------------------------------
    // Printing Section
    // TODO: replace System.out.println() and System.err.println() with logging

    static String print(Object o) {
        return print(true, o);
    }

    static String print(boolean isOk, Object o) {
        String s = o == null ? "" : o.toString();
        if (isOk) {
            System.out.println(s);
        } else {
            System.err.println(s);
        }
        return s;
    }

    char SPACE = ' ';

    static String print(Object... objects) {
        return print(true, SPACE, objects);
    }

    static String printerr(Object... objects) {
        return print(false, SPACE, objects);
    }

    @SafeVarargs // really?
    static String print(boolean isOk, char delimiter, Object... objects) {
        StringBuilder sb = new StringBuilder();
        for (Object o : objects) {
            if (o != null) {
                if (sb.length() > 0) {
                    sb.append(delimiter);
                }
                sb.append(o.toString());
            }
        }
        return print(isOk, sb);
    }

    static void newMethod(String method) {
        String title = method; // + "()";
        String underline = title.replaceAll(".", "-");
        print();
        print(title);
        print(underline);
    }

    static void newMethod_Log(String method) {
        String title = method; // + "()";
        String underline = title.replaceAll(".", "-");
        logger.info(title);
        logger.info(underline);
    }

    static void printStream(String text, Stream<?> stream, boolean useCollect) {
        if (useCollect) {
            Utils.print(text, "=", stream.collect(Collectors.toList()));
        } else { // use forEach()
            System.out.print(text);
            System.out.print(" = ");
            stream.forEach(p -> System.out.print(p + ", "));
            System.out.println();
        }
    }
}
