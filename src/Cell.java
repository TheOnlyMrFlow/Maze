import java.io.*;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;


class Cell implements Comparable<Cell> {
	public final int c;
	public final int r;
	public Cell(int row, int column){
		r= row;
		c= column;
	}
	@Override
	public int hashCode(){
		return c^(r >>> 32);
	}
	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Cell))return false;
		Cell otherCell = (Cell)other;
		return r==otherCell.r && c==otherCell.c;
	}
	@Override
	public String toString(){
		return "["+r+","+c+"]";
	}
	
	@Override
	public int compareTo(Cell other) {
		if (other == null) return 1;
		if (other == this) return 0;
		if (this.r == other.r) {
			return this.c - other.c;
		}
		return this.r - other.r;
	}
}




