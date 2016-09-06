package juli.service;

import juli.SpringTestBase;
import juli.domain.User;
import juli.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UserServiceTest extends SpringTestBase {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void createUser() throws Exception {
        String userName = UUID.randomUUID().toString();
        String password = "pass";

        User newUser = new User(userName, password);
        userService.createUser(newUser);

        User addedUser = userRepository.findByUserName(userName);

        Assert.assertNotNull(addedUser);
        Assert.assertNotEquals(addedUser.getPassword(), password);

        Assert.assertTrue(userService.isValidAccount(userName, password));
    }
}