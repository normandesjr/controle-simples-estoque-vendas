package com.semijoias.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.semijoias.model.Peca;
import com.semijoias.repository.filter.PecaFilter;
import com.semijoias.service.NegocioException;
import com.semijoias.util.jpa.Transactional;

public class Pecas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Peca salvar(Peca peca) {
		return manager.merge(peca);
	}

	@SuppressWarnings("unchecked")
	public List<Peca> filtradas(PecaFilter filtro) {
		Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Peca.class);
        
        if (filtro.getCodigo() != null) {
                criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
        }
        
        if (StringUtils.isNotBlank(filtro.getNome())) {
                criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
        }
        
        return criteria.addOrder(Order.asc("nome")).list();
	}
	
	@Transactional
	public void remover(Peca peca) {
		try {
			peca = porCodigo(peca.getCodigo());
			manager.remove(peca);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Peça [Cód. " + peca.getCodigo() + ", nome: " + peca.getNome() + "] não pode ser excluída.");
		}
	}

	private Peca porCodigo(Long codigo) {
		return manager.find(Peca.class, codigo);
	}

	public Peca peloCodigo(Long codigo) {
		return manager.find(Peca.class, codigo);
	}

}
