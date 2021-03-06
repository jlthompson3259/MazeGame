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
package mazegame;

import mazegame.player.PlayerList;
import mazegame.player.Player;
import java.awt.Color;
import mazegame.gui.MazeFrame;

/**
 *
 * @author Jeff
 */
public class MazeGame {
    public static void main(String[] args) {
		MazeOptions options = new MazeOptions(21,21);
		options.setAlgorithm(MazeOptions.Algorithm.WILSON);
		options.setGoal(new Point(10,10));
		Maze m = new Maze(options);
		PlayerList.add(new Player(m,Color.red));
		PlayerList.add(new Player(m,Color.blue,new Point(20,20)));
	    MazeFrame mf = new MazeFrame(m);
    }
}
