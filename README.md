# Festa Com Papel

Web System made with Spring, Cloudinary, MySQL, Thymeleaf and StarAdmin.

## Live demo:
https://festa-com-papel.herokuapp.com/

```

User: neldeci
Password: 123

```

## Setting it up:
Change the username and password in config/WebSecurityConfig.java
```
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("adm")
                        .password("123")
                        .roles("USER")
                        .build();
```

Add your Cloudinary at util/CloudinaryConfig.java

```
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(""); // Cloudinary ID
    }

```

Add your DataSource at main/resources/application.propperties


```

spring.datasource.url=jdbc:mysql://#dbUrl?reconnect=true
spring.datasource.username=#dbUser
spring.datasource.password=#dbPassword

```

## Authors

* **Francisco Borges Carreiro Filho**
