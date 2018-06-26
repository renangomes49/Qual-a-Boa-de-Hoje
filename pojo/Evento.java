package pojo;

import java.util.List;

public class Evento {
	
	private String nome;
	private String data;
	private String descricao;
	private List<Artista> artistas;
	private Local local;
	
	public Evento(String nome, String data, String descricao, List<Artista> artistas, Local local) {
		this.nome = nome;
		this.data = data;
		this.descricao = descricao;
		this.artistas = artistas;
		this.local = local;
	}
	
	public Evento(String nome, String data, String descricao, Local local) {
		this.nome = nome;
		this.data = data;
		this.descricao = descricao;
		this.artistas = artistas;
		this.local = local;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return data;
	}



	public void setData(String data) {
		this.data = data;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public List<Artista> getGrupos() {
		return artistas;
	}

	public void setGrupos(List<Artista> artistas) {
		this.artistas = artistas;
	}



	public Local getEndereco() {
		return local;
	}



	public void setEndereco(Local local) {
		this.local = local;
	}



	@Override
	public String toString() {
		return "\nNome do Evento: "+this.nome+"\n"+
			   "Data: "+this.data+"\n"+
			   "Descrição: "+this.descricao+"\n"+
			   "Local: "+this.local;
	}
	
}
