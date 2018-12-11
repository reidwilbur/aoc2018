package com.wilb0t.aoc;

public class Day11 {

  private static final int GSIZE = 300;

  public int getPowerLevel(int snum, int x, int y) {
    int rackId = x + 10;
    int tmp = (((rackId * y) + snum) * rackId);
    return ((tmp / 100) - ((tmp / 1000) * 10)) - 5;
  }

  public static class Grid {
    private final int[] data;
    private final int size;

    public Grid(int size) {
      this.size = size;
      this.data = new int[size * size];
    }

    public void set(int x, int y, int val) {
      data[(y * size) + x] = val;
    }

    public int get(int x, int y) {
      return data[(y * size) + x];
    }

    @Override
    public String toString() {
      StringBuilder bldr = new StringBuilder();
      for (int y = 0; y < size; y++) {
        for (int x = 0; x < size; x++) {
          bldr.append(String.format("%1$3d", get(x, y)));
        }
        bldr.append("\n");
      }
      return bldr.toString();
    }
  }

  public String getLargestCellPower(int snum) {
    Grid power = new Grid(GSIZE);

    for (int y = 0; y < GSIZE; y++) {
      for (int x = 0; x < GSIZE; x++) {
        int cellPower = getPowerLevel(snum, x, y);
        power.set(x, y, cellPower);
      }
    }

    String maxLabel = "";
    int maxPower = Integer.MIN_VALUE;
    for (int y = 0; y < GSIZE - 3; y++) {
      for (int x = 0; x < GSIZE - 3; x++) {
        int gp = 0;
        for (int sy = 0; sy < 3; sy++) {
          for (int sx = 0; sx < 3; sx++) {
            gp += power.get(x + sx, y + sy);
          }
        }
        if (gp > maxPower) {
          maxPower = gp;
          maxLabel = x + "," + y;
        }
      }
    }
    return maxLabel;
  }

  public String getLargestCellPowerAllSizes(int snum) {
    Grid power = new Grid(GSIZE);
    Grid gridPowers = new Grid(GSIZE);

    for (int y = 0; y < GSIZE; y++) {
      for (int x = 0; x < GSIZE; x++) {
        int cellPower = getPowerLevel(snum, x, y);
        power.set(x, y, cellPower);
        gridPowers.set(x, y, cellPower);
      }
    }

    String maxLabel = "";
    int maxPower = Integer.MIN_VALUE;
    for (int s = 2; s <= GSIZE; s++) {
      int newSize = GSIZE - s + 1;
      Grid newGridPowers = new Grid(newSize);
      for (int y = 0; y < newSize; y++) {
        for (int x = 0; x < newSize; x++) {
          int gp = gridPowers.get(x, y);
          for (int sx = 0; sx < s; sx++) {
            gp += power.get(x + sx, y + s - 1);
          }
          for (int sy = 0; sy < s - 1; sy++) {
            gp += power.get(x + s - 1, y + sy);
          }
          newGridPowers.set(x, y, gp);
          if (gp > maxPower) {
            maxPower = gp;
            maxLabel = x + "," + y + "," + s;
          }
        }
      }
      gridPowers = newGridPowers;
    }

    return maxLabel;
  }
}
