package com.bridgelabz.fundoonotes.repository;

/*
 * author:Lakshmi Prasad A
 */
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.Profile;

@Repository
public interface ProfilePicRepository extends JpaRepository<Profile, Long> {

	@Query(value = "select * from profile_pic where user_id=? ", nativeQuery = true)
	Profile findUserById(Long userId);
}
