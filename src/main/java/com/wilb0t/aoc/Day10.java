package com.wilb0t.aoc;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 {

  public static class Point {
    private int x, y;
    private int vx, vy;

    public Point(String line) {
      String[] parts = line.split("[<>]");

      String[] posParts = parts[1].split(",");
      x = Integer.valueOf(posParts[0].trim());
      y = Integer.valueOf(posParts[1].trim());

      String[] velParts = parts[3].split(",");
      vx = Integer.valueOf(velParts[0].trim());
      vy = Integer.valueOf(velParts[1].trim());
    }

    public int x() {
      return x;
    }

    public int y() {
      return y;
    }

    public void update() {
      x += vx;
      y += vy;
    }
  }

  public String toString(List<Point> points) {
    IntSummaryStatistics xstats = points.stream().mapToInt(Point::x).summaryStatistics();
    IntSummaryStatistics ystats = points.stream().mapToInt(Point::y).summaryStatistics();
    int lines = ystats.getMax() - ystats.getMin() + 1;
    int cols = xstats.getMax() - xstats.getMin() + 1;

    String clear = String.join("", Collections.nCopies(cols, "."));
    List<StringBuilder> bldrs = IntStream.range(0, lines)
        .mapToObj(i -> new StringBuilder(clear))
        .collect(Collectors.toList());

    points.forEach(p ->
        bldrs.get(p.y - ystats.getMin()).setCharAt(p.x - xstats.getMin(), '#')
    );
    return String.join("\n", bldrs);
  }

  boolean pointsAligned(List<Point> points) {
    Set<Map.Entry<Integer, Integer>> coords = points.stream()
        .map(p -> new AbstractMap.SimpleEntry<>(p.x, p.y))
        .collect(Collectors.toSet());

    return points.stream()
        .allMatch(p ->
            coords.contains(new AbstractMap.SimpleEntry<>(p.x + 1, p.y))
            || coords.contains(new AbstractMap.SimpleEntry<>(p.x - 1, p.y))
            || coords.contains(new AbstractMap.SimpleEntry<>(p.x, p.y + 1))
            || coords.contains(new AbstractMap.SimpleEntry<>(p.x, (p.y - 1)))
            || coords.contains(new AbstractMap.SimpleEntry<>((p.x - 1), (p.y - 1)))
            || coords.contains(new AbstractMap.SimpleEntry<>((p.x + 1), (p.y - 1)))
            || coords.contains(new AbstractMap.SimpleEntry<>((p.x + 1), (p.y + 1)))
            || coords.contains(new AbstractMap.SimpleEntry<>((p.x - 1), (p.y + 1)))
        );
  }

  Map.Entry<Integer, String> runPoints(List<Point> points, int itrs) {
    for(int i = 0; i < itrs; i++) {
      points.forEach(Point::update);
      if (pointsAligned(points)) {
        return new AbstractMap.SimpleEntry<>(i + 1, toString(points));
      }
    }
    return new AbstractMap.SimpleEntry<>(0, "");
  }
}
