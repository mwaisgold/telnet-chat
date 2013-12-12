package mwaisgold.context

import mwaisgold.domain.Room
import mwaisgold.persistance.BasicPersistor

/**
 * User: mwaisgold
 * Date: 12/12/13
 * Time: 1:15 AM 
 */
 
class SecureRoomContext extends LoggedContext {

    Room room

    @Override
    Context executeContext(line) {
        BasicPersistor.saveNewRoomPassword(room, line)
        println("Password created ok")
        new RoomContext(out: out, loggedUser: loggedUser, room: room)
    }
}
