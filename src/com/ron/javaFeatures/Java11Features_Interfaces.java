package com.ron.javaFeatures;

import java.util.List;

public class Java11Features_Interfaces {

    public static void main(String[] args) {
        Utils.print("Features Introduced in Java 11: Interfaces()");
        Java11Features_Interfaces j11 = new Java11Features_Interfaces();
        j11.useJ11Interface();
    }


    /**
     * Java 11 introduced private interface methods.
     * They cannot be abstract.
     * They can be static or instance methods.
     * They are not accessible from classes & other interfaces.
     */
    public interface Java11Interface {

        default List<String> doPreferredSort() {
            return sortByName();
        }

        default List<String> sortByName() {
            return sort(1, true);
        }

        default List<String> sortByAddress() {
            return sort(2, true);
        }

        default List<String> sortByAge(boolean direction) {
            return sort(3, direction);
        }

        // a private instance method
        private List<String> sort(int index, boolean direction) {
            Utils.print("sort: index =", index, ", direction =", direction);
            List<String> results = null;
            // do some sorting and processing ...
            return results;
        }

        default void defaultHello() {
            hello("from an instance method");  // instance methods can call static methods
        }

        static void staticHello() {
            // defaultHello();   // static methods cannot call instance methods
            hello("from a static method");
        }

        // a private static method
        private static void hello(String s) {
            Utils.print("Hi", s);
        }
    }


    public void useJ11Interface() {
        Utils.newMethod("useJ11Interface");

        // create an instance of Java11Interface
        // and override one method
        Java11Interface j11i = new Java11Interface() {

            @Override
            public List<String> doPreferredSort() {
                // return sort(3, false);  // inaccessible private member
                return sortByAge(false);
            }
        };

        Java11Interface.staticHello();  // call static methods directly, not with an instance
        j11i.defaultHello();

        j11i.doPreferredSort();
        j11i.sortByName();
        j11i.sortByAddress();
        j11i.sortByAge(false);
    }
}
