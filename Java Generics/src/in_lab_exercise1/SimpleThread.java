package in_lab_exercise1;

public class SimpleThread implements Runnable{

	Resource resource;
	
	@Override
	public void run() {
		for(int i = 0; i<10; i++){
			try {
			System.out.println(resource.increment());
			
			Thread.sleep(1);
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	SimpleThread(Resource resource){
		this.resource = resource;
	}

	public static void main(String args[]) {
		Resource resource = new Resource();
		Runnable run = new SimpleThread(resource);
		Thread t = new Thread(run);
		Thread s = new Thread(run);
		
		t.start();
		s.start();
	}

}
