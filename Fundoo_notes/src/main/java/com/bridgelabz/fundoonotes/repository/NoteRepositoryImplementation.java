package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.NoteInformation;

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

	@Override
	public boolean deleteNote(Long id, Long userid) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("DELETE FROM NoteInformation" + "WHERE id=:id").setParameter("id", id);
		int result = query.executeUpdate();
		if (result >= 1) {
			return true;

		}
		return false;
	}

	@Override
	public List<NoteInformation> getNotes(Long userid) {
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("from NoteInformation where user_id='" + userid + "'"
				+ "and is_trashed=false and is_archieved=false ORDER BY id DESC").getResultList();

	}

	@Transactional
	@Override
	public List<NoteInformation> getTrashedNotes(Long userid) {
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("from NoteInformation where user_Id='" + userid + "'" + "and is_trashed=true")
				.getResultList();
	}

	@Transactional
	@Override
	public List<NoteInformation> getArchievedNotes(Long userid) {
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("from NoteInformation where user_Id='" + userid + "'" + "and is_archieved=true")
				.getResultList();
	}

}
