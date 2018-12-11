package com.wilb0t.aoc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class Day11Test {

  private Day11 testInst;

  @Before
  public void before() throws Exception {
    testInst = new Day11();
  }

  @Test
  public void testGetPowerLevel_case1() {
    assertThat(testInst.getPowerLevel(8, 3, 5), is(4));
  }

  @Test
  public void testGetPowerLevel_case2() {
    assertThat(testInst.getPowerLevel(57, 122, 79), is(-5));
  }

  @Test
  public void testGetPowerLevel_case3() {
    assertThat(testInst.getPowerLevel(39, 217, 196), is(0));
  }

  @Test
  public void testGetLargestCellPower_case1() {
    assertThat(testInst.getLargestCellPower(18), is("33,45"));
  }

  @Test
  public void testGetLargestCellPower_input1() {
    assertThat(testInst.getLargestCellPower(6042), is("21,61"));
  }

  //@Test
  // this takes a couple seconds to run
  public void testGetLargestCellPowerAllSizes_case1() {
    assertThat(testInst.getLargestCellPowerAllSizes(18), is("90,269,16"));
  }

  //@Test
  // this takes a couple seconds to run
  public void testGetLargestCellPower_input2() {
    assertThat(testInst.getLargestCellPowerAllSizes(6042), is("232,251,12"));
  }
}
