package com.tunnel.element;

public class Maze {
	
	private int numberOfY;
	private int numberOfX;
	private int[][] points;
	private int startStationID;
	private int endStationID;
	
	public static final int BLANK = 0;
	public static final int WALL = 1;
	
	public int getNumberOfY() {
		return numberOfY;
	}

	public void setNumberOfY(int numberOfY) {
		this.numberOfY = numberOfY;
	}

	public int getNumberOfX() {
		return numberOfX;
	}

	public void setNumberOfX(int numberOfX) {
		this.numberOfX = numberOfX;
	}

	public int[][] getPoints() {
		return points;
	}

	public void setPoints(int[][] points) {
		this.points = points;
	}

	public int getStartStationID() {
		return startStationID;
	}

	public void setStartStationID(int startStationID) {
		this.startStationID = startStationID;
	}

	public int getEndStationID() {
		return endStationID;
	}

	public void setEndStationID(int endStationID) {
		this.endStationID = endStationID;
	}
	
	public boolean isValid() {
		int[][] points = this.getPoints();
		
		for(int y = 0; y < this.numberOfY; y++) {
			for(int x = 0; x < this.numberOfX; x++) {
				if(points[y][x] == BLANK
						&&points[y + 1][x] == BLANK 
						&& points[y][x + 1] == BLANK 
						&& points[y + 1][x + 1] == BLANK) {
					return false;
				}
			}
		}
		return true;
		
	}
}
