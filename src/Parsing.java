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

            parseOpeningTags(sb, tag, rest);

            inOrderTraverse(tagLevels.get(i).children, sb);

            if (!openedParenth && !rest.equals("")) parseClosingTags(sb, tag);
        }
    }

    static boolean openedParenth = false;
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
            if (rest.equals(" ")) sb.append(">\n");
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
                sb.append('>');

                String rest = tag.substring(i + 1);
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
}
