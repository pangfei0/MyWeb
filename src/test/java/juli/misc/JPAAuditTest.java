package juli.misc;

import juli.SpringTestBase;
import juli.domain.User;
import juli.repository.UserRepository;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JPAAuditTest extends SpringTestBase {

    @Autowired
    UserRepository userRepository;

    @Test
    public void auditTest() {
        User u = new User();
        u.setUserName("u");
        u.setPassword("p");
        u = userRepository.save(u);

        Assert.assertNotNull(u.getCreatedDate());
        Assert.assertNotNull(u.getLastModifiedDate());

        DateTime lastModifyDate = u.getLastModifiedDate();
        DateTime createDate = u.getCreatedDate();
        u.setPassword("ppp");
        u = userRepository.save(u);

        Assert.assertNotEquals(lastModifyDate, u.getLastModifiedDate());
        Assert.assertEquals(createDate, u.getCreatedDate());
    }
}
