package student;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import student.SetExam.ButtonEditor;
import student.SetExam.ButtonRenderer;
import vo.CorrectTableVO;
import vo.ExFrameVO;
import vo.ExamResultVO;
import vo.QuestVO;

public class CorrectTable{

	StudentFrame parent;
	List<ExFrameVO> list;
	public String [] c_name = {"시험번호","시험명", "확인"};
	String ar [][];
	JTable table;
	SqlSessionFactory factory;
	CorrectExam ce;
	public CorrectTable(StudentFrame parent, JTable table ) {
	
		this.parent = parent;
		this.table = table;
		factory = parent.factory;
		showList();
	}
		private void showList() {
			SqlSession ss = factory.openSession();
			list = ss.selectList("student.stu_exam_list",parent.s.getStu_no());
			ar = makeArray(list);
			DefaultTableModel model = new DefaultTableModel(ar, c_name);
			table.setModel(model);
			table.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
			table.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox()));
			ss.close();
		}

		String[][] makeArray(List<ExFrameVO> list) {
			this.ar = null;

			if (list != null && !list.isEmpty()) {
				ar = new String[list.size()][c_name.length];
				int i = 0;
				
				for (ExFrameVO vo : list) { // 2차원 배열에 자원들을 넣기위한 반복문
					ar[i][0] = vo.getExf_no();
					ar[i][1] = vo.getExf_name();
					ar[i][2] = "확인";
					i++;
				}
			}

			return ar;
		}

		public String[][] selectExam() {
			SqlSession ss = parent.factory.openSession();

			list = ss.selectList("student.stu_exam_list",parent.s.getStu_no());

			ss.close();

			return makeArray(list);
		}
			// 클래스 Buttonrender를 생성하고, jbutton과 tablecellrender를 상속 받는다.
			class ButtonRenderer extends JButton implements TableCellRenderer {
				//생성자 buttonrender 생성하기
				public ButtonRenderer() {
					// 버튼을 불투명한 색깔로 변경시켜주기.
					setOpaque(true);
				}
				// implements하기.
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					setText(value == null ? "" : value.toString());
					return this;
				}
			}

			// JButton 에디터 클래스
			class ButtonEditor extends DefaultCellEditor {
				protected JButton button;
				private String label;
				private boolean clicked;
				ExFrameVO vo;
				public ButtonEditor(JCheckBox checkBox) {
					super(checkBox);
					button = new JButton("");
					button.setOpaque(true);
					
					// 버튼 클릭 이벤트
					button.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

			                int bt = table.getSelectedRow();
							new CorrectExam(CorrectTable.this,ar[bt][0]).setVisible(true);;

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
						
					}
					clicked = false;
					return label;
				}

				private List<QuestVO> getExamInfo() {
					SqlSession ss = factory.openSession();
					String exf_no = ar[table.getSelectedRow()][0];
					this.vo  = ss.selectOne("student.get_exf_one", exf_no);
					List<QuestVO> qvo = ss.selectList("student.get_quest_list", exf_no);
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


