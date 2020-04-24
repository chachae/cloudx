package com.cloudx.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.auth.dao.PermissionDAO;
import com.cloudx.auth.service.IPermissionService;
import com.cloudx.common.entity.system.Permission;
import org.springframework.stereotype.Service;

/**
 * @author chachae
 * @since 2020/4/22 13:55
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDAO, Permission> implements
    IPermissionService {

}
