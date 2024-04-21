
import java.util.*;

public class Vertex implements Comparable<Vertex> {

	private City city;
	private double dis;
	private Vertex prev;

	public Vertex(City city) {
		this.city = city;
		this.dis = Double.MAX_VALUE;

	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public double getDis() {
		return dis;
	}

	public void setDis(double dis) {
		this.dis = dis;
	}

	public Vertex getPrev() {
		return prev;
	}

	public void setPrev(Vertex prev) {
		this.prev = prev;
	}

	@Override
	public int compareTo(Vertex o) {
		return Double.compare(this.dis, o.getDis());
	}
}
