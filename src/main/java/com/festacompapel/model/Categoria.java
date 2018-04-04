package com.festacompapel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Categoria")
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idCategoria")
	private long idCategoria;

	@NotNull
	@Column(name = "nomeCategoria", unique = true)
	private String nome;

	@Size(max=40)
	@Column(name = "descricaoCategoria")
	private String descricao;

	@Column(name = "caminhoImagemCategoria")
	private String caminhoImagemCategoria;

	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCaminhoImagemCategoria() {
		return caminhoImagemCategoria;
	}

	public void setCaminhoImagemCategoria(String caminhoImagemCategoria) {
		this.caminhoImagemCategoria = caminhoImagemCategoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}