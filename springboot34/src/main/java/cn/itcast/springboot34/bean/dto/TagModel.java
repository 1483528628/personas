package cn.itcast.springboot34.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagModel {
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
    private TagDto tag;
    private ModelDto model;
}
