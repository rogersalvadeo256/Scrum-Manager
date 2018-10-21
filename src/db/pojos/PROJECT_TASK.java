package db.pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import javafx.scene.text.Text;

@Entity(name = "PROJECT_STORIES")
public class PROJECT_TASK {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TASK_COD")
	private int taskCod;

	@Column(name = "TASK_TITLE")
	private String taskTitle;

	@Column(name = "TASK_TEXT")
	@Type(type = "text")
	private Text task;

	@Column(name = "TASK_PONTUATION")
	private int taskPontuation;

	@Column(name = "TASK_CREATOR")
	private USER_PROFILE taskCreator;

	@Column(name = "TASK_EXECUTOR")
	private USER_PROFILE taskExecutor;

	@Column(name = "TASK_STATUS")
	private String taskStatus;

	@Temporal(TemporalType.DATE)
	@Column(name = "TASK_DT_STARTED")
	private Date taskDateStart;

	public Text getTask() {
		return task;
	}

	public void setTask(Text task) {
		this.task = task;
	}

	public int getTaskPontuation() {
		return taskPontuation;
	}

	public void setTaskPontuation(int taskPontuation) {
		this.taskPontuation = taskPontuation;
	}

	public USER_PROFILE getTaskCreator() {
		return taskCreator;
	}

	public void setTaskCreator(USER_PROFILE taskCreator) {
		this.taskCreator = taskCreator;
	}

	public USER_PROFILE getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(USER_PROFILE taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Date getTaskDateStart() {
		return taskDateStart;
	}

	public void setTaskDateStart(Date taskDateStart) {
		this.taskDateStart = taskDateStart;
	}

	public int getTaskCod() {
		return taskCod;
	}

	public void setTaskCod(int taskCod) {
		this.taskCod = taskCod;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}
}
