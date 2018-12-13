import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TrackerVisitor implements IVertexVisitor<Cell> {
	
	private LinkedList<Cell> visited = new LinkedList<Cell>();
	
	public void visit(Cell v) {
		
		this.visited.addFirst(v);
		
	}
	
	public List<Cell> getVisited() {
		return visited;
	}

}
