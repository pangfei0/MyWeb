package juli.infrastructure.security;

import juli.domain.Permission;
import juli.domain.Role;
import juli.domain.User;
import juli.repository.UserRepository;
import juli.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JuliRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String userName = token.getUsername();
        String password = String.valueOf(token.getPassword());
        try {
            if (userService.isValidAccount(userName, password)) {
                User user = userRepository.findByUserName(userName);
                return new SimpleAuthenticationInfo(user, token.getCredentials(), getName());
            }
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        //user role是懒加载，需要重新获取最新的用户信息
        user = userRepository.findOne(user.getId());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(getRoleNames(user));
        authorizationInfo.addStringPermissions(userService.getPermissions(user));
        return authorizationInfo;
    }

    public List<String> getRoleNames(User user) {
        return user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    }
}