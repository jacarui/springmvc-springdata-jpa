package foo.domain;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
		User usuario = new User();
		usuario.setFirstName("first");
		usuario.setUsername("username");
		userRepository.save(usuario);
	}
		
	@Test
	public void findsCustomersAccounts() {
		User user = userRepository.findByUsername("username");

		assertFalse(user == null);
	}
	
}
