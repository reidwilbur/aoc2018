package com.wilb0t.aoc;

import io.norberg.automatter.AutoMatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day4 {

  @AutoMatter
  public interface GuardLog {
    enum Action { BS, FA, WU }

    int minute();
    String time();
    Action action();
    Optional<Integer> guardId();
  }

  @AutoMatter
  public interface GuardMaxMinute {
    int minute();
    int guardId();
    int sleepCount();
  }

  public long getStrategy1Val(List<GuardLog> logs) {
    Map<Integer, int[]> guardSleepCounts = getSleepCounts(logs);

    Map.Entry<Integer, int[]> mostSleeps = guardSleepCounts.entrySet().stream()
        .max(Comparator.comparing(e -> Arrays.stream(e.getValue()).sum()))
        .orElseThrow(() -> new IllegalStateException("bad times"));

    GuardMaxMinute maxMinute = getGuardMaxMinute(mostSleeps);

    return maxMinute.guardId() * maxMinute.minute();
  }

  public long getStrategy2Val(List<GuardLog> logs) {
    Map<Integer, int[]> guardSleepCounts = getSleepCounts(logs);

    GuardMaxMinute maxMinute = guardSleepCounts.entrySet().stream()
        .map(Day4::getGuardMaxMinute)
        .max(Comparator.comparing(GuardMaxMinute::sleepCount))
        .orElseThrow(() -> new IllegalStateException("bad times"));

    return maxMinute.guardId() * maxMinute.minute();
  }

  private static Map<Integer, int[]> getSleepCounts(List<GuardLog> logs) {
    List<GuardLog> sortedLogs = logs.stream()
        .sorted(Comparator.comparing(GuardLog::time))
        .collect(Collectors.toList());

    Map<Integer, int[]> guardSleeps = new HashMap<>();

    int curId = -1;
    GuardLog lastLog = null;

    for (GuardLog curLog : sortedLogs) {
      if (curLog.guardId().isPresent()) {
        curId = curLog.guardId().get();
        if (!guardSleeps.containsKey(curId)) {
          guardSleeps.put(curId, new int[60]);
        }
        lastLog = curLog;
      } else if (curLog.action().equals(GuardLog.Action.FA)) {
        lastLog = curLog;
      } else if (curLog.action().equals(GuardLog.Action.WU)) {
        for (int minute = lastLog.minute(); minute < curLog.minute(); minute++) {
          guardSleeps.get(curId)[minute] += 1;
        }
        lastLog = curLog;
      }
    }

    return guardSleeps;
  }

  private static GuardMaxMinute getGuardMaxMinute(Map.Entry<Integer, int[]> sleepCounts) {
    int maxMinute = 0;
    for (int i = 1; i < sleepCounts.getValue().length; i++) {
      if (sleepCounts.getValue()[i] > sleepCounts.getValue()[maxMinute]) {
        maxMinute = i;
      }
    }
    return new GuardMaxMinuteBuilder()
        .guardId(sleepCounts.getKey())
        .minute(maxMinute)
        .sleepCount(sleepCounts.getValue()[maxMinute])
        .build();
  }

  public static GuardLog toLog(String line) {
    String time = line.substring(1, 17);
    int minute = Integer.parseInt(time.substring(time.length() - 2));

    String[] parts = line.split(" ");

    if (parts[2].equals("Guard")) {
      return new GuardLogBuilder()
          .time(time)
          .minute(minute)
          .action(GuardLog.Action.BS)
          .guardId(Integer.parseInt(parts[3].substring(1)))
          .build();
    }

    if (parts[2].equals("falls")) {
      return new GuardLogBuilder()
          .time(time)
          .minute(minute)
          .action(GuardLog.Action.FA)
          .build();
    }

    if (parts[2].equals("wakes")) {
      return new GuardLogBuilder()
          .time(time)
          .minute(minute)
          .action(GuardLog.Action.WU)
          .build();
    }

    throw new IllegalStateException("bad times");
  }
}
