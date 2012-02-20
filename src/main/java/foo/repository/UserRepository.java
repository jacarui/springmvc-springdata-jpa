package foo.repository;

import org.springframework.data.repository.CrudRepository;

import foo.domain.User;

public interface UserRepository  extends CrudRepository<User, Long> {
  User findByUsername(String username);
}