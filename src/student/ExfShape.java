package student;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import vo.QuestVO;

public class ExfShape {
    private JLabel text, l1, l2, l3, l4;
    private JRadioButton first, second, third, forth;
    private JPanel text_p, radio_p;

    public ExfShape(QuestVO vo, JPanel parentPanel) {
        // 패널 초기화
        text_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radio_p = new JPanel(new GridLayout(4, 2, 5, 5)); // 4행 2열의 그리드

        // 질문 텍스트
        String wrappedText = "<html><body style='width:300px;'>" + vo.getQ_text() + "</body></html>";
        text = new JLabel(wrappedText); // HTML 태그로 텍스트 감싸기
        text_p.add(text);
        // 각 옵션과 라디오 버튼 생성
        first = new JRadioButton();
        second = new JRadioButton();
        third = new JRadioButton();
        forth = new JRadioButton();

        l1 = new JLabel(vo.getFirst());
        l2 = new JLabel(vo.getSecond());
        l3 = new JLabel(vo.getThird());
        l4 = new JLabel(vo.getForth());

        // 라디오 버튼 그룹화
        ButtonGroup bg = new ButtonGroup();
        bg.add(first);
        bg.add(second);
        bg.add(third);
        bg.add(forth);

        // 옵션 추가
        radio_p.add(first);
        radio_p.add(l1);
        radio_p.add(second);
        radio_p.add(l2);
        radio_p.add(third);
        radio_p.add(l3);
        radio_p.add(forth);
        radio_p.add(l4);

        // 부모 패널에 추가
        parentPanel.setLayout(new GridLayout(2, 1)); // 부모 패널 레이아웃
        parentPanel.add(text_p);
        parentPanel.add(radio_p);
    }
}