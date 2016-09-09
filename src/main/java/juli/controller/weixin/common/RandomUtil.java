package juli.controller.weixin.common;

import java.util.Random;

/**
 * Created by 53803 on 2015/9/14.
 */
public class RandomUtil {
    public static long getSixRandomNum(){
        Random random = new Random();
        return random.nextInt(899999)+100000;
    }
}
