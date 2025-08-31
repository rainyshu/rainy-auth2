package com.rainy.common.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.rainy.common.controller.vo.LoginVo;
import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Map<String, String> captcha_Cache = new ConcurrentHashMap<>();

    /* =============== 1. 登录 =============== */
    @PostMapping("/login")
    public Object login(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        // 验证码校验
        String sessionCode = (String) request.getSession().getAttribute("happy-captcha");
        if (sessionCode == null || !sessionCode.equalsIgnoreCase(loginVo.getCaptcha())) {
            throw new RuntimeException("验证码错误");
        }
        // 第一步：比对前端提交的账号名称、密码
        if("admin".equals(loginVo.getUsername()) && "123456".equals(loginVo.getPassword())) {
            // 第二步：根据账号id，进行登录
            StpUtil.login(10001);
            Map<String, String> captchaMap = new HashMap<>();
            captchaMap.put("accessToken", StpUtil.getTokenValue());
            return SaResult.ok().setData(captchaMap);
        }
        return SaResult.error("登录失败");
    }

    /* =============== 6. 获取验证码 =============== */
    @GetMapping("/captcha")
    public void createCaptcha(HttpServletRequest request, HttpServletResponse response) {
        Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 40);
        new HappyCaptcha.Builder(request, response).font(font).type(CaptchaType.ARITHMETIC).build().finish();
        // 2. 从 Session 中取出验证码文本
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("happy-captcha");
        // 3. 再次存储到任何地方（这里演示放进自定义内存 Map）
        //    你也可以放到 Redis、ConcurrentHashMap、Spring Cache…
        captcha_Cache.put(session.getId(), code);
    }

}
