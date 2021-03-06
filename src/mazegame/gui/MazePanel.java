/*
 * The MIT License
 *
 * Copyright 2015 Jeff Thompson.
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
package mazegame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import mazegame.Maze;
import mazegame.player.Player;
import mazegame.player.PlayerList;

/**
 *
 * @author Jeff
 */
public class MazePanel extends JPanel {
	
	Maze maze;
	
	boolean blockView = false;
	private int viewSize = 3;
	
	public MazePanel(Maze m){
		maze = m;
		setPreferredSize(new Dimension(m.getOptions().getSizeX()*Maze.CELL_SIZE+1,
				m.getOptions().getSizeY()*Maze.CELL_SIZE+1));
	}
	
	public void restart(){
		for(Player p : PlayerList.getPlayers())
			p.restart();
	}
	public void setMaze(Maze m){
		maze = m;
	}
	
	public void resize(){
		setPreferredSize(new Dimension(maze.getOptions().getSizeX()*Maze.CELL_SIZE+1,
				maze.getOptions().getSizeY()*Maze.CELL_SIZE+1));
		repaint();
	}
	
	public void setBlockView(boolean block){
		blockView = block;
	}
	public void increaseViewSize(){ 
		viewSize++;
		repaint();
	}
	public void decreaseViewSize(){ 
		if(viewSize > 1) viewSize--; 
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		maze.paint(g2);
		
		Area blackout = new Area(new Rectangle2D.Double(0,0,
				Maze.CELL_SIZE*maze.getOptions().getSizeX(),
				Maze.CELL_SIZE*maze.getOptions().getSizeY()));
		boolean allFinished = true;
		for(Player p : PlayerList.getPlayers()) {
			p.paint(g2);
			Ellipse2D view = new Ellipse2D.Double(
					(p.getPos().getX() - viewSize)*Maze.CELL_SIZE+Maze.CELL_SIZE/2, 
					(p.getPos().getY() - viewSize)*Maze.CELL_SIZE+Maze.CELL_SIZE/2,
					2*viewSize*Maze.CELL_SIZE, 2*viewSize*Maze.CELL_SIZE);
			Area viewArea = new Area(view);
			if(!p.hasFinished()) {
				blackout.subtract(viewArea);
				allFinished = false;
			}
		}
		g2.setColor(Color.black);
		
		if(!allFinished && blockView) g2.fill(blackout);
	}
}
