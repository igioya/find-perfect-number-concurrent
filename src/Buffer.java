import java.math.BigInteger;

public class Buffer {
	private Integer N;
	private BigInteger[] data;
	private int begin = 0, end = 0;
	
	public Buffer(Integer n) {
		this.N = n;
		this.data = new BigInteger[N +1];
	}
	
	public synchronized void push(BigInteger num) throws InterruptedException {
		
		while (isFull()) wait();
		data [begin] = num;
		begin = next (begin);
		notifyAll();
		
	}
	
	public synchronized BigInteger pop() throws InterruptedException {
		while (isEmpty()) wait();
		BigInteger result = data [end];
		end = next (end);
		notifyAll();
		return result ;
	}
	
	private boolean isEmpty(){return begin == end ;}
	private boolean isFull(){return next (begin) == end ;}
	private int next (int i){return (i +1) %(N +1);}
}