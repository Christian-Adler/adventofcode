import java.util.*;

public class Task {
  private final Set<Component> components = new HashSet<>();


  public void init() {
  }

  public void addLine(String input) {
    String[] split = input.split(":");
    Component component = getComponent(split[0]);
    String[] connects = split[1].trim().split("\\s+");
    for (String connect : connects) {
      Component connectedComponent = getComponent(connect);
      Component.connectComponents(component, connectedComponent);
    }
  }

  private Component getComponent(String name) {
    Component component = components.stream().filter(
        c -> c.name().equals(name)
    ).findFirst().orElse(null);
    if (component == null) {
      component = new Component(name);
      components.add(component);
    }
    return component;
  }


  public void afterParse() {
    out("components", components.size());
    // out("['" + components.stream().map(c -> c.name).collect(Collectors.joining("','")) + "']");

    // For yFiles
    // Set<Set<String>> edges = new HashSet<>();
    // out("[");
    // for (Component component : components) {
    //   for (Component conectedcomponent : component.edges) {
    //     Set<String> edge = new HashSet<>();
    //     edge.add(component.name);
    //     edge.add(conectedcomponent.name);
    //     edges.add(edge);
    //   }
    // }
    // out(edges.stream().map(cSet -> {
    //   ArrayList<String> list = new ArrayList<>(cSet);
    //   return "{from:'" + list.get(0) + "', to:'" + list.get(1) + "', name:'e1',stroke: '1.5px #662b00'}";
    // }).collect(Collectors.joining(",")));
    // out("]");

    int minNodes = 3;
    Set<Edge> largeCircleStarts = new HashSet<>();
    while (true) {
      out("search large circles min nodes ", minNodes);
      largeCircleStarts = new HashSet<>();
      for (Component component : components) {
        for (Component connectedComponent : component.edges) {
          if (!hasCircleWithLessThanNodes(component, connectedComponent, minNodes)) {
            // out("Found large circle start ", component, connectedComponent);
            largeCircleStarts.add(new Edge(component, connectedComponent));
          }
        }
      }
      if (largeCircleStarts.size() == 3) {
        break;
      }
      minNodes++;
    }

    out("largeCircleStarts", largeCircleStarts);
    // out("Part 1:", largeCircleStarts.size());

    out("remove Edges... count remaining graph nodes");

    for (Edge largeCircleStart : largeCircleStarts) {
      Component.disconnectComponents(largeCircleStart.c1(), largeCircleStart.c2());
    }

    Set<Component> graph1 = new HashSet<>();
    Set<Component> graph2 = new HashSet<>();

    for (Edge largeCircleStart : largeCircleStarts) {
      Component c = largeCircleStart.c1();
      if (graph1.contains(c)) continue;
      if (graph2.contains(c)) continue;

      Set<Component> graph = calcGraph(c);
      if (graph1.isEmpty())
        graph1.addAll(graph);
      else // if (graph2.isEmpty())
        graph2.addAll(graph);

      c = largeCircleStart.c2();
      if (graph1.contains(c)) continue;
      if (graph2.contains(c)) continue;

      graph = calcGraph(c);
      if (graph1.isEmpty())
        graph1.addAll(graph);
      else //  if (graph2.isEmpty())
        graph2.addAll(graph);
    }

    out("Separated graphs size:", graph1.size(), graph2.size());
    out("Part 1:", graph1.size() * graph2.size());
  }

  private Set<Component> calcGraph(Component component) {
    Set<Component> graph = new HashSet<>();

    List<Component> worklist = new ArrayList<>();
    worklist.add(component);
    while (!worklist.isEmpty()) {
      Component c = worklist.removeFirst();
      if (!graph.add(c)) continue;

      worklist.addAll(c.edges);
    }

    return graph;
  }

  private boolean hasCircleWithLessThanNodes(Component component, Component connectedComponent, int minNodes) {
    return hasCircleWithLessThanNodes(component, component, connectedComponent, new LinkedHashSet<>(), minNodes);
  }

  private boolean hasCircleWithLessThanNodes(Component targetComponent, Component prev, Component check, LinkedHashSet<Edge> visited, int minNodes) {
    if (!visited.add(new Edge(prev, check))) {
      return false;
    }
    if (visited.size() > 1 && check.equals(targetComponent))
      return visited.size() <= minNodes;

    if (visited.size() > minNodes)
      return false;

    for (Component nextCheck : check.edges) {
      LinkedHashSet<Edge> nextVisited = new LinkedHashSet<>(visited);
      if (hasCircleWithLessThanNodes(targetComponent, check, nextCheck, nextVisited, minNodes))
        return true;
    }
    return false;
  }

  public void out(Object... str) {
    Util.out(str);
  }


  public String toStringSVG() {
    SVG svg = new SVG();
    return svg.toSVGStringAged();
  }


  public String toStringConsole() {
    SVG svg = new SVG();
    return svg.toConsoleString();
  }
}
