package core.util.astar;

import java.util.ArrayList;
import java.util.Arrays;

public class AreaMap {

	private int goalLocationX = 0;

	private int goalLocationY = 0;

	public ArrayList<ArrayList<Node>> map;
	private int mapHeight;
	private int mapWith;
	private int[][] obstacleMap;
	private int startLocationX = 0;
	private int startLocationY = 0;
	public AreaMap(int mapWith, int mapHeight, int[][] obstacleMap) {
		this.mapWith = mapWith;
		this.mapHeight = mapHeight;
		this.obstacleMap = obstacleMap;

		createMap();
		registerEdges();
	}
	public void clear() {
		startLocationX = 0;
		startLocationY = 0;
		goalLocationX = 0;
		goalLocationY = 0;
		createMap();
		registerEdges();
	}

	private void createMap() {
		Node node;
		map = new ArrayList<ArrayList<Node>>();
		for (int x = 0; x < mapWith; x++) {
			map.add(new ArrayList<Node>());
			for (int y = 0; y < mapHeight; y++) {
				node = new Node(x, y);
				if (obstacleMap[x][y] == 1) {
					node.setObstical(true);
				}
				map.get(x).add(node);
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AreaMap other = (AreaMap) obj;
		if (goalLocationX != other.goalLocationX)
			return false;
		if (goalLocationY != other.goalLocationY)
			return false;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		if (mapHeight != other.mapHeight)
			return false;
		if (mapWith != other.mapWith)
			return false;
		if (!Arrays.deepEquals(obstacleMap, other.obstacleMap))
			return false;
		if (startLocationX != other.startLocationX)
			return false;
		if (startLocationY != other.startLocationY)
			return false;
		return true;
	}

	public float getDistanceBetween(Node node1, Node node2) {
		// if the nodes are on top or next to each other, return 1
		if (node1.getX() == node2.getX() || node1.getY() == node2.getY())
			return 1 * (mapHeight + mapWith);
		else
			return (float) 1.7 * (mapHeight + mapWith);
	}

	public Node getGoalLocation() {
		return map.get(goalLocationX).get(goalLocationY);
	}

	public int getGoalLocationX() {
		return goalLocationX;
	}

	public int getGoalLocationY() {
		return goalLocationY;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public int getMapWith() {
		return mapWith;
	}

	public Node getNode(int x, int y) {
		return map.get(x).get(y);
	}

	public ArrayList<ArrayList<Node>> getNodes() {
		return map;
	}

	public int getStartLocationX() {
		return startLocationX;
	}

	public int getStartLocationY() {
		return startLocationY;
	}

	public Node getStartNode() {
		return map.get(startLocationX).get(startLocationY);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + goalLocationX;
		result = prime * result + goalLocationY;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + mapHeight;
		result = prime * result + mapWith;
		result = prime * result + Arrays.deepHashCode(obstacleMap);
		result = prime * result + startLocationX;
		result = prime * result + startLocationY;
		return result;
	}

	/**
	 * Registers the nodes edges (connections to its neighbors).
	 */
	private void registerEdges() {
		for (int x = 0; x < mapWith - 1; x++) {
			for (int y = 0; y < mapHeight - 1; y++) {
				Node node = map.get(x).get(y);
				if (!(y == 0)) {
					node.setNorth(map.get(x).get(y - 1));
				}
				if (!(y == 0) && !(x == mapWith)) {
					node.setNorthEast(map.get(x + 1).get(y - 1));
				}
				if (!(x == mapWith)) {
					node.setEast(map.get(x + 1).get(y));
				}
				if (!(x == mapWith) && !(y == mapHeight)) {
					node.setSouthEast(map.get(x + 1).get(y + 1));
				}
				if (!(y == mapHeight)) {
					node.setSouth(map.get(x).get(y + 1));
				}
				if (!(x == 0) && !(y == mapHeight)) {
					node.setSouthWest(map.get(x - 1).get(y + 1));
				}
				if (!(x == 0)) {
					node.setWest(map.get(x - 1).get(y));
				}
				if (!(x == 0) && !(y == 0)) {
					node.setNorthWest(map.get(x - 1).get(y - 1));
				}
			}
		}
	}

	public void setGoalLocation(int x, int y) {
		map.get(goalLocationX).get(goalLocationY).setGoal(false);
		map.get(x).get(y).setGoal(true);
		goalLocationX = x;
		goalLocationY = y;
	}

	public void setObstical(int x, int y, boolean isObstical) {
		map.get(x).get(y).setObstical(isObstical);
	}

	public void setStartLocation(int x, int y) {
		map.get(startLocationX).get(startLocationY).setStart(false);
		map.get(x).get(y).setStart(true);
		startLocationX = x;
		startLocationY = y;
	}
}
