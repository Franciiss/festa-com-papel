package com.festacompapel.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Categoria")
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@NotNull
	@Column(name = "nome", unique = true)
	private String nome;

	@Size(max = 40)
	@Column(name = "descricao")
	private String descricao;

	@Column(name = "caminho_imagem")
	private String caminhoImagemCategoria;

	@Column(name = "data_criacao")
	private Date dataCriacao;

	@Enumerated(EnumType.STRING)
	private StatusBasicos status;

	@PrePersist
	public void PrePersist(){
		this.setStatus(StatusBasicos.ATIVO);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCaminhoImagemCategoria() {
		return caminhoImagemCategoria;
	}

	public void setCaminhoImagemCategoria(String caminhoImagemCategoria) {
		this.caminhoImagemCategoria = caminhoImagemCategoria;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public StatusBasicos getStatus() {
		return status;
	}

	public void setStatus(StatusBasicos status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Categoria categoria = (Categoria) o;
		return id == categoria.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}