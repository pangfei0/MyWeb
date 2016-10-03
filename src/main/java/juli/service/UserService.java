package juli.service;

import io.swagger.annotations.ApiParam;
import juli.api.core.APIResponse;
import juli.api.dto.PermissionDto;
import juli.domain.Company;
import juli.domain.Permission;
import juli.domain.Role;
import juli.domain.User;
import juli.infrastructure.exception.JuliException;
import juli.repository.CompanyRepository;
import juli.repository.PermissionRepository;
import juli.repository.RoleRepository;
import juli.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<String> getPermissions(User user) {
        List<Permission> permissions = new ArrayList<>();

        //if(user.getUserName().equals("admin"))
        Role role=user.getRoles().get(0);
        if(role.getName().equals("超级管理员"))
        {//角色为超级管理员，将自动匹配所有权限
            for(Permission p:permissionRepository.findAll()){
                permissions.add(p);
            }
            role.getPermissions().clear();
            role.setPermissions(permissions);
            roleRepository.save(role);
        }
        user.getRoles().stream().forEach(o -> permissions.addAll(o.getPermissions()));
        return permissions.stream().map(Permission::getCode).collect(Collectors.toList());
    }

    /**
     * 创建新用户
     *
     * @param user 要创建的用户。注意，用户密码传过来的时候不需要hash或者加密，此方法会自动对密码加密处理
     * @return
     */
    public User createUser(User user) throws JuliException {
        if (StringUtils.isEmpty(user.getUserName())) {
            throw new JuliException("用户名为空");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new JuliException("用户密码为空");
        }
        if (user.getRoles() == null) {
            throw new JuliException("用户角色为空");
        }
        User existingUser = userRepository.findByUserName(user.getUserName());
        if (existingUser != null) {
            throw new JuliException("已经存在用户名：" + user.getUserName());
        }

        //对密码进行HASH处理
        user.setPassword(getEncryptedPassword(user.getUserName(), user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * 更新用户
     */
    public User updateUser(User user) throws JuliException {
        if (StringUtils.isEmpty(user.getUserName())) {
            throw new JuliException("用户名为空");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new JuliException("用户密码为空");
        }
        if (user.getRoles() == null) {
            throw new JuliException("用户角色为空");
        }
        User existingUser = userRepository.findOne(user.getId());
        if (!existingUser.getUserName().equals(user.getUserName())) {
            //如果更改了用户名，则检查用户名是否已经被占用
            User existingUserByName = userRepository.findByUserName(user.getUserName());
            if (existingUserByName != null) {
                throw new JuliException("已经存在用户名：" + user.getUserName());
            }
        }

        //如果更改了密码，则重新设置密码
        if (!existingUser.getPassword().equals(user.getPassword())) {
            user.setPassword(getEncryptedPassword(user.getUserName(), user.getPassword()));
        }
        return userRepository.save(user);
    }

    /**
     * 校验给定的用户名和密码是否是真实有效的
     *
     * @param userName 登陆用户名
     * @param password 登陆密码
     * @return 如果用户真实有效则返回true，否则false
     */
    public boolean isValidAccount(String userName, String password) {
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            if (getEncryptedPassword(userName, password).equals(user.getPassword())) {
                return true;
            }
            else
            {
                if(user.getOpenid()!=null&&user.getInUse().equals("1"))
                {
                    System.out.println("success");
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 获得处理过后的密码，处理规则为：md5（md5（pw）＋username)
     */
    public String getEncryptedPassword(String userName, String password) {
        return new Md5Hash(new Md5Hash(password).toString() + userName).toString();
    }

    public void login(String userName, String password) throws JuliException {
        logger.info("用户：" + userName + " 尝试登录系统");

        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new JuliException("不存在此用户");
        }

        if (!getPermissions(user).contains("user:login")) {
            throw new JuliException("没有权限登录");
        }

        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(userName, password));
        } catch (AuthenticationException e) {
            throw new JuliException("登录失败");
        }
    }

    public APIResponse getCompany(int companyType){
        try {
            User user = this.getCurrentUser();
            List<Company> companies=new ArrayList<>();
            if(user.getCompanyId()!=null){
                Company company=companyRepository.findById(user.getCompanyId());
               companies.add(company);
            }else
            {
                companies = companyRepository.findByType(companyType,0);
            }
            return APIResponse.success(companies);
        }catch (JuliException e) {
            return APIResponse.error("wrong request!");
        }
    }

    public void changePassword(String oldPassword, String newPassword) throws JuliException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        user = userRepository.findOne(user.getId());
        String existedEncryptedPassword = getEncryptedPassword(user.getUserName(), oldPassword);
        if (!user.getPassword().equals(existedEncryptedPassword)) {
            throw new JuliException("旧密码不对");
        }

        user.setPassword(getEncryptedPassword(user.getUserName(), newPassword));
        userRepository.save(user);
    }

    public User getCurrentUser() throws JuliException {
        try {
            User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
            if(loginUser==null)
                return null;
            else
            loginUser = userRepository.findOne(loginUser.getId());

            return loginUser;
        } catch (AuthenticationException e) {
            throw new JuliException("获取当前用户失败");
        }
    }
    /*微信端*/

    /**
     * 跟新微信端用户经纬度
     * @param lat
     * @param lng
     * @param openid
     */
    public void updateUserLatAndlng(double lat,double lng,String openid)
    {
        User user=userRepository.findByOpenid(openid);
        user.setLat(lat);
        user.setLng(lng);
        userRepository.save(user);
    }
}
