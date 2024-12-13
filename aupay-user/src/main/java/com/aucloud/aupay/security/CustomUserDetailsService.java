package com.aucloud.aupay.security;

import com.aucloud.aupay.user.orm.po.AupayUser;
import com.aucloud.aupay.user.orm.service.AupayUserService;
import com.aucloud.commons.exception.ResourceNotFoundException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author waynewang
 * @since 2024-12-09
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    AupayUserService aupayUserService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        AupayUser user = aupayUserService.lambdaQuery().eq(AupayUser::getEmail, email).oneOpt()
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
        );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserByUserId(String userId) {
        LambdaQueryWrapper<AupayUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AupayUser::getUserId, userId);
        AupayUser user = aupayUserService.getOne(queryWrapper);
        if (user == null) {
            throw new ResourceNotFoundException("User", "userId", userId);
        }

        return UserPrincipal.create(user);
    }
}