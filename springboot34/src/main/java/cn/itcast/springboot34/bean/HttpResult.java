package cn.itcast.springboot34.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResult<T> {
//    状态码
    private Integer code;
//    提示信息
    private String msg;
//    响应结果数据
    private T data;
}
