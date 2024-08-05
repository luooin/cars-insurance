package com.luyifan.cars.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luyifan.cars.model.dto.InfoParm;
import com.luyifan.cars.model.entity.CarsInfo;
import com.luyifan.cars.model.entity.ExportMaterial;
import com.luyifan.cars.service.CarsInfoService;
import com.luyifan.cars.utils.HttpImgUtils;
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
import java.util.List;

/**
 * @author lyf
 * @version 1.0
 * @date 2023/7/5 22:37
 */
@RestController
@RequestMapping("/api/carsInfo")
public class CarsInfoController {
    @Autowired
    private CarsInfoService carsInfoService;

    //新增
    @PostMapping
    public ResultVo add(@RequestBody CarsInfo carsInfo){
        if(carsInfoService.save(carsInfo)){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody CarsInfo materialInfo){
        if(carsInfoService.updateById(materialInfo)){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{infoId}")
    public ResultVo delete(@PathVariable("infoId") Long infoId){
        if(carsInfoService.removeById(infoId)){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }


    //列表
    @GetMapping("/list")
    public ResultVo list(InfoParm parm){
        IPage<CarsInfo> list = carsInfoService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }

    //列表
    @GetMapping("/selectList")
    public ResultVo selectList(InfoParm parm){
        //构造查询条件
        IPage<CarsInfo> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<CarsInfo> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getCarName())){
            query.lambda().like(CarsInfo::getCarName,parm.getCarName());
        }
        if(parm.getCategoryId() != null){
            query.lambda().eq(CarsInfo::getCategoryId,parm.getCategoryId());
        }
        IPage<CarsInfo> list = carsInfoService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    @RequestMapping("/exporMaterial")
    public void exporMaterial(HttpServletResponse response) throws Exception {
        List<ExportMaterial> exportList = new ArrayList<>();
        List<CarsInfo> list = carsInfoService.list();
        //组装数据
        for (int i = 0; i < list.size(); i++) {
            ExportMaterial vo = new ExportMaterial();
            BeanUtils.copyProperties(list.get(i), vo);
            if(StringUtils.isNotEmpty(list.get(i).getImage())){
                vo.setImgFile(HttpImgUtils.getNetImgByUrl(list.get(i).getImage()));
            }
            exportList.add(vo);
        }
        //导出
        String fileName = "车辆信息.xlsx";
        ExportParams exportParams = new ExportParams();
        exportParams.setType(ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExportMaterial.class, exportList);
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
