package roadgraph;

import java.util.Comparator;

public class MapNodeComparator implements Comparator<MapNode>{

	@Override
	public int compare(MapNode a, MapNode b) {
		// TODO Auto-generated method stub
		if (a.getDistance() < b.getDistance()) {
			return -1;
		}
		if (a.getDistance() > b.getDistance()) {
			return 1;
		}
		return 0;
	}

}
