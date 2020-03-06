package business;


import dataaccess.AccountRepository;


public class AccountService {
	AccountRepository acountRepo = new AccountRepository();
	public boolean validateUser(String username, String password) throws LibrarySystemException {
		
		User userFromRepo = acountRepo.getUser(username, password);
		if(userFromRepo == null)
			return false;		
		return true;
	}

	
	public boolean CreateMember(LibraryMember member) throws LibrarySystemException {	
		// logic for field validation here
		//if member.getMemberId() == null
		if(member==null)
			throw new LibrarySystemException();
		return acountRepo.createMember(member)	;
	}

}
