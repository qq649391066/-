/*package db;


import java.sql.*;

public class Test {

    public static void main(String[] args) {
        // �������ݿ�
        //1.�����������������ݿ��Ӧ�İ���oracle.jdbc.driver
        //1.���������ݿ��Ӧ�������ࣩOracleDriver.class
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //���д���ǹ̶���
        //2.��ȡ���ݿ�����:ͨ��java������������
        //url-���ݿ��ַ,��ͬ�����ݿ�д����ͬ 127.0.0.1��localhost��������
        //url-���ݿ��ַ��user -�û�����password-����     ConnectionΪ����     DriverManager����������
        Connection conn=DriverManager.getConnection(
                "jdbc:oracle:thin:@127.0.0.1:1521:orcl",
                "system","manager");
        System.out.println("���ӳɹ�");
        //�������ݿ�-��ɾ�Ĳ�
        //3.��ò������ݿ�����
        Statement st=conn.createStatement();//Statement����   createStatement��������
        
        //4.DML ��������
        //ִ�и��²���
        //����ֵ����ò���Ӱ������ݼ�¼����
        //int i=st.executeUpdate("insert into student(sno,sname,ssex)"
        //+"values('120','����','��')");
        
        //int i=st.executeUpdate("update student set ssex='��' where ssex='1'");
        
        //System.out.println("������ݳɹ�  ����ֵ="+i);//����ֵ
        
        //5.��ѯ����
        //ResultSet���ݽ����
        ResultSet rs=st.executeQuery("select * from userlist");
        
        //���������   �����϶��Ǹ�ѭ��
        //next() �ж��Ƿ������һ����¼��������ھ��ƶ�ָ�뵽��һ����¼��
        while(rs.next())
        {
            //��ȡ����
            String id=rs.getString("id");
            String name=rs.getString("name");
            String stunum=rs.getString("stunum");
            String pwd=rs.getString("pwd");
            System.out.println("id="+id+
                    "name="+name+
                    "stunum="+stunum+"pwd="+pwd);    
        }
        //�ͷ���Դ   
        //�ͷ���Դ Ŀ�ģ���Windows��ÿ����һ������ϵͳ��Դ�ͻ���١�
        //�еĳ�������Ĵ�����ϵͳ��Դ����ʹ�ѳ���رգ����ڴ��л�����һЩû�õ�DLL�ļ������У�������ʹ��ϵͳ�������ٶ��½���
        rs.close();
        
        st.close();
        
        //�ر����ݿ�
        conn.close();
        
        } catch (ClassNotFoundException | SQLException e) {
            // TODO �Զ����ɵ� catch ��
            e.printStackTrace();
        }
    }
}*/