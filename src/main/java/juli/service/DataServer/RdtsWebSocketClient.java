package juli.service.DataServer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import juli.domain.Elevator;
import juli.repository.ElevatorRepository;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;

@Component
@Scope("prototype")
@ClientEndpoint
public class RdtsWebSocketClient {

    @Value("${dataparsing.url}")
    String dataparsingUrl;

    Logger logger = LoggerFactory.getLogger(getClass());
    HashMap<String, Elevator> elevators = new HashMap<>();

    JSONObject serverData;
    String sid;
    Session session;

    @Autowired
    StatusSyncService statusSyncService;

    @Autowired
    ElevatorRepository elevatorRepository;

    private void sendMessageToRDTS() {
        JSONObject json = new JSONObject();
        json.put("from", serverData.getString("from"));
        json.put("proxy", "");
        json.put("to", "gdhs_002@inovance");
        json.put("version", "1.0");
        json.put("securityCode", serverData.getString("securityCode"));
        json.put("msgType", "TD_REALTIME_DATA_ESTABLISH_REQ");
        json.put("sid", sid);
        try {
            session.getBasicRemote().sendText(JSON.toJSONString(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        JSONObject json = JSON.parseObject(message);
        String msgType = json.getString("msgType");
        String regCode = json.getString("deviceSerial");

        //实时数据透传数据
        if (msgType.equals("DEVICE_DATA_TRANSFER")) {
            Elevator elevator = elevatorRepository.findByIntelHardwareNumber(regCode);
            if (elevator == null) return;

            JSONObject addedData = new JSONObject();
            String id = json.getString("deviceSerial") + json.getString("tdSerial") + (json.getInteger("type") != null ? json.getInteger("type") : 0);
            addedData.put("id", id);
            addedData.put("dataSource", json);
            addedData.put("ctrlType", elevator.getControllerType());

            try {
                String response = Request.Post(dataparsingUrl).bodyString(JSON.toJSONString(addedData), ContentType.APPLICATION_JSON).execute().returnContent().asString();
                if (StringUtils.isNotEmpty(response)) {
                    JSONArray parseData = JSON.parseArray(response);
                    logger.info("获得电梯(" + elevator.getId() + ")实时数据");
                    for (Object item : parseData) {
                        if (elevator.getStatus() == 10) {
                            JSONObject jsonItem = (JSONObject) item;
                            String name = jsonItem.getString("name");
                            String escapeValue = jsonItem.getString("escapeValue");
                            String value = jsonItem.getString("value");
                            if(name.equals("检修状态")){
                                if (escapeValue.equals("0")) {
                                    elevator.setMaintenanceStatus(10);
                                } else if (escapeValue.equals("1")) {
                                    elevator.setMaintenanceStatus(20);
                                }
                            }
                            if(name.equals("电梯故障代码") || name.equals("故障代码")) {
                                if (escapeValue.equals("0")){
                                    elevator.setFaultStatus(10);
                                } else {
                                    elevator.setFaultStatus(20);
                                    elevator.setIsHandled(10);
                                    elevator.setFaultCode(escapeValue);
                                    if (elevator.getFaultTime() == null) {
                                        elevator.setFaultTime(new Date());
                                    }
                                }
                            }
                            elevatorRepository.save(elevator);
                        }
                    }
                }
            } catch (IOException e) {
                logger.error("dataparsing失败", e);
            }
        }
    }

    @OnError
    public void onError(Throwable t) {
        logger.error("websocket返回错误：", t);
    }

    public Session connect(String uri, JSONObject json, String sid) {
        this.serverData = json;
        this.sid = sid;
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            session = container.connectToServer(this, URI.create(uri));
            logger.error("连接rdts websocket成功");
            sendMessageToRDTS();
            return session;
        } catch (Exception e) {
            logger.error("连接rdts websocket发生错误", e);
        }

        return null;
    }

    public void sendMessageToRDTS(JSONObject json, String sid) {
        this.serverData = json;
        this.sid = sid;
        sendMessageToRDTS();
    }
}
