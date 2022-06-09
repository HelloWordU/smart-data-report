package com.rz.smartDataReport;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MybatisCodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator root = FastAutoGenerator.create("jdbc:mysql://192.168.1.207:3306/smart_report?useSSL=false&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull",
                "root", "System@123");

        root.globalConfig(builder -> {
                    builder.author("baomidou") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://smart_report"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.baomidou.mybatisplus.samples.generator") // 设置父包名
                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.entity, "D://smart_report")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user","project_category","project","company","category_screen_config","category_maintenance","category_industry_hot_word","category_hot_word","category_article") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_","crm_"); // 设置过滤表前缀
                    builder.entityBuilder().enableLombok();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
