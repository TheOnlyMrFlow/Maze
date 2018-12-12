import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class AdjacencyDiGraph<Vertex, Edge> implements IDiGraph<Vertex, Edge> {
	protected Set<Vertex> vertices = new HashSet<Vertex>();
	protected Set<Edge> edges = new HashSet<Edge>();
	protected Map<Vertex, List<Edge>> vertexToEdges = new HashMap<Vertex, List<Edge>>();
	protected Map<Edge, Vertex> edgeToSrc = new HashMap<Edge, Vertex>();
	protected Map<Edge, Vertex> edgeToDest = new HashMap<Edge, Vertex>();
	private Map<String, Vertex> nameToVertex = new HashMap<String, Vertex>();

	
	protected Queue<Vertex> toVisitQueue;
	protected Map<Vertex, Vertex> historyMap;
	protected Map<Vertex, Integer> distancesMap;
	
	protected IDistanceHeuristic<Vertex> heuristicStrategy;
	protected IVertexVisitor<Vertex>[] allEncounteredCellsVisitors;
	protected IVertexVisitor<Vertex>[] onShortestPathVisitors;

	public AdjacencyDiGraph(IDistanceHeuristic<Vertex> heuristicStrategy, IVertexVisitor<Vertex>[] allEncounteredCellsVisitors, IVertexVisitor<Vertex>[] onShortestPathVisitors) {
		this.heuristicStrategy = heuristicStrategy;
		this.allEncounteredCellsVisitors = allEncounteredCellsVisitors;
		this.onShortestPathVisitors = onShortestPathVisitors;
	}

	public void addVertex(Vertex v) {
		if (!vertices.contains(v)) {
			vertices.add(v);
			vertexToEdges.put(v, new ArrayList<Edge>());
		}
	}

	public List<Vertex> getVertices() {
		return new ArrayList<Vertex>(vertices);
	}

	public void addEdge(Edge e, Vertex src, Vertex dest) {
		addVertex(src);
		addVertex(dest);
		edges.add(e);
		edgeToSrc.put(e, src);
		edgeToDest.put(e, dest);
		vertexToEdges.get(src).add(e);
	}

	public List<Edge> getEdges() {
		return new ArrayList<Edge>(edges);
	}

	public List<Vertex> getAdjacentVertices(Vertex src) {
		List<Vertex> res = new ArrayList<Vertex>();
		for (Edge e : vertexToEdges.get(src)) {
			res.add(edgeToDest.get(e));
		}
		return res;
	}

	public void nameVertex(String name, Vertex v) {
		nameToVertex.put(name, v);
	}

	public Vertex getVertexByName(String name) {
		return nameToVertex.get(name);
	}

	// should use java.util.Optional
	public String getNameOrNullByVertex(Vertex v) {
		for (Map.Entry<String, Vertex> e : nameToVertex.entrySet()) {
			if (e.getValue().equals(v)) {
				return e.getKey();
			}
		}
		return null;
	}

	public List<String> getNames() {
		return new ArrayList<String>(nameToVertex.keySet());
	}

	public boolean areConnected(Vertex src, Vertex dest) {
		HashSet<Vertex> visitedSet = new HashSet<Vertex>();
		Queue<Vertex> toVisitQueue = new LinkedList<Vertex>();
		toVisitQueue.add(src);
		Vertex visiting = null;
		while (!toVisitQueue.isEmpty()) {
			visiting = toVisitQueue.remove();
			for (Vertex v : this.getAdjacentVertices(visiting)) {
				if (!visitedSet.contains(v)) {
					if (v.equals(dest)) {
						return true;
					}
					toVisitQueue.add(v);
				}
			}
			visitedSet.add(visiting);
		}

		return false;
	}

	public boolean areConnected(String src, String dest) {
		return areConnected(getVertexByName(src), getVertexByName(dest));
	}

	

	protected int getDistBetween(Vertex from, Vertex to) {

		for (Edge e : edges) {
			if (edgeToSrc.get(e).equals(from) && edgeToDest.get(e).equals(to)) {
				// System.out.println(edgesToWeight.get(e));
				return 1;
			}
		}

		System.err.println("No edge in common");
		return 0;
	}

	public List<Vertex> shortestPath(Vertex src, Vertex dest) {
		this.toVisitQueue = new LinkedList<Vertex>();
		this.historyMap = new HashMap<Vertex, Vertex>();
		this.distancesMap = new HashMap<Vertex, Integer>();
		return shortestPathBase(src, dest);

	}
	
	protected List<Vertex> shortestPathBase(Vertex src, Vertex dest) {
		toVisitQueue.add(src);
		distancesMap.put(src, 0);
		Vertex visiting = null;
		while (visiting != dest && !toVisitQueue.isEmpty()) {

			visiting = toVisitQueue.remove();
			
			for (IVertexVisitor visitor: this.allEncounteredCellsVisitors) {
				visitor.visit(visiting);
			}


			if (visiting.equals(dest)) {
				LinkedList<Vertex> path = new LinkedList<Vertex>();
				path.addFirst(visiting);
				while (path.getFirst() != src) {
					for (IVertexVisitor visitor: this.onShortestPathVisitors) {
						visitor.visit(historyMap.get(path.getFirst()));
					}
					path.addFirst(historyMap.get(path.getFirst()));
				}
				return new ArrayList(path);
			}

			for (Vertex v : this.getAdjacentVertices(visiting)) {
				if (!historyMap.containsKey(v) || v.equals(dest)) {
					historyMap.put(v, visiting);
					distancesMap.put(	v,
										getDistBetween(visiting, v) 
										+ distancesMap.get(visiting)
										- this.heuristicStrategy.distance(visiting, src)
										+ this.heuristicStrategy.distance(v, src));
					toVisitQueue.add(v);
				}
			}
		}
		return new ArrayList<Vertex>();
	}


	

	public List<Vertex> shortestPath(String src, String dest) {
		return shortestPath(getVertexByName(src), getVertexByName(dest));
	}
}
