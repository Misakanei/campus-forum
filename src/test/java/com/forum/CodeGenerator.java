package com.forum;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/campus-forum", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("CBL") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .outputDir("D:\\Development\\Code\\Java\\campus-forum\\src\\main\\java"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.forum") // 设置父包名
//                                .moduleName("user") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\Development\\Code\\Java\\campus-forum\\src\\main\\resources\\mapper\\user")) // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                        builder.addInclude("users", "posts", "comments", "categories") // 设置需要生成的表名
                                .addTablePrefix() // 设置过滤表前缀
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
