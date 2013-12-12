package mwaisgold.context

import mwaisgold.persistance.BasicPersistor

/**
 * User: mwaisgold
 * Date: 12/11/13
 * Time: 1:44 AM 
 */

class LoginContext extends AbstractContext {

    @Override
    Context executeContext(line) {
        if (line){
            try {
                def user = BasicPersistor.addUser(line)
                user.output = out
                if (user.isAnonymus){
                    println "Welcome $line!"
                    new MenuContext(out: out, loggedUser: user)
                } else {
                    println "User $line needs authentication"
                    new PasswordRequiredContext(out: out, loggedUser: user)
                }


            } catch (IllegalArgumentException e) {
                println "Sorry, name taken"
                askUser()
                this
            }
        } else {
            askUser()
            this
        }
    }

    def askUser() {
        println("Login Name?")
    }
}
