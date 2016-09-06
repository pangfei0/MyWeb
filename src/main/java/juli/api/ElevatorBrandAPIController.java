package juli.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.domain.Elevator;
import juli.domain.ElevatorBrand;
import juli.domain.Premise;
import juli.domain.RepairLevel;
import juli.infrastructure.excel.ExcelUtil;
import juli.repository.ElevatorBrandRepository;
import juli.repository.RepairLevelRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Api(value = "电梯品牌API", description = " ")
@RestController
@RequestMapping("/api/elevatorBrand")
public class ElevatorBrandAPIController extends APIController<ElevatorBrand,ElevatorBrandRepository> {

    @Autowired
    ElevatorBrandRepository elevatorBrandRepository;

    @Autowired
    RepairLevelRepository repairLevelRepository;
    @ApiOperation("新增电梯品牌")
    @RequestMapping(method = RequestMethod.POST)
    public APIResponse create(ElevatorBrand elevatorBrand) {
        String brandName=elevatorBrand.getName();
       if(brandName.trim().contains("巨立")){
           return APIResponse.error("此电梯品牌已存在：" + brandName);
       }
        elevatorBrandRepository.save(elevatorBrand);

        elevatorBrand=elevatorBrandRepository.findByName(elevatorBrand.getName());
        //新增电梯品牌时，默认新增维保等级：一级，二级，三级
        for(int i=0;i<3;i++){
            RepairLevel repairLevel=new RepairLevel();
            if(i==0) {
                repairLevel.setName("低");
            }
            else if(i==1) {
                repairLevel.setName("中");
            }
            else if(i==2) {
                repairLevel.setName("高");
            }
            repairLevel.setElevatorBrand(elevatorBrand);
            repairLevelRepository.save(repairLevel);
        }

        return APIResponse.success();
    }

    @ApiOperation("更新")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public APIResponse update(@ApiParam("电梯品牌ID") @PathVariable("id") String id,
                              @ApiParam("需要更新的对象") ElevatorBrand elevatorBrand) throws InvocationTargetException, IllegalAccessException{
        ElevatorBrand existingEntity = elevatorBrandRepository.findOne(id);
        if (existingEntity == null) {
            return APIResponse.error("不存在此对象：" + id);
        }
        BeanUtils.copyProperties(existingEntity, elevatorBrand);

        elevatorBrandRepository.save(existingEntity);
        return APIResponse.success();
    }
    @ApiOperation("删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public APIResponse delete(@ApiParam("电梯品牌ID") @PathVariable("id") String id) {
        ElevatorBrand existingEntity = elevatorBrandRepository.findOne(id);
        List<RepairLevel>  repairLevelList=repairLevelRepository.findByElevatorBrand(existingEntity);
        repairLevelRepository.delete(repairLevelList);
        elevatorBrandRepository.delete(existingEntity);
        return APIResponse.success();
    }


    @RequiresPermissions("export")
    @RequestMapping("/export")
    public void export(HttpServletResponse response) throws Exception {

        List<ElevatorBrand> brands = IteratorUtils.toList(elevatorBrandRepository.findAll().iterator());
        LinkedHashMap<String, List<?>> map = new LinkedHashMap<>();
        map.put("电梯品牌数据", brands);

        List<String[]> fieldNames = new ArrayList<>();
        fieldNames.add(new String[]{"number", "name"});

        List<String[]> columNames = new ArrayList<>();
        columNames.add(new String[]{"品牌编号", "品牌名称"});

        ExcelUtil.ExcelExportData setInfo = new ExcelUtil.ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[]{"电梯品牌数据"});
        setInfo.setColumnNames(columNames);

        ExcelUtil.export2Browser(response, setInfo, "电梯品牌数据");
    }



}
