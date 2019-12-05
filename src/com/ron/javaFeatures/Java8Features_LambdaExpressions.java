package com.ron.javaFeatures;

public class Java8Features_LambdaExpressions {

    public static final String INTRO_NAME = "Hi, I am";
    public static final String AND = "and";
    public static final String INTRO_JOB = "I am a";

    public static void main(String[] args) {
        Utils.print("Features Introduced in Java 8: LambdaExpressions");
        Java8Features_LambdaExpressions j8 = new Java8Features_LambdaExpressions();
        j8.lambdaExpressions();
    }

    /*
        Variables – Lambdas can only access final or effectively final (ie never changed) variables.
        State – Anonymous inner classes can use instance variables and thus can have state, lambdas cannot.
        Scope – Lambdas can’t define a variable with the same name as a variable in enclosing scope.
        Compilation – (Anonymous) class compiles to a class, lambda is a bytecode invokedynamic instruction.
        Performance - At runtime (anonymous) inner classes require class loading, memory allocation,
            and object initialization and invocation of a non-static method while lambda expressions
            don’t incur extra cost during runtime.
        Debugging - much easier with (anonymous) classes
     */

    @FunctionalInterface // This annotation allows exactly 1 abstract method
    private interface Announce {
        String introduce();
        // void anotherAbstractMethod();    // This would cause a compilation error if uncommented
    }

    @FunctionalInterface
    private interface Present {
        String introduce(String name);
    }

    @FunctionalInterface
    private interface Intro {
        String introduce(String name, String job);
    }

    // better to use a single interface with varargs?


    private class MyIntro implements Intro {

        @Override
        public String introduce(String name, String job) {
            return Utils.print(INTRO_NAME, name, AND, INTRO_JOB, job);
        }
    }


    private void lambdaExpressions() {
        Utils.newMethod("lambdaExpressions");

        // Note: Lambdas work with interfaces with exactly one abstract method (aka Functional Interfaces).
        // You may use the annotation @FunctionalInterface to help prevent accidental addition of abstract methods.


        // Create a Intro object with a named (inner) class
        Intro introA = new MyIntro();

        // Create a Intro object with an anonymous inner class
        Intro introB = new Intro() {

            @Override
            public String introduce(String name, String job) {
                return Utils.print(INTRO_NAME, name, AND, INTRO_JOB, job);
            }
        };

        // 1st lambda expression version:
        Intro introL1 = (String name, String job) -> {
            return Utils.print(INTRO_NAME, name, AND, INTRO_JOB, job);
        };
        // Note that some key words are omitted compared to the anonymous class
        // including 'new Intro()', the method name, access modifier and return type:

        // The method parameter types need not be specified:
        Intro introL2 = (name, job) -> {
            return Utils.print(INTRO_NAME, name, AND, INTRO_JOB, job);
        };

        // If the implementation is a single line, the curly braces and 'return' may be removed:
        Intro introL3 = (name, job) -> Utils.print(INTRO_NAME, name, AND, INTRO_JOB, job);


        // The original anonymous inner class can thus be replaced by a single line of code.


        // If the single abstract method has only one parameter, you do not need the ():
        Present present = name -> Utils.print(INTRO_NAME, name);

        // Using a single abstract method which has no parameters:
        Announce announce = () -> Utils.print("I do not know who I am");


        // let's try using them:
        introA.introduce("Liz", "queen");
        introB.introduce("Florence", "machine");
        introL1.introduce("Francis", "pope");
        introL2.introduce("Donald", "dope");
        introL3.introduce("Justin", "chanteuse");

        present.introduce("Kim");
        announce.introduce();
    }
}
