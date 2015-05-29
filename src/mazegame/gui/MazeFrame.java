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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import mazegame.Direction;
import mazegame.Maze;

/**
 *
 * @author Jeff
 */
public class MazeFrame extends JFrame {
	MazePanel panel;
	Maze maze;
	public MazeFrame(Maze m) {
		maze = m;
		panel = new MazePanel(m);
		this.setContentPane(panel);
		
		this.setJMenuBar(createMenuBar());
		this.addKeyListener(new KeyboardInput());
		panel.addKeyListener(new KeyboardInput());
		//this.setMinimumSize(new Dimension(300,300));
		this.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.requestFocus();
	}
        
	public final JMenuBar createMenuBar(){
		JMenuBar bar = new JMenuBar();

		JMenu maze = new JMenu("Maze");
		MenuListener listener = new MenuListener();
		
		JMenuItem newMaze = new JMenuItem("New...");
		newMaze.setMnemonic(KeyEvent.VK_N);
		newMaze.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newMaze.addActionListener(listener);
		maze.add(newMaze);
		
		JMenuItem restart = new JMenuItem("Restart");
		restart.setMnemonic(KeyEvent.VK_R);
		restart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		restart.addActionListener(listener);
		maze.add(restart);
		
		JMenu view = new JMenu("View");
		JMenuItem zoomIn = new JMenuItem("Zoom in");
		zoomIn.addActionListener(listener);
		view.add(zoomIn);
		
		JMenuItem zoomOut = new JMenuItem("Zoom out");
		zoomOut.addActionListener(listener);
		view.add(zoomOut);
		
		bar.add(maze);
		bar.add(view);
		return bar;
	}
	
	public void resize(){
		panel.resize();
		this.pack();
	}
	
	public class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			if(ae.getActionCommand().equals("New..."))
			{
				panel.newMaze();
			}
			if(ae.getActionCommand().equals("Restart"))
			{
				panel.restartMaze();
			}
			if(ae.getActionCommand().equals("Zoom in"))
			{
				Maze.CELL_WIDTH *= 1.25;
				Maze.CELL_HEIGHT *= 1.25;
				resize();
			}
			if(ae.getActionCommand().equals("Zoom out"))
			{
				Maze.CELL_WIDTH /= 1.25;
				Maze.CELL_HEIGHT /= 1.25;
				resize();
			}
		}
	}
	
	public class KeyboardInput implements KeyListener{

			@Override
			public void keyTyped(KeyEvent ke) {
				
			}

			@Override
			public void keyPressed(KeyEvent ke) {
				switch(ke.getKeyCode()) {
					case KeyEvent.VK_UP:
					case KeyEvent.VK_W:
						maze.getPlayer().move(Direction.NORTH);
						break;
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_S:
						maze.getPlayer().move(Direction.SOUTH);
						break;
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_A:
						maze.getPlayer().move(Direction.WEST);
						break;
					case KeyEvent.VK_RIGHT:
					case KeyEvent.VK_D:
						maze.getPlayer().move(Direction.EAST);
						panel.repaint();
						break;
					default:
						break;
				}
				
				maze.update();
				panel.repaint();
			}

			@Override
			public void keyReleased(KeyEvent ke) {
				
			}
			
		}
}
