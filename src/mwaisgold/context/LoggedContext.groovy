package mwaisgold.context

import mwaisgold.domain.User

/**
 * User: mwaisgold
 * Date: 12/11/13
 * Time: 2:38 AM 
 */
 
abstract class LoggedContext extends AbstractContext {

    User loggedUser
}
