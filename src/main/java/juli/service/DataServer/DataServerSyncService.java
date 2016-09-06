package juli.service.DataServer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import juli.domain.*;
import juli.domain.enums.ElevatorFaultStatus;
import juli.domain.enums.ElevatorMaintennanceStatus;
import juli.domain.enums.ElevatorStatus;
import juli.domain.enums.FaultHandledStatus;
import juli.infrastructure.MD5Util;
import juli.infrastructure.exception.JuliException;
import juli.repository.AreaRepository;
import juli.repository.CompanyRepository;
import juli.repository.ElevatorBrandRepository;
import juli.repository.ElevatorRepository;
import juli.service.DataServer.contracts.DataServerCompany;
import juli.service.DataServer.contracts.DataServerElevator;
import juli.service.DataServer.contracts.DataServerPageObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 同步DataServer平台数据服务
 */
@Service
public class DataServerSyncService {
    Logger logger = LoggerFactory.getLogger(getClass());
    static int PAGE_SIZE = 100;
    String sid = null;

    @Autowired
    ElevatorRepository elevatorRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ElevatorBrandRepository elevatorBrandRepository;


    @Value("${dataserver.url}")
    String baseUrl;

    @Value("${dataserver.user}")
    String userName;

    @Value("${dataserver.password}")
    String password;

    /**
     * 是否已经登陆DataServer平台
     */
    public boolean isLogined() {
        return StringUtils.isNotEmpty(sid);
    }

    public String getSid() {
        return sid;
    }

    /**
     * 登陆DataServer平台
     */
    public String login() throws JuliException, IOException {
        logger.info("登录DataServer平台");
        String url = baseUrl + "/sys/user/user!login.do?from=android";
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("userName", userName));
        parameters.add(new BasicNameValuePair("password", MD5Util.parseStrToMd5U32(password)));
        Content response = Request.Post(url).bodyForm(parameters).execute().returnContent();
        String res = response.asString(StandardCharsets.UTF_8);
        logger.info("DataServer接口返回：" + res);
        JSONObject jsonObject = JSON.parseObject(res);
        if (jsonObject.getString("msg").equals("登录成功")) {
            sid = jsonObject.getJSONObject("obj").getString("sid");
        } else {
            throw new JuliException("登录失败");
        }
        return sid;
    }

    /**
     * 同步dataserver的电梯信息到系统
     */
    public void syncElevators() throws JuliException, IOException {
        if (!isLogined()) login();

        logger.info("同步电梯数据");
        String url = baseUrl + "/baseinfo/elevator/elevator!elevList.do?sid=" + sid + "&rows=" + PAGE_SIZE;
       // String url = baseUrl + "/baseinfo/elevator/elevator!elevMonitorList.do?sid=" + sid + "&rows=" + PAGE_SIZE;
        int totalElevators = 0;
        int totalPage = getTotalPageForListAPI(url);
        for (int pageIndex = 1; pageIndex <= totalPage; pageIndex++) {
            Content response = Request.Post(url + "&page=" + pageIndex).execute().returnContent();
            JSONObject jsonObject = JSON.parseObject(response.asString(StandardCharsets.UTF_8));
            JSONArray jsonArray = JSON.parseArray(jsonObject.getJSONArray("rows").toJSONString());
            for (Object elevator : jsonArray) {
                totalElevators++;
                String elevatorId = ((JSONObject) elevator).getString("id");
                logger.info("同步电梯数据，编号：" + elevatorId);
                try {
                    syncElevator(elevatorId);
                } catch (Exception e) {
                    logger.info("同步电梯错误：" + e.getMessage(), e);
                }
            }
        }
        logger.info("同步完成，共同步 " + totalElevators + " 条电梯数据");
    }

    public void syncAllCompanies() throws IOException, JuliException {
        //安装单位
        syncCompanies(1);
        //维保单位
        syncCompanies(2);
        //使用单位
        syncCompanies(5);
    }

    /**
     * 同步dataserver的单位
     */
    public void syncCompanies(int companyType) throws JuliException, IOException {
        if (!isLogined()) login();

        logger.info("同步安装公司数据");
        String url = baseUrl + "/baseinfo/company/company!companyList.do?sid=" + sid + "&type=" + companyType + "&rows=" + PAGE_SIZE;
        int totalCount = 0;
        int totalPage = getTotalPageForListAPI(url);
        for (int pageIndex = 1; pageIndex <= totalPage; pageIndex++) {
            Content response = Request.Post(url + "&page=" + pageIndex).execute().returnContent();
            JSONObject jsonObject = JSON.parseObject(response.asString(StandardCharsets.UTF_8));
            List<DataServerCompany> items = JSON.parseArray(jsonObject.getJSONArray("rows").toJSONString(), DataServerCompany.class);
            for (DataServerCompany item : items) {
                totalCount++;
                logger.info("同步单位数据，单位名字：" + item.getCompanyName());
                syncInstallCompany(item, companyType);
            }
        }
        logger.info("同步完成，共同步 " + totalCount + " 条数据");
    }

    private void syncInstallCompany(DataServerCompany item, int companyType) {
        int type = 0;
        if(companyType==1){
            type =10;
        }else if(companyType==2){
            type =20;
        }else {
            type = 30;
        }
        try {
            Company company = companyRepository.findByNameAndType(item.getCompanyName(), type);
            if (company != null) return;
            company = new Company();
            company.setId("company-" + UUID.randomUUID().toString());
            company.setName(item.getCompanyName());
            company.setAddress(item.getAddress());
            company.setContact(item.getPrincipal()); //负责人
            company.setPhone(item.getCompanyTelOne());
            company.setMobile(item.getPrincipalTel());//负责人电话

            switch (companyType) {
                case 1:
                    company.setType(10);//安装单位
                    break;
                case 2:
                    company.setType(20);//维保单位
                    company.setMainTainName(item.getPrincipal());//维保联系人
                    company.setMainTainPhone(item.getPrincipalTel());//维保电话
                    break;
                case 5:
                    company.setType(30); //使用单位
                    company.setManagerName(item.getPrincipal());//物业联系人
                    company.setManagerPhone(item.getPrincipalTel());//物业电话
            }
            companyRepository.save(company);
        } catch (Exception e) {
            logger.info("同步单位错误：" + e.getMessage(), e);
        }
    }

    private void syncElevator(String dataServerElevatorId) throws IOException {
        Elevator elevator = elevatorRepository.findByDataServerReferenceId(dataServerElevatorId);
        if (elevator != null) return;

//        ElevatorBrand juliBrand = new ElevatorBrand();
//        juliBrand.setId("brand67f61987-9d2e-4c2a-be7c-09957140d00015");

        DataServerElevator dataServerElevator = getDataServerElevatorForAll(dataServerElevatorId);
        elevator = new Elevator();
        elevator.setId("elevator-" + UUID.randomUUID().toString());
        elevator.setAlias(dataServerElevator.getAliasOfAddress());  //项目名称（电梯具体位置）
        elevator.setNumber(dataServerElevator.getFactoryNO());      //电梯工号
        elevator.setRegCode(dataServerElevator.getRegisterCode());      //注册代码
        elevator.setDataServerReferenceId(dataServerElevatorId);
        if(null!=dataServerElevator.getBuilding()){
            elevator.setProjectName(dataServerElevator.getBuilding().getBuildingName());//注意：同步过来的楼盘名称作为项目名称
        }
        elevator.setIntelHardwareNumber(dataServerElevator.getRegCode());//智能硬件注册码

//        synchronized (DataServerSyncService.class) {
//            String intelHardwareNumber = dataServerElevator.getRegCode();
//            Collector collector = collectorService.findByIntelHardwareNumber(intelHardwareNumber);
//            if (collector == null) {
//                collector = new Collector();
//                collector.setIntelHardwareNumber(intelHardwareNumber);
//                collectorService.save(collector);
//            }
//            elevator.setCollector(collector);           //采集设备注册码
//        }

        elevator.setAddress(dataServerElevator.getAddress());//具体地址
        elevator.setTdSerial(dataServerElevator.getTdSerial());//地址码
        elevator.setControllerType(dataServerElevator.getCtrlType());       //控制器型号
        if(dataServerElevator.getElevatorType().equals("1")){
            elevator.setElevatorType("直梯"); //电梯类型
        }else if(dataServerElevator.getElevatorType().equals("2")){
            elevator.setElevatorType("扶梯");
        }else if(dataServerElevator.getElevatorType().equals("3")){
            elevator.setElevatorType("自动人行道");
        }else
        {
            elevator.setElevatorType(null);
        }
        elevator.setElevatorModel(dataServerElevator.getElevatorModel());    //电梯型号
        elevator.setUsageType(dataServerElevator.getUsageType());//使用场合
        elevator.setStation(dataServerElevator.getFloor());//层站门
        elevator.setRatedSpeed(dataServerElevator.getSpeed());//额定速度
        elevator.setRatedWeight(dataServerElevator.getLoadWeight());
        elevator.setProductionTime(dataServerElevator.getOutFactoryDate());//出厂日期
        elevator.setDutyPhone(dataServerElevator.getDutyPhone());//24小时值班电话
        /*elevator.setMaintainName1(dataServerElevator.getMaintainName1());//维保联系人一
        elevator.setMaintainName2(dataServerElevator.getMaintainName2());//维保联系人二
        elevator.setMaintainName3(dataServerElevator.getMaintainName3());//维保联系人三
        elevator.setManagerName1(dataServerElevator.getManagerName1());//物业联系人一
        elevator.setManagerName2(dataServerElevator.getManagerName2());//物业联系人二
        elevator.setManagerName3(dataServerElevator.getManagerName3());//物业联系人三*/
        elevator.setLastCheckDate(dataServerElevator.getLastcheckDate());//上次年检日期
        elevator.setLastUpkeepTime(dataServerElevator.getLastUpkeepTime());//上次维保时间
        if (StringUtils.isNotEmpty(dataServerElevator.getGpsLng())) {
            elevator.setLng(Double.valueOf(dataServerElevator.getGpsLng()));
            elevator.setLat(Double.valueOf(dataServerElevator.getGpsLat()));
        } else {
            elevator.setLng(0.0);
            elevator.setLat(0.0);
        }
        elevator.setBrandId("brand67f61987-9d2e-4c2a-be7c-09957140d00015");
        Area province = areaRepository.findByName(dataServerElevator.getProvince());
        if (province != null) {
            elevator.setProvinceId(province.getId());

            Area city = areaRepository.findByNameAndParent(dataServerElevator.getCity(), province.getId());
            if (city != null) {
                elevator.setCityId(city.getId());

                Area area = areaRepository.findByNameAndParent(dataServerElevator.getArea(), city.getId());
                if (area != null) {
                    elevator.setRegionId(area.getId());
                }
            }
        }

        if (dataServerElevator.getDeviceStatus() != null) {
            if (dataServerElevator.getDeviceStatus() == 1) {
                elevator.setStatus(ElevatorStatus.ONLINE.getCode());
            } else {
                elevator.setStatus(ElevatorStatus.OFFLINE.getCode());
            }
        }
//
//        if (dataServerElevator.getUpkeepStatus() != null) {
//            if (dataServerElevator.getUpkeepStatus() == 1) {
//                elevator.setMaintenanceStatus(ElevatorMaintennanceStatus.NORMAL.getCode());
//            } else {
//                elevator.setMaintenanceStatus(ElevatorMaintennanceStatus.RECONDITION.getCode());
//            }
//        }
//
//        if (dataServerElevator.getFaultStatus() != null) {
//            if (dataServerElevator.getFaultStatus() == 1) {
//                elevator.setFaultStatus(ElevatorFaultStatus.NORMAL.getCode());
//            } else {
//                elevator.setFaultStatus(ElevatorFaultStatus.MALFUNCTION.getCode());
//            }
//        }
        //安装单位
        if (StringUtils.isNotEmpty(dataServerElevator.getInstallCompanyName())) {
            Company company = companyRepository.findByNameAndType(dataServerElevator.getInstallCompanyName(),10);
            if (company != null) {
                elevator.setInstallCompanyId(company.getId());
            }
        }
        //使用单位
        if (StringUtils.isNotEmpty(dataServerElevator.getCustomerCompanyName())) {
            Company company = companyRepository.findByNameAndType(dataServerElevator.getCustomerCompanyName(),30);
            if (company != null) {
                elevator.setUserCompanyId(company.getId());
            }
        }
        //物业单位
        if (StringUtils.isNotEmpty(dataServerElevator.getPropertyCompanyName())) {
            Company company = companyRepository.findByNameAndType(dataServerElevator.getPropertyCompanyName(),50);
            if (company != null) {
                elevator.setOwnerCompanyId(company.getId());
            }
        }
        //维保单位
        if (StringUtils.isNotEmpty(dataServerElevator.getUpkeepCompanyName())) {
            Company company = companyRepository.findByNameAndType(dataServerElevator.getUpkeepCompanyName(),20 );
            if (company != null) {
                elevator.setMaintainerId(company.getId());
            }
        }
        //制造厂商名称即制造单位
        if (StringUtils.isNotEmpty(dataServerElevator.getManufacturerName())) {
            Company company = companyRepository.findByNameAndType(dataServerElevator.getManufacturerName(),40);
            if (company != null) {
                elevator.setManufacturerId(company.getId());
            }
        }
        elevatorRepository.save(elevator);
    }

    private DataServerElevator getDataServerElevatorForAll(String dataServerElevatorId) throws IOException {
        String url = baseUrl + "/baseinfo/elevator/elevator!getAllById.do?sid=" + sid + "&id=" + dataServerElevatorId;
//        String url = baseUrl + "/baseinfo/elevator/elevator!getById.do?sid=" + sid + "&id=" + dataServerElevatorId;
        Content response = Request.Post(url).execute().returnContent();
        DataServerElevator dataServerElevator = JSON.parseObject(response.asString(StandardCharsets.UTF_8), DataServerElevator.class);
        try{
            JSONObject jsonObject=JSON.parseObject(response.asString(StandardCharsets.UTF_8));
            JSONArray tdList=JSONArray.parseArray(jsonObject.getJSONArray("tdList").toJSONString());
            JSONObject tdObject=JSON.parseObject(tdList.getJSONObject(0).toJSONString());
            dataServerElevator.setCtrlType(tdObject.getString("ctrlType"));
            dataServerElevator.setTdSerial(tdObject.getString("address"));
        }catch (Exception e){

        }

        return dataServerElevator;
    }

    private void getDataServerElevatorFaultStatus() throws JuliException, IOException {
        if (!isLogined()) login();
        String url = baseUrl + "/baseinfo/elevator/elevator!elevMonitorList.do?sid=" + sid + "&elevStatus=6" + "&rows=" + PAGE_SIZE;
        int totalPages = getTotalPageForListAPI(url);
        for (int pageIndex = 1; pageIndex < totalPages + 1; pageIndex ++){
            Content response = Request.Post(url + "&page=" + pageIndex).execute().returnContent();
            JSONObject jsonObject = JSON.parseObject(response.asString(StandardCharsets.UTF_8));
            JSONArray jsonArray = JSON.parseArray(jsonObject.getJSONArray("rows").toJSONString());
            for (Object item : jsonArray){

                String elevatorId = ((JSONObject) item).getString("id");
                Elevator elevator = elevatorRepository.findByDataServerReferenceId(elevatorId);
                if (elevator != null){
                    elevator.setFaultStatus(ElevatorFaultStatus.MALFUNCTION.getCode());
                    elevator.setIsHandled(FaultHandledStatus.NOTHANDLED.getCode());
                    if (elevator.getFaultTime() == null) {
                        elevator.setFaultTime(new Date());
                    }
                    elevatorRepository.save(elevator);
                }
            }
        }
    }

    private void getDataServerElevatorMaintenanceStatus() throws JuliException, IOException {
        if (!isLogined()) login();
        String url = baseUrl + "/baseinfo/elevator/elevator!elevMonitorList.do?sid=" + sid + "&elevStatus=7" + "&rows=" + PAGE_SIZE;
        int totalPages = getTotalPageForListAPI(url);
        for (int pageIndex = 1; pageIndex < totalPages + 1; pageIndex ++){
            Content response = Request.Post(url + "&page=" + pageIndex).execute().returnContent();
            JSONObject jsonObject = JSON.parseObject(response.asString(StandardCharsets.UTF_8));
            JSONArray jsonArray = JSON.parseArray(jsonObject.getJSONArray("rows").toJSONString());
            for (Object item : jsonArray) {

                String elevatorId = ((JSONObject) item).getString("id");
                Elevator elevator = elevatorRepository.findByDataServerReferenceId(elevatorId);
                if (elevator != null){
                    elevator.setMaintenanceStatus(ElevatorMaintennanceStatus.RECONDITION.getCode());
                    elevatorRepository.save(elevator);
                }

            }
        }

    }

    //查询在线和离线的电梯
    private void getDataServerElevatorDeviceStatus(int type) throws JuliException, IOException {
        if (!isLogined()) login();
        String url = baseUrl + "/baseinfo/elevator/elevator!elevMonitorList.do?sid=" + sid + "&deviceStatus=" + type + "&rows=" + PAGE_SIZE;
        int totalPages = getTotalPageForListAPI(url);
        for (int pageIndex = 1; pageIndex < totalPages + 1; pageIndex ++){
            Content response = Request.Post(url + "&page=" + pageIndex).execute().returnContent();
            JSONObject jsonObject = JSON.parseObject(response.asString(StandardCharsets.UTF_8));
            JSONArray jsonArray = JSON.parseArray(jsonObject.getJSONArray("rows").toJSONString());
            for (Object item : jsonArray) {

                String elevatorId = ((JSONObject) item).getString("id");
                Elevator elevator = elevatorRepository.findByDataServerReferenceId(elevatorId);
                if (elevator != null) {
                    if (type == 1){
                        elevator.setStatus(ElevatorStatus.ONLINE.getCode());
                    } else if (type == 2) {
                        elevator.setStatus(ElevatorStatus.OFFLINE.getCode());
                    }
                    elevatorRepository.save(elevator);
                }
            }
        }

    }

    private int getTotalPageForListAPI(String url) throws IOException {
        Content response = Request.Post(url).execute().returnContent();
        DataServerPageObject pageObject = JSON.parseObject(response.asString(StandardCharsets.UTF_8), DataServerPageObject.class);
        return Math.round(pageObject.getTotal() / PAGE_SIZE) + 1;
    }

    public void sync() throws IOException, JuliException {
        syncAllCompanies();
        syncElevators();
        syncElevatorStatus();
    }

//    public void syncElevatorStatus() {
//        for (Elevator elevator : elevatorRepository.findByDataServerReferenceIdNotNull()) {
//            try {
//                DataServerElevator dataServerElevator = getDataServerElevatorForStatusData(elevator.getDataServerReferenceId());
//
//                if (dataServerElevator.getDeviceStatus() != null) {
//                    if (dataServerElevator.getDeviceStatus() == 1) {
//                        elevator.setStatus(ElevatorStatus.ONLINE.getCode());
//                    } else {
//                        elevator.setStatus(ElevatorStatus.OFFLINE.getCode());
//                    }
//                }
//                if (dataServerElevator.getFaultStatus() != null) {
//                    if (dataServerElevator.getFaultStatus() == 1) {
//                        elevator.setFaultStatus(ElevatorFaultStatus.NORMAL.getCode());
//                    } else {
//                        elevator.setFaultStatus(ElevatorFaultStatus.MALFUNCTION.getCode());
//                    }
//                }
//                if (dataServerElevator.getUpkeepStatus() != null) {
//                    if (dataServerElevator.getUpkeepStatus() == 1) {
//                        elevator.setMaintenanceStatus(ElevatorMaintennanceStatus.NORMAL.getCode());
//                    } else {
//                        elevator.setMaintenanceStatus(ElevatorMaintennanceStatus.RECONDITION.getCode());
//                    }
//                }
//            } catch (Exception e) {
//                logger.error("同步电梯状态数据出错：elevator=" + elevator.getNumber(), e);
//            }
//        }
//    }

    public void syncElevatorStatus() {
        try {
            getDataServerElevatorFaultStatus();
            getDataServerElevatorMaintenanceStatus();
            getDataServerElevatorDeviceStatus(1);
            getDataServerElevatorDeviceStatus(2);
        } catch (JuliException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Elevator> getStatusElevators(int elevStatus, int pageIndex) throws IOException, JuliException {
        if (!isLogined()) login();
        String url = baseUrl + "/baseinfo/elevator/elevator!elevMonitorList.do?sid=" + sid + "&elevStatus=" + elevStatus + "&page" + pageIndex + "&rows=20";
        Content response = Request.Post(url).execute().returnContent();
        JSONObject jsonObject = JSON.parseObject(response.asString(StandardCharsets.UTF_8));
        JSONArray jsonArray = JSON.parseArray(jsonObject.getJSONArray("rows").toJSONString());
        List<Elevator> elevators = new ArrayList<>();
        for (Object item : jsonArray) {

            String elevatorId = ((JSONObject) item).getString("id");
            Elevator elevator = elevatorRepository.findByDataServerReferenceId(elevatorId);
            elevators.add(elevator);

        }
        return elevators;
    }

}
