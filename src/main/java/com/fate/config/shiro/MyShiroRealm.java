package com.fate.config.shiro;

//import com.fate.entity.SysPermission;
import com.fate.entity.UserInfo;
import com.fate.service.IUserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Air on 2018/5/12.
 */
public class MyShiroRealm extends AuthorizingRealm{

    @Autowired
    private IUserInfoService userInfoService;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo)principals.getPrimaryPrincipal();
//        for (SysRole role : userInfo.getRoleList()) {
//            authorizationInfo.addRole(role.getRole());
//            for (SysPermission p : role.getPermissions()) {
//                authorizationInfo.addStringPermission(p.getPermission());
//            }
//        }

        return authorizationInfo;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        UserInfo userInfo = userInfoService.findByUsername(username);

        if (userInfo == null){
            return null;
        }
        if (userInfo.getState()==1){
            throw new LockedAccountException();
        }
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                userInfo,
//                userInfo.getPassword(),
//                ByteSource.Util.bytes(userInfo.getUsername()+ new SecureRandomNumberGenerator().nextBytes().toHex()),
//                getName()
//        );
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                userInfo,
//                userInfo.getPassword(),
//                this.getClass().getSimpleName()
//        );
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//            userInfo,
//            userInfo.getPassword(),
//            getName()
//        );
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo,
                userInfo.getPassword(),
                ByteSource.Util.bytes(userInfo.getUsername()),
                getName()
        );
        return authenticationInfo;
    }
}
