
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.io.IOException;

public aspect OperationsList {

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
  
  public OperationsList() {
	  
		this.operaciones = new ArrayList();

	  }
  
  after() returning(Object resultado): retirar() {

	  	this.operaciones.add("retirar");
	  }
  
  after() returning(Object resultado): salir() {
	  for (int i = 0; i < this.operaciones.size(); i++) {
		  System.out.println(this.operaciones.get(i));
		  }
	  }
  
  after() returning(Object resultado): consignar() {
	  this.operaciones.add("consignar");
	  }

}
