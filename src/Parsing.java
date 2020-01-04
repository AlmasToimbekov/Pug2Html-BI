import java.util.List;

public class Parsing {
    static void inOrderTraverse(List<Tag> tagLevels, StringBuilder sb) {
        for (int i = 0; i < tagLevels.size(); i++) {
            Tag currentTag = tagLevels.get(i);
            String tag = currentTag.body;
            String rest = "";
            if (!openedParenth) {
                int delimiter = getFirstWordDelim(currentTag.body);
                tag = currentTag.body.substring(0, delimiter);
                rest = currentTag.body.substring(delimiter);
            }
            currentTag.selfClosing = isSelfClosing(tag);
            if (!selfClosing) selfClosing = currentTag.selfClosing;
            parseOpeningTags(sb, tag, rest);

            inOrderTraverse(tagLevels.get(i).children, sb);

            if (!openedParenth && !rest.equals("") && !currentTag.selfClosing) parseClosingTags(sb, tag);
        }
    }

    private static boolean openedParenth = false;
    private static boolean selfClosing = false;
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
            if (tag.charAt(i) == '(') System.out.println("Unexpected text '(");
            else if (tag.charAt(i) == '\'') sb.append('"');
            else if (tag.charAt(i) == ')') {
                openedParenth = false;
                String rest = tag.substring(i + 1);
                if (selfClosing) {
                    sb.append(" />\n");
                    if (!rest.equals(" ")) System.out.println("Unexpected text after self closing tag");
                    selfClosing = false;
                    return;
                }
                sb.append('>');

                if (rest.charAt(0) != ' ') System.out.println("Unexpected text '" + rest.charAt(0) + "'");
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
