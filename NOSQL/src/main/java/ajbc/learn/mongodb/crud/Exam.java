package ajbc.learn.mongodb.crud;

public class Exam {

	private String topic;
	private int grade;

	public Exam(String topic, int grade) {
		this.topic = topic;
		this.grade = grade;
	}

	public Exam() {
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getScore() {
		return grade;
	}

	public void setScore(int score) {
		this.grade = score;
	}

	@Override
	public String toString() {
		return "Exam [topic=" + topic + ", score=" + grade + "]";
	}

}
