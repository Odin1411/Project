import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/*
   *
   * Store graph as ArrayList where index of ArrayList represents a node
   */
public class Graph {
  ArrayList<ArrayList<Integer>> graph;
  int V;
  int compCounter = 0; // counts number of components
  int cliqueCounter = 0; // counts number of cliques
  int numberOfEdges = 0; // counts edges

  Graph(int nodes) {
    V = nodes;
    graph = new ArrayList<>();
    for (int i = 0; i < V; i++) { // Add an empty ArrayList on each position that will contain the neighbors for
                                  // that specific node
      graph.add(new ArrayList<Integer>());
    }
  }

  /**
   * Add new edge
   */
  void addEdge(int v, int u) {
    if (!graph.get(v).contains(u)) {
      graph.get(v).add(u);
      graph.get(u).add(v);
      numberOfEdges++;
    }

  }

  /**
   * print graph
   */
  public void printGraph() {
    for (int i = 0; i < V; i++) {
      System.out.println("Node: " + i);
      for (int x : graph.get(i))
        System.out.println("-> " + x);
    }
  }

  /**
   * Perform depthfirstsearch on graph
   *
   */
  public void dfs() {

    boolean[] isVisited = new boolean[V];
    for (int v = 0; v < V; v++) {
      isVisited[v] = false;
    }
    for (int v = 0; v < V; v++) { // O(V) dependent on number of vertices

      if (!isVisited[v] && graph.get(v).size() > 0) {
        ArrayList<Integer> connectedList = new ArrayList<>();
        // System.out.println("new component");

        dfsVisit(v, isVisited, connectedList);
        // System.out.println(connectedList);
        if (connectedList.size() >= 3) { // checks if enough nodes for component
          compCounter++;
          if (isClique(connectedList)) { // checks if clique
            cliqueCounter++;
          }
        }

      }
    }
  }

  /**
   * helper function for dfs()
   *
   */
  public void dfsVisit(int v, boolean[] isVisited, ArrayList<Integer> connectedList) {
    connectedList.add(v);
    isVisited[v] = true;
    // System.out.println(v + " " + connected);
    for (int u : graph.get(v)) { // for neighbors (done for each edge) O(E)
      if (!isVisited[u]) {
        dfsVisit(u, isVisited, connectedList);

      }

    }
  }

  /**
   * if all nodes are connected to every other node return true
   *
   */
  public boolean isClique(Collection<Integer> compset) {
    int neighbors = compset.size() - 1;
    for (int v : compset) {
      if (graph.get(v).size() != neighbors) {
        return false;
      }
    }
    return true;
  }

  public int size() {
    return V;
  }

  public Collection<Integer> get(int v) {
    return graph.get(v);
  }

  /**
   * Not necessary for project, just for debugging
   *
   */
  public static void main(String[] args) {
    Graph g = new Graph(10);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(2, 1);
    g.addEdge(1, 2); // multiple of same edge is a problem
    g.addEdge(4, 3);
    g.addEdge(5, 4);
    g.addEdge(5, 3);
    g.addEdge(6, 7);

    g.dfs();
    // g.printGraph();
    System.out.println(g.graph);
    System.out.println("number of components: " + g.compCounter);
    System.out.println("number of cliques: " + g.cliqueCounter);

  }
}