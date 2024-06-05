package com.company.motors.users.adapter.controller

import com.company.motors.extensions.CompanionLogger
import com.company.motors.users.adapter.controller.model.UserRequest
import com.company.motors.users.adapter.controller.model.UserResponse
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserControllerAdapter {
    @PostMapping("/v1/public/users")
    fun createUser(@RequestBody user:UserRequest){
        log.info("creando el usuario {}",user)
    }

    @PostMapping("/v1/private/users/{id}")
    @Secured("ROLE_ADMIN")
    fun getUser(@PathVariable id:String,):UserResponse{
        return UserResponse("1","user@user.com")
    }

    companion object : CompanionLogger()
}
