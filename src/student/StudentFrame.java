package student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Reader;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import common.LoginFrame;
import vo.StuVO;

public class StudentFrame extends javax.swing.JFrame {

	SqlSessionFactory factory;

	JTable table;

	private String[][] data = {};
	private String[] columnNames = { "과목명", "시험명", "시험현황" };

	StuVO s;
	private JButton bt_apply, logout;
	JPanel north_p, south_p;
	JLabel stu_info;
	Font buttonFont = new Font("맑은 고딕", Font.BOLD, 12);

	public StudentFrame(StuVO s) {
		this.s = s;
		initComponents();
	}

	private void initComponents() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		this.setTitle("학생 관리 프로그램 - 학생");

		jDialog1 = new javax.swing.JDialog();
		jPanel4 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		bt_apply = new JButton("신청");
		stu_info = new JLabel();
		north_p = new JPanel();
		south_p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		logout = new JButton("로그아웃");
		dbConnect();
		this.setLocation(500, 250);
		stu_info.setForeground(new Color(70, 130, 180)); // 글자 색을 흰색으로 설정
		stu_info.setHorizontalAlignment(SwingConstants.CENTER); // 텍스트 중앙 정렬
		stu_info.setVerticalAlignment(SwingConstants.CENTER); // 세로로도 중앙 정렬
		stu_info.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // 여백 추가
		stu_info.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 폰트 크기 좀 더 크게 설정
		stu_info.setText("<html><font size='5'>" + s.getStu_name() + "의 학습정보</font></html>"); // HTML로 크기 조정 가능
		ImageIcon icon = new ImageIcon("src/images/sist.png");
		Image image = icon.getImage().getScaledInstance(70, 55, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(image);
		JLabel imageLabel = new JLabel(scaledIcon);
		north_p.setLayout(new BorderLayout());
		north_p.add(imageLabel);
		north_p.add(stu_info, BorderLayout.SOUTH);
		north_p.setBackground(Color.white);
		north_p.setPreferredSize(new java.awt.Dimension(200, 80)); // 높이 조정
		north_p.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(70, 130, 180))); // 하단 경계선 추가

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		// 왼쪽 패널 생성
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBackground(Color.white);
		// 라벨 추가
		leftPanel.add(north_p, BorderLayout.NORTH);

		// 버튼 추가
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new java.awt.GridLayout(3, 1, 5, 5)); // 버튼 간 간격 설정
		buttonPanel.add(jButton1);
		buttonPanel.add(jButton2);
		buttonPanel.add(bt_apply);
		buttonPanel.setBackground(Color.white);
		leftPanel.add(buttonPanel, BorderLayout.CENTER);

		table = new javax.swing.JTable();
		
		// 테이블 비활성화
		table.setDefaultEditor(Object.class, null);
		
		jScrollPane2.setViewportView(table);
		table.getTableHeader().setBackground(Color.white);
		table.setGridColor(new Color(70, 130, 180));
		// 테이블 패널
		JScrollPane tableScrollPane = new JScrollPane(table);

		// jPanel4 레이아웃 변경
		jPanel4.setLayout(new FlowLayout(FlowLayout.LEADING));
		jPanel4.add(leftPanel);
		jPanel4.setBackground(Color.white);

		jButton1.setText("시험");
		jButton1.setFont(buttonFont);
		jButton1.setBackground(new Color(70, 130, 180));
		jButton1.setForeground(Color.WHITE);
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new SetExam(StudentFrame.this, table);
			}
		});
		logout.setFont(buttonFont);
		logout.setBackground(new Color(255, 69, 58));
		logout.setForeground(Color.WHITE);
		south_p.add(logout);
		south_p.setBackground(Color.white);
		bt_apply.setFont(buttonFont);
		bt_apply.setBackground(new Color(70, 130, 180));
		bt_apply.setForeground(Color.WHITE);
		jButton2.setText("정오표");
		jButton2.setFont(buttonFont);
		jButton2.setBackground(new Color(70, 130, 180));
		jButton2.setForeground(Color.WHITE);
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new CorrectTable(StudentFrame.this, table);
			}
		});
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				ImageIcon image = new ImageIcon("src/images/warning-sign.png");
				Image img = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(img);
				int yes = JOptionPane.showConfirmDialog(tableScrollPane, "로그아웃 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, icon);
				if (yes == JOptionPane.YES_OPTION) {
					new LoginFrame(factory).setVisible(true);
					dispose();
				}

			}
		});
		bt_apply.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ApplySubject(StudentFrame.this);
			}
		});

		this.add(south_p, BorderLayout.SOUTH);
		this.add(north_p, BorderLayout.NORTH);
		this.add(tableScrollPane, BorderLayout.CENTER);
		this.add(jPanel4, BorderLayout.WEST);

		pack();
	}

	private void dbConnect() {
		Reader r;
		try {
			r = Resources.getResourceAsReader("config/config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			factory = builder.build(r);
			r.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JDialog jDialog1;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JScrollPane jScrollPane2;
}