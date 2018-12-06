package com.wilb0t.aoc;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day6 {

  public static class Coord {
    private final int x;
    private final int y;

    private long area;

    public Coord(String line) {
      String[] parts = line.split(", ");
      this.x = Integer.parseInt(parts[0]);
      this.y = Integer.parseInt(parts[1]);
      this.area = 0;
    }

    int x() {
      return x;
    }

    int y() {
      return y;
    }

    void incArea() {
      area += 1;
    }

    long area() {
      return area;
    }

    int dist(int x, int y) {
      return Math.abs(this.x - x) + Math.abs(this.y - y);
    }
  }

  public long getMaxFiniteArea(List<Coord> coords) {
    int minx = coords.stream().mapToInt(Coord::x).min().getAsInt();
    int maxx = coords.stream().mapToInt(Coord::x).max().getAsInt();

    int miny = coords.stream().mapToInt(Coord::y).min().getAsInt();
    int maxy = coords.stream().mapToInt(Coord::y).max().getAsInt();

    IntStream ys = IntStream.range(miny, maxy + 1);
    IntStream xs = IntStream.range(minx, maxx + 1);

    IntStream.range(miny, maxy + 1).forEach(
        y -> IntStream.range(minx, maxx + 1).forEach(
            x -> {
              List<Coord> closest = coords.stream()
                  .sorted(Comparator.comparingInt(c -> c.dist(x, y)))
                  .limit(2)
                  .collect(Collectors.toList());

              if (closest.get(0).dist(x, y) != closest.get(1).dist(x, y)) {
                closest.get(0).incArea();
              }
            })
    );

    return coords.stream()
        .mapToLong(Coord::area)
        .max()
        .getAsLong();
  }

  public long getSafeArea(List<Coord> coords, long maxDist) {
    int minx = coords.stream().mapToInt(Coord::x).min().getAsInt();
    int maxx = coords.stream().mapToInt(Coord::x).max().getAsInt();

    int miny = coords.stream().mapToInt(Coord::y).min().getAsInt();
    int maxy = coords.stream().mapToInt(Coord::y).max().getAsInt();

    return IntStream.range(miny, maxy + 1).reduce(
        0,
        (area, y) -> IntStream.range(minx, maxx + 1).reduce(
            area,
            (rowArea, x) -> {
              long posDist = coords.stream()
                  .mapToLong(c -> c.dist(x, y))
                  .sum();

              if (posDist < maxDist) {
                return rowArea + 1;
              } else {
                return rowArea;
              }
            })
    );
  }
}

