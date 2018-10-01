package com.festacompapel.com.festacompapel.util;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary( "cloudinary://192755424847465:rULdh3rVDBB52FZ2EHr9kUj-1fY@hkz3sajjn");
    }
}
