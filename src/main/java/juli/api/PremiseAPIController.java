package juli.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.api.dto.PremiseDto;
import juli.domain.Premise;
import juli.domain.User;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.excel.ExcelUtil;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.PremiseRepository;
import juli.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Api(value = "楼盘API", description = " ")
@RestController
@RequestMapping("/api/premise")
public class PremiseAPIController extends APIController<Premise, PremiseRepository> {
    @Autowired
    PremiseRepository premiseRepository;
    @Autowired
    UserService userService;

    @RequiresPermissions("premise:new")
    @ApiOperation("新增楼盘")
    @RequestMapping(method = RequestMethod.POST)
    public APIResponse create(PremiseDto premiseDto) {
        Premise premise = premiseDto.mapTo();
        premiseRepository.save(premise);
        return APIResponse.success();
    }

    @ApiOperation("分页查询")
    @RequestMapping(value = "/search/page", method = RequestMethod.POST)
    public APIResponse searchPage(ServletRequest request,
                                  @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                  @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                  @ApiParam(value = "每页条数", defaultValue = "15")
                                  @RequestParam(value = "pageSize", defaultValue = "15") int pageSize
    ) {
        try {
            User user = userService.getCurrentUser();
            Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
            Map<String, Object> searchParams_new = new HashMap<>();
            List<String> idList=new ArrayList<>();
            if (user.getCompanyId() != null && !user.getCompanyId().equals("")) {
                switch (user.getCompanyType()) {
                    case "10"://安装单位
                        idList = premiseRepository.findPremiseIdsByCompanyIdInstall(user.getCompanyId());
                        break;
                    case "20"://维保单位
                        idList = premiseRepository.findPremiseIdsByCompanyIdMaintainer(user.getCompanyId());
                        break;
                    case "30"://使用单位
                        idList = premiseRepository.findPremiseIdsByCompanyIdUser(user.getCompanyId());
                        break;
                    case "40"://制造单位
                        idList = premiseRepository.findPremiseIdsByCompanyIdmanufacturer(user.getCompanyId());
                        break;
                    case "50"://物业单位
                        idList = premiseRepository.findPremiseIdsByCompanyIdOwner(user.getCompanyId());
                        break;
                    case "60"://监管机构
                        idList = premiseRepository.findPremiseIdsByCompanyIdRegulator(user.getCompanyId());
                        break;
                    case "70"://个人用户
                        idList = premiseRepository.findPremiseIdsByCompanyIdPersonal(user.getCompanyId());
                        break;
                    case "80"://其他类型
                        idList = premiseRepository.findPremiseIdsByCompanyIdOthers(user.getCompanyId());
                }
                searchParams.put("id_in", getIds(idList));
                if (searchParams.get("name(TEXT)_LIKE") != null) {
                    searchParams_new.put("name(TEXT)_LIKE", searchParams.get("name(TEXT)_LIKE").toString().trim());
                } else {
                    searchParams_new.put("name(TEXT)_LIKE", "");
                }
                if (searchParams.get("address(TEXT)_LIKE") != null) {
                    searchParams_new.put("address(TEXT)_LIKE", searchParams.get("address(TEXT)_LIKE").toString().trim());
                } else {
                    searchParams_new.put("address(TEXT)_LIKE", "");
                }
                searchParams_new.put("id_in", searchParams.get("id_in") != null ? searchParams.get("id_in").toString() : "");
            }
            Specification specification = DynamicSpecification.buildSpecification(searchParams_new);
            Page entities = premiseRepository.findAll(specification, new PageRequest(pageNumber - 1, pageSize));
            return APIResponse.success(entities);
        } catch (JuliException e) {
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
    @RequiresPermissions("premise:edit")
    @ApiOperation("更新楼盘")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public APIResponse update(@ApiParam("楼盘ID") @PathVariable("id") String id,
                              @ApiParam("需要更新的对象") PremiseDto premiseDto) throws InvocationTargetException, IllegalAccessException {
        Premise existingEntity = premiseRepository.findOne(id);
        if (existingEntity == null) {
            return APIResponse.error("不存在此对象: " + id);
        }
        BeanUtils.copyProperties(existingEntity, premiseDto);
        premiseRepository.save(existingEntity);
        return APIResponse.success();
    }


    @RequiresPermissions("export")
    @RequestMapping("/export")
    public void export(HttpServletResponse response) throws Exception {

        List<Premise> premises = IteratorUtils.toList(premiseRepository.findAll().iterator());
        LinkedHashMap<String, List<?>> map = new LinkedHashMap<>();
        map.put("楼盘数据", premises);

        List<String[]> fieldNames = new ArrayList<>();
        fieldNames.add(new String[]{"name", "address"});

        List<String[]> columNames = new ArrayList<>();
        columNames.add(new String[]{"名称", "详细地址"});

        ExcelUtil.ExcelExportData setInfo = new ExcelUtil.ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[]{"楼盘数据"});
        setInfo.setColumnNames(columNames);

        ExcelUtil.export2Browser(response, setInfo, "楼盘数据");
    }
}
