package com.semijoias.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private Cliente cliente;
	private List<ItemDoPedido> itensDoPedido;
	private Pagamento pagamento;
	private Date data;
	private BigDecimal desconto;
	private BigDecimal valorTotal;
	
	public Pedido() {
		setData(new Date());
		setDesconto(BigDecimal.ZERO);
		setValorTotal(BigDecimal.ZERO);
		this.itensDoPedido = new ArrayList<>();
		this.pagamento = new Pagamento();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_cliente", nullable=false)
	@NotNull
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="pedido")
	public List<ItemDoPedido> getItensDoPedido() {
		return itensDoPedido;
	}
	public void setItensDoPedido(List<ItemDoPedido> itensDoPedido) {
		this.itensDoPedido = itensDoPedido;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="codigo_pagamento", nullable=false)
	public Pagamento getPagamento() {
		return pagamento;
	}
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	@Temporal(TemporalType.DATE)
	@Column(nullable=false, precision=10, scale=2)
	@NotNull
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

	@Column(nullable=false, precision=10, scale=2)
	public BigDecimal getDesconto() {
		return desconto;
	}
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	@Column(name="valor_total", nullable=false, precision=10, scale=2)
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public void recalcularValorTotal() {
		this.setValorTotal(BigDecimal.ZERO);
		for (ItemDoPedido item : this.getItensDoPedido()) {
			BigDecimal valorTotalDoItem = item.getValorTotal();
			this.setValorTotal(this.getValorTotal().add(valorTotalDoItem));
		}
		
		this.setValorTotal(this.getValorTotal().subtract(this.getDesconto()));
	}
	
	public void recalcularParcelas() {
		this.pagamento.setParcelas(new ArrayList<Parcela>());
		
		Integer numeroParcelas = this.pagamento.getNumeroParcelas();
		if (numeroParcelas != null && numeroParcelas > 0) {
			BigDecimal valorParcela = this.valorTotal.divide(new BigDecimal(numeroParcelas), 2);
			
			Calendar calendar = Calendar.getInstance();
			for (int i = 0; i < numeroParcelas; i++) {
				Parcela parcela = new Parcela();
				parcela.setNumero(i + 1);
				parcela.setValor(valorParcela);
				parcela.setVencimento(calendar.getTime());
				parcela.setPagamento(this.pagamento);
				
				this.pagamento.getParcelas().add(parcela);
				calendar.add(Calendar.MONTH, 1);
			}
		} 
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
