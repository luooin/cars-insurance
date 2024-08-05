package com.luyifan.cars.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.luyifan.cars.model.dto.LoginParm;
import com.luyifan.cars.model.entity.SysMenu;
import com.luyifan.cars.model.entity.SysUser;
import com.luyifan.cars.model.vo.login.LoginResult;
import com.luyifan.cars.model.vo.login.UserInfo;
import com.luyifan.cars.model.vo.role.RouterVO;
import com.luyifan.cars.service.SysMenuService;
import com.luyifan.cars.service.SysUserService;
import com.luyifan.cars.utils.JwtUtils;
import com.luyifan.cars.utils.MakeMenuTree;
import com.luyifan.utils.ResultUtils;
import com.luyifan.utils.ResultVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private SysUserService sysUserService;
    @PostMapping("/image")
    public ResultVo imageCode(HttpServletRequest request) throws IOException {
        //获取验证码字符
        String text = defaultKaptcha.createText();
        //存储验证码到session
        HttpSession session = request.getSession();
        session.setAttribute("code", text);
        //生成图片
        BufferedImage bufferedImage = defaultKaptcha.createImage(text);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(outputStream.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
            ResultVo result = new ResultVo("生成成功", 200, captchaBase64);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @PostMapping("/login")
    public ResultVo login(HttpServletRequest request, @RequestBody LoginParm parm){
        //获取前端传递的验证码
        String code = parm.getCode();
        //从session里面获取验证码
        HttpSession session = request.getSession();
        String scode =  (String)session.getAttribute("code");
        //判断验证码是否正确
        if(StringUtils.isEmpty(scode)){
            return ResultUtils.error("验证码过期!");
        }
        if(!scode.equals(code)){
            return ResultUtils.error("验证码错误!");
        }
        //查询用户信息
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername,parm.getUsername()).eq(SysUser::getPassword,parm.getPassword());
        SysUser one = sysUserService.getOne(query);
        if(one == null){
            return ResultUtils.error("用户名或密码错误!");
        }
        //生成token
        Map<String,String> map = new HashMap<>();
        map.put("userId",one.getUserId().toString());
        String token = jwtUtils.generateToken(map);
        //返回给前端
        LoginResult result = new LoginResult();
        result.setUserId(one.getUserId());
        result.setToken(token);
        return ResultUtils.success("登录成功",result);
    }

    //获取用户信息
    @GetMapping("/getInfo")
    public ResultVo getInfo(Long userId){
        //获取用户信息
        SysUser user = sysUserService.getById(userId);
        //获取用户的权限信息
        List<SysMenu> sysMenuList = null;
        //判断是否是超级管理员
        if(StringUtils.isNotEmpty(user.getIsAdmin()) && user.getIsAdmin().equals("1")){
            sysMenuList = sysMenuService.list();
        }else{
            sysMenuList = sysMenuService.getMenuByUserId(userId);
        }
        //过滤出code字段
        List<String> collect = Optional.ofNullable(sysMenuList).orElse(new ArrayList<>())
                .stream().map(item -> item.getCode())
                .filter(item -> item != null)
                .collect(Collectors.toList());
        //转换为数组
        String[] strings = collect.toArray(new String[collect.size()]);
        //设置返回值
        UserInfo userInfo = new UserInfo();
        userInfo.setName(user.getNickName());
        userInfo.setUserId(userId);
        userInfo.setPermissons(strings);
        return ResultUtils.success("查询成功",userInfo);
    }

    //获取用户信息
    @GetMapping("/getMenuList")
    public ResultVo getMenuList(Long userId){
        //获取用户信息
        SysUser user = sysUserService.getById(userId);
        //获取用户的权限信息
        List<SysMenu> sysMenuList = null;
        //判断是否是超级管理员
        if(StringUtils.isNotEmpty(user.getIsAdmin()) && user.getIsAdmin().equals("1")){
            sysMenuList = sysMenuService.list();
        }else{
            sysMenuList = sysMenuService.getMenuByUserId(userId);
        }
        //菜单数据，查询类型为 0和1的数据
        List<SysMenu> menuList = Optional.ofNullable(sysMenuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
        //组装菜单数据类型
        List<RouterVO> rourer = MakeMenuTree.makeRourer(menuList, 0L);
        return ResultUtils.success("查询成功",rourer);
    }
}