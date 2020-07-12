package exemplo.modelo;

public class Carro {
	private int id;
	private String marca;
	private String modelo; 
	
	public Carro() { }
	
	public Carro(int id, String modelo, String marca) {		
		this.id = id;
		this.setModelo(modelo);
		this.setMarca(marca);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	@Override
	public String toString() {
		return "Carro [id=" + id + ", modelo=" + getModelo() + "]";
	}

}
