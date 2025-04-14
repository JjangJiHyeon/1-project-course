package vo;

public class QuestVO {
	String q_no,
	q_text,
	q_answer,
	q_score,
	first,
	second,
	third,
	forth,
	score;
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	ExFrameVO exam_frame;
	public ExFrameVO getExam_frame() {
		return exam_frame;
	}

	public void setExam_frame(ExFrameVO exam_frame) {
		this.exam_frame = exam_frame;
	}

	public String getQ_no() {
		return q_no;
	}

	public void setQ_no(String q_no) {
		this.q_no = q_no;
	}

	public String getQ_text() {
		return q_text;
	}

	public void setQ_text(String q_text) {
		this.q_text = q_text;
	}

	public String getQ_answer() {
		return q_answer;
	}

	public void setQ_answer(String q_answer) {
		this.q_answer = q_answer;
	}

	public String getQ_score() {
		return q_score;
	}

	public void setQ_score(String q_score) {
		this.q_score = q_score;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public String getThird() {
		return third;
	}

	public void setThird(String third) {
		this.third = third;
	}

	public String getForth() {
		return forth;
	}

	public void setForth(String forth) {
		this.forth = forth;
	}
}
