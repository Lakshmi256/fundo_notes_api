package com.bridgelabz.fundoonotes.repository;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;

@Repository
public class LabelRepositoryImplementation implements LabelRepository {
@Autowired
private EntityManager entityManager;
public LabelInformation save(LabelInformation labelInformation) {
	Session session =entityManager.unwrap(Session.class);
	session.save(labelInformation);
	return labelInformation;
}

}
