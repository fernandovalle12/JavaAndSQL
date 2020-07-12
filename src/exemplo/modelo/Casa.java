package exemplo.modelo;

public class Casa {
	private int id;
	private String endereco;
	private Pessoa proprietario; 
	
	public Casa() { }

	public Pessoa getProprietario() {
		return proprietario;
	}

	public void setProprietario(Pessoa proprietario) {
		this.proprietario = proprietario;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Casa [id=" + id + ", proprietario=" + getProprietario() + "]";
	}

}
