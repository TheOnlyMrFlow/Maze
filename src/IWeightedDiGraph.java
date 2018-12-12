import java.util.List;

public interface IWeightedDiGraph<Vertex, Edge> extends IDiGraph<Vertex, Edge> {
	

	public void addEdge(Edge e, Vertex src, Vertex dest, int w);
	
	//public int getDistBetween(Vertex from, Vertex to);
	


}
