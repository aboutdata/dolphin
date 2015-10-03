package com.sabsari.dolphin.core.value;

public interface ValueGenerator {
	public String generateGroupKey();
	public String generateClientId();
	public String generateUserKey();
	
	public String generateEmailVerificationValue();
    public String generateAccessToken();
    public String generateRefreshToken(); 
    public String generateClientSecret();    
    public String generateRandomPassword();
}
