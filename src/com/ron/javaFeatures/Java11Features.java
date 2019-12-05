package com.ron.javaFeatures;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class Java11Features {

    public static void main(String[] args) throws IOException {
        Utils.print("Features Introduced in Java 9: Interfaces()");
        Java11Features j11 = new Java11Features();
        j11.javaShell_REPL();
        j11.immutableCollections();
        j11.tryWithResources(null);
        j11.processAPI();
        j11.streamAPI();
        j11.stringClass();
        j11.otherFeatures();
    }

    public void javaShell_REPL() {
        Utils.newMethod("javaShell_REPL");
    }

    public void immutableCollections() {
        Utils.newMethod("immutableCollections");

        // Java 5
        List emptyListJ5 = Collections.emptyList();
        Set emptySetJ5 = Collections.emptySet();
        Map emptyMapJ5 = Collections.emptyMap();

        List<Integer> mutableList = new ArrayList<>();
        mutableList.add(1);
        mutableList.add(2);
        mutableList.add(3);
        List<Integer> j8ImmutableList = Collections.unmodifiableList(mutableList);

        Map<Integer, String> mutableMap = new HashMap<>();
        mutableMap.put(1, "one");
        mutableMap.put(2, "to");
        mutableMap.put(3, "three");
        Map<Integer, String> j8ImmutableMap = Collections.unmodifiableMap(mutableMap);


        // Java 11
        // of() and ofEntryies() methods
        // These are factory methods which produced optimised immutable collections
        // There is no need to specify the implementation

        List j11EmptyList = List.of();
        List<Integer> j11IntList = List.of(1, 3, 5, 3, 1);
        List<String> j11StrList = List.of("one", "too", "three");

        Set j11EmptySet = Set.of();
        Set<Integer> j11IntSet = Set.of(1, 2, 3);
        Set<String> j11StrSet = Set.of("won", "two", "tree");

        Map j11EmptyMap = Map.of();
        Map j11Int2StringMap = Map.of(1, "one", 2, "two", 3, "free");
        Map j11CubeMap = Map.of(1, 1, 2, 8, 3, 27, 4, 64);

        // for maps, ofEntries() is clearer than of() for creating populated immutable maps
        Map<Integer, String> emptyImmutableMap = Map.ofEntries();
        Map<Integer, String> nonemptyImmutableMap = Map.ofEntries(
                entry(1, "one"), entry(2, "two"), entry(3, "theee"));

    }

    public void tryWithResources(BufferedReader reader) {
        Utils.newMethod("tryWithResources");

        // Java 8
        try (BufferedReader j8Reader = reader;) {
            Utils.print(j8Reader.readLine());
        } catch (Exception e) {
            Utils.print("resource problem: ", e.getMessage());
        }

        // Java 11
        try (reader) {
            Utils.print(reader.readLine());
        } catch (Exception e) {
            Utils.print("resource problem: ", e.getMessage());
        }
    }

    public void processAPI() {
        Utils.newMethod("processAPI");

        ProcessHandle currentProcess = ProcessHandle.current();
        Utils.print("Process ID =", currentProcess.pid());

        Optional<ProcessHandle> parentOptional = currentProcess.parent();
        if (parentOptional.isPresent()) {
            Utils.print("Parent process ID =", parentOptional.get());
        }

        currentProcess.children().forEach(t -> Utils.print("child process =", t.pid()));
    }

    public void streamAPI() {
        Utils.newMethod("streamAPI");

        // new methods: dropWhile, takeWhile, ofNullable

        // iterate method can take a predicate (the 2nd argument)
        IntStream.iterate(10, i -> i < 20, i -> i + 2).forEach(Utils::print);

        // this version without the predicate would run forever
        // IntStream.iterate(10, i -> i + 2).forEach(System.out::println);

        // Optional can be turned into a stream
        Stream<String> strStream = Optional.of("xyz").stream();
    }

    public void stringClass() {
        Utils.newMethod("stringClass - additions");

        // Java 9
        IntStream intStream1 = "xyz".chars();
        IntStream intStream2 = "xyz".codePoints();

        // Java 11
        String s = "  hi there  ";
        Utils.print("s: [", s, "]");
        Utils.print("strip: [", s.strip(), "]");
        Utils.print("stripLeading: [", s.stripLeading(), "]");
        Utils.print("stripTrailing: [", s.stripTrailing(), "]");
        // compare with trim(); strip() is unicode-aware

        Utils.print("isBlank():", s.isBlank());
        Stream<String> stream = s.lines();
        Utils.print("repeat:", s.repeat(5));
    }

    public void otherFeatures() throws IOException {

        Utils.newMethod("Other Features");

        // run source code with a single command (java.exe - no need to compile first)

        // local variable type inference
        var list = new ArrayList<String>();


        // Garbage Collectors
        // Epsilon - a no-op garbage collector
        //      no GC pauses, but OutOfMemoryError before too long (only for testing!)
        // ZGC - a scalable, low-latency, experimental GC


        // new methods in Files class
        Path path = Files.writeString(Files.createTempFile("test", ".txt"), "line 1\nline 2\nline 3\n");
        System.out.println(path);
        String s = Files.readString(path); // read whole file into a string
        System.out.println(s);


        Utils.newMethod("Modules");
        // Java module system; modular JAR files; "requires", "exports";


        Utils.newMethod("jlink");
        // Create your own JRE
        // jlink tool - link modules -> create minimal runtime environment
        // The default JRE contains >4000 Java class files, over 200MB
        // => not suitable for IoT devices, low memory usage, microservices, etc
        // jlink - create a JRE with only required classes


        Utils.newMethod("Multi-Release Jars");
        // Multi-release JARs allow you to create a single JAR file
        // that contains bytecode for several Java versions.
        // JVMs will then load the code that was included for their version.


        Utils.newMethod("Javadoc Search");
        // search box added to javadoc generated API documentation
        // (but who writes documentation?)


        Utils.newMethod("JShell");
        // JShell - interactive Java REPL (read-eval-print loop)
    }
}
