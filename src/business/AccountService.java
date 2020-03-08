package business;


import dataaccess.AccountRepository;


public class AccountService {
	private AccountRepository accountRepository = new AccountRepository();
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

    public void createMember(String firstName, String lastName, String memberId, String telephone, String city, String state, String zip, String street){
	    Address address = new Address(street, city, state, zip);
	    LibraryMember member = new LibraryMember(memberId, firstName, lastName, telephone, address);
	    accountRepository.createMember(member);
    }

}
