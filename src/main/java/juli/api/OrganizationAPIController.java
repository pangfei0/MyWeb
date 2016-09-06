package juli.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.api.dto.MenuDto;
import juli.api.dto.OrganizationDto;
import juli.api.dto.PremiseDto;
import juli.api.dto.ZTreeDto;
import juli.domain.*;
import juli.repository.OrganizationRepository;
import juli.repository.UserRepository;
import juli.service.OrganizationService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "组织架构API", description = " ")
@RestController
@RequestMapping("/api/organization")
public class OrganizationAPIController extends APIController<Organization, OrganizationRepository> {

    @Autowired
    OrganizationService organizationService;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    UserRepository userRepository;


    @RequiresPermissions("organization:new")
    @ApiOperation("新增组织架构")
    @RequestMapping(method = RequestMethod.POST)
    public APIResponse create(Organization organization) {
        organizationRepository.save(organization);
        return APIResponse.success();
    }

    @ApiOperation(value = "组织架构树形结构")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public APIResponse findOrganizationTree() {
        List<ZTreeDto> zTreeDtos = new ArrayList<>();
        List<Organization> organizations = organizationService.getTreeOrganizations();
        for (Organization organization : organizations) {
            ZTreeDto dto = new ZTreeDto();
            zTreeDtos.add(dto);
            convertOrganizationToZtree(dto, organization);
        }
        return APIResponse.success(zTreeDtos);
    }

    @ApiOperation(value = "组织架构扁平树形结构")
    @RequestMapping(value = "/tree/flat", method = RequestMethod.GET)
    public APIResponse findOrganizationFlatTree() {
        List<Organization> organizations = organizationService.getFlatTreeOrganizations();
        List<OrganizationDto> dtos = organizations.stream().map(o -> new OrganizationDto().mapFrom(o)).collect(Collectors.toList());
        return APIResponse.success(dtos);
    }

    @ApiOperation("删除")
    @RequiresPermissions("organization:delete")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public APIResponse delete(@ApiParam("对象ID") @PathVariable("id") String id) {
        Organization entity = organizationRepository.findOne(id);
        if (entity == null) {
            return APIResponse.error("不存在此对象：" + id);
        }

        //todo:transaction
        List<User> users = userRepository.findByOrganization(entity);
        users.stream().forEach(o -> {
            o.setOrganization(null);
            userRepository.save(o);
        });
        organizationRepository.delete(entity.getId());
        return APIResponse.success();
    }

    @ApiOperation("获得对象")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public APIResponse get(@ApiParam("对象ID") @PathVariable("id") String id) {
        Organization entity = organizationRepository.findOne(id);
        if (entity == null) {
            return APIResponse.error("不存在此对象：" + id);
        }

        OrganizationDto organizationDto = new OrganizationDto().mapFrom(entity);
        organizationDto.setParent(entity.getParent().getId());
        return APIResponse.success(organizationDto);
    }

    @ApiOperation("更新一个Menu对象")
    @RequiresPermissions("organization:edit")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public APIResponse update(@ApiParam("ID") @PathVariable("id") String id,
                              @ApiParam("需要更新的对象") OrganizationDto organizationDto) throws InvocationTargetException, IllegalAccessException {
        Organization organization = organizationRepository.findOne(id);
        if (organization == null) {
            return APIResponse.error("不存在此对象：" + id);
        }

        organization.setName(organizationDto.getName());
        organization.setOrderIndex(organizationDto.getOrderIndex());
        if (StringUtils.isNotEmpty(organizationDto.getParent())) {
            organization.setParent(organizationRepository.findOne(organizationDto.getParent()));
        }

        organizationRepository.save(organization);
        return APIResponse.success();
    }


    private void convertOrganizationToZtree(ZTreeDto dto, Organization organization) {
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setOpen(true);
        if (CollectionUtils.isNotEmpty(organization.getChildren())) {
            for (Organization subOrganization : organization.getChildren()) {
                ZTreeDto child = new ZTreeDto();
                dto.getChildren().add(child);
                convertOrganizationToZtree(child, subOrganization);
            }
        }
    }
}