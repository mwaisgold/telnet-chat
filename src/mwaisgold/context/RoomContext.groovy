package mwaisgold.context

import mwaisgold.domain.Room
import mwaisgold.persistance.BasicPersistor

/**
 * User: mwaisgold
 * Date: 12/11/13
 * Time: 2:26 PM 
 */
 
class RoomContext extends LoggedContext {

    Room room

    static emoticones = [
            '/smile': ':)',
            '/cry': ':\'(',
            '/surprise': ':O'
    ]

    @Override
    Context executeContext(line) {
        switch (line){
            case ~$//help/$: printHelp()
                break
            case ~$//leave/$: return leaveRoom()
            case ~$//emoticons/$: showEmoticons()
                break
            case ~$//secure/$: return secureRoom()
                break
            default:
                def emoticon = emoticones[line]
                if (emoticon)
                    chat(emoticon)
                 else
                    chat(line)
        }

        this
    }

    def secureRoom() {
        if (room.creator == loggedUser){
            println("Enter new password for this room:")
            new SecureRoomContext(out: out, loggedUser: loggedUser, room: room)
        } else {
            println "You are not allowed to do this operation"
            this
        }
    }

    def showEmoticons() {
        println "Available emoticones (adding more on next release :D )"
        emoticones.each { k,v ->
            println(k)
        }
    }

    def leaveRoom() {
        room.currentUsers.remove(loggedUser)
        room.sendMessage("* user has left chat: $loggedUser.userName", loggedUser)
        new MenuContext(out: out, loggedUser: loggedUser)
    }

    Context chat(message) {
        room.sendMessage("$loggedUser.userName: $message", loggedUser)

        this
    }

    void printHelp() {
        println "/help shows this message"
        println "/emoticons shows available emoticons"
        println "/leave leaves this room"
        println "/kick \$USER_NAME kicks user from room"
    }
}
