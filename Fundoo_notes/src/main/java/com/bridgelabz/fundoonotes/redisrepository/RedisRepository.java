package com.bridgelabz.fundoonotes.redisrepository;

import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.UserInformation;

@Repository
public class RedisRepository {
private static final String key="notes";
private RedisTemplate<String,Object> redisTemplate;
private HashOperations<String,Long, Object> hashOperations;
public RedisRepository(RedisTemplate<String,Object> redisTemplate) {
	this.redisTemplate=redisTemplate;
	hashOperations=redisTemplate.opsForHash();
}
public void save(NoteInformation note) {
	hashOperations.put("note",note.getId(),note);
}

	public void save(UserInformation user) {
		hashOperations.put("note",user.getUserId(),user);
	}
}
