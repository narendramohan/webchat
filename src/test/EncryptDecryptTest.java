package test;

import com.chat.application.common.RandomPasswordGenerator;
import com.chat.application.common.RandomStringGenerator;
import com.chat.application.service.PasswordEncryptionService;
import com.chat.application.service.serviceImpl.PasswordEncryptionServiceImpl;

public class EncryptDecryptTest {
	public static void main(String args[]){
		PasswordEncryptionService ps = new PasswordEncryptionServiceImpl();
		System.out.println(ps.encrypt("M0nit0r123"));
		System.out.println(ps.decrypt("4JrwGaK0aOsLbsQln4YYlbI7cYabuS+C"));
		 int noOfCAPSAlpha = 1;
	        int noOfDigits = 1;
	        int noOfSplChars = 1;
	        int minLen = 8;
	        int maxLen = 12;
		System.out.println("Random String: "+RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits, noOfSplChars));
	}

}
