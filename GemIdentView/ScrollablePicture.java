/*
    GemIdent v1.1b
    Interactive Image Segmentation Software via Supervised Statistical Learning
    http://gemident.com
    
    Copyright (C) 2009 Professor Susan Holmes & Adam Kapelner, Stanford University

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details:
    
    http://www.gnu.org/licenses/gpl-2.0.txt

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/

package GemIdentView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * This class creates an object that can display an image
 * to the screen with horizontal and vertical sliders to
 * navigate through the image. This was lifted straight from
 * the Java website and is therefore undocumented.
 * 
 * @author Sun's Java engineers
 * @see <a href="http://java.sun.com/docs/books/tutorial/uiswing/components/examples/ScrollablePicture.java">ScrollablePicture on Sun's Java website</a>
 */
@SuppressWarnings("serial")
public class ScrollablePicture extends JLabel implements Scrollable,MouseMotionListener {

	private int maxUnitIncrement = 1;
	private boolean missingPicture = false;
	private ImageIcon i;
	
	public ScrollablePicture(ImageIcon i, int m) {
		super(i);
		this.i = i;
		
		if (i == null) {
			missingPicture = true;
			setText("No picture found.");
			setHorizontalAlignment(CENTER);
			setOpaque(true);
			setBackground(Color.white);
		}
		maxUnitIncrement = m;
		
		//Let the user scroll by dragging to outside the window.
		setAutoscrolls(true); //enable synthetic drag events
		addMouseMotionListener(this); //handle mouse drags
	}
	
	public Graphics getImageIconGraphics(){
		return i.getImage().getGraphics();
	}
	
	//Methods required by the MouseMotionListener interface:
	public void mouseMoved(MouseEvent e) { }
	public void mouseDragged(MouseEvent e) {
		//The user is dragging us, so scroll!
		Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
		scrollRectToVisible(r);
	}
	
	public Dimension getPreferredSize() {
		if (missingPicture) {
		return new Dimension(320, 480);
		} else {
		return super.getPreferredSize();
		}
	}
	
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}
	
	public int getScrollableUnitIncrement(Rectangle visibleRect,int orientation,int direction) {
		//Get the current position.
		int currentPosition = 0;
		if (orientation == SwingConstants.HORIZONTAL) {
		currentPosition = visibleRect.x;
		} else {
		currentPosition = visibleRect.y;
		}
		
		//Return the number of pixels between currentPosition
		//and the nearest tick mark in the indicated direction.
		if (direction < 0) {
		int newPosition = currentPosition -
		  (currentPosition / maxUnitIncrement)
		   * maxUnitIncrement;
		return (newPosition == 0) ? maxUnitIncrement : newPosition;
		} else {
		return ((currentPosition / maxUnitIncrement) + 1)
		* maxUnitIncrement
		- currentPosition;
		}
	}
	
	public int getScrollableBlockIncrement(Rectangle visibleRect,int orientation,int direction) {
		if (orientation == SwingConstants.HORIZONTAL) {
		return visibleRect.width - maxUnitIncrement;
		} else {
		return visibleRect.height - maxUnitIncrement;
		}
	}
	
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}
	
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
	
	public void setMaxUnitIncrement(int pixels) {
		maxUnitIncrement = pixels;
	}
	
	public int getWidth(){
		return i.getImage().getWidth(null);
	}
	public int getHeight(){
		return i.getImage().getHeight(null);
	}
	
	public Dimension getDimensionOfImage(){
		return new Dimension(getWidth(), getHeight());
	}	
}