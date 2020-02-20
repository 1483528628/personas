package cn.itcast.springboot34.controller;

import cn.itcast.springboot34.bean.HttpResult;
import cn.itcast.springboot34.bean.dto.ModelDto;
import cn.itcast.springboot34.bean.dto.TagDto;
import cn.itcast.springboot34.bean.dto.TagModel;
import cn.itcast.springboot34.service.TagService;
import cn.itcast.springboot34.util.Codes;
import cn.itcast.up.common.HDFSUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    //http://localhost:8081/tags/relation
//    请求方法:PUT
    @PutMapping("tags/relation")
    public void addTags(@RequestBody List<TagDto> list) {
        for (TagDto tagDto : list) {
            System.out.println(tagDto);
        }
        tagService.saveAll(list);
    }

    //    请求网址:http://localhost:8081/tags?pid=-1
//    请求方法:GET
    @GetMapping("tags")
    public HttpResult<List<TagDto>> findTags(Long pid) {
        List<TagDto> list = tagService.findTagsByPid(pid);
        return new HttpResult<List<TagDto>>(Codes.SUCCESS,"成功",list);
    }

    //    请求网址:http://localhost:8081/tags/model
//    请求方法:PUT
    /*{
        "tag": {
        "name": "性别",
                "business": "统计",
                "industry": "",
                "rule": "type=hive",
                "level": 4,
                "parentId3": 595,
                "parentId2": 596,
                "pid": 597
    },
        "model": {
        "name": "hello",
                "path": "",
                "mainClass": "cn.itcast",
                "args": "memory",
                "schedule": {
            "dateRange": ["2020-02-25 00:00", "2020-03-25 00:00"],
            "startTime": "2020-02-25 00:00",
                    "endTime": "2020-03-25 00:00",
                    "frequency": "1"
        },
        "state": 4
    }
    }*/
    @PutMapping("tags/model")
    public void saveTagModel(@RequestBody TagModel tagModel) {
        tagService.saveTagModel(tagModel.getTag(), tagModel.getModel());
    }

    //    请求网址:http://localhost:8081/tags/model?pid=597
//    请求方法:GET
//    pid	597
    @GetMapping("tags/model")
    public HttpResult getTagModel(Long pid) {
        List<TagModel> list =tagService.findAllTagModel(pid);
        HttpResult<List<TagModel>> result = new HttpResult<>(Codes.SUCCESS, "成功", list);
        return result;
    }

    @PutMapping("tags/data")
    public HttpResult putFiveTag(@RequestBody TagDto dto) {
        tagService.saveTag(dto);
        return new HttpResult(Codes.SUCCESS, "成功", dto);
    }

    @PostMapping("tags/upload")
    public HttpResult uploadFile(MultipartFile file) throws IOException {
        String type = file.getContentType();
        String name = file.getName();
//        上传文件的名字
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        InputStream inputStream = file.getInputStream();
        IOUtils.copy(inputStream, new FileOutputStream(new File("a.jar")));
        String fileName = UUID.randomUUID().toString() + ".jar";
        String path = "/tmp/jars/" + fileName;

        HDFSUtils.getInstance().copyFromFile("a.jar", path);
        HttpResult success = new HttpResult(Codes.SUCCESS, "success", "hdfs://bd001:8020" + path);
        return success;
    }

}
