package com.bridgelabz.fundoonotes.repository;

import javax.persistence.EntityManager;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.fundoonotes.entity.UserInformation;

public class USerRepositoryImplementation implements UserRepository{
@Autowired
	private EntityManager entityManager;

	@Override
	public UserInformation save(UserInformation userInfromation) {
		Session session=entityManager.unwrap(Session.class);
		session.saveOrUpdate(userInfromation);
		return userInfromation;
	}
		@Override
		public UserInformation getUser(String email) {
			Session session=entityManager.unwrap(Session.class);
			Query q=session.createQuery("FROM UserInformation where email=:email");
			q.setParameter("email",email);
			return(UserInformation)q.uniqueResult();
	}

}
