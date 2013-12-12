package mwaisgold.context

/**
 * User: mwaisgold
 * Date: 12/12/13
 * Time: 12:46 AM 
 */
 
class PasswordRequiredContext extends LoggedContext{

    private static final int MAX_INVALID_ATTEMPTS = 3
    def attempts = 0

    @Override
    Context executeContext(line) {
        if (loggedUser.password == line){
            println "Welcome back $loggedUser.userName!"
            new MenuContext(out: out, loggedUser: loggedUser)
        } else {
            attempts++
            println "Invalid password!"
            if (attempts > MAX_INVALID_ATTEMPTS){
                new MenuContext(out: out, loggedUser: loggedUser).quit()
            }
            this
        }
    }
}
