package juli.api.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.JsonViewSerializer;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.domain.BaseEntity;
import juli.infrastructure.DateTime.CustomJodaDateEditor;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.persist.DynamicSpecification;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API基类，包含一些默认的API操作
 *
 * @param <TEntity>
 * @param <TRepository>
 */
public class APIController<TEntity extends BaseEntity, TRepository extends PagingAndSortingRepository & JpaSpecificationExecutor> {

    protected ObjectMapper jsonMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    @Autowired
    TRepository entityRepository;

    public APIController() {
        //使用JsonView允许在序列化的时候动态的忽略一些字段，防止循环嵌套的属性
        module.addSerializer(JsonView.class, new JsonViewSerializer());
        jsonMapper.registerModule(module);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(DateTime.class, new CustomJodaDateEditor());
    }

    @ApiOperation("获得所有对象")
    @RequestMapping(method = RequestMethod.GET)
    public APIResponse list() {
        return APIResponse.success(entityRepository.findAll());
    }

    @ApiOperation("搜索（带分页）")
    @RequestMapping(value = "/search/page", method = RequestMethod.POST)
    public APIResponse searchPage(ServletRequest request,
                                  @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                  @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                  @ApiParam(value = "每页条数", defaultValue = "15")
                                  @RequestParam(value = "pageSize", defaultValue = "15") int pageSize
    ) {
        Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");

        Specification specification = DynamicSpecification.buildSpecification(searchParams);
        Page entities = entityRepository.findAll(specification, new PageRequest(pageNumber - 1, pageSize));
        return APIResponse.success(entities);
    }

    @ApiOperation("搜索（不带分页）")
    @RequestMapping(value = "/search/nopage", method = RequestMethod.POST)
    public APIResponse search(ServletRequest request) {
        Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
        Specification specification = DynamicSpecification.buildSpecification(searchParams);
        List entities = entityRepository.findAll(specification);
        return APIResponse.success(entities);
    }

    @ApiOperation("获得所有对象")
    @RequestMapping(value = "/search/all", method = RequestMethod.POST)
    public APIResponse searchAll() {
        return APIResponse.success(entityRepository.findAll());
    }

    @ApiOperation("删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public APIResponse delete(@ApiParam("对象ID") @PathVariable("id") String id) {
        TEntity entity = (TEntity) entityRepository.findOne(id);
        if (entity == null) {
            return APIResponse.error("不存在此对象：" + id);
        }
        entityRepository.delete(entity.getId());
        return APIResponse.success();
    }

    @ApiOperation("获得对象")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public APIResponse get(@ApiParam("对象ID") @PathVariable("id") String id) {
        TEntity entity = (TEntity) entityRepository.findOne(id);
        if (entity == null) {
            return APIResponse.error("不存在此对象：" + id);
        }
        return APIResponse.success(entity);
    }


}