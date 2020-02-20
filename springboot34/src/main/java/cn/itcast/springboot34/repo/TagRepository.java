package cn.itcast.springboot34.repo;

import cn.itcast.springboot34.bean.po.TagPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<TagPo,Long> {

    TagPo findByLevelAndNameAndPid(Integer level, String name, Long pid);

    List<TagPo> findByPid(Long pid);
}
