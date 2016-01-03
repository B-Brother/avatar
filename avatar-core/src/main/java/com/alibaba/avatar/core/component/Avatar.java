package com.alibaba.avatar.core.component;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Avatar {

	@Autowired
	private AvatarKeeper avatarKeeper;
	
	@RequestMapping("/")
    String home() { 
        return "Avatar Works!";
    }

    public static void main(String[] args) throws Exception {  
        SpringApplication.run(Avatar.class, args);
    }
}
