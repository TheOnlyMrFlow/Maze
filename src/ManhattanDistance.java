
public class ManhattanDistance implements IDistanceHeuristic<Cell> {

	public int distance(Cell c1, Cell c2) {			
		return (int) Math.round(Math.pow(new Double(c1.r - c2.r), 2) + Math.pow(new Double(c1.c - c2.c), 2));		
	}

}
