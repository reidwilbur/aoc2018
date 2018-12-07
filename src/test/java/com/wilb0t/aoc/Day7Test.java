package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;
import java.io.File;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Day7Test {

  private Day7 testInst;

  private static Map<Character, Set<Character>> input1;
  private static Map<Character, Set<Character>> testInput1;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Day7.toSteps(Files.readLines(
        new File(Day7Test.class.getResource("/Day7_input1.txt").toURI()),
        Charsets.UTF_8
    ));

    testInput1 = Day7.toSteps(ImmutableList.of(
        "Step C must be finished before step A can begin.",
        "Step C must be finished before step F can begin.",
        "Step A must be finished before step B can begin.",
        "Step A must be finished before step D can begin.",
        "Step B must be finished before step E can begin.",
        "Step D must be finished before step E can begin.",
        "Step F must be finished before step E can begin."
    ));
  }

  @Before
  public void before() {
    testInst = new Day7();
  }

  @Test
  public void testToSteps() {
    assertThat(testInput1, is(
        ImmutableMap.<Character, Set<Character>>builder()
            .put('A', ImmutableSet.of('C'))
            .put('B', ImmutableSet.of('A'))
            .put('C', ImmutableSet.of())
            .put('D', ImmutableSet.of('A'))
            .put('E', ImmutableSet.of('B', 'D', 'F'))
            .put('F', ImmutableSet.of('C'))
            .build()
        )
    );
  }

  @Test
  public void testTopoSort_case1() {
    assertThat(testInst.topoSort(testInput1), is("CABDFE"));
  }

  @Test
  public void testTopoSort_input1() {
    assertThat(testInst.topoSort(input1), is("LAPFCRGHVZOTKWENBXIMSUDJQY"));
  }

  @Test
  public void testTopoSortWorkers_case1() {
    assertThat(testInst.topoSortWorkers(testInput1, 2, 0), is(15));
  }

  @Test
  public void testTopoSortWorkers_input1() {
    assertThat(testInst.topoSortWorkers(input1, 5, 60), is(936));
  }
}
