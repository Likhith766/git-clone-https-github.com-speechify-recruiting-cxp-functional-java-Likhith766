import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import org.xml.sax.InputSource;

class SSMLNode {
    String tag;
    java.util.Map<String, String> attributes;
    String text;
    java.util.List<SSMLNode> children;

    SSMLNode(String tag, NamedNodeMap attrs, String text) {
        this.tag = tag;
        this.attributes = new java.util.HashMap<>();
        this.text = text;
        this.children = new java.util.ArrayList<>();
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                Node attr = attrs.item(i);
                attributes.put(attr.getNodeName(), attr.getNodeValue());
            }
        }
    }

    @Override
    public String toString() {
        return "SSMLNode{tag=" + tag + ", attrs=" + attributes + ", text=" + text + ", children=" + children + "}";
    }
}

class SSMLParser {
    SSMLNode parse(String ssml) {
        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(ssml)));
            return elementToNode(doc.getDocumentElement());
        } catch (Exception e) {
            System.err.println("Parse error: " + e.getMessage());
            return null;
        }
    }

    private SSMLNode elementToNode(Node element) {
        String text = element.getTextContent().trim().isEmpty() ? null : element.getTextContent().trim();
        SSMLNode node = new SSMLNode(element.getNodeName(), element.getAttributes(), text);
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                node.children.add(elementToNode(child));
            } else if (child.getNodeType() == Node.TEXT_NODE && child.getTextContent().trim().length() > 0) {
                node.children.add(new SSMLNode("text", null, child.getTextContent().trim()));
            }
        }
        return node;
    }

    static void printTree(SSMLNode node, int indent) {
        System.out.printf("%s%s %s%n", "  ".repeat(indent), node.tag, node.attributes);
        if (node.text != null) System.out.printf("%sText: %s%n", "  ".repeat(indent + 1), node.text);
        for (SSMLNode child : node.children) printTree(child, indent + 1);
    }
}

public class Main {
    public static void main(String[] args) {
        String ssml = """
                <speak version="1.0">
                    <p><s>First sentence.</s><s pitch="high">Second sentence.</s></p>
                    <break time="500ms"/>
                    <p>Hello, <emphasis level="strong">world</emphasis>!</p>
                </speak>""";
        SSMLParser parser = new SSMLParser();
        SSMLNode root = parser.parse(ssml);
        if (root != null) SSMLParser.printTree(root, 0);
    }
}