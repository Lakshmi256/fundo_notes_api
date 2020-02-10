package com.bridgelabz.fundoonotes.repository;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;

//@Repository
public class LabelRepositoryImplementation implements LabelRepository {
	@Autowired
	private EntityManager entityManager;

	@Override
	public LabelInformation save(LabelInformation labelInformation) {
		Session session = entityManager.unwrap(Session.class);
		session.save(labelInformation);
		return labelInformation;
	}

	@Override
	public LabelInformation fetchLabel(Long userid, String labelname) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("from LabelInfromation where user_id=:id and name=:name");
		q.setParameter("id", userid);
		q.setParameter("name", labelname);
		return (LabelInformation) q.uniqueResult();
	}
	@Override
	public LabelInformation fetchLabelById(Long id) {
		Session session=entityManager.unwrap(Session.class);
		Query q=session.createQuery("from LabelInformation where label_id=:id");
		q.setParameter("id", id);
		return (LabelInformation) q.uniqueResult();
	}
}
