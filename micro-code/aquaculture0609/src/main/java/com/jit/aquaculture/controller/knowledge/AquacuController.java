package com.jit.aquaculture.controller.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Knowledge;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.knowledge.KnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(value = "aquaculture", description = "养殖技术知识库")
@ResponseResult
@RestController
//@EnableEurekaClient
@RequestMapping("/aquacu")
public class AquacuController {


    @Autowired
    private KnowledgeService knowledgeService;

    /**
     * 插入一条百科知识
     *
     * @param image
     * @param name
     * @return
     * @Param content
     */
    @ApiOperation(value = "插入百科知识", notes = "插入一条百科知识")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "知识名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "content", value = "养殖知识内容", required = true, dataType = "String")
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Knowledge insertKnowledge(MultipartFile image, @RequestParam("name") String name, @RequestParam("content") String content) throws IOException {
        return knowledgeService.insertKnowledge(image, name, content);
    }

    /**
     * @param id
     * @param image
     * @param name
     * @param content
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "更新百科知识", notes = "更新一条百科知识")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "百科知识", required = true, dataType = "int"),
            @ApiImplicitParam(name = "name", value = "知识名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "content", value = "养殖知识内容", required = true, dataType = "String")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Knowledge updateKnowledge(@PathVariable Integer id, MultipartFile image, String name, String content) throws IOException {
        return knowledgeService.updateKnowledge(image, name, content, id);
    }

    /**
     * 删除一条百科知识
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除百科知识", notes = "删除百科知识")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "百科知识id", required = true, dataType = "string")
    })
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    public Boolean deleteKnowledge(@PathVariable("ids") String ids) {
        return knowledgeService.deleteKnowledge(ids);
    }

    /**
     * 获取所有百科知识
     *
     * @return
     */
    @ApiOperation(value = "获取所有百科知识", notes = "获取所有百科知识")
    @GetMapping("all")
    public PageVO<Knowledge> getAllKnowledge(PageQO pageQO) {
        return knowledgeService.selectAll(pageQO);
    }

    /**
     * 获取一条百科知识
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取一条百科知识", notes = "获取一条百科知识")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Knowledge getOneKnowledge(@PathVariable Integer id) {
        return knowledgeService.selectKnowledge(id);
    }
}
