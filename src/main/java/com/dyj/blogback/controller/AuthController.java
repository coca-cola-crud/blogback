package com.dyj.blogback.controller;

import com.dyj.blogback.model.LoginDTO;
import com.dyj.blogback.model.User;
import com.dyj.blogback.ov.TokenVO;
import com.dyj.blogback.service.AuthService;
import com.dyj.blogback.util.Result;
import com.dyj.blogback.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录校验
 */
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 登录
     *
     * @param loginDTO
     * @return token登录凭证
     */
    @PostMapping("/Login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        //用户信息
        User user = authService.findByUsername(username);
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(password)) {
            return Result.build(400, "用户名或密码错误");
        } else {
            //生成token，并保存到数据库
            String token = authService.createToken(user);
            TokenVO tokenVO = new TokenVO();
            tokenVO.setToken(token);
            return Result.ok(tokenVO);
        }
    }

    /**
     * 登出
     *
     * @param
     * @return
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        //从request中取出token
        String token = TokenUtil.getRequestToken(request);
        authService.logout(token);
        return Result.ok();
    }

    /**
     * 测试
     *
     * @param
     * @return
     */
    @PostMapping("/test")
    public Result test( ) {

        return Result.ok("恭喜你，验证成功啦，我可以返回数据给你");
    }

}

