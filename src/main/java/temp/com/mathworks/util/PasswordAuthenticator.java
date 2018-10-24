   package temp.com.mathworks.util;

   import javax.mail.Authenticator;
   import javax.mail.PasswordAuthentication;


   public class PasswordAuthenticator
     extends Authenticator
   {
     String username;
     String password;

     public PasswordAuthenticator(String paramString1, String paramString2)
     {
       this.username = paramString1;
       this.password = paramString2;
     }

     public PasswordAuthentication getPasswordAuthentication() {
       return new PasswordAuthentication(this.username, this.password);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\PasswordAuthenticator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */