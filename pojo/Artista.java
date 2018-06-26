package pojo;

public class Artista {
	
	private String nome;
	private String estilo;
	
	public Artista(String nome, String estilo) {
		super();
		this.nome = nome;
		this.estilo = estilo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}
	
	@Override
	public String toString() {
		return "Nome: "+this.nome+"\n"+
			   "Estilo: "+this.estilo+"\n";
	}

}
