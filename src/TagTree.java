import java.util.ArrayList;
import java.util.List;

public class TagTree {

    List<LevelItem> tagsTree;

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
            while (textArr[i].length() > 0 && textArr[i].charAt(count) == ' ') count++;
            countSpaces[i] = count;
        }

        List<LevelItem> currentLevel = tagsTree;
        count = countSpaces[0];
        LevelItem parent = null;
        LevelItem prev = null;
        for (int i = 0; i < rows; i++) {
            if (countSpaces[i] > count) {
                parent = currentLevel.get(currentLevel.size() - 1);
                currentLevel = parent.children;
            } else if (countSpaces[i] < count) {
                while (parent != null && parent.level >= countSpaces[i]) parent = parent.parent;
                currentLevel = parent == null ? tagsTree : parent.children;
            }
            count = countSpaces[i];
            if (textArr[i].trim().startsWith(")")) {
                prev.body += textArr[i].trim() + " ";
                // Mark deleted line
                countSpaces[i] = -1;
            } else {
                LevelItem newTag = new LevelItem(textArr[i].trim() + " ", parent, countSpaces[i]);
                currentLevel.add(newTag);
                prev = newTag;
                if ( i > 0 && countSpaces[i - 1] == - 1) newTag.levelUp = true;
            }
        }
    }
}
