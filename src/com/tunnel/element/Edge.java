package com.tunnel.element;

public class Edge implements Comparable<Edge> {
	
	private Vertex firstVertex;
	private Vertex secondVertex;
	private int weight;
	
	public Edge(Integer firstVertexID, Integer secondVertexID) {
		this.firstVertex = new Vertex(firstVertexID);
		this.secondVertex = new Vertex(secondVertexID);
	}
	
	public Edge(Vertex firstVertex, Vertex secondVertex) {
		this.firstVertex = firstVertex;
		this.secondVertex = secondVertex;
		setWeight(firstVertex, secondVertex);
	}
	
	public Edge(Vertex firstVertex, Vertex secondVertex, int weight) {
		this.firstVertex = firstVertex;
		this.secondVertex = secondVertex;
		this.weight = weight;
	}
	
	public Vertex getFirstVertex() {
		return firstVertex;
	}
	
	public void setFirstVertex(Vertex firstVertex) {
		this.firstVertex = firstVertex;
	}

	public Vertex getSecondVertex() {
		return secondVertex;
	}

	public void setSecondVertex(Vertex secondVertex) {
		this.secondVertex = secondVertex;
	}

	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	private void setWeight(Vertex firstVertex, Vertex secondVertex) {
		int weight;
		int distanceX = Math.abs(firstVertex.getX() - secondVertex.getX());
		int distanceY = Math.abs(firstVertex.getY() - secondVertex.getY());
		
		weight = distanceX + distanceY;
		this.weight = weight;
	}
	
	@Override
    public int compareTo(Edge otherEdge) {
		Integer firstEdgeWeight = this.getWeight();
		Integer secondEdgeWeight = otherEdge.getWeight();
        return firstEdgeWeight.compareTo(secondEdgeWeight);
    }

}
