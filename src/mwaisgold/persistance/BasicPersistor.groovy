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
        //def user = new User(userName: userName)
        User user = BasicPersistor.instance.users.find { it.userName == userName }
        if (user && user.isLoggedIn()){
            throw new IllegalArgumentException("User already taken")
        } else if (!user){
            user = new User(userName: userName)
            BasicPersistor.instance.users << user
        }
        user
    }

    static clear(){
        BasicPersistor.instance.users = [] as HashSet
        BasicPersistor.instance.rooms = [] as HashSet
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

    static synchronized removeUser(User user) {
        user.output = null //clean output
    }

    static saveNewPassword(User user, password) {
        user.isAnonymus = false
        user.password = password
    }

    static findUserByName(userName) {
        BasicPersistor.instance.users.find { it.userName == userName }
    }

    static saveNewRoomPassword(Room room, password) {
        room.password = password
    }
}
