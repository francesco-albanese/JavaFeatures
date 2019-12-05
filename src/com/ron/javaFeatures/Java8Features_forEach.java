package com.ron.javaFeatures;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Java8Features_forEach {

    public static void main(String[] args) {
        Utils.print("Features Introduced in Java 8: forEach()");
        Java8Features_forEach j8 = new Java8Features_forEach();
        j8.forLoopProgression();
        j8.mapIteration();
    }

    public void forLoopProgression() {
        Utils.newMethod("forLoopProgression");

        List<Integer> myList = Utils.getRandomList(5, -1000, 1_000);

        // traversing a list prior to Java 5
        for (int i = 0; i < myList.size(); i++) {
            int value = ((Integer) myList.get(i)).intValue();
            Utils.print("old for loop: value =", value);
            // myList.remove(value);   // we cannot remove items while looping - this would cause a runtime exception
        }

        // traversing a list: Java 5 (autoboxing and generics were introduced in Java 5)
        for (int i = 0; i < myList.size(); i++) {
            int value = myList.get(i);
            Utils.print("old for loop: value =", value);
        }

        // traversing with the enhanced for loop (Java 5)
        for (Integer i : myList) {
            Utils.print("Java 5 for loop: value =", i);
        }

        // traversing using an Iterator
        for (Iterator<Integer> iter = myList.iterator(); iter.hasNext(); ) {
            Integer i = iter.next();
            Utils.print("Iterator - for loop: value =", i);
            // iter.remove();
        }

        // traversing using an Iterator with a while loop
        Iterator<Integer> iter = myList.iterator();
        while (iter.hasNext()) {
            Integer i = iter.next();
            Utils.print("Iterator - while loop: value =", i);
            if (i < 0) {
                iter.remove(); // with an iterator it is possible to remove items while looping
            }
        }


        // Java 8 introduced the forEach() method in the java.lang.Iterable interface
        // to help focus on the business logic.
        // The forEach() method takes a java.util.function.Consumer object as an argument.
        // Code may be longer, but it can keep the business logic at a separate location that can be reused.

        // traversing a list using forEach method of Iterable with an anonymous class
        myList.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                Utils.print("forEach with anonymous class: value =", t);
            }
        });

        // traversing using forEach with a (reuseable) named class
        MyConsumer myConsumer = new MyConsumer();
        myList.forEach(myConsumer);

        // traversing using forEach with a Java 8 lambda expression
        myList.forEach(t -> Utils.print("forEach with lambda: value =", t));
    }

    // A named inner class.
    // This is an implementation of Consumer that can be reused.
    private class MyConsumer implements Consumer<Integer> {

        @Override
        public void accept(Integer t) {
            Utils.print("forEach with named class: value =", t);
        }
    }

    // This is an implementation of BiConsumer that can be reused.
    private class MapConsumer implements BiConsumer<Integer, Integer> {

        @Override
        public void accept(Integer k, Integer v) {
            Utils.print("forEach with named class: key =", k, ", value =", v);
        }
    }

    public void mapIteration() {
        Utils.newMethod("mapIteration");

        // Map interface also has a forEach() method
        // This loops over the map's entrySet and calls the accept method with both key & value

        Map<Integer, Integer> map = Utils.getRandomMap(100, 7);

        // iterate over the map with forEach() and an anonymous inner class
        map.forEach(new BiConsumer<Integer, Integer>() {

            @Override
            public void accept(Integer key, Integer value) {
                Utils.print("forEach with anonymous class: key =", key, ", value =", value);

            }
        });

        // iterate over the map with forEach() and a reusable named class
        map.forEach(new MapConsumer());

        // iterate over the map with forEach() and a lambda expression
        map.forEach((k, v) -> Utils.print("forEach with lambda: key =", k, ", value =", v));
    }
}
