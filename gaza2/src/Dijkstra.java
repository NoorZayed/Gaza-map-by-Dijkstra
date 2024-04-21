
import java.util.*;

public class Dijkstra {

	public Dijkstra() {
	}

	public List<City> getShortestPath(City start, City end, Graph graph) {
		// Priority queue to store cities based on their distances from the source
	    PriorityQueue<City> queue = new PriorityQueue<>(Comparator.comparingDouble(city -> graph.getDistances().get(city)));
//		PriorityQueue<City> queue = new PriorityQueue<>(
//				(city1, city2) -> (int) (graph.getDistances().get(city1) - graph.getDistances().get(city2)));
		// Add the source city to the queue and set its distance to 0

		queue.add(start);
		graph.getDistances().put(start, 0.0);
		// Map to store the previous city in the shortest path

		Map<City, City> previous = new HashMap<>();
		// Dijkstra's algorithm to find the shortest path

		while (!queue.isEmpty()) {
			City current = queue.poll();
			// Iterate through the neighbors of the current city

			for (Edge edge : graph.getEdges().get(current)) {
				//adj for source
				City neighbor = edge.getEnd();
				//dis for edge and neighbor
				double distance = edge.getDistance();
				//  Update the distance if a shorter path is found
				double newDistance = graph.getDistances().get(current) + distance;
				
				if (graph.getDistances().get(neighbor) > newDistance) {
					
					graph.getDistances().put(neighbor, newDistance);
					previous.put(neighbor, current);
					queue.add(neighbor);
				}


			}
		}
		if (previous.get(end) == null) {
			return null; // No path exists
		}
		// Reconstruct the shortest path from the destination to the source

		Stack<City> pathStack = new Stack<>();
		City current = end;

		// Trace back the path from the destination to the source using a stack
		while (current != start) {
			pathStack.push(current);
			current = previous.get(current);
		}
		pathStack.push(start);

		// Pop elements from the stack to get the correct order
		List<City> path = new ArrayList<>();
		while (!pathStack.isEmpty()) {
			path.add(pathStack.pop());
		}

		return path;

	}
}
