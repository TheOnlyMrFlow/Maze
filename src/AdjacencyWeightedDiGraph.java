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

public class AdjacencyWeightedDiGraph<Vertex, Edge>  extends AdjacencyDiGraph<Vertex, Edge> implements WeightedDiGraph<Vertex, Edge> {

	protected Map<Edge, Integer> edgesToWeight= new HashMap<Edge, Integer>();

	
	
	
	@Override 
	public void addEdge(Edge e, Vertex src, Vertex dest) {
		super.addEdge(e, src, dest);
		this.edgesToWeight.put(e, 1);
		
	}
	
	public void addEdge(Edge e, Vertex src, Vertex dest, int w) {
		this.addEdge(e, src, dest);
		this.edgesToWeight.put(e, w);
		
	}

	public int getDistBetween(Vertex from, Vertex to) {
		
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
				
		HashMap<Vertex, Vertex> historyMap = new HashMap<Vertex, Vertex>();
		HashMap<Vertex, Integer> distancesMap = new HashMap<Vertex, Integer>();
		distancesMap.put(src, 0);
		
		Comparator<Vertex> vertexComparators = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex v1, Vertex v2) {
            	return distancesMap.get(v1) - distancesMap.get(v2);
            }
        };
        
		Queue<Vertex> toVisitQueue = new PriorityQueue<Vertex>(vertexComparators);
		
		toVisitQueue.add(src);
		Vertex visiting = null;
		while (visiting != dest && !toVisitQueue.isEmpty()) {
						
			visiting = toVisitQueue.remove();
			System.out.println("visiting " + visiting + " that has a distance of " + distancesMap.get(visiting));

			if(visiting.equals(dest)) {
				LinkedList<Vertex> path = new LinkedList<Vertex>();
				path.addFirst(visiting);
				while(path.getFirst() != src) {
					path.addFirst(historyMap.get(path.getFirst()));
				}
				System.out.println("Total cost = " + distancesMap.get(visiting));
				return new ArrayList(path);
			}
			
			for (Vertex v : this.getAdjacentVertices(visiting)) {
				if (!historyMap.containsKey(v) || v.equals(dest)) {
					historyMap.put(v, visiting);
					distancesMap.put(v, getDistBetween(visiting, v) + distancesMap.get(visiting));
					toVisitQueue.add(v);
				}
			}
		}
		return new ArrayList<Vertex>();
	}
	
	public List<Vertex> shortestPath(Vertex src, Vertex dest, DistanceHeuristic<Vertex> h){
		
		HashMap<Vertex, Vertex> historyMap = new HashMap<Vertex, Vertex>();
		HashMap<Vertex, Integer> distancesMap = new HashMap<Vertex, Integer>();
		distancesMap.put(src, 0);
		
		Comparator<Vertex> vertexComparators = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex v1, Vertex v2) {
            	return distancesMap.get(v1) - distancesMap.get(v2);
            }
        };
        
		Queue<Vertex> toVisitQueue = new PriorityQueue<Vertex>(vertexComparators);
		
		toVisitQueue.add(src);
		Vertex visiting = null;
		while (visiting != dest && !toVisitQueue.isEmpty()) {
			
			visiting = toVisitQueue.remove();
			System.out.println("visiting " + visiting + " that has a heuristic distance of " + distancesMap.get(visiting));
			
			if(visiting.equals(dest)) {
				LinkedList<Vertex> path = new LinkedList<Vertex>();
				path.addFirst(visiting);
				while(path.getFirst() != src) {
					path.addFirst(historyMap.get(path.getFirst()));
				}
				System.out.println("Total cost = " + distancesMap.get(visiting));
				return new ArrayList(path);
			}
			
			for (Vertex v : this.getAdjacentVertices(visiting)) {
				if (!historyMap.containsKey(v) || v.equals(dest)) {
					historyMap.put(v, visiting);
					distancesMap.put(v, getDistBetween(visiting, v) + distancesMap.get(visiting) - h.distance(visiting, src) + h.distance(v, src));
					toVisitQueue.add(v);
				}
			}
		}
		return new ArrayList<Vertex>();
	}
	
	
	public List<Vertex> shortestPath(String src, String dest, DistanceHeuristic<Vertex> h){
		return shortestPath(getVertexByName(src), getVertexByName(dest), h);
		
	}
	

}


