package xyz.dvnlabs.approvalapi.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.dvnlabs.approvalapi.dto.LoginRequest
import xyz.dvnlabs.approvalapi.dto.UserLoginResponse
import xyz.dvnlabs.approvalapi.dto.UserRegister
import xyz.dvnlabs.approvalapi.entity.User
import xyz.dvnlabs.approvalapi.service.UserService

@RequestMapping("/auth")
@RestController
@Api(tags = ["Auth Controller"])
class AuthController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/register")
    @ApiOperation("Register")
    fun register(
        @RequestBody userRegister: UserRegister
    ): User {
        return userService.register(userRegister)
    }


    @PostMapping("/signin")
    @ApiOperation("Sign In")
    fun signin(
        @RequestBody loginRequest: LoginRequest
    ): UserLoginResponse? {
        return userService.signin(loginRequest)
    }
}