package mwaisgold.context

import mwaisgold.persistance.BasicPersistor
import org.junit.Before

/**
 * User: mwaisgold
 * Date: 12/11/13
 * Time: 1:14 PM 
 */
 
abstract class AbstractContextTest<T extends AbstractContext> {

    Class<T> resource
    T context

    AbstractContextTest(Class<T> resource){
        this.resource = resource
    }

    @Before
    void init(){
        BasicPersistor.clear()
        this.context = createResource()
    }

    def createResource() {
        def context = resource.newInstance()
        context.out = new StringWriter()
        context
    }
}
