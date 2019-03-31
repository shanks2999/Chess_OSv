package wrapper;

import org.apache.log4j.Logger;
import pl.art.lach.mateusz.javaopenchess.core.Game;

import java.util.HashMap;
import java.util.Map;
// Class containing static variables used by different classes.
public class StaticHeap {
    public static final Logger LOG4J = Logger.getLogger("SMAITH2");
    public static  Map<String, Game> mapMyGames = new HashMap<>();
}
