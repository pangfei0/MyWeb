package juli.api;

import juli.SpringWebTestBase;
import juli.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAPITest extends SpringWebTestBase {

    @Autowired
    UserRepository userRepository;

    @Test
    public void createUser() throws Exception {
//        this.mockMvc.perform(post("/api/user")
//                .param("userName", "11")
//                .param("password", "11"))
//                .andExpect(status().isOk());
    }
}