package vo;

import java.util.ArrayList;

public class ExFrameVO {
	private String exf_no,
	exf_name,
	prof_no,
	sub_no,
	score;
	public ArrayList<QuestVO> getQ() {
		return q;
	}

	public void setQ(ArrayList<QuestVO> q) {
		this.q = q;
	}

	ArrayList<QuestVO> q;

	public String getExf_no() {
		return exf_no;
	}

	public void setExf_no(String exf_no) {
		this.exf_no = exf_no;
	}

	public String getExf_name() {
		return exf_name;
	}

	public void setExf_name(String exf_name) {
		this.exf_name = exf_name;
	}

	public String getProf_no() {
		return prof_no;
	}

	public void setProf_no(String prof_no) {
		this.prof_no = prof_no;
	}

	public String getSub_no() {
		return sub_no;
	}

	public void setSub_no(String sub_no) {
		this.sub_no = sub_no;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
}
