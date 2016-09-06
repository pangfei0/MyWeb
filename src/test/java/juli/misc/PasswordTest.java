package juli.misc;

import juli.SpringTestBase;
import juli.service.UserService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PasswordTest extends SpringTestBase {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    @Test
    public void auditTest() {
        logger.info(userService.getEncryptedPassword("staff", "123456"));
    }
}