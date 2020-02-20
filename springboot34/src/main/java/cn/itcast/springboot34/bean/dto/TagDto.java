package cn.itcast.springboot34.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TagDto {
//    [{
//        "label": "一级标签名称",
//                "name": "电商",
//                "level": "1"
//    }, {
//        "label": "二级标签名称",
//                "name": "京东",
//                "level": "2"
//    }, {
//        "label": "三级标签名称",
//                "name": "人口属性",
//                "level": "3"
//    }]
//    标签的ID
    private Long id;
    //标签的名字
    private String name;
    //标签的规则
    private String rule;
    //标签的等级
    private Integer level;
    //标签的父ID
    private Long pid;
    //标签的状态
    private Integer state;
}
