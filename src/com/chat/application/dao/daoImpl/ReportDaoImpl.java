package com.chat.application.dao.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.chat.application.dao.ReportDao;
import com.chat.application.domain.SybilAttack;
import com.chat.application.domain.User;
@Service("reportDao")
public class ReportDaoImpl implements ReportDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<SybilAttack> getSybilAttackReport() {
		Query query = entityManager.createQuery("FROM SybilAttack u");
		List<SybilAttack> list = query.getResultList();
		return list;
	}

	public void addReportData(SybilAttack s) {
		entityManager.merge(s);		
	}

}
