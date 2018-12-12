package com.wilb0t.aoc;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    int offset = 0;
    for (int i = 0; i < itrs; i++) {
      s = step(s, rules);
      offset -= PATLEN - 2;
    }
    int sum = 0;
    for (int i = 0; i < s.length(); i++) {
      sum += (s.charAt(i) == '#') ?  offset + i : 0;
    }
    return sum;
  }

  public long runPart2(String state, Map<String, String> rules) {
    String s = state;
    String ls = state;
    long itr = 0;
    long offset = 0;
    long lastOffset = 0;
    while (true) {
      ls = s;
      lastOffset = offset;
      s = step(s, rules);
      itr += 1;
      offset -= PATLEN - 2;
      if (trimPots(s).equals(trimPots(ls))) {
        break;
      }
    }
    long repeatShift = (s.indexOf('#') + offset) - (ls.indexOf('#') + lastOffset);
    long finalShift = (50000000000L - itr) * repeatShift;
    long sum = 0;
    for (int i = 0; i < s.length(); i++) {
      sum += (s.charAt(i) == '#')
             ? offset + i + finalShift
             : 0L;
    }
    return sum;
  }

  String trimPots(String input) {
    int leftPlant = input.indexOf('#');
    int rightPlant = input.lastIndexOf('#');
    return input.substring(leftPlant, rightPlant + 1);
  }
}

