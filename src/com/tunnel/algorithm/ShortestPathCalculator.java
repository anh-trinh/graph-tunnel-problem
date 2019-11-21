package com.tunnel.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

import com.tunnel.element.Edge;
import com.tunnel.element.Graph;
import com.tunnel.element.Vertex;

public class ShortestPathCalculator {
	
	private String path;
	private int weight;
	
	public ShortestPathCalculator(Graph graph, int sourceVertexID, int endVertexID) {
		this.findShortestPathsOf(graph, sourceVertexID, endVertexID);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	private void findShortestPathsOf(Graph graph, int sourceVertexID, int endVertexID) {
		LinkedHashMap<Integer, ArrayList<Edge>> adjacencyVertices = graph.getAdjacencyVertices();
		// parentVertexList <child, parent> to show which parent of a added vertex will lead to sourceVertex
		
		if(isStationExist(adjacencyVertices, sourceVertexID) && isStationExist(adjacencyVertices, endVertexID)) {
			Map<Integer, Integer> parentVertexList = new HashMap<>();
			// totalWeightList show total weight from a vertex to sourceVertex
			Map<Integer, Integer> totalWeightList = new HashMap<>();
			PriorityQueue<Edge> considerVertexQueue = new PriorityQueue<>();
			
			parentVertexList.put(sourceVertexID, sourceVertexID);
			totalWeightList.put(sourceVertexID, 0);
			
			Edge firstConsiderEdge = new Edge(sourceVertexID, sourceVertexID);
			considerVertexQueue.add(firstConsiderEdge);
			
			do {
				Integer currentSourceVertexID = considerVertexQueue.poll().getSecondVertex().getVertexID();
				ArrayList<Edge> adjacencyEdgeList = adjacencyVertices.get(currentSourceVertexID.intValue());
				
				int tempTotalWeight = 0;
				for(Edge adjacencyEdge : adjacencyEdgeList) {
					if(!considerVertexQueue.contains(adjacencyEdge)) {
						tempTotalWeight = totalWeightList.get(currentSourceVertexID).intValue() + adjacencyEdge.getWeight();
						Vertex currentEndVertex = adjacencyEdge.getSecondVertex();
						if(!totalWeightList.containsKey(adjacencyEdge.getSecondVertex().getVertexID())) {
							parentVertexList.put(currentEndVertex.getVertexID(), currentSourceVertexID);
							totalWeightList.put(currentEndVertex.getVertexID(), tempTotalWeight);
							considerVertexQueue.add(adjacencyEdge);
						}
						else if (totalWeightList.get(currentEndVertex.getVertexID()) > tempTotalWeight) {
							parentVertexList.put(currentEndVertex.getVertexID(), currentSourceVertexID);
							totalWeightList.put(currentEndVertex.getVertexID(), tempTotalWeight);
							considerVertexQueue.add(adjacencyEdge);
						}
					}
				}
			}
			while (!considerVertexQueue.isEmpty());
			
			int nextStationID = endVertexID;
			StringBuffer path = new StringBuffer();
			while(nextStationID != sourceVertexID) {
				path.insert(0, nextStationID);
				path.insert(0, "-");
				nextStationID = parentVertexList.get(nextStationID).intValue();
			}
			path.insert(0, sourceVertexID);
			this.setPath(path.toString());
			
			int shortestWeight = totalWeightList.get(endVertexID) != null ? totalWeightList.get(endVertexID).intValue() : 0;
			this.setWeight(shortestWeight);
		}
	}
	
	private boolean isStationExist(LinkedHashMap<Integer, ArrayList<Edge>> adjacencyVertices, int stationID) {
		if(adjacencyVertices.get(stationID) != null) {
			return true;
		}
		return false;
	}
}
