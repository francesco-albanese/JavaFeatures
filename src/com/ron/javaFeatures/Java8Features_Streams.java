package com.ron.javaFeatures;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Java8Features_Streams {

    public static void main(String[] args) {
        Utils.print("Features Introduced in Java 8: Streams");
        Java8Features_Streams j8 = new Java8Features_Streams();
        j8.quickIntroToStreams();
        j8.whyDoWeNeedStreams();
        j8.streamIntro();
        j8.creatingStreams();
        j8.convertingStreams();
        j8.intermediateOperations();
        j8.terminalOperations();
    }


    private void quickIntroToStreams() {
        Utils.newMethod("quickIntroToStreams");

        final int MAX = 970;
        List<Integer> myList = Utils.getRandomList(100, 500, 1000);

        // get a Sequential streams
        Stream<Integer> sequentialStream = myList.stream();

        // Filter the list using the Stream API and a lambda expression.
        // How this was done prior to Java 8?
        Stream<Integer> highNumbersSeq = sequentialStream.filter(p -> p > MAX);

        // print out the values - using forEach and a lambda
        highNumbersSeq.forEach(p -> Utils.print("High Numbers: sequential =", p));
        Utils.print();


        // All in one line
        myList.stream().filter(p -> p > MAX).forEach(p -> Utils.print("High Numbers: sequential =", p));
        Utils.print();

        // parallel processing
        myList.parallelStream().filter(p -> p > MAX).forEach(p -> Utils.print("High Numbers: parallel =", p));
        // Note that the values from the parallel stream are not in order
    }


    private void whyDoWeNeedStreams() {
        Utils.newMethod("whyDoWeNeedStreams");

        List<Integer> myList = Utils.getRandomList(100, -500, 500);

        // Solve this problem: get the sum of all positive values in a list

        // prior to Java 8
        int sum = 0;
        for (Integer i : myList) {
            if (i > 0) {
                sum += i;
            }
        }
        Utils.print("sum =", sum);


        // This works just fine, but ...
        // 1. Is this too much code for such a simple problem?
        // 2. We have to deal with the iteration/looping as well as the actual problem. (External iteration)
        // 3. This solution cannot be parallelized easily. (Would be handy with a gigantic list.)


        // The Java 8 way:
        int streamSum = myList.stream().filter(p -> p > 0).mapToInt(i -> i).sum();
        Utils.print("streamSum =", streamSum);

        // This can be parallelized very easily:
        int parallelStreamSum = myList.parallelStream().filter(p -> p > 0).mapToInt(i -> i).sum();
        Utils.print("parallelStreamSum =", parallelStreamSum);


        // Iteration with the Stream API is said to be internal rather than external.
        // i.e. the framework controls the iteration and provides several features:
        // sequential or parallel execution, filtering based on given criteria, mapping, etc

        // The Java 8 Stream API uses mainly functional interfaces (single abstract method)
        // so it works well in conjunction with lambda expressions.
    }


    private void streamIntro() {
        Utils.newMethod("streamIntro");

        // Collections vs Streams
        //
        // Collections: in memory data structures
        //     they store data
        //     must be populated before usage
        //
        // Streams: data structures that are computed on demand
        //     they do not store data
        //     operate on source data structures
        //     produce pipelined data
        //     are consumable - cannot be reused

        // Stream Operations
        //
        // Intermediate Operations: eg filter, map
        //     always return a new Stream
        //     "lazy" in nature - process each element, produce new stream element & send to next operation.
        //                        Necessary because sometimes not all elements need to be processed.

        // Terminal Operations: eg min, max, sum, findFirst, anyMatch, forEach
        //     consume the stream (never return a Stream)
        //     "eager" in nature (process all elements before returning result)

        // Use of Streams can make code considerably smaller and easier to read and maintain.
        // Since much of the work is done by the framework, streams can be very efficient.
        // Parallel processing in particular is easy with streams.
    }

    private void creatingStreams() {
        Utils.newMethod("creatingStreams");

        // use static of() method in interface Stream
        Stream<Integer> stream = Stream.of(1, 2, 3, 1, 2, 3);

        // creating Sequential and Parallel streams from collections
        List<Integer> myList = Utils.getRandomList(10, -10, 10);
        Stream<Integer> sequentialStream = myList.stream();
        Stream<Integer> parallelStream = myList.parallelStream();

        // creating streams from Strings
        IntStream intStreamFromString = "abc".chars();  // Java 9

        // creating streams from Arrays
        IntStream intStream = Arrays.stream(new int[]{1, 2, 3, 4});
        Stream<Integer> stream1 = Arrays.stream(new Integer[]{1, 2, 3, 4});
        Stream<Java8Features_Streams> j8Stream = Arrays.stream(new Java8Features_Streams[]{});

        // use Stream.iterate() and Stream.generate()
        Stream<String> strStream1 = Stream.iterate("12345", i -> Integer.parseInt(i) + 321 + "");
        Stream<String> strStream2 = Stream.generate(() -> {
            return "abc";
        });
    }

    private void convertingStreams() {
        Utils.newMethod("convertingStreams");

        // use collect() to convert a stream to a collection (Set, List or Map)

        Stream<Integer> intStream = Stream.of(4, 3, 2, 1, 2, 3, 4, 1, 2, 3, 1);
        Set<Integer> intSet = intStream.collect(Collectors.toSet());
        Utils.print("intStream: set =", intSet.toString());

        intStream = Stream.of(4, 3, 2, 1, 2, 3, 4, 1, 2, 3, 1); // stream was closed - have to create a new one
        Utils.print("intStream: list =", intStream.collect(Collectors.toList()).toString());

        intStream = Stream.of(9, 3, 5, 1, 13, 2);
        Map<Integer, Integer> intMap = intStream.collect(Collectors.toMap(k -> k, v -> v * v));
        Utils.print("intStream: map =", intMap.toString());

        // Convert Stream to an Array
        intStream = Stream.of(1, 2, 3, 4);
        Integer[] intArray = intStream.toArray(Integer[]::new);
        Utils.print("intStream1: array =", Arrays.toString(intArray));
    }

    private void intermediateOperations() {
        Utils.newMethod("intermediateOperations");
        List<Integer> list = Utils.getRandomList(20, -100, 100);
        Utils.print("list =", list);

        // filter()
        Stream<Integer> stream1 = list.stream();
        Stream<Integer> positives = stream1.filter(p -> p > 0); // keep only positive numbers
        Utils.printStream("filter: positives", positives, true);

        // map()
        Stream<String> words = Stream.of("m", "thr33", "Awesome", "Consulting", "Corporation");
        // Stream<String> upperCaseStream = words.map(s -> s.toUpperCase());
        Stream<String> upperCaseStream = words.map(String::toUpperCase);
        Utils.printStream("map: upperCase", upperCaseStream, true);

        // sorted()
        Stream<Integer> stream2 = list.stream();
        Stream<Integer> sortedInts = stream2.sorted();
        Utils.printStream("sorted", sortedInts, false);

        // sorted() using Comparator argument
        Stream<Integer> stream3 = list.stream();
        Stream<Integer> reverseSortedInts = stream3.sorted(Comparator.reverseOrder());
        Utils.printStream("reverse sorted", reverseSortedInts, false);

        // flatMap() - 'flatten' a stream of collections to a simple, flat stream of objects
        Stream<List<String>> wordListsStream = Stream.of(
                Arrays.asList("The", "quick", "brown", "fox"),
                Arrays.asList("jumps", "over"),
                Arrays.asList("the", "lazy", "dog"));
        // Utils.printStream("wordListsStream", wordListsStream, true);
        Stream<String> wordStream = wordListsStream.flatMap(Collection::stream);
        Utils.printStream("flatMap", wordStream, true);
    }

    private void terminalOperations() {
        Utils.newMethod("terminalOperations");
        List<Integer> shortList = Arrays.asList(2, 3, 4, 5, 10);
        List<Integer> randomList = Utils.getRandomList(12, -200, 400);
        terminalOperations(shortList, "shortList");
        terminalOperations(randomList, "randomList");
    }

    private void terminalOperations(List<Integer> intList, String name) {

        // count()
        Utils.print("\nname = " + name + ", count =", intList.stream().count());
        Utils.print("intList =", intList);

        // reduce() - use an associative accumulation function
        Optional<Integer> optionalSum = intList.stream().reduce((i, j) -> i + j);
        if (optionalSum.isPresent()) {
            Utils.print("reduce: sum =", optionalSum.get());
        }
        Optional<Integer> optionalProduct = intList.stream().reduce((i, j) -> i * j);
        optionalProduct.ifPresent(result -> Utils.print("reduce: product =", result));

        // forEach() - please also see separate document
        //
        // forEach() - print out odd numbers
        Stream<Integer> intStream = intList.stream();
        intStream.forEach(i -> {
            if (i % 2 == 1) Utils.print("forEach: odd =", i);
        });

        // match - match at least one, none or all items
        Utils.print("match: does the stream contain 10? ", intList.stream().anyMatch(i -> i == 10));
        Utils.print("match: does the stream not contain 10? ", intList.stream().noneMatch(i -> i == 10));
        Utils.print("match: are all elements positive? ", intList.stream().allMatch(i -> i > 0));
        // exercise: change the above responses to Yes/No instead of true/false

        // findFirst
        Optional<Integer> optionalFirstOdd = intList.stream().filter(i -> i % 2 == 1).findFirst();
        optionalFirstOdd.ifPresent(integer -> Utils.print("findFirst: first odd number =", integer));

        // findAny
        Optional<Integer> optionalFirstAny = intList.stream().filter(i -> i % 2 == 1).findAny();
        optionalFirstAny.ifPresent(integer -> Utils.print("findAny: an odd number =", integer));
    }
}
