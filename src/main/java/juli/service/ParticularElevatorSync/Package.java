package juli.service.ParticularElevatorSync;

import org.joda.time.DateTime;

import java.util.Date;

public class Package {
    private byte[] requestType;
    private byte[] dataType;
    private byte[] version;
    private byte[] reserve;
    private byte[] data;
    private DateTime receiveTime;

    public byte[] getRequestType() {
        return requestType;
    }

    public void setRequestType(byte... requestType) {
        this.requestType = requestType;
    }

    public byte[] getDataType() {
        return dataType;
    }

    public void setDataType(byte... dataType) {
        this.dataType = dataType;
    }

    public byte[] getVersion() {
        return version;
    }

    public void setVersion(byte... version) {
        this.version = version;
    }

    public byte[] getReserve() {
        return reserve;
    }

    public void setReserve(byte... reserve) {
        this.reserve = reserve;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte... data) {
        this.data = data;
    }

    public DateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(DateTime receiveTime) {
        this.receiveTime = receiveTime;
    }
}