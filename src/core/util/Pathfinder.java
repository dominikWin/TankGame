package core.util;

import core.Game;
import core.util.astar.AStar;
import core.util.astar.AreaMap;
import core.util.astar.ClosestHeuristic;
import core.util.astar.Path;

public class Pathfinder {
	public static Path getPathToLocation(int startX, int startY, int endX, int endY) {
		Logger.log("Finding path between (" + startX + ", " + startY + ") and (" + endX + ", " + endY + ")");
		AreaMap areaMap = new AreaMap((int) Game.getWorld().getMap().getSize().getHeight(),
				(int) Game.getWorld().getMap().getSize().getWidth(), Game.getWorld().getMap().getObsticleMap());
		ClosestHeuristic heuristic = new ClosestHeuristic();
		AStar astar = new AStar(areaMap, heuristic);
		return astar.calcShortestPath(startY, startX, endY, endX);
	}
}
