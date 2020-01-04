import java.util.ArrayList;
import java.util.List;

public class Tag {
    String body;
    List<Tag> children;
    Tag parent;
    int level;
    boolean selfClosing;

    Tag(String body, Tag parent, int level) {
        this.body = body;
        this.parent = parent;
        this.level = level;
        children = new ArrayList<>();
    }
}
