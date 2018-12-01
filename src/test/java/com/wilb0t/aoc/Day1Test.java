package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day1Test {

  private Day1 testInst;

  private static List<Integer> input1;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Files.readLines(
        new File(Day1Test.class.getResource("/Day1_input1.txt").toURI()),
        Charsets.UTF_8
    ).stream().map(Integer::new).collect(Collectors.toList());
  }

  @Before
  public void setup() {
    testInst = new Day1();
  }

  @Test
  public void testGetFrequency_case1() {
    assertThat(testInst.getFrequency(ImmutableList.of(1, 1, 1)), is(3));
  }

  @Test
  public void testGetFrequency_case2() {
    assertThat(testInst.getFrequency(ImmutableList.of(1, 1, -2)), is(0));
  }

  @Test
  public void testGetFrequency_case3() {
    assertThat(testInst.getFrequency(ImmutableList.of(-1, -2, -3)), is(-6));
  }

  @Test
  public void testGetFrequency_input1() {
    assertThat(testInst.getFrequency(input1), is(574));
  }

  @Test
  public void testGetFirstRepFreq_case1() {
    assertThat(testInst.getFirstRepFreq(ImmutableList.of(1, -1)), is(0L));
  }

  @Test
  public void testGetFirstRepFreq_case2() {
    assertThat(testInst.getFirstRepFreq(ImmutableList.of(+3, +3, +4, -2, -4)), is(10L));
  }

  @Test
  public void testGetFirstRepFreq_case3() {
    assertThat(testInst.getFirstRepFreq(ImmutableList.of(-6, +3, +8, +5, -6)), is(5L));
  }

  @Test
  public void testGetFirstRepFreq_case4() {
    assertThat(testInst.getFirstRepFreq(ImmutableList.of(+7, +7, -2, -7, -4)), is(14L));
  }

  @Test
  public void testGetFirstRepFreq_input1() {
    assertThat(testInst.getFirstRepFreq(input1), is(452L));
  }
}
