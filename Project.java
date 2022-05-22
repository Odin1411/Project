import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;

/*
     *
     * Read from a file and create a Graph object
     * 
     */
public class Project {
	HashMap<String, Integer> identifiers = new HashMap<String, Integer>(); // string identifier as keys, int identifier
																			// as values
	int lines = 0;
	int counter = 0;

	/**
	 * assign an unique integer identifier to each string identifier
	 * save in hashmap identifiers
	 */
	private int assignIntIdentifier(String identifier) {

		Integer id = identifiers.get(identifier);
		if (id != null) {
			return id;
		} else { // new node
			identifiers.put(identifier, counter);
			counter++;
			return counter - 1;
		}

	}

	/**
	 * Checks if enough overlap in both sequences, in that case creates an edge
	 * 
	 */
	private void enoughOverlap(int ident1, int ident2, int diff1, int diff2, Graph graph) {
		if (diff1 >= 1000 && diff2 >= 1000) {
			graph.addEdge(ident1, ident2);

		}
	}

	/**
	 * read from file, line by line
	 * return Graph object
	 */
	public Graph read(InputStream in) {
		Graph graph = new Graph(11393436);
		Scanner scan = new Scanner(in);
		while (scan.hasNextLine()) {
			String nextLine = scan.nextLine();
			String[] dividedLine = nextLine.split("\\s+");
			String identifier1 = dividedLine[0];
			String identifier2 = dividedLine[1];
			int diff1 = Integer.parseInt(dividedLine[6]) - Integer.parseInt(dividedLine[5]);
			int diff2 = Integer.parseInt(dividedLine[10]) - Integer.parseInt(dividedLine[9]);

			int ident1 = assignIntIdentifier(identifier1);
			int ident2 = assignIntIdentifier(identifier2);

			lines++;
			enoughOverlap(ident1, ident2, diff1, diff2, graph);
			if (lines % 1000000 == 0) { // to see the progress
				System.out.println(lines);
			}
		}
		scan.close();
		// graph.printGraph();
		return graph;
	}

	public void saveHistogram(Graph graph) {
		PrintWriter out;
		try {
			out = new PrintWriter("histogramtest.txt");
			for (Integer i = 0; i < graph.size(); i++) {
				Integer a = graph.get(i).size();

				out.println(a.toString());
			}
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Exception " + e.getMessage());
		}

	}

	public static void main(String[] args) throws FileNotFoundException {
		Project pr = new Project();
		InputStream in = new FileInputStream("c:\\Users\\olivi\\eclipse-workspace\\Project\\overlaps.m4");
		long checkpoints[] = new long[5]; // To store timestamps in

		checkpoints[0] = System.currentTimeMillis();
		Graph graph = pr.read(in);
		checkpoints[1] = System.currentTimeMillis();
		pr.saveHistogram(graph);

		checkpoints[2] = System.currentTimeMillis();
		graph.dfs();

		checkpoints[3] = System.currentTimeMillis();
		System.out.println("number of edges: " + graph.numberOfEdges);
		System.out.println("number of components: " + graph.compCounter);
		System.out.println("number of cliques: " + graph.cliqueCounter);

		String algs[] = { "read", "read", "histogram", "depthfirstsearch" };
		// Output timing results
		for (int i = 1; i < 4; i++) {
			System.out.format("Time for task %s: %d ms", algs[i], checkpoints[i] - checkpoints[i - 1]);
			System.out.println();

		}

	}
}