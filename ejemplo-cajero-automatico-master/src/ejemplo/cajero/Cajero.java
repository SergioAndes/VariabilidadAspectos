package ejemplo.cajero;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import ejemplo.cajero.control.Comando;
import ejemplo.cajero.control.ComandoConsignar;
import ejemplo.cajero.control.ComandoListarCuentas;
import ejemplo.cajero.control.ComandoRetirar;
import ejemplo.cajero.control.ComandoTransferir;
import ejemplo.cajero.modelo.Banco;
import ejemplo.cajero.modelo.Cuenta;
import ejemplo.cajero.modelo.Usuario;

/**
 * Simulador de un Cajero de Banco
 */
public class Cajero {
	
	/**
	 * Programa principal
	 * @param args parámetros de línea de comandos. Son ignorados por el programa.
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		// crea el banco
		Banco banco = new Banco();
		Properties opciones = new Properties();
		
		 try {
				opciones.load(new FileReader("src/config-if.properties"));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 

		 
		
		// crea unas cuentas, para la prueba
		banco.agregarCuenta(new Cuenta("1", "clave", 200000));
		banco.agregarCuenta(new Cuenta("2", "clave", 200000));
		banco.agregarCuenta(new Cuenta("3", "clave", 200000));
		
		if (opciones.getProperty("saldoReducido", "true").equals("true")) {
        	for (Cuenta cuenta : banco.getCuentas()) {
    			cuenta.setsaldoReducido(true);
    		}
	 }
		Scanner console = new Scanner(System.in);
		// crea los comandos que se van a usar en la aplicación
		List<Comando> comandos = cargaComandos();
		
		
		// Ciclo del Programa
		// ==================
		
		System.out.println("Cajero Automático");
		System.out.println("=================");
		System.out.println();
		
		boolean login=false;
		
		while(login!=true) {
			
			System.out.println("Ingrese usuario");
			
			String usuario = console.nextLine();
			
			System.out.println("Ingrese contrasena");
			
			String pass = console.nextLine();
			
			login =logIn(usuario,pass);
		}
		
		System.out.println("");
		System.out.println("Bienvenido");
		
		
		
		
		boolean fin = false;
		do {
			
			// muestra los nombres de los comandos
			muestraMenuConComandos(comandos);
			System.out.println("X.- Salir");
			
			// la clase Console no funciona bien en Eclipse
						
			String valorIngresado = console.nextLine();
			
			// obtiene el comando a ejecutar
			Comando comandoSeleccionado = retornaComandoSeleccionado(comandos, valorIngresado);
			if (comandoSeleccionado != null) {
				
				// intenta ejecutar el comando
				try {
					comandoSeleccionado.ejecutar(banco);
					
				} catch (Exception e) {
					// si hay una excepción, muestra el mensaje
					System.err.println(e.getMessage());
				}
				
			} 
			// si no se obtuvo un comando, puede ser la opción de salir
			else if (valorIngresado.equalsIgnoreCase("X")) {
				fin = true;
			}
			
			System.out.println();
		} while ( !fin );
		
		System.out.println("Lista de Operaciones");
		
		salir();
		System.out.println("");
		System.out.println("Gracias por usar el programa.");
	}
		 
	
	public static void salir() {
		
	}
	
	public static boolean logIn(String user, String pass) {
		
		//crea usuarios
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(new Usuario("sergio", "sergioPass"));
		usuarios.add(new Usuario("andres", "andresPass"));
		usuarios.add(new Usuario("ana", "anaPass"));	
		boolean result = false;
		
		for (int i = 0; i < usuarios.size(); i++) {
			//System.out.println(user+" "+usuarios.get(i).getuser());
			if(user.equals(usuarios.get(i).getuser()) ) {
				
				if(pass.equals(usuarios.get(i).getpassword())) {
					result = true;
				}
			}
		}
		return result;
		
	}
	
	// Manejo de los comandos de la aplicación
	// =======================================
	
	// carga los comandos usados en el programa
	private static List<Comando> cargaComandos() {
		
		// crea los comandos que se van a usar en la aplicación
		List<Comando> comandos = new ArrayList<>();
		Properties opciones = new Properties();
		
		comandos.add(new ComandoListarCuentas());
		comandos.add(new ComandoRetirar());
		
		try {
				opciones.load(new FileReader("src/config-if.properties"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		  if (opciones.getProperty("transferenciaYConsignacion", "true").equals("true")) {
			  comandos.add(new ComandoTransferir());     
			  comandos.add(new ComandoConsignar());
          }		
		
		
		return comandos;
	}
	
	
	// Muestra el listado de comandos, para mostrar un menú al usuario
	// muestra el índice en el arreglo de comandos y el nombre del comando
	private static void muestraMenuConComandos(List<Comando> comandos) {
		
		// muestra los nombres de los comandos 
		for (int i=0; i < comandos.size(); i++) {
			System.out.println(i + ".-" + comandos.get(i).getNombre());
		}
	}
	
	
	// dado el texto ingresado por el usuario, retorna el comando correspondiente
	// retorna null si el texto no es un número o no existe ningún comando con ese índice
	private static Comando retornaComandoSeleccionado(List<Comando> comandos, String valorIngresado) {
		
		Comando comandoSeleccionado = null;
		
		// el valorIngresado es un número ?
		if (valorIngresado.matches("[0-9]")) {			
			int valorSeleccionado = Integer.valueOf(valorIngresado);
			// es un índice válido para la lista de comandos
			if (valorSeleccionado < comandos.size()) {
				comandoSeleccionado = comandos.get(valorSeleccionado);
			}
		}
		
		return comandoSeleccionado;
	}
	
}
