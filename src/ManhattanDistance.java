
public class ManhattanDistance implements DistanceHeuristic<Cell> {

	public int distance(Cell c1, Cell c2) {			
		return (Math.abs(c1.r - c2.r) + Math.abs(c1.c - c2.c));			
	}

}
