package juli.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rolePermission")
public class RolePermissionController {

    @RequestMapping("/view")
    @RequiresPermissions("role:view")
    public String view() {
        return "user/role";
    }
}
