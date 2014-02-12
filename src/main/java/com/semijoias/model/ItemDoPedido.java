package com.semijoias.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "item_do_pedido")
public class ItemDoPedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private Peca peca;
	private Long quantidade;
	private Pedido pedido;
	private BigDecimal valorUnitario;
	
	private BigDecimal valorTotal;
	
	public ItemDoPedido() {
		setQuantidade(1L);
		setValorUnitario(BigDecimal.ZERO);
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
	@JoinColumn(name="codigo_peca", nullable=false)
	public Peca getPeca() {
		return peca;
	}
	public void setPeca(Peca peca) {
		this.peca = peca;
	}

	@Min(1)
	@NotNull
	public Long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_pedido", nullable=false)
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	@Column(name="valor_unitario", precision=10, scale=2)
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	@Transient
	public BigDecimal getValorTotal() {
		if (valorUnitario != null && quantidade != null) {
			this.valorTotal = valorUnitario.multiply(new BigDecimal(quantidade));
		}
		
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
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
		ItemDoPedido other = (ItemDoPedido) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
