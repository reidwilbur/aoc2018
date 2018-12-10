package com.wilb0t.aoc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

public class Day10Test {

  private Day10 testInst;

  private List<Day10.Point> input1;
  private List<Day10.Point> testInput1;

  @Before
  public void before() throws Exception {
    input1 = Files.readLines(
        new File(Day7Test.class.getResource("/Day10_input1.txt").toURI()),
        Charsets.UTF_8
    ).stream().map(Day10.Point::new).collect(Collectors.toList());

    testInput1 = Stream.of(
        "position=< 9,  1> velocity=< 0,  2>",
        "position=< 7,  0> velocity=<-1,  0>",
        "position=< 3, -2> velocity=<-1,  1>",
        "position=< 6, 10> velocity=<-2, -1>",
        "position=< 2, -4> velocity=< 2,  2>",
        "position=<-6, 10> velocity=< 2, -2>",
        "position=< 1,  8> velocity=< 1, -1>",
        "position=< 1,  7> velocity=< 1,  0>",
        "position=<-3, 11> velocity=< 1, -2>",
        "position=< 7,  6> velocity=<-1, -1>",
        "position=<-2,  3> velocity=< 1,  0>",
        "position=<-4,  3> velocity=< 2,  0>",
        "position=<10, -3> velocity=<-1,  1>",
        "position=< 5, 11> velocity=< 1, -2>",
        "position=< 4,  7> velocity=< 0, -1>",
        "position=< 8, -2> velocity=< 0,  1>",
        "position=<15,  0> velocity=<-2,  0>",
        "position=< 1,  6> velocity=< 1,  0>",
        "position=< 8,  9> velocity=< 0, -1>",
        "position=< 3,  3> velocity=<-1,  1>",
        "position=< 0,  5> velocity=< 0, -1>",
        "position=<-2,  2> velocity=< 2,  0>",
        "position=< 5, -2> velocity=< 1,  2>",
        "position=< 1,  4> velocity=< 2,  1>",
        "position=<-2,  7> velocity=< 2, -2>",
        "position=< 3,  6> velocity=<-1, -1>",
        "position=< 5,  0> velocity=< 1,  0>",
        "position=<-6,  0> velocity=< 2,  0>",
        "position=< 5,  9> velocity=< 1, -2>",
        "position=<14,  7> velocity=<-2,  0>",
        "position=<-3,  6> velocity=< 2, -1>"
    ).map(Day10.Point::new).collect(Collectors.toList());
    testInst = new Day10();
  }

  @Test
  public void testRunPoints_case1() {
    Map.Entry<Integer, String> got = testInst.runPoints(testInput1, 1000000);
    assertThat(got.getKey(), is(3));
    System.out.println(got.getValue());
  }

  @Test
  public void testRunPoints() {
    Map.Entry<Integer, String> got = testInst.runPoints(input1, 1000000);
    assertThat(got.getKey(), is(10369));
    System.out.println(got.getValue());
  }
}
