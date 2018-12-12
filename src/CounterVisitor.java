
public class CounterVisitor implements IVertexVisitor<Cell> {
	
	private int count = 0;
	
	public void visit(Cell c) {;
		this.count++;
	}
	
	public int getCount() {
		return this.count;
	}

}
