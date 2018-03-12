import java.math.BigInteger;

public class PerfectWorker extends Thread{
	private Buffer buffer;
	private Barrier barrier;
	
	public PerfectWorker(Buffer buffer, Barrier barrier) {
		this.buffer = buffer ;
		this.barrier = barrier ;
	}
	
	public void run() {
		while(true){
			try {
				BigInteger num = buffer.pop();
				Boolean termino;
				termino = this.evaluar(num);
				if(termino) break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private Boolean evaluar(BigInteger num) {
		Boolean termino = false;
		if(num.equals(new BigInteger("-1"))){ 
			try {
				this.barrier.esperar();
				termino = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			this.esPerfecto(num);			
		}	
		
		return termino;
	}
	
	private boolean esPerfecto(BigInteger number){
		BigInteger temp = BigInteger.ZERO;
		
		for(BigInteger i = BigInteger.ONE; menorOIgual(i, number.divide(new BigInteger("2"))); i = i.add(BigInteger.ONE)){
			if(number.mod(i).equals(BigInteger.ZERO) ){
				temp = (temp.add(i));
			} 
		}
		
		if(temp.equals(number)){
			System.out.println("|"+ number +"| Es Perfecto (#BY THREAD:" + this.getId()+")");
			return true;
		} else {
			System.out.println("|"+ number +"| No es perfecto (#BY THREAD:" + this.getId()+")");
			return false;
		}
	}
	
	private static boolean menorOIgual(BigInteger big1, BigInteger big2){		
		return (big1.compareTo(big2) == -1) || (big1.compareTo(big2) == 0);
	}
}
