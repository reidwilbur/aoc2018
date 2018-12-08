package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day8Test {

  private Day8 testInst;

  private static List<Integer> input1;
  private static List<Integer> testInput1;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Day8.parse(Files.readLines(
        new File(Day8Test.class.getResource("/Day8_input1.txt").toURI()),
        Charsets.UTF_8
    ).get(0));

    testInput1 = Day8.parse("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2");
  }

  @Before
  public void before() {
    testInst = new Day8();
  }

  @Test
  public void testGetMetadataSum_case1() {
    assertThat(testInst.getMetadataSum(testInput1), is(138));
  }

  @Test
  public void testGetMetadataSum_input1() {
    assertThat(testInst.getMetadataSum(input1), is(41454));
  }

  @Test
  public void testRootValue_case1() {
    assertThat(testInst.rootValue(testInput1), is(66));
  }

  @Test
  public void testRootValue_input1() {
    assertThat(testInst.rootValue(input1), is(25752));
  }
}
