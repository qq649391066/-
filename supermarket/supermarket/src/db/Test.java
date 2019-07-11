/*package db;


import java.sql.*;

public class Test {

    public static void main(String[] args) {
        // 访问数据库
        //1.加载驱动：加载数据库对应的包名oracle.jdbc.driver
        //1.（加载数据库对应的驱动类）OracleDriver.class
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //这个写法是固定的
        //2.获取数据库连接:通过java的驱动管理器
        //url-数据库地址,不同的数据库写法不同 127.0.0.1和localhost都代表本机
        //url-数据库地址：user -用户名：password-密码     Connection为连接     DriverManager驱动管理器
        Connection conn=DriverManager.getConnection(
                "jdbc:oracle:thin:@127.0.0.1:1521:orcl",
                "system","manager");
        System.out.println("连接成功");
        //操作数据库-增删改查
        //3.获得操作数据库声明
        Statement st=conn.createStatement();//Statement声明   createStatement创建声明
        
        //4.DML 增加数据
        //执行更新操作
        //返回值代表该操作影响的数据记录条数
        //int i=st.executeUpdate("insert into student(sno,sname,ssex)"
        //+"values('120','王五','男')");
        
        //int i=st.executeUpdate("update student set ssex='男' where ssex='1'");
        
        //System.out.println("添加数据成功  返回值="+i);//返回值
        
        //5.查询数据
        //ResultSet数据结果集
        ResultSet rs=st.executeQuery("select * from userlist");
        
        //遍历结果集   遍历肯定是个循环
        //next() 判断是否存在下一条记录，如果存在就移动指针到下一条记录上
        while(rs.next())
        {
            //读取数据
            String id=rs.getString("id");
            String name=rs.getString("name");
            String stunum=rs.getString("stunum");
            String pwd=rs.getString("pwd");
            System.out.println("id="+id+
                    "name="+name+
                    "stunum="+stunum+"pwd="+pwd);    
        }
        //释放资源   
        //释放资源 目的：在Windows中每运行一个程序，系统资源就会减少。
        //有的程序会消耗大量的系统资源，即使把程序关闭，在内存中还是有一些没用的DLL文件在运行，这样就使得系统的运行速度下降。
        rs.close();
        
        st.close();
        
        //关闭数据库
        conn.close();
        
        } catch (ClassNotFoundException | SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}*/