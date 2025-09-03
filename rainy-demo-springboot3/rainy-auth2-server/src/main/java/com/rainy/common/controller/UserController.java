package com.rainy.common.controller;

import cn.dev33.satoken.util.SaResult;
import com.rainy.common.condition.UserCondition;
import com.rainy.common.controller.vo.LoginVo;
import com.rainy.common.controller.vo.UserVo;
import com.rainy.common.service.UserService;
import com.rainy.common.utils.JsonUtils;
import com.rainy.core.common.Result;
import com.rainy.core.common.jdbc.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /* ========= 新增用户 ========= */
    @PostMapping("/add")
    public Result<UserVo> add(@RequestBody UserVo userVo) {
        return Result.toSuccess(userService.add(userVo));
    }

    /* ---------- 修改角色 ---------- */
    @PatchMapping("/update")
    public Result<UserVo> update(@RequestBody UserVo userVo) {
        return Result.toSuccess(userService.update(userVo));
    }

    /* ---------- 删除角色 ---------- */
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@PathVariable("id") Long id) {
        UserVo userVo = userService.getById(id);
        userService.deleteLogic(userVo);
        return Result.toSuccess(Boolean.TRUE);
    }

    /* ========= 分页/条件查询用户 ========= */
    @GetMapping("/getPage")
    public Result<Page<UserVo>> getPage(UserCondition condition) {
        Page<UserVo> page = userService.getPageByCondition(condition);
        return Result.toSuccess(page);
    }

    /* ========= 管理员重置密码 ========= */
    @PatchMapping("/password/reset")
    public Result<Boolean> resetPassword(@RequestBody UserVo dto) {
        userService.resetPassword(dto.getId(), dto.getPassword());
        return Result.toSuccess(Boolean.TRUE);
    }

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
