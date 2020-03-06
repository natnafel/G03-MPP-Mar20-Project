package business;


import dataaccess.AccountRepository;


public class AccountService {
	AccountRepository acountRepo = new AccountRepository();
	public boolean validateUser(String username, String password)  {
		
		User userFromRepo = acountRepo.getUser(username, password);
		if(userFromRepo == null)
			return false;		
		return true;
	}

	
	public boolean CreateMember(LibraryMember member)  {	
		// logic for field validation here
		//if member.getMemberId() == null
		if(member==null)
			return false;
		return acountRepo.createMember(member)	;
	}

}
