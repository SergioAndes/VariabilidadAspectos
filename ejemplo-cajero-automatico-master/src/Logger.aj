
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.io.IOException;

public aspect Logger {

  // defina un pointcut con el nombre "metodosDelMundo"
  // para todas las invocaciones de los m�todos (call)
  // - m�todos con cualquier tipo de retorno (*)
  // - m�todos de cualquier clase en el paquete "mundo"
  // - m�todos con cualquier tipo de argumentos
  pointcut retirar() : call( * ejemplo.cajero.modelo.Cuenta.retirar(..));
  pointcut consignar() : call( * ejemplo.cajero.modelo.Cuenta.consignar(..));
  pointcut salir() : call( * ejemplo.cajero.Cajero.salir(..));
  
  ArrayList<String> operaciones;
  PrintWriter  writer;
  
  public Logger() {
	  try {
		this.writer = new PrintWriter("Logdger.txt",  "UTF-8");
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  }
  
  after() returning(Object resultado): retirar() {

	  	this.writer.println(resultado);
	  }
  
  after() returning(Object resultado): salir() {
		this.writer.close();
	  }
  
  after() returning(Object resultado): consignar() {
	 
	  this.writer.println(resultado);
	  }

}
