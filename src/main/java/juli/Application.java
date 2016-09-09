package juli;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *修改启动的端口号。
 实现EmbeddedServletContainerCustomizer接口，重写customize，设置端口号。
 启动项目的时候，直接启动这个类的main方法就可以了。
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "JPAAuditorAware")
public class Application extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    static {
        try{
            // 初始化log4j
            String log4jPath = "";
                // 配置本地地址
                log4jPath = Application.class.getClassLoader().getResource("").getPath()+"log4j.properties";
                logger.info("Log4j线下开发模式初始化。。。");

            logger.info("初始化Log4j。。。。");
            logger.info("path is "+ log4jPath);
            PropertyConfigurator.configure(log4jPath);
        }catch (Exception e){
            logger.error(e.toString());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        final String[] temp = args;

        logger.info("oops: main入口函数编码-" +System.getProperty("file.encoding"));

//        if(Constant.LOG_MODE == 0){
//            SpringApplication.run(Application.class, args);
//            logger.info("oops: 线下开发测试");
//        }else{
//            logger.info("oops:" + args[0]);
//        }

        if(ArrayUtils.isNotEmpty(args))
        {
            // 如果你的应用程序，运行后不自动退出，那么处理start时不要执行正常的代码，否则在部署测试 appctl.sh 的时候，会一直等待进程退出
            if(args[0].equals("startup"))
            {
                new Thread(
                        new Runnable(){
                            public void run(){
                                System.out.println("启动Mythread子线程");
                                logger.info("启动Mythread子线程");
                                SpringApplication.run(Application.class, temp);
                            }
                        }).start();

                System.out.println("program startup");
                logger.info("program startup");
            }else if(args[0].equals("stop"))
            {
                System.out.println("program stop");
                logger.info("program stop");
            }else if(args[0].equals("restart"))
            {
                System.out.println("program restart");
                logger.info("program restart");
            }else if(args[0].equals("status"))
            {
                System.out.println("program status");
                logger.info("program status");
            }
        }
    }
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container){
        container.setPort(50020);
    }
}