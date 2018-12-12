import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class AdjacencyWeightedDiGraph<Vertex, Edge>  extends AdjacencyDiGraph<Vertex, Edge> implements IWeightedDiGraph<Vertex, Edge> {

	protected Map<Edge, Integer> edgesToWeight= new HashMap<Edge, Integer>();
	
	public AdjacencyWeightedDiGraph(IDistanceHeuristic heuristicStrategy, IVertexVisitor[] allEncounteredCellsVisitors, IVertexVisitor[] onShortestPathVisitors) {
		super(heuristicStrategy, allEncounteredCellsVisitors, onShortestPathVisitors);
	}
	
	@Override 
	public void addEdge(Edge e, Vertex src, Vertex dest) {
		this.addEdge(e, src, dest, 1);
		
	}
	
	public void addEdge(Edge e, Vertex src, Vertex dest, int w) {
		super.addEdge(e, src, dest);
		this.edgesToWeight.put(e, w);
		
	}

	@Override
	protected int getDistBetween(Vertex from, Vertex to) {
		
		for(Edge e : edges) {
			if (edgeToSrc.get(e).equals(from) && edgeToDest.get(e).equals(to)) {
				//System.out.println(edgesToWeight.get(e));
				return edgesToWeight.get(e);
			}
		}
		
		System.err.println("No edge in common");
		return 0;
	}
	
	
	@Override
	public List<Vertex> shortestPath(Vertex src, Vertex dest){
		
		this.historyMap = new HashMap<Vertex, Vertex>();
		this.distancesMap = new HashMap<Vertex, Integer>();
		
		
		Comparator<Vertex> vertexComparators = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex v1, Vertex v2) {
            	return distancesMap.get(v1) - distancesMap.get(v2);
            }
        };
        
		this.toVisitQueue = new PriorityQueue<Vertex>(vertexComparators);
		
		return this.shortestPathBase(src,  dest);
	}
	
	
	public List<Vertex> shortestPath(String src, String dest){
		return shortestPath(getVertexByName(src), getVertexByName(dest));
		
	}
	

}


