package com.semijoias.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.semijoias.model.MargemLucro;
import com.semijoias.service.AvisoException;
import com.semijoias.service.NegocioException;
import com.semijoias.util.format.Formatador;
import com.semijoias.util.jpa.Transactional;

public class MargensLucro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Inject
	private Formatador formatador;
	
	@Transactional
	public MargemLucro salvar(MargemLucro margemLucro) {
		return manager.merge(margemLucro);
	}

	@SuppressWarnings("unchecked")
	public List<MargemLucro> buscarTodos() {
		return manager.createQuery("from MargemLucro ml order by ml.custoInicial").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public boolean existeCustoNoIntervalo(BigDecimal custo) {
		List<MargemLucro> margensLucro = manager.createQuery("from MargemLucro ml where ml.custoInicial <= :custo and ml.custoFinal >= :custo")
				.setParameter("custo", custo)
				.getResultList();
		
		return margensLucro != null && !margensLucro.isEmpty();
		
	}

	@Transactional
	public void remover(MargemLucro margemLucro) {
		try {
			margemLucro = porCodigo(margemLucro.getCodigo());
            manager.remove(margemLucro);
            manager.flush();
	    } catch (PersistenceException e) {
	            throw new NegocioException("Margem de lucro não pode ser excluída.");
	    }
	}

	public MargemLucro porCodigo(Long codigo) {
		return manager.find(MargemLucro.class, codigo);
	}
	
	public BigDecimal fatorMultiplicacaoPara(BigDecimal custo) {
		StringBuilder sb = new StringBuilder("select ml.fatorMultiplicacao from MargemLucro ml ");
		sb.append("where :custo between ml.custoInicial and ml.custoFinal");
		try {
			return manager.createQuery(sb.toString(), BigDecimal.class)
						.setParameter("custo", custo)
						.getSingleResult();
		} catch (NoResultException e) {
			throw new AvisoException("Margem de lucro não encontrada para o valor da peça " + this.formatador.moeda(custo));
		}
	}
	
}
