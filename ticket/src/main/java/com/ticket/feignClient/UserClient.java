package com.ticket.feignClient;


import com.ticket.common.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name ="user-service", url = "${application.config.users-url}")
public interface UserClient {

        @GetMapping("{id}")
        UserResponseDto findUserById(@PathVariable("id") Long id);
    }

