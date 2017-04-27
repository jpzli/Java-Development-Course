package in_lab_exercise1;

public class Resource {

	int counter;

	public synchronized int increment() {
		return counter++;
	}
	
}
