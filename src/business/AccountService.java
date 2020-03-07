package business;


import dataaccess.AccountRepository;


public class AccountService {
	AccountRepository acountRepo = new AccountRepository();
	public User validateUser(String username, String password)  {
		return acountRepo.getUser(username, password);
	}

	
	public boolean CreateMember(LibraryMember member)  {	
		// logic for field validation here
		//if member.getMemberId() == null
		if(member==null)
			return false;
		return acountRepo.createMember(member)	;
	}

}
