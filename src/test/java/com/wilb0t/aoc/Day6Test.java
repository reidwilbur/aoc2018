package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

public class Day6Test {

  private Day6 testInst;

  private List<Day6.Coord> input1;
  private List<Day6.Coord> testInput1;

  @Before
  public void before() throws Exception {
    testInst = new Day6();

    input1 = Files.readLines(
        new File(Day6Test.class.getResource("/Day6_input1.txt").toURI()),
        Charsets.UTF_8
    ).stream().map(Day6.Coord::new).collect(Collectors.toList());

    testInput1 = Stream.of(
        "1, 1",
        "1, 6",
        "8, 3",
        "3, 4",
        "5, 5",
        "8, 9"
    ).map(Day6.Coord::new).collect(Collectors.toList());
  }

  @Test
  public void testGetMaxFiniteArea_case1() {
    assertThat(testInst.getMaxFiniteArea(testInput1), is(17L));
  }

  @Test
  public void testGetMaxFiniteArea_input1() {
    assertThat(testInst.getMaxFiniteArea(input1), is(4475L));
  }

  @Test
  public void testSafeArea_case1() {
    assertThat(testInst.getSafeArea(testInput1, 32), is(16L));
  }

  @Test
  public void testSafeArea_input1() {
    assertThat(testInst.getSafeArea(input1, 10000), is(35237L));
  }
}
