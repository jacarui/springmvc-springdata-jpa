package foo.domain;

import static foo.repository.UserSpecs.bornBefore;
import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import foo.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-business-context.xml" })
public class UserRepositoryTest {

	private static final int NUMBER_OF_USERS = 50;
	private static final int USERS_PER_PAGE = 10;

	@Autowired
	UserRepository userRepository;

	@Before
	public void setUp() {
		userRepository.deleteAll();
		for (int i = 0; i < NUMBER_OF_USERS; i++) {
			saveUser("username" + 1, LocalDate.now().minusYears(i));
		}
	}

	private void saveUser(String username, LocalDate birthdate) {
		User usuario = new User();
		usuario.setFirstName("first");
		usuario.setUsername(username);
		usuario.setBirthdate(birthdate.toDate());
		userRepository.save(usuario);
	}

	@Test
	public void findUserByUserName() {
		Sort sort = new Sort("username");
		List<User> users = userRepository.findByUsernameLike("username%", sort);
		Assert.assertEquals("username1", users.get(0).getUsername());
	}

	@Test
	public void findSorted() {
		Pageable pageable = new PageRequest(1, USERS_PER_PAGE, new Sort("username"));
		Page<User> pageUser = userRepository.findByUsernameLike("username%", pageable);
		Assert.assertEquals(10, pageUser.getContent().size());
	}

	@Test
	public void findUsersBornBefore() {
		List<User> bornBeforeNineties = userRepository.findAll(where(bornBefore(new LocalDate(1990, 1, 1))));
		Assert.assertEquals(NUMBER_OF_USERS - (LocalDate.now().year().get() - 1989), bornBeforeNineties.size());
	}
}
