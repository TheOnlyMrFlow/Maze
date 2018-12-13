# Hands on 2 : Mazes

### Explain the implementations of hashcode and equals methods provided for the Cell class.
Hashcode returns the bitwise xor between the  column and the row of the cell. We use xor because it is sensible to a change in any of the 2 inputs. What’s more, each bit of the output has 50% chance  to be a 1 and 50% chance to be a 0, which means the function “mix” the bits pretty well, and give a good repartition. So in the end, the hash will be almost unique.

The first line of the equals method checks if the object to compare is null. If it is, it cannot be equal to the cell on which the method is called, which is obviously not null because the method has been called on it. So it returns false.
The second line of the method checks if the object to compare is the same object as the one on which the method is called (i.e if the objects are truly equal) with the == operator. If it is the same object, we return true of course.
Then we check if the given object is of type Cell. If it is not, it cannot be equals to the cell on which the method is called so we return false.
Now we are sure the object is of type Cell, we cast it to a Cell and compare their position in the maze. If they belong to the same row and the same column, then they are considered equals, so we return true. We return false otherwise.

### Could we store instances of the Cell class in a TreeMap or a TreeSet ? Explain why, and if not, make it so that it would be possible.
We couldn’t store them in a TreeMap or TreeSet because these data structures sort their keys. That means the Cell class should implement the Comparable Interface, and so they must have a CompareTo method.
=> see comparteTo implemented in Cell class

### Explain the algorithmic complexities of the methods provided in the AdjacencyDiGraph implementation
addVertex has constant complexity because HashSet.contains, HashSet.add and HashMap.put all have a constant complexity.
getVertices has a linear complexity because it copies a whole List in a new one
addEdge has constant time complexity because all the functions it calls have constant time complexity
getEdges has linear time complexity for the same reason than getvertices
getAdjacentVertices complexity is linear because it has a for loop that containes a constant time complexity method
nameVertex is constant time because HashMap.put has constant time complexity
getVertexByNameis constant time because HashMap.get has constant time complexity
getNameOrNullByVertex has inear time complexity because it is made of a for loop containing a constant time complexity method
getNames is linear because it copies a whole set in a list

### Explain the algorithm to find if two vertices are connected.
We iterate over the graph by doing a breadth search.
We keep track of the visited nodes in a set. For each node we visit, we look at all his neighbors and add them in a queue if they haven’t been visited before. This queue determines the order in which we’ll visit the next nodes. This process makes us do a breadth search of the destination. The algorithm ends either if the queue gets empty, which means the two vertices are not connected, or if the destination has been found, which means the two vertices are connected.

### Explain how to nd the shortest path between two vertices.
We simply use the algorithm seen previously. Indeed, since it does a breadth search of the destination, we are sure the first time we visit the destination, we are coming from the shortest path. To be able to return the entire path from which we are coming, we use a Map<Vertex, Vertex> where the key is a vertex, and the value is the vertex from which we came. So when the destination is reached we return a list of vertex which is the full path by reading the ancestor of the dest, and his ancestor, etc … until the source.

### Explain what could be a WeightedDiGraph generic interface (weights could be integers).
It could extend the Digraph interface so we just have to overload the addEdge method to add a weight to the edge.

### Explain what could be a AdjacencyWeightedDiGraph implementation of the WeightedDiGraph interface. 
The implementation of such a class could be a inheritance of AdjacencyDigraph.
We would need to add a map that keeps track of the weights of the edges (key = edge, value = weight)
We would override the original addEdge method to give a default weight of 1, and the overload of the addEdge method would add a weight given as parameter.



### Explain how to find the path with minimal sum of weights between two vertices (using a priority queue).
We use the same algorithm than in the unweighted graph algorithm, but the Queue is replaced by a PrioriyQueue. We configure it in a way that the element we pick from the queue is always the one that has the smallest weighted distance from the source. To do so,  we need to define a Comparator that defines what is the order of priority between 2 vertices. 
This comparator compares their distance to the source, which is stored in a Map (key = vertex, value = weighted distance to source)

### Explain how to take advantage of the positions (row, column) of cells in order speed up the shortest path finding algorithm. You can use the A* algorithm with the Manhattan distance as heuristic.
Thanks to the position of a Cell, we can compute a heuristic distance between each cell and the source. The map that stores the distance of each vertex from the source now stores the sum of the weighted distance and the heuristic distance. The rest of the process is the same. 
