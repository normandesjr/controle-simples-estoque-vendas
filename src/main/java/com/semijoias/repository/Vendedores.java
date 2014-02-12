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

import com.semijoias.model.Vendedor;
import com.semijoias.repository.filter.VendedorFilter;
import com.semijoias.service.NegocioException;
import com.semijoias.util.jpa.Transactional;

public class Vendedores implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Vendedor salvar(Vendedor vendedor) {
		return manager.merge(vendedor);
	}
	
	@SuppressWarnings("unchecked")
	public List<Vendedor> filtrados(VendedorFilter filtro) {
		Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Vendedor.class);
        
        if (StringUtils.isNotBlank(filtro.getNome())) {
        	criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
        }
        
        if (StringUtils.isNotBlank(filtro.getTelefone())) {
        	criteria.add(Restrictions.ilike("telefone", filtro.getTelefone(), MatchMode.EXACT));
        }
        
        if (StringUtils.isNotBlank(filtro.getCelular())) {
        	criteria.add(Restrictions.ilike("celular", filtro.getCelular(), MatchMode.EXACT));
        }
        
        return criteria.addOrder(Order.asc("nome")).list();
	}

	@Transactional
	public void remover(Vendedor vendedor) {
		vendedor = peloCodigo(vendedor.getCodigo());
		
		try {
			manager.remove(vendedor);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Vendedor \"" + vendedor.getNome() + "\" não pode ser excluído.");
		}
	}

	public Vendedor peloCodigo(Long codigo) {
		return manager.find(Vendedor.class, codigo);
	}

}
