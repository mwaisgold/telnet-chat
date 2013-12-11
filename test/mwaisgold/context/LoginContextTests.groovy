package mwaisgold.context

import org.junit.Test

/**
 * User: mwaisgold
 * Date: 12/11/13
 * Time: 1:14 PM 
 */

class LoginContextTests extends AbstractContextTest {


    LoginContextTests() {
        super(LoginContext)
    }

    @Test
    void "Should ask for userName on login"(){
        context.executeContext(null)
        assert context.out.toString() == "Login Name?\n"
    }

    @Test
    void "Should successfully welcome a new user"(){
        def newContext = context.executeContext("John")
        assert context.out.toString() == "Welcome John!\n"
        assert newContext in MenuContext
    }

    @Test
    void "Should warn on duplicated user"(){
        context.executeContext("John")
        assert context.out.toString() == "Welcome John!\n"

        def anotherContext = createResource()
        assert anotherContext.executeContext("John") in LoginContext
        assert anotherContext.out.toString().contains( "Sorry, name taken")
    }
}
