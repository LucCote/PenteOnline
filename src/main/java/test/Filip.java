package test;
import java.util.ArrayList;
import java.util.Random;

public class Filip {
	PenteGameBoard board;
	Square [][] boardArray;
	int stoneColor;
	int bwidthsq;
	int rowMove;
	int colMove;
	int lastMoveR;
	int lastMoveC;
	int checkFor1 = 1;
	int checkForEmpty = 2;
	int checkAll = 3;
	int returnD2 = 3;
	int returnD1 = 2;
	int returnY = 1;
	int returnX = 0;
	int returnDir = 0;
	Random randObject = new Random();
	ArrayList<Square> black = new ArrayList<Square>();
	ArrayList<Square> white = new ArrayList<Square>();
	public Filip(PenteGameBoard b, int color){
		board = b;
		stoneColor = color;
		bwidthsq = b.getWidthSq();
		boardArray = b.getBoard();
	}
	public Square NextMove(int lastMoveRow, int lastMoveCol){
		black.clear();
		white.clear();
		//System.out.println(black.size());
		for(int row=0;row<bwidthsq;row++){
			for(int column = 0; column<bwidthsq; column++){
				if(boardArray[row][column].getState() == PenteMain.BLACK){
					//System.out.println(boardArray[row][column].getRow() + "," + boardArray[row][column].getColumn());
					black.add(boardArray[row][column]);
				}else if(boardArray[row][column].getState() == PenteMain.WHITE){
					white.add(boardArray[row][column]);
				}
			}
		}
//		if(defense(3, false)!=null){
//			System.out.println("defense 4");
//			return defense(3, false);
//		}else if(defense(2, true)!=null){
//			System.out.println("defense 3 true");
//			return defense(2, true);
//		}else if(defense(1, false)!=null){
//			System.out.println("defense 2");
//			return defense(1, false);
//		}else if(defense(2, false)!=null){
//			System.out.println("defense 3 false");
//			return defense(2, false);
//		}else if(offense(3, this.checkAll)!=null){
//			System.out.println("offense 4");
//			return offense(3, this.checkAll);
//		}else if(offense(2, this.checkForEmpty) != null){
//			System.out.println("offense 3");
//			return offense(2, this.checkForEmpty);
//		}else if(offense(1, this.checkFor1)!=null){
//			System.out.println("offense 2");
//			return offense(1, this.checkFor1);
//		}else if(offense(1, this.checkAll)!=null){
//			System.out.println("offense 2");
//			return offense(1, this.checkAll);
//		}else if(offense(2, this.checkAll) != null){
//			System.out.println("offense 3");
//			return offense(2, this.checkAll);
//		}else if(offense(0, this.checkAll)!=null){
//			System.out.println("offense 1");
//			return offense(0, this.checkAll);
//		}else if(defense(0, false)!=null){
//			System.out.println("defense 1");
//			return defense(0, false);
//		}
//		else{
//			System.out.println("random");
//			int newMoverow = (int)(Math.random()*19);
//			int newMovecol = (int)(Math.random()*19);
//			while(boardArray[newMoverow][newMovecol].getState() != PenteMain.EMPTY){
//				//System.out.println(newMoverow + "\t" + newMovecol);
//				newMoverow = (int)(Math.random()*19);
//				newMovecol = (int)(Math.random()*19);
//			}
//			return boardArray[newMoverow][newMovecol];
//		}
		if(offense(3, this.checkAll)!=null){
			System.out.println("offense 4");
			return offense(3, this.checkAll);
		}else if(defense(3, false)!=null){
			System.out.println("defense 4");
			return defense(3, false);
		}else if(defense(2, true)!=null){
			System.out.println("defense 3 true");
			return defense(2, true);
		}else if(offense(2, this.checkForEmpty) != null){
			System.out.println("offense 3");
			return offense(2, this.checkForEmpty);
		}else if(offense(1, this.checkFor1)!=null){
			System.out.println("offense 2");
			return offense(1, this.checkFor1);
		}else if(defense(1, false)!=null){
			System.out.println("defense 2");
			return defense(1, false);
		}else if(defense(2, false)!=null){
			System.out.println("defense 3 false");
			return defense(2, false);
		}else if(offense(1, this.checkAll)!=null){
			System.out.println("offense 2");
			return offense(1, this.checkAll);
		}else if(offense(2, this.checkAll) != null){
			System.out.println("offense 3");
			return offense(2, this.checkAll);
		}else if(offense(0, this.checkAll)!=null){
			System.out.println("offense 1");
			return offense(0, this.checkAll);
		}else if(defense(0, false)!=null){
			System.out.println("defense 1");
			return defense(0, false);
		}else{
			System.out.println("random");
			int newMoverow = (int)(Math.random()*19);
			int newMovecol = (int)(Math.random()*19);
			while(boardArray[newMoverow][newMovecol].getState() != PenteMain.EMPTY){
				//System.out.println(newMoverow + "\t" + newMovecol);
				newMoverow = (int)(Math.random()*19);
				newMovecol = (int)(Math.random()*19);
			}
			return boardArray[newMoverow][newMovecol];
		}
	}
	public Square randSq(int c){
		return offense(c-1, 0);
		
		
	}
	public Square defense(int chek, boolean openOnly){
		if(white.size() <= 0){
			return null;
		}
		int best = getBestLine(white.get(0));
		int bi = 0;
		for(int count = 1; count < white.size(); count++){
			if(getBestLine(white.get(count)) > best && openOnly == false){
				bi = count;
				best = getBestLine(white.get(count));
			}else if(this.getOpenBest(white.get(count)) > best && openOnly == true){
				bi = count;
				best = getOpenBest(white.get(count));
			}
		}
		if(best == chek){
			if(board.winY(white.get(bi)) == best && best == board.winX(white.get(bi)) && best == board.winD1(white.get(bi)) && best == board.winD2(white.get(bi)) && best == this.skipsD1(white.get(bi)) && best == this.skipsD2(white.get(bi)) && best == this.skipsX(white.get(bi)) && best == this.skipsY(white.get(bi))){
				int chooser = (int)(Math.random()*4);
				if(chooser == 0){
					return placeY(white.get(bi), bi, chek);	
				}else if(chooser == 1){
					return placeX(white.get(bi), bi, chek);	
				}else if(chooser == 2){
					return placeD1(white.get(bi), bi, chek);
				}else{
					return placeD2(white.get(bi), bi, chek);
				}
			}
			else if(board.winY(white.get(bi)) == best && returnDir == this.returnY){
				System.out.println("Placing y");
				return placeY(white.get(bi), bi, chek);		
			}else if(board.winD1(white.get(bi)) == best && returnDir == this.returnD1){
				System.out.println("placing d1");
				return placeD1(white.get(bi), bi, chek);
			}else if(board.winD2(white.get(bi)) == best && returnDir == this.returnD2){
				System.out.println("placingD2");
				return placeD2(white.get(bi), bi, chek);
			}else if(board.winX(white.get(bi)) == best && returnDir == this.returnX){
				System.out.println("Placing X");
				return placeX(white.get(bi), bi, chek);
			}else if(this.skipsY(white.get(bi)) == best && returnDir == this.returnY){
				System.out.println("skips y");
				return placeY(white.get(bi), bi, chek);
			}else if(this.skipsX(white.get(bi)) == best && returnDir == this.returnX){
				System.out.println("skips x");
				return placeX(white.get(bi), bi, chek);
			}else if(this.skipsD1(white.get(bi)) == best && returnDir == this.returnD1){
				System.out.println("skips d1");
				return placeD1(white.get(bi), bi, chek);
			}else{
				System.out.println("skips d2");
				return placeD2(white.get(bi), bi, chek);
			}
		}else{
			return null;
		}
	}
	public Square offense(int chek, int howManyGuards){
		if(black.size() >= 1){
//			if(chek == 1){
//				return defense(chek);
//			}
			//System.out.println(black.size() + "\n");
			//black = sortList(black);
		}else{
			return null;
		}
		
		int best = getBestLine(black.get(0));
		int bi = 0;
		for(int count = 1; count < black.size(); count++){
			if(getBestLine(black.get(count)) > best && howManyGuards == this.checkAll){
				bi = count;
				best = getBestLine(black.get(count));
			}else if(this.getOpenBest(black.get(count)) > best && howManyGuards == this.checkForEmpty){
				bi = count;
				best = getOpenBest(black.get(count));
			}else if(this.getHalfBest(black.get(count)) > best && howManyGuards == this.checkFor1){
				bi = count;
				best = getOpenBest(black.get(count));
			}
		}
		if(best == chek){
			if(board.winY(black.get(bi)) == best && best == board.winX(black.get(bi)) && best == board.winD1(black.get(bi)) && best == board.winD2(black.get(bi)) && best == this.skipsD1(black.get(bi)) && best == this.skipsD2(black.get(bi)) && best == this.skipsX(black.get(bi)) && best == this.skipsY(black.get(bi))){
				int chooser = (int)(Math.random()*4);
				if(chooser == 0){
					return placeY(black.get(bi), bi, chek);	
				}else if(chooser == 1){
					return placeX(black.get(bi), bi, chek);	
				}else if(chooser == 2){
					return placeD1(black.get(bi), bi, chek);	
				}else{
					return placeD2(black.get(bi), bi, chek);
				}
			}
			else if(returnDir == this.returnY && board.winY(black.get(bi)) == best/*|| this.skipsY(black.get(bi)) == best*/){
				return placeY(black.get(bi), bi, chek);		
			}else if(returnDir == this.returnD1 && board.winD1(black.get(bi)) == best/*|| this.skipsD1(black.get(bi)) == best*/){
				return placeD1(black.get(bi), bi, chek);
			}else if(returnDir == this.returnD2 && board.winD2(black.get(bi)) == best/*|| this.skipsD2(black.get(bi)) == best*/){
				return placeD2(black.get(bi), bi, chek);
			}else if(returnDir == this.returnX && board.winX(black.get(bi)) == best){
				return placeX(black.get(bi), bi, chek);
			}else if(howManyGuards == this.checkAll){
				if(returnDir == this.returnY){
					return placeY(black.get(bi), bi, chek);
				}else if(returnDir == this.returnX){
					return placeX(black.get(bi), bi, chek);
				}else if(returnDir == this.returnD1){
					return placeD1(black.get(bi), bi, chek);
				}else{
					return placeD2(black.get(bi), bi, chek);
				}
			}else return null;
		}else{
			return null;
			
		}
		
	}
	public boolean checkFor5Open(int rorr, int colr, Square s){
		int r = 0;
		int count = 0;
		boolean contin = true;
		while((boardArray[s.getRow()+(r*rorr)][s.getColumn()+(r*colr)].getState() == s.getState() || boardArray[s.getRow()+(r*rorr)][s.getColumn()+(r*colr)].getState() == PenteMain.EMPTY) && contin == true){
			if(s.getRow()+((r+1)*rorr) >= 0 && s.getRow()+((r+1)*rorr) <= bwidthsq-1 && s.getColumn()+((r+1)*colr) >=0 && s.getColumn()+((r+1)*colr) < bwidthsq){
				r++;
				count++;
			}else{
					contin = false;
			}
		}
		
		contin = true;
		r = 0;
		while((boardArray[s.getRow()-(r*rorr)][s.getColumn()-(r*colr)].getState() == s.getState() || boardArray[s.getRow()-(r*rorr)][s.getColumn()-(r*colr)].getState() == PenteMain.EMPTY) && contin == true){
			if(s.getRow()-((r+1)*rorr) >= 0 && s.getRow()-((r+1)*rorr) <= bwidthsq-1 && s.getColumn()-((r+1)*colr) >=0 && s.getColumn()-((r+1)*colr) < bwidthsq){
					r++;
					count++;
			}else{
				contin = false;
			}
			
		}	
		if(count >= 4){
			return true;
		}else{
			return false;
		}
			
				
		
	}
	public boolean checkOpenSq(int rorr, int colr, Square s){
		int r = 0;
		boolean contin = true;
		while(boardArray[s.getRow()+(r*rorr)][s.getColumn()+(r*colr)].getState() == s.getState() && contin == true){
			if(s.getRow()+((r+1)*rorr) >= 0 && s.getRow()+((r+1)*rorr) <= bwidthsq-1 && s.getColumn()+((r+1)*colr) >=0 && s.getColumn()+((r+1)*colr) < bwidthsq){
				r++;
			}else{
					contin = false;
			}
		}
		if(boardArray[s.getRow()+(r*rorr)][s.getColumn()].getState() == PenteMain.EMPTY){
			return true;
		}else{
			contin = true;
			r = 0;
			while(boardArray[s.getRow()-(r*rorr)][s.getColumn()-(r*colr)].getState() == s.getState() && contin == true){
				if(s.getRow()-((r+1)*rorr) >= 0 && s.getRow()-((r+1)*rorr) <= bwidthsq-1 && s.getColumn()-((r+1)*colr) >=0 && s.getColumn()-((r+1)*colr) < bwidthsq){
						r++;
				}else{
					contin = false;
				}
				
				
			}
			if(boardArray[s.getRow()-(r*rorr)][s.getColumn()].getState() == PenteMain.EMPTY){
				return true;
			}else{
				return false;
				}
				
		}
	}
	public int getBestLine(Square s){
		int bestNum = 0;
		if(board.winY(s) > bestNum){
			if(checkOpenSq(1, 0, s) && checkFor5Open(1, 0, s)){
				returnDir = this.returnY;
				bestNum = board.winY(s);
			}
		}
		if(board.winX(s) > bestNum){
			if(checkOpenSq(0, 1, s) && checkFor5Open(0, 1, s)){
				returnDir = this.returnX;
				bestNum = board.winX(s);
			}
		}if(board.winD1(s) > bestNum){
			if(checkOpenSq(-1, 1, s) && checkFor5Open(-1, 1, s)){
				returnDir = this.returnD1;
				bestNum = board.winD1(s);
			}
			
		}if(board.winD2(s) > bestNum){
			if(checkOpenSq(1,1,s) && checkFor5Open(1, 1, s)){
				returnDir = this.returnD2;
				bestNum = board.winD2(s);
			}
		}if(this.skipsY(s) > bestNum){
			if(checkOpenSq(1,0,s) && checkFor5Open(1, 0, s)){
				returnDir = this.returnY;
				bestNum = this.skipsY(s);
			}
		}if(this.skipsX(s) > bestNum){
			if(checkOpenSq(0,1,s) && checkFor5Open(0, 1, s)){
				returnDir = this.returnX;
				bestNum = this.skipsX(s);
			}
		}if(this.skipsD1(s) > bestNum){
			if(checkOpenSq(-1,1,s) && checkFor5Open(-1, 1, s)){
				returnDir = this.returnD1;
				bestNum = this.skipsD1(s);
			}
		}if(this.skipsD2(s) > bestNum){
			if(checkOpenSq(1,1,s) && checkFor5Open(1, 1, s)){
				returnDir = this.returnD2;
				bestNum = this.skipsD2(s);
			}
		}
		return bestNum;
	}
	public int getOpenBest(Square s){
		int bestNum = 0;
		if(board.winY(s) > bestNum){
			if(checkFullOpen(1, 0, s) && checkFor5Open(1, 0, s)){
				bestNum = board.winY(s);
			}
		}
		if(board.winX(s) > bestNum){
			if(checkFullOpen(0, 1, s) && checkFor5Open(0, 1, s)){
				bestNum = board.winX(s);
			}
		}if(board.winD1(s) > bestNum){
			if(checkFullOpen(-1, 1, s) && checkFor5Open(-1, 1, s)){
				bestNum = board.winD1(s);
			}
			
		}if(board.winD2(s) > bestNum){
			if(checkFullOpen(1,1,s) && checkFor5Open(1, 1, s)){
				bestNum = board.winD2(s);
			}
		}if(this.skipsY(s) > bestNum){
			if(checkFullOpen(1,0,s) && checkFor5Open(1, 0, s)){
				bestNum = this.skipsY(s);
			}
		}if(this.skipsX(s) > bestNum){
			if(checkFullOpen(0,1,s) && checkFor5Open(0, 1, s)){
				bestNum = this.skipsX(s);
			}
		}if(this.skipsD1(s) > bestNum){
			if(checkFullOpen(-1,1,s) && checkFor5Open(-1, 1, s)){
				bestNum = this.skipsD1(s);
			}
		}if(this.skipsD2(s) > bestNum){
			if(checkFullOpen(1,1,s) && checkFor5Open(1, 1, s)){
				bestNum = this.skipsD2(s);
			}
		}
		return bestNum;
	}
	public int getHalfBest(Square s){
		int bestNum = 0;
		if(board.winY(s) > bestNum){
			if(checkHalfGuarded(1, 0, s)){
				bestNum = board.winY(s);
			}
		}
		if(board.winX(s) > bestNum){
			if(checkHalfGuarded(0, 1, s)){
				bestNum = board.winX(s);
			}
		}if(board.winD1(s) > bestNum){
			if(checkHalfGuarded(-1, 1, s)){
				bestNum = board.winD1(s);
			}
			
		}if(board.winD2(s) > bestNum){
			if(checkHalfGuarded(1,1,s)){
				bestNum = board.winD2(s);
			}
		}if(this.skipsY(s) > bestNum){
			if(checkHalfGuarded(1,0,s)){
				bestNum = this.skipsY(s);
			}
		}if(this.skipsX(s) > bestNum){
			if(checkHalfGuarded(0,1,s)){
				bestNum = this.skipsX(s);
			}
		}if(this.skipsD1(s) > bestNum){
			if(checkHalfGuarded(-1,1,s)){
				bestNum = this.skipsD1(s);
			}
		}if(this.skipsD2(s) > bestNum){
			if(checkHalfGuarded(1,1,s)){
				bestNum = this.skipsD2(s);
			}
		}
		return bestNum;
	}
	public boolean checkFullOpen(int rorr, int colr, Square s){
		int r = 0;
		boolean contin = true;
		while(boardArray[s.getRow()+(r*rorr)][s.getColumn()+(r*colr)].getState() == s.getState() && contin == true){
			if(s.getRow()+((r+1)*rorr) >= 0 && s.getRow()+((r+1)*rorr) <= bwidthsq-1 && s.getColumn()+((r+1)*colr) >=0 && s.getColumn()+((r+1)*colr) < bwidthsq){
				r++;
			}else{
				contin = false;
			}
		}
		if(boardArray[s.getRow()+(r*rorr)][s.getColumn()].getState() == PenteMain.EMPTY){
			contin = true;
			r = 0;
			while(boardArray[s.getRow()-(r*rorr)][s.getColumn()-(r*colr)].getState() == s.getState() && contin == true){
				if(s.getRow()-((r+1)*rorr) >= 0 && s.getRow()-((r+1)*rorr) <= bwidthsq-1 && s.getColumn()-((r+1)*colr) >=0 && s.getColumn()-((r+1)*colr) < bwidthsq){
					r++;
				}else{
					contin = false;
				}
			}
			if(boardArray[s.getRow()-(r*rorr)][s.getColumn()].getState() == PenteMain.EMPTY){
				return true;
			}else{
				return false;
				}
		}else{
			return false;
				
		}
	}
	public boolean checkHalfGuarded(int rorr, int colr, Square s){
		int r = 0;
		boolean contin = true;
		boolean firstFilled = true;
		while(boardArray[s.getRow()+(r*rorr)][s.getColumn()+(r*colr)].getState() == s.getState() && contin == true){
			if(s.getRow()+((r+1)*rorr) >= 0 && s.getRow()+((r+1)*rorr) <= bwidthsq-1 && s.getColumn()+((r+1)*colr) >=0 && s.getColumn()+((r+1)*colr) < bwidthsq){
				r++;
			}else{
				contin = false;
			}
		}
		if(boardArray[s.getRow()+(r*rorr)][s.getColumn()].getState() == PenteMain.EMPTY){
			firstFilled = false;
		}
		contin = true;
		r = 0;
		while(boardArray[s.getRow()-(r*rorr)][s.getColumn()-(r*colr)].getState() == s.getState() && contin == true){
			if(s.getRow()-((r+1)*rorr) >= 0 && s.getRow()-((r+1)*rorr) <= bwidthsq-1 && s.getColumn()-((r+1)*colr) >=0 && s.getColumn()-((r+1)*colr) < bwidthsq){
				r++;
			}else{
				contin = false;
			}
		}
		if(boardArray[s.getRow()-(r*rorr)][s.getColumn()].getState() == PenteMain.EMPTY){
			return firstFilled;
		}else{
			if (firstFilled == false){
				return true;
			}else{
				return false;
			}
			}
				
	}
	public Square placeY(Square s, int bi, int c){
		Square toPlace;
		int r = 0;
		boolean contin = true;
		int rand = 1;
		boolean picker = randObject.nextBoolean();
		if(picker){
			rand = 1;
		}else{
			rand = -1;
		}
		while(boardArray[s.getRow()+(r*rand)][s.getColumn()].getState() == s.getState() && contin == true){
			if(rand == -1){
				if(s.getRow()-(r)-1 >= 0){
					r++;
				}else{
					contin = false;
				}
			}else{
				if(s.getRow()+(r)+1 <= bwidthsq-1){
					r++;
				}else{
					contin = false;
				}
			}
		}
		if(boardArray[s.getRow()+(r*rand)][s.getColumn()].getState() == PenteMain.EMPTY){
			toPlace = boardArray[s.getRow()+(r*rand)][s.getColumn()];
		}else{
			contin = true;
			r = 0;
			while(boardArray[s.getRow()-(r*rand)][s.getColumn()].getState() == s.getState() && contin == true){
				if(rand == 1){
					if(s.getRow()-(r)-1 >= 0){
						r++;
					}else{
						contin = false;
					}
				}else{
					if(s.getRow()+(r)+1 <= bwidthsq-1){
						r++;
					}else{
						contin = false;
					}
				}
				
			}
			if(boardArray[s.getRow()-(r*rand)][s.getColumn()].getState() == PenteMain.EMPTY){
				toPlace = boardArray[s.getRow()-(r*rand)][s.getColumn()];
			}else{
				if(s.getState() == PenteMain.WHITE){
					white.remove(bi);
					toPlace = defense(c, false);
				}else{
					black.remove(bi);
					toPlace = offense(c, this.checkAll);
				}
				
			}
		}
		return toPlace;
	}
	public Square placeX(Square s, int bi, int c){
		Square toPlace;
		int r = 0;
		boolean contin = true;
		int rand = 1;
		boolean picker = randObject.nextBoolean();
		if(picker){
			rand = 1;
		}else{
			rand = -1;
		}
		while(boardArray[s.getRow()][s.getColumn()+(r*rand)].getState() == s.getState() && contin == true){
			if(rand == -1){
				if(s.getColumn()-(r)-1 >= 0){
					r++;
				}else{
					contin = false;
				}
			}else{
				if(s.getColumn()+(r)+1 <= bwidthsq-1){
					r++;
				}else{
					contin = false;
				}
			}
		}
		if(boardArray[s.getRow()][s.getColumn()+(r*rand)].getState() == PenteMain.EMPTY){
			toPlace = boardArray[s.getRow()][s.getColumn()+(r*rand)];
		}else{
			contin = true;
			r = 0;
			while(boardArray[s.getRow()][s.getColumn()-(r*rand)].getState() == s.getState() && contin == true){
				if(rand == 1){
					if(s.getColumn()-(r)-1 >= 0){
						r++;
					}else{
						contin = false;
					}
				}else{
					if(s.getColumn()+(r)+1 <= bwidthsq-1){
						r++;
					}else{
						contin = false;
					}
				}
				
			}
			if(boardArray[s.getRow()][s.getColumn()-(r*rand)].getState() == PenteMain.EMPTY){
				toPlace = boardArray[s.getRow()][s.getColumn()-(r*rand)];
			}else{
				if(s.getState() == PenteMain.WHITE){
					white.remove(bi);
					toPlace = defense(c, false);
				}else{
					black.remove(bi);
					toPlace = offense(c, this.checkAll);
				}
				
			}
		}
		return toPlace;
	}
	public Square placeD1(Square s, int bi, int c){
		System.out.println("called");
		Square toPlace;
		int r = 0;
		boolean contin = true;
		int rand = 1;
		boolean picker = randObject.nextBoolean();
		if(picker){
			rand = 1;
		}else{
			rand = -1;
		}
		while(boardArray[s.getRow()-(r*rand)][s.getColumn()+(r*rand)].getState() == s.getState() && contin == true){
			if(rand == -1){
				if(s.getRow() + (r) + 1 <= bwidthsq-1 && s.getColumn() - (r) - 1 >=0 && contin == true){
					r++;
				}else{
					contin = false;
				}
			}else{
				if(s.getRow() - (r) - 1 >= 0 && s.getColumn() + (r) + 1 <= bwidthsq-1 && contin == true){
					r++;
				}else{
					contin = false;
				}
			}
		}
		if(boardArray[s.getRow()-(r*rand)][s.getColumn()+(r*rand)].getState() == PenteMain.EMPTY){
			toPlace = boardArray[s.getRow()-(r*rand)][s.getColumn()+(r*rand)];
		}else{
			r = 0;
			contin = true;
			while(boardArray[s.getRow()+(r*rand)][s.getColumn()-(r*rand)].getState() == s.getState()){
				if(rand == 1){
					if(s.getRow() + (r) + 1 <= bwidthsq-1 && s.getColumn() - (r) - 1 >=0 && contin == true){
						r++;
					}else{
						contin = false;
					}
				}else{
					if(s.getRow() - (r) - 1 >= 0 && s.getColumn() + (r) + 1 <= bwidthsq-1 && contin == true){
						r++;
					}else{
						contin = false;
					}
				}
				
			}
			if(boardArray[s.getRow()+(r*rand)][s.getColumn()-(r*rand)].getState() == PenteMain.EMPTY){
				toPlace = boardArray[s.getRow()+(r*rand)][s.getColumn()-(r*rand)];
			}else{
				if(s.getState() == PenteMain.WHITE){
					white.remove(bi);
					toPlace = defense(c, false);
				}else{
					black.remove(bi);
					toPlace = offense(c, this.checkAll);
				}
				
			}
		}
		return toPlace;
	}
	public Square placeD2(Square s, int bi, int c){
		Square toPlace;
		int r = 0;
		boolean contin = true;
		int rand = 1;
		boolean picker = randObject.nextBoolean();
		if(picker){
			rand = 1;
		}else{
			rand = -1;
		}
		while(boardArray[s.getRow()+(r*rand)][s.getColumn()+(r*rand)].getState() == s.getState() && contin == true){
			if(rand == -1){
				if(s.getRow() - (r) - 1 >= 0 && s.getColumn() - (r) - 1>= 0){
					r++;
				}else{
					contin = false;
				}
			}else{
				if(s.getRow() + (r) + 1 <= bwidthsq-1 && s.getColumn() + (r) + 1<= bwidthsq-1){
					r++;
				}else{
					contin = false;
				}
			}
			
		}
		if(boardArray[s.getRow()+(r*rand)][s.getColumn()+(r*rand)].getState() == PenteMain.EMPTY){
			toPlace = boardArray[s.getRow()+(r*rand)][s.getColumn()+(r*rand)];
		}else{
			r = 0;
			contin = true;
			while(boardArray[s.getRow()-(r*rand)][s.getColumn()-(r*rand)].getState() == s.getState() && contin == true){
				if(rand == 1){
					if(s.getRow() - (r) - 1 >= 0 && s.getColumn() - (r) - 1>= 0){
						r++;
					}else{
						contin = false;
					}
				}else{
					if(s.getRow() + (r) + 1 <= bwidthsq-1 && s.getColumn() + (r) + 1<= bwidthsq-1){
						r++;
					}else{
						contin = false;
					}
				}
				
			}
			if(boardArray[s.getRow()-(r*rand)][s.getColumn()-(r*rand)].getState() == PenteMain.EMPTY){
				toPlace = boardArray[s.getRow()-(r*rand)][s.getColumn()-(r*rand)];
			}else{
				if(s.getState() == PenteMain.WHITE){
					white.remove(bi);
					toPlace = defense(c, false);
				}else{
					black.remove(bi);
					toPlace = offense(c, this.checkAll);
				}
				
			}
		}
		return toPlace;
	}
	public int skipsY(Square s){
		int row = s.getRow();
		int column = s.getColumn();
		boolean contin = true;
		int i = 0;
		if(row > 4){
			int c2 = 0;
			while(contin == true && c2 <= 3){
				if(boardArray[row-c2-1][column].getState() == s.getState() ){
					//System.out.println("adding to I left");
					i++;
					c2++;
				}
				else if((boardArray[row-c2-2][column].getState() == s.getState() && (boardArray[row-c2-1][column].getState() == PenteMain.EMPTY || boardArray[row-c2-1][column].getState() == s.getState()))){
					i++;
					c2 = c2+2;
				}
				else{
					contin = false;
				}
			}
		}
		contin = true;
		if(row < bwidthsq - 5){
			int c = 0;
			while(contin == true && c < 4){
				if(boardArray[row+c+1][column].getState() == s.getState()){
					//System.out.println("adding to I right");
					i++;
					c++;
				}else if((boardArray[row+c+2][column].getState() == s.getState() && boardArray[row+c+1][column].getState() == PenteMain.EMPTY)){
					i++;
					c = c+2;
				}
				else{
					contin = false;
				}
			}
		}
		//System.out.println(i + "y");
		return i;
	}
	public int skipsX(Square s){
		int row = s.getRow();
		int column = s.getColumn();
		boolean contin = true;
		int i = 0;
		if(column > 4){
			int c2 = 0;
			while(contin == true && c2 < 4){
				if(boardArray[row][column-c2-1].getState() == s.getState()){
					i++;
					c2++;
				}else if(boardArray[row][column-c2-2].getState() == s.getState() && boardArray[row][column-c2-1].getState() == PenteMain.EMPTY){
					i++;
					c2 = c2 + 2;
				}
				else{
					contin = false;
				}
			}
		}
		contin = true;
		if(column < bwidthsq - 5){
			int c = 0;
			while(contin == true && c < 4){
				if(boardArray[row][column+c+1].getState() == s.getState()){
					i++;
					c++;
				}else if((boardArray[row][column+c+2].getState() == s.getState() && boardArray[row][column+c+1].getState() == PenteMain.EMPTY)){
					i++;
					c = c+2;
				}
				else{
					contin = false;
				}
			}
		}
		//System.out.println(i + "x");
		return i;
	}
	public int skipsD1(Square s){
		int row = s.getRow();
		int column = s.getColumn();
		boolean contin = true;
		int i = 0;
		if(row > 4 && column < bwidthsq - 5){
			int c2 = 0;
			while(contin == true && c2 < 4){
				if(boardArray[row-c2-1][column+c2+1].getState() == s.getState()){
					i++;
					c2++;
				}else if(boardArray[row-c2-2][column+c2+2].getState() == s.getState() && boardArray[row-c2-1][column+c2+1].getState() == PenteMain.EMPTY){
					i++;
					c2 = c2+2;
				}
				else{
					contin = false;
				}
			}
		}
		contin = true;
		if(row < bwidthsq - 5 && column > 4){
			int c = 0;
			while(contin == true && c < 4){
				if(boardArray[row+c+1][column-c-1].getState() == s.getState()){
					i++;
					c++;
				}else if((boardArray[row+c+2][column-c-2].getState() == s.getState() && boardArray[row+c+1][column-c-1].getState() == PenteMain.EMPTY)){
					i++;
					c=c+2;
				}
				else{
					contin = false;
				}
			}
		}
		return i;
	}
	public int skipsD2(Square s){
		int row = s.getRow();
		int column = s.getColumn();
		boolean contin = true;
		int i = 0;
		if(row > 4 && column > 4){
			int c2 = 0;
			while(contin == true && c2 < 4){
				if(boardArray[row-c2-1][column-c2-1].getState() == s.getState()){
					i++;
					c2++;
				}else if((boardArray[row-c2-2][column-c2-2].getState() == s.getState() && boardArray[row-c2-1][column-c2-1].getState() == PenteMain.EMPTY)){
					i++;
					c2 = c2+2;
				}
				else{
					contin = false;
				}
			}
		}
		contin = true;
		if(row < bwidthsq - 5 && column < bwidthsq - 5){
			int c = 0;
			while(contin == true && c < 4){
				if(boardArray[row+c+1][column+c+1].getState() == s.getState()){
					i++;
					c++;
				}else if((boardArray[row+c+2][column+c+2].getState() == s.getState() && boardArray[row+c+1][column+c+1].getState() == PenteMain.EMPTY)){
					i++;
					c=c+2;
				}
				else{
					contin = false;
				}
			}
		}
		return i;
	}
	public ArrayList<Square> sortList(ArrayList<Square> toSort){
		toSort = sort(toSort);
		return toSort;
	}
	
	public ArrayList<Square> sort(ArrayList<Square> a){
		double n = a.size();
		if ( n <= 1 ){
			return a;
		}
		ArrayList<Square> done;
		double hn = n/2;
		//System.out.println(hn);
		int ln = (int) (hn);
		int lln = (int) Math.floor(hn);
		
		ArrayList<Square> l1 = new ArrayList<Square>();
		ArrayList<Square> l2 = new ArrayList<Square>();
		
		for(int i = 0; i<ln; i++){
			l1.add(a.get(i));
		}
		for(int i = 0; i<lln; i++){
			l2.add(a.get(ln+i));
		}
		l1 = sort(l1);
	    l2 = sort(l2);
	    done = merge(l1, l2);
	    return done;
	}
	
	private ArrayList<Square> merge(ArrayList<Square> list1, ArrayList<Square> list2){
	int length = list1.size() + list2.size();
	ArrayList<Square> newList3 = new ArrayList<Square>();
	int list1Index = 0; 
	int list2Index = 0;
	for(int i=0; i < length; ++i){
		if (list1Index==list1.size()){
			newList3.add(list2.get(list2Index));
			++list2Index;
		}else if (list2Index==list2.size()){
			newList3.add(list1.get(list1Index));
			++list1Index;
		}else if(getBestLine(list1.get(list1Index))>getBestLine(list2.get(list2Index))){
			newList3.add(list2.get(list2Index));
			++list2Index;
		}else {
			newList3.add(list1.get(list1Index));
			++list1Index;
		}
	}
	return newList3;

}
	
	
}
