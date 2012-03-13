package foo.web;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import foo.domain.User;
import foo.repository.UserRepository;

@Controller
@RequestMapping("say-hello")
public class UserController implements InitializingBean {

	@Autowired
	private UserRepository userRepository;

	public void afterPropertiesSet() {
		if (userRepository.count() == 0) {
			for (int i = 0; i < 45; i++) {
				saveUser("username" + 1);
			}
		}
	}

	private void saveUser(String username) {
		User usuario = new User();
		usuario.setFirstName("first");
		usuario.setUsername(username);
		userRepository.save(usuario);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelMap getInitialMessage() {
		ModelMap modelMap = new ModelMap();
		Page<User> usersPage = userRepository.findByUsernameLike("usern%", new PageRequest(4, 10));
		modelMap.addAttribute("usersPage", usersPage);
		modelMap.addAttribute("message", "Enter a Valid Name");
		return modelMap;
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ModelAttribute(value = "message")
	String getGreeting(@RequestParam("username") String username) {
		User user = userRepository.findByUsernameLike(username, new PageRequest(0, 1)).getContent().get(0);
		if (user == null) {
			return "No such user exists! Use 'emuster' or 'jdoe'";
		}
		return "Hello, " + user.getFirstName() + " " + user.getLastName() + "!";
	}
}