package com.luyifan.cars.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luyifan.cars.model.dto.UpPasswordParm;
import com.luyifan.cars.model.dto.UserPageParm;
import com.luyifan.cars.model.entity.ExportUser;
import com.luyifan.cars.model.entity.SysUser;
import com.luyifan.cars.model.entity.SysUserRole;
import com.luyifan.cars.service.SysUserRoleService;
import com.luyifan.cars.service.SysUserService;
import com.luyifan.utils.ResultUtils;
import com.luyifan.utils.ResultVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;
    //新增
    @PostMapping
    public ResultVo add(@RequestBody SysUser sysUser){
        // 判断账户是否被占用
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getUsername,sysUser.getUsername());
        SysUser one = sysUserService.getOne(queryWrapper);
        if (one != null) {
            return ResultUtils.error("账户被占用");
        }
        sysUser.setCreateTime(new Date());
        sysUserService.addUser(sysUser);
        return ResultUtils.success("新增成功!");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody SysUser sysUser){
        // 判断账户是否被占用
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getUsername,sysUser.getUsername());
        SysUser one = sysUserService.getOne(queryWrapper);
        if (one != null && !one.getUserId().equals(sysUser.getUserId())) {
            return ResultUtils.error("账户被占用");
        }
        sysUserService.editUser(sysUser);
        return ResultUtils.success("编辑成功!");
    }

    //删除
    @DeleteMapping("/{userId}")
    public ResultVo delete(@PathVariable("userId") Long userId){
        sysUserService.deleteUser(userId);
        return ResultUtils.success("删除成功!");
    }

    //列表
    @GetMapping("/list")
    public ResultVo list(UserPageParm parm){
        //构造分页对象
        IPage<SysUser> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getNickName())){
            query.lambda().like(SysUser::getNickName,parm.getNickName());
        }
        if(StringUtils.isNotEmpty(parm.getPhone())){
            query.lambda().like(SysUser::getPhone,parm.getPhone());
        }
        query.lambda().orderByDesc(SysUser::getCreateTime);
        IPage<SysUser> list = sysUserService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
    // 根据用户id查询角色id
    @GetMapping("/getRoleByUserId")
    public ResultVo getRoleByUserIdApi(Long userId) {
        // 构造查询条件
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserRole::getUserId,userId);
        SysUserRole one = sysUserRoleService.getOne(queryWrapper);
        return ResultUtils.success("查询成功",one   );
    }

    //重置密码
    @PostMapping("/resetPassword")
    public ResultVo upPassword(@RequestBody SysUser sysUser){
       if (sysUserService.updateById(sysUser)) {
           return ResultUtils.success("重置成功");
       }else {
           return ResultUtils.success("重置失败");
       }

    }

    //修改密码
    @PostMapping("/upPassword")
    public ResultVo upPassword(@RequestBody UpPasswordParm parm){
        //判断原密码是否正确
        SysUser user = sysUserService.getById(parm.getUserId());
        if(!parm.getOldPassword().equals(user.getPassword())){
            return ResultUtils.error("原密码错误!");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserId(parm.getUserId());
        sysUser.setPassword(parm.getPassword());
        if(sysUserService.updateById(sysUser)){
            return ResultUtils.success("修改成功!");
        }
        return ResultUtils.error("修改失败!");
    }

    @RequestMapping("/exportUser")
    public void exportUser(HttpServletResponse response) throws Exception {
        List<ExportUser> exportList = new ArrayList<>();
        List<SysUser> list = sysUserService.list();
        //组装数据
        for (int i = 0; i < list.size(); i++) {
            ExportUser vo = new ExportUser();
            BeanUtils.copyProperties(list.get(i), vo);
            exportList.add(vo);
        }
        //导出
        String fileName = "用户信息.xlsx";
        ExportParams exportParams = new ExportParams();
        exportParams.setType(ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExportUser.class, exportList);
        downloadExcel(fileName, workbook, response);

    }

    public static void downloadExcel(String fileName, Workbook workbook, HttpServletResponse response) throws Exception {
        try {
            if (StringUtils.isEmpty(fileName)) {
                throw new RuntimeException("导出文件名不能为空");
            }
            String encodeFileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName);
            response.setHeader("FileName", encodeFileName);
            response.setHeader("Access-Control-Expose-Headers", "FileName");
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            workbook.close();
        }
    }
}