import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

//Aprašo bazinį URI visiems resursams.
@ApplicationPath("/")
//Java klasė aprašanti šakninius resursus ir tiekėjų klases
public class MyApplication extends Application{

    //Metodas grąžina netuščią klasių rinkinį, įtraukiamą į JAX-RS sistemą
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add( Resources.class );
        return h;
    }
}