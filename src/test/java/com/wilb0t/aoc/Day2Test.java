package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import java.io.File;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day2Test {

  private Day2 testInst;

  private static List<String> input1;

  private static List<String> testInput1 = ImmutableList.of(
      "abcdef",
      "bababc",
      "abbcde",
      "abcccd",
      "aabcdd",
      "abcdee",
      "ababab"
  );

  private static List<String> testInput2 = ImmutableList.of(
      "abcde",
      "fghij",
      "klmno",
      "pqrst",
      "fguij",
      "axcye",
      "wvxyz"
  );

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Files.readLines(
        new File(Day2Test.class.getResource("/Day2_input1.txt").toURI()),
        Charsets.UTF_8
    );
  }

  @Before
  public void setup() {
    testInst = new Day2();
  }

  @Test
  public void testGetChecksum_case1() {
    assertThat(testInst.getChecksum(testInput1), is(12L));
  }

  @Test
  public void testGetChecksum_input1() {
    assertThat(testInst.getChecksum(input1), is(6150L));
  }

  @Test
  public void testGetMaxCommonChars_case1() {
    assertThat(testInst.getMaxCommonChars(testInput2), is("fgij"));
  }

  @Test
  public void testGetMaxCommonChars_input1() {
    assertThat(testInst.getMaxCommonChars(input1), is("rteotyxzbodglnpkudawhijsc"));
  }
}
