package juli.controller;

import juli.api.dto.RoleDto;
import juli.domain.*;
import juli.domain.enums.CompanyType;
import juli.infrastructure.exception.JuliException;
import juli.repository.CompanyRepository;
import juli.repository.PermissionRepository;
import juli.repository.RoleRepository;
import juli.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    UserService userService;

    @RequestMapping("/authorization")
    @RequiresPermissions("user:view")
    public String authorization() {
        return "user/authorization";
    }

    @RequestMapping("/seniorUserApply")
    public String seniorUserApply() {
        return "user/seniorUserApply";
    }

    @RequestMapping("/auditPermission")
    @RequiresPermissions("auditPermission:view")
    public String auditPermission() {
        return "user/auditPermission";
    }


    @RequestMapping("/authorization/cou")
    @RequiresPermissions("user:view")
    public String createOrUpdate(Model model) {
        try{
            User user = userService.getCurrentUser();
            List<Map<String,Object>> mapList=new ArrayList<>();
            List<RoleDto> roles = new ArrayList<>();
            if(user.getCompanyType()!=null){
                CompanyType companyType=CompanyType.getCompanyType(Integer.parseInt(user.getCompanyType()));
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("id", String.valueOf(companyType.getCode()));
                paramMap.put("name", companyType.getName());
                mapList.add(paramMap);
                List<Role> roles1=roleRepository.findRoleByParentId(user.getRoles().get(0).getId());
                for (Role role : roles1) {
                    roles.add(new RoleDto().mapFrom(role));
                }
            }
            else {
                for (CompanyType companyType1 : CompanyType.values()) {
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("id", String.valueOf(companyType1.getCode()));
                    paramMap.put("name", companyType1.getName());
                    mapList.add(paramMap);
                }
                for (Role role : roleRepository.findAll()) {
                    roles.add(new RoleDto().mapFrom(role));
                }
            }
            roles.add(new RoleDto().mapFrom(user.getRoles().get(0)));
            model.addAttribute("roles", roles);
            model.addAttribute("companyTypes", mapList);
             return "user/authorizationCoU";
        }catch (JuliException e) {
            return "wrong request!";
        }
    }

    @RequestMapping("/role/cou")
    @RequiresPermissions("role:view")
    public String createOrUpdateRole(Model model) {
        try{
            List<Map<String, Object>> mapList = new ArrayList<>();
            User user = userService.getCurrentUser();
            if(user.getCompanyType()!=null){
                CompanyType companyType=CompanyType.getCompanyType(Integer.parseInt(user.getCompanyType()));
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("id", String.valueOf(companyType.getCode()));
                paramMap.put("name", companyType.getName());
                mapList.add(paramMap);
            }
            else{
                for (CompanyType companyType : CompanyType.values()) {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("id", String.valueOf(companyType.getCode()));
                paramMap.put("name", companyType.getName());
                mapList.add(paramMap);
                }
            }
            model.addAttribute("companyTypes", mapList);
            return "user/roleCoU";
        }catch (JuliException e) {
            return "wrong request!";
    }}

    @RequestMapping("/userInfo")
    public String userInfo() {
        return "user/userInfo";
    }

    @RequestMapping("/userInfo/edit")
    public String userInfoEdit() {
        return "user/userInfoEdit";
    }

    @RequestMapping("/changePassword")
    public String changePassword() {
        return "user/changePassword";
    }
}
