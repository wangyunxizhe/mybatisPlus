package com.yuan.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MybatisPlusEvoConfig {

    /**
     * 作用：逻辑删除
     * 注意：3.1.2以上的版本已改为默认配置，就不需要在这里显示注册了
     */
    @Bean
    public ISqlInjector mySqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * 乐观锁配置
     * 配合实体上的@Version一起使用
     */
    @Bean
    public OptimisticLockerInterceptor happyLock() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 性能分析配置：开启后MP执行的每条sql都会给出执行时间
     */
    @Bean
    //@Profile({"dev","test"})//该注解可以标识性能分析在什么环境中才被激活
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor pi = new PerformanceInterceptor();
        //如果sql执行时间超过指定时间，就让该sql报错（方便开发时调试）
        //pi.setMaxTime(5L);
        return pi;
    }

    /**
     * 一：多租户配置，要依赖于PaginationInterceptor该分页插件
     * 作用：执行的任意一条增/删/改/查的sql都会已对应的方式添加在这条sql中
     * 注意：也有更简单的用法，使用注解，
     * <p>
     * 二：动态表名配置
     * 使用场景：分库分表时使用
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor pi = new PaginationInterceptor();
        ArrayList<ISqlParser> sqlParsers = new ArrayList<>();
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        //多租户配置：为测试方便，不单独写实现类了
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId() {
                //测试用，就直接写死了
                return new LongValue(1088248166370832385L);
            }

            @Override
            public String getTenantIdColumn() {
                return "manager_id";//表字段名
            }

            @Override
            public boolean doTableFilter(String tableName) {
                //为不影响所有测试方法的正常效果，这里过滤掉该工程中使用到的表，如果要看效果，注释掉if语句
                if ("t_mp_user_evo".equals(tableName) || "t_mp_user".equals(tableName)) {
                    return true;
                }
                return false;
            }
        });

        //动态表名配置
        DynamicTableNameParser dtnp = new DynamicTableNameParser();
        Map<String, ITableNameHandler> map = new HashMap<>();
        map.put("t_mp_user_evo", new ITableNameHandler() {
            @Override
            public String dynamicTableName(MetaObject metaObject, String s, String s1) {
                //return需要替换的表名，可以用习惯的方式将要替换的表名传入，t_mp_user_evo将替换成传入的表名
                //注意：如果返回为空，则不会替换
                return null;
            }
        });
        dtnp.setTableNameHandlerMap(map);

        sqlParsers.add(dtnp);
        sqlParsers.add(tenantSqlParser);
        pi.setSqlParserList(sqlParsers);
        //多租户配置：上面的TenantSqlParser是表级别的过滤，下面的实现类可以更细粒度的过滤
        pi.setSqlParserFilter(new ISqlParserFilter() {
            @Override
            public boolean doFilter(MetaObject metaObject) {
                MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
                //过滤掉指定方法，同时上面的动态表名也会失效
                if ("com.yuan.dao.MPUserEvoMapper.mySelectList".equals(ms.getId())) {
                    return true;
                }
                return false;
            }
        });
        return pi;
    }

}
