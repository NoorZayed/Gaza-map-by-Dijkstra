
public class Edge {

	private City start;
	private City end;
	private double distance;

	public Edge() {

	}

	public Edge(City start, City destination, double distance) {
		this.start = start;
		this.end = destination;
		this.distance = distance;
	}

	public City getStart() {
		return start;
	}

	public void setStart(City start) {
		this.start = start;
	}

	public City getEnd() {
		return end;
	}

	public void setEnd(City end) {
		this.end = end;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Edge{" + "source=" + start.getName() + ", destination=" + end.getName() + ", weight=" + distance + '}';
	}
}
