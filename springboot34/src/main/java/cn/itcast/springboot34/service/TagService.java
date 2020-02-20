package cn.itcast.springboot34.service;

import cn.itcast.springboot34.bean.dto.ModelDto;
import cn.itcast.springboot34.bean.dto.TagDto;
import cn.itcast.springboot34.bean.dto.TagModel;

import java.util.List;

public interface TagService {
    void saveAll(List<TagDto> list);

    List<TagDto> findTagsByPid(Long pid);

    void saveTagModel(TagDto tag, ModelDto model);

    List<TagModel> findAllTagModel(Long pid);

    void saveTag(TagDto dto);
}
