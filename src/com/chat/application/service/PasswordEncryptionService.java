package com.chat.application.service;

public interface PasswordEncryptionService {
	public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) throws Exception;
	public byte[] getEncryptedPassword(String password, byte[] salt) throws Exception ;
	public byte[] generateSalt() throws Exception;
	
	public String encrypt(String keyWord);
	public String decrypt(String encryptedValue);
}
