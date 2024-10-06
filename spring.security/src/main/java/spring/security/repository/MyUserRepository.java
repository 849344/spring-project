package spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.security.entity.Users;

@Repository
public interface MyUserRepository extends JpaRepository<Users, Integer> {

	Users findByName(String username);
	Users save(Users u);
}
