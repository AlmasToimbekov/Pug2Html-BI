import java.util.ArrayList;
import java.util.List;

public class TagTree {

    List<TagLevel> tagsTree;

    TagTree(String[] textArr) {
        tagsTree = new ArrayList<>();
        makeTagsTree(textArr);
    }

    void makeTagsTree(String[] textArr) {
        int rows = textArr.length;
        int[] countSpaces = new int[rows];
        int count;
        for (int i = 0; i < rows; i++) {
            count = 0;
            while (textArr[i].charAt(count) == ' ') count++;
            countSpaces[i] = count;
        }

        List<TagLevel> currentLevel = tagsTree;
        count = countSpaces[0];
        TagLevel parent = null;
        for (int i = 0; i < rows; i++) {
            if (countSpaces[i] > count) {
                count = countSpaces[i];
                parent = currentLevel.get(currentLevel.size() - 1);
                parent.children.add(new TagLevel(textArr[i], parent));
                currentLevel = parent.children;
            } else if (countSpaces[i] == count) {
                currentLevel.add(new TagLevel(textArr[i], parent));
            } else {
                parent = parent.parent;
                currentLevel = parent.children;
                count = countSpaces[i];
                currentLevel.add(new TagLevel(textArr[i], parent));
            }
        }
    }
}
