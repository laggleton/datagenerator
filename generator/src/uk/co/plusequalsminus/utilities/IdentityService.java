package uk.co.plusequalsminus.utilities;

import java.util.ArrayList;

public class IdentityService {
	
	private static IdentityService instance;
	
	private ArrayList<String> identityList = new ArrayList<String>();

	private IdentityService() {}
	
	public static IdentityService getInstance() {
		if (null == instance) {
			instance = new IdentityService();
		}
		return instance;
	}
	
	public int getIdentity(String primaryKey) {
		if (!identityList.contains(primaryKey)) {
			identityList.add(primaryKey);
		}
		return (identityList.indexOf(primaryKey));
	}
	
}

