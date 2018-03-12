import java.math.BigInteger;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Main {
	static Buffer buffer;
	static ThreadPool threadPool;
	static int cantWorkers;
	static Barrier barrier;
	static Proveedor proveedor = new Proveedor();
	
	public static void main(String[] args){
		// Lista de BigIntegers
		List<BigInteger> numbers = proveedor.getBigIntegers();
		
		//Cantidad de numeros a evaluar
		int n = numbers.size();
		
		//Declaro el tiempo inicial
		LocalTime tiempoInicio = LocalTime.now();
		
		//Inicializo el threadPool
		threadPool = new ThreadPool();
		
		//Inicializo la cantidad de workers que quiero utilizar
		cantWorkers = 6;
		
		//Inicializo el buffer a utilizar
		buffer = new Buffer(n+cantWorkers);
		
		//Declaro el barrier a utilizar, le paso +1 teniendo en cuenta el thread principal
		barrier = new Barrier(cantWorkers +1);
		
		//LLeno el buffer para que los perfectworkers puedan empezar a trabajar
		llenarBuffer(buffer, numbers, cantWorkers);
		
		System.out.println("########## SE INICO LA BUSQUEDA ##########");
		System.out.println("");
		
		//Le pido al threadPool que inicie los threads
		threadPool.iniciarThreads(cantWorkers,buffer,barrier);
		
		//Espero a que los workers terminen su trabajo
		esperar();
		
		System.out.println("");
		System.out.println("########## TERMINO LA BUSQUEDA ##########");
		
		//Informo al usuario cuanto tardo el programa en encontrar los numeros perfectos
		long milisegundos = ChronoUnit.MILLIS.between(tiempoInicio, LocalTime.now());
		
		//Muestro estadisticas de la ejecucion
		mostrarEstadisticas(n,cantWorkers,milisegundos);
	}

	private static void mostrarEstadisticas(int n, int cantWorkers, long milisegundos) {
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("|#######################################################|");
		System.out.println("|############| ESTADISTICAS DE LA EJECUCION |###########|");
		System.out.println("|#######################################################|");
		System.out.println("|##############| Se evaluaron: " + n + " numeros |##############|"); 
		System.out.println("|############--------------------------------###########|");
		System.out.println("|##########| Se iniciaron: " + cantWorkers+ " workers(Threads) |#########|");
		System.out.println("|###########--------------------------------############|"); 
		System.out.println("|#######| La busqueda tardo: " + milisegundos + " milisegundos |#######|");
		System.out.println("|#######################################################|"); 
		
	}

	private static void esperar() {
		try {
			barrier.esperar();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}

	private static void llenarBuffer(Buffer buffer, List<BigInteger> numbers, int cantWorkers) {
		//Lleno el buffer con los numeros de la lista
		for(BigInteger num: numbers){
			try {
				buffer.push(num);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//Le agrego al buffer las pildoras(-1) para que los workers sepan que tienen que erperar.(Tantas como workers haya inicializado el threadPool)
		for(int i=1;i <= cantWorkers ;i++){
            try {
				buffer.push(new BigInteger("-1"));
            	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
	}
}


