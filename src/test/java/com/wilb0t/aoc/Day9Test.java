package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class Day9Test {

  private Day9 testInst;

  @Before
  public void before() {
    testInst = new Day9();
  }

  @Test
  public void testGetHighSchore_case1() {
    assertThat(testInst.getHighScore(9, 25), is(32L));
  }

  @Test
  public void testGetHighSchore_case2() {
    assertThat(testInst.getHighScore(10, 1618), is(8317L));
  }

  @Test
  public void testGetHighSchore_case4() {
    assertThat(testInst.getHighScore(17, 1104), is(2764L));
  }

  @Test
  public void testGetHighSchore_input1() {
    assertThat(testInst.getHighScore(439, 71307), is(410375L));
  }

  @Test
  public void testGetHighSchore_input2() {
    assertThat(testInst.getHighScore(439, 7130700), is(3314195047L));
  }
}
