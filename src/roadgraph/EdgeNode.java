package roadgraph;

import geography.GeographicPoint;

public class EdgeNode {
	
	private GeographicPoint from;
	private GeographicPoint to;
	private String roadName;
	private String roadType;
	private double length;
	
	public EdgeNode(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) {
		this.from = from;
		this.to = to;
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
		
	}
	
	public GeographicPoint getFrom() {
		return this.from;
	}
	
	public GeographicPoint getTo() {
		return this.to;
	}
	
	public String toString() {
		String s = "";
		s += "[From: " + this.from + " To: " + this.to + " RoadName: " + this.roadName + 
				" RoadType: " + this.roadType + " Length: " + this.length + "]";
		return s;
	}

}
