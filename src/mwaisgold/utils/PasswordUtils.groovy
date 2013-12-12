package mwaisgold.utils

/**
 * User: mwaisgold
 * Date: 12/12/13
 * Time: 2:44 AM 
 */

class PasswordUtils {

    static hashPassword(password){
        java.security.MessageDigest.getInstance("SHA-256")
                    .digest(password.getBytes("UTF-8")).encodeBase64().toString()
    }
}
