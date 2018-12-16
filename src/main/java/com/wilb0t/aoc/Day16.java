package com.wilb0t.aoc;

import com.google.common.collect.ImmutableMap;
import io.norberg.automatter.AutoMatter;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        .map(s -> operations.values().stream().map(s::opMatches).filter(b -> b).count())
        .filter(c -> c >= 3)
        .count();
  }
}

