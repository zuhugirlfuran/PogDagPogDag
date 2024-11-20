package com.ssafy.snuggle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
@MapperScan(basePackages = "com.ssafy.snuggle.model.dao")
public class SnuggleServerApplication {


	public static void main(String[] args) {
		SpringApplication.run(SnuggleServerApplication.class, args);
	}
	
   @Bean
    public OpenAPI postsApi() {
        Info info = new Info()
               .title("폭닥폭닥 Rest API")
               .description("<h3>폭닥폭닥에서 제공되는 Rest api의 문서 제공</h3><br>" + "<p>귀엽구 멋진 즈후, 미갱, 승디의 프로젝트</p>" 
               		+ "<img src=\"/imgs/logo.png\" width=\"200\">")
               .contact(new Contact().name("ssafy").email("ssafy@ssafy.com"))
               .license(new License().name("SSAFY License").url("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp"))
               .version("1.0");
        
        return new OpenAPI()
                .info(info);
    }

}
