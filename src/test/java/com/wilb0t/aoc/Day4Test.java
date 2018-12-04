package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
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

public class Day4Test {

  private Day4 testInst;

  private static List<Day4.GuardLog> input1;
  private static List<Day4.GuardLog> testInput1;

  @BeforeClass
  public static void beforeClass() throws Exception {
    input1 = Files.readLines(
        new File(Day4Test.class.getResource("/Day4_input1.txt").toURI()),
        Charsets.UTF_8
    ).stream().map(Day4::toLog).collect(Collectors.toList());

    testInput1 = Stream.of(
        "[1518-11-01 00:00] Guard #10 begins shift",
        "[1518-11-01 00:05] falls asleep",
        "[1518-11-01 00:25] wakes up",
        "[1518-11-01 00:30] falls asleep",
        "[1518-11-01 00:55] wakes up",
        "[1518-11-01 23:58] Guard #99 begins shift",
        "[1518-11-02 00:40] falls asleep",
        "[1518-11-02 00:50] wakes up",
        "[1518-11-03 00:05] Guard #10 begins shift",
        "[1518-11-03 00:24] falls asleep",
        "[1518-11-03 00:29] wakes up",
        "[1518-11-04 00:02] Guard #99 begins shift",
        "[1518-11-04 00:36] falls asleep",
        "[1518-11-04 00:46] wakes up",
        "[1518-11-05 00:03] Guard #99 begins shift",
        "[1518-11-05 00:45] falls asleep",
        "[1518-11-05 00:55] wakes up"
    ).map(Day4::toLog).collect(Collectors.toList());
  }

  @Before
  public void setup() {
    testInst = new Day4();
  }

  @Test
  public void testToLog_case1() {
    Day4.GuardLog guardLog = new GuardLogBuilder()
        .time("1518-11-01 00:00")
        .guardId(10)
        .action(Day4.GuardLog.Action.BS)
        .build();
    assertThat(Day4.toLog("[1518-11-01 00:00] Guard #10 begins shift"), is(guardLog));
  }

  @Test
  public void testToLog_case2() {
    Day4.GuardLog guardLog = new GuardLogBuilder()
        .time("1518-11-01 00:00")
        .action(Day4.GuardLog.Action.WU)
        .build();
    assertThat(Day4.toLog("[1518-11-01 00:00] wakes up"), is(guardLog));
  }

  @Test
  public void testToLog_case3() {
    Day4.GuardLog guardLog = new GuardLogBuilder()
        .time("1518-11-01 00:00")
        .action(Day4.GuardLog.Action.FA)
        .build();
    assertThat(Day4.toLog("[1518-11-01 00:00] falls asleep"), is(guardLog));
  }

  @Test
  public void testGetStrategy1Val_case1() {
    assertThat(testInst.getStrategy1Val(testInput1), is(240L));
  }

  @Test
  public void testGetStrategy1Val_input1() {
    assertThat(testInst.getStrategy1Val(input1), is(140932L));
  }

  @Test
  public void testGetStrategy2Val_case1() {
    assertThat(testInst.getStrategy2Val(testInput1), is(4455L));
  }

  @Test
  public void testGetStrategy2Val_input1() {
    assertThat(testInst.getStrategy2Val(input1), is(51232L));
  }
}
