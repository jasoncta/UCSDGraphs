/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	// Stores vertices and corresponding edges into a hashmap
	private Map<GeographicPoint, ArrayList<EdgeNode>> mapList;
	private int numVertices;
	private int numEdges;
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// Initialize variables 
		mapList = new HashMap<GeographicPoint, ArrayList<EdgeNode>>();
		numVertices = 0;
		numEdges = 0;
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return this.numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		return mapList.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		return this.numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// Will return false if duplicate key is added to hashmap of vertices
		if (mapList.containsKey(location)){
			return false;
		}
		// Puts vertex into map and initializes edgenodes 
		ArrayList<EdgeNode> edges = new ArrayList<EdgeNode>();
		mapList.put(location, edges);
		numVertices++;
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		// Throws an exception if start-point or end-point of the edge do not belong to a vertex that has been added already
		if (!mapList.containsKey(to) || !mapList.containsKey(from)) {
			throw new IllegalArgumentException("edge endpoints not in graph");
		}
		// Throws an exception if any of the field values of the edge are invalid
		if (from == null || to == null || roadName == null || roadType == null || length <= 0) {
			throw new IllegalArgumentException("inputs contain null value");
		}
		EdgeNode edgeN = new EdgeNode(from, to, roadName, roadType, length);
		mapList.get(from).add(edgeN);
		numEdges++;
		
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		// Initialize queue
		Queue<GeographicPoint> q = new LinkedList<GeographicPoint>();
		// Initialize visited hashset
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		// Initialize parent hashmap to store path
		Map<GeographicPoint, GeographicPoint> parent = new HashMap<GeographicPoint, GeographicPoint>();
		// Adds start to queue and visited 
		q.add(start);
		visited.add(start);
		// Iterates through the queue while its not empty
		while(!q.isEmpty()) {
			GeographicPoint curr = q.remove();
			// For front-end visualization
			nodeSearched.accept(curr);
			// Goal is found when entering this if statement
			if (curr.equals(goal)) {
				return getBFSPath(start, goal, parent);
			}
			// Iterates through all neighbors of the current node
			iterateNeighbors(curr, q, visited, parent);
		}
		// Path is not found
		return null;
	}
	
	private void iterateNeighbors(GeographicPoint curr, Queue<GeographicPoint> q ,
			HashSet<GeographicPoint> visited, Map<GeographicPoint, GeographicPoint> parent) {
		// Finds all the neighbors of the current node and added them to the queue, visited
		for (EdgeNode en: mapList.get(curr)) {
			if (!visited.contains(en.getTo())) {
				visited.add(en.getTo());
				parent.put(en.getTo(), curr);
				q.add(en.getTo());
			}
		}
	}
	// Gets the path
	private List<GeographicPoint> getBFSPath(GeographicPoint start, GeographicPoint goal, Map<GeographicPoint, GeographicPoint> parent) {
		// Initialize the path
		List<GeographicPoint> path = new ArrayList<GeographicPoint>();
		// Adds goal node to path
		path.add(goal);
		GeographicPoint curr = parent.get(goal);
		while (!curr.equals(start)){
			// Adds current node to path
			path.add(0, curr);
			// Gets the parent of the current node
			curr = parent.get(curr);
			
		}
		// Adds start node to head of the path list
		path.add(0, start);
		return path;
	}


	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}
	
	public String toString() {
		String s ="Adjacency List";
		
		for (GeographicPoint v : mapList.keySet()) {
			s += "\n\t"+v+": ";
			for (EdgeNode w : mapList.get(v)) {
				s += w+", ";
			}
		}
		return s;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println(theMap.getVertices());
		System.out.println(theMap.bfs(new GeographicPoint(1,1), new GeographicPoint(8,-1)));
		System.out.println("DONE.");
		
		// My tests
		System.out.println("My test: ");
		MapGraph myMap = new MapGraph();
		GeographicPoint myP = new GeographicPoint(10,10);
		GeographicPoint myF = new GeographicPoint(11,10);
		GeographicPoint myL = new GeographicPoint(13,17);
		GeographicPoint myE = new GeographicPoint(163,19);
		
		myMap.addVertex(myP);
		myMap.addVertex(myF);
		myMap.addVertex(myL);
		myMap.addVertex(myE);
		//System.out.print(myMap);
		myMap.addEdge(myF, myP, "a", "b", 7);
		myMap.addEdge(myP, myL, "a", "b", 7);
		myMap.addEdge(myL, myE, "a", "b", 7);
		System.out.println(myMap);
		System.out.println(myMap.getVertices());
		System.out.println(myMap.bfs(myF, myE));
		
		// You can use this method for testing.  
		
		/* Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
