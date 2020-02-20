package cn.itcast.springboot34.bean.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelDto {
    private Long id;
    private String name; //模型的名称
    private String mainClass; //主程序
    private String path; //jar路径
    private String args;//运行参数
    //调用频次
    private Schedule schedule;
    private Integer state; //模型的状态: 3->运行,4->停止
    @Data
    public static class Schedule {
        private Integer frequency;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private Date startTime;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private Date endTime;
        public String toPattern(){
            String schedule = "";
            String starStr = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(startTime);
            String endStr = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(endTime);
            // 将频次和开始/结束时间拼接成数据库需要的格式.
            return frequency + "," + starStr + "," + endStr;
        }
        public static String formatTime(Date time) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'+0800'");
            return formatter.format(time);
        }
    }
    public static Schedule parseDate(String sourceDate){
        Schedule schedule = new Schedule();
        try {
            String[] sourceArr = sourceDate.split(",");
            schedule.setFrequency(Integer.parseInt(sourceArr[0]));
            schedule.setStartTime(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").parse(sourceArr[1]));
            schedule.setEndTime(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").parse(sourceArr[2]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return schedule;
    }
}
