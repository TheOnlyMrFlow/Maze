
public interface IDistanceHeuristic<Vertex> {

	public int distance(Vertex v0, Vertex v1);
	
	public enum DistanceType {
		ManhattanDistance,
		ZeroDistance
	}
}


