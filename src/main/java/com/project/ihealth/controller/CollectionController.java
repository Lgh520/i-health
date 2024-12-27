package com.project.ihealth.controller;

import com.project.ihealth.dao.CollectionMapper;
import com.project.ihealth.dao.po.CollectionEntry;
import com.project.ihealth.po.RestResponse;
import com.project.ihealth.util.WeightUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/collection")
public class CollectionController {
    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private HttpServletRequest servletRequest;

    @PostMapping("/save")
    public RestResponse<Long> saveCollection(@RequestBody CollectionEntry entry) {
        String clientIp = getClientIpAddr(servletRequest);
        System.out.println("clientIp = " + clientIp);
        entry.setIp(clientIp);
        collectionMapper.save(entry);
        CollectionEntry collectionEntry = collectionMapper.findById(entry.getId());
        String collectionInfo = collectionEntry.getCollectionInfo();
        String png = null;
        if (!StringUtils.isEmpty(collectionInfo)) {
            List<String> strings = dealWithCollectionInfo(collectionInfo);
            String weight = WeightUtil.getWeight(strings);
            png = weight + ".png";
            System.out.println(png);
        }
        // 计算权重
        return RestResponse.success(entry.getId(), png);
    }

    @PostMapping("/saveResult")
    public Object saveDoctorRecommendation(@RequestBody CollectionEntry entry) {
        collectionMapper.update(entry);
        return RestResponse.success(entry.getId());
    }

    private List<String> dealWithCollectionInfo(String collectionInfo) {

        String[] split = collectionInfo.split("\"\\], \\[\"");
        List<String> list = new ArrayList<>(8);
        for (String s : split) {
            String firstChose = s.split(",")[0];
            list.add(firstChose);
        }
        return list;
    }

    private String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if(ip!=null) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
