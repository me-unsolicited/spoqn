package com.spoqn.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.JndiDataSourceProvider;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.spoqn.server.data.mappers.MapperHelper;
import com.squarespace.jersey2.guice.JerseyGuiceModule;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

public class SpoqnContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {

        List<Module> modules = new ArrayList<>();
        modules.add(new JerseyGuiceModule("__HK2_Generated_0"));
        modules.add(new ServletModule());
        modules.add(new MyBatisModule() {

            @Override
            protected void initialize() {
                environmentId("development");
                bindDataSourceProvider(new JndiDataSourceProvider("java:comp/env/dataSource"));
                bindTransactionFactoryType(JdbcTransactionFactory.class);
                addMapperClasses(MapperHelper.pkg());
            }
        });

        Injector injector = Guice.createInjector(modules);
        JerseyGuiceUtils.install(injector);

        return injector;
    }
}
