package com.njzyd.sop.thlsdb;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.njzyd.sop.thlsdb.aspectj.DynamicDataSourceAnnotationAdvisor;
import com.njzyd.sop.thlsdb.aspectj.DynamicDataSourceAnnotationInterceptor;
import com.njzyd.sop.thlsdb.datasource.DynamicDataSourceRegister;
import com.njzyd.sop.thlsdb.utils.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@Import(DynamicDataSourceRegister.class)
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan({"com.njzyd.thlsdb.system.mapper"})
public class OpenDataApiApplication {
	@Bean
	public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
		return new DynamicDataSourceAnnotationAdvisor(new DynamicDataSourceAnnotationInterceptor());
	}
	public static void main(String[] args) {
		SpringApplication.run(OpenDataApiApplication.class, args);
	}
	@Bean
	public SpringUtil springUtil(){return new SpringUtil();}

	//druid数据源状态监控.
	@WebServlet(urlPatterns = "/druid/*",
			initParams = {
					// IP白名单 (没有配置或者为空，则允许所有访问)
					@WebInitParam(name = "allow", value = "192.168.1.72,127.0.0.1"),
					// IP黑名单 (存在共同时，deny优先于allow)
					@WebInitParam(name = "deny", value = "192.168.1.73"),
					// 用户名
					@WebInitParam(name = "loginUsername", value = "admin"),
					// 密码
					@WebInitParam(name = "loginPassword", value = "admin"),
					// 禁用HTML页面上的“Reset All”功能
					@WebInitParam(name = "resetEnable", value = "false")
			}
	)
	public class DruidStatViewServlet extends StatViewServlet {

	}
	// druid过滤器.
	@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
			initParams = {
					// 忽略资源
					@WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")
			}
	)
	public class DruidStatFilter extends WebStatFilter {
	}
}
