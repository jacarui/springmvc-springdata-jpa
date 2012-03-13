package foo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import foo.domain.User;

public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User>,
		QueryDslPredicateExecutor<User> {
	User findByUsername(String username);

	Page<User> findByUsername(String username, Pageable pageable);

	Page<User> findByUsernameLike(String username, Pageable pageable);

	List<User> findByUsernameLike(String username, Sort sort);
}