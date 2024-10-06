package spring.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="turf")
public class Turf {

	@Id
	private int code;
	private String name;
	private int lenght;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLenght() {
		return lenght;
	}
	public void setLenght(int lenght) {
		this.lenght = lenght;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getBreath() {
		return breath;
	}
	public void setBreath(int breath) {
		this.breath = breath;
	}
	private int breath;
}
