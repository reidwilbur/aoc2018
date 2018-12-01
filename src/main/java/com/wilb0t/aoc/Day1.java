package com.wilb0t.aoc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day1 {

  int getFrequency(List<Integer> adjs) {
    return adjs.stream().reduce(0, (o, n) -> o + n);
  }

  long getFirstRepFreq(List<Integer> adjs) {
    Set<Long> seen = new HashSet<>();

    long freq = 0;
    int idx = 0;
    while(!seen.contains(freq)) {
      seen.add(freq);
      freq += adjs.get(idx);
      idx = (idx + 1) % adjs.size();
    }
    return freq;
  }
}
