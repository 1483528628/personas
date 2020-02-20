package cn.itcast.springboot34.repo;

import cn.itcast.springboot34.bean.po.ModelPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//模型的Repo
public interface ModelRepository extends JpaRepository<ModelPo, Long> {
    //根据4级的ID进行查询
    ModelPo findByTagId(Long tagId);

}
