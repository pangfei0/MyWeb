/*
package juli.service.ParticularElevatorSync;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class Receiver extends TextWebSocketHandler {

    Logger logger = Logger.getLogger(getClass());
    private static final String BUFFER = "buffer";
    private static int bufferSize = 2048;
    private static int idleTime=30;
    private BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();

    private static final ArrayList<WebSocketSession> users = new ArrayList<>();

    String mUid;
    Boolean gFlag = false;
    long mLightState;
    int mNumber;
    int mTemp;
    int mHumid;
    Boolean mInfrared;
    int mGyrox;
    int mGyroy;
    int mGyroz;

    //from right to left
    final int LIGHT0 =0xee;
    final int LIGHT1 =0x24;
    final int LIGHT1_1= 0x48;
    final int LIGHT2 =0xd6;
    final int LIGHT3 =0xb6;
    final int LIGHT3_3 =0xda;
    final int LIGHT4 =0x3c;
    final int LIGHT4_4 =0x78;
    final int LIGHT5 =0xba;//02
    final int LIGHT6 =0xbe;
    final int LIGHT6_6= 0xde;
    final int LIGHT7 =0x26;
    final int LIGHT7_7 = 0xc8;
    final int LIGHT8 =0xfe;
    final int LIGHT9 =0xfa;
    final int LIGHT_MINUS =0x10;

    //from left to right

    final int L_RLIGHT0 =0xee;
    final int L_RLIGHT1 =0x24;
    final int L_RLIGHT2 =0xba;
    final int L_RLIGHT3 =0xb6;
    final int L_RLIGHT4 =0x74;
    final int L_RLIGHT5 =0xd6;
    final int L_RLIGHT6 =0xde;
    final int L_RLIGHT7 =0xa4;
    final int L_RLIGHT8 =0xfe;
    final int L_RLIGHT9 =0xf6;
    final int L_RLIGHT9_9= 0xf4;


    //plate 3 version 3
    //led1

    final int DOWNLOAD3 =0x3E;//上行
    final int UPLOAD =0x6E;//7E
    final int TEMP_UPLOAD=0x7e;
    final int STAY =0x10;

    //led5&led6
    final int OPENED_STATE  =0xCAA6;//开门
    final int CLOSED_STATE =0xA6CA;//关门
    final int OPENING_STATE =0x1A16;//开门中
    final int CLOSING_STATE  =0x161A;//关门中
    final int WARNING =0x1010;

    //plateform4
    final int LIGHT_UP =0x0e;
    final int LIGHT_DOWN =0xe0;
    final int LIGHT_STAY = 0x00;
    final int LIGHT_E =0xDA;

    @PostConstruct
    public void init() throws IOException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        final SensorDecoder mSensorDecoder = new SensorDecoder() ;
        final SensorEncoder mSensorEncoder = new SensorEncoder();
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ProtocolCodecFactory() {
            public ProtocolEncoder getEncoder(IoSession session) throws Exception {
                return mSensorEncoder;

            }

            public ProtocolDecoder getDecoder(IoSession session) throws Exception {
                return mSensorDecoder;
            }
        }));
        acceptor.setHandler(new Handler());
        acceptor.getSessionConfig().setReadBufferSize(bufferSize);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, idleTime);
        acceptor.bind(new InetSocketAddress(8810));
        new Thread(new R()).start();
    }

    private class Handler extends IoHandlerAdapter {

        @Override
        public void sessionCreated(IoSession session) throws Exception {
            logger.info("session created");
            logger.info(session.getRemoteAddress().toString());
        }
        @Override
        public void exceptionCaught(IoSession session, Throwable cause) throws Exception {

        }
        @Override
        public void messageReceived(IoSession session, Object message) throws Exception {
            Object buffer = session.removeAttribute(BUFFER);
            if (message instanceof byte[]) {
                DateTime time = new DateTime();
                byte[] bytes = (byte[]) message;
                if (buffer != null) {
                    byte[] fragment = (byte[]) buffer;
                    byte[] tmp = new byte[fragment.length + bytes.length];
                    System.arraycopy(fragment, 0, tmp, 0, fragment.length);
                    System.arraycopy(bytes, 0, tmp, fragment.length, bytes.length);
                    bytes = tmp;
                }
                int start = 0;
                for (int i = 0;i < bytes.length; i++) {
                    if (bytes[i] == '\r') {
                        try {
                            Package p = new Package();
                            p.setVersion(bytes[start], bytes[start + 1]);
                            p.setRequestType(bytes[start + 2], bytes[start + 3]);
                            p.setDataType(bytes[start + 4], bytes[start + 5]);
                            p.setData(Arrays.copyOfRange(bytes, start + 12, i));
                            p.setReceiveTime(time);

                            queue.put(p);
                        }  catch (Exception e) {
                            logger.info("false");
                        } finally {
                            start = i + 1;
                        }
                    }
                }
                if (bytes[bytes.length - 1] != '\r') {
                    session.setAttribute(BUFFER, Arrays.copyOfRange(bytes, start, bytes.length));
                }
            }

        }
        @Override
        public void sessionIdle(IoSession session, IdleStatus status) throws Exception {

        }


    }

    private class R implements Runnable {
        public void run() {
            while (true) {
                try {
                    Object obj = queue.take();
                    Package mPackage = (Package) obj;
                    decode(mPackage);

                } catch (Exception e) {

                }
            }
        }
    }

    //decode
    public void decode(Package mPackage) throws IOException {
        byte[] mBytes = mPackage.getData();
        int mDataType = AsciiUtil.toInt(16, mBytes[0], mBytes[1]);
        int mLength = AsciiUtil.toInt(16, mBytes[2], mBytes[3]);
        int i = 4;
        String mNumber1 = null;
        int mNumber2 = 0;
        int mNumber3 = 0;
        Boolean mlamp1 = false;
        Boolean mlamp2 = false;
        Boolean mlamp3 = false;
        Boolean mlamp4 = false;
        Boolean mlamp5 = false;
        Boolean mlamp6 = false;
        Boolean mlamp7 = false;
        Boolean mlamp8 = false;
        Boolean mlamp9 = false;
        Boolean mlamp10 = false;
        Boolean mlamp11 = false;
        Boolean mlamp12 = false;
        Boolean mlamp13 = false;
        Boolean mlamp14 = false;
        Boolean mlamp15 = false;
        Boolean mlamp16 = false;
        Boolean mlamp17 = false;
        Boolean mlamp18 = false;
        Boolean mlamp19 = false;
        Boolean mlamp20 = false;
        Boolean mlamp21 = false;

        boolean flag = false;

        switch (mDataType){
            case 0x01:          //通力控制板电梯
                while(i<mLength){
                    int tag = AsciiUtil.toInt(16, mBytes[i],mBytes[i+1]);
                    int length = AsciiUtil.toInt(16, mBytes[i+2],mBytes[i+3]);
                    switch(tag){
                        case 0x00:
                            mUid = AsciiUtil.toString(Arrays.copyOfRange(mBytes, i + 4, i + 4 + length));
//                            logger.info("plate1 data start:" + mUid);
                            break;
                        case 0x01:
                            mLightState = AsciiUtil.toLong(16, Arrays.copyOfRange(mBytes, i + 4, i + 4 + length));
                            mlamp1=(mLightState&0x800000) > 0 ? true:false;
                            mlamp2=(mLightState&0x400000) > 0 ? true:false;
                            mlamp3=(mLightState&0x200000) > 0 ? true:false;
                            mlamp4=(mLightState&0x100000) > 0 ? true:false;
                            mlamp5=(mLightState&0x080000) > 0 ? true:false;
                            mlamp6=(mLightState&0x040000) > 0 ? true:false;
                            mlamp7=(mLightState&0x020000) > 0 ? true:false;
                            mlamp8=(mLightState&0x008000) > 0 ? true:false;
                            mlamp9=(mLightState&0x004000) > 0 ? true:false;
                            mlamp10=(mLightState&0x002000) > 0 ? true:false;
                            mlamp11=(mLightState&0x001000) > 0 ? true:false;
                            mlamp12=(mLightState&0x000800) > 0 ? true:false;
                            mlamp13=(mLightState&0x000400) > 0 ? true:false;
                            mlamp14=(mLightState&0x000200) > 0 ? true:false;
                            mlamp15=(mLightState&0x000080) > 0 ? true:false;
                            mlamp16=(mLightState&0x000040) > 0 ? true:false;
                            mlamp17=(mLightState&0x000020) > 0 ? true:false;
                            mlamp18=(mLightState&0x000010) > 0 ? true:false;
                            mlamp19=(mLightState&0x000008) > 0 ? true:false;
                            mlamp20=(mLightState&0x000004) > 0 ? true:false;
                            mlamp21=(mLightState&0x000002) > 0 ? true:false;
                            break;
                        case 0x02:
                            mNumber = AsciiUtil.toInt(16, Arrays.copyOfRange(mBytes, i + 4,  i + 4 + length));

                            mNumber3=parseLed(mNumber&0x00ff, 1);
                            mNumber2=parseLed((mNumber>>8)&0x00ff, 1);

                            if (gFlag == false){
                                mNumber=mNumber2*10+mNumber3;
                            }
                            logger.info("plate1 floor:" + mNumber);
                            break;
                        default:break;
                    }
                    i=i+4+length;
                }

                JSONObject object = new JSONObject();
                String doorStatus = null;
                String direction = null;

                if ((!mlamp4) && (!mlamp5) && (mlamp11)){
                    doorStatus = "opening";
                }
                if ((!mlamp4) && (!mlamp5) && (mlamp6)){
                    doorStatus = "closing";
                }
                if ((mlamp4) && (mlamp5) && (mlamp11)){
                    doorStatus = "closed";
                }

                if (mlamp13){
                    direction = "up";
                }
                if (mlamp21){
                    direction = "down";
                }

                object.put("Type", mUid.substring(9));  //截取muid最后3位
                object.put("doorStatus", doorStatus);   //开关门状态
                object.put("direction", direction);     //上下行
                object.put("floor", mNumber);          //当前楼层

                sendMessageToAllUsers(new TextMessage(object.toJSONString()));
                break;
            case 0x02:
                while(i<mLength){
                    int tag = AsciiUtil.toInt(16, mBytes[i],mBytes[i+1]);
                    int length = AsciiUtil.toInt(16, mBytes[i+2],mBytes[i+3]);
                    switch(tag){
                        case 0x00://uid
                            mUid = AsciiUtil.toString(Arrays.copyOfRange(mBytes, i + 4, i + 4 + length));
//                            logger.info("env data start...");
                            break;
                        case 0x01:
                            mTemp = AsciiUtil.toInt(Arrays.copyOfRange(mBytes, i + 4,  i + 4 + length));
                            break;
                        case 0x02:
                            mHumid = AsciiUtil.toInt( Arrays.copyOfRange(mBytes, i + 4, i + 4 + length));
                            break;
                        case 0x03:
                            //mInfrared = AsciiUtil.toInt(16, Arrays.copyOfRange(mBytes, i + 4,  i + 4 + length));
                            mInfrared= AsciiUtil.toInt(16, Arrays.copyOfRange(mBytes, i + 4,  i + 4 + length))==1 ? true:false;
                            break;
                        case 0x04:
                            mGyrox = AsciiUtil.toInt( Arrays.copyOfRange(mBytes, i + 4,  i + 4 + length-16));
                            mGyroy = AsciiUtil.toInt(Arrays.copyOfRange(mBytes, i + 4+8,  i + 4 + length-8));
                            mGyroz = AsciiUtil.toInt( Arrays.copyOfRange(mBytes, i + 4+16,  i + 4 + length));
                            //float
                            break;
                        default:break;
                    }
                    i=i+4+length;
                }
                //pushToFrontEnd

                break;
            case 0x03:              //03 plate
                // define led
                int mLed1=0;
                int mLed2=0;
                int mLed3=0;
                int mLed4=0;
                int mLed5=0;
                int mLed6=0;
                long mLedState;
                while(i<mLength-2){
                    int tag = AsciiUtil.toInt(16, mBytes[i],mBytes[i+1]);
                    int length = AsciiUtil.toInt(16, mBytes[i+2],mBytes[i+3]);
                    switch(tag){
                        case 0x00://uid
                            mUid = AsciiUtil.toString(Arrays.copyOfRange(mBytes, i + 4, i + 4 + length));
//                            logger.info("plate 3 start...mUid: " + mUid);
                            break;
                        case 0x01://led
                            mLedState = AsciiUtil.toLong(16, Arrays.copyOfRange(mBytes, i + 4, i + 4 + length));
                            switch ((int) (mLedState&0x00000000ffff)){
                                case CLOSED_STATE:
                                    mLed6=0;
                                    break;
                                case OPENED_STATE:
                                    mLed6=1;
                                    break;
                                case CLOSING_STATE:
                                    mLed6=2;
                                    break;
                                case OPENING_STATE:
                                    mLed6=3;
                                    break;
                                case WARNING:
                                    mLed6=4;
                                    break;
                                default:break;
                            }
                            mLed4 = parseLed((int) ((mLedState>>16)&0x0000000000ff),0);
                            mLed3 = parseLed((int) ((mLedState >> 24) & 0x0000000000ff), 0);
                            mNumber = mLed3*10 + mLed4;
                            mLed2= (int) ((mLedState>>32)&0x0000000000ff);
                            switch ((int) ((mLedState>>40)&0x0000000000ff)){
                                case DOWNLOAD3:
                                    mLed1=0;
                                    break;
                                case UPLOAD:
                                case TEMP_UPLOAD:
                                    mLed1=1;
                                    break;
                                case STAY:
                                    mLed1=2;
                                    break;
                                default:break;
                            }
                            logger.info("plate3 floor:" + mNumber);
                            break;
                        default:break;
                    }

                    i=i+4+length;
                }

                JSONObject object1 = new JSONObject();

                object1.put("Type", "003");
                object1.put("direction", mLed1 == 0 ? "down":(mLed1 == 1 ? "up" : " "));
                object1.put("doorStatus", mLed6 == 0 ? "closed":(mLed6 == 1 ? "open" :(mLed6 == 2 ? "closing": (mLed6 == 3 ? "opening" : " "))));
                object1.put("floor", mNumber);

                sendMessageToAllUsers(new TextMessage(object1.toJSONString()));

                break;

            case 0x04:          // no led，plate 4
                long mPlatform4Data = 0;
                int mPlat4Led1=0;
                int mPlat4Led2=0;
                int mPlat4Led3=0;
                while(i<mLength-2){
                    int tag = AsciiUtil.toInt(16, mBytes[i],mBytes[i+1]);
                    int length = AsciiUtil.toInt(16, mBytes[i+2],mBytes[i+3]);
                    switch(tag){
                        case 0x00:
                            mUid = AsciiUtil.toString(Arrays.copyOfRange(mBytes, i + 4, i + 4 + length));
//                            logger.info("platform4 data start...mUid:" + mUid);
                            break;
                        case 0x01:
                            mPlatform4Data = AsciiUtil.toLong(16, Arrays.copyOfRange(mBytes, i + 4, i + 4 + length));

                            switch((int) (mPlatform4Data>>16)){
                                case LIGHT_UP:
                                    mPlat4Led1 = 1;//up
                                    break;
                                case LIGHT_DOWN://down
                                    mPlat4Led1 = 0;
                                    break;
                                case LIGHT_STAY:
                                    mPlat4Led1= 2;//stay
                                default:break;
                            }

                            mPlat4Led3= parseLed((int) (mPlatform4Data&0x0000ff),0);
                            mPlat4Led2= parseLed((int) ((mPlatform4Data >> 8) & 0x0000ff), 0);


                            if (gFlag == false){
                                mNumber=mPlat4Led2*10+mPlat4Led3;
                            }
                            logger.info("plat4楼层:" + mNumber);
                            break;
                        default:break;

                    }
                    i=i+4+length;
                }

                JSONObject object2 = new JSONObject();

                object2.put("Type", "004");
                object2.put("direction", mPlat4Led1 == 1 ? "down" : (mPlat4Led1 == 0 ? "up" : " "));
                object2.put("floor", mNumber);

                sendMessageToAllUsers(new TextMessage(object2.toJSONString()));

                break;
            default:
                break;
        }
    }

    private int parseLed(int type,int encode){
        int mByte = 0;
        if(encode == 0x00){//from left to right.
            switch(type){
                case L_RLIGHT0:
                    mByte = 0;
                    break;
                case L_RLIGHT1:
                    mByte = 1;
                    break;
                case L_RLIGHT2:
                    mByte = 2;
                    break;
                case L_RLIGHT3:
                    mByte = 3;
                    break;
                case L_RLIGHT4:
                    mByte = 4;
                    break;
                case L_RLIGHT5:
                    mByte = 5 ;
                    break;
                case L_RLIGHT6:
                    mByte = 6;
                    break;
                case L_RLIGHT7:
                    mByte = 7;
                    break;
                case L_RLIGHT8:
                    mByte = 8 ;
                    break;
                case L_RLIGHT9_9:
                case L_RLIGHT9:
                    mByte = 9 ;
            }
        }

        if(encode == 0x01) {//from right to left
            switch(type){
                case LIGHT0:
                    mByte = 0;
                    break;
                case LIGHT1:
                case LIGHT1_1:
                    mByte = 1;
                    break;
                case LIGHT2:
                    mByte = 2 ;
                    break;
                case LIGHT3:
                case LIGHT3_3:
                    mByte = 3;
                    break;
                case LIGHT4:
                case LIGHT4_4:
                    mByte = 4;
                    break;
                case LIGHT5:
                    mByte = 5 ;
                    break;
                case LIGHT6:
                    mByte = 6;
                    break;
                case LIGHT7:
                case LIGHT7_7:
                    mByte = 7;
                    break;
                case LIGHT8:
                    mByte = 8 ;
                    break;
                case LIGHT9:
                    mByte = 9 ;
                    break;
                case LIGHT_MINUS:
                    mByte = ( (-1) * mByte) ;
                    gFlag = true ;
                    break;
                default:break;

            }
        }
        return mByte;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.add(session);
//        init();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        users.remove(session);
        logger.info("handleTransportError" + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        users.remove(session);
        logger.info("afterConnectionClosed" + closeStatus.getReason());
    }

    public void sendMessageToAllUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
*/
