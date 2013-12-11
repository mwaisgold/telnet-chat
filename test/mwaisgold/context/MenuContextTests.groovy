package mwaisgold.context

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
}
