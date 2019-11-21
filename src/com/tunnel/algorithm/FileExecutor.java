package com.tunnel.algorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import com.tunnel.element.Edge;
import com.tunnel.element.Graph;
import com.tunnel.element.Maze;
import com.tunnel.element.Vertex;

public class FileExecutor {
	
	private final int Y_NUMBERS_INPUT_POSITION = 1;
	private final int X_NUMBERS_INPUT_POSITION = 2;
	
	private final int GRAPH_START_POSITION = 3;
	private int GRAPH_END_POSITION = 0;
	
	private int START_STATION_ID_POSITION = 0;
	private int END_STATION_ID_POSITION = 0;
	
	public Maze readMazeFromInputFile(String inputFileName) throws IOException {
		// still not implement case content of file is incorrect
		Maze maze = new Maze();
		FileReader reader = null;
  		LineNumberReader lnr = null;
  		
  		String lineContent;
  		int lineNumber;
  		
  		int[][] points = null;
  		int x = 0;
  		int y = 0;
  		
  		try {
	    	reader = new FileReader(inputFileName);
	  		lnr = new LineNumberReader(reader);
	   
	         // read lines till the end of the stream
	         while((lineContent = lnr.readLine()) != null) {
	        	lineNumber = lnr.getLineNumber();
	            if(lineNumber == Y_NUMBERS_INPUT_POSITION) {
	            	y = Integer.parseInt(lineContent);
	            	maze.setNumberOfY(y);
	            	GRAPH_END_POSITION = y + 2;
	            	START_STATION_ID_POSITION = y + 3;
	            	END_STATION_ID_POSITION = y + 4;
	            	
	            }
	            else if(lineNumber == X_NUMBERS_INPUT_POSITION) {
	            	x = Integer.parseInt(lineContent);
	            	maze.setNumberOfX(x);
	            	points = new int[y][x];
	            }
	            else if(points != null && (GRAPH_START_POSITION <= lineNumber && lineNumber <= GRAPH_END_POSITION)) {
	            	int yPosition = lineNumber - GRAPH_START_POSITION;
	            	for (int xPosition = 0; xPosition < maze.getNumberOfX(); xPosition++) {
						if (lineContent.charAt(xPosition) == '.') {
							points[yPosition][xPosition] = Maze.BLANK;
						}
						else if (lineContent.charAt(xPosition) == '#') {
							points[yPosition][xPosition] = Maze.WALL;
						}
					}
	            }
	            else if(lineNumber == START_STATION_ID_POSITION) {
	            	int startStationID = Integer.parseInt(lineContent);
	            	maze.setStartStationID(startStationID);
	            }
	            else if(lineNumber == END_STATION_ID_POSITION) {
	            	int endStationID = Integer.parseInt(lineContent);
	            	maze.setEndStationID(endStationID);
	            }
	         }
	         
	     } catch(Exception e) {
	         // if any error occurs
	         e.printStackTrace();
	     } finally {
	         // closes the stream and releases system resources
	         if(reader != null)
	        	 reader.close();
	         if(lnr != null)
	            lnr.close();
	      }
  		
  		maze.setPoints(points);
		
		return maze;
	}
	
	public Graph readGraphFromInputFile(String inputFileName) throws IOException {
		Graph graph = new Graph();
		FileReader reader = null;
  		LineNumberReader lnr = null;
  		
  		String lineContent;
  		
  		try {
	    	reader = new FileReader(inputFileName);
	  		lnr = new LineNumberReader(reader);
	   
	         // read lines till the end of the stream
	         while((lineContent = lnr.readLine()) != null) {
	        	
	        	ArrayList<Integer> dataOfGraphCollection = new ArrayList<>();
	        	StringBuilder currentNumber = new StringBuilder();
	            for (int i = 0; i < lineContent.length(); i++) {
	                char c = lineContent.charAt(i);
	                if (Character.isDigit(c)) {
	                	currentNumber.append(c);
	                }
	                else {
	                	try {
	                		Integer convertedNumber = Integer.parseInt(currentNumber.toString());
	                		dataOfGraphCollection.add(convertedNumber);
	                	}
	                	catch (NumberFormatException e) { }
	                	finally {
	                		currentNumber.setLength(0);
	                	}
	                }
	            }
	            
	            Vertex keyStation = new Vertex(dataOfGraphCollection.get(0));
	            graph.addKeyStation(keyStation.getVertexID());
	            
	            for(int i = 1; i < dataOfGraphCollection.size() - 1; i+=2) {
	            	Vertex connectedStation = new Vertex(dataOfGraphCollection.get(i));
	            	int weight = dataOfGraphCollection.get(i + 1);
	            	Edge edge = new Edge(keyStation, connectedStation);
	            	edge.setWeight(weight);
	            	graph.addEdgeToStation(keyStation, edge);
	            }
	         }
	         
  		} catch(Exception e) {
	         // if any error occurs
	         e.printStackTrace();
	    } finally {
	         // closes the stream and releases system resources
	         if(reader != null)
	        	 reader.close();
	         if(lnr != null)
	            lnr.close();
	    }
  		
		return graph;
	}
	
	public void writeDiagramOutputFile(Graph graph, String outputFileName) {
		
		// Delete the existed output files
		File file = new File(outputFileName);
		if (file.exists() == true) {
			file.delete();
		}
		
		// Generate the file diagram.txt
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fw);
			
			out.write(graph.toString());
			out.close();
			System.out.println("Write diagram successfully!");
			System.out.println(graph.toString());
		} catch (IOException e) {
			System.out.println("Error: " + e.toString());
		}
			
	}
	
	public void writeShortesPathOutputFile(ShortestPathCalculator shortestPathCalculator, String outputFileName) {
		
		// Delete the existed output files
		File file = new File(outputFileName);
		if (file.exists() == true) {
			file.delete();
		}
		
		// Generate the file shortest_path.txt
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fw);
			
			String pathContent = "";
			if(shortestPathCalculator.getPath() == null) {
				pathContent = "No path exist between these two stations";
			}
			else {
				pathContent = shortestPathCalculator.getPath()+"\n"+shortestPathCalculator.getWeight();
			}
			out.write(pathContent);
			out.close();
			System.out.println("Write the shortest path successfully!");
			System.out.println(pathContent);
			System.out.println("\n");
		} catch (IOException e) {
			System.out.println("Error: " + e.toString());
		}
			
	}
}
