package com.wilb0t.aoc;

import com.google.common.collect.ImmutableSet;
import io.norberg.automatter.AutoMatter;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day7 {

  public static Map<Character, Set<Character>> toSteps(List<String> lines) {
    return lines.stream()
        .flatMap(l -> {
          String[] parts = l.split(" ");
          return Stream.<Map.Entry<Character, Set<Character>>>of(
              new AbstractMap.SimpleEntry<>(parts[7].charAt(0), ImmutableSet.of(parts[1].charAt(0))),
              new AbstractMap.SimpleEntry<>(parts[1].charAt(0), ImmutableSet.of())
          );
        }).collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (l, r) -> ImmutableSet.<Character>builder().addAll(l).addAll(r).build())
        );
  }

  public String topoSort(Map<Character, Set<Character>> steps) {
    List<Character> rs = getReadySteps(steps);

    PriorityQueue<Character> queue = new PriorityQueue<>(rs);

    StringBuilder order = new StringBuilder();
    Map<Character, Set<Character>> waitingSteps = removeSteps(steps, rs);

    while (!queue.isEmpty()) {
      Character step = queue.poll();
      order.append(step);
      waitingSteps = updateDeps(waitingSteps, ImmutableSet.of(step));

      List<Character> readySteps = getReadySteps(waitingSteps);

      waitingSteps = removeSteps(waitingSteps, readySteps);

      queue.addAll(readySteps);
    }

    return order.toString();
  }

  Map<Character, Set<Character>> updateDeps(
      Map<Character, Set<Character>> steps,
      Set<Character> done) {
    return steps.entrySet().stream()
        .map(e ->
            new AbstractMap.SimpleEntry<>(
                e.getKey(),
                e.getValue().stream().filter(dep -> !done.contains(dep)).collect(Collectors.toSet())
            )
        )
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  List<Character> getReadySteps(Map<Character, Set<Character>> steps) {
   return steps.entrySet().stream()
          .filter(e -> e.getValue().size() == 0)
          .map(Map.Entry::getKey)
          .collect(Collectors.toList());
  }

  List<WorkingStep> getReadyWorkingSteps(
      Map<Character, Set<Character>> steps, int baseCost
  ) {
    return steps.entrySet().stream()
        .filter(e -> e.getValue().size() == 0)
        .map(e -> new WorkingStepBuilder()
            .id(e.getKey())
            .cost(e.getKey() - 'A' + baseCost + 1)
            .started(false)
            .build())
        .collect(Collectors.toList());
  }

  Map<Character, Set<Character>> removeSteps(
      Map<Character, Set<Character>> steps,
      List<Character> toRemove
  ) {
    return steps.entrySet().stream()
          .filter(e -> !toRemove.contains(e.getKey()))
          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @AutoMatter
  public interface WorkingStep {
    Character id();
    int cost();
    boolean started();

    default WorkingStep doWork() {
      return WorkingStepBuilder.from(this).started(true).cost(Math.max(0, cost() - 1)).build();
    }
  }

  Set<WorkingStep> doWork(Set<WorkingStep> workingSteps, int numWorkers) {
    List<WorkingStep> sorted = workingSteps.stream()
        .sorted(
            Comparator.comparing(WorkingStep::started)
                .reversed()
                .thenComparing(WorkingStep::id))
        .collect(Collectors.toList());

    IntStream.range(0, Math.min(workingSteps.size(), numWorkers))
        .forEach(i -> sorted.set(i, sorted.get(i).doWork()));

    return ImmutableSet.copyOf(sorted);
  }

  public int topoSortWorkers(Map<Character, Set<Character>> steps, int numWorkers, int baseCost) {
    List<WorkingStep> rs = getReadyWorkingSteps(steps, baseCost);
    Set<WorkingStep> workingSteps = ImmutableSet.copyOf(rs);

    Map<Character, Set<Character>> waitingSteps = removeSteps(
        steps,
        rs.stream().map(WorkingStep::id).collect(Collectors.toList())
    );

    int time = 0;
    while (!workingSteps.isEmpty()) {
      workingSteps = doWork(workingSteps, numWorkers);

      Set<WorkingStep> doneSteps = workingSteps.stream()
          .filter(ws -> ws.cost() == 0)
          .collect(Collectors.toSet());

      waitingSteps = updateDeps(
          waitingSteps,
          doneSteps.stream().map(WorkingStep::id).collect(Collectors.toSet())
      );

      List<WorkingStep> readySteps = getReadyWorkingSteps(waitingSteps, baseCost);

      waitingSteps = removeSteps(
          waitingSteps,
          readySteps.stream().map(WorkingStep::id).collect(Collectors.toList())
      );

      workingSteps = Stream.concat(readySteps.stream(), workingSteps.stream())
          .filter(e -> !doneSteps.contains(e))
          .collect(Collectors.toSet());
      time += 1;
    }

    return time;
  }
}

