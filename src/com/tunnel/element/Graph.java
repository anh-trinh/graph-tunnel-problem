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

	public void addKeyStation(Integer vertexID) {
	    this.adjacencyVertices.putIfAbsent(vertexID, new ArrayList<>());
	}
	
	public void addEdgeToStation(Vertex mainStation, Vertex concernStaion) {
		this.adjacencyVertices.get(mainStation.getVertexID()).add(new Edge(mainStation, concernStaion));
	}

	public void addEdgeToStation(Vertex mainStation, Edge edge) {
		this.adjacencyVertices.get(mainStation.getVertexID()).add(edge);
	}

	public ArrayList<Edge> getEdgeListByStation(Vertex mainStation) {
		ArrayList<Edge> edgeListOfStation = new ArrayList<>();
		edgeListOfStation.addAll(this.adjacencyVertices.get(mainStation.getVertexID()));
		return edgeListOfStation;
	}

	public boolean isEdgeAddedForStation(Edge edge) {
		ArrayList<Edge> edgeListOfStaion = this.getEdgeListByStation(edge.getFirstVertex());
		if(edgeListOfStaion.size() > 0) {
			for(int i = 0; i < edgeListOfStaion.size(); i++) {
				if (edge.getFirstVertex().getVertexID() == edgeListOfStaion.get(i).getFirstVertex().getVertexID()
				&& edge.getSecondVertex().getVertexID() == edgeListOfStaion.get(i).getSecondVertex().getVertexID()) {
					return true;
				}
			}
		}
		return false;
	}

	public void updateEdgeOfStation(Edge edge) {
		ArrayList<Edge> edgeListOfStaion = this.getEdgeListByStation(edge.getFirstVertex());
		if(edgeListOfStaion.size() > 0) {
			for(int i = 0; i < edgeListOfStaion.size(); i++) {
				if (edge.getFirstVertex().getVertexID() == edgeListOfStaion.get(i).getFirstVertex().getVertexID()
						&& edge.getSecondVertex().getVertexID() == edgeListOfStaion.get(i).getSecondVertex().getVertexID()) {
					this.adjacencyVertices.get(edge.getFirstVertex().getVertexID()).set(i, edge);
				}
			}
		}
	}

	public int getWeightOfEdgeFromEdgeListOfStation(Edge edge) {
		int weight = 0;
		ArrayList<Edge> edgeListOfStaion = this.getEdgeListByStation(edge.getFirstVertex());
		if(edgeListOfStaion.size() > 0) {
			for(int i = 0; i < edgeListOfStaion.size(); i++) {
				Edge considerEdge = edgeListOfStaion.get(i);
				if (edge.getFirstVertex().getVertexID() == considerEdge.getFirstVertex().getVertexID()
						&& edge.getSecondVertex().getVertexID() == considerEdge.getSecondVertex().getVertexID()) {
					weight = considerEdge.getWeight();
				}
			}
		}
		return weight;
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
