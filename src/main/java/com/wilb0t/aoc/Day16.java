package com.wilb0t.aoc;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.norberg.automatter.AutoMatter;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16 {

  @AutoMatter
  public interface Instr {
    int opCode();
    int a();
    int b();
    int out();

    static Instr parse(String line) {
      String[] parts = line.split(" ");
      return new InstrBuilder()
          .opCode(Integer.parseInt(parts[0]))
          .a(Integer.parseInt(parts[1]))
          .b(Integer.parseInt(parts[2]))
          .out(Integer.parseInt(parts[3]))
          .build();
    }

    default int[] execrr(int[] init, BiFunction<Integer, Integer, Integer> op) {
      int[] res = Arrays.copyOf(init, init.length);
      res[out()] = op.apply(init[a()], init[b()]);
      return res;
    }

    default int[] execri(int[] init, BiFunction<Integer, Integer, Integer> op) {
      int[] res = Arrays.copyOf(init, init.length);
      res[out()] = op.apply(init[a()], b());
      return res;
    }

    default int[] execir(int[] init, BiFunction<Integer, Integer, Integer> op) {
      int[] res = Arrays.copyOf(init, init.length);
      res[out()] = op.apply(a(), init[b()]);
      return res;
    }
  }

  interface Operation extends BiFunction<int[], Instr, int[]> { }

  @AutoMatter
  public interface Sample {
    int[] init();
    Instr instr();
    int[] result();

    static Sample parse(List<String> lines) {
      String[] initParts = lines.get(0).substring(9, lines.get(0).length() - 1).split(", ");
      Instr instr = Instr.parse(lines.get(1));
      String[] resultParts = lines.get(2).substring(9, lines.get(0).length() - 1).split(", ");

      return new SampleBuilder()
          .instr(instr)
          .init(new int[]{Integer.parseInt(initParts[0]), Integer.parseInt(initParts[1]), Integer.parseInt(initParts[2]), Integer.parseInt(initParts[3])})
          .result(new int[]{Integer.parseInt(resultParts[0]), Integer.parseInt(resultParts[1]), Integer.parseInt(resultParts[2]), Integer.parseInt(resultParts[3])})
          .build();
    }

    default boolean opMatches(Operation op) {
      return Arrays.equals(op.apply(init(), instr()), result());
    }
  }

  public static Map<String, Operation> operations = ImmutableMap.<String, Operation>builder()
      .put("addr", (init, instr) -> instr.execrr(init, (a, b) -> a + b))
      .put("addi", (init, instr) -> instr.execri(init, (a, b) -> a + b))
      .put("mulr", (init, instr) -> instr.execrr(init, (a, b) -> a * b))
      .put("muli", (init, instr) -> instr.execri(init, (a, b) -> a * b))
      .put("banr", (init, instr) -> instr.execrr(init, (a, b) -> a & b))
      .put("bani", (init, instr) -> instr.execri(init, (a, b) -> a & b))
      .put("borr", (init, instr) -> instr.execrr(init, (a, b) -> a | b))
      .put("bori", (init, instr) -> instr.execri(init, (a, b) -> a | b))
      .put("setr", (init, instr) -> instr.execrr(init, (a, b) -> a))
      .put("seti", (init, instr) -> instr.execir(init, (a, b) -> a))
      .put("gtir", (init, instr) -> instr.execir(init, (a, b) -> (a > b) ? 1 : 0))
      .put("gtri", (init, instr) -> instr.execri(init, (a, b) -> (a > b) ? 1 : 0))
      .put("gtrr", (init, instr) -> instr.execrr(init, (a, b) -> (a > b) ? 1 : 0))
      .put("eqir", (init, instr) -> instr.execir(init, (a, b) -> (a.equals(b)) ? 1 : 0))
      .put("eqri", (init, instr) -> instr.execri(init, (a, b) -> (a.equals(b)) ? 1 : 0))
      .put("eqrr", (init, instr) -> instr.execrr(init, (a, b) -> (a.equals(b)) ? 1 : 0))
      .build();

  public long samplesMatchAtLeastThree(List<Sample> samples) {
    return samples.stream()
        .map(s -> operations.values().stream().filter(s::opMatches).count())
        .filter(c -> c >= 3)
        .count();
  }

  public Optional<Map<Integer, String>> dfs(Set<String> ops, List<Map.Entry<Integer, Set<String>>> nodes) {
    if (ops.isEmpty() && !nodes.isEmpty()) {
      return Optional.empty();
    } if (ops.isEmpty()) {
      return Optional.of(Collections.emptyMap());
    } else {
      int opCode = nodes.get(0).getKey();
      Set<String> opNames = nodes.get(0).getValue();
      for (String opName : opNames) {
        if (ops.contains(opName)) {
          Set<String> nextOps = ops.stream().filter(op -> !op.equals(opName)).collect(Collectors.toSet());
          List<Map.Entry<Integer, Set<String>>> nextNodes = nodes.subList(1, nodes.size());

          Optional<Map<Integer, String>> next = dfs(nextOps, nextNodes);

          if (next.isPresent()) {
            return Optional.of(
                ImmutableMap.<Integer, String>builder()
                    .putAll(next.get())
                    .put(opCode, opName)
                    .build()
            );
          }
        }
      }
      return Optional.empty();
    }
  }

  public Map<Integer, String> matchOpCodesToNames(List<Sample> samples) {
    Map<Integer, Set<String>> matches = samples.stream()
        .map(s ->
            new AbstractMap.SimpleEntry<>(
                s.instr().opCode(),
                operations.entrySet().stream()
                    .filter(e -> s.opMatches(e.getValue())).map(Map.Entry::getKey)
                    .collect(Collectors.toSet())
            )
        ).collect(Collectors.toMap(
            AbstractMap.SimpleEntry::getKey,
            AbstractMap.SimpleEntry::getValue,
            (o, n) -> { o.addAll(n); return o;})
        );

    List<Map.Entry<Integer, Set<String>>> ordered = matches.entrySet().stream()
        .sorted(Comparator.comparingInt(e -> e.getValue().size()))
        .collect(Collectors.toList());

    return dfs(operations.keySet(), ordered).get();
  }

  public int exec(List<Sample> samples, List<Instr> instrs) {
    Map<Integer, String> ops = matchOpCodesToNames(samples);

    int[] state = new int[]{ 0, 0, 0, 0 };
    for (Instr instr : instrs) {
      String opName = ops.get(instr.opCode());
      Operation op = operations.get(opName);
      state = op.apply(state, instr);
    }
    return state[0];
  }
}

