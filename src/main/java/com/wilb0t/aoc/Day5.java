package com.wilb0t.aoc;

import java.util.Comparator;
import java.util.stream.IntStream;

public class Day5 {

  public int react(String polymer) {
    int len = polymer.length();
    int lIdx = 0;
    int rIdx = 1;
    StringBuilder polyBldr = new StringBuilder(polymer);
    while (rIdx < polyBldr.length()) {
      if (doesReact(polyBldr.charAt(lIdx), polyBldr.charAt(rIdx))) {
        polyBldr.setCharAt(lIdx, '0');
        polyBldr.setCharAt(rIdx, '0');
        len -= 2;
        rIdx += 1;
        lIdx = getLeftIdx(polyBldr, lIdx, rIdx);
        if (lIdx == rIdx) {
          rIdx = lIdx + 1;
        }
      } else {
        lIdx = rIdx;
        rIdx += 1;
      }
    }
    return len;
  }

  private int getLeftIdx(StringBuilder polyBldr, int lIdx, int rIdx) {
    for (int i = lIdx - 1; i >= 0; i--) {
      if (polyBldr.charAt(i) != '0') {
        return i;
      }
    }
    return rIdx;
  }

  boolean doesReact(char left, char right) {
    return left + 32 == right || left - 32 == right;
  }

  public int reactReduce(String polymer) {
    return IntStream.range(0, 26)
        .map(i -> i + 'a')
        .mapToObj(i -> (char) i)
        .map(c -> "[" + c + Character.toUpperCase(c) +  "]")
        .map(pattern -> polymer.replaceAll(pattern, ""))
        .map(this::react)
        .min(Comparator.comparing(l -> l))
        .orElseThrow(() -> new IllegalStateException("bad times"));
  }
}

/*

**
dabAcCaCBAcCcaDA
 **
dabAcCaCBAcCcaDA
  **
dabAcCaCBAcCcaDA
   **
dabAcCaCBAcCcaDA
    **
dabAcCaCBAcCcaDA
   *  *
dabA00aCBAcCcaDA
  *    *
dab0000CBAcCcaDA
       **
dab0000CBAcCcaDA
        **
dab0000CBAcCcaDA
         **
dab0000CBAcCcaDA
          **
dab0000CBAcCcaDA
         *  *
dab0000CBA00caDA
            **
dab0000CBA00caDA
             **
dab0000CBA00caDA
              **
dab0000CBA00caDA


**
aAbBcCaCBAcCcDA
**
00bBcCaCBAcCcDA
  **
00bBcCaCBAcCcDA
  * *
0000cCaCBAcCcDA

**
UjVvhHOKkRrOooNpxXPnJjJnGgNyY
 **
UjVvhHOKkRrOooNpxXPnJjJnGgNyY
  **
Uj00hHOKkRrOooNpxXPnJjJnGgNyY
 *  *
Uj00hHOKkRrOooNpxXPnJjJnGgNyY
    **
Uj00hHOKkRrOooNpxXPnJjJnGgNyY
 *    *
Uj0000OKkRrOooNpxXPnJjJnGgNyY
      **
Uj0000OKkRrOooNpxXPnJjJnGgNyY
       **
Uj0000OKkRrOooNpxXPnJjJnGgNyY
      *  *
Uj0000O00RrOooNpxXPnJjJnGgNyY
         **
Uj0000O00RrOooNpxXPnJjJnGgNyY
      *    *
Uj0000O0000OooNpxXPnJjJnGgNyY
           **
Uj0000O0000OooNpxXPnJjJnGgNyY
      *      *
Uj0000O000000oNpxXPnJjJnGgNyY
 *            *
Uj000000000000NpxXPnJjJnGgNyY

 */

