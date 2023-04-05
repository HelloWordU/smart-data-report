package com.rz.smartDataReport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.CacheManager;
import com.rz.smartDataReport.common.CurrentLoginUserManager;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.common.ResultEntityList;
import com.rz.smartDataReport.common.enums.NegativeChartPlantFormEnum;
import com.rz.smartDataReport.entity.*;
import com.rz.smartDataReport.pojo.entity.CacheEntity;
import com.rz.smartDataReport.pojo.vo.NegativeRateVo;
import com.rz.smartDataReport.service.INegativeAllService;
import com.rz.smartDataReport.service.INegativeChartService;
import com.rz.smartDataReport.service.INegativeFiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/negativeRate")
@Api(tags = "负面率统计")
public class NegativeRateController {
    @Resource
    private HttpServletRequest request;


    @Resource
    private INegativeAllService iNegativeAllService;

    @Resource
    private INegativeFiveService iNegativeFiveService;

    @Resource
    private INegativeChartService iNegativeChartService;
    Random random = new Random();

    @ApiOperation(value = "获取全网负面率", httpMethod = "GET")
    @GetMapping("/getAll")
    public ResultEntityList<NegativeRate> getAll(@RequestParam int categoryId) {
//        HttpSession session = request.getSession();
//        Integer userId = Integer.getInteger(session.getAttribute("userId").toString());
//        Integer companyId = CurrentLoginUserManager.currentLoginUser.get(userId).getCompanyId();

        List<NegativeRate> data = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        int i = c.get(Calendar.HOUR_OF_DAY);
        String cacheKey = "negativeRate_getAll_"+i;
        CacheEntity cacheInfo = CacheManager.getCacheInfo(cacheKey);
        if(cacheInfo !=null && !cacheInfo.isExpired())
        {
            data = ( List<NegativeRate> )cacheInfo.getValue();
        }else
        {
            LambdaQueryWrapper<NegativeAll> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(NegativeAll::getCategoryId, categoryId);
            List<NegativeAll> listData = iNegativeAllService.list(wrapper);
            Map<Integer, NegativeAll> negativeAllMap = listData.stream().collect(Collectors.toMap(NegativeAll::getTime, n -> n));
            int j = i;
            int totalCount = 0;
            for (; i >= 0; i--) {
                NegativeRate negativeRate = new NegativeRate();
                negativeRate.setKey(i + "时");
                if(negativeAllMap.containsKey(i))
                {
                    negativeRate.setValue(negativeAllMap.get(i).getState());
                }else
                {
                    negativeRate.setValue(randomState());
                }

                data.add(negativeRate);
                totalCount++;
            }
            if (totalCount < 24) {
                for (int m = 1; m < 24 - j; m++) {
                    NegativeRate negativeRate = new NegativeRate();
                    Integer key = (24 - m);
                    negativeRate.setKey( key+ "时");
                    if(negativeAllMap.containsKey(key))
                    {
                        negativeRate.setValue(negativeAllMap.get(key).getState());
                    }else
                    {
                        negativeRate.setValue(randomState());
                    }
                 //   negativeRate.setValue(randomState());
                    data.add(negativeRate);
                    totalCount++;
                }
            }
            Collections.reverse(data);
            cacheInfo = new CacheEntity();
            cacheInfo.setKey(cacheKey);
            cacheInfo.setValue(data);
            cacheInfo.setTimeOut(System.currentTimeMillis()+ (60 - c.get(Calendar.MINUTE))*60*1000);
            CacheManager.putCache(cacheKey,cacheInfo);
        }
        return new ResultEntityList<>(200, data, "获取成功");

    }


    @ApiOperation(value = "获取五大门户头条负面率", httpMethod = "GET")
    @GetMapping("/getGateWay")
    public ResultEntityList<NegativeRate> getGateWay(@RequestParam int categoryId) {
//        HttpSession session = request.getSession();
//        Integer userId = Integer.getInteger(session.getAttribute("userId").toString());
//        Integer companyId = CurrentLoginUserManager.currentLoginUser.get(userId).getCompanyId();
        List<NegativeRate> data = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int i = c.get(Calendar.HOUR_OF_DAY);
        String cacheKey = "negativeRate_getGateWay_"+i;
        CacheEntity cacheInfo = CacheManager.getCacheInfo(cacheKey);
        if(cacheInfo !=null &&  !cacheInfo.isExpired())
        {
            data = ( List<NegativeRate> )cacheInfo.getValue();
        }else {
            LambdaQueryWrapper<NegativeFive> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(NegativeFive::getCategoryId, categoryId);
            List<NegativeFive> listData = iNegativeFiveService.list(wrapper);
            Map<Integer, NegativeFive> negativeAllMap = listData.stream().collect(Collectors.toMap(NegativeFive::getTime, n -> n));
            int j = i;
            int totalCount = 0;
            for (; i >= 0; i--) {
                NegativeRate negativeRate = new NegativeRate();
                negativeRate.setKey(i + "时");
                if(negativeAllMap.containsKey(i))
                {
                    negativeRate.setValue(negativeAllMap.get(i).getState());
                }else
                {
                    negativeRate.setValue(randomState());
                }
                data.add(negativeRate);
                totalCount++;
            }
            if (totalCount < 24) {
                for (int m = 1; m < 24 - j; m++) {
                    NegativeRate negativeRate = new NegativeRate();
                    Integer key = (24 - m);
                    negativeRate.setKey(key + "时");
                    if(negativeAllMap.containsKey(key))
                    {
                        negativeRate.setValue(negativeAllMap.get(key).getState());
                    }else
                    {
                        negativeRate.setValue(randomState());
                    }

                    data.add(negativeRate);
                    totalCount++;
                }
            }
            Collections.reverse(data);
            cacheInfo = new CacheEntity();
            cacheInfo.setKey(cacheKey);
            cacheInfo.setValue(data);
            cacheInfo.setTimeOut(System.currentTimeMillis()+ (60 - c.get(Calendar.MINUTE))*60*1000);
            CacheManager.putCache(cacheKey,cacheInfo);
        }
        return new ResultEntityList<>(200, data, "获取成功");
    }

    @ApiOperation(value = "获取五大门户头条负面率(折线图)", httpMethod = "GET")
    @GetMapping("/getGateWayRate")
    public ResultEntity<NegativeRateVo> getGateWayRate(@RequestParam int categoryId) {
//        HttpSession session = request.getSession();
//        Integer userId = Integer.getInteger(session.getAttribute("userId").toString());
//        Integer companyId = CurrentLoginUserManager.currentLoginUser.get(userId).getCompanyId();
        NegativeRateVo res = new NegativeRateVo();
        Calendar c = Calendar.getInstance();
        int i = c.get(Calendar.HOUR_OF_DAY);
        String cacheKey = "negativeRate_getGateWayRate_"+i;
        CacheEntity cacheInfo = CacheManager.getCacheInfo(cacheKey);
        if(cacheInfo !=null &&  !cacheInfo.isExpired())
        {
            res = (NegativeRateVo )cacheInfo.getValue();
        }else {
            List<NegativeRate> data = getNegativeRates(categoryId, NegativeChartPlantFormEnum.凤凰网.getCode());
            res.setTimeData(data.stream().map(item -> item.getKey()).collect(Collectors.toList()));
           //凤凰网
            res.setFhwData(data.stream().map(item -> item.getValue()).collect(Collectors.toList()));
          //搜狐
            data = getNegativeRates(categoryId,NegativeChartPlantFormEnum.搜狐.getCode());
            res.setShData(data.stream().map(item -> item.getValue()).collect(Collectors.toList()));
            //头条
            data = getNegativeRates(categoryId,NegativeChartPlantFormEnum.头条.getCode());
            res.setTtData(data.stream().map(item -> item.getValue()).collect(Collectors.toList()));
            //腾讯
            data = getNegativeRates(categoryId,NegativeChartPlantFormEnum.腾讯.getCode());
            res.setTxData(data.stream().map(item -> item.getValue()).collect(Collectors.toList()));
            //网易
            data = getNegativeRates(categoryId,NegativeChartPlantFormEnum.网易.getCode());
            res.setWyData(data.stream().map(item -> item.getValue()).collect(Collectors.toList()));
            //新浪
            data = getNegativeRates(categoryId,NegativeChartPlantFormEnum.新浪.getCode());
            res.setXlData(data.stream().map(item -> item.getValue()).collect(Collectors.toList()));


            cacheInfo = new CacheEntity();
            cacheInfo.setKey(cacheKey);
            cacheInfo.setValue(res);
            cacheInfo.setTimeOut(System.currentTimeMillis()+ (60 - c.get(Calendar.MINUTE))*60*1000);
            CacheManager.putCache(cacheKey,cacheInfo);
        }

        return new ResultEntity<>(200, res, "获取成功");
    }

    private List<NegativeRate> getNegativeRates( int categoryId,int plantId) {
        List<NegativeRate> data = new ArrayList<>();
        LambdaQueryWrapper<NegativeChart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NegativeChart::getCategoryId, categoryId);
        wrapper.eq(NegativeChart::getMonitoringPlantformId, plantId);
        List<NegativeChart> listData = iNegativeChartService.list(wrapper);
        Map<Integer, NegativeChart> negativeAllMap = listData.stream().collect(Collectors.toMap(NegativeChart::getTime, n -> n));
        Calendar c = Calendar.getInstance();
        int i = c.get(Calendar.HOUR_OF_DAY);
        int j = i;
        int totalCount = 0;
        Integer interval = 2;
        Integer randomValue = 0; // random.nextInt(10);
        for (; i >= 0; i--) {
            NegativeRate negativeRate = new NegativeRate();
            negativeRate.setKey(i + "时");
            //negativeRate.setValue(randomValue);
            if(negativeAllMap.containsKey(i))
            {
                negativeRate.setValue(negativeAllMap.get(i).getRate());
            }else
            {
                negativeRate.setValue(randomValue);
            }
            data.add(negativeRate);
            totalCount++;
            interval--;
            if(interval<=0)
            {
                interval = 2;
                randomValue =  0; //  random.nextInt(10);
            }
        }
        if (totalCount < 24) {
            for (int m = 1; m < 24 - j; m++) {
                NegativeRate negativeRate = new NegativeRate();
                Integer key = (24 - m);
                negativeRate.setKey(key + "时");
                if(negativeAllMap.containsKey(key))
                {
                    negativeRate.setValue(negativeAllMap.get(key).getRate());
                }else
                {
                    negativeRate.setValue(randomValue);
                }
                data.add(negativeRate);
                totalCount++;
                interval--;
                if(interval<=0)
                {
                    interval = 2;
                    randomValue = 0; //   random.nextInt(10);
                }
            }
        }
        Collections.reverse(data);
        return data;
    }


    private Integer randomState() {
       // return random.nextInt(2);
        return  1;
    }
}
