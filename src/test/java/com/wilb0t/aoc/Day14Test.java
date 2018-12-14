package com.wilb0t.aoc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day14Test {

  private Day14 testInst;

  private static int input1 = 409551;

  @BeforeClass
  public static void beforeClass() throws Exception {
  }

  @Before
  public void before() {
    testInst = new Day14();
  }

  @Test
  public void testRecipeScore_case1() {
    assertThat(testInst.recipeScore(9), is("5158916779"));
  }

  @Test
  public void testRecipeScore_case2() {
    assertThat(testInst.recipeScore(5), is("0124515891"));
  }

  @Test
  public void testRecipeScore_case3() {
    assertThat(testInst.recipeScore(18), is("9251071085"));
  }

  @Test
  public void testRecipeScore_case4() {
    assertThat(testInst.recipeScore(2018), is("5941429882"));
  }

  @Test
  public void testRecipeScore_input1() {
    assertThat(testInst.recipeScore(input1), is("1631191756"));
  }

  @Test
  public void testRecipeCount_case1() {
    assertThat(testInst.recipeCount("51589"), is(9));
  }

  @Test
  public void testRecipeCount_case2() {
    assertThat(testInst.recipeCount("01245"), is(5));
  }

  @Test
  public void testRecipeCount_case3() {
    assertThat(testInst.recipeCount("92510"), is(18));
  }

  @Test
  public void testRecipeCount_case4() {
    assertThat(testInst.recipeCount("59414"), is(2018));
  }

  // @Test
  // takes 3s to run
  public void testRecipeCount_input1() {
    assertThat(testInst.recipeCount("409551"), is(20219475));
  }
}
