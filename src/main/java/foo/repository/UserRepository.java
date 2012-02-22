package foo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import foo.domain.User;

public interface UserRepository  extends CrudRepository<User, Long> {
	User findByUsername(String username);
	Page<User> findByUsername(String username, Pageable pageable);
	Page<User> findByUsernameLike(String username, Pageable pageable);
}