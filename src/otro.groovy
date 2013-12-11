import mwaisgold.ScriptContext
import mwaisgold.context.LoginContext

import java.util.concurrent.atomic.AtomicInteger

/**
 * User: mwaisgold
 * Date: 12/11/13
 * Time: 1:14 AM 
 */
AtomicInteger connectionSequence = new AtomicInteger(0)
//def connectionId
def threadId = Thread.currentThread().getId()

if (init){
    connectionId = connectionSequence.incrementAndGet()

    ScriptContext.connections[threadId] = [context: new LoginContext(out: out)]
    println "Welcome to the XYZ chat server"
}

//ScriptContext.connections[threadId].context.out.println threadId

Socket socket = socket
String line = line

ScriptContext.connections[threadId].context = ScriptContext.connections[threadId].context.executeContext(line)
ScriptContext.connections[threadId].context.out.print "=> "
ScriptContext.connections[threadId].context.out.flush()
return null
