import java.util.ArrayList;
import java.util.List;

public class TagLevel {
    String body;
    List<TagLevel> children;
    TagLevel parent;

    TagLevel() {
        children = new ArrayList<>();
    }

    TagLevel(String body) {
        this.body = body;
        children = new ArrayList<>();
    }

    TagLevel(String body, TagLevel parent) {
        this.body = body;
        this.parent = parent;
        children = new ArrayList<>();
    }
}
