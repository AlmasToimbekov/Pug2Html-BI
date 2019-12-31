import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("There must be 2 arguments: path to source file and new file's name");
        }
//        String[] textArr = FileInteraction.getFileTextArr("./file.txt");
        String[] textArr = FileInteraction.getFileTextArr(args[0]);
        TagTree tagTree = new TagTree(textArr);

        StringBuilder sb = new StringBuilder();
        inOrderTraverse(tagTree.tagsTree, sb);

        System.out.println(sb.toString());
//        FileInteraction.writeToFile("./output.txt", sb.toString());
        FileInteraction.writeToFile(args[1], sb.toString());
    }

    static void inOrderTraverse(List<TagLevel> tagLevels, StringBuilder sb) {
        for (int i = 0; i < tagLevels.size(); i++) {
            TagLevel currentTag = tagLevels.get(i);
            int delimiter = getFirstWordDelim(currentTag.body);
            String tag = currentTag.body.substring(0, delimiter);
            String rest = currentTag.body.substring(delimiter);

            sb.append("<");
            sb.append(tag);
            sb.append(">");
            if (!rest.equals(" ")) sb.append(rest);
            sb.append("\n");

            inOrderTraverse(tagLevels.get(i).children, sb);
            sb.append("</");
            sb.append(tag);
            sb.append(">");
            sb.append("\n");
        }
    }

    static int getFirstWordDelim(String string) {
        int count = 0;
        boolean wordStarted = false;
        for (int i = 0; i < string.length(); i++) {
            if (!wordStarted && string.charAt(i) != ' ') wordStarted = true;
            else if(wordStarted) {
                char delim = string.charAt(i);
                if (delim == ' ' || delim == '.' || delim == '(') return count;
            }
            count++;
        }
        return count;
    }
}