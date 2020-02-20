package cn.itcast.springboot34.bean.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

//数据库中的标签实体类
@Data //让编译的时候将getset方法自动创建,我们IDEA需要有lombok插件支持.
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_basic_tag") //TblBasicTag
public class TagPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增
    private Long id;

    private String name;
    private String industry;
    private String rule;
    private String business;
    private Integer level;
    private Long pid;
    private Date ctime;
    private Date utime;
    private Integer state;
    private String remark;

}
