package com.tuts.ots.onetimeshop;

import com.tuts.ots.onetimeshop.configs.AppConstants;
import com.tuts.ots.onetimeshop.entities.Role;
import com.tuts.ots.onetimeshop.repositires.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class OnetimeshopApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {

		SpringApplication.run(OnetimeshopApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
	System.out.println(this.passwordEncoder.encode("asadadfff"));
	try {

		Role role=new Role();
		role.setId(AppConstants.ADMIN_USER);
		role.setName("ADMIN_USER");

		Role role1=new Role();
		role1.setId(AppConstants.NORMAL_USER);
		role1.setName("NORMAL_USER");

		List<Role> roles=List.of(role,role1);
		List<Role>result=this.roleRepo.saveAll(roles);

		result.forEach(r->{
			System.out.println(r.getName());
		});



	}catch (Exception e){
		e.printStackTrace();
	}

	}


}
