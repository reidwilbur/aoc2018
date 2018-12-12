package com.wilb0t.aoc;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12 {

  private static int PATLEN = 5;

  public static Map<String, String> parseRules(List<String> input) {
    return input.stream()
        .skip(2)
        .map(l -> {
          String[] parts = l.split(" => ");
          return new AbstractMap.SimpleEntry<>(parts[0], parts[1]);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  public String step(String state, Map<String, String> rules) {
    String wideState = "....." + state + ".....";
    StringBuilder bldr = new StringBuilder();
    for (int i = 0; i <= wideState.length() - PATLEN; i++) {
      bldr.append(rules.getOrDefault(wideState.substring(i, i + PATLEN), "."));
    }
    return bldr.toString();
  }

  public int runPart1(String state, Map<String, String> rules) {
    String s = state;
    int itrs = 20;
    for (int i = 0; i < itrs; i++) {
      System.out.println(s);
      s = step(s, rules);
    }
    String finalState = s;
    System.out.println(finalState);
    return IntStream.range(0, finalState.length())
        .map(i -> (finalState.charAt(i) == '#') ?  (itrs * -1 * (PATLEN - 2)) + i : 0)
        .sum();
  }
}

