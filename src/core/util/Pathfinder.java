package core.util;

import core.Game;
import core.util.astar.AStar;
import core.util.astar.AreaMap;
import core.util.astar.ClosestHeuristic;
import core.util.astar.Path;

/**
 * A class to interact with the AStar source code.
 * 
 * @author Dominik Winecki
 *
 * 
 */
public class Pathfinder {
	/**
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return a path from the start location to the end location.
	 */
	public static Path getPathToLocation(int startX, int startY, int endX, int endY) {
		Logger.log("Finding path between (" + startX + ", " + startY + ") and (" + endX + ", " + endY + ")");
		AreaMap areaMap = new AreaMap((int) Game.getWorld().getMap().getSize().getHeight(),
				(int) Game.getWorld().getMap().getSize().getWidth(), Game.getWorld().getMap().getObsticleMap());
		ClosestHeuristic heuristic = new ClosestHeuristic();
		AStar astar = new AStar(areaMap, heuristic);
		return astar.calcShortestPath(startY, startX, endY, endX);
	}
}
