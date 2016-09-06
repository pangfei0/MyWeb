package juli.controller;

import juli.domain.Organization;
import juli.repository.OrganizationRepository;
import juli.service.OrganizationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/organization")
public class OrganizationController {

    @RequestMapping("/view")
    @RequiresPermissions("organization:view")
    public String view() {
        return "user/organization";
    }

    @RequestMapping("/cou")
    public String createOrUpdate() {
        return "user/organizationCoU";
    }
}
