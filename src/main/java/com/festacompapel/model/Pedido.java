package com.festacompapel.model;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Pedido")
@DynamicUpdate
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "data_pedido", updatable=false)
	private LocalDateTime dataDoPedido;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "data_entrega_Pedido", updatable=false)
	private LocalDateTime dataEntregaPedido;

	@Column(name = "frete_pedido")
	private double frete;

	@Column(name = "valor_pedido")
	private double valorPedido;

	@Column(name = "valor_do_desconto")
	private double valorDoDesconto;

	@Column(name = "desconto_pedido")
	private double descontoPedido;

	@Column(name = "valor_total")
	private double valorTotal;

	@Enumerated(EnumType.STRING)
	private StatusPedido status;

	@ManyToOne
	private Cliente cliente;

	@ManyToMany
	private List<Produto> produtos;

	@SuppressWarnings("static-access")
	@PrePersist
	private void prePersistUpdate() {
		this.dataDoPedido = LocalDateTime.now();
		this.valorDoDesconto = (this.descontoPedido / 100) * valorPedido;
		this.status = StatusPedido.NOVO;
		this.valorTotal = this.valorPedido + this.frete - this.valorDoDesconto;
	}

    @PreUpdate
    public void preUpdate(){
        this.valorDoDesconto = (this.descontoPedido / 100) * valorPedido;
        this.valorTotal = this.valorPedido + this.frete - this.valorDoDesconto;
    }


	public Calendar formataDataPedidoEntrega() {
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			cal.setTime(sdf.parse(this.getDataEntregaPedido().toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public LocalDateTime getDataDoPedido() {
        return dataDoPedido;
    }

    public void setDataDoPedido(LocalDateTime dataDoPedido) {
        this.dataDoPedido = dataDoPedido;
    }

    public LocalDateTime getDataEntregaPedido() {
        return dataEntregaPedido;
    }

    public void setDataEntregaPedido(LocalDateTime dataEntregaPedido) {
        this.dataEntregaPedido = dataEntregaPedido;
    }

    public double getFrete() {
		return frete;
	}

	public void setFrete(double frete) {
		this.frete = frete;
	}

	public double getValorPedido() {
		return valorPedido;
	}

	public void setValorPedido(double valorPedido) {
		this.valorPedido = valorPedido;
	}

	public double getValorDoDesconto() {
		return valorDoDesconto;
	}

	public void setValorDoDesconto(double valorDoDesconto) {
		this.valorDoDesconto = valorDoDesconto;
	}

	public double getDescontoPedido() {
		return descontoPedido;
	}

	public void setDescontoPedido(double descontoPedido) {
		this.descontoPedido = descontoPedido;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pedido pedido = (Pedido) o;
		return id == pedido.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
