package in.nishu.tradex.portfolio_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.http.HttpHeaders;

@Configuration
public class FeignAuthConfig {
   @Bean
    public RequestInterceptor authRequestIntereptor(){
       return requestTemplate->{
           ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

           if(attributes==null){
               return;
           }
           HttpServletRequest request=attributes.getRequest();
           String authorization=request.getHeader(HttpHeaders.AUTHORIZATION);

           if(authorization!=null&& !authorization.isBlank()){
               requestTemplate.header(HttpHeaders.AUTHORIZATION,authorization);
           }
       };
   }
}


