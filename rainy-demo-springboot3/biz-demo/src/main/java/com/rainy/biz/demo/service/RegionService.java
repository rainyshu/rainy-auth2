package com.rainy.biz.demo.service;

import com.rainy.biz.demo.condition.RegionCondition;
import com.rainy.biz.demo.dao.RegionDao;
import com.rainy.biz.demo.dto.RegionDto;
import com.rainy.biz.demo.entity.RegionEntity;
import com.rainy.core.server.IBaseDao;
import com.rainy.core.server.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Run.Shu
 */
@Service
public class RegionService extends BaseServiceImpl<RegionEntity, RegionDto> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegionDao regionDao;

    @Override
    public IBaseDao<RegionEntity> getBaseDao() {
        return regionDao;
    }

    public List<RegionDto> getProvince() {
        RegionCondition condition = new RegionCondition();
        condition.setRegionLevel("1");
        condition.setParentId("0");
        return super.getListByCondition(condition);
    }

    public List<RegionDto> getCity(RegionDto region) {
        RegionCondition condition = new RegionCondition();
        condition.setRegionLevel("2");
        condition.setParentId(region.getParentId());
        return super.getListByCondition(condition);
    }

    public List<RegionDto> getDistrict(RegionDto region) {
        RegionCondition condition = new RegionCondition();
        condition.setRegionLevel("3");
        condition.setParentId(region.getParentId());
        return super.getListByCondition(condition);
    }

    public void init() {

        ClassPathResource resource = new ClassPathResource("行政代码.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            int i = 0;
            //数据
            List<RegionDto> list = new ArrayList<>();
            //临时
            Map<String, RegionDto> map = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                if (0 == i++) {
                    continue;
                }
                String[] res = line.split("\\s+");
                String code = res[0];
                String name = res[1];
                if (!code.matches("-?\\d+")) {
                    continue;
                }
                //区域
                RegionDto region = new RegionDto();
                region.setRegionCode(code);
                region.setRegionName(name);
                map.put(code, region);
                if(code.contains("0000")){
                    // 省
                    region.setRegionLevel("1");
                    region.setParentId("0");
                }else if (code.contains("00")) {
                    // 市
                    region.setRegionLevel("2");
                    region.setParentId(Integer.valueOf(Integer.valueOf(code) / 10000 * 10000).toString());
                }else {
                    // 区
                    region.setRegionLevel("3");
                    String cityCode = Integer.valueOf(Integer.valueOf(code) / 100 * 100).toString();
                    region.setParentId(cityCode);
                    if (!map.containsKey(cityCode)) {
                        String provinceCode = Integer.valueOf(Integer.valueOf(cityCode) / 1000 * 1000).toString();
                        RegionDto province = map.get(provinceCode);
                        RegionDto cityRegion = new RegionDto();
                        cityRegion.setRegionCode(cityCode);
                        cityRegion.setRegionName(province.getRegionName());
                        cityRegion.setRegionLevel("2");
                        cityRegion.setParentId(provinceCode);
                        list.add(cityRegion);
                        map.put(cityCode, cityRegion);
                    }
                }
                list.add(region);
            }
            for(RegionDto dto : list) {
                super.add(dto);
            }
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
    }
}
