package cn.itcast.springboot34.bean.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "tbl_model")
@AllArgsConstructor
@NoArgsConstructor
public class ModelPo {
    //当前模型的状态
    public static final Integer STATE_APPLYING = 1; //以受理
    public static final Integer STATE_PASSED = 2; // 以通过审核
    public static final Integer STATE_ENABLE = 3; //开启
    public static final Integer STATE_DISABLE = 4; //关闭
    //当前模型执行频次
    public static final Integer FREQUENCY_ONCE = 0; //仅一次
    public static final Integer FREQUENCY_DAILY = 1; //每天
    public static final Integer FREQUENCY_WEEKLY = 2; //每周
    public static final Integer FREQUENCY_MONTHLY = 3; //每月
    public static final Integer FREQUENCY_YEARLY = 4; //每年

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "model_name")
    private String name;
    private Integer state; // 1申请中、2审核通过、3运行中、4未运行


    @Column(name = "model_path")
    private String path;
    @Column(name = "model_main")
    private String mainClass;
    @Column(name = "sche_time")
    private String schedule;
    private String args;

    private Date ctime;
    private Date utime;
}
