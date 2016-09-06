package juli;

import juli.infrastructure.exception.JuliException;
import juli.service.DataServer.DataServerSyncService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class DataSyncTest extends SpringTestBase {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataServerSyncService dataServerSyncService;

    @Test
    public void loginTest() throws IOException, JuliException {
        dataServerSyncService.sync();
    }
}