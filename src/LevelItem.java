import java.util.ArrayList;
import java.util.List;

public class LevelItem {
    String body;
    List<LevelItem> children;
    LevelItem parent;
    int level;
    boolean selfClosing;
    boolean levelUp;

    LevelItem(String body, LevelItem parent, int level) {
        this.body = body;
        this.parent = parent;
        this.level = level;
        children = new ArrayList<>();
    }
}
