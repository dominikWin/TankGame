package core.util.astar;

import java.util.ArrayList;

public class Node implements Comparable<Node> {
	/* Nodes that this is connected to */
	AreaMap map;
	Node north;
	Node northEast;
	Node east;
	Node southEast;
	Node south;
	Node southWest;
	Node west;
	Node northWest;
	ArrayList<Node> neighborList;
	boolean visited;
	float distanceFromStart;
	float heuristicDistanceFromGoal;
	Node previousNode;
	int x;
	int y;
	boolean isObstacle;
	boolean isStart;
	boolean isGoal;

	Node(int x, int y) {
		neighborList = new ArrayList<Node>();
		this.x = x;
		this.y = y;
		visited = false;
		distanceFromStart = Integer.MAX_VALUE;
		isObstacle = false;
		isStart = false;
		isGoal = false;
	}

	Node(int x, int y, boolean visited, int distanceFromStart, boolean isObstical, boolean isStart, boolean isGoal) {
		neighborList = new ArrayList<Node>();
		this.x = x;
		this.y = y;
		this.visited = visited;
		this.distanceFromStart = distanceFromStart;
		isObstacle = isObstical;
		this.isStart = isStart;
		this.isGoal = isGoal;
	}

	@Override
	public int compareTo(Node otherNode) {
		float thisTotalDistanceFromGoal = heuristicDistanceFromGoal + distanceFromStart;
		float otherTotalDistanceFromGoal = otherNode.getHeuristicDistanceFromGoal() + otherNode.getDistanceFromStart();

		if (thisTotalDistanceFromGoal < otherTotalDistanceFromGoal)
			return -1;
		else if (thisTotalDistanceFromGoal > otherTotalDistanceFromGoal)
			return 1;
		else
			return 0;
	}

	public boolean equals(Node node) {
		return node.x == x && node.y == y;
	}

	public float getDistanceFromStart() {
		return distanceFromStart;
	}

	public Node getEast() {
		return east;
	}

	public float getHeuristicDistanceFromGoal() {
		return heuristicDistanceFromGoal;
	}

	public ArrayList<Node> getNeighborList() {
		return neighborList;
	}

	public Node getNorth() {
		return north;
	}

	public Node getNorthEast() {
		return northEast;
	}

	public Node getNorthWest() {
		return northWest;
	}

	public Node getPreviousNode() {
		return previousNode;
	}

	public Node getSouth() {
		return south;
	}

	public Node getSouthEast() {
		return southEast;
	}

	public Node getSouthWest() {
		return southWest;
	}

	public Node getWest() {
		return west;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isGoal() {
		return isGoal;
	}

	public boolean isObstical() {
		return isObstacle;
	}

	public boolean isStart() {
		return isStart;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setDistanceFromStart(float f) {
		distanceFromStart = f;
	}

	public void setEast(Node east) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.east)) {
			neighborList.remove(this.east);
		}
		neighborList.add(east);

		// set the new Node
		this.east = east;
	}

	public void setGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}

	public void setHeuristicDistanceFromGoal(float heuristicDistanceFromGoal) {
		this.heuristicDistanceFromGoal = heuristicDistanceFromGoal;
	}

	public void setNorth(Node north) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.north)) {
			neighborList.remove(this.north);
		}
		neighborList.add(north);

		// set the new Node
		this.north = north;
	}

	public void setNorthEast(Node northEast) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.northEast)) {
			neighborList.remove(this.northEast);
		}
		neighborList.add(northEast);

		// set the new Node
		this.northEast = northEast;
	}

	public void setNorthWest(Node northWest) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.northWest)) {
			neighborList.remove(this.northWest);
		}
		neighborList.add(northWest);

		// set the new Node
		this.northWest = northWest;
	}

	public void setObstical(boolean isObstical) {
		isObstacle = isObstical;
	}

	public void setPreviousNode(Node previousNode) {
		this.previousNode = previousNode;
	}

	public void setSouth(Node south) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.south)) {
			neighborList.remove(this.south);
		}
		neighborList.add(south);

		// set the new Node
		this.south = south;
	}

	public void setSouthEast(Node southEast) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.southEast)) {
			neighborList.remove(this.southEast);
		}
		neighborList.add(southEast);

		// set the new Node
		this.southEast = southEast;
	}

	public void setSouthWest(Node southWest) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.southWest)) {
			neighborList.remove(this.southWest);
		}
		neighborList.add(southWest);

		// set the new Node
		this.southWest = southWest;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public void setWest(Node west) {
		// replace the old Node with the new one in the neighborList
		if (neighborList.contains(this.west)) {
			neighborList.remove(this.west);
		}
		neighborList.add(west);

		// set the new Node
		this.west = west;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Node[" + x + ", " + y + "]";
	}
}
