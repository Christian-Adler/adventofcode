package aoc;

public class Pair {
  private Object left;
  private Object right;
  private Pair parent = null;


  private Pair(Object left, Object right, Pair parent) {
    this.parent = parent;
    this.left = getPairItem(left, this);
    this.right = getPairItem(right, this);
  }

  private boolean has2Numbers() {
    return left instanceof Integer && right instanceof Integer;
  }

  public long magnitude() {
    long res = 0;
    if (left instanceof Integer i)
      res += 3L * i;
    else if (left instanceof Pair p)
      res += 3 * p.magnitude();

    if (right instanceof Integer i)
      res += 2L * i;
    else if (right instanceof Pair p)
      res += 2 * p.magnitude();

    return res;
  }

  public static Pair add(Pair p1, Pair p2) {
    return new Pair(p1, p2, null);
  }

  public void reduce() {
    boolean foundReduce = true;
    while (foundReduce) {
      // out(this);
      foundReduce = false;
      // find explode...
      Pair explode = findExplodePair(this, 1);
      if (explode != null) {
        explodeLeft(explode);
        // out(this);
        explodeRight(explode);
        // out(this);
        replaceBy0(explode);
        // out(this);
        foundReduce = true;
        continue;
      }
      Pair split = findSplitPair(this);
      if (split != null) {
        splitPair(split);

        foundReduce = true;
        // continue;
      }
    }
    // out(this);
  }

  private void splitPair(Pair split) {
    if (split.left instanceof Integer i && i >= 10) {
      split.left = new Pair(Math.floorDiv(i, 2), Math.ceilDiv(i, 2), split);
    } else if (split.right instanceof Integer i && i >= 10) {
      split.right = new Pair(Math.floorDiv(i, 2), Math.ceilDiv(i, 2), split);
    }
  }


  private static void replaceBy0(Pair pair) {
    Pair parent = pair.parent;
    if (parent == null) return;

    if (parent.left.equals(pair))
      parent.left = 0;
    else
      parent.right = 0;
  }

  private static void explodeLeft(Pair start) {
    Pair act = start;
    while (true) {
      if (act.parent == null)
        return;
      boolean foundNextNeighbour = !act.parent.left.equals(act);
      act = act.parent;
      if (foundNextNeighbour)
        break;
    }

    if (act.left instanceof Integer val) {
      act.left = val + (Integer) start.left;
      return;
    }

    act = (Pair) act.left;
    while (act.right instanceof Pair p)
      act = p;

    act.right = (Integer) act.right + (Integer) start.left;
  }

  private static void explodeRight(Pair start) {
    Pair act = start;
    while (true) {
      if (act.parent == null)
        return;
      boolean foundNextNeighbour = !act.parent.right.equals(act);
      act = act.parent;
      if (foundNextNeighbour)
        break;
    }

    if (act.right instanceof Integer val) {
      act.right = val + (Integer) start.right;
      return;
    }

    act = (Pair) act.right;
    while (act.left instanceof Pair p)
      act = p;

    act.left = (Integer) act.left + (Integer) start.right;
  }

  private static Pair findExplodePair(Pair pair, int depth) {
    if (pair.has2Numbers() && depth > 4)
      return pair;

    Pair explode = null;
    if (pair.left instanceof Pair left)
      explode = findExplodePair(left, depth + 1);
    if (explode == null && pair.right instanceof Pair right)
      explode = findExplodePair(right, depth + 1);
    return explode;
  }

  private static Pair findSplitPair(Pair pair) {
    Pair split = null;

    if (pair.left instanceof Integer i) {
      if (i >= 10)
        return pair;
    }
    if (pair.left instanceof Pair left)
      split = findSplitPair(left);

    if (split != null)
      return split;

    if (pair.right instanceof Integer i) {
      if (i >= 10)
        return pair;
    }
    if (pair.right instanceof Pair right)
      split = findSplitPair(right);

    return split;
  }


  public static Pair parse(String input, Pair parent) {
    // find split comma
    String inputContent = input.substring(1, input.length() - 1);

    String left = null;
    String right = null;
    int openBrackets = 0;
    for (int i = 0; i < inputContent.length(); i++) {
      char c = inputContent.charAt(i);
      if (c == '[')
        openBrackets++;
      else if (c == ']')
        openBrackets--;
      else if (c == ',' && openBrackets == 0) {
        left = inputContent.substring(0, i);
        right = inputContent.substring(i + 1);
        break;
      }
    }

    if (left == null) throw new IllegalStateException("got null!");

    return new Pair(left, right, parent);
  }

  private static Object getPairItem(Object input, Pair parent) {
    if (input instanceof Integer i) return i;
    String inputString = input.toString();
    if (inputString.startsWith("["))
      return parse(inputString, parent);
    return Integer.parseInt(inputString);
  }

  @Override
  public String toString() {
    return "[" + left +
        "," + right +
        ']';
  }
}
