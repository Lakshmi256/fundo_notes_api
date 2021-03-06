package com.bridgelabz.fundoonotes.redisrepository;

/*
 * author:Lakshmi Prasad A
 */
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;

//@Repository
public class RedisRepository {
	private static final String key = "notes";
	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, Long, Object> hashOperations;

	public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
		hashOperations = redisTemplate.opsForHash();
	}

	public void save(NoteInformation note) {
//S		hashOperations.put("note", note.getId(), note);
	}

	public void save(UserInformation user) {
		hashOperations.put("note", user.getUserId(), user);
	}
}
