package root;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.session.SqlSession;

import vo.DeptVO;


public class SetDept {

RootFrame r;
JTable table;

String[] c_name = {"학과번호", "학과명"};


public SetDept(RootFrame r, JTable table,JButton add_bt) {
   this.r = r;
   this.table = table;
   searchTotal();

   add_bt.setEnabled(true);
   r.removeListner(table);
   table.addMouseListener(new MouseAdapter() {
	   
	   @Override
	   public void mouseClicked(MouseEvent e) {
		   int row = table.rowAtPoint(e.getPoint());
		   String deptNo = table.getValueAt(row, 0).toString(); // 학과 번호
		   String deptName = table.getValueAt(row, 1).toString(); // 학과명
		   
		   // AddDeptDialog 다이얼로그 호출, 선택된 학과 정보 전달
		   new AddDeptDialog(r, deptNo, deptName);
		    
	   }
   
   });
   
   add_bt.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		new AddDeptDialog(r);
	}
});
   }
   public void searchTotal() {
      SqlSession ss = r.factory.openSession();
      List<DeptVO>list = ss.selectList("dept.dept_All");
      String [][] ar = makeArray(list);
      table.setModel(new DefaultTableModel(ar, c_name));
    	  ss.close();
    	  
   }
   private String[][] makeArray(List<DeptVO>list){
   
      String [][] ar = null;
      if(!list.isEmpty()) {
         ar = new String[list.size()][c_name.length];
         
         int i = 0;
         for(DeptVO vo : list) {
            ar[i][0] = vo.getDept_no();
            ar[i][1] = vo.getDept_name();
            i++;
            
            
         }
         
         
               }
      return ar;
      
   
}


}