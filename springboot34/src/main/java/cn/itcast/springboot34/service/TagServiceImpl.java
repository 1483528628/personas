package cn.itcast.springboot34.service;

import cn.itcast.springboot34.bean.dto.ModelDto;
import cn.itcast.springboot34.bean.dto.TagDto;
import cn.itcast.springboot34.bean.dto.TagModel;
import cn.itcast.springboot34.bean.po.ModelPo;
import cn.itcast.springboot34.bean.po.TagPo;
import cn.itcast.springboot34.repo.ModelRepository;
import cn.itcast.springboot34.repo.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ModelRepository modelRepository;
    @Override
    public void saveAll(List<TagDto> list) {
        List<TagPo> poList = list.stream().map(this::convert).collect(Collectors.toList());
        TagPo tag1 = poList.get(0);
        TagPo tag2 = poList.get(1);
        TagPo tag3 = poList.get(2);
//        设置一级标签的父ID为-1
        tag1.setPid(-1L);

        TagPo tmpTag = tagRepository.findByLevelAndNameAndPid(tag1.getLevel(), tag1.getName(), tag1.getPid());
        if (tmpTag == null) {
            tmpTag = tagRepository.save(tag1);
        }
        tag2.setPid(tmpTag.getId());
        tmpTag = tagRepository.findByLevelAndNameAndPid(tag2.getLevel(), tag2.getName(), tag2.getPid());
        if (tmpTag == null) {
            tmpTag = tagRepository.save(tag2);
        }
        tag3.setPid(tmpTag.getId());
        tmpTag = tagRepository.findByLevelAndNameAndPid(tag3.getLevel(), tag3.getName(), tag3.getPid());
        if (tmpTag == null) {
            tmpTag = tagRepository.save(tag3);
        }
    }

    @Override
    public List<TagDto> findTagsByPid(Long pid) {
        List<TagPo> poList = tagRepository.findByPid(pid);
        for (TagPo tagPo : poList) {
            System.out.println(tagPo);
        }
        return poList.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public void saveTagModel(TagDto tag, ModelDto model) {
        TagPo tagPo = tagRepository.save(convert(tag));
        modelRepository.save(convert(model, model.getId()));
    }

    @Override
    public List<TagModel> findAllTagModel(Long pid) {
        ArrayList<TagModel> list = new ArrayList<>();
        List<TagPo> tagList = tagRepository.findByPid(pid);
        for (TagPo tagPo : tagList) {
//            Long tagId = tagPo.getId();
//            ModelPo modelPo = modelRepository.findByTagId(tagId);
//            list.add(new TagModel(convert(tagPo), convert(modelPo)));
            ModelPo modelPo = null;
            if (tagPo.getLevel() == 4) {
                Long tagid = tagPo.getId();
                modelPo = modelRepository.findByTagId(tagid);
                list.add(new TagModel(convert(tagPo), convert(modelPo)));
            } else {
                list.add(new TagModel(convert(tagPo), null));
            }
        }
        return list;
    }

    @Override
    public void saveTag(TagDto dto) {
        tagRepository.save(convert(dto));
    }

    private ModelDto convert(ModelPo modelPo) {
        ModelDto modelDto = new ModelDto();
        modelDto.setId(modelPo.getId());
        modelDto.setName(modelPo.getName());
        modelDto.setMainClass(modelPo.getMainClass());
        modelDto.setPath(modelPo.getPath());
        modelDto.setArgs(modelPo.getArgs());
        modelDto.setState(modelPo.getState());
        modelDto.setSchedule(modelDto.parseDate(modelPo.getSchedule()));
        return modelDto;
    }
    private ModelPo convert(ModelDto modelDto,Long id) {
        ModelPo modelPo = new ModelPo();
        modelPo.setId(modelDto.getId());
        //设置4级ID为外部传入的ID
        modelPo.setTagId(id);
        modelPo.setName(modelDto.getName());
        modelPo.setMainClass(modelDto.getMainClass());
        modelPo.setPath(modelDto.getPath());
        modelPo.setSchedule(modelDto.getSchedule().toPattern());
        modelPo.setCtime(new Date());
        modelPo.setUtime(new Date());
        modelPo.setState(modelDto.getState());
        modelPo.setArgs(modelDto.getArgs());
        return modelPo;
    }
    private TagDto convert(TagPo tagPo) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tagPo.getId());
        tagDto.setLevel(tagPo.getLevel());
        tagDto.setName(tagPo.getName());
        tagDto.setPid(tagPo.getPid());
        tagDto.setRule(tagPo.getRule());
        tagDto.setState(tagPo.getState());
        return tagDto;
    }

    public TagPo convert(TagDto tagDto) {
        TagPo tagPo = new TagPo();
        tagPo.setId(tagDto.getId());
        tagPo.setName(tagDto.getName());
        tagPo.setRule(tagDto.getRule());
        tagPo.setLevel(tagDto.getLevel());
        tagPo.setPid(tagDto.getPid());
        tagPo.setState(tagDto.getState());
        return tagPo;
    }
}
