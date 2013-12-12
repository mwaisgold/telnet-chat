package mwaisgold.context

import mwaisgold.domain.Room
import mwaisgold.utils.PasswordUtils

/**
 * User: mwaisgold
 * Date: 12/12/13
 * Time: 1:21 AM 
 */
 
class RoomPasswordRequiredContext extends LoggedContext{
    private static final int MAX_INVALID_ATTEMPTS = 3
    def attempts = 0
    Room room

    @Override
    Context executeContext(line) {
        if (room.password == PasswordUtils.hashPassword(line)){
            println("entering room: $room.name")
            room.currentUsers.each {
                println " * $it.userName" + (it == loggedUser ? " (** this is you)" : "")
            }
            room.sendMessage("* new user joined chat: $loggedUser.userName", loggedUser)
            return new RoomContext(room: room, loggedUser: loggedUser, out: out)
        } else {
            attempts++
            println "Invalid password!"
            if (attempts > MAX_INVALID_ATTEMPTS){
                println "Maximum attempts quantity exceeded"
                room.currentUsers.remove(loggedUser)
                return new MenuContext(out: out, loggedUser: loggedUser)
            }
            this
        }
    }
}
