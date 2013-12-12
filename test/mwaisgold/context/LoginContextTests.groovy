package mwaisgold.context

import mwaisgold.domain.User
import mwaisgold.persistance.BasicPersistor
import mwaisgold.utils.ScriptInterruptedException
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

    @Test
    void "Should ask for password on registered user"(){
        User user = BasicPersistor.addUser("John")
        BasicPersistor.saveNewPassword(user, "password")

        def validatePassContext = context.executeContext("John")
        assert validatePassContext in PasswordRequiredContext

        assert validatePassContext.executeContext("password") in MenuContext
    }

    @Test
    void "Should ask for password on registered user and fail on repeated invalid attepts"(){
        User user = BasicPersistor.addUser("John")
        BasicPersistor.saveNewPassword(user, "password")

        def validatePassContext = context.executeContext("John")
        assert validatePassContext in PasswordRequiredContext

        assert validatePassContext.executeContext("invalidpassword") in PasswordRequiredContext
        assert validatePassContext.executeContext("invalidpassword") in PasswordRequiredContext
        assert validatePassContext.executeContext("invalidpassword") in PasswordRequiredContext

        try{
            assert validatePassContext.executeContext("invalidpassword") in PasswordRequiredContext
            assert false //should have thrown exception
        } catch(ScriptInterruptedException e){
            assert true
        }
    }
}
