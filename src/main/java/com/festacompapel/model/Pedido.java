package com.festacompapel.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_pedido")
	private Calendar dataDoPedido;

	// @DateTimeFormat(pattern = "dd/MM/yyyy")
	// @Temporal(TemporalType.DATE)
	@Column(name = "data_entrega_Pedido")
	private String dataEntregaPedido;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_entrega_pedido_Calendar")
	private Calendar dataEntregaPedidoCalendar;

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
	@PreUpdate
	private void prePersistUpdate() {
		this.dataDoPedido = this.dataDoPedido.getInstance();
		this.dataEntregaPedidoCalendar = this.formataDataPedidoEntrega();
		this.valorDoDesconto = (this.descontoPedido / 100) * valorPedido;
		this.status = StatusPedido.NOVO;
		this.valorTotal = this.valorPedido + this.frete - this.valorDoDesconto;
	}

	public String getDataPedidoFormatada() {
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
		String data = s.format(this.dataDoPedido.getTime());
		return data;
	}

	public String getDataPedidoEntregaFormatada() {
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
		String data = s.format(this.dataEntregaPedidoCalendar.getTime());
		return data;
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

	public Calendar getDataDoPedido() {
		return dataDoPedido;
	}

	public void setDataDoPedido(Calendar dataDoPedido) {
		this.dataDoPedido = dataDoPedido;
	}

	public String getDataEntregaPedido() {
		return dataEntregaPedido;
	}

	public void setDataEntregaPedido(String dataEntregaPedido) {
		this.dataEntregaPedido = dataEntregaPedido;
	}

	public Calendar getDataEntregaPedidoCalendar() {
		return dataEntregaPedidoCalendar;
	}

	public void setDataEntregaPedidoCalendar(Calendar dataEntregaPedidoCalendar) {
		this.dataEntregaPedidoCalendar = dataEntregaPedidoCalendar;
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
