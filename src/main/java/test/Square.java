package test;
import java.awt.Color;

import java.awt.Graphics;

public class Square {
	private int xloc, yloc; //location of square
	private int ssize;
	Color boardsqcolor = Color.GRAY;
	Color crosshaircolor = Color.PINK;
	private int squareState = PenteMain.EMPTY;
	private int myrow;
	private int mycolumn;
	public Square(int x, int y, int w, int row, int column){
		xloc = x;
		yloc = y;
		ssize = w;
		myrow = row;
		mycolumn = column;
		
	}
	
	public void drawMe(Graphics g){
		g.setColor(boardsqcolor);
		g.fillRect(xloc, yloc, ssize, ssize);
		
		//corsshair
		g.setColor(crosshaircolor);
		g.drawLine(xloc + (int)(ssize/2), yloc, xloc + (int)(ssize/2), yloc + ssize);
		
		g.setColor(crosshaircolor);
		g.drawLine(xloc, yloc+ (int)(ssize/2), xloc + ssize, yloc + (int)(ssize/2));
		
		
		if(squareState == PenteMain.BLACK){
			g.setColor(Color.BLACK);
			g.fillOval(xloc+1, yloc+1, ssize-2, ssize-2);
		}
		if(squareState == PenteMain.WHITE){
			g.setColor(Color.WHITE);
			g.fillOval(xloc+1, yloc+1, ssize-2, ssize-2);
		}
	}
	
	public int getState(){
		return squareState;
	}
	
	public void setState(int newState){
		squareState = newState;
	}
	
	public int getRow(){
		return myrow;
	}
	public int getColumn(){
		return mycolumn;
	}
	public boolean youClickedMe(int mouseX, int mouseY){
		boolean didYouClickMe = false;
		if(mouseX >= xloc && mouseX <= xloc+ssize && mouseY >= yloc && mouseY <= yloc+ssize){
			didYouClickMe = true;
		}
		return didYouClickMe;
	}
}
