package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Day8 {

  public static List<Integer> parse(String input) {
    return Arrays.stream(input.split(" "))
        .map(Integer::valueOf)
        .collect(Collectors.toList());
  }

  public int getMetadataSum(List<Integer> input) {
    Deque<Integer> nodeStack = new ArrayDeque<>();
    Deque<Integer> metadataStack = new ArrayDeque<>();

    int idx = 0;
    nodeStack.push(input.get(idx++));
    metadataStack.push(input.get(idx++));

    int metadataSum = 0;
    while (idx < input.size()) {
      if (nodeStack.peek() == 0) {
        nodeStack.pop();
        int numMetadata = metadataStack.pop();
        for (int i = 0; i < numMetadata; i++, idx++) {
          metadataSum += input.get(idx);
        }
        if (nodeStack.size() > 0) {
          nodeStack.push(nodeStack.pop() - 1);
        }
      } else {
        nodeStack.push(input.get(idx++));
        metadataStack.push(input.get(idx++));
      }
    }
    return metadataSum;
  }

  public int rootValue(List<Integer> input) {
    // holds the count of child nodes for node at a level
    Deque<Integer> nodeStack = new ArrayDeque<>();
    // used to count how children left to process at a level
    Deque<Integer> nodeStateStack = new ArrayDeque<>();
    // holds the count of metadata elements at a level
    Deque<Integer> metadataStack = new ArrayDeque<>();
    // holds the current calculated child values at a level
    Deque<List<Integer>> childValueStack = new ArrayDeque<>();
    // adding this extra child stack to make the final value calc easier
    // w/o it have to special case the root value calc
    childValueStack.push(new ArrayList<>());

    int idx = 0;
    // init stacks from root
    nodeStack.push(input.get(idx));
    nodeStateStack.push(input.get(idx++));
    metadataStack.push(input.get(idx++));
    childValueStack.push(new ArrayList<>());

    while (idx < input.size()) {
      // leaf nodes
      if (nodeStateStack.peek() == 0 && nodeStack.peek() == 0) {
        nodeStateStack.pop();
        nodeStack.pop();
        childValueStack.pop();
        int numMetadata = metadataStack.pop();
        int metadataSum = 0;
        for (int i = 0; i < numMetadata; i++, idx++) {
          metadataSum += input.get(idx);
        }
        childValueStack.peek().add(metadataSum);
        if (nodeStateStack.size() > 0) {
          nodeStateStack.push(nodeStateStack.pop() - 1);
        }
      // parent nodes where all children have been calculated
      } else if (nodeStateStack.peek() == 0) {
        nodeStateStack.pop();
        int children = nodeStack.pop();
        List<Integer> childValues = childValueStack.pop();
        int numMetadata = metadataStack.pop();
        int nodeValue = 0;
        for (int i = 0; i < numMetadata; i++, idx++) {
          int metadata = input.get(idx);
          // use metadata value as child idx (1 based, not 0)
          // out of bounds metadata idx's are ignored
          if (metadata <= children) {
            nodeValue += childValues.get(metadata - 1);
          }
        }
        // add this nodes value to list of child values for parent
        childValueStack.peek().add(nodeValue);
        // update parent's current child processing count
        if (nodeStateStack.size() > 0) {
          nodeStateStack.push(nodeStateStack.pop() - 1);
        }
      // new child nodes
      } else {
        nodeStack.push(input.get(idx));
        nodeStateStack.push(input.get(idx++));
        metadataStack.push(input.get(idx++));
        childValueStack.push(new ArrayList<>());
      }
    }
    return childValueStack.pop().stream().mapToInt(i -> i).sum();
  }
}

