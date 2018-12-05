package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day5Test {

  private Day5 testInst;

  private static String input1;
  private static String testInput1 = "dabAcCaCBAcCcaDA";

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Files.readLines(
        new File(Day5Test.class.getResource("/Day5_input1.txt").toURI()),
        Charsets.UTF_8
    ).get(0);
  }

  @Before
  public void setup() {
    testInst = new Day5();
  }

  @Test
  public void testReact_case1() {
    assertThat(testInst.react(testInput1), is(10));
  }

  @Test
  public void testReact_input1() {
    assertThat(testInst.react(input1), is(11946));
  }

  @Test
  public void testReactReduce_case1() {
    assertThat(testInst.reactReduce(testInput1), is(4));
  }

  @Test
  public void testReactReduce_input1() {
    assertThat(testInst.reactReduce(input1), is(4240));
  }

  @Test
  public void testDoesReact() {
    assertTrue(testInst.doesReact('a', 'A'));
    assertTrue(testInst.doesReact('A', 'a'));
    assertTrue(testInst.doesReact('z', 'Z'));
    assertTrue(testInst.doesReact('Z', 'z'));

    assertFalse(testInst.doesReact('a', 'a'));
    assertFalse(testInst.doesReact('A', 'A'));
    assertFalse(testInst.doesReact('a', 'B'));
    assertFalse(testInst.doesReact('B', 'a'));
  }
}
