import java.util.ArrayList;
import java.util.List;

public class TrackerVisitor implements IVertexVisitor<Cell> {
	
	private List<Cell> visited = new ArrayList<Cell>();
	
	public void visit(Cell v) {
		
		this.visited.add(v);
		
	}
	
	public Cell[] getVisited() {
		return (Cell[]) visited.toArray();
	}

}
