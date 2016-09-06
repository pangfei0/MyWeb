package juli.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.api.dto.ElevatorDto;
import juli.api.dto.PermissionDto;
import juli.api.dto.RoleDto;
import juli.domain.*;
import juli.domain.enums.CompanyType;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.MenuRepository;
import juli.repository.PermissionRepository;
import juli.repository.RoleRepository;
import juli.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Api(value = "用户角色与权限API", description = " ")
@RestController
@RequestMapping("/api/permission")
public class UserPermissionAPIController extends APIController<Permission, MenuRepository> {

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;


    @ApiOperation("获得所有可用的权限")
    @RequestMapping(value = "/all/flat", method = RequestMethod.GET)
    public APIResponse getAllFlatPermission() {

        try{
            Map<String, List<PermissionDto>> permissions = new TreeMap();
            User user = userService.getCurrentUser();
//            if(user.getRoles().get(0).getParent()!=null&&user.getRoles().get(0).getParent().getName().equals("超级管理员")){
//
//            }
            for (Permission p : user.getRoles().get(0).getPermissions()) {
                String key = p.getCategory();
                PermissionDto dto = new PermissionDto(p.getId(), p.getName(), p.getCode(), p.getPriority(), p.getDescription(), p.getCategory());
                if (permissions.containsKey(key)) {
                    permissions.get(key).add(dto);
                } else {
                    List<PermissionDto> dtos = new ArrayList<>();
                    dtos.add(dto);
                    permissions.put(key, dtos);
                }
            }

            return APIResponse.success(permissions);
        }catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }

    @ApiOperation("获得所有角色")
    @RequiresPermissions("role:view")
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public APIResponse getAllRoles() {
        List<RoleDto> roles = new ArrayList<>();
        for (Role role : roleRepository.findAll()) {
            roles.add(new RoleDto().mapFrom(role));
        }

        return APIResponse.success(roles);
    }

    @ApiOperation("获得角色")
    @RequiresPermissions("role:view")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public APIResponse getRole(@ApiParam("对象ID") @PathVariable("id") String id) {
        Role role = roleRepository.findOne(id);
        if (role == null) {
            return APIResponse.error("不存在此角色：" + id);
        }

        RoleDto dto = new RoleDto().mapFrom(role);
        List<String> permissions = role.getPermissions().stream().map(Permission::getId).collect(Collectors.toList());
        dto.setPermissions(permissions);

        return APIResponse.success(dto);
    }

    @ApiOperation("更新角色")
    @RequiresPermissions("role:edit")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.PATCH)
    public APIResponse update(@ApiParam("ID") @PathVariable("id") String id,
                              @ApiParam("Name") @RequestParam("name") String name,
                              @ApiParam("permissions") @RequestParam("permissions[]") List<String> permissions) {
        Role existingRole = roleRepository.findOne(id);
        if (existingRole == null) {
            return APIResponse.error("不存在此角色：" + id);
        }

        existingRole.setName(name);

        if (existingRole.getPermissions() != null) {
            existingRole.getPermissions().clear();
        } else {
            existingRole.setPermissions(new ArrayList<>());
        }
        for (String permission : permissions) {
            existingRole.getPermissions().add(permissionRepository.findOne(permission));
        }

        roleRepository.save(existingRole);
        return APIResponse.success();
    }

    @ApiOperation("新建角色")
    @RequiresPermissions("role:new")
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public APIResponse update(@ApiParam("需要新建的对象") RoleDto roleDto) throws InvocationTargetException, IllegalAccessException {
        Role role = roleDto.mapTo();
        setForeignFieldFromDto(role,roleDto);

        roleRepository.save(role);
        return APIResponse.success();
    }
    private void setForeignFieldFromDto(Role role, RoleDto roleDto) {


        List<Permission> permissions=new ArrayList<>();
        if (roleDto.getPermissions()!=null) {
            String s="[\\[\\]\"]";
            for(int i=0;i<roleDto.getPermissions().size();i++){
                String sId=roleDto.getPermissions().get(i).toString().replaceAll(s, "");
                Permission permission=permissionRepository.findOne(sId);
                permissions.add(permission);
            }
            role.setPermissions(permissions);
        } else {
            role.setPermissions(null);
        }

        //指定单位管理员只能新增固定单位类型的人员
        try {
            User user = userService.getCurrentUser();
            if(user.getCompanyType()!=null)
            {
                CompanyType companyType=CompanyType.getCompanyType(Integer.parseInt(user.getCompanyType()));
                role.setName(companyType.getName() + role.getName());

                Role role2=roleRepository.findById(user.getRoles().get(0).getId());
                role.setParent(role2);//设置父级角色
            }else
            {
                if(roleDto.getCompanyType()!=null&&!roleDto.getCompanyType().equals("-1"))
                {
                    role.setName(roleDto.getCompanyType() + role.getName());
                   // Role role2=roleRepository.findByType(roleDto.getCompanyType()+"管理员");
                   // role.setParent(role2);//设置父级角色
                }
              //所有公司管理员的上级都是超级管理员
                if(role.getName().contains("管理员"))
                {
                    Role role2=roleRepository.findByName("超级管理员");
                    role.setParent(role2);
                }else
                {
                    Role role2=roleRepository.findById(user.getRoles().get(0).getId());
                    role.setParent(role2);//设置父级角色
                }
            }

        }catch (JuliException e){
            APIResponse.error(e.getMessage());
        }


         role.setInUse("1");
        //role.setOrderIndex(0);//TODO
        //role.setId(UUID.randomUUID().toString());

    }

    @ApiOperation("搜索（带分页）")
    @RequiresPermissions("role:view")
    @RequestMapping(value = "/search/page/role", method = RequestMethod.POST)
    public APIResponse searchPage(ServletRequest request,
                                  @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                  @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                  @ApiParam(value = "每页条数", defaultValue = "15")
                                  @RequestParam(value = "pageSize", defaultValue = "15") int pageSize
    ) {
        try {
            User user = userService.getCurrentUser();
            List<String> idList = new ArrayList<>();
            Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
            if (user.getCompanyId() != null) {
                if(user.getRoles().get(0).getName().contains("管理员")){
                    idList=roleRepository.findroleIdsByUserId(user.getRoles().get(0).getId());
                    idList.add(user.getRoles().get(0).getId());
                }
                searchParams.put(("id_in"),getIds(idList));
            }else
            {
                Role role=user.getRoles().get(0);
                if(!role.getName().equals("超级管理员"))
                {
                    searchParams.put("id_in", "none");
                }
            }
            if(searchParams.get("roleStatus(TEXT)_LIKE")!=null&&!searchParams.get("roleStatus(TEXT)_LIKE").equals("-1")){
                searchParams.put(("inUse_eq"),searchParams.get("roleStatus(TEXT)_LIKE").toString());
                searchParams.remove("roleStatus(TEXT)_LIKE");
            }else
            {
                searchParams.remove("roleStatus(TEXT)_LIKE");
            }
            Sort sort = null;
            String sortName = request.getParameter("sortName");
            String sortOrder = request.getParameter("sortOrder");
            if (sortName == null) {
                sort = new Sort(Sort.Direction.DESC, "createdDate");
            } else {
                if ("name".equals(sortName)) {
                    sortName = "name";
                }
                if ("asc".equals(sortOrder)) {
                    sort = new Sort(Sort.Direction.ASC, sortName);
                } else {
                    sort = new Sort(Sort.Direction.DESC, sortName);
                }
            }
            Specification specification = DynamicSpecification.buildSpecification(searchParams);
            Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
            Page entities = roleRepository.findAll(specification, pageable);
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

    @ApiOperation("禁用角色")
    @RequestMapping(value = "/forbid/{id}", method = RequestMethod.DELETE)
    public APIResponse forbid(@PathVariable("id") String id) {
        Role role = roleRepository.findById(id);
        role.setInUse("0");
        roleRepository.save(role);
        return APIResponse.success();
    }
}
