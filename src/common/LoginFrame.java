package common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Reader;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import root.RootFrame;

public class LoginFrame extends javax.swing.JFrame {

	/**
	 * Creates new form LoginFrame
	 */
	private LoginService loginService;

	public LoginFrame(SqlSessionFactory sqlSessionFactory) {
		this.loginService = new LoginService(sqlSessionFactory);
		initComponents();

	}

	private void initComponents() {	
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		this.setTitle("로그인");

		jLabel1 = new javax.swing.JLabel();
		jComboBox1 = new javax.swing.JComboBox<>();
		jTextField1 = new javax.swing.JTextField();
		jTextField2 = new javax.swing.JPasswordField();
		jButton1 = new javax.swing.JButton();
		this.setLocation(700, 300);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "관리자", "학생", "강사" }));

		jTextField1.setText("ID");
		jTextField2.setText("Password");
		
		ImageIcon icon = new ImageIcon("src/images/sist.png");
		Image image = icon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(image);
		JLabel imageLabel = new JLabel(scaledIcon);		
		
		jButton1.setText("로그인");
	    jLabel1.setText("쌍용교육센터");
	    jLabel1.setFont(new java.awt.Font("맑은 고딕", Font.BOLD, 20));
	    jLabel1.setForeground(new java.awt.Color(70, 130, 180));
	    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

	    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "관리자", "학생", "강사" }));
	    jComboBox1.setFont(new java.awt.Font("맑은 고딕", Font.PLAIN, 14));
	    jComboBox1.setToolTipText("로그인 역할을 선택하세요");

	    jTextField1.setFont(new java.awt.Font("Arial", Font.PLAIN, 14));
	   
	    jTextField1.setToolTipText("아이디를 입력하세요");
	    jTextField1.setBackground(new java.awt.Color(245, 245, 245)); // 연한 회색
	    jTextField1.setBorder(javax.swing.BorderFactory.createCompoundBorder(
	            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)),
	            javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)
	        ));

	    jTextField2.setFont(new java.awt.Font("맑은 고딕", Font.PLAIN, 14));
	   
	    jTextField2.setToolTipText("비밀번호를 입력하세요");
	    jTextField2.setBackground(new java.awt.Color(245, 245, 245)); 
	    jTextField2.setBorder(javax.swing.BorderFactory.createCompoundBorder(
	            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)),
	            javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)
	        ));

	    jButton1.setText("로그인");
	    jButton1.setFont(new java.awt.Font("맑은 고딕", Font.BOLD, 14));
	    jButton1.setBackground(new java.awt.Color(70, 130, 180));
	    jButton1.setForeground(java.awt.Color.WHITE);
	    jButton1.setToolTipText("로그인 버튼을 클릭하세요");
	    jButton1.setFocusPainted(false);
		
	    // 초기값(placeholder) 설정
        jTextField1.setForeground(java.awt.Color.GRAY);
        jTextField2.setForeground(java.awt.Color.GRAY);

        // FocusListener 추가
        jTextField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jTextField1.getText().equals("ID")) {
                    jTextField1.setText("");
                    jTextField1.setForeground(java.awt.Color.BLACK);  // 글씨색을 검정색으로 변경
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextField1.getText().isEmpty()) {
                    jTextField1.setText("ID");
                    jTextField1.setForeground(java.awt.Color.GRAY);  // 글씨색을 회색으로 변경
                }
            }
        });

        jTextField2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jTextField2.getText().equals("Password")) {
                    jTextField2.setText("");
                    jTextField2.setForeground(java.awt.Color.BLACK);  // 글씨색을 검정색으로 변경
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextField2.getText().isEmpty()) {
                    jTextField2.setText("Password");
                    jTextField2.setForeground(java.awt.Color.GRAY);  // 글씨색을 회색으로 변경
                }
            }
        });
	    
		jButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedRole = (String) jComboBox1.getSelectedItem();
				String id = jTextField1.getText();
				String password = new String(((JPasswordField) jTextField2).getPassword());

				// 간단한 조건 체크
				if (id.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(null, "ID와 Password를 입력해주세요.");
				} else {
					// 로그인 검증
					loginService.validateLogin(selectedRole, id, password,LoginFrame.this);
					// 로그인 성공 후 처리

					// 현재 로그인 창 닫기

				}
			}
		});
		jTextField1.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if (code == KeyEvent.VK_ENTER) {
					login();

				}
			}
		});
		jTextField2.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if (code == KeyEvent.VK_ENTER) {
					login();

				}
			}
		});
		jComboBox1.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if (code == KeyEvent.VK_ENTER) {
					login();

				}
			}
		});
		 javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		    getContentPane().setLayout(layout);
		    layout.setHorizontalGroup(
		        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
		            .addGroup(layout.createSequentialGroup()
		                .addContainerGap()
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
		                	.addComponent(imageLabel)
		                    .addComponent(jLabel1)
		                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addContainerGap())
		    );
		    layout.setVerticalGroup(
		        layout.createSequentialGroup()
		        .addGap(20)
		        .addComponent(imageLabel)
		            .addComponent(jLabel1)
		            .addGap(20)
		            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(10)
		            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(10)
		            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addGap(20)
		            .addComponent(jButton1)
		            .addGap(20)
		    );

		pack();
	}// </editor-fold>

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		try {
			Reader reader = Resources.getResourceAsReader("config/config.xml");
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

			java.awt.EventQueue.invokeLater(() -> {
				new LoginFrame(sqlSessionFactory).setVisible(true);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login() {
		String selectedRole = (String) jComboBox1.getSelectedItem();
		String id = jTextField1.getText();
		String password = new String(((JPasswordField) jTextField2).getPassword());

		// 간단한 조건 체크
		if (id.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "ID와 Password를 입력해주세요.");
		} else {
			// 로그인 검증
			loginService.validateLogin(selectedRole, id, password,this);
			// 로그인 성공 후 처리
			// 현재 로그인 창 닫기

		}
	}

	// Variables declaration - do not modify
	private javax.swing.JButton jButton1;
	private javax.swing.JComboBox<String> jComboBox1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	// End of variables declaration
}
