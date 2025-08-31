package com.rainy.biz.demo.controller;

import com.rainy.biz.demo.dto.DemoDto;
import com.rainy.biz.demo.service.DemoService;
import com.rainy.core.common.jdbc.JdbcCondition;
import com.rainy.core.common.jdbc.OpJpaType;
import com.rainy.core.common.jdbc.QueryParam;
import com.rainy.core.server.IBaseService;
import com.rainy.core.server.impl.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController extends BaseController<DemoDto> {

    @Autowired
    private DemoService demoService;

    @GetMapping("/getDemo")
    public String getDemo() {
        DemoDto demoDto = new DemoDto();
        demoDto.setDemoCode("aaa");
        demoDto.setDemoName("bbb");
        demoService.add(demoDto);
        return "success";
    }

    @GetMapping("/getList")
    public List<DemoDto> getList() {
        JdbcCondition condition = new JdbcCondition();
        List<QueryParam> queryParams = new ArrayList<>();
        QueryParam up0 = new QueryParam("updateTime", OpJpaType.GREATER_THAN, "2025-05-01 00:00:00");
        QueryParam up1 = new QueryParam("updateTime", OpJpaType.LESS_THAN, "2025-09-01 00:00:00");
        queryParams.add(up0);
        queryParams.add(up1);
        condition.setQueryParams(queryParams);
        return demoService.getListByCondition(condition);
    }

    @Override
    public IBaseService<DemoDto> getBaseService() {
        return demoService;
    }
}
