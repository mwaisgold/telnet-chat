package mwaisgold.context

/**
 * User: mwaisgold
 * Date: 12/11/13
 * Time: 1:45 AM 
 */

abstract class AbstractContext implements Context {

    def out

    void println(ln){
        out.println(ln)
    }
}
