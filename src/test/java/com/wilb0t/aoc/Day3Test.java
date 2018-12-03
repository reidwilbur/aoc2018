package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day3Test {

  private Day3 testInst;

  private static List<Day3.Claim> input1;

  private static List<Day3.Claim> testInput1 = Stream.of(
      "#1 @ 1,3: 4x4",
      "#2 @ 3,1: 4x4",
      "#3 @ 5,5: 2x2"
  ).map(Day3::toClaim).collect(Collectors.toList());

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Files.readLines(
        new File(Day3Test.class.getResource("/Day3_input1.txt").toURI()),
        Charsets.UTF_8
    ).stream().map(Day3::toClaim).collect(Collectors.toList());
  }

  @Before
  public void setup() {
    testInst = new Day3();
  }

  @Test
  public void testToClaim() {
    Day3.Claim claim = new ClaimBuilder()
        .id(123)
        .xOfs(3)
        .yOfs(2)
        .xSize(5)
        .ySize(4)
        .build();

    assertThat(Day3.toClaim("#123 @ 3,2: 5x4"), is(claim));
  }

  @Test
  public void testGetOverlap_case1() {
    assertThat(testInst.getOverlap(testInput1), is(4L));
  }

  @Test
  public void testGetOverlap_input1() {
    assertThat(testInst.getOverlap(input1), is(109785L));
  }

  @Test
  public void testGetClaimWithNoOverlap_case1() {
    assertThat(testInst.getClaimWithNoOverlap(testInput1), is(3L));
  }

  @Test
  public void testGetClaimWithNoOverlap_input1() {
    assertThat(testInst.getClaimWithNoOverlap(input1), is(504L));
  }
}
