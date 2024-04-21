
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {
//	Represents an adjacency list where each city is associated with a list of neighboring cities.
	private Map<City, List<City>> adjacencyList;
//	Represents a map where each city is associated with a list of edges.
	private Map<City, List<Edge>> edges;
//	Represents a map where each city is associated with its distance from a source city.
	private Map<City, Double> distances;
//	 Represents a map where each pair of cities is associated with the distance between them.
//	private Map<City, Map<City, Double>> distances1;

	public Graph() {
		adjacencyList = new HashMap<>();
		edges = new HashMap<>();
		distances = new HashMap<>();
	}

	// calculate the distance
	// Method to add a city with neighbors to the adjacency list
	public void addCity(City city, List<City> neighbors) {
		adjacencyList.put(city, neighbors);
	}

//	method adds a city to the edges and distances maps.
	// Overloaded method to add a city with default values to edges and distances
	public void addCity(City city) {
		edges.put(city, new LinkedList<>());
		distances.put(city, Double.POSITIVE_INFINITY);
	}

	// Method to find a city by its name
	public City getCityByName(String cityName) {
		for (City city : adjacencyList.keySet()) {
			if (city.getName().equals(cityName)) {
				return city;
			}
		}
		return null;
	}


	// Method to add an edge between two cities with a given distance
	public void addEdge(City start, City end, double dis) {
		// Ensure the source city has an entry in the edges map
		if (!edges.containsKey(start)) {
			edges.put(start, new ArrayList<>());
		}

		// Ensure the destination city has an entry in the edges map
		if (!edges.containsKey(end)) {
			edges.put(end, new ArrayList<>());
		}

		// Add the edge to the source city's list of edges
		edges.get(start).add(new Edge(start, end, dis));
	}

	public Map<City, List<Edge>> getEdges() {
		return edges;
	}

	public void setEdges(Map<City, List<Edge>> edges) {
		this.edges = edges;
	}

	public Map<City, Double> getDistances() {
		return distances;
	}

	public void setDistances(Map<City, Double> distances) {
		this.distances = distances;
	}
////This method adds the distance between two cities to the distances1 map. It ensures that the distance is added for both directions.
//// Method to add distance between two cities bidirectionally
//public void addDistance(City city1, City city2, double distance) {
//	distances1.computeIfAbsent(city1, k -> new HashMap<>()).put(city2, distance);
//	distances1.computeIfAbsent(city2, k -> new HashMap<>()).put(city1, distance);
//}
//
////This method retrieves the distance between two cities from the distances1 map.
//// Method to get the distance between two cities
//public double getDistance(City city1, City city2) {
//	return distances1.get(city1).get(city2);
//}
}
