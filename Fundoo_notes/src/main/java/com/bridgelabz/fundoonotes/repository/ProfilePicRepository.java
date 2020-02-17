package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.fundoonotes.entity.Profile;

public interface ProfilePicRepository extends JpaRepository<Profile, Long> {
	@Query(value = "select star from profile_pic where user_id=? ", nativeQuery = true)
	Profile findUserById(Long userId);
}
