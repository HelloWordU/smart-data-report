package com.rz.smartDataReport.controller;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.common.ResultEntityList;
import com.rz.smartDataReport.entity.CategoryArticle;
import com.rz.smartDataReport.entity.CategoryHotWord;
import com.rz.smartDataReport.entity.ProjectCategory;
import com.rz.smartDataReport.pojo.vo.CategoryArticleVo;
import com.rz.smartDataReport.pojo.vo.CategoryHotWordVo;
import com.rz.smartDataReport.service.ICategoryArticleService;
import com.rz.smartDataReport.service.IProjectCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categoryArticle")
@Slf4j
@Api(value = "关键词文章")
public class CategoryArticleController {


    @Resource
    private IProjectCategoryService iProjectCategoryService;

    @Resource
    private ICategoryArticleService iCategoryArticleService;

    @Resource
    private HttpServletRequest request;

//
//    @GetMapping("/getProjectCategory")
//    public ResultEntityList<ProjectCategory> getProjectCategory(@RequestParam int projectId) {
//        LambdaQueryWrapper<ProjectCategory> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(ProjectCategory::getProjectId, projectId);
//        List<ProjectCategory> data = iProjectCategoryService.list(wrapper);
//        return new ResultEntityList<>(200, data, "获取成功");
//    }


    @GetMapping("/get")
    @ApiOperation(value = "通过关键词获取关键词文章")
    public ResultEntityList<CategoryArticleVo> getCategoryArticleList(@RequestParam int categoryId) {
        LambdaQueryWrapper<CategoryArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryArticle::getCategoryId, categoryId);
        List<CategoryArticle> listData = iCategoryArticleService.list(wrapper);
        List<CategoryArticleVo> res = new ArrayList<>();
        if (listData != null) {
            for (CategoryArticle item : listData) {
                CategoryArticleVo itemData = new CategoryArticleVo();
                itemData.setTitle(item.getTitle());
                itemData.setAuthor(item.getSheet());
                itemData.setInteractionNum(item.getCommentCount());
                itemData.setSource(item.getMedia());
                itemData.setReadNum(item.getReadCount());
                itemData.setUrl(item.getUrl());
                res.add(itemData);
            }
        }
        return new ResultEntityList(200, res, "获取成功");
    }

    @GetMapping("/admin/get")
    @ApiOperation(value = "通过关键词获取关键词文章-后台使用")
    public ResultEntityList<CategoryArticleVo> getCategoryHotWordListAdmin(@RequestParam int categoryId) {
        LambdaQueryWrapper<CategoryArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryArticle::getCategoryId, categoryId);
        List<CategoryArticle> listData = iCategoryArticleService.list(wrapper);
        return new ResultEntityList(200, listData, "获取成功");
    }



    @PostMapping("/save")
    @ApiOperation(value = "保存关键词文章")
    public ResultEntity<Boolean> save(@RequestBody CategoryArticle entity) {
        iCategoryArticleService.saveOrUpdate(entity);
        return new ResultEntity<>(200, true, "保存");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除关键词文章")
    public ResultEntity<Boolean> delete(@RequestParam Integer id) {
        iCategoryArticleService.removeById(id);
        return new ResultEntity<>(200, true, "保存");
    }


    @PostMapping("/upload")
    @ApiOperation(value = "上传关键词文章")
    public ResultEntity<Boolean> upload(@RequestParam Integer categoryId, @RequestParam MultipartFile uploadFile) {
        if (categoryId == null || categoryId < 1) {
            return new ResultEntity<>(0, true, "无效的关键词");
        }
        if (uploadFile.isEmpty())
            return new ResultEntity<>(0, true, "无效的文件");
        if (!FileNameUtil.extName(uploadFile.getOriginalFilename()).equals("xlsx") && !FileNameUtil.extName(uploadFile.getOriginalFilename()).equals("xls")) {
            return new ResultEntity<>(0, true, "无效的文件格式，只支持xlsx、xls");
        }

        try {
            ExcelReader reader = ExcelUtil.getReader(uploadFile.getInputStream());
            List<CategoryArticle> list = new ArrayList<>();
            List<Map<String, Object>> maps = reader.readAll();
            for (Map<String, Object> item : maps) {
                CategoryArticle newCategoryArticle = new CategoryArticle();
                newCategoryArticle.setCategoryId(categoryId);
                Integer commentCount = 0 ;
                try{
                    commentCount = Integer.parseInt(item.get("评论数") == null ? "0" : item.get("评论数").toString());
                }catch(Exception e) {}
                Integer readCount = 0 ;
                try{
                    readCount = Integer.parseInt(item.get("阅读数") == null ? "0" : item.get("阅读数").toString());
                }catch(Exception e) {}
                newCategoryArticle.setCommentCount(commentCount);
                newCategoryArticle.setMedia(item.get("发布媒体") == null ? "" : item.get("发布媒体").toString());
                newCategoryArticle.setReadCount(readCount);
                newCategoryArticle.setSheet(item.get("版面、作者") == null ? "" : item.get("版面、作者").toString());
                newCategoryArticle.setTitle(item.get("标题") == null ? "" : item.get("标题").toString());
                newCategoryArticle.setUrl(item.get("网址") == null ? "" : item.get("网址").toString());
                list.add(newCategoryArticle);
            }
            if (list.size() > 0) {
                LambdaQueryWrapper<CategoryArticle> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(CategoryArticle::getCategoryId, categoryId);
                iCategoryArticleService.remove(wrapper);
                iCategoryArticleService.saveBatch(list);
            }
        } catch (IOException e) {
            log.error("读取文件失败", e);
        }
        return new ResultEntity<>(200, true, "上传成功");
    }


}
