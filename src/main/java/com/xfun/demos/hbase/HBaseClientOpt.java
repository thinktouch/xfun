package com.xfun.demos.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.hadoop.yarn.webapp.hamlet.HamletSpec.Scope.row;

/**
 * Created by xfun on 12/25/16.
 */
public class HBaseClientOpt {
    public static Configuration conf;

    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "quickstart.cloudera");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
//        conf.addResource("hbase-site.xml");
    }

    /**
     * 创建表
     *
     * @param tablename 表名
     * @param columnFamily 列族
     * @throws MasterNotRunningException
     * @throws ZooKeeperConnectionException
     * @throws IOException
     */
    public static void createTable(String tablename, String columnFamily) throws IOException {
        System.out.println("tablename: " + tablename);
        System.out.println("columnFamily: " + columnFamily);
        System.out.println("conf: " + conf);
        Connection conn = ConnectionFactory.createConnection(conf);
        Admin admin = conn.getAdmin();
        try {
            if (admin.tableExists(TableName.valueOf(tablename))) {
                System.out.println(tablename + " already exists");
            } else {
                TableName tableName = TableName.valueOf(tablename);
                HTableDescriptor tableDesc = new HTableDescriptor(tableName);
                tableDesc.addFamily(new HColumnDescriptor(columnFamily));
                admin.createTable(tableDesc);
                System.out.println(tablename + " created succeed");
            }
        } finally {
            admin.close();
            conn.close();
        }
    }

    /**
     * 向表中插入一条新数据
     *
     * @param tableName 表名
     * @param row 行键key
     * @param columnFamily 列族
     * @param column 列名
     * @param data 要插入的数据
     * @throws IOException
     */
    public static void putData(String tableName, String row, String columnFamily, String column, String data)
            throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(tableName));
        try {
            Put put = new Put(Bytes.toBytes(row));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(data));
            table.put(put);
            System.out.println("put '" + row + "','" + columnFamily + ":" + column + "','" + data + "'");
        } finally {
            table.close();
            conn.close();
        }
    }

    /**
     * add a column family to an existing table
     *
     * @param tableName table name
     * @param columnFamily column family
     * @throws IOException
     */
    public static void putFamily(String tableName, String columnFamily) throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        Admin admin = conn.getAdmin();
        try {
            if (!admin.tableExists(TableName.valueOf(tableName))) {
                System.out.println(tableName + " not exists");
            } else {
                admin.disableTable(TableName.valueOf(tableName));

                HColumnDescriptor cf1 = new HColumnDescriptor(columnFamily);
                admin.addColumn(TableName.valueOf(tableName), cf1);

                admin.enableTable(TableName.valueOf(tableName));
                System.out.println(TableName.valueOf(tableName) + ", " + columnFamily + " put succeed");
            }
        } finally {
            admin.close();
            conn.close();
        }
    }

    /**
     * 根据key读取一条数据
     *
     * @param tableName 表名
     * @param row 行键key
     * @param columnFamily 列族
     * @param column 列名
     * @throws IOException
     */
    public static void getData(String tableName, String row, String columnFamily, String column) throws IOException{
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(tableName));
        try{
            Get get = new Get(Bytes.toBytes(row));
            Result result = table.get(get);
            byte[] rb = result.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
            String value = new String(rb, "UTF-8");
            System.out.println(value);
        } finally {
            table.close();
            conn.close();
        }
    }

    /**
     * get all data of a table
     *
     * @param tableName table name
     * @throws IOException
     */
    public static void scanAll(String tableName) throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(tableName));
        try {
            Scan scan = new Scan();
            ResultScanner resultScanner = table.getScanner(scan);
            for(Result result : resultScanner){
                List<Cell> cells = result.listCells();
                for(Cell cell : cells){
                    String row = new String(result.getRow(), "UTF-8");
                    String family = new String(CellUtil.cloneFamily(cell), "UTF-8");
                    String qualifier = new String(CellUtil.cloneQualifier(cell), "UTF-8");
                    String value = new String(CellUtil.cloneValue(cell), "UTF-8");
                    System.out.println("[row:"+row+"],[family:"+family+"],[qualifier:"+qualifier+"],[value:"+value+"]");
                }
            }
        } finally {
            table.close();
            conn.close();
        }
    }

    /**
     * delete a data by row key
     *
     * @param tableName table name
     * @param rowKey row key
     * @throws IOException
     */
    public static void delData(String tableName, String rowKey) throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(tableName));
        try {
            List<Delete> list = new ArrayList<Delete>();
            Delete del = new Delete(rowKey.getBytes());
            list.add(del);
            table.delete(list);
            System.out.println("delete record " + rowKey + " ok");
        } finally {
            table.close();
            conn.close();
        }
    }

    /**
     * delete a column's value of a row
     *
     * @param tableName table name
     * @param rowKey row key
     * @param familyName family name
     * @param columnName column name
     * @throws IOException
     */
    public static void deleteColumn(String tableName, String rowKey, String familyName, String columnName) throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(tableName));
        try{
            Delete del = new Delete(Bytes.toBytes(rowKey));
            del.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
            List<Delete> list = new ArrayList<Delete>(1);
            list.add(del);
            table.delete(list);
            System.out.println("[table:"+tableName+"],row:"+rowKey+"],[family:"+familyName+"],[qualifier:"+columnName+"]");
        } finally {
            table.close();
            conn.close();
        }
    }

    /**
     * delete a columnFamily's all columns value of a row
     *
     * @param tableName table name
     * @param rowKey row key
     * @param familyName family name
     * @throws IOException
     */
    public static void deleteFamily(String tableName, String rowKey, String familyName) throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(tableName));
        try{
            Delete del = new Delete(Bytes.toBytes(rowKey));
            del.addFamily(Bytes.toBytes(familyName));
            List<Delete> list = new ArrayList<Delete>(1);
            list.add(del);
            table.delete(list);
            System.out.println("[table:"+tableName+"],row:"+rowKey+"],[family:"+familyName+"]");
        } finally {
            table.close();
            conn.close();
        }
    }

    /**
     * delete a table
     *
     * @param tableName table name
     * @throws IOException
     * @throws MasterNotRunningException
     * @throws ZooKeeperConnectionException
     */
    public static void deleteTable(String tableName) throws IOException, MasterNotRunningException, ZooKeeperConnectionException {
        Connection conn = ConnectionFactory.createConnection(conf);
        Admin admin = conn.getAdmin();
        try {
            admin.disableTable(TableName.valueOf(tableName));
            admin.deleteTable(TableName.valueOf(tableName));
            System.out.println("delete table " + tableName + " ok");
        } finally {
            admin.close();
            conn.close();
        }
    }

    /**
     * 批量插入數據
     * @param tableName
     * @param puts
     * @throws IOException
     */
    public static void batchPut(String tableName, List<Put> puts) throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(tableName));
        try {
//            for(Put put : puts){
//                table.put(put);
//            }
            table.put(puts);
        } finally {
            table.close();
            conn.close();
        }
    }

    public static void main(String[] args) {
        System.err.println("start...");
        long startTime = System.currentTimeMillis();

        String tableName         = "msg_online";
        String columnFamily      = "info";
        String column_send_time  = "send_time";
        String column_from       = "from";
        String column_to         = "to";
        String from              = "x_00000001";

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{
//          createTable(tableName, "info");

/*

            for(int i = 1; i <= 100000; i++){
                Date date = new Date();
                String cunnentTime = dateFormatter.format(date);
                String to = new StringBuffer("x_").append(String.format("%08d",i)).toString();
                String rowkey = new StringBuilder(from).append("#").append(to).toString();
                putData(tableName, rowkey, columnFamily, column_send_time, cunnentTime);
                putData(tableName, rowkey, columnFamily, column_from, from);
                putData(tableName, rowkey, columnFamily, column_to, to);
            }
*/





            List<Put> putList = new ArrayList<Put>();
            for(int n = 1; n <= 200000; n++){
                Date date = new Date();
                String cunnentTime = dateFormatter.format(date);
                String to = new StringBuffer("x_").append(String.format("%08d",n)).toString();
                String rowkey = new StringBuilder(from).append("#").append(to).toString();
                Put put = new Put(Bytes.toBytes(rowkey));
                put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column_send_time), Bytes.toBytes(cunnentTime));
                put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column_from), Bytes.toBytes(from));
                put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column_to), Bytes.toBytes(to));
                putList.add(put);
            }
            System.out.println("开始批量插入数据。。。");
            batchPut(tableName, putList);



        } catch(Exception ex){
            ex.printStackTrace();
        }
        System.err.println("end...");
        System.err.println("cost time (ms):" + (System.currentTimeMillis() - startTime));
    }
}
