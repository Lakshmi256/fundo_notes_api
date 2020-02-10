package com.bridgelabz.fundoonotes.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.dto.NoteInformation;

@Repository
public class NoteRepositoryImplementation implements NoteRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public NoteInformation save(NoteInformation noteInformation) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(noteInformation);
		return noteInformation;
	}

	@Override
	public NoteInformation findById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("from NoteInformation where id=:id");
		q.setParameter("id", id);
		return (NoteInformation) q.uniqueResult();
	}

}
