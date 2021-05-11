package com.gdpu.homework.Controller;

import com.gdpu.homework.Config.DisableToken;
import com.gdpu.homework.Config.OnlyRoot;
import com.gdpu.homework.Entity.Config.Code;
import com.gdpu.homework.Entity.Config.JsonData;
import com.gdpu.homework.Entity.Config.TokenUtils;
import com.gdpu.homework.Entity.User;
import com.gdpu.homework.ServiceImpl.Service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/vue/login")
public class userLoginController {
    private HttpSession session ;
    @Autowired
    UserService userService;
    @DisableToken
    @GetMapping("/code")
    public void getCode(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("image/jpg");
        Code code = new Code();
        BufferedImage bufferedImage= code.getlmage();
        //自定义session容器
        this.session = request.getSession();
        //设置session值
        session.setAttribute("code",code.getText());
        code.output(bufferedImage,response);
    }
    @DisableToken
    @PostMapping("/code")
    @ResponseBody
//    @RequestBody Map map
    public JsonData checkCode(String code){
        String sessionCode = (String) session.getAttribute("code");
        System.out.println(sessionCode);
        System.out.println(code);
        if(code.equals(sessionCode)){
           return JsonData.success(null);
        }
        else {
            return JsonData.error("验证码错误");
        }

    }
    @DisableToken
    @PostMapping("/user")
    @ResponseBody
    public JsonData checkLogin(@RequestBody Map map){
        User user = new User();
        user.setUsername((String) map.get("username"));
        user.setPassword((String)map.get("password"));
        System.out.println(user);

        User currentUser = userService.checkUser(user);
        //失败,返回错误信息
          if(currentUser == null )
            return JsonData.error("账号密码错误");
        else {

            //成功,返回token和username
            String token = TokenUtils.token(currentUser.getUsername(),currentUser.getPassword(),currentUser.getRoot());
            Map hashMap = new HashMap();
            hashMap.put("username",currentUser.getUsername());
            hashMap.put("token",token);
              hashMap.put("root",currentUser.getRoot());
            System.out.println(map);
            return JsonData.success(hashMap);
        }


    }
    @OnlyRoot
    @GetMapping("/users/{currentPage}/{pageSize}")
    @ResponseBody
    public JsonData getStudentsByCollege(@PathVariable("currentPage") int currentPage,@PathVariable("pageSize") int pageSize)
    {

        PageHelper.startPage(currentPage,pageSize);
        List<User>  users = userService.getUsers();
        PageInfo pageInfo = new PageInfo(users);
        HashMap map = new HashMap();
        map.put("users",pageInfo.getList());
        map.put("totalNum",pageInfo.getTotal());

        return JsonData.success(map);


    }
    @GetMapping("/users/{currentPage}/{pageSize}/search/{name}")
    @ResponseBody
    public JsonData getSomeOneStudent(@PathVariable("currentPage") int currentPage,
                                      @PathVariable("pageSize") int pageSize,@PathVariable("name") String name){


        System.out.println("1");
        PageHelper. startPage(currentPage,pageSize);
        List<User>  users = userService.getUsersByUsername(name);
        System.out.println(users);
        PageInfo pageInfo = new PageInfo(users);
        HashMap map = new HashMap();
        map.put("users",pageInfo.getList());
        map.put("totalNum",pageInfo.getTotal());

        return JsonData.success(map);
    }
    @PutMapping("/password/{username}")
    @ResponseBody
    public JsonData updatePassword(@PathVariable("username") String username,@RequestBody Map map) {
        System.out.println(username);
        System.out.println(map.get("password"));
        try {
            userService.updatePassword(username, (String) map.get("password"));
        }catch (Exception e){
            e.printStackTrace();
            return  JsonData.error("更新密码失败");
        }
        return  JsonData.progress("更新密码成功");
    }
    @OnlyRoot
    @DeleteMapping("/user/{username}")
    @ResponseBody
    public JsonData deleteUser(@PathVariable("username") String username){

        try{

            userService.deleteUser(username);
        }catch (Exception e){
            e.printStackTrace();
            return   JsonData.error("用户删除失败");
        }
        return JsonData.progress("用户删除成功");
    }
    @OnlyRoot
    @PutMapping("/root/{username}")
    @ResponseBody
    public JsonData updateRoot(@PathVariable("username") String username,@RequestBody Map map) {


        try {
            userService.updateRoot(username, (int) map.get("root"));
        }catch (Exception e){
            e.printStackTrace();
            return  JsonData.error("更新权限失败");
        }
        return  JsonData.progress("更新权限成功");
    }
    @OnlyRoot
    @PostMapping("/user/")
    @ResponseBody
    public JsonData insertUser(@RequestBody User user){

        System.out.println(user);
        try{
            userService.insertUser(user);
        }catch (Exception e){
            e.printStackTrace();
          return   JsonData.error("该用户已存在");
        }
        return  JsonData.progress("添加用户成功");
    }
}
