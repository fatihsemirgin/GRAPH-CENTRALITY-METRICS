import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
//import ADTPackage.*; // Classes that implement various ADTs

public class Graph<T> implements GraphInterface<T> {
	private HashMap<String, Vertex> vertices;
	private int edgeCount;

	public Graph() {
		vertices = new HashMap<>();
		edgeCount = 0;
	} // end default constructor

	public void addVertex(T vertexLabel) { // boolean void oldu
		if (!hasVertex(vertexLabel)) {
			VertexInterface<T> addOutcome = vertices.put((String) vertexLabel, new Vertex<>(vertexLabel));
			// return addOutcome == null;
		}
		// Was addition to dictionary successful?
	} // end addVertex

	public boolean addEdge(T begin, T end, double edgeWeight) {
		if (!hasEdge(begin, end)) {
			boolean result = false;
			VertexInterface<T> beginVertex = vertices.get(begin);
			VertexInterface<T> endVertex = vertices.get(end);
			if ((beginVertex != null) && (endVertex != null))
				result = beginVertex.connect(endVertex, edgeWeight);
			if (result)
				edgeCount++;

			return result;
		}
		return false;

	} // end addEdge

	public boolean addEdge(T begin, T end) {
		return addEdge(begin, end, 0);
	} // end addEdge

	public boolean hasVertex(T vertexLabel) {
		boolean flag = false;
		for (VertexInterface<T> vertex : vertices.values()) {
			if (vertex.getLabel().equals(vertexLabel)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public boolean hasEdge(T begin, T end) {
		boolean found = false;
		VertexInterface<T> beginVertex = vertices.get(begin);
		VertexInterface<T> endVertex = vertices.get(end);
		if ((beginVertex != null) && (endVertex != null)) {
			Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
			while (!found && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (endVertex.equals(nextNeighbor))
					found = true;
			} // end while
		} // end if
		return found;
	} // end hasEdge

	public boolean isEmpty() {
		return vertices.isEmpty();
	} // end isEmpty

	public void clear() {
		vertices.clear();
		edgeCount = 0;
	} // end clear

	public int getNumberOfVertices() {
		return vertices.size();
	} // end getNumberOfVertices

	public int getNumberOfEdges() {
		return edgeCount;
	} // end getNumberOfEdges

	public void resetVertices() {
		for (VertexInterface<T> vertex : vertices.values()) {
			vertex.unvisit();
			vertex.setCost(0);
			vertex.setPredecessor(null);
		}
	} // end resetVertices

	public int getShortestPath(T begin, T end, Stack<T> path) { // For betweenness sort(From begin to End)
		resetVertices();
		boolean done = false;
		Queue<VertexInterface<T>> vertexQueue = new LinkedList<>();

		VertexInterface<T> originVertex = vertices.get(begin);
		VertexInterface<T> endVertex = vertices.get(end);
		originVertex.visit();
		// Assertion: resetVertices() has executed setCost(0)
		// and setPredecessor(null) for originVertex
		vertexQueue.add(originVertex);
		while (!done && !vertexQueue.isEmpty()) {
			VertexInterface<T> frontVertex = vertexQueue.remove();
			Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
			while (!done && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next(); // It sets the necessary fields as its neighbors
																	// visit
				if (!nextNeighbor.isVisited()) {
					nextNeighbor.visit();
					nextNeighbor.setCost(1 + frontVertex.getCost());
					nextNeighbor.setPredecessor(frontVertex);
					vertexQueue.add(nextNeighbor);
				} // end if
				if (nextNeighbor.equals(endVertex)) // When it reaches the destination.
					done = true;
			} // end while
		} // end while
			// Traversal ends; construct shortest path
		int pathLength = (int) endVertex.getCost(); // The length of the short path.
		path.push(endVertex.getLabel()); // To keep the shortest path in path stack.
		VertexInterface<T> vertex = endVertex;
		while (vertex.hasPredecessor()) {
			vertex = vertex.getPredecessor(); // When the end vertexe is reached, the predecessor travels and adds it to
												// the stack.
			path.push(vertex.getLabel());
		} // end while
		c_betweeness(path); // Thus, the count of the nodes for betweenes reach their values.
		return pathLength;
	} // end getShortestPath

	public void c_betweeness(Stack<T> path) { // To set how many times the current node has passed in short paths.
		while (!path.isEmpty()) {
			vertices.get(path.peek()).setCount_betweennes(vertices.get(path.peek()).getCount_betweennes() + 1);
			path.pop();
		}
	}

	@SuppressWarnings("unchecked")
	public void centrality_Metrics(Stack<T> path) { // A function that performs both closeness and betweenness.
		double sum = 0; // By creating short paths once, both closeness and betweenness are reached.
		for (int i = 1; i < vertices.size() + 1; i++) {
			sum = 0;
			for (int j = i + 1; j < vertices.size() + 1; j++) {
				double path_length = getShortestPath((T) (Integer.toString(i)), (T) (Integer.toString(j)), path);
				if (path_length > 0) {
					vertices.get(Integer.toString(j))
							.setResult_closeness(vertices.get(Integer.toString(j)).getResult_closeness() + path_length);
				}
				sum += path_length;
			}
			if (sum != 0)
				vertices.get(Integer.toString(i))
						.setResult_closeness(sum + vertices.get(Integer.toString(i)).getResult_closeness());
		}
		// Bottom side, for sorting values and vertices (for betweenness and closeness.)
		int max = 0;
		double max_2 = 0;
		VertexInterface<T> s1 = null;
		VertexInterface<T> s2 = null;
		VertexInterface<T> s3 = new Vertex("-1"); // temp vertex
		s3.setResult_closeness(-1); // temp vertex
		for (VertexInterface<T> vertex : vertices.values()) {
			if (max < vertex.getCount_betweennes()) { // For betweenness sort
				max = vertex.getCount_betweennes();
				s1 = vertex;
			}
			if (max_2 < vertex.r_closeness()) { // For closeness sort
				max_2 = vertex.r_closeness();
				s2 = vertex;
				if (Integer.valueOf((String) s2.getLabel()) < Integer.valueOf((String) s3.getLabel()))
					s3 = s2; // For the node with the highest label while the closeness values(max) are
								// equal.
			} // There can be more than one element with the same maximum value.

		}
		System.out.println(
				"- The Highest Node for Betweeness: " + s1.getLabel() + " and The Value: " + s1.getCount_betweennes());
		System.out
				.println("- The Highest Node for Closeness : " + s2.getLabel() + " and The Value: " + s2.r_closeness());
	}

} // end DirectedGraph