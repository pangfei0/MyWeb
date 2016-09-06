package juli.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.api.dto.UserDto;
import juli.domain.Company;
import juli.domain.Organization;
import juli.domain.Role;
import juli.domain.User;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.CompanyRepository;
import juli.repository.OrganizationRepository;
import juli.repository.RoleRepository;
import juli.repository.UserRepository;
import juli.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "用户API", description = " ")
@RestController
@RequestMapping("/api/user")
public class UserAPIController extends APIController<User, UserRepository> {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @ApiOperation("用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public APIResponse login(@ApiParam(value = "用户名", required = true) String userName,
                             @ApiParam(value = "登陆密码", required = true) String password,
                             @RequestParam(defaultValue = "false")
                             @ApiParam(value = "是否是管理后台登陆", required = false, defaultValue = "false") boolean isLoginAdmin
    ) {
        try {
            userService.login(userName, password);
            HashMap<String, String> data = new HashMap<>();
            data.put("redirectUrl", "/realTime");
            return APIResponse.success(data);
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }


    @ApiOperation("获得用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public APIResponse get(@ApiParam("User ID") @PathVariable("id") String id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            return APIResponse.error("不存在此用户");
        }

        UserDto userDto = new UserDto().mapFrom(user);
        if (user.getRoles().size() > 0) {
            userDto.setRoleId(user.getRoles().get(0).getId());
        }
        if (user.getCompanyId() != null) {
            userDto.setCompanyId(user.getCompanyId());
        }
        if (user.getOrganization() != null) {
            userDto.setOrganization(user.getOrganization().getId());
        }
        return APIResponse.success(userDto);
    }

    @ApiOperation("新增用户:游客身份")
    @RequestMapping(value = "/registerVisitor", method = RequestMethod.POST)
    public APIResponse createUser(UserDto userDto) {
        try {
            User user = userDto.mapTo();
            setForeignFieldFromDto(user, userDto);
            user = userService.createUser(user);
            return APIResponse.success(new UserDto().mapFrom(user));
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }


    @ApiOperation("新增用户")
    @RequestMapping(method = RequestMethod.POST)
    public APIResponse create(UserDto userDto) {
        try {
            User user = userDto.mapTo();
            setForeignFieldFromDto(user, userDto);
            user = userService.createUser(user);
            return APIResponse.success(new UserDto().mapFrom(user));
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }

    @ApiOperation("搜索（带分页）")
    @RequestMapping(value = "/search/page", method = RequestMethod.POST)
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
            if (user.getCompanyId() != null && !user.getCompanyId().equals("")) {
                if(user.getRoles().get(0).getName().contains("管理员")){
                    idList=userRepository.findUserIdByCompanyId(user.getCompanyId());
                }
                else
                {
                    idList.add(user.getId());
                }

                searchParams.put(("id_in"),getIds(idList));
            }
            else
            {
                Role role=user.getRoles().get(0);
                if(!role.getName().equals("超级管理员"))
                {
                    searchParams.put("id_in", "none");
                }
            }

            if(searchParams.get("userStatus(TEXT)_LIKE") != null&&!searchParams.get("userStatus(TEXT)_LIKE").equals("-1")){
                searchParams.put(("inUse_eq"),searchParams.get("userStatus(TEXT)_LIKE").toString());
                searchParams.remove("userStatus(TEXT)_LIKE");
            }
            else
            {
                searchParams.remove("userStatus(TEXT)_LIKE");
            }
            searchParams.put(("userName(TEXT)_LIKE"), searchParams.get("userName(TEXT)_LIKE") != null ? searchParams.get("userName(TEXT)_LIKE").toString().trim() : "");
            searchParams.put(("nick(TEXT)_LIKE"), searchParams.get("nick(TEXT)_LIKE") != null ? searchParams.get("nick(TEXT)_LIKE").toString().trim() : "");
            Sort sort = null;
            String sortName = request.getParameter("sortName");
            String sortOrder = request.getParameter("sortOrder");
            if (sortName == null) {
                sort = new Sort(Sort.Direction.DESC, "createdDate");
            } else {
                if ("userName".equals(sortName)) {
                    sortName = "userName";
                }
                if ("nick".equals(sortName)) {
                    sortName = "nick";
                }
                if ("role".equals(sortName)) {
                    sortName = "role";
                }
                if ("asc".equals(sortOrder)) {
                    sort = new Sort(Sort.Direction.ASC, sortName);
                } else {
                    sort = new Sort(Sort.Direction.DESC, sortName);
                }
            }
            Specification specification = DynamicSpecification.buildSpecification(searchParams);
            Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
            Page entities = userRepository.findAll(specification, pageable);
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

    @ApiOperation("新增用户时获得公司名称")
    @RequestMapping(value = "/searchCompany/{companyType}", method = RequestMethod.POST)
    public APIResponse searchCompany(@ApiParam("公司类型") @PathVariable("companyType") int companyType) {
        return userService.getCompany(companyType);
    }

    @ApiOperation("更新用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public APIResponse update(UserDto userDto) {
        try {
            User user = userDto.mapTo();
            setForeignFieldFromDto(user, userDto);
            user = userService.updateUser(user);
            return APIResponse.success(new UserDto().mapFrom(user));
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }

    @ApiOperation("更新用户")
    @RequestMapping(value = "/{id}/selfupdate", method = RequestMethod.PATCH)
    public APIResponse selfUpdate(UserDto userDto) {
        User existingUser = userRepository.findByUserName(userDto.getUserName());
        existingUser.setNick(userDto.getNick());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setTelephone(userDto.getTelephone());
        userRepository.save(existingUser);
        return APIResponse.success();
    }

    @ApiOperation("禁用用户")
    @RequestMapping(value = "/forbid/{id}", method = RequestMethod.DELETE)
    public APIResponse forbid(@PathVariable("id") String id) {
        User existingUser = userRepository.findById(id);
        existingUser.setInUse("0");
        userRepository.save(existingUser);
        return APIResponse.success();
    }


    @ApiOperation("更新密码")
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public APIResponse changePassword(@RequestParam("oldPassword") String oldPassword,
                                      @RequestParam("newPassword") String newPassword) {
        try {
            userService.changePassword(oldPassword, newPassword);
            return APIResponse.success();
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }

    private void setForeignFieldFromDto(User user, UserDto userDto) {
        if (StringUtils.isNotEmpty(userDto.getRoleId())) {
            Role role = roleRepository.findOne(userDto.getRoleId());
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
        } else {
            user.setRoles(null);
        }

        if (StringUtils.isNotEmpty(userDto.getOrganization())) {
            user.setOrganization(organizationRepository.findOne(userDto.getOrganization()));
        } else {
            user.setOrganization(null);
        }
        //添加公司

        if (StringUtils.isNotEmpty(userDto.getCompanyType()) && !userDto.getCompanyType().equals("-1")) {
            user.setCompanyType(userDto.getCompanyType());
        } else {
            user.setCompanyType(null);
        }
        if (StringUtils.isNotEmpty(userDto.getCompanyId()) && !userDto.getCompanyId().equals("-1")) {
            user.setCompanyId(userDto.getCompanyId());
        } else {
            user.setCompanyId(null);
        }
        user.setInUse("1");
    }

    @ApiOperation("获得当前登录用户")
    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    public APIResponse getCurrentUser() {
        try {
            User user = userService.getCurrentUser();
            UserDto userDto = new UserDto().mapFrom(user);
            if (user.getRoles() != null && user.getRoles().size() > 0) {
                userDto.setRoleName(user.getRoles().get(0).getName());
                userDto.setRoleId(user.getRoles().get(0).getId());
            }
            userDto.setUserName(user.getUserName());
            userDto.setCompanyType(user.getCompanyType() == null ? null : user.getCompanyType());
            return APIResponse.success(userDto);
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }


}