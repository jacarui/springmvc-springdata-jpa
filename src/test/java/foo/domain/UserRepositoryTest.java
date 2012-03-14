package foo.domain;

import static foo.repository.UserSpecs.bornBefore;
import static org.junit.Assert.assertEquals;
import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysema.query.types.expr.BooleanExpression;

import foo.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-business-context.xml" })
public class UserRepositoryTest {

	private static final int NUMBER_OF_USERS = 50;
	private static final int USERS_PER_PAGE = 10;
	private Date theNineties;

	@Autowired
	UserRepository userRepository;

	@Before
	public void setUp() {
		theNineties = new LocalDate(1990, 1, 1).toDate();
		userRepository.deleteAll();
		for (int i = 1; i <= NUMBER_OF_USERS; i++) {
			saveUser("username" + i, LocalDate.now().minusYears(i));
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
		Sort sort = new Sort(new Order(Direction.ASC, "username"));
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
		List<User> bornBeforeNineties = userRepository.findAll(where(bornBefore(theNineties)));
		assertEquals(NUMBER_OF_USERS - (LocalDate.now().year().get() - 1990), bornBeforeNineties.size());
	}

	@Test
	public void testQueryDsl() {
		QUser usuario = QUser.user;
		BooleanExpression condicion = usuario.username.eq("username1").or(usuario.username.eq("username2"));
		Iterable<User> queryResult = userRepository.findAll(condicion);
		List<User> usuarios = IteratorUtils.toList(queryResult.iterator());
		assertEquals(2, usuarios.size());
	}

	@Test
	public void testUsersBornBeforeDateQueryDsl() {
		QUser user = QUser.user;
		BooleanExpression condicion = user.birthdate.before(theNineties).and(user.modified.isNull());
		Iterable<User> queryResult = userRepository.findAll(condicion);
		List<User> usuarios = IteratorUtils.toList(queryResult.iterator());
		assertEquals(NUMBER_OF_USERS - (LocalDate.now().year().get() - 1990), usuarios.size());
	}
}
