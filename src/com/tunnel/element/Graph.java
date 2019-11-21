package com.tunnel.element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Graph {

	private LinkedHashMap<Integer, ArrayList<Edge>> adjacencyVertices;
	
	public Graph() {
		this.setAdjacencyVertices(new LinkedHashMap<Integer, ArrayList<Edge>>());
	}
	
	public LinkedHashMap<Integer, ArrayList<Edge>> getAdjacencyVertices() {
		return adjacencyVertices;
	}

	public void setAdjacencyVertices(LinkedHashMap<Integer, ArrayList<Edge>> adjacencyVertices) {
		this.adjacencyVertices = adjacencyVertices;
	}

	public void addKeyStation(Integer vertex) {
	    this.adjacencyVertices.putIfAbsent(vertex, new ArrayList<>());
	}
	
	public void addEdgeToStation(Vertex mainStation, Vertex concernStaion) {
		adjacencyVertices.get(mainStation.getVertexID()).add(new Edge(mainStation, concernStaion));
	}
	
	public void addEdgeToStation(Vertex mainStation, Edge edge) {
		adjacencyVertices.get(mainStation.getVertexID()).add(edge);
	}
	
	@Override
	public String toString() {
		String content = "";
		StringBuffer relatedVertices = new StringBuffer();
		for (Entry<Integer, ArrayList<Edge>> vertices : this.adjacencyVertices.entrySet()) {
			Integer mainVertex = vertices.getKey();
		    ArrayList<Edge> edgeList = vertices.getValue();
		    int mainVertexID = mainVertex;
		    relatedVertices.append(mainVertexID);
		    for (Edge edge : edgeList) {
		    	relatedVertices.append("(");
		    	int adjacencyVertexID = edge.getSecondVertex().getVertexID();
		    	relatedVertices.append(adjacencyVertexID);
		    	relatedVertices.append(",");
		    	relatedVertices.append(edge.getWeight());
		    	relatedVertices.append(")");
		    }
		    relatedVertices.append("\n");
		}
		content = relatedVertices.toString();
		
		return content;
	}
}
