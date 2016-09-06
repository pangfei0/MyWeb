package juli.service.DataServer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import juli.infrastructure.exception.JuliException;
import juli.repository.ElevatorRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
@ClientEndpoint
public class StatusSyncService {
    Logger logger = LoggerFactory.getLogger(getClass());
    private Session session;
    private String sid;

    HashMap<String, String> deviceMap = new HashMap<>();
    HashMap<String, RdtsWebSocketClient> rdtsClients = new HashMap<>();

    @Autowired
    ApplicationContext context;

    @Autowired
    ElevatorRepository elevatorRepository;

    @Autowired
    DataServerSyncService dataServerSyncService;

    public void sync() throws JuliException, IOException {
        if (!dataServerSyncService.isLogined()) dataServerSyncService.login();
        sid = dataServerSyncService.sid;
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        String uri = "ws://greatdt.dataserver.cn/monitor/websocket";
        try {
            session = container.connectToServer(this, URI.create(uri));
            logger.error("连接websocket成功");
            sendMessageToAPPServer();
        } catch (Exception e) {
            logger.error("连接websocket发生错误", e);
        }
    }

    @OnMessage
    public void onMessage(String message) {
        JSONObject jsonObject = JSON.parseObject(message);
        String msgType = jsonObject.getString("msgType");
        Integer errorCode = jsonObject.getInteger("errorCode");
        if (msgType.equals("GET_TD_REALTIME_DATA_RSP") && errorCode == 0) {
            String uri = "ws://" + jsonObject.getString("rdtsHost") + ":" + jsonObject.getString("rdtsWsPort");
            if (rdtsClients.containsKey(uri)) {
                Session rdtsSession = rdtsClients.get(uri).session;
                if (rdtsSession != null) {
                    if (rdtsSession.isOpen()) {
                        rdtsClients.get(uri).sendMessageToRDTS(jsonObject, sid);
                        return;
                    } else {
                        rdtsClients.remove(uri);
                    }
                }
            }

            //创建新的websocket连接
            RdtsWebSocketClient rdtsWebSocketClient = context.getBean(RdtsWebSocketClient.class);
            rdtsWebSocketClient.connect(uri, jsonObject, sid);
            rdtsClients.put(uri, rdtsWebSocketClient);
        }
    }

    @OnError
    public void onError(Throwable t) {
        logger.error("websocket返回错误：", t);
    }

    public HashMap<String, String> getSyncElevators() {
        HashMap<String, String> eleMap = new HashMap<>();
        elevatorRepository.findByDataServerReferenceIdNotNull().stream().filter(elevator -> elevator.getIntelHardwareNumber() != null).forEach(elevator -> {
            eleMap.put(elevator.getNumber(), elevator.getIntelHardwareNumber());
            deviceMap.put(elevator.getIntelHardwareNumber(), elevator.getControllerType());
        });
        return eleMap;
    }


    public void sendMessageToAPPServer() {
        HashMap<String, String> eleMap = getSyncElevators();
        Iterator it = eleMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            JSONObject postDataObject = new JSONObject();
            postDataObject.put("from", "evdhs_003@ece");
            postDataObject.put("proxy", "");
            postDataObject.put("to", "gdhs_002@inovance");
            postDataObject.put("version", "1.0");
            postDataObject.put("msgType", "GET_TD_REALTIME_DATA_REQ");
            postDataObject.put("deviceSerial", entry.getValue());
            postDataObject.put("tdSerial", "1");
            postDataObject.put("sid", sid);

            JSONArray tags = new JSONArray();
            JSONObject tag = new JSONObject();
            tags.add(tag);
            tag.put("value", 1);
            tag.put("samplingPeriod", 3);
            postDataObject.put("tdDataTags", tags);

            try {
                if (session != null) {
                    session.getBasicRemote().sendText(JSON.toJSONString(postDataObject));
                    querySignalByData(entry);
                }
            } catch (Exception e) {
                logger.error("sendMessageToAPPServer error", e);
            }

        }
    }

    private void querySignalByData(Map.Entry entry) {
        JSONObject json = new JSONObject();
        json.put("deviceSerial", entry.getValue());
        json.put("msgType", "DEVICE_DATA_TRANSFER");
        json.put("tdSerial", "");
        json.put("transferData", "");
        json.put("sid", sid);
        json.put("transferMsgType", 51);
        try {
            session.getBasicRemote().sendText(JSON.toJSONString(json));
        } catch (Exception e) {
            logger.error("querySignalByData error", e);
        }
    }
}
