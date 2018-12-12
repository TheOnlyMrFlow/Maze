import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MazeFactory {
	
	public MazeFactory() {
		
	}
	
	public IDiGraph<Cell, Integer> generate(InputStream is,
											boolean weighted,
											IDistanceHeuristic heuristicStrategy,
											IVertexVisitor[] allEncounteredCellVisitors,	// will use the visit() method on every cell that is visited whether or not it is on the shortest path
											IVertexVisitor[] onShortestPathVisitors) { //will use the visit() method only on cells thats are on the shortest path
		
		IDiGraph<Cell, Integer> result;
		
		if (weighted) {
			result = new AdjacencyWeightedDiGraph<Cell, Integer>(heuristicStrategy, allEncounteredCellVisitors, onShortestPathVisitors);
		}
		else {
			result = new AdjacencyDiGraph<Cell, Integer>(heuristicStrategy, allEncounteredCellVisitors, onShortestPathVisitors);
		}
		
		try {
			read(is, result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
		
	}
	
	
	private void read(InputStream is, IDiGraph<Cell, Integer> res) throws IOException{
		int currentEdgeId= 0;
		BufferedReader br= new BufferedReader(new InputStreamReader(is));
		for(int line=0; br.ready(); ++line){
			String currentLine= br.readLine();
			for(int nCh=0; nCh != currentLine.length(); ++nCh){
				char ch= currentLine.charAt(nCh);
				if(line % 2 == 0){ // cells and vertical walls
					if(nCh % 2 == 0){// cell
						Cell currentCell= new Cell(line / 2, nCh / 2);
						res.addVertex(currentCell);
						if(ch != ' '){// named cell
							res.nameVertex( ""+ch, currentCell);
						}
					}else{// vertical wall or not
						if((ch == ' ' || (ch >= '1' && ch <= '9')) && ((nCh != currentLine.length()-1))){// no wall, not end of the line
							int weight = ch == ' ' ? 1 : Character.getNumericValue(ch);
							currentEdgeId= connectCellsBothWays(new Cell(line / 2, (nCh+1)/2),
									new Cell(line / 2, (nCh-1)/2),
									currentEdgeId, res, weight);
						}
					}
				}else{// horizontal walls or unused
					if( nCh % 2 == 0){ // horizontal wall or not
						if((ch == ' ' || (ch >= '0' && ch <= '9')) && br.ready()){ // no wall, not end of the stream
							int weight = ch == ' ' ? 1 : Character.getNumericValue(ch);
							currentEdgeId= connectCellsBothWays(new Cell((line+1)/2, nCh / 2),
									new Cell((line-1)/2, nCh / 2),
									currentEdgeId, res, weight);
						}
					}
				}
			}
		}
	}
	
	
	
	
	private static int connectCellsBothWays(Cell c0, Cell c1, int currentEdgeId, IDiGraph<Cell, Integer> maze, int weight){
		
		if (maze instanceof AdjacencyWeightedDiGraph) {
			((IWeightedDiGraph<Cell, Integer>)maze).addEdge(currentEdgeId++, c0, c1, weight);
			((IWeightedDiGraph<Cell, Integer>)maze).addEdge(currentEdgeId++, c1, c0, weight);
			return currentEdgeId;
		} 
		
		else {
			maze.addEdge(currentEdgeId++, c0, c1);
			maze.addEdge(currentEdgeId++, c1, c0);
			return currentEdgeId;
		}
		
	}
	
	
	
	
}
