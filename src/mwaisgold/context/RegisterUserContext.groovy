package mwaisgold.context

import mwaisgold.persistance.BasicPersistor

/**
 * User: mwaisgold
 * Date: 12/12/13
 * Time: 12:34 AM 
 */
 
class RegisterUserContext extends LoggedContext {

    @Override
    Context executeContext(line) {
        BasicPersistor.saveNewPassword(loggedUser, line)
        println("Password created ok")
        new MenuContext(out: out, loggedUser: loggedUser)
    }
}
