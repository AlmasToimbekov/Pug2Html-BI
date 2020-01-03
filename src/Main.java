public class Main {

    public static void main(String[] args) {
        boolean readFromComandLine = args.length != 0;
        if (args.length != 2){
            System.out.println("There must be 2 arguments: path to source file and new file's name");
        }

        String[] textArr;
        if (readFromComandLine) textArr = FileInteraction.getFileTextArr(args[0]);
        else textArr = FileInteraction.getFileTextArr("src/file.txt");

        // Building tags tree
        TagTree tagTree = new TagTree(textArr);

        // Parsing tags tree into string result
        StringBuilder sb = new StringBuilder();
        Parsing.inOrderTraverse(tagTree.tagsTree, sb);

        // Result output
        if (readFromComandLine) FileInteraction.writeToFile(args[1], sb.toString());
        else System.out.println(sb.toString());

    }
}