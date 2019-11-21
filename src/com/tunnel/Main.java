package com.tunnel;

import java.io.IOException;

import com.tunnel.algorithm.FileExecutor;
import com.tunnel.algorithm.GraphConverter;
import com.tunnel.algorithm.ShortestPathCalculator;
import com.tunnel.element.Graph;
import com.tunnel.element.Maze;

public class Main {
	
	private final static String inputMazeFileName = "./input/maze_input.txt";
	private final static String outputDiagramFileName = "./output/diagram.txt";
	
	private final static String inputGraphFileName = "./input/graph_input.txt";
	private final static String outputShortestPathFileName = "./output/shortest_path.txt";

	public static void main(String[] args) throws IOException {
		
		FileExecutor fileExecute = new FileExecutor();
		
		// requirement
		Maze maze = new Maze();
        maze = fileExecute.readMazeFromInputFile(inputMazeFileName);
        
        GraphConverter graphConvert = new GraphConverter(maze);
        Graph graph = graphConvert.getGraph();

        Graph graphInput = fileExecute.readGraphFromInputFile(inputGraphFileName);
        ShortestPathCalculator shortestPathCalculator = new ShortestPathCalculator(graphInput, maze.getStartStationID(), maze.getEndStationID());
		
		fileExecute.writeDiagramOutputFile(graph, outputDiagramFileName);
		
		fileExecute.writeShortesPathOutputFile(shortestPathCalculator, outputShortestPathFileName);
		
		// test case
		for (int i = 1; i <= 8; i++) {		
			String inputMazeFileNameTestCase = "./input/testcase"+i+".txt";
			String outputDiagramFileNameTestCase = "./output/testcase"+i+"-diagram.txt";
			String outputShortestPathFileNameTestCase = "./output/testcase"+i+"-result.txt";
			
			FileExecutor fileExecuteTest = new FileExecutor();
			
			Maze mazeTest = new Maze();
			mazeTest = fileExecuteTest.readMazeFromInputFile(inputMazeFileNameTestCase);
			
			GraphConverter graphConvertTest = new GraphConverter(mazeTest);
	        Graph graphTest = graphConvertTest.getGraph();

	        ShortestPathCalculator shortestPathCalculatorTest = new ShortestPathCalculator(graphTest, mazeTest.getStartStationID(), mazeTest.getEndStationID());
			
	        fileExecuteTest.writeDiagramOutputFile(graphTest, outputDiagramFileNameTestCase);
	        fileExecuteTest.writeShortesPathOutputFile(shortestPathCalculatorTest, outputShortestPathFileNameTestCase);
		}
	}

}
