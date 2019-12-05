package com.ron.javaFeatures;

import java.time.LocalDateTime;

public class Java8Features_Interfaces {

    public static void main(String[] args) {
        Utils.print("Features Introduced in Java 8: Interfaces()");
        // Java8Features_Interfaces j8 = new Java8Features_Interfaces();
    }

    // Default and Static methods in interfaces.
    // Prior to Java 8, interfaces could not contain implementations.
    public interface Java8Interface {

        void method(String str);

        void method(int i);

        void method(double d, boolean b);

        // A method with an implementation (which can be overridden).
        // Useful if the implementation of the method would be the same for most users.
        // Also useful for adding new methods to old interfaces which already have users.
        default void log(String str, int level) {
            LocalDateTime now = LocalDateTime.now();
            Utils.print(now, "Level", level, ":", str);
        }

        // A static method in an interface - these cannot be overridden
        static String help(String s) {
            return Utils.print("Please help!", s);
        }

        // Note that you cannot override the methods in class Object
        // This would cause a compilation error:
//        default String toString() {
//            return "This is not the object you are looking for";
//        }
    }



    // The Diamond Problem in multiple inheritance.
    //
    // This now arises because interfaces can have implementations.
    // If a class (or interface) implements 2 interfaces both of which have default implementations
    // of a method with exactly the same signature, which implementation will be used?
    // Answer: the code will not compile. The child class must have its own implementation of the method
    // which may be something new or may redirect to one of its parents.
}
