package mwaisgold.context

import mwaisgold.persistance.BasicPersistor
import org.junit.Before
import org.junit.Test

/**
 * User: mwaisgold
 * Date: 12/11/13
 * Time: 1:39 PM 
 */

class MenuContextTests extends AbstractContextTest {

    MenuContextTests() {
        super(MenuContext)
    }

    @Before
    void logUser(){
        def user = BasicPersistor.addUser("John")
        context.loggedUser = user
    }

    @Test
    void "Help should retrieve all possible options"(){
        context.executeContext("/help")
        assert context.out.toString().contains("/help")
        assert context.out.toString().contains("/rooms")
        assert context.out.toString().contains("/createRoom")
    }

    @Test
    void "Should be able to create a room"(){
        context.executeContext("/createRoom myRoom")
        assert context.out.toString().contains("Room created")

        context.executeContext("/rooms")
        assert context.out.toString().contains("* myRoom")
    }

    @Test
    void "Should be able to register a user"(){
        def registerContext = context.executeContext("/register")
        assert registerContext in RegisterUserContext

        registerContext.executeContext("password")
        assert registerContext.loggedUser.password == "password"
    }

    @Test
    void "Should be able to access a secured room"(){
        def room = BasicPersistor.addRoom("chat",context.loggedUser)
        BasicPersistor.saveNewRoomPassword(room, "password")

        def anotherUser = BasicPersistor.addUser("Mark")
        def menuContext = createResource()
        menuContext.loggedUser = anotherUser

        def passwordContext = menuContext.executeContext("/join chat")
        assert passwordContext in RoomPasswordRequiredContext

        assert passwordContext.executeContext("password") in RoomContext

    }

    @Test
    void "Should not be able to access a secured room with an invalid password"(){
        def room = BasicPersistor.addRoom("chat",context.loggedUser)
        BasicPersistor.saveNewRoomPassword(room, "password")

        def anotherUser = BasicPersistor.addUser("Mark")
        def menuContext = createResource()
        menuContext.loggedUser = anotherUser

        def passwordContext = menuContext.executeContext("/join chat")
        assert passwordContext in RoomPasswordRequiredContext

        3.times {
            assert passwordContext.executeContext("invalid") in RoomPasswordRequiredContext
        }

        assert passwordContext.executeContext("invalid") in MenuContext
    }


}
