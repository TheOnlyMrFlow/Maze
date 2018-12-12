import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SquareMaze{
	private Cell cellNextCol(Cell c){
		return new Cell(c.r, c.c+1);
	}
	private Cell cellNextRow(Cell c){
		return new Cell(c.r+1, 0);
	}
	// should use java.util.Optional
	private Cell nextCellOrNull(Cell c, Set<Cell> cells) {
		Cell res= cellNextCol(c);
		if(!cells.contains(res)){
			res= cellNextRow(c);
			if(!cells.contains(res)){
				res= null;
			}
		}
		return res;
	}
	public static String mazeToString(IDiGraph<Cell, Integer> maze){
		StringBuilder res= new StringBuilder();
		Set<Cell> cells= new HashSet<Cell>(maze.getVertices());
		for(Cell cr= new Cell(0,0); cells.contains(cr); cr=new Cell(cr.r+1, 0)){
			StringBuilder horiz= new StringBuilder();
			for(Cell current= cr; cells.contains(current); current= new Cell(current.r, current.c+1)){
				String name= maze.getNameOrNullByVertex(current);
				res.append((name != null)? name : " ");
				horiz.append(maze.getAdjacentVertices(current).contains(new Cell(current.r+1, current.c)) ? " " :"-");
				res.append(maze.getAdjacentVertices(current).contains(new Cell(current.r, current.c+1)) ? " " :"|");
				horiz.append("+");
			}
			res.append("\n");
			res.append(horiz);
			res.append("\n");
		}
		return res.toString();
	}
		
	
	private static String pathToString(Collection<Cell> path, String sep){
		StringBuilder res= new StringBuilder();
		for(Cell c : path){
			res.append(c);
			res.append(sep);
		}
		return res.toString();
	}
	public static void main(String[] args) throws IOException{
		try(InputStream is = args.length==0 ? System.in
				: new FileInputStream(new File(args[0]))){
			MazeFactory diGraphFactory = new MazeFactory(); 
			List<IVertexVisitor<Cell>> allCellsVisitors = new ArrayList<IVertexVisitor<Cell>>();
			//allCellsVisitors.add(new CounterVisitor());
			//allCellsVisitors.add(new PrinterVisitor());
			List<IVertexVisitor<Cell>> onPathVisitors = new ArrayList<IVertexVisitor<Cell>>();
			//onPathVisitors.add(new CounterVisitor());
			//onPathVisitors.add(new PrinterVisitor());
			//onPathVisitors.add(new TrackerVisitor());

			
			IDiGraph<Cell, Integer> maze= (diGraphFactory.generate(
					is,
					false,
					new ManhattanDistance(),
					allCellsVisitors.toArray( (IVertexVisitor<Cell>[]) new IVertexVisitor[allCellsVisitors.size()]),
					onPathVisitors.toArray( (IVertexVisitor<Cell>[]) new IVertexVisitor[0])));
			System.out.println("working with maze:\n"+mazeToString(maze));
			System.out.println("A and B are connected ? :" + maze.areConnected("A", "B"));
			System.out.println("Shortest path from A to B :" + pathToString(maze.shortestPath("A", "B"), "=>"));
		}
	}
}