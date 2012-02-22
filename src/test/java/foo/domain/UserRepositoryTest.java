package foo.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import foo.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-business-context.xml"})
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Before
	public void setUp() {
		for (int i = 0; i < 50; i++) {
			saveUser("username" + 1);
		}
	}
		
	private void saveUser(String username) {
		User usuario = new User();
		usuario.setFirstName("first");
		usuario.setUsername(username);
		userRepository.save(usuario);
	}

	@Test
	public void findCustomersAccounts() {
		int usersPerPage = 10;
		Pageable pageable = new PageRequest(1, usersPerPage);
		Page<User> pageUser = userRepository.findByUsernameLike("username%", pageable);
		System.out.println(pageUser.getContent().size());
		Assert.assertEquals(pageUser.getContent().size(), usersPerPage);
	}
	
}
