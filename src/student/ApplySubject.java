package student;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import student.SetExam.ButtonEditor;
import student.SetExam.ButtonRenderer;
import vo.ExFrameVO;
import vo.QuestVO;
import vo.StuSubInfoVO;
import vo.StuVO;
import vo.SubStuExVO;
import vo.SubVO;

public class ApplySubject {
	StudentFrame parent;
	JTable table;
	SqlSessionFactory factory;
	String[][] ar;
	List<SubVO> sub_list;
	List<SubVO> applied;
	String[] c_name = { "과목 번호", "과목명", "시작 날짜", "과목 정보", "정원", "" };
	StuVO s;

	public ApplySubject(StudentFrame parent) {
		this.parent = parent;
		this.table = parent.table;
		this.factory = parent.factory;
		this.s = parent.s;
		init();
		DefaultTableModel model = new DefaultTableModel(ar, c_name);
		
		// 테이블 비활성화
		table.setDefaultEditor(Object.class, null);
		
		table.setModel(model);
		table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));
	}

	private void init() {

		SqlSession ss = factory.openSession();
		sub_list = ss.selectList("subject.subjectAll");
		applied = ss.selectList("subject.chosen_subject", s.getStu_no());
		ar = new String[sub_list.size()][c_name.length];
		boolean dup = false;
		int i = 0;
		for (SubVO svo : sub_list) {
			for (SubVO ap : applied) {
				if (svo.getSub_no().equals(ap.getSub_no())) {
					dup = true;
					System.out.println(ap.getSub_name()+" / "+dup);
				}
			}
			if (dup == false) {
				ar[i][0] = svo.getSub_no();
				ar[i][1] = svo.getSub_name();
				ar[i][2] = svo.getSub_time();
				ar[i][3] = svo.getSub_info();
				ar[i][4] = svo.getSub_cnt();
				ar[i][5] = "신청";
				i++;
			}
			dup = false;
		}
		ss.close();
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			  setText(value == null ? "" : value.toString());
			return this;
		}

	}

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;
		private String label;
		private boolean clicked;
		ExFrameVO vo;
		private boolean tested = false;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton("신청하기");
			button.setOpaque(true);

			// 버튼 클릭 이벤트
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			clicked = true;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			if (clicked) {
				// 버튼 클릭 시 수행할 작업
				insertStuSub();
				init();
			}
			clicked = false;
			return label;
		}

		private void insertStuSub() {
			SqlSession ss = factory.openSession();
			String sub_no = ar[table.getSelectedRow()][0];
			StuSubInfoVO vo = new StuSubInfoVO();
			vo.setStu_no(s.getStu_no());
			vo.setSub_no(sub_no);
			int a = ss.insert("subject.insert_stu_sub", vo);

			ss.commit();
			ss.close();
		}

		@Override
		public boolean stopCellEditing() {
			clicked = false;
			return super.stopCellEditing();
		}

		@Override
		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}

	}
}
