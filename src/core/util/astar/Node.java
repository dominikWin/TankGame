package core.util.astar;

import java.util.ArrayList;

public class Node implements Comparable<Node> {
	float distanceFromStart;
	Node east;
	float heuristicDistanceFromGoal;
	boolean isGoal;
	boolean isObstacle;
	boolean isStart;
	/* Nodes that this is connected to */
	ArrayList<Node> neighborList;
	Node north;
	Node northEast;
	Node northWest;
	Node previousNode;
	Node south;
	Node southEast;
	Node southWest;
	boolean visited;
	Node west;
	int x;
	int y;

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
		return (node.x == x) && (node.y == y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (Float.floatToIntBits(distanceFromStart) != Float.floatToIntBits(other.distanceFromStart))
			return false;
		if (east == null) {
			if (other.east != null)
				return false;
		} else if (!east.equals(other.east))
			return false;
		if (Float.floatToIntBits(heuristicDistanceFromGoal) != Float.floatToIntBits(other.heuristicDistanceFromGoal))
			return false;
		if (isGoal != other.isGoal)
			return false;
		if (isObstacle != other.isObstacle)
			return false;
		if (isStart != other.isStart)
			return false;
		if (neighborList == null) {
			if (other.neighborList != null)
				return false;
		} else if (!neighborList.equals(other.neighborList))
			return false;
		if (north == null) {
			if (other.north != null)
				return false;
		} else if (!north.equals(other.north))
			return false;
		if (northEast == null) {
			if (other.northEast != null)
				return false;
		} else if (!northEast.equals(other.northEast))
			return false;
		if (northWest == null) {
			if (other.northWest != null)
				return false;
		} else if (!northWest.equals(other.northWest))
			return false;
		if (previousNode == null) {
			if (other.previousNode != null)
				return false;
		} else if (!previousNode.equals(other.previousNode))
			return false;
		if (south == null) {
			if (other.south != null)
				return false;
		} else if (!south.equals(other.south))
			return false;
		if (southEast == null) {
			if (other.southEast != null)
				return false;
		} else if (!southEast.equals(other.southEast))
			return false;
		if (southWest == null) {
			if (other.southWest != null)
				return false;
		} else if (!southWest.equals(other.southWest))
			return false;
		if (visited != other.visited)
			return false;
		if (west == null) {
			if (other.west != null)
				return false;
		} else if (!west.equals(other.west))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(distanceFromStart);
		result = prime * result + ((east == null) ? 0 : east.hashCode());
		result = prime * result + Float.floatToIntBits(heuristicDistanceFromGoal);
		result = prime * result + (isGoal ? 1231 : 1237);
		result = prime * result + (isObstacle ? 1231 : 1237);
		result = prime * result + (isStart ? 1231 : 1237);
		result = prime * result + ((neighborList == null) ? 0 : neighborList.hashCode());
		result = prime * result + ((north == null) ? 0 : north.hashCode());
		result = prime * result + ((northEast == null) ? 0 : northEast.hashCode());
		result = prime * result + ((northWest == null) ? 0 : northWest.hashCode());
		result = prime * result + ((previousNode == null) ? 0 : previousNode.hashCode());
		result = prime * result + ((south == null) ? 0 : south.hashCode());
		result = prime * result + ((southEast == null) ? 0 : southEast.hashCode());
		result = prime * result + ((southWest == null) ? 0 : southWest.hashCode());
		result = prime * result + (visited ? 1231 : 1237);
		result = prime * result + ((west == null) ? 0 : west.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
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
