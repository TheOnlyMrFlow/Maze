
public class PrinterVisitor implements IVertexVisitor<Cell> {
	
	public void visit(Cell c) {
		System.out.println("Visiting " + c.toString());
	}

}
