package com.rz.smartDataReport.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.CacheManager;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.entity.CommentNegativeConfig;
import com.rz.smartDataReport.pojo.entity.CacheEntity;
import com.rz.smartDataReport.pojo.vo.CommentNegativeConfigVo;
import com.rz.smartDataReport.pojo.vo.CommentNegativeInfoVo;
import com.rz.smartDataReport.service.ICommentNegativeConfigService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-07-11
 */
@RestController
@RequestMapping("/commentNegativeConfig")
@Api("评论负面红绿灯配置")
@Slf4j
public class CommentNegativeConfigController {
    @Resource
    private ICommentNegativeConfigService iCommentNegativeConfigService;
    Random random = new Random(System.currentTimeMillis());

    @GetMapping("/get")
    public ResultEntity<CommentNegativeConfigVo> getCategoryHotWordList(@RequestParam int categoryId) {
        LambdaQueryWrapper<CommentNegativeConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentNegativeConfig::getCategoryId, categoryId);
        CommentNegativeConfig res = iCommentNegativeConfigService.getOne(wrapper);
        CommentNegativeConfigVo result = new CommentNegativeConfigVo() ;
        BeanUtils.copyProperties(res,result);
        String last2HourDataCacheKey ="CommentNegativeInfoVo_2_"+ DateUtil.format(new Date(),"yyyyMMdd")+result.getLast2HourNormalCount()
                + result.getLast2HourNegetiveCount()+result.getLast2HourNoCommentCount();
        CacheEntity cacheInfo = CacheManager.getCacheInfo(last2HourDataCacheKey);
        List<CommentNegativeInfoVo> last2HourData;
        if(cacheInfo==null)
        {
            last2HourData = getRandomInfoList(result.getLast2HourNormalCount(),
                    result.getLast2HourNegetiveCount(), result.getLast2HourNoCommentCount());
            cacheInfo = new CacheEntity();
            cacheInfo.setKey(last2HourDataCacheKey);
            cacheInfo.setValue(last2HourData);
            cacheInfo.setTimeOut(System.currentTimeMillis()+(12*60*60*1000));
            CacheManager.putCache(last2HourDataCacheKey,cacheInfo);
        }else
        {
            last2HourData = (List<CommentNegativeInfoVo>) cacheInfo.getValue();
        }
        result.setLast2HourData(last2HourData);
        List<CommentNegativeInfoVo> last5HourData;
        String last5HourDataCacheKey ="CommentNegativeInfoVo_5_"+ DateUtil.format(new Date(),"yyyyMMdd")
                +result.getLast5HourNormalCount()
                + result.getLast5HourNegetiveCount()+result.getLast5HourNoCommentCount();
        CacheEntity cacheInfo5 = CacheManager.getCacheInfo(last5HourDataCacheKey);

        if(cacheInfo5==null)
        {
            last5HourData = getRandomInfoList(result.getLast5HourNormalCount(),
                    result.getLast5HourNegetiveCount(), result.getLast5HourNoCommentCount());
            cacheInfo5 = new CacheEntity();
            cacheInfo5.setKey(last5HourDataCacheKey);
            cacheInfo5.setValue(last5HourData);
            cacheInfo5.setTimeOut(System.currentTimeMillis()+(12*60*60*1000));
            CacheManager.putCache(last5HourDataCacheKey,cacheInfo5);

        }else
        {
            last5HourData = (List<CommentNegativeInfoVo>) cacheInfo5.getValue();
        }

        result.setLast5HourData(last5HourData);

        List<CommentNegativeInfoVo> last12HourData;
        String last12HourDataCacheKey ="CommentNegativeInfoVo_12_"+ DateUtil.format(new Date(),"yyyyMMdd")
                +result.getLast12HourNormalCount()
                + result.getLast12HourNegetiveCount()+result.getLast12HourNoCommentCount();
        CacheEntity cacheInfo12 = CacheManager.getCacheInfo(last12HourDataCacheKey);
        if(cacheInfo12==null)
        {
            last12HourData = getRandomInfoList(result.getLast12HourNormalCount(),
                    result.getLast12HourNegetiveCount(), result.getLast12HourNoCommentCount());
            cacheInfo12 = new CacheEntity();
            cacheInfo12.setKey(last12HourDataCacheKey);
            cacheInfo12.setValue(last12HourData);
            cacheInfo12.setTimeOut(System.currentTimeMillis()+(12*60*60*1000));
            CacheManager.putCache(last12HourDataCacheKey,cacheInfo12);

        }else
        {
            last12HourData = (List<CommentNegativeInfoVo>) cacheInfo12.getValue();
        }
        result.setLast12HourData(last12HourData);
        List<CommentNegativeInfoVo> last24HourData;
        String last24HourDataCacheKey ="CommentNegativeInfoVo_24_"+ DateUtil.format(new Date(),"yyyyMMdd")
                +result.getLast24HourNormalCount()
                + result.getLast24HourNegetiveCount()+result.getLast24HourNoCommentCount();
        CacheEntity cacheInfo24 = CacheManager.getCacheInfo(last24HourDataCacheKey);
        if(cacheInfo24==null)
        {
            last24HourData = getRandomInfoList(result.getLast24HourNormalCount(),
                    result.getLast24HourNegetiveCount(), result.getLast24HourNoCommentCount());
            cacheInfo24 = new CacheEntity();
            cacheInfo24.setKey(last24HourDataCacheKey);
            cacheInfo24.setValue(last24HourData);
            cacheInfo24.setTimeOut(System.currentTimeMillis()+(12*60*60*1000));
            CacheManager.putCache(last24HourDataCacheKey,cacheInfo24);

        }else
        {
            last24HourData = (List<CommentNegativeInfoVo>) cacheInfo24.getValue();
        }
        result.setLast24HourData(last24HourData);
        return new ResultEntity(200, result, "获取成功");
    }

    private List<CommentNegativeInfoVo> getRandomInfoList(Integer normalCount,
                                                          Integer negetiveCount,
                                                          Integer noCommentCount)
    {
        List<CommentNegativeInfoVo> res = new ArrayList<>();
        for(Integer i=0;i<normalCount;i++)
        {
            CommentNegativeInfoVo item = new CommentNegativeInfoVo();
            item.setIsNormal(true);
            item.setIsNegetive(false);
            item.setIsNoComment(false);
            res.add(item);
        }
        for(Integer i=0;i<negetiveCount;i++)
        {
            CommentNegativeInfoVo item = new CommentNegativeInfoVo();
            item.setIsNormal(false);
            item.setIsNegetive(true);
            item.setIsNoComment(false);

            res.add(random.nextInt(res.size()-1),item);
        }
        for(Integer i=0;i<noCommentCount;i++)
        {
            CommentNegativeInfoVo item = new CommentNegativeInfoVo();
            item.setIsNormal(false);
            item.setIsNegetive(false);
            item.setIsNoComment(true);

            res.add(random.nextInt(res.size()-1),item);
        }
        return  res;
    }

    @PostMapping("/delete")
    public ResultEntity<Boolean> delete(@RequestParam Integer id) {
        try {
            iCommentNegativeConfigService.removeById(id);
            return new ResultEntity(200, true, "删除成功");
        } catch (Exception e) {
            log.error("CategoryHotWord 删除失败", e);
            return new ResultEntity(0, false, "删除失败");
        }

    }
    @PostMapping("/save")
    public ResultEntity<Boolean> save(@RequestBody CommentNegativeConfig data) {

        if (data.getCategoryId() == null || data.getCategoryId() < 1) {
            return new ResultEntity(0, false, "无效的关键词");
        }
        try {
            iCommentNegativeConfigService.saveOrUpdate(data);
            return new ResultEntity(200, true, "保存成功");
        } catch (Exception e) {
            log.error("CommentNegativeConfig 保存失败", e);
            return new ResultEntity(0, false, "保存失败");
        }

    }
}
