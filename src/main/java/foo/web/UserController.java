package foo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import foo.domain.User;
import foo.repository.UserRepository;

@Controller
@RequestMapping("say-hello")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ModelAttribute("message")
    String getInitialMessage() {
        return "Enter a Valid Name";
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ModelAttribute("message")
    String getGreeting(@RequestParam("username") String username) {
        User user = userRepository.findByUsernameLike(username, new PageRequest(0, 1)).getContent().get(0);
        if (user !=null) {
            return "Hello, " + user.getFirstName() + " " + user.getLastName() + "!";
        } else {
            return "No such user exists! Use 'emuster' or 'jdoe'";
        }
    }
}