package pojo;

public class Local {
	
	private String nome;
	private String rua;
	private String bairro;
	private String cidade;
	
	public Local(String rua, String bairro, String cidade) {
		this.rua = rua;
		this.bairro = bairro;
		this.cidade = cidade;
	}
	
	public Local(String nome, String rua, String bairro, String cidade) {
		this.nome = nome;
		this.rua = rua;
		this.bairro = bairro;
		this.cidade = cidade;
	}
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Override
	public String toString() {
		return " "+this.rua+"\n"+
			   "Bairro: "+this.bairro+"\n"+
			   "Cidade: "+this.cidade+"\n";
	}
}
