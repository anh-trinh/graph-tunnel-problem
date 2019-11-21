package com.tunnel.algorithm;

import java.util.ArrayList;

import com.tunnel.element.Graph;
import com.tunnel.element.Maze;
import com.tunnel.element.Vertex;

public class GraphConverter {
	
	private ArrayList<Vertex> stationList;
	private Graph graph;
	
	private static final int NONE = 0;
	private static final int LEFT = 1;
	private static final int RIGHT = 2;
	private static final int UP = 3;
	private static final int DOWN = 4;
	
	public GraphConverter(Maze maze) {
		this.createGraphFromMaze(maze);
	}
	
	public ArrayList<Vertex> getStationList() {
		return stationList;
	}

	public void setStationList(ArrayList<Vertex> stationList) {
		this.stationList = stationList;
	}
	
	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	private ArrayList<Vertex> createStationList(Maze maze) {
		ArrayList<Vertex> stationList = new ArrayList<>();
		int[][] points = maze.getPoints();
		
		for(int yPosition = 0; yPosition < maze.getNumberOfY(); yPosition++) {
			for(int xPosition = 0; xPosition < maze.getNumberOfX(); xPosition++) {
				if(points[yPosition][xPosition] == Maze.BLANK) {
					if(isStation(maze, xPosition, yPosition)) {
						int stationID = yPosition * maze.getNumberOfX() + xPosition;
						stationList.add(new Vertex(stationID, xPosition, yPosition));
					}
				}
			}
		}
		
		this.setStationList(stationList);
		
		return this.getStationList();
	}
	
	private boolean isStation(Maze maze, int rowPosition, int columnPosition) {
		int numberOfConnectPoint = calculateNumberOfConnectPoint(maze, rowPosition, columnPosition);
		if(isEndRoute(numberOfConnectPoint) || isIntersection(numberOfConnectPoint)) {
			return true;
		}
		return false;
	}
	
	// ends of routes have only one "." connects with current "."
	private boolean isEndRoute(int numberOfConnectPoint) {
		if (numberOfConnectPoint == 1)
			return true;
		return false;
	}
	
	// intersection have at least 3 "." connect with current "."
	private boolean isIntersection(int numberOfConnectPoint) {
		if (numberOfConnectPoint >= 3)
			return true;
		return false;
	}
	
	private int calculateNumberOfConnectPoint(Maze maze, int currentPointXPosition, int currentPointYPosition) {
		int[][] points = maze.getPoints();
		int numberOfConnectPoint = 0;
		
		if(currentPointXPosition > 0) {
			int leftPointXPosition = currentPointXPosition - 1;
			int leftPoint = points[currentPointYPosition][leftPointXPosition];
			if(leftPoint == Maze.BLANK) {
				numberOfConnectPoint++;
			}
		}
		
		if(currentPointXPosition < maze.getNumberOfX() - 1) {
			int rightPointXPosition = currentPointXPosition + 1;
			int rightPoint = points[currentPointYPosition][rightPointXPosition];
			if(rightPoint == Maze.BLANK) {
				numberOfConnectPoint++;
			}
		}
		
		if(currentPointYPosition > 0) {
			int upperPointYPosition = currentPointYPosition - 1;
			int upperPoint = points[upperPointYPosition][currentPointXPosition];
			if(upperPoint == Maze.BLANK) {
				numberOfConnectPoint++;
			}
		}
		
		if(currentPointYPosition < maze.getNumberOfY() - 1) {
			int lowerPointYPosition = currentPointYPosition + 1;
			int lowerPoint = points[lowerPointYPosition][currentPointXPosition];
			if(lowerPoint == Maze.BLANK) {
				numberOfConnectPoint++;
			}
		}
		
		return numberOfConnectPoint;
	}
	
	public Graph createGraphFromMaze(Maze maze) {
		Graph graph = new Graph();
		ArrayList<Vertex> stationList = this.createStationList(maze);
		
		for (Vertex stationBegin : stationList) {
			int stationBeginX = stationBegin.getX();
			int stationBeginY = stationBegin.getY();
			graph.addKeyStation(stationBegin.getVertexID());
			ArrayList<Integer> availableDirectionsToMoveNextFromStation = findAvailableDirectionsFormStation(maze, stationBegin);
			for (Vertex stationEnd : stationList) {
				int stationEndX = stationEnd.getX();
				int stationEndY = stationEnd.getY();
				
				boolean isEdgeExist = false;
				
				if(stationBeginX == stationEndX && stationBeginY == stationEndY) {
					continue;
				}
				else {
					
					checkAllDirections: for (int directionToMoveNextFromStation : availableDirectionsToMoveNextFromStation) {
						int pointX = stationBegin.getX();
						int pointY = stationBegin.getY();
						
						switch(directionToMoveNextFromStation) {
						case LEFT:
							pointX = pointX > 0 ? pointX - 1 : pointX;
							break;
						case RIGHT:
							pointX = pointX < maze.getNumberOfX() - 1 ? pointX + 1 : pointX;
							break;
						case UP:
							pointY = pointY > 0 ? pointY - 1 : pointY;
							break;
						case DOWN:
							pointY = pointY < maze.getNumberOfY() - 1 ? pointY + 1 : pointY;
							break;
						}
						boolean stopMoving = false;
						int previousMoveDirection = directionToMoveNextFromStation;
						
						do {
							int directionToMove = findAvailableDirectionsFormPointToMove(maze, pointX, pointY, previousMoveDirection);
							
							if(!isStation(maze, pointX, pointY)) {
								switch(directionToMove) {
								case LEFT:
									pointX = pointX > 0 ? pointX - 1 : pointX;
									break;
								case RIGHT:
									pointX = pointX < maze.getNumberOfX() - 1 ? pointX + 1 : pointX;
									break;
								case UP:
									pointY = pointY > 0 ? pointY - 1 : pointY;
									break;
								case DOWN:
									pointY = pointY < maze.getNumberOfY() - 1 ? pointY + 1 : pointY;
									break;
								}
								previousMoveDirection = directionToMove;
							}
							
							if(isStation(maze, pointX, pointY)) {
								if(pointX == stationEndX && pointY == stationEndY) {
									isEdgeExist = true;
									break checkAllDirections;
								}
								else {
									stopMoving = true;
								}
							}
							
						}
						while(!stopMoving);
					}
				}
				
				if(isEdgeExist) {
					graph.addEdgeToStation(stationBegin, stationEnd);
				}
			}
		}

		this.setGraph(graph);
		
		return graph;
	}
	
	private ArrayList<Integer> findAvailableDirectionsFormStation(Maze maze, Vertex stationBegin) {
		int[][] points = maze.getPoints();
		ArrayList<Integer> directions = new ArrayList<>();
		if(stationBegin.getX() > 0 && points[stationBegin.getY()][stationBegin.getX() - 1] == Maze.BLANK) {
			directions.add(LEFT);
		}
		if(stationBegin.getX() < maze.getNumberOfX() - 1 && points[stationBegin.getY()][stationBegin.getX() + 1] == Maze.BLANK) {
			directions.add(RIGHT);
		}
		if(stationBegin.getY() > 0 && points[stationBegin.getY() - 1][stationBegin.getX()] == Maze.BLANK) {
			directions.add(UP);
		}
		if(stationBegin.getY() < maze.getNumberOfY() - 1 && points[stationBegin.getY() + 1][stationBegin.getX()] == Maze.BLANK) {
			directions.add(DOWN);
		}
		return directions;
	}
	
	private int findAvailableDirectionsFormPointToMove(Maze maze, int pointX, int pointY, int previousMoveDirection) {
		int direction = NONE;
		int[][] points = maze.getPoints();
		
		if(previousMoveDirection != RIGHT && pointX > 0 && points[pointY][pointX - 1] == Maze.BLANK) {
			direction = LEFT;
		}
		else if(previousMoveDirection != LEFT && pointX < maze.getNumberOfX() - 1 && points[pointY][pointX + 1] == Maze.BLANK) {
			direction = RIGHT;
		}
		else if(previousMoveDirection != DOWN && pointY > 0 && points[pointY - 1][pointX] == Maze.BLANK) {
			direction = UP;
		}
		else if(previousMoveDirection != UP && pointY < maze.getNumberOfY() - 1 && points[pointY + 1][pointX] == Maze.BLANK) {
			direction = DOWN;
		}
		
		return direction;
	}
	
}
