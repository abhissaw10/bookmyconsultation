package com.bmc.notificationservice;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@Bean
	public FreeMarkerConfigurer freemarkerClassLoaderConfig() {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
		TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(), "/mail-templates");
		configuration.setTemplateLoader(templateLoader);
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setConfiguration(configuration);
		return freeMarkerConfigurer;
	}


}
