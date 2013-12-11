package mwaisgold.domain

/**
 * User: mwaisgold
 * Date: 12/11/13
 * Time: 1:08 PM 
 */

class User {

    String userName
    Boolean isAnonymus = true
    String password = ""

    Room currentRoom
    def output //Current out to print the message

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false
        User user = (User) o
        if (userName != user.userName) return false
        return true
    }

    int hashCode() {
        return userName.hashCode()
    }
}
