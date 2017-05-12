package test;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PenteGameBoard extends JPanel implements MouseListener, ActionListener{
	int bwidthpix;
	int bwidthsq;
	int sqwidthpixxel;
	int currentTurn;
	String loptur = null;
	public int whiteCaps = 0;
	public int blackCaps = 0;
	boolean playFilip = false;
	Filip fil = null;
	int filcolor;
	FireBasePente fr = new FireBasePente();
	//Color boardsqcolor = new Color(150, 11, 51);
	Square [][] theBoard;
	public PenteGameBoard(int BWPixel, int BWSquares){
		t.start();
		bwidthpix = BWPixel;
		bwidthsq = BWSquares;
		this.setSize(bwidthpix, bwidthpix);
		this.setBackground(Color.BLACK);
		int bwidthpix2 = bwidthpix-2*(bwidthsq-1);
		sqwidthpixxel = (int)bwidthpix2/bwidthsq;
		theBoard = new Square[bwidthsq][bwidthsq];
		for(int row=0;row<bwidthsq;row++){
			for(int column = 0; column<bwidthsq; column++){
				theBoard[row][column] = new Square((int)(row*sqwidthpixxel)+2*row, (int)(column*sqwidthpixxel)+2*column, sqwidthpixxel, row, column);
			}
		}
		String playComp = JOptionPane.showInputDialog("do you want to play the computer");
		if(playComp.toLowerCase().contains("y")){
			playFilip = true;
			fil = new Filip(this, PenteMain.BLACK);
			filcolor = PenteMain.BLACK;
		}
		this.addMouseListener(this);
		theBoard[(int)(bwidthsq/2)][(int)(bwidthsq/2)].setState(PenteMain.BLACK);
		
		changeTurn();
		repaint();
		if(fr.color == "Black"){
			alert("You are black stone, please wait for a white stone to appear before you make a move");
			
		}else{
			alert("You are white stone, please make a move then wait for black");
		}
	}
	
	public void changeTurn(){
		if(currentTurn == PenteMain.WHITE){
			currentTurn = PenteMain.BLACK;
		}else{
			currentTurn = PenteMain.WHITE;
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, bwidthpix, bwidthpix);
		
		for(int row=0;row<bwidthsq;row++){
			for(int column = 0; column<bwidthsq; column++){
				theBoard[row][column].drawMe(g);
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int mouseX = e.getX();
		int mouseY = e.getY();
		Square s = findSquare(mouseX, mouseY);
		if(s != null){
			String ct = "White";
			if(currentTurn == PenteMain.BLACK){
				ct = "Black";
			}
			if(s.getState() == PenteMain.EMPTY && ct == fr.color){
				this.finishMove(s);
				this.repaint();
				this.requestFocus();
				fr.MakeNextMove(s.getRow(), s.getColumn());
//				this.finishMove(fr.readString(toMove, theBoard));
//				this.repaint();
//				this.requestFocus();
				if(playFilip){
					if(currentTurn == filcolor){
						Square fs = fil.NextMove(s.getRow(), s.getColumn());
						theBoard[fs.getRow()][fs.getColumn()].setState(filcolor);
						checkForCapture(fs);
						checkFor5Win(fs);
						changeTurn();
						repaint();
						this.requestFocus();
					}
				}
			}else{
				JOptionPane.showMessageDialog(null, "it would be appreciated if one did not click in a space in which a circle is previously located within");
			}
		}else{
			JOptionPane.showMessageDialog(null, "it would be appreciated if one did not click in that vicinity");
		}
		
//		boolean foundClickedSquare = false;
//		int xcounter = 0;
//		int ycounter = 0;
//		while(foundClickedSquare == false && ycounter < bwidthsq){
//			System.out.println("clicked at " + xcounter + "\t" + ycounter);
//			while(xcounter < bwidthsq){
//				System.out.println("clicked at " + xcounter + "\t" + ycounter);
//				if(theBoard[ycounter][xcounter].youClickedMe(mouseX, mouseY) == true){
//					System.out.println("clicked at " + xcounter + "\t" + ycounter);
//					if(theBoard[ycounter][xcounter].getState() == PenteMain.EMPTY){
//						theBoard[ycounter][xcounter].setState(currentTurn);
//						changeTurn();
//						repaint();
//						
//					}
//					foundClickedSquare = true;
//					
//				}
//				xcounter++;
//				
//			}
//			ycounter++;
//		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public Square findSquare(int mouseX, int mouseY){
		Square clickedSQ = null;
		for(int row=0;row<bwidthsq;row++){
			for(int column = 0; column<bwidthsq; column++){
				if(theBoard[row][column].youClickedMe(mouseX, mouseY) == true){
					clickedSQ = theBoard[row][column];
				}
			}
		}
		return clickedSQ;
	}
	public void checkForCapture(Square s){
		int row = s.getRow();
		int column = s.getColumn();
		int opposite = s.getState() * -1;
		//Check x direction
		if(column < bwidthsq - 3){
			if(opposite == theBoard[row][column+1].getState() && opposite == theBoard[row][column+2].getState() && s.getState() == theBoard[row][column+3].getState()){
				theBoard[row][column+1].setState(PenteMain.EMPTY);
				theBoard[row][column+2].setState(PenteMain.EMPTY);
				checkForCaptureWin(s);
			}
		}
		if(column > 2){
			if(opposite == theBoard[row][column-1].getState() && opposite == theBoard[row][column-2].getState() && theBoard[row][column].getState() == theBoard[row][column-3].getState()){
				theBoard[row][column-1].setState(PenteMain.EMPTY);
				theBoard[row][column-2].setState(PenteMain.EMPTY);
				checkForCaptureWin(s);
			}
		}
		
		//Check y Directions and diagnols
		if(row < bwidthsq - 3){
			if(opposite == theBoard[row+1][column].getState() && opposite == theBoard[row+2][column].getState() && theBoard[row][column].getState() == theBoard[row+3][column].getState()){
				theBoard[row+1][column].setState(PenteMain.EMPTY);
				theBoard[row+2][column].setState(PenteMain.EMPTY);
				checkForCaptureWin(s);
			}
			if(column > 2){
				if(opposite == theBoard[row+1][column-1].getState() && opposite == theBoard[row+2][column-2].getState() && theBoard[row][column].getState() == theBoard[row+3][column-3].getState()){
					theBoard[row+1][column-1].setState(PenteMain.EMPTY);
					theBoard[row+2][column-2].setState(PenteMain.EMPTY);
					checkForCaptureWin(s);
				}
			}
			if(column < bwidthsq - 3){
				if(opposite == theBoard[row+1][column+1].getState() && opposite == theBoard[row+2][column+2].getState() && theBoard[row][column].getState() == theBoard[row+3][column+3].getState()){
					theBoard[row+1][column+1].setState(PenteMain.EMPTY);
					theBoard[row+2][column+2].setState(PenteMain.EMPTY);
					checkForCaptureWin(s);
				}
			}
		}
		if(row > 2){
			if(opposite == theBoard[row-1][column].getState() && opposite == theBoard[row-2][column].getState() && theBoard[row][column].getState() == theBoard[row-3][column].getState()){
				theBoard[row-1][column].setState(PenteMain.EMPTY);
				theBoard[row-2][column].setState(PenteMain.EMPTY);
				checkForCaptureWin(s);
			}
			if(column > 2){
				if(opposite == theBoard[row-1][column-1].getState() && opposite == theBoard[row-2][column-2].getState() && theBoard[row][column].getState() == theBoard[row-3][column-3].getState()){
					theBoard[row-1][column-1].setState(PenteMain.EMPTY);
					theBoard[row-2][column-2].setState(PenteMain.EMPTY);
					checkForCaptureWin(s);
				}
			}
			if(column < bwidthsq - 3){
				if(opposite == theBoard[row-1][column+1].getState() && opposite == theBoard[row-2][column+2].getState() && theBoard[row][column].getState() == theBoard[row-3][column+3].getState()){
					theBoard[row-1][column+1].setState(PenteMain.EMPTY);
					theBoard[row-2][column+2].setState(PenteMain.EMPTY);
					checkForCaptureWin(s);
				}
			}
		}
	}
	public void checkForCaptureWin(Square s){
		int state = s.getState();
		int num;
		String stateStr;
		if(state == PenteMain.WHITE){
			whiteCaps++;
			num = whiteCaps;
			stateStr = "White";
		}else{
			blackCaps++;
			num = blackCaps;
			stateStr = "Black";
		}
		if(num >= 5){
			alert(stateStr + " Wins!");
			for(int row=0;row<bwidthsq;row++){
				for(int column = 0; column<bwidthsq; column++){
					theBoard[row][column].setState(PenteMain.EMPTY);
				}
			}
			blackCaps = 0;
			whiteCaps = 0;
			theBoard[(int)(bwidthsq/2)][(int)(bwidthsq/2)].setState(PenteMain.BLACK);
			currentTurn = PenteMain.BLACK;
		}
	}
	public void checkFor5Win(Square s){
		if(winY(s) >=4 || winX(s) >= 4 || winD1(s) >= 4 || winD2(s) >= 4){
			repaint();
			String stateStr;
			if(s.getState() == PenteMain.WHITE){
				stateStr = "White";
			}else{
				stateStr = "Black";
			}
			reset(stateStr);
			repaint();
		}
		
	}
	public int winY(Square s){
		int row = s.getRow();
		int column = s.getColumn();
		boolean contin = true;
		int i = 0;
		if(row > 3){
			int c2 = 0;
			while(contin == true && c2 <= 3){
				if(theBoard[row-c2-1][column].getState() == s.getState()){
					i++;
					c2++;
				}else{
					contin = false;
				}
			}
		}
		contin = true;
		if(row < bwidthsq - 4){
			int c = 0;
			while(contin == true && c < 4){
				if(theBoard[row+c+1][column].getState() == s.getState()){
					i++;
					c++;
				}else{
					contin = false;
				}
			}
		}
		return i;
	}
	public int winX(Square s){
		int row = s.getRow();
		int column = s.getColumn();
		boolean contin = true;
		int i = 0;
		if(column > 3){
			int c2 = 0;
			while(contin == true && c2 < 4){
				if(theBoard[row][column-c2-1].getState() == theBoard[row][column-c2].getState()){
					i++;
					c2++;
				}else{
					contin = false;
				}
			}
		}
		contin = true;
		if(column < bwidthsq - 4){
			int c = 0;
			while(contin == true && c < 4){
				if(theBoard[row][column+c+1].getState() == theBoard[row][column+c].getState()){
					i++;
					c++;
				}else{
					contin = false;
				}
			}
		}
		return i;
	}
	public int winD1(Square s){
		int row = s.getRow();
		int column = s.getColumn();
		boolean contin = true;
		int i = 0;
		if(row > 3 && column < bwidthsq - 4){
			int c2 = 0;
			while(contin == true && c2 < 4){
				if(theBoard[row-c2-1][column+c2+1].getState() == theBoard[row-c2][column+c2].getState()){
					i++;
					c2++;
				}else{
					contin = false;
				}
			}
		}
		contin = true;
		if(row < bwidthsq - 4 && column > 3){
			int c = 0;
			while(contin == true && c < 4){
				if(theBoard[row+c+1][column-c-1].getState() == theBoard[row+c][column-c].getState()){
					i++;
					c++;
				}else{
					contin = false;
				}
			}
		}
		return i;
	}
	public int winD2(Square s){
		int row = s.getRow();
		int column = s.getColumn();
		boolean contin = true;
		int i = 0;
		if(row > 3 && column > 3){
			int c2 = 0;
			while(contin == true && c2 < 4){
				if(theBoard[row-c2-1][column-c2-1].getState() == theBoard[row-c2][column-c2].getState()){
					i++;
					c2++;
				}else{
					contin = false;
				}
			}
		}
		contin = true;
		if(row < bwidthsq - 4 && column < bwidthsq - 4){
			int c = 0;
			while(contin == true && c < 4){
				if(theBoard[row+c+1][column+c+1].getState() == theBoard[row+c][column+c].getState()){
					i++;
					c++;
				}else{
					contin = false;
				}
			}
		}
		return i;
	}
	
	public void reset(String stateStr){
		alert(stateStr + " Wins!");
		for(int row=0;row<bwidthsq;row++){
			for(int column = 0; column<bwidthsq; column++){
				theBoard[row][column].setState(PenteMain.EMPTY);
			}
		}
		whiteCaps = 0;
		blackCaps = 0;
		theBoard[(int)(bwidthsq/2)][(int)(bwidthsq/2)].setState(PenteMain.BLACK);
		currentTurn = PenteMain.BLACK;
	}
	
	public int getWidthSq(){
		return bwidthsq;
	}
	public Square[][] getBoard(){
		return theBoard;
	}
	
	private void finishMove(Square s){
		s.setState(currentTurn);
		checkForCapture(s);
		checkFor5Win(s);
		changeTurn();
		repaint();
	}

	public void alert(String m){
		JOptionPane.showMessageDialog(null, m);
	}
	Timer t = new Timer(500, this);
	@Override
	public void actionPerformed(ActionEvent e) {
		if(loptur != null){
			if(loptur != fr.ListenNextMove()){
				String oc = "White";
				if(currentTurn == PenteMain.WHITE){
					oc = "Black";
				}if(oc == fr.color){
					loptur = fr.ListenNextMove();
					this.finishMove(fr.readString(loptur, theBoard));
					this.repaint();
					this.requestFocus();
				}
				
			}
		}else{
			loptur = fr.ListenNextMove();
		}
		
		
	}
	
}
