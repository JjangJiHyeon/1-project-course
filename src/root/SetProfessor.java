package root;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.session.SqlSession;

import vo.ProfVO;

public class SetProfessor {
	
	RootFrame r;
	List<ProfVO> list;
	public String[] c_name = { "번호", "강사명", "이메일" };

	public SetProfessor(RootFrame r, JTable table, JButton add_bt) {
		this.r = r;
		String[][] ar = selectProf();
		table.setModel(new DefaultTableModel(ar, c_name));
		add_bt.setEnabled(true);
		r.removeListner(table);
		add_bt.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new AddProfDialog(r,SetProfessor.this);
			}
		});
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				ProfVO vo = list.get(row);
				
				new AddProfDialog(r, SetProfessor.this, table, vo);
			}
			
		});
	}
	
	// 2차원 배열
	public String[][] makeArray() {
		String[][] ar = null;
		
		if (list != null && list.size() > 0) {
			ar = new String[list.size()][c_name.length];
			
			int i = 0;
			for (ProfVO vo : list) {
				ar[i][0] = vo.getProf_no();
				ar[i][1] = vo.getProf_name();
				ar[i][2] = vo.getProf_email();
				
				i++;
			}
		}
		
		return ar;
	}
	
	// 강사 조회
	public String[][] selectProf() {
		SqlSession ss = r.factory.openSession();
		
		list = ss.selectList("prof.select_prof");
		
		ss.close();
		
		return makeArray();
	}	
	
}
