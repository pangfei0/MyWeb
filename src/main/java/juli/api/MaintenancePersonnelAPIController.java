package juli.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.api.dto.MaintenancePersonnelDto;
import juli.domain.*;
import juli.infrastructure.MD5Util;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.*;
import juli.service.MaintenancePersonnelService;
import juli.service.MaintenanceService;
import juli.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;


@Api(value = "维保人员API", description = " ")
@RestController
@RequestMapping("/api/maintenancePersonnel")
public class MaintenancePersonnelAPIController extends APIController<MaintenancePersonnel, MaintenanceRepository> {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private LoginRecordRepository loginRecordRepository;

    @Autowired
    private RepairLevelRepository repairLevelRepository;

    @Autowired
    private WorkBillRepository workBillRepository;

    @Autowired
    private MaintenanceService maintenanceService;
    @Autowired
    private ElevatorBrandRepository elevatorBrandRepository;
    @Autowired
    private MaintenancePersonnelRepository maintenancePersonnelRepository;
    @Autowired
    UserService userService;

    @Autowired
    MaintenancePersonnelService maintenancePersonnelService;
    @ApiOperation("搜索（带分页）")
    @RequestMapping(value = "/search/page", method = RequestMethod.POST)
    public APIResponse searchPage(ServletRequest request,
                                  @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                  @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                  @ApiParam(value = "每页条数", defaultValue = "15")
                                  @RequestParam(value = "pageSize", defaultValue = "15") int pageSize
    ) {

       try
       {
           Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
           Map<String, Object> searchParams_new = new HashMap<>();
           User user = userService.getCurrentUser();
           Role role=user.getRoles().get(0);
               //判断用户是否是维保单位人员，不是则看不到内容
           if(user.getCompanyType().equals("20")||role.getName().equals("超级管理员")) {
               List<String> idList = maintenancePersonnelRepository.findPersonIdsByCompanyId(user.getCompanyId());
               searchParams.put("id_in", getIds(idList));
               searchParams_new.put(("number(TEXT)_LIKE"), searchParams.get("number(TEXT)_LIKE") != null ? searchParams.get("number(TEXT)_LIKE").toString().trim() : "");
               searchParams_new.put(("name(TEXT)_LIKE"), searchParams.get("name(TEXT)_LIKE") != null ? searchParams.get("name(TEXT)_LIKE").toString().trim() : "");
               searchParams_new.put("id_in", searchParams.get("id_in") != null ? searchParams.get("id_in").toString() : "");
               Specification specification = DynamicSpecification.buildSpecification(searchParams_new);

               Page entities = maintenanceRepository.findAll(specification, new PageRequest(pageNumber - 1, pageSize));
               return APIResponse.success(entities);
           } else {
               return APIResponse.success(null);
           }
       }catch (JuliException e) {
           return APIResponse.error(e.getMessage());
       }
    }
    private String getIds(List<String> list) {
        StringBuffer sb = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size() - 1) {
                    sb.append(list.get(i) + ",");
                } else {
                    sb.append(list.get(i));
                }
            }
        } else {
            sb.append("none");
        }
        return sb.toString();
    }
    @ApiOperation("分页查询")
    @RequestMapping(value = "/new/search/page",method = RequestMethod.POST)
    public APIResponse searchNewPage(ServletRequest request,
                                     @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                     @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                     @ApiParam(value = "每页条数", defaultValue = "15") @RequestParam(value = "pageSize", defaultValue = "15") int pageSize)
    {
        return  maintenanceService.findMaintenancePersonal(request, pageNumber, pageSize);
    }
    @ApiOperation("获得对象")
    @RequestMapping(value = "/getInfo/{id}", method = RequestMethod.GET)
    public APIResponse get(@ApiParam("对象ID") @PathVariable("id") String id) {
       return maintenancePersonnelService.getInfor(id);
    }
    @ApiOperation("获取所有人员的Dto")
    @RequestMapping(value = "/maintenance/dto",method = RequestMethod.GET)
    public APIResponse getAllDto() {
        return  maintenanceService.findAllMaintencePersonalDto();
    }

    @ApiOperation("获取所有的电梯品牌等级")
    @RequestMapping(value = "/maintenance/getAllElevatorBrand",method = RequestMethod.GET)
    public APIResponse getSomeBaseInfo()
    {
        Iterator<ElevatorBrand> list=elevatorBrandRepository.findAll().iterator();

        JSONArray jsonArray=new JSONArray();//等级
        while(list.hasNext())
        {
            JSONObject jsonObject=new JSONObject();
//            Map<String,Object> map=new HashMap<>();
            ElevatorBrand eb=list.next();
            List<RepairLevel> repairLevelList=repairLevelRepository.findByElevatorBrand(eb);
//            map.put(eb.getName(),repairLevelList);
            jsonObject.put("elevatorName",eb.getName());
            jsonObject.put("repairLevel",repairLevelList);
            jsonArray.add(jsonObject);
        }
        return APIResponse.success(jsonArray);
    }

    @ApiOperation("查询所有维保负责人")
    @RequestMapping(value = "/searchAllManager/{id}", method = RequestMethod.POST)
    public APIResponse searchAllManager(@ApiParam("公司id") @PathVariable("id") String id) {
        List<String> manager = maintenancePersonnelRepository.findByMaintainerId(id);
        return APIResponse.success(manager);
    }
    @ApiOperation("添加员工")
    @RequestMapping(method = RequestMethod.POST)
    public APIResponse create (MaintenancePersonnelDto dto,String levels)throws JuliException, IOException
    {
        try{
            MaintenancePersonnel personnel=dto.mapTo();
            personnel.setActive("1");
            personnel.setBadgeNumber(0);
            String password="000000";//初始密码默认值000000
            personnel.setPassword(MD5Util.parseStrToMd5L32(password));
            String url="http://localhost:50021/systemAddUnifieduser";
            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("username", personnel.getNumber()));
            parameters.add(new BasicNameValuePair("oneuid", personnel.getName()));
            parameters.add(new BasicNameValuePair("systemid", "1"));
            Content response = Request.Post(url).bodyForm(parameters, Charset.forName("utf-8")).execute().returnContent();
            String res = response.asString(StandardCharsets.UTF_8);
            JSONObject jsonObject = JSON.parseObject(res);
            if (jsonObject.getString("msg").equals("用户名已存在！")) {
                return APIResponse.error("用户名已存在！");
            } else if(jsonObject.getString("success").equals("true")){
                MaintenancePersonnel personnel2=maintenanceRepository.findByNumber(personnel.getNumber());
                setForeignFieldFromDto(personnel2, dto);
                maintenanceRepository.save(personnel2);
                return  APIResponse.success();
            } else {
                return APIResponse.error("其他错误！");
            }
        }catch (Exception e){
            return APIResponse.error("无法连接单点系统");
        }
    }

    @ApiOperation("禁用员工")
    @RequestMapping(value="/delete/{id}",method = RequestMethod.POST)
    public APIResponse create(@ApiParam("员工id")@PathVariable("id") String id) {
        try{
            MaintenancePersonnel maintenancePersonnel=maintenancePersonnelRepository.findById(id);
            maintenancePersonnel.setActive("0");
            maintenanceRepository.save(maintenancePersonnel);
        }
        catch (Exception e)
        {
            return APIResponse.error("存在关联信息，请先删除关联信息");
        }

        return APIResponse.success();
    }
    @ApiOperation("更新员工")
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.PATCH)
    public APIResponse update (@ApiParam("员工id")@PathVariable("id") String id,
                               @ApiParam("需要更新的对象") MaintenancePersonnelDto dto) throws InvocationTargetException, IllegalAccessException {
        MaintenancePersonnel existingEntity = maintenanceRepository.findById(id);
        if (existingEntity == null) {
            return APIResponse.error("不存在此对象：" + id);
        }

        setForeignFieldFromDto(existingEntity, dto);
        maintenanceRepository.save(existingEntity);
        return APIResponse.success();
    }
    @ApiOperation("获取员工维修等级")
    @RequestMapping(value = "/geLevel/{id}",method = RequestMethod.GET)
    public APIResponse getLevelByMaintenanceId(@ApiParam("员工id")@PathVariable("id")String id)
    {
        MaintenancePersonnel existingEntity = maintenanceRepository.findOne(id);
        return APIResponse.success(existingEntity.getLevelList());
    }

    @ApiOperation("删除员工维修等级")
    @RequestMapping(value = "/repairLevel/{id}",method = RequestMethod.DELETE)
    public APIResponse deleteLevel (@ApiParam("员工id")@PathVariable("id") String id,
                                    @ApiParam("需要删除的维修等级id") String levelid) throws InvocationTargetException, IllegalAccessException {
        MaintenancePersonnel existingEntity = maintenanceRepository.findOne(id);
        if (existingEntity == null) {
            return APIResponse.error("不存在此对象：" + id);
        }
        existingEntity.getLevelList().removeIf(new Predicate<RepairLevel>() {
            @Override
            public boolean test(RepairLevel repairLevel) {
                if (repairLevel.getId().equals(levelid))
                    return true;
                else
                    return false;
            }
        });
        maintenanceRepository.save(existingEntity);
        return APIResponse.success();
    }

    @ApiOperation("添加员工维修等级")
    @RequestMapping(value = "/repairLevel/{id}",method = RequestMethod.POST)
    public APIResponse addLevel (@ApiParam("员工id")@PathVariable("id") String id,
                                 @ApiParam("需要添加的维修等级id") String levelid) throws InvocationTargetException, IllegalAccessException {
        MaintenancePersonnel existingEntity = maintenanceRepository.findOne(id);
        if (existingEntity == null) {
            return APIResponse.error("不存在此对象：" + id);
        }
        existingEntity.getLevelList().add(repairLevelRepository.findOne(levelid));
        maintenanceRepository.save(existingEntity);
        return APIResponse.success();
    }

    @ApiOperation("获取员工综合信息")
    @RequestMapping(value="/getSomeInfo/{id}",method =RequestMethod.GET)
    public APIResponse getSomeInfos(@ApiParam("员工id")@PathVariable("id")String id,Date starttime,Date endtime) {
        return APIResponse.success(maintenanceService.getPersonelInfo(id, starttime, endtime));
    }

    @RequiresPermissions("export")
    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public APIResponse export(HttpServletResponse response,String startTime,String endTime) throws Exception {
        maintenanceService.export(response,startTime,endTime);
        return APIResponse.success();
    }

    @RequiresPermissions("export")
    @RequestMapping(value = "/exportForPerson",method = RequestMethod.GET)
    public APIResponse exportForPerson(HttpServletResponse response,HttpServletRequest request,@ApiParam("number")String number,@ApiParam("name")String name) throws Exception {
        maintenanceService.exportForPerson(response,request,number,name);
        return APIResponse.success();
    }

    @ApiOperation("获取员工综合信息")
    @RequestMapping(value="/getMaintenceInfo",method =RequestMethod.POST)
    public APIResponse  getmaintenanceInfo(@ApiParam("员工id")@RequestParam("id")String id,String starttime,String endtime) throws Exception {
        Map<String ,Object> paramMap = maintenanceService.getmaintenanceInfo(id, starttime, endtime);
        return APIResponse.success(paramMap);
    }
    @ApiOperation("导出员工综合信息")
    @RequestMapping(value="/maintenanceInfoExportExcel",method =RequestMethod.GET)
    public APIResponse exportExcel(HttpServletRequest request,HttpServletResponse response,@ApiParam("员工id")@RequestParam("id")String id,String startTime,String endTime) throws Exception {
        maintenanceService.exportExcel(request, response, id,startTime,endTime);
        return  APIResponse.success();
    }
    @ApiOperation("添加人员时判断人员编号是否已存在")
    @RequestMapping(value="/numberSearch",method = RequestMethod.POST)
    public APIResponse numberSearch(@ApiParam("num")String num){
        MaintenancePersonnel maintenancePersonnel=maintenancePersonnelRepository.findByNumber(num);
        if (maintenancePersonnel != null) {
            return APIResponse.success();
        } else {
            return APIResponse.error("");
        }
    }

    @RequestMapping(value="/searchCompanyName",method=RequestMethod.POST)
    public APIResponse searchCompanyName(@ApiParam("companyName")String companyName,@ApiParam("type")int type){
        return maintenanceService.searchCompanyName(companyName, type);
    }
    private void setForeignFieldFromDto(MaintenancePersonnel personnel, MaintenancePersonnelDto dto) {
        personnel.setNumber(dto.getNumber());
        personnel.setName(dto.getName());
        personnel.setTelephone(dto.getTelephone());
        personnel.setRegion(dto.getRegion());
        personnel.setStation(dto.getStation());
        if (StringUtils.isNotEmpty(dto.getMaintainerId())||StringUtils.isNotEmpty(dto.getMaintainerName())) {

            if(StringUtils.isNotEmpty(dto.getMaintainerId())){
                personnel.setMaintainer(companyRepository.findById(dto.getMaintainerId()));
            }
           else if(StringUtils.isNotEmpty(dto.getMaintainerName())){
                personnel.setMaintainer(companyRepository.findByNameForCompany(dto.getMaintainerName()));
            }
        }
        else{
            personnel.setMaintainer(null);
        }
        if(StringUtils.isNotEmpty(dto.getManager())&&!dto.getManager().equals("-1")&&!dto.getManager().equals("--"))
        {
            MaintenancePersonnel manager=maintenanceRepository.findByName(dto.getManager());
            if (manager != null){
                personnel.setParentId(manager.getId());
                personnel.setManager(dto.getManager());
            }
        }
        else
        {
            personnel.setManager(null);
            personnel.setParentId(null);
        }

        String []levelId=dto.getLevelListId().split(",");
        personnel.getLevelList().clear();
        Set<RepairLevel> repairLevelList=new HashSet<>();
        for(String id1:levelId)
        {
            if(!id1.equals(""))
            {
                RepairLevel level=repairLevelRepository.findOne(id1);
                if(level!=null)
                { repairLevelList.add(level);}
            }

        }
        personnel.setLevelList(repairLevelList);
        personnel.setActive("1");
    }







}