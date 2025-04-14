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

import vo.ExFrameVO;
import vo.ExamVO;
import vo.QuestVO;
import vo.StuAnsVO;
import vo.StuSubInfoVO;
import vo.StuVO;
import vo.SubVO;

public class SetExam {
	StudentFrame parent;
	List<ExFrameVO> list;
	public String[] c_name = { "시험번호", "과목명", "시험명", "응시" };
	String[][] ar;
	JTable table;
	SqlSessionFactory factory;

	public SetExam(StudentFrame parent, JTable table) {
		this.parent = parent;
		this.table = table;
		
		// 테이블 비활성화
		table.setDefaultEditor(Object.class, null);
		
		factory = parent.factory;
		showList();

	}

	public void showList() {
		SqlSession ss = factory.openSession();

		String[][] ar = makeArray();
		DefaultTableModel model = new DefaultTableModel(ar, c_name);
		table.setModel(model);
		table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));
		ss.close();
	}

	String[][] makeArray() {
		this.ar = null;
		SqlSession ss = factory.openSession();
		list = ss.selectList("student.get_exf_stu", parent.s.getStu_no());
		List<SubVO> ss_list = ss.selectList("subject.chosen_subject", parent.s.getStu_no());

		if (list != null && !list.isEmpty()) {
			ar = new String[list.size()][c_name.length];
			int i = 0;
			for (SubVO svo : ss_list) {
				for (ExFrameVO vo : list) { // 2차원 배열에 자원들을 넣기위한 반복문
					
					if (vo.getSub_no().equals(svo.getSub_no())) {
						ar[i][0] = vo.getExf_no();
						ar[i][1] = svo.getSub_name();
						ar[i][2] = vo.getExf_name();
						if (check_tested(vo))
							ar[i][3] = "시험완료";
						else
							ar[i][3] = "응시하기";
						
						i++;

					}
				}
			}
		}
		ss.close();
		return ar;
	}

	public boolean check_tested(ExFrameVO exfvo) {
		SqlSession ss = factory.openSession();
		List<StuAnsVO> sa_list = ss.selectList("get_stuans_all", parent.s.getStu_no());
		for (StuAnsVO savo : sa_list) {

			if (exfvo.getExf_no().equals(savo.getExf_no())) {
				ss.close();
				return true;
			}
		}
		ss.close();
		return false;
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			String status = (value != null) ? value.toString() : "";
			if (value == null) {
				setText("신청"); // 기본값 설정
			} else {
				setText(value.toString());
			}
			setText(status);
			setEnabled(!"시험완료".equals(status));
			return this;
		}

	}

	// JButton 에디터 클래스
	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;
		private String label;
		private boolean clicked;
		ExFrameVO vo;
		private boolean tested = false;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton("");
			button.setOpaque(true);

			// 버튼 클릭 이벤트
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!"시험완료".equals(label)) { // '시험완료'가 아니면 작업 실행
						fireEditingStopped();
					} else {
						JOptionPane.showMessageDialog(null, "이미 완료된 시험입니다.");
					}
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			label = value == null ? "" : value.toString();
			button.setText(label);
			clicked = true;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			if (clicked) {
				// 버튼 클릭 시 수행할 작업

				List<QuestVO> qvo = getExamFrameInfo();
				new TakingExamDialog(SetExam.this, qvo, vo);

			}
			clicked = false;
			return label;
		}

		private List<QuestVO> getExamFrameInfo() {
			SqlSession ss = factory.openSession();
			String exf_no = ar[table.getSelectedRow()][0];
			this.vo = ss.selectOne("student.get_exf_one", exf_no);
			List<QuestVO> qvo = ss.selectList("student.get_quest_list", exf_no);
			ss.close();
			return qvo;
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
