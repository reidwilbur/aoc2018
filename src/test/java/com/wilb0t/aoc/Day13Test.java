package com.wilb0t.aoc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day13Test {

  private Day13 testInst;

  private static List<String> input1;
  private static List<String> testInput1;
  private static List<String> testInput2;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Files.readLines(
        new File(Day13Test.class.getResource("/Day13_input.txt").toURI()),
        Charsets.UTF_8
    );

    testInput1 = Stream.of(
        "/->-\\        ",
        "|   |  /----\\",
        "| /-+--+-\\  |",
        "| | |  | v  |",
        "\\-+-/  \\-+--/",
        "  \\------/   "
    ).collect(Collectors.toList());

    testInput2 = Stream.of(
        "/>-<\\  ",
        "|   |  ",
        "| /<+-\\",
        "| | | v",
        "\\>+</ |",
        "  |   ^",
        "  \\<->/"
    ).collect(Collectors.toList());
  }

  @Before
  public void before() {
    testInst = new Day13();
  }

  @Test
  public void testFirstCrash_case1() {
    assertThat(testInst.firstCrash(testInput1), is(new PosBuilder().x(7).y(3).build()));
  }

  @Test
  public void testFirstCrash_input1() {
   assertThat(testInst.firstCrash(input1), is(new PosBuilder().x(143).y(43).build()));
  }

  @Test
  public void testLastCart_case1() {
    assertThat(testInst.lastCart(testInput2), is(new PosBuilder().x(6).y(4).build()));
  }

  @Test
  public void testLastCart_input1() {
    assertThat(testInst.lastCart(input1), is(new PosBuilder().x(116).y(125).build()));
  }
}
