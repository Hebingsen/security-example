package com.github.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long>{

	User findByUsername(String username);

	User findByPhone(String phone);


}
