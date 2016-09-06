package juli.service.ParticularElevatorSync;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class SensorDecoder implements ProtocolDecoder {

    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        byte[] bytes = new byte[in.limit()];
        in.get(bytes);
        out.write(bytes);
    }

    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {

    }

    public void dispose(IoSession session) throws Exception {

    }
}
