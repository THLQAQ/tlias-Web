package com.yun.controller;

import com.yun.pojo.Dept;
import com.yun.pojo.Result;

import com.yun.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    /*查询部门数据*/
    @GetMapping
    public Result list(){
        log.info("查询全部部门数据");
        List<Dept> deptList =  deptService.list();
        return Result.success(deptList);
    }

    /*删除部门*/
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
         log.info("根据id删除部门:{}",id);
         deptService.delete(id);
         return Result.success();
    }

    /*新增部门*/
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增部门:{}",dept);
        deptService.add(dept);
        return Result.success();
    }

    /*修改部门*/
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据id查询部门:{}",id);
        Dept dept= deptService.getById(id);
        return Result.success(dept);
    }

    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("修改部门:{}",dept);
        deptService.update(dept);
        return Result.success();
    }
}
