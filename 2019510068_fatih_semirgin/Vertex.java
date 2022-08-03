//package GraphPackage;

import java.util.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
//import ADTPackage.*; // Classes that implement various ADTs

class Vertex<T> implements VertexInterface<T> {
	private T label;
	private ArrayList<Edge> edgeList; // Edges to neighbors
	private boolean visited; // True if visited
	private VertexInterface<T> previousVertex; // On path to this vertex
	private double cost; // Of path to this vertex
	private int count_betweennes;
	private double result_closeness;

	public Vertex(T vertexLabel) {
		label = vertexLabel;
		edgeList = new ArrayList<>();
		visited = false;
		previousVertex = null;
		cost = 0;
		count_betweennes = 0;
	}

	public double r_closeness() {	// for the value of closeness.
		return 1.0 / result_closeness;
	}

	public double getResult_closeness() {
		return result_closeness;
	}

	public void setResult_closeness(double result_closeness) {
		this.result_closeness = result_closeness;
	}

	public ArrayList<Edge> getEdgeList() {
		return edgeList;
	}

	public void setEdgeList(ArrayList<Edge> edgeList) {
		this.edgeList = edgeList;
	}

	public VertexInterface<T> getPreviousVertex() {
		return previousVertex;
	}

	public void setPreviousVertex(VertexInterface<T> previousVertex) {
		this.previousVertex = previousVertex;
	}

	public void setLabel(T label) {
		this.label = label;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
		boolean result = false;
		if (!this.equals(endVertex)) { // Vertices are distinct
			Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
			boolean duplicateEdge = false;
			while (!duplicateEdge && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (endVertex.equals(nextNeighbor))
					duplicateEdge = true;
			} // end while
			if (!duplicateEdge) {
				edgeList.add(new Edge(endVertex, edgeWeight));
				result = true;
			} // end if
		} // end if
		return result;
	} // end connect

	public boolean connect(VertexInterface<T> endVertex) {
		return connect(endVertex, 0);
	} // end connect

	protected class Edge {
		private VertexInterface<T> vertex; // Vertex at end of edge
		private double weight;

		protected Edge(VertexInterface<T> endVertex, double edgeWeight) {
			vertex = endVertex;
			weight = edgeWeight;
		} // end constructor

		protected VertexInterface<T> getEndVertex() {
			return vertex;
		} // end getEndVertex

		protected double getWeight() {
			return weight;
		} // end getWeight
	} // end Edge

	private class NeighborIterator implements Iterator<VertexInterface<T>> {
		private Iterator<Edge> edges;

		private NeighborIterator() {
			edges = edgeList.iterator();
		} // end default constructor

		public boolean hasNext() {
			return edges.hasNext();
		} // end hasNext

		public VertexInterface<T> next() {
			VertexInterface<T> nextNeighbor = null;
			if (edges.hasNext()) {
				Edge edgeToNextNeighbor = edges.next();
				nextNeighbor = edgeToNextNeighbor.getEndVertex();
			} else
				throw new NoSuchElementException();

			return nextNeighbor;
		} // end next

		public void remove() {
			throw new UnsupportedOperationException();
		} // end remove
	} // end NeighborIterator

	@Override
	public T getLabel() {
		// TODO Auto-generated method stub
		return this.label;
	}

	@Override
	public void visit() {
		this.visited = true;

	}

	@Override
	public void unvisit() {
		this.visited = false;

	}

	@Override
	public boolean isVisited() {
		if (visited == true)
			return true;
		else
			return false;
	}

	@Override
	public Iterator<VertexInterface<T>> getNeighborIterator() {
		return new NeighborIterator();
		// return null;
	}

	@Override
	public Iterator<Double> getWeightIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasNeighbor() {
		return !edgeList.isEmpty();
	}

	@Override
	public VertexInterface<T> getUnvisitedNeighbor() {
		VertexInterface<T> result = null;
		Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
		while (neighbors.hasNext() && (result == null)) {
			VertexInterface<T> nextNeighbor = neighbors.next();
			if (!nextNeighbor.isVisited())
				result = nextNeighbor;
		} // end while
		return result;
	}

	@Override
	public void setPredecessor(VertexInterface<T> predecessor) {
		this.previousVertex = predecessor;
	}

	@Override
	public VertexInterface<T> getPredecessor() {
		return this.previousVertex;
	}

	@Override
	public boolean hasPredecessor() {// parent
		if (this.previousVertex != null)
			return true;
		else
			return false;
	}

	@Override
	public void setCost(double newCost) {
		// TODO Auto-generated method stub
		this.cost = newCost;
	}

	@Override
	public double getCost() {
		// TODO Auto-generated method stub
		return this.cost;
	}

	@Override
	public int getCount_betweennes() {
		// TODO Auto-generated method stub
		return this.count_betweennes;
	}

	@Override
	public void setCount_betweennes(int count) {
		this.count_betweennes = count;
	}

}
// end constructor