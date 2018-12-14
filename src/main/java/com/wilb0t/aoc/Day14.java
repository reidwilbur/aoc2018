package com.wilb0t.aoc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {

  public List<Integer> toDigits(int i) {
    if (i > 99) {
      throw new IllegalStateException("too high");
    }
    int tens = i / 10;
    int ones = i % 10;
    return (tens > 0) ? Arrays.asList(tens, ones) : Collections.singletonList(ones);
  }

  public String recipeScore(int numRecipes) {
    List<Integer> scores = Stream.of(3, 7).collect(Collectors.toList());

    int elf1 = 0;
    int elf2 = 1;
    int itrs = numRecipes + 1 + 10;
    while (scores.size() < itrs) {
      int newScore = scores.get(elf1) + scores.get(elf2);

      scores.addAll(toDigits(newScore));

      elf1 = (elf1 + scores.get(elf1) + 1) % scores.size();
      elf2 = (elf2 + scores.get(elf2) + 1) % scores.size();
    }

    return scores.subList(numRecipes, itrs - 1).stream()
        .map(String::valueOf)
        .collect(Collectors.joining(""));
  }

  public Optional<Integer> scoreboardMatches(List<Integer> end, List<Integer> scoreboard) {
    if (scoreboard.size() >= end.size()
        && scoreboard.subList(scoreboard.size() - end.size(), scoreboard.size()).equals(end)) {

      return Optional.of(scoreboard.size() - end.size());

    } else if (scoreboard.size() - 1 >= end.size()
               && scoreboard.subList(scoreboard.size() - 1 - end.size(), scoreboard.size() - 1).equals(end)) {
      return Optional.of(scoreboard.size() - 1 - end.size());

    }
    return Optional.empty();
  }

  public int recipeCount(String score) {
    List<Integer> scores = Stream.of(3, 7).collect(Collectors.toList());

    List<Integer> end = Arrays.stream(score.split(""))
        .map(Integer::valueOf)
        .collect(Collectors.toList());

    int elf1 = 0;
    int elf2 = 1;
    while (true) {
      int newScore = scores.get(elf1) + scores.get(elf2);

      scores.addAll(toDigits(newScore));

      elf1 = (elf1 + scores.get(elf1) + 1) % scores.size();
      elf2 = (elf2 + scores.get(elf2) + 1) % scores.size();

      Optional<Integer> countOpt = scoreboardMatches(end, scores);
      if (countOpt.isPresent()) {
        return countOpt.get();
      }
    }
  }
}

