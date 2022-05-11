//Name : Malwatta H.G.
//ID : IT19240848


package com.shopping.dal.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.shopping.dal.model.ERole;
import com.shopping.dal.model.Role;



//created new RoleRepository to find out user role in the database
public interface RoleMongoRepository extends MongoRepository<Role, String> {
	
	Optional<Role> findByName(ERole name);
	
}
