package juli.service.ParticularElevatorSync;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class SensorEncoder implements ProtocolEncoder {
    private final Logger logger = Logger.getLogger(SensorEncoder.class);
    private byte[] successByte = {48, 49};
    private byte[] errorByte = {48, 48};
    private byte[] otherByte = {00, 00, 00, 00, 00, 00};
    private byte endByte = 10;

    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
       /* if (message instanceof Result) {

            IoBuffer buf = IoBuffer.allocate(12).setAutoExpand(true);
            try {
                Result result = (Result) message;
                buf.put(result.getVersion()).put(result.getRequestType());
                byte[] data = null;
                if (result.isSuccess()) {
                    buf.put(successByte).put(otherByte);
                    if (result.getValue() != null) {
                        data = result.getValue().getBytes();
                        buf.put(data);
                    }
                } else {
                    data = Integer.toHexString(result.getErrorCode()).getBytes();
                    buf.put(errorByte).put(otherByte).put(data);
                }
                buf.put(endByte).flip();
                out.write(buf);
            } catch (Exception e) {
                logger.error(message, e);
                buf.clear();
            }finally {
                out.flush();
                buf.free();
            }

        } else {
            logger.error("Message is not instanceof Result");
        } */
    }

    public void dispose(IoSession session) throws Exception {

    }

}

