import java.util.ArrayList;
import java.util.List;

public class TagTree {

    List<Tag> tagsTree;

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

        List<Tag> currentLevel = tagsTree;
        count = countSpaces[0];
        Tag parent = null;
        for (int i = 0; i < rows; i++) {
            if (countSpaces[i] > count) {
                parent = currentLevel.get(currentLevel.size() - 1);
                currentLevel = parent.children;
            } else if (countSpaces[i] < count) {
                while (parent != null && parent.level >= countSpaces[i]) parent = parent.parent;
                currentLevel = parent == null ? tagsTree : parent.children;
            }
            count = countSpaces[i];
            currentLevel.add(new Tag(textArr[i].trim() + " ", parent, countSpaces[i]));
        }
    }
}
