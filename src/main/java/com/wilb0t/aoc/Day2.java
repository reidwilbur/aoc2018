package com.wilb0t.aoc;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day2 {

  public long getChecksum(List<String> boxIds) {
    List<Map<Character, Integer>> counts = boxIds.stream()
        .map(id -> {
          Map<Character, Integer> cc = new HashMap<>();
          id.codePoints()
              .mapToObj(c -> (char) c)
              .forEach(c -> cc.put(c, cc.getOrDefault(c, 0) + 1));
          return cc;
        }).collect(Collectors.toList());

    long twoCounts = counts.stream().filter(cc -> cc.containsValue(2)).count();
    long threeCounts = counts.stream().filter(cc -> cc.containsValue(3)).count();

    return threeCounts * twoCounts;
  }

  public String getMaxCommonChars(List<String> boxIds) {
    return IntStream.range(0, boxIds.size())
        .boxed()
        .flatMap(i -> {
          String id = boxIds.get(i);
          return boxIds.subList(i + 1, boxIds.size()).stream()
              .map(otherId -> commonChars(id, otherId));
        })
        .max(Comparator.comparing(String::length))
        .orElseThrow(() -> new IllegalStateException("bad times"));
  }

  String commonChars(String base, String other) {
    return IntStream.range(0, base.length())
        .boxed()
        .flatMap(i ->
            base.charAt(i) == other.charAt(i)
            ? Stream.of(base.substring(i, i+1))
            : Stream.empty())
        .collect(Collectors.joining(""));
  }
}
