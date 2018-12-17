package com.wilb0t.aoc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day16Test {

  private Day16 testInst;

  private static List<Day16.Sample> input1;
  private static Day16.Sample testSample;

  private static List<Day16.Instr> input2;

  @BeforeClass
  public static void beforeClass() throws Exception {
    List<String> lines = Files.readLines(
        new File(Day7Test.class.getResource("/Day16_input1.txt").toURI()),
        Charsets.UTF_8
    );

    input1 = new ArrayList<>();
    for (int i = 0; i < lines.size() - 4; i += 4) {
      input1.add(Day16.Sample.parse(lines.subList(i, i + 4)));
    }

    testSample = Day16.Sample.parse(ImmutableList.of(
        "Before: [3, 2, 1, 1]",
        "9 2 1 2",
        "After:  [3, 2, 2, 1]"
    ));

    input2 = Files.readLines(
        new File(Day7Test.class.getResource("/Day16_input2.txt").toURI()),
        Charsets.UTF_8
    ).stream().map(Day16.Instr::parse).collect(Collectors.toList());
  }

  @Before
  public void before() {
    testInst = new Day16();
  }

  @Test
  public void testOpMatches() {
    Set<String> opMatches = Day16.operations.entrySet().stream()
        .flatMap(e -> (testSample.opMatches(e.getValue())) ? Stream.of(e.getKey()) : Stream.empty())
        .collect(Collectors.toSet());

    assertThat(opMatches, is(ImmutableSet.of("mulr", "addi", "seti")));
  }

  @Test
  public void testSamesMatchThree_input1() {
    assertThat(testInst.samplesMatchAtLeastThree(input1), is(544L));
  }

  @Test
  public void testMatchInstrsToOps() {
    Map<Integer, String> codesToNames = testInst.matchOpCodesToNames(input1);

    assertThat(codesToNames.size(), is(Day16.operations.size()));
  }

  @Test
  public void testExec_input2() {
    assertThat(testInst.exec(input1, input2), is(600));
  }
}
