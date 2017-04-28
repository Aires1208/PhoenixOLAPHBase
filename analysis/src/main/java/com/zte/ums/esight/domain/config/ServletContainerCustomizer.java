package com.zte.ums.esight.domain.config;

import org.apache.catalina.webresources.StandardRoot;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServletContainerCustomizer implements EmbeddedServletContainerCustomizer {

    private static final Log log = LogFactory.getLog(ServletContainerCustomizer.class);

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        if (TomcatEmbeddedServletContainerFactory.class.isAssignableFrom(container.getClass())) {

            int cacheSize = 1024 * 1024;
            log.info("Customizing tomcat factory. New cache size (KB) is " + cacheSize);

            TomcatEmbeddedServletContainerFactory tomcatFactory = (TomcatEmbeddedServletContainerFactory) container;
            tomcatFactory.addContextCustomizers((context) -> {
                StandardRoot standardRoot = new StandardRoot(context);
                standardRoot.setCacheMaxSize(cacheSize);
                context.setResources(standardRoot);
            });

        }
    }

}
