package juli.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 生产环境配置
 */
@Configuration
@Profile("prod")
public class ProdConfiguration {
}