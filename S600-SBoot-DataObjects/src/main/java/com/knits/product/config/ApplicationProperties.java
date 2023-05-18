package com.knits.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Product.
 * <p>
 * Properties are configured in the {@code application-ms.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Data
public class ApplicationProperties {

    private Boolean initData;
}
