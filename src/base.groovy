import mwaisgold.utils.GroovySocketServer

/**
 * User: mwaisgold
 * Date: 12/11/13
 * Time: 2:05 AM 
 */
new GroovySocketServer(
        new GroovyShell(this.class.classLoader),  // evaluator
        true,
        "otro.groovy",             // script to return
        true,               // return result to client
        8888)