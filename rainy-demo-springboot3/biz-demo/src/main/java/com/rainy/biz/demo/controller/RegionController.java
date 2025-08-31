package com.rainy.biz.demo.controller;

import com.rainy.biz.demo.dto.RegionDto;
import com.rainy.biz.demo.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Run.Shu
 */
@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/init")
    public void init() {
        regionService.init();
    }

    @GetMapping("/getProvince")
    public List<RegionDto> getProvince() {
        return regionService.getProvince();
    }

    @GetMapping("/getCity")
    public List<RegionDto> getCity(@RequestParam String pid) {
        RegionDto region = new RegionDto();
        region.setParentId(pid);
        return regionService.getCity(region);
    }

    @GetMapping("/getDistrict")
    public List<RegionDto> getDistrict(@RequestParam String pid) {
        RegionDto region = new RegionDto();
        region.setParentId(pid);
        return regionService.getDistrict(region);
    }

}
