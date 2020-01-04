import java.util.List;

public class Parsing {
    static void inOrderTraverse(List<LevelItem> tagLevels, StringBuilder sb) {
        for (int i = 0; i < tagLevels.size(); i++) {
            currentLine++;
            LevelItem currentItem = tagLevels.get(i);
            if (currentItem.levelUp) currentLine++;
            if (tagLevels.get(i).body.trim().length() == 0) {
                sb.append('\n');
                continue;
            }
            String tag = currentItem.body;
            String rest = "";
            if (!openedParenth) {
                int delimiter = getFirstWordDelim(currentItem.body);
                tag = currentItem.body.substring(0, delimiter);
                rest = currentItem.body.substring(delimiter);
            }
            currentItem.selfClosing = isSelfClosing(tag);
            if (!selfClosing) selfClosing = currentItem.selfClosing;
            parseOpeningTags(sb, tag, rest);

            inOrderTraverse(tagLevels.get(i).children, sb);

            if (!openedParenth && !rest.equals("") && !currentItem.selfClosing) parseClosingTags(sb, tag);
        }
    }

    private static boolean openedParenth = false;
    private static boolean selfClosing = false;
    private static int currentLine = 0;
    static void parseOpeningTags(StringBuilder sb, String tag, String rest) {
        if (openedParenth) {
            parseAttributes(tag, sb);
        } else {
            sb.append('<');
            if (tag.equals("doctype")) {
                sb.append("!DOCTYPE");
                sb.append(rest, 0, rest.length() - 1);
                sb.append(">");
                return;
            }
            sb.append(tag);
            if (rest.equals(" ")) {
                if (selfClosing) sb.append(" /");
                sb.append(">\n");
            }
            else if (rest.charAt(0) == '(') {
                openedParenth = true;
                sb.append(' ');
                parseAttributes(rest.substring(1), sb);
            } else {
                sb.append('>');
                sb.append(rest, 1, rest.length() - 1);
            }
        }
    }

    static void parseAttributes(String tag, StringBuilder sb) {
        if (tag.equals(" ")) return;
        for (int i = 0; i < tag.length(); i++) {
            if (tag.charAt(i) == '(') System.out.println("Unexpected text '(' on line " + currentLine);
            else if (tag.charAt(i) == '\'') sb.append('"');
            else if (tag.charAt(i) == ')') {
                openedParenth = false;
                String rest = tag.substring(i + 1);
                if (selfClosing) {
                    sb.append(" />\n");
                    if (!rest.equals(" ")) System.out.println("Unexpected text after self closing tag on line " + currentLine);
                    selfClosing = false;
                    return;
                }
                sb.append('>');

                if (rest.charAt(0) != ' ') System.out.println("Unexpected text '" + rest.charAt(0) + "'" + " on line " + currentLine);
                sb.append(' ');
                sb.append(rest.substring(1));
                sb.append('\n');
                return;
            }
            else sb.append(tag.charAt(i));
        }
    }

    static void parseClosingTags(StringBuilder sb, String tag) {
        if (!tag.equals("doctype")) {
            sb.append("</");
            sb.append(tag);
            sb.append(">");
        }
        sb.append("\n");
    }

    static int getFirstWordDelim(String string) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            char delim = string.charAt(i);
            if (delim == ' ' || delim == '.' || delim == '(' || delim == ')') return count;
            count++;
        }
        return count;
    }

    static boolean isSelfClosing(String tag) {
        switch (tag) {
            case "area":
            case "base":
            case "br":
            case "col":
            case "embed":
            case "hr":
            case "img":
            case "input":
            case "link":
            case "meta":
            case "param":
            case "source":
            case "track":
            case "wbr":
            case "command":
            case "keygen":
            case "menuitem": return true;
            default: return false;
        }
    }
}
