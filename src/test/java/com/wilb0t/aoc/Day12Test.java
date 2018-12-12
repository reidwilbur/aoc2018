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
import org.junit.BeforeClass;
import org.junit.Test;

public class Day12Test {

  private Day12 testInst;

  private static String input1;
  private static Map<String, String> rules;

  private static String testInput1;
  private static Map<String, String> testRules;

  @BeforeClass
  public static void beforeClass() throws Exception {
    List<String> input1Lines = Files.readLines(
        new File(Day12Test.class.getResource("/Day12_input.txt").toURI()),
        Charsets.UTF_8
    );
    input1 = input1Lines.get(0).substring(15);
    rules = Day12.parseRules(input1Lines);

    List<String> testLines = Stream.of(
        "initial state: #..#.#..##......###...###",
        "",
        "...## => #",
        "..#.. => #",
        ".#... => #",
        ".#.#. => #",
        ".#.## => #",
        ".##.. => #",
        ".#### => #",
        "#.#.# => #",
        "#.### => #",
        "##.#. => #",
        "##.## => #",
        "###.. => #",
        "###.# => #",
        "####. => #"
    ).collect(Collectors.toList());
    testInput1 = testLines.get(0).substring(15);
    testRules = Day12.parseRules(testLines);
  }

  @Before
  public void before() {
    testInst = new Day12();
  }

  @Test
  public void testRunPart1_case1() {
    assertThat(testInst.runPart1(testInput1, testRules), is(325));
  }

  @Test
  public void testRunPart1_input1() {
    assertThat(testInst.runPart1(input1, rules), is(2736));
  }

  @Test
  public void testRunPart2_input1() {
    assertThat(testInst.runPart2(input1, rules), is(3150000000905L));
  }
}
