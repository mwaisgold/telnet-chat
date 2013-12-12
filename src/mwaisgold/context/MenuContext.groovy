package mwaisgold.context

import mwaisgold.persistance.BasicPersistor
import mwaisgold.utils.ScriptInterruptedException

/**
 * User: mwaisgold
 * Date: 12/11/13
 * Time: 2:37 AM 
 */
 
class MenuContext extends LoggedContext {

    @Override
    Context executeContext(line) {
        switch (line){
            case ~$//help/$: printHelp()
                break
            case ~$//rooms/$: listRooms()
                break
            case ~$//createRoom .*/$: createRoom(line)
                break
            case ~$//join .*/$: return joinRoom(line)
            case ~$//register/$: return registerUser()
            case ~$//quit/$: quit()

        }

        this
    }

    Context registerUser() {
        println("Enter new password for user:")
        new RegisterUserContext(out: out, loggedUser: loggedUser)
    }

    def quit() {
        BasicPersistor.removeUser(loggedUser)
        throw new ScriptInterruptedException()
    }

    def joinRoom(def line) {
        def split = line.split(" ")
        if (split.size() != 2){
            println("Invalid parameters. Should be /createRoom \$ROOM_NAME")
            return this
        }

        def roomName = split.last()
        def room = BasicPersistor.joinRoom(roomName, loggedUser)

        if (room.creator != loggedUser && room.password){
            println "Enter password for room"
            new RoomPasswordRequiredContext(out: out, loggedUser: loggedUser, room: room)
        } else {
            println("entering room: $roomName")
            room.currentUsers.each {
                println " * $it.userName" + (it == loggedUser ? " (** this is you)" : "")
            }
            room.sendMessage("* new user joined chat: $loggedUser.userName", loggedUser)
            return new RoomContext(room: room, loggedUser: loggedUser, out: out)
        }

    }

    def listRooms() {
        println("Listing rooms")
        BasicPersistor.instance.rooms.each {
            println " * $it.name"
        }
    }

    def createRoom(line){
        //Validate input
        def split = line.split(" ")
        if (split.size() != 2){
            println("Invalid parameters. Should be /createRoom \$ROOM_NAME")
            return this
        }

        def roomName = split.last()
        BasicPersistor.addRoom(roomName, loggedUser)
        println("Room created")
    }

    void printHelp() {
        println "/help shows this message"
        println "/rooms shows available room chats"
        println "/createRoom \$ROOM_NAME creates a room"
        println "/join \$ROOM_NAME enters a room"
        println "/quit Closes chat"
    }
}
