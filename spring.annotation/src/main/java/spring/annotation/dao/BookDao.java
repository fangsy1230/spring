package spring.annotation.dao;

import org.springframework.stereotype.Repository;

@Repository
public class BookDao {
	
	@Override
	public String toString() {
		return "BookDao [label=" + label + "]";
	}

	private String label = "1";

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
