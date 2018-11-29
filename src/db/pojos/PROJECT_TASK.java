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
import statics.ENUMS;

@Entity(name = "PROJECT_TASK")
public class PROJECT_TASK {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TASK_COD")
	private int taskCod;

	@Column(name = "TASK_TITLE")
	private String taskTitle;

	@Column(name = "TASK_TEXT")
	@Type(type = "text")
	private String task;

	@Column(name = "TASK_PONTUATION")
	private int taskPontuation;

	@Column(name = "TASK_CREATOR")
	private int taskCreator;

	@Column(name = "TASK_EXECUTOR")
	private int taskExecutor;

	@Column(name = "TASK_STATUS")
	private ENUMS.PROJECT_FRAMEWORK taskStatus;

	@Temporal(TemporalType.DATE)
	@Column(name = "TASK_DT_STARTED")
	private Date taskDateStart;

	public String getTask() {
		return task;
	}
	public void setTask(String string) {
		this.task = string;
	}

	public int getTaskPontuation() {
		return taskPontuation;
	}

	public void setTaskPontuation(int taskPontuation) {
		this.taskPontuation = taskPontuation;
	}

	public int getTaskCreator() {
		return taskCreator;
	}

	public void setTaskCreator(int taskCreator) {
		this.taskCreator = taskCreator;
	}

	public int getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(int taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public ENUMS.PROJECT_FRAMEWORK getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(ENUMS.PROJECT_FRAMEWORK taskStatus) {
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
