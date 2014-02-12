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

import com.semijoias.model.Cliente;
import com.semijoias.repository.filter.ClienteFilter;
import com.semijoias.service.NegocioException;
import com.semijoias.util.jpa.Transactional;

public class Clientes implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Cliente salvar(Cliente cliente) {
		return manager.merge(cliente);
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> filtrados(ClienteFilter filtro) {
		Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Cliente.class);
        
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
	public void remover(Cliente cliente) {
		cliente = peloCodigo(cliente.getCodigo());
		
		try {
			manager.remove(cliente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Cliente \"" + cliente.getNome() + "\" não pode ser excluído.");
		}
	}

	public Cliente peloCodigo(Long codigo) {
		return manager.find(Cliente.class, codigo);
	}

}
