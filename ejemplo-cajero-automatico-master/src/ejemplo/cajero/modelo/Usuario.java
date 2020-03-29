package ejemplo.cajero.modelo;

public class Usuario {
	
	private String user;
	private String password;
	
	
	public String getuser() {
		return user;
	}
	
	public String getpassword() {
		return password;
	}
	
	public void setuser(String usuario) {
		user = usuario;
    }
	
	public void setpassword(String pass) {
		password = pass;
    }

	public Usuario(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}

}




