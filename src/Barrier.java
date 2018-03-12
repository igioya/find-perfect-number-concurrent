
public class Barrier {
	private Integer cupos;

	public Barrier(Integer n){
		this.cupos = n; 
	}
	
	public synchronized void esperar() throws InterruptedException{
		this.cupos--;
		while(this.cupos > 0) wait();
		notifyAll();
	}
}
