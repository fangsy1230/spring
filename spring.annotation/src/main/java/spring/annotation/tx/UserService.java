package spring.annotation.tx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserDao dao;
	
	@Transactional
	public void insertUser(){
		dao.insert();
		//otherdao.other();
		System.out.println("插入完成");
		int i = 10/0;
	}
	
	
}
