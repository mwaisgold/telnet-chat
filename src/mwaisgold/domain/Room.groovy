package mwaisgold.domain

/**
 * User: mwaisgold
 * Date: 12/11/13
 * Time: 1:07 PM 
 */

class Room {

    String name
    String password

    User creator

    def currentUsers = []

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false
        Room room = (Room) o
        if (name != room.name) return false
        return true
    }

    int hashCode() {
        return name.hashCode()
    }

    def sendMessage(message, loggedUser) {
        currentUsers.each { User it ->
            if (it != loggedUser){
                it.output.print("\b\b\b")
                it.output.println(message)
                it.output.print("=> ")
                it.output.flush()
            }
        }
    }
}
