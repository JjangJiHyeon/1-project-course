package root;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.session.SqlSession;

import vo.SubVO;

public class SetSubject{
	RootFrame parent;
   SetSubject s;
   JTable table;
	String[] c_name = {"과목번호", "과목명", "과목시간",
			"과목정보", "교수번호", "학과번호", "인원수"};
    
    public SetSubject(RootFrame p,JTable table,JButton add_bt) {
    	this.parent = p;
    	this.table =table;
    	p.removeListner(table);
    	searchTotal();
    	add_bt.setEnabled(true);
    	addTableClickListener(add_bt);// 클릭 이벤트 추가 아직 안만듬
    	add_bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddSubDialog(p, SetSubject.this).setVisible(true);;
				
			}
		});
    	
    }

    
    private void addTableClickListener(JButton add_bt) {
    	table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int selectedRow =table.rowAtPoint(e.getPoint());;
					if(selectedRow != -1) {
						SubVO selectedSubject =  getSelectedSubject(selectedRow);
                        showSubjectDialog(selectedSubject);
					}
				}
			}
    		
		});
    }
    private SubVO getSelectedSubject(int rowIndex) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        SubVO subject = new SubVO();
        subject.setSub_no(getValue(model, rowIndex, 0));
        subject.setSub_name(getValue(model, rowIndex, 1));
        subject.setSub_time(getValue(model, rowIndex, 2));
        subject.setSub_info(getValue(model, rowIndex, 3));
        subject.setSub_cnt(getValue(model, rowIndex, 6));
        subject.setProf_no(getValue(model, rowIndex, 4));
        subject.setDept_no(getValue(model, rowIndex, 5));
        return subject;
    }

    // 안전하게 데이터 추출
    private String getValue(DefaultTableModel model, int row, int column) {
        Object value = model.getValueAt(row, column);
        return value != null ? value.toString() : ""; // null이면 빈 문자열 반환
    }
    private void showSubjectDialog(SubVO subject) {
        SubjectDialog dialog = new SubjectDialog(parent, parent.factory, subject, this);
        dialog.setVisible(true);
       
    }
    
    public void refreshTable() {
        searchTotal(); // 기존 테이블 데이터 갱신하는 메서드 추가
    }
    
    public void searchTotal() {
        try (SqlSession session = parent.factory.openSession()) {
            List<SubVO> list = session.selectList("subject.subjectAll");
            String[][] data = makeArray(list);
            table.setModel(new DefaultTableModel(data, c_name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String[][] makeArray(List<SubVO> list) {
        String[][] data = new String[list.size()][c_name.length];
        for (int i = 0; i < list.size(); i++) {
            SubVO vo = list.get(i);
            data[i][0] = vo.getSub_no();
            data[i][1] = vo.getSub_name();
            data[i][2] = vo.getSub_time();
            data[i][3] = vo.getSub_info();
            data[i][4] = vo.getProf_no();
            data[i][5] = vo.getDept_no();
            data[i][6] = vo.getSub_cnt();
        }
    	return data;
    }
}
