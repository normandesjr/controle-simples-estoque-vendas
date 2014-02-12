package com.semijoias.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="margem_lucro")
public class MargemLucro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private BigDecimal custoInicial;
	private BigDecimal custoFinal;
	private BigDecimal fatorMultiplicacao;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	@Column(name="custo_inicial", nullable=false, precision=10, scale=2)
	@NotNull
	public BigDecimal getCustoInicial() {
		return custoInicial;
	}
	public void setCustoInicial(BigDecimal custoInicial) {
		this.custoInicial = custoInicial;
	}
	
	@Column(name="custo_final", nullable=false, precision=10, scale=2)
	@NotNull
	public BigDecimal getCustoFinal() {
		return custoFinal;
	}
	public void setCustoFinal(BigDecimal custoFinal) {
		this.custoFinal = custoFinal;
	}
	
	@Column(name="fator_multiplicacao", nullable=false, precision=10, scale=2)
	@NotNull
	public BigDecimal getFatorMultiplicacao() {
		return fatorMultiplicacao;
	}
	public void setFatorMultiplicacao(BigDecimal fatorMultiplicacao) {
		this.fatorMultiplicacao = fatorMultiplicacao;
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
		MargemLucro other = (MargemLucro) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
