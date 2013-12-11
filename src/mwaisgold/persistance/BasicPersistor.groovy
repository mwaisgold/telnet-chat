package mwaisgold.persistance

import mwaisgold.domain.Room
import mwaisgold.domain.User

/**
 * User: mwaisgold
 * Date: 12/11/13
 * Time: 2:31 AM 
 */

@Singleton
class BasicPersistor {

    private users = [] as HashSet
    private rooms = [] as HashSet

    static synchronized addUser(userName){
        def user = new User(userName: userName)
        if (user in BasicPersistor.instance.users){
            throw new IllegalArgumentException("User already taken")
        } else {
            BasicPersistor.instance.users << user
            return user
        }
    }

    static clear(){
        BasicPersistor.instance.users = []
    }

    static synchronized addRoom(roomName, user){
        def room = new Room(name: roomName, creator: user)
        if (room in BasicPersistor.instance.rooms){
            throw new IllegalArgumentException("Room already exists")
        } else {
            BasicPersistor.instance.rooms << room
            return room
        }
    }

    static joinRoom(roomName, user) {
        Room room = BasicPersistor.instance.rooms.find { it.name == roomName }
        if (!room) throw new IllegalArgumentException("No room found")
        room.currentUsers << user
        room
    }

    static synchronized removeUser(user) {
        BasicPersistor.instance.users.remove(user)
    }
}
