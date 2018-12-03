package com.wilb0t.aoc;

import io.norberg.automatter.AutoMatter;
import java.util.Arrays;
import java.util.List;

public class Day3 {

  private static final int FABRIC_SIZE = 1000;
  private static final int EMPTY_VAL = 0;
  private static final int OVERLAP_VAL = -1;

  @AutoMatter
  public interface Claim {
    int id();
    int xOfs();
    int yOfs();
    int xSize();
    int ySize();
  }

  public long getOverlap(List<Claim> claims) {
    int[] fabric = new int[FABRIC_SIZE * FABRIC_SIZE];

    claims.forEach(c -> fillClaim(fabric, c));

    return Arrays.stream(fabric).filter(i -> i == OVERLAP_VAL).count();
  }

  void fillClaim(int[] fabric, Claim c) {
    int ofs = (c.yOfs() * FABRIC_SIZE) + c.xOfs();
    for (int y = 0; y < c.ySize(); y++) {
      for (int x = 0; x < c.xSize(); x++) {
        int fabidx = ofs + (y * FABRIC_SIZE) + x;
        fabric[fabidx] = fabric[fabidx] == EMPTY_VAL ? c.id() : OVERLAP_VAL;
      }
    }
  }

  public static Claim toClaim(String line) {
    String[] parts = line.split(" ");
    int id = Integer.valueOf(parts[0].substring(1));

    String[] ofs = parts[2].substring(0, parts[2].length() - 1).split(",");
    int xofs = Integer.valueOf(ofs[0]);
    int yofs = Integer.valueOf(ofs[1]);

    String[] szs = parts[3].split("x");
    int xs = Integer.valueOf(szs[0]);
    int ys = Integer.valueOf(szs[1]);

    return new ClaimBuilder()
        .id(id)
        .xOfs(xofs)
        .yOfs(yofs)
        .xSize(xs)
        .ySize(ys)
        .build();
  }
}
