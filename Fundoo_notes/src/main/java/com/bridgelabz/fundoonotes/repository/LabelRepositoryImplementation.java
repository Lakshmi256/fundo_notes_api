package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;

@Repository
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
		Query q = session.createQuery("from LabelInformation where user_id=:id and name=:name");
		q.setParameter("id", userid);
		q.setParameter("name", labelname);
		return (LabelInformation) q.uniqueResult();
	}

	@Override
	public LabelInformation fetchLabelById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("from LabelInformation where label_id=:id");
		q.setParameter("id", id);
		return (LabelInformation) q.uniqueResult();
	}

	@Override
	public int deleteLabel(Long i) {
		String hq1 = "DELETE FROM LabelInformation" + "where label_id=:id";
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery(hq1);
		query.setParameter("id", i);
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public List<LabelInformation> getAllLabel(Long id) {
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("FROM LabelInformation WHERE user_Id='" + id + "'").getResultList();
	}

	@Override
	public LabelInformation getLabel(Long id) {
		Session session = entityManager.unwrap(Session.class);
		return (LabelInformation) session.createQuery("FROM LabelInformation WHERE user_Id='" + id + "'")
				.uniqueResult();
	}

}
