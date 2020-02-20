package cn.itcast.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Hive2Hbase {
    public static final String ZK_QUORUM = "bd001:2181";
    public static final String ZK_NODE_PATH = "/hbase-unsecure";
    //hive表数据目录
    public static final String INPUT_PATH = "hdfs://bd001:8020/user/hive/warehouse/tags_dat.db/tbl_users";
    //生成的hfile目录,是一个临时目录
    public static final String OUTPUT_PATH = "hdfs://bd001:8020/output_hfile/tbl_users_34";
    //表名
    public static final String TABLE_NAME = "tbl_user_tmp_33";
    //列簇的名字
    public static final String FAMILY_NAME = "detail";
    //表字段,new 类名(){}创建的是该类的子类对象.{{代码块,随着对象的创建而加载}}
    public static final List<String> fieldNameList = new ArrayList<String>() {{
        add("id");
        add("siteid");
        add("avatarimagefileid");
        add("email");
        add("username");
        add("password");
        add("salt");
        add("registertime");
        add("lastlogintime");
        add("lastloginip");
        add("memberrankid");
        add("bigcustomerid");
        add("lastaddressid");
        add("lastpaymentcode");
        add("gender");
        add("birthday");
        add("qq");
        add("job");
        add("mobile");
        add("politicalface");
        add("nationality");
        add("validatecode");
        add("pwderrcount");
        add("source");
        add("marriage");
        add("money");
        add("moneypwd");
        add("isemailverify");
        add("issmsverify");
        add("smsverifycode");
        add("emailverifycode");
        add("verifysendcoupon");
        add("canreceiveemail");
        add("modified");
        add("channelid");
        add("grade_id");
        add("nick_name");
        add("is_blacklist");
    }};

    public static void main(String[] args) throws Exception {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", ZK_QUORUM);
        //Hbase的在zookeeper上的节点信息
        configuration.set("zookeeper.znode.parent", ZK_NODE_PATH);
        Job job = Job.getInstance(configuration);
        job.setMapperClass(MyMapper.class);
//        2. map 输出的key
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);
//        3. map 输出的value
        job.setMapOutputValueClass(Put.class);
//        4. jar 的class
        job.setJarByClass(Hive2Hbase.class);

//        4. 利用Hbase的Reduce实现生成HFile的功能
        //创建Table
        Connection conn = ConnectionFactory.createConnection(configuration);
        //获取管理对象
        Admin admin = conn.getAdmin();
        //创建表
        TableName tableName = TableName.valueOf("tbl_user_tmp_34");
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        tableDescriptor.addFamily(new HColumnDescriptor(FAMILY_NAME));
        admin.createTable(tableDescriptor);
        Table table = conn.getTable(tableName);
        //创建一个region的定位器
        RegionLocator regionLocator = conn.getRegionLocator(tableName);
        HFileOutputFormat2.configureIncrementalLoad(job, table, regionLocator);
//        5. 设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));
//        6. 执行Job
        job.waitForCompletion(true);
//        7. 使用HBase的Bulkload,将HFile装载到HBase.
        LoadIncrementalHFiles bulkload = new LoadIncrementalHFiles(configuration);
        //执行bulkload操作
        bulkload.doBulkLoad(new Path(OUTPUT_PATH), admin, table, regionLocator);
        System.out.println("BulkLoad执行完毕");
    }
}
