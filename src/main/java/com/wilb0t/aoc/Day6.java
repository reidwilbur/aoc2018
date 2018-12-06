package com.wilb0t.aoc;

import io.norberg.automatter.AutoMatter;
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
    int minx = coords.stream()
        .map(Coord::x)
        .min(Comparator.naturalOrder())
        .orElseThrow(() -> new IllegalStateException("no min x"));
    int miny = coords.stream()
        .map(Coord::y)
        .min(Comparator.naturalOrder())
        .orElseThrow(() -> new IllegalStateException("no min y"));
    int maxx = coords.stream()
        .map(Coord::x)
        .max(Comparator.naturalOrder())
        .orElseThrow(() -> new IllegalStateException("no max x"));
    int maxy = coords.stream()
        .map(Coord::y)
        .max(Comparator.naturalOrder())
        .orElseThrow(() -> new IllegalStateException("no max y"));

    for (int y = miny; y <= maxy; y++) {
      for (int x = minx; x <= maxx; x++) {
        int tx = x; int ty = y;
        List<Coord> closest = coords.stream()
            .sorted(Comparator.comparingInt(c -> c.dist(tx, ty)))
            .limit(2)
            .collect(Collectors.toList());

        if (closest.get(0).dist(tx, ty) != closest.get(1).dist(tx, ty)) {
          closest.get(0).incArea();
        }
      }
    }

    return coords.stream()
        .map(Coord::area)
        .max(Comparator.naturalOrder())
        .orElseThrow(() -> new IllegalStateException("no max area"));
  }
}

