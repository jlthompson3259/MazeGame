/*
 * The MIT License
 *
 * Copyright 2015 Jeffery Thompson.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package mazegame.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Stack;
import mazegame.Direction;
import mazegame.Maze;
import mazegame.Maze.Cell;
import mazegame.Point;

/**
 * A class to track a Player's path through the maze. Also tracks any paths the
 * Player backtracks from, displayed in a different color.
 * @author Jeffery Thompson
 */
public class Path {
	private Stack<PathPoint> curPath;
	private HashSet<PathPoint> backtrack;
	
	private Color pathColor;
	private Color backtrackColor;
	
	private int pathWidth = (Maze.CELL_WIDTH-1)/4;
	
	public Path(Color pc, Color btc){
		curPath = new Stack();
		backtrack = new HashSet();
		
		pathColor = pc;
		backtrackColor = btc;
	}
	
	public void add(Point p, Direction dir){
		if(!curPath.isEmpty() && p.getNeighbor(dir).equals(curPath.peek().getPoint())){
			curPath.pop();
			backtrack.add(new PathPoint(p, dir));
		}
		else {
			PathPoint point = new PathPoint(p.getNeighbor(dir), dir);
			if(backtrack.contains(point)) backtrack.remove(point);
			curPath.push(new PathPoint(p, dir));
		}
	}
	
	public void paint(Graphics2D g){
		g.setColor(pathColor);
		for(PathPoint pp : curPath){
			pp.paint(g);
		}
		g.setColor(backtrackColor);
		for(PathPoint pp : backtrack){
			pp.paint(g);
		}
	}
	
	/**
	 * Stores a single cell and the direction the Player moved out of that cell.
	 */
	public class PathPoint {
		private Point point;
		private Direction dir;
		
		public PathPoint(Point p, Direction d){
			point = p;
			dir = d;
		}
		public Point getPoint(){
			return point;
		}
		public Direction getDirection(){
			return dir;
		}
		
		@Override
		public int hashCode(){
			return (point.getX() <<16)+point.getY();
		}
		
		@Override
		public boolean equals(Object o){
			if(!o.getClass().equals(PathPoint.class)) return false;
			PathPoint p = (PathPoint)o;
			return p.point.equals(this.point);
		}
		
		public void paint(Graphics2D g){
			g.fillRect(point.getX()*Maze.CELL_WIDTH + (Maze.CELL_WIDTH-pathWidth-1)/2+1,
				   point.getY()*Maze.CELL_HEIGHT + (Maze.CELL_HEIGHT-pathWidth-1)/2+1,
				   pathWidth, pathWidth);
			switch(dir){
				case NORTH:
					g.fillRect(point.getX()*Maze.CELL_WIDTH + (Maze.CELL_WIDTH-pathWidth-1)/2+1,
							point.getY()*Maze.CELL_HEIGHT - (Maze.CELL_HEIGHT-pathWidth-1)/2,
							pathWidth, Maze.CELL_HEIGHT-pathWidth);
					break;
				case SOUTH:
					g.fillRect(point.getX()*Maze.CELL_WIDTH + (Maze.CELL_WIDTH-pathWidth-1)/2+1,
							(point.getY()+1)*Maze.CELL_HEIGHT - (Maze.CELL_HEIGHT-pathWidth-1)/2,
							pathWidth, Maze.CELL_HEIGHT-pathWidth);
					break;
				case EAST:
					g.fillRect((point.getX()+1)*Maze.CELL_WIDTH - (Maze.CELL_WIDTH-pathWidth-1)/2,
							point.getY()*Maze.CELL_HEIGHT + (Maze.CELL_HEIGHT-pathWidth-1)/2 +1,
							Maze.CELL_WIDTH-pathWidth, pathWidth);
					break;
				case WEST:
					g.fillRect(point.getX()*Maze.CELL_WIDTH - (Maze.CELL_WIDTH-pathWidth-1)/2,
							point.getY()*Maze.CELL_HEIGHT + (Maze.CELL_HEIGHT-pathWidth-1)/2 + 1,
							Maze.CELL_WIDTH-pathWidth, pathWidth);
					break;
			}
		}
	}
}