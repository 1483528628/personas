package cn.itcast.demo;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MyMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] tempArr = value.toString().split("\001");
        List<String> valueList = Arrays.asList(tempArr);
        int num = Hive2Hbase.fieldNameList.size() - valueList.size();
        for (int i = 0; i < num; i++) {
            valueList.add("");
        }
        Put put = new Put(valueList.get(0).getBytes());
        for (int i = 0; i < valueList.size(); i++) {
            put.addColumn(
                    Hive2Hbase.FAMILY_NAME.getBytes(),
                    Hive2Hbase.fieldNameList.get(i).getBytes(),
                    valueList.get(i).getBytes()
            );
        }
        context.write(new ImmutableBytesWritable(valueList.get(0).getBytes()),put);
    }
}
