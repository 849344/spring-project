package spring.security.model;

public class Candidates {
	private int Id;
	private String name;
	private int age;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [Id=" + Id + ", name=" + name + ", age=" + age + "]";
	}
	public Candidates(int id, String name, int age) {
		super();
		Id = id;
		this.name = name;
		this.age = age;
	}

}
