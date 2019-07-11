package librarySystem;

import java.awt.*; 
  
import javax.swing.*; 
  
import java.awt.event.*; 
import java.sql.*; 
  
@SuppressWarnings("serial") 
public class BookInfo extends JFrame implements ActionListener{ 
 //�������ϵĿؼ� 
 private JLabel inputLabel; 
 private JTextField inputText; 
 private JButton searchBut; 
  
 private JTable bookTable; 
 private JScrollPane bookScroll; 
 private JButton addBut; 
 private JButton modifyBut; 
 private JButton deleteBut; 
 private JButton refreshBut; 
 private BookTableModel bookTableModel; 
 public static void main(String[] args) throws SQLException { 
  // TODO Auto-generated method stub 
  BookInfo bookInfo=new BookInfo(); 
  bookInfo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
  bookInfo.setBounds(350, 150, 600, 400); 
  bookInfo.setVisible(true); 
//  bookInfo.importSQL();//�������� 
  bookInfo.setMinWindowLayout();//�������� 
 } 
 public BookInfo() throws SQLException{ 
  //�����������ϵĿؼ� 
  inputLabel=new JLabel("����������:"); 
  inputText=new JTextField(10); 
  searchBut=new JButton("��ѯ"); 
  bookTableModel=new BookTableModel(); 
    
  bookTable=new JTable(bookTableModel); 
  bookScroll=new JScrollPane(bookTable); 
    
  addBut=new JButton("���"); 
  modifyBut=new JButton("�޸�"); 
  deleteBut=new JButton("ɾ��"); 
  refreshBut=new JButton("ˢ��"); 
  searchBut.addActionListener(this); 
  addBut.addActionListener(this); 
  refreshBut.addActionListener(this); 
  modifyBut.addActionListener(this); 
  deleteBut.addActionListener(this); 
  
 } 
  
 void setMinWindowLayout(){ 
  //�����沼�� 
  Container con1=new Container(); 
  con1.setLayout(new FlowLayout()); 
  con1.add(inputLabel); 
  con1.add(inputText); 
  con1.add(searchBut); 
  con1.add(refreshBut); 
  Container con2=new Container(); 
  con2.setLayout(new FlowLayout()); 
  con2.add(addBut); 
  con2.add(modifyBut); 
  con2.add(deleteBut); 
  this.setLayout(new BorderLayout()); 
  this.add(con1,BorderLayout.NORTH); 
  this.add(bookScroll,BorderLayout.CENTER); 
  this.add(con2,BorderLayout.SOUTH); 
  this.validate(); 
 } 
 @Override
 public void actionPerformed(ActionEvent e) { 
  // TODO Auto-generated method stub 
  if(e.getSource()==searchBut){ 
   if(!this.inputText.getText().equals("")){ 
    String bookName=this.inputText.getText(); 
    String sql="SELECT * FROM book_info WHERE book_name ='"+bookName+"'"; 
    try { 
    bookTableModel=new BookTableModel(sql); 
    bookTable.setModel(bookTableModel); 
   } catch (SQLException e1) { 
    // TODO Auto-generated catch block 
    e1.printStackTrace(); 
   } 
      
   }else{ 
    JOptionPane.showMessageDialog(this,"���벻��Ϊ��", "��ʾ",JOptionPane.PLAIN_MESSAGE); 
   } 
  } 
  else if(e.getSource()==addBut){ 
   @SuppressWarnings("unused") 
   AddBookDialog addWin=new AddBookDialog(this,"���ͼ��",true); 
   this.refreshTable(); 
  } 
  else if(e.getSource()==refreshBut){ 
   this.refreshTable(); 
  } 
  else if(e.getSource()==deleteBut){ 
   int rowNum=bookTable.getSelectedRow(); 
   if(rowNum<0||rowNum>bookTable.getRowCount()){    
    JOptionPane.showMessageDialog(this,"δѡ��", "��ʾ",JOptionPane.PLAIN_MESSAGE); 
   } 
   else{ 
    //System.out.print(bookName); 
    int n = JOptionPane.showConfirmDialog(null, "ȷ��ɾ����?", "ȷ��ɾ����", JOptionPane.YES_NO_OPTION); 
    if (n == JOptionPane.YES_OPTION) { 
     String bookNum=(String) bookTable.getValueAt(rowNum, 0); 
     String sql="DELETE FROM book_info WHERE book_num= '"+bookNum+"'"; 
     bookTableModel.deleteBook(sql); 
     this.refreshTable(); 
     JOptionPane.showMessageDialog(this,"ɾ���ɹ�", "��ʾ",JOptionPane.PLAIN_MESSAGE); 
    } else if (n == JOptionPane.NO_OPTION) { 
     return; 
    } 
   } 
  } 
  else if(e.getSource()==modifyBut){ 
   bookTable.setModel(bookTableModel); 
   int rowNum=bookTable.getSelectedRow(); 
   if(rowNum<0||rowNum>bookTable.getRowCount()){    
    JOptionPane.showMessageDialog(this,"δѡ��", "��ʾ",JOptionPane.PLAIN_MESSAGE); 
   } 
   else{ 
    @SuppressWarnings("unused") 
    ModifyBook modifyWin=new ModifyBook(this,"�޸���Ϣ",true,bookTableModel,rowNum); 
    this.refreshTable(); 
   } 
  } 
    
 } 
 public void refreshTable(){ 
  BookTableModel searchBook; 
  try { 
   searchBook = new BookTableModel("SELECT * FROM book_info"); 
   bookTable.setModel(searchBook); 
   bookTableModel=searchBook; 
  } catch (SQLException e1) { 
   // TODO Auto-generated catch block 
   e1.printStackTrace(); 
  } 
 } 
} 