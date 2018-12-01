package com.wilb0t.aoc;

import java.util.List;

public class Day1 {

  int getFrequency(List<Integer> adjs) {
    return adjs.stream().reduce(0, (o, n) -> o + n);
  }
}
