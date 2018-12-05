package com.wilb0t.aoc;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 {

  private static final String reactPatternStr = IntStream.range(0, 26)
      .map(i -> i + 'a')
      .mapToObj(i -> (char) i)
      .flatMap(c -> Stream.of(
          String.valueOf(c) + String.valueOf(Character.toUpperCase(c)),
          String.valueOf(Character.toUpperCase(c)) + String.valueOf(c)
      )).collect(Collectors.joining("|"));

  private static final Pattern reactPattern = Pattern.compile(reactPatternStr);

  public long react(String polymer) {
    boolean reacted = true;
    String lastPolymer = polymer;
    while (reacted) {
      Matcher m = reactPattern.matcher(lastPolymer);
      String newPolymer = m.replaceAll("");
      if (newPolymer.equals(lastPolymer)) {
        reacted = false;
      }
      lastPolymer = newPolymer;
    }
    return lastPolymer.length();
  }

  public long reactReduce(String polymer) {
    return IntStream.range(0, 26)
        .map(i -> i + 'a')
        .mapToObj(i -> (char) i)
        .map(c -> "[" + c + Character.toUpperCase(c) +  "]")
        .map(pattern -> polymer.replaceAll(pattern, ""))
        .map(this::react)
        .min(Comparator.comparing(l -> l))
        .orElseThrow(() -> new IllegalStateException("bad times"));
  }
}
