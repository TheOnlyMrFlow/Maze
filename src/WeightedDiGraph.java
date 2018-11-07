import java.util.List;

public interface WeightedDiGraph<Vertex, Edge> extends DiGraph<Vertex, Edge> {
	

	public void addEdge(Edge e, Vertex src, Vertex dest, int w);
	
	//public int getDistBetween(Vertex from, Vertex to);
	
	public List<Vertex> shortestPath(Vertex src, Vertex dest, DistanceHeuristic<Vertex> h);
	public List<Vertex> shortestPath(String src, String dest, DistanceHeuristic<Vertex> h);


}
