package sf.codemo.utils.document;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public abstract class SimpleXmlUtils {
	public static final String VERSION = "1.0";
	public static final String ENCODING = "utf-8";
	public static final String NODE_PATH_SEP = "/";
	public static final String NODE_NAME_REPEAT_CHAR = "^";
	public static final String LF = "\r\n";
	public static final String CDATA_BEGIN = "<![CDATA[";
	public static final String CDATA_END = "]]>";
	public static final String FORMAT_INDENT = "\t";
	public static final String[] ENTITIES = { "&", "&amp;", "<", "&lt;", ">", "&gt;", "'", "&apos;", "\"", "&quot;" };

	public static void main(String[] args) throws Exception {
		Document doc = SimpleXmlUtils.createDocument();
		doc.addNode("order/id", "1001");
		doc.addAttribute("order/id", "type", "number");
		doc.addAttribute("order/id", "max", "99999");
		doc.addNode("order/mer", "M001");
		doc.addNode("order/amt", "200.0");
		doc.addNode("order/buyee/name", "Andy");
		doc.addNode("order/buyee/address", "HongKong", true);
		doc.addNode("order/receiver/name", "Bill");
		doc.addNode("order/receiver/phone", "8888");
		doc.addNode("order/receiver^2/name", "Carl");
		doc.addNode("order/receiver^2/phone", "9999");
		doc.addNode("order/receiver^3/name", "Davy");
		doc.addNode("order/receiver^3/phone", "0000");

		System.out.println(doc.getString());

		FileWriter f = new FileWriter(new File("D:/tmp/zzz.xml"));
		f.write(doc.getString(true));
		f.close();
	}

	public static Document createDocument() {
		return createDocument(ENCODING);
	}

	public static Document createDocument(String encoding) {
		Document doc = new Document();
		doc.setVersion(VERSION);
		doc.setEncoding(Charset.forName(encoding));
		return doc;
	}

	public static class Document implements Serializable {
		private static final long serialVersionUID = -7322813248356729698L;
		private String version;
		private Charset encoding;
		private Boolean nodeNameRepeat = false;
		private Node defaultRoot = new Node("_doc_root_");

		public Document addAttribute(String nodePath, String attrName, String attrValue) {
			if (StringUtils.isBlank(nodePath)) return this;
			String[] paths = StringUtils.strip(nodePath, NODE_PATH_SEP).split(NODE_PATH_SEP);
			Node current = null;
			for (int index = 0; index < paths.length; index++) {
				String name = paths[index];
				if (0 == index) {
					current = checkFirstChild(defaultRoot, name);
				} else {
					current = checkFirstChild(current, name);
				}
				if (paths.length - 1 == index) {
					current.getAttributes().put(attrName, replaceEntities(attrValue));
				}
			}
			return this;
		}

		public Document addNode(String nodePath, String text, boolean isCdata) {
			if (StringUtils.isBlank(nodePath)) return this;
			String[] paths = StringUtils.strip(nodePath, NODE_PATH_SEP).split(NODE_PATH_SEP);
			Node current = null;
			for (int index = 0; index < paths.length; index++) {
				String name = replaceEntities(paths[index]);
				if (0 == index) {
					current = checkFirstChild(defaultRoot, name);
				} else {
					current = checkFirstChild(current, name);
				}
				if (paths.length - 1 == index) {
					if (isCdata) {
						current.setText(text);
					} else {
						current.setText(replaceEntities(text));
					}
					current.setIsCdata(isCdata);
				}
			}
			return this;
		}

		private String replaceEntities(final String text) {
			String newText = text;
			for (int i = 0; i < ENTITIES.length; i = i + 2) {
				String entityKey = ENTITIES[i];
				String entityVal = ENTITIES[i + 1];
				newText = StringUtils.replace(newText, entityKey, entityVal);
			}
			return newText;
		}

		public Document addNode(String nodePath, String text) {
			return addNode(nodePath, text, false);
		}

		public String getString() {
			return getString(false);
		}

		public String getString(boolean beFormated) {
			StringBuilder sb = new StringBuilder();
			String lf = beFormated ? LF : "";
			sb.append("<?xml version=\"" + version + "\" encoding=\"" + encoding + "\"?>").append(lf);
			List<Node> nodes = defaultRoot.getChildren();
			for (Node node : nodes) {
				sb.append(getString(node, beFormated, 0));
			}
			return new String(sb.toString().getBytes(), encoding);
		}

		private String getString(Node node, boolean beFormated, int depth) {
			StringBuilder sb = new StringBuilder();
			List<Node> nodes = node.getChildren();
			boolean isOneLine = (0 == nodes.size());
			String lf = (isOneLine ? "" : (beFormated ? LF : ""));
			String attrs = getAttrString(node);
			if (beFormated) {
				sb.append(StringUtils.repeat(FORMAT_INDENT, depth));
			}
			String name = transferNodename(node.getName());
			sb.append("<").append(name).append(attrs).append(">").append(lf);
			if (StringUtils.isNotBlank(node.getText())) {
				if (node.getIsCdata()) sb.append(CDATA_BEGIN).append(lf);
				sb.append(node.getText()).append(lf);
				if (node.getIsCdata()) sb.append(CDATA_END).append(lf);
			}
			for (Node nd : nodes) {
				sb.append(getString(nd, beFormated, depth + 1));
			}
			if (isOneLine) {
				sb.append("</").append(name).append(">").append(beFormated ? LF : "");
			} else {
				if (beFormated) {
					sb.append(StringUtils.repeat(FORMAT_INDENT, depth));
				}
				sb.append("</").append(name).append(">").append(beFormated ? LF : "");
			}
			return sb.toString();
		}

		private String transferNodename(String name) {
			int charIndex = StringUtils.indexOf(name, NODE_NAME_REPEAT_CHAR);
			if (-1 == charIndex) return name;
			return StringUtils.substring(name, 0, charIndex);
		}

		private String getAttrString(Node node) {
			StringBuilder sb = new StringBuilder("");
			Map<String, String> attrs = node.getAttributes();
			if (0 != attrs.size()) sb.append(" ");
			for (Map.Entry<String, String> entry : attrs.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				sb.append(key).append("=\"").append(value).append("\" ");
			}
			return StringUtils.removeEnd(sb.toString(), " ");
		}

		private Node checkFirstChild(Node node, String name) {
			if (!nodeNameRepeat) {
				List<Node> subNodes = node.getChildren();
				for (Node n : subNodes) {
					if (n.getName().equals(name)) {
						return n;
					}
				}
			}
			Node n = new Node();
			n.setName(name);
			node.addChild(n);
			return n;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public Charset getEncoding() {
			return encoding;
		}

		public void setEncoding(Charset encoding) {
			this.encoding = encoding;
		}

	}

	public static class Node implements Serializable {
		private static final long serialVersionUID = -3072137211596860227L;
		private String name;
		private int order = -1;
		private String text;
		private Boolean isCdata = false;
		private Node parent;
		private Map<String, String> attributes = new HashMap<String, String>();
		private List<Node> children = new LinkedList<Node>();

		public Node(String name) {
			this.name = name;
		}

		public Node() {
			this(null);
		}

		public String getName() {
			return name;
		}

		public void addChild(Node node) {
			getChildren().add(node);
			node.setParent(this);
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getOrder() {
			return order;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public Map<String, String> getAttributes() {
			return attributes;
		}

		public void setAttributes(Map<String, String> attributes) {
			this.attributes = attributes;
		}

		public List<Node> getChildren() {
			return children;
		}

		public void setChildren(List<Node> children) {
			this.children = children;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public Boolean getIsCdata() {
			return isCdata;
		}

		public void setIsCdata(Boolean isCdata) {
			this.isCdata = isCdata;
		}
	}
}
