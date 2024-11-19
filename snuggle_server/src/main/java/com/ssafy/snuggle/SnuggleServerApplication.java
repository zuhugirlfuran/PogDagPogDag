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
@MapperScan(basePackages = "com.ssafy.cafe.model.dao")
public class SnuggleServerApplication {


	public static void main(String[] args) {
		SpringApplication.run(SnuggleServerApplication.class, args);
	}
	
   @Bean
    public OpenAPI postsApi() {
        Info info = new Info()
               .title("폭닥폭닥 Rest API")
               .description("<h3>폭닥폭닥에서 제공되는 Rest api의 문서 제공</h3><br>"
               		+ "<img src=\"/imgs/ssafy_logo.png\" width=\"200\">")
               .contact(new Contact().name("ssafy").email("ssafy@ssafy.com"))
               .license(new License().name("SSAFY License").url("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp"))
               .version("1.0");
        
        return new OpenAPI()
                .info(info);
    }

}
