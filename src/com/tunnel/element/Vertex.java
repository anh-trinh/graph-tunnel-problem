package com.tunnel.element;

/*
 * Maze after convert to graph will use coordinate x, y as below:
 * 0,0
 *----------->(x)
 *| 
 *|
 *|
 *|
 *v
 *(y)
 */
public class Vertex {
	
	private int vertexID;
	private int x;
	private int y;
	
	public Vertex() {
		
	}
	
	public Vertex(int vertexID) {
		this.vertexID = vertexID;
	}
	
	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vertex(int vertexID, int x, int y) {
		this.vertexID = vertexID;
		this.x = x;
		this.y = y;
	}
	
	public int getVertexID() {
		return vertexID;
	}
	
	public void setVertexID(int vertexID) {
		this.vertexID = vertexID;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
}
