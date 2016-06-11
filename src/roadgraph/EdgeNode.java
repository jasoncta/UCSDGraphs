package roadgraph;

import geography.GeographicPoint;

// This class stores all information for the edges
public class EdgeNode {
	
	// Variables for an edgeNode
	private GeographicPoint from;
	private GeographicPoint to;
	private String roadName;
	private String roadType;
	private double length;
	
	// Constructor
	public EdgeNode(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) {
		this.from = from;
		this.to = to;
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
		
	}
	
	// Gets the start-node location
	public GeographicPoint getFrom() {
		return this.from;
	}
	
	// Gets the end-node location
	public GeographicPoint getTo() {
		return this.to;
	}
	
	// Converts object to readable string
	public String toString() {
		String s = "";
		s += "[From: " + this.from + " To: " + this.to + " RoadName: " + this.roadName + 
				" RoadType: " + this.roadType + " Length: " + this.length + "]";
		return s;
	}

}
