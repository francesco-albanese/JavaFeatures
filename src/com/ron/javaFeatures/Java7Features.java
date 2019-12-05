package com.ron.javaFeatures;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Java7Features {

    private static final Logger logger = Logger.getLogger(Java7Features.class.getName());

    // underscores in numeric literals
    public static final int MILLION = 1000000;               // prior to Java 7
    public static final int BILLION = 1_000_000_000;         // Java 7
    public static final long TRILLION = 1_000_000_000_000L;  // Java 7


    // Binary Literals
    private int b0 = 0b100001;
    private int b1 = 0b0001_0011;
    private int b2 = 0b0011_0100_0101_0110;
    private int b3 = 0b0111_1000_1001_1010_1011_1100;


    // Type Inference for Generic Instance Creation
    private Set<Float> java6Set = new TreeSet<Float>();
    private Set<Float> java7Set = new TreeSet<>();

    private List<Boolean> java6List = new ArrayList<Boolean>();
    private List<Boolean> java7List = new ArrayList<>();

    private Map<String, List<Double>> java6Map = new HashMap<String, List<Double>>();
    private Map<String, List<Double>> java7Map = new HashMap<>();


    public static final String ROOT = ".\\src\\com\\ron\\examples\\ron\\";
    public static final String IN_FILE = ROOT + "hello.txt";
    public static final String OUT_FILE = ROOT + "out.txt";

    public static void main(String[] args) {
        logger.info("Features Introduced in Java 7");
        Java7Features j7 = new Java7Features();
        j7.tryWithResources();
        j7.tryWithResources_betterErrorReporting();
        j7.stringsInSwitch("abc");
        j7.stringsInSwitch_2("abc");
        try {
            j7.multipleExceptionHandling();
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Failed in multipleExceptionHandling()", e);
        }
    }

    public void tryWithResources() {
        Utils.newMethod_Log("tryWithResources");

        // also known as Automatic Resource Management

        // prior to Java 7
        // notice that one try is nested inside another
        try {
            // must declared 'reader' outside the try block
            // otherwise it won't be in scope in the finally block
            BufferedReader reader = new BufferedReader(new FileReader(IN_FILE));
            try {
                String firstLine = reader.readLine();
                logger.info(firstLine);
            } finally {
                reader.close();    // remember to close the reader
            }
        } catch (IOException e) {
            logger.severe("resource problem: " + e.getMessage());
        }


        // Java 7
        // single try block; AutoCloseable reader will be automatically closed
        try (BufferedReader reader = new BufferedReader(new FileReader(IN_FILE))) {
            String firstLine = reader.readLine();
            logger.info(firstLine);
        } catch (IOException e) {
            logger.severe("resource problem: " + e.getMessage());
        }


        // Java 7
        try (
                // one or more AutoCloseable resources
                Reader reader = new BufferedReader(new FileReader(IN_FILE));
                Writer writer = new BufferedWriter(new FileWriter(OUT_FILE));
                InputStream in = new FileInputStream(IN_FILE);
                OutputStream out = new FileOutputStream(OUT_FILE);
        ) {
            // use the reader, writer, streams, etc
        } catch (IOException e) {
            logger.severe("resource problem: " + e.getMessage());
        }
    }


    public void tryWithResources_betterErrorReporting() {
        Utils.newMethod_Log("tryWithResources_betterErrorReporting");

        // prior to Java 7
        // notice that one try is nested inside another
        try {
            // must declared 'reader' outside the try block
            // otherwise it won't be in scope in the finally block
            BufferedReader reader = new BufferedReader(new FileReader(IN_FILE));
            try {
                String firstLine = reader.readLine();
                logger.info(firstLine);
            } catch (IOException e) {
                logger.severe("Failed to read file: " + IN_FILE);
                // e.printStackTrace();
            } finally {
                reader.close();    // manually close the reader
            }
        } catch (FileNotFoundException e) {
            logger.severe("Could not find file: " + IN_FILE);
            // e.printStackTrace();
        } catch (IOException e) {
            logger.severe("Failed to close file: " + IN_FILE);
            // e.printStackTrace();
        }


        // Java 7
        // single try block; reader will be automatically closed
        try (BufferedReader reader = new BufferedReader(new FileReader(IN_FILE))) {
            String firstLine = reader.readLine();
            logger.info(firstLine);
        } catch (FileNotFoundException e) {
            logger.severe("Could not find file: " + IN_FILE);
            // e.printStackTrace();
        } catch (IOException e) {
            // stack trace is needed to tell us whether read or close failed
            logger.severe("Failed to read or close file: " + IN_FILE);
            // e.printStackTrace();
        }
    }


    private void stringsInSwitch(String s) {
        Utils.newMethod_Log("stringsInSwitch");

        // String objects can be used in switch statements.
        // In this (contrived) example there are more lines of code in the switch version
        // but it is a bit easier to follow and to maintain.

        // prior to Java 7
        if ("abc".equals(s)) {
            doSomething(1, 2, 3);
        }
        if ("abc".equals(s) || "def".equals(s)) {
            doSomething(4, 5, 6);
        }
        if ("abc".equals(s) || "def".equals(s) || "foo".equals(s) || "bar".equals(s) || "janfu".equals(s)) {
            doSomething(10, 20, 30, 40, 50);
        } else if ("ijk".equals(s)) {
            doSomething(7, 8, 9);
        } else {
            doSomething(0);
        }


        // Java 7
        switch (s) {
            case "abc":
                doSomething(1, 2, 3);
            case "def":
                doSomething(4, 5, 6);
            case "foo":
            case "bar":
            case "janfu":
                doSomething(10, 20, 30, 40, 50);
                break;
            case "ijk":
                doSomething(7, 8, 9);
                break;
            default:
                doSomething(0);
        }
    }

    private void stringsInSwitch_2(String s) {
        Utils.newMethod_Log("stringsInSwitch_2");

        // In this example the switch version is longer and looks more complicated
        // (((However, the Java compiler usually generates more efficient bytecode from switch
        // statements that use String objects than from chained if/else statements.)))


        // prior to Java 7
        if ("abc".equals(s)) {
            doSomething(1, 2, 3);
        } else if ("def".equals(s)) {
            doSomething(4, 5, 6);
        } else if ("foo".equals(s) || "bar".equals(s) || "janfu".equals(s)) {
            doSomething(10, 20, 30, 40, 50);
        } else if ("ijk".equals(s)) {
            doSomething(7, 8, 9);
        } else {
            doSomething(0);
        }


        // Java 7
        switch (s) {
            case "abc":
                doSomething(1, 2, 3);
                break;
            case "def":
                doSomething(4, 5, 6);
                break;
            case "foo":
            case "bar":
            case "janfu":
                doSomething(10, 20, 30, 40, 50);
                break;
            case "ijk":
                doSomething(7, 8, 9);
                break;
            default:
                doSomething(0);
        }
    }


    private void multipleExceptionHandling() throws IOException, InterruptedException {
        Utils.newMethod_Log("multipleExceptionHandling");

        // this is handy for removing code duplication (better than writing a method)
        // but only if you do exactly the same processing in 2 or more exception handlers

        // prior to Java 7
        try {
            exceptionalMethod();
        } catch (NullPointerException e) {
            // logging, stack trace, etc
            // do some processing, recovery, cleaning up, etc
            // rethrow exception
            throw e;
        } catch (IOException e) {
            // logging, stack trace, etc
            // do some processing, recovery, cleaning up, etc
            // rethrow exception
            throw e;
        } catch (InterruptedException e) {
            // logging, stack trace, etc
            // do some processing, recovery, cleaning up, etc
            // rethrow exception
            throw e;
        }

        // Java 7
        try (BufferedReader reader = new BufferedReader(new FileReader(IN_FILE))) {
            logger.info(reader.readLine());
            exceptionalMethod();
        } catch (NullPointerException | IOException | InterruptedException e) {
            // logging, stack trace, etc
            // do some processing, recovery, cleaning up, etc
            // rethrow exception
            throw e;
        }
    }


    // Annotation @SafeVarargs
    // @SuppressWarnings({"unchecked", "varargs"})  // prior to Java 7
    // @SafeVarargs                                 // Java 7

    @SafeVarargs
    private void doSomething(int... numbers) {
        for (int i : numbers) {
            // do something
        }
    }

    private void exceptionalMethod() throws
            FileNotFoundException, ObjectStreamException, IOException, // the first 2 are subclasses of IOException
            NullPointerException, ClassCastException, IllegalArgumentException, // all 3 are runtime exceptions
            InterruptedException, SecurityException, IllegalThreadStateException // threading exceptions
    {
        //
    }
}
