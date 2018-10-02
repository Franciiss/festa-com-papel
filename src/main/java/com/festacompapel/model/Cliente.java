package com.festacompapel.model;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Cliente")
@DynamicUpdate
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "nome")
	@NotNull
	private String nome;

	@Column(name = "sobrenome")
	private String sobrenome;

	@Column(name = "nome_formatado")
	private String nomeFormatado;

	@Column(name = "rua")
	private String rua;

	@Column(name = "numero")
	private String numero;

	@Column(name = "bairro")
	private String bairro;

	@Column(name = "cidade")
	private String cidade;

	@Column(name = "estado")
	private String estado;

	@Column(name = "celular")
	private String celular;

	@Column(name = "telefone")
	private String telefone;

	@Column(name = "email")
	private String email;

	@Column(name = "cep")
	private String cep;

	@Column(name = "sexo")
    private String sexo;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro", updatable=false)
	private Date dataCadastro;

    @Column(name = "imagem")
    private String imagem;

    @Enumerated(EnumType.STRING)
    private StatusBasicos status;

    @Column
    private String endereco;

	@PrePersist
	private void prePresist() {
	    if(this.rua.isEmpty() && this.numero.isEmpty() && this.bairro.isEmpty() ){

        } else {
            this.endereco = this.rua + ", número " + this.numero + ", " + this.bairro;
        }
		this.nomeFormatado = this.nome + " " + this.sobrenome;
		this.dataCadastro =  new Date();
        this.setStatus(StatusBasicos.ATIVO);
        this.cidade = "São Luís";
        this.estado = "Maranhão";
    }

    @PreUpdate
    public void PreUpdate(){
        this.nomeFormatado = this.nome + " " + this.sobrenome;

        if(this.rua.isEmpty() && this.numero.isEmpty() && this.bairro.isEmpty() ){

        } else {
            this.endereco = this.rua + ", número " + this.numero + ", " + this.bairro;
        }

        this.nomeFormatado = this.nome + " " + this.sobrenome;

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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNomeFormatado() {
        return nomeFormatado;
    }

    public void setNomeFormatado(String nomeFormatado) {
        this.nomeFormatado = nomeFormatado;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public StatusBasicos getStatus() {
        return status;
    }

    public void setStatus(StatusBasicos status) {
        this.status = status;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return id == cliente.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
