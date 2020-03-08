package business;


import dataaccess.AccountRepository;


public class AccountService {
	AccountRepository accountRepository = new AccountRepository();
	public User validateUser(String username, String password)  {
		return accountRepository.getUser(username, password);
	}


	
	public boolean createMember(LibraryMember member)  {
		// logic for field validation here
		//if member.getMemberId() == null
		if(member==null)
			return false;
		return accountRepository.createMember(member)	;
	}

	public LibraryMember findMemberByMemberId(String memberId){
	    return accountRepository.findMemberByMemberId(memberId);
    }

}
