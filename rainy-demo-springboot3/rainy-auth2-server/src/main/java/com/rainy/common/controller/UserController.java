package com.rainy.common.controller;

import cn.dev33.satoken.util.SaResult;
import com.rainy.common.controller.vo.LoginVo;
import com.rainy.common.utils.JsonUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    /* ========= 当前登录用户详情 ========= */
    @GetMapping("/detail")
    public Object getUserInfo(LoginVo current) {
        String res = "{\"id\":1,\"username\":\"admin\",\"enable\":true,\"createTime\":\"2023-11-18T08:18:59.150Z\",\"updateTime\":\"2023-11-18T08:18:59.150Z\",\"profile\":{\"id\":1,\"nickName\":\"Admin\",\"gender\":null,\"avatar\":\"https://img.isme.top/isme/67bd93db39a90.png\",\"address\":null,\"email\":null,\"userId\":1},\"roles\":[{\"id\":1,\"code\":\"SUPER_ADMIN\",\"name\":\"超级管理员\",\"enable\":true},{\"id\":2,\"code\":\"ROLE_QA\",\"name\":\"质检员\",\"enable\":true}],\"currentRole\":{\"id\":1,\"code\":\"SUPER_ADMIN\",\"name\":\"超级管理员\",\"enable\":true}}";
        Map map = new HashMap();
        try {
            map = JsonUtils.json2pojo(res, HashMap.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return SaResult.ok().setData(map);
    }

}
