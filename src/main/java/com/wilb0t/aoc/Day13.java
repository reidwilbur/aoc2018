package com.wilb0t.aoc;

import io.norberg.automatter.AutoMatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.checkerframework.checker.units.qual.A;

public class Day13 {

  enum Dir {
    N, E, S, W;

    public Dir turn(Turn turn) {
      switch (turn) {
        case LT: return values()[(this.ordinal() + values().length - 1) % values().length];
        case RT: return values()[(this.ordinal() + 1) % values().length];
        default: return this;
      }
    }
  }
  enum Turn {
    LT, ST, RT;

    public Turn next() {
      return values()[(this.ordinal() + 1) % values().length];
    }
  }

  @AutoMatter
  public interface Pos {
    int x();
    int y();

    default Pos next(Dir dir) {
      switch (dir) {
        case N: return PosBuilder.from(this).y(this.y() - 1).build();
        case S: return PosBuilder.from(this).y(this.y() + 1).build();
        case E: return PosBuilder.from(this).x(this.x() + 1).build();
        case W: return PosBuilder.from(this).x(this.x() - 1).build();
        default: throw new RuntimeException("bad direction");
      }
    }
  }

  public static class Cart {
    private Dir direction;
    private Turn turn;
    private Pos pos;

    public Cart(Pos p, Dir dir) {
      this.pos = p;
      this.direction = dir;
      this.turn = Turn.LT;
    }

    public Pos tick(List<String> map) {
      Pos nextPos = pos.next(direction);
      char c = map.get(nextPos.y()).charAt(nextPos.x());
      direction = nextDir(c, direction, turn);
      turn = nextTurn(c, turn);
      pos = nextPos;
      return pos;
    }

    public Pos pos() {
      return pos;
    }

    static Turn nextTurn(char c, Turn curTurn) {
      return (c == '+')
             ? curTurn.next()
             : curTurn;
    }

    static Dir nextDir(char c, Dir curDir, Turn curTurn) {
      switch (c) {
        case '/': {
          switch (curDir) {
            case N: return Dir.E;
            case S: return Dir.W;
            case E: return Dir.N;
            case W: return Dir.S;
          }
        }
        case '\\': {
          switch (curDir) {
            case N: return Dir.W;
            case S: return Dir.E;
            case W: return Dir.N;
            case E: return Dir.S;
          }
        }
        case '+': return curDir.turn(curTurn);
        case '|':
        case '-': return curDir;
        default: throw new RuntimeException("bad state");
      }
    }

    public char show() {
      switch (direction) {
        case N: return '^';
        case S: return 'v';
        case E: return '>';
        case W: return '<';
        default: throw new RuntimeException("bad dir");
      }
    }
  }

  public List<Cart> getCarts(List<String> map) {
    List<Cart> carts = new ArrayList<>();

    for (int y = 0; y < map.size(); y++) {
      for (int x = 0; x < map.get(y).length(); x++) {
        char c = map.get(y).charAt(x);
        switch (c) {
          case '^': { carts.add(new Cart(new PosBuilder().x(x).y(y).build(), Dir.N)); break; }
          case '>': { carts.add(new Cart(new PosBuilder().x(x).y(y).build(), Dir.E)); break; }
          case 'v': { carts.add(new Cart(new PosBuilder().x(x).y(y).build(), Dir.S)); break; }
          case '<': { carts.add(new Cart(new PosBuilder().x(x).y(y).build(), Dir.W)); break; }
        }
      }
    }

    return carts;
  }

  public List<String> removeCarts(List<String> map) {
    return map.stream()
        .map(s -> s.replaceAll("[v^]", "|"))
        .map(s -> s.replaceAll("[<>]", "-"))
        .collect(Collectors.toList());
  }

  void printMap(List<String> map, List<Cart> carts) {
    List<StringBuilder> bldrs = map.stream().map(StringBuilder::new).collect(Collectors.toList());

    carts.forEach(c -> bldrs.get(c.pos().y()).setCharAt(c.pos().x(), c.show()));

    bldrs.forEach(b -> System.out.println(b.toString()));
    System.out.println();
  }

  public Pos firstCrash(List<String> map) {
    List<Cart> carts = getCarts(map);
    List<String> wMap = removeCarts(map);

    int itr = 0;
    while (true) {
      List<Cart> ordered = carts.stream()
          .sorted(Comparator.<Cart>comparingInt(c -> c.pos().y()).thenComparingInt(c -> c.pos().x()))
          .collect(Collectors.toList());

      for (int i = 0; i < ordered.size(); i++) {
        Cart c = ordered.get(i);
        c.tick(wMap);
        Optional<Cart> crashed = ordered.stream()
            .filter(other -> other != c)
            .filter(other -> other.pos().equals(c.pos()))
            .findFirst();

        if (crashed.isPresent()) {
          System.out.println(itr);
          return crashed.get().pos();
        }
      }
      itr += 1;
    }
  }

  public Pos lastCart(List<String> map) {
    List<Cart> carts = getCarts(map);
    List<String> wMap = removeCarts(map);

    while (true) {
      if (carts.size() == 1) {
        return carts.get(0).pos();
      }

      List<Cart> ordered = carts.stream()
          .sorted(Comparator.<Cart>comparingInt(c -> c.pos().y()).thenComparingInt(c -> c.pos().x()))
          .collect(Collectors.toList());

      for (int i = 0; i < ordered.size(); i++) {
        Cart c = ordered.get(i);
        c.tick(wMap);
        List<Cart> crashed = ordered.stream()
            .filter(other -> other != c)
            .filter(other -> other.pos().equals(c.pos()))
            .collect(Collectors.toList());

        if (crashed.size() > 0) {
          crashed.add(c);
          carts.removeAll(crashed);
        }
      }
    }
  }
}

