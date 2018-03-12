
public class ThreadPool {
	public ThreadPool(){}

//	public int cantWorkersAUtilizar(int n) {
//		return (((n/6)+1)%n); 
//	}

	public void iniciarThreads(int cantWorkersAiniciar, Buffer buffer, Barrier barrier) {
		for(int i = 1; i <= cantWorkersAiniciar; i++){
			new PerfectWorker(buffer, barrier).start();
		}
	}
}
