package com.semijoias.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

@Entity
@Table(name="pagamento")
public class Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private FormaPagamento formaPagamento;
	private Integer numeroParcelas;
	private List<Parcela> parcelas;
	private boolean completamenteRecebido;
	
	public Pagamento() {
		this.parcelas = new ArrayList<>();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@Enumerated(EnumType.STRING)
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	@Column(name="numero_parcelas")
	@Min(1)
	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}
	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	@OneToMany(mappedBy="pagamento", fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	public List<Parcela> getParcelas() {
		return parcelas;
	}
	public void setParcelas(List<Parcela> parcelas) {
		this.parcelas = parcelas;
	}
	
	@Column(name="completamente_recebido", nullable=false)
	public boolean isCompletamenteRecebido() {
		return completamenteRecebido;
	}
	public void setCompletamenteRecebido(boolean completamenteRecebido) {
		this.completamenteRecebido = completamenteRecebido;
	}

	@Transient
	public boolean isParcelado() {
		if (this.formaPagamento != null && this.formaPagamento.equals(FormaPagamento.PARCELADO)) {
			return true;
		}
		
		return false;
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
		Pagamento other = (Pagamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
