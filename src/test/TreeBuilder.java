package test;

import java.util.ArrayList;
import java.util.List;

public class TreeBuilder {
	List<Node> nodes = new ArrayList<Node>();

	public TreeBuilder(List<Node> nodes) {
		super();
		this.nodes = nodes;
	}
	
	public List<Node> buildJSONTree()	{
		List<Node> nodeTree = buildTree();
		return nodeTree;	
	}
	
	public List<Node> buildTree(){
		List<Node> treeNodes = new ArrayList<Node>();
		List<Node> rootNodes = getRootNodes();
		
		for(Node rootNode : rootNodes) {
			buildChildNodes(rootNode);
			treeNodes.add(rootNode);
		}
		return treeNodes;
	}
	
	public void buildChildNodes(Node node) {
		List<Node> children = getChildNodes(node);
		
		System.out.println("##########");
		
		if(!children.isEmpty()) {
			for(Node child : children) {
				buildChildNodes(child);
			}
			node.setChildren(children);
		}
	}
	
	public List<Node> getChildNodes(Node pnode){
		List<Node> childNodes = new ArrayList<Node>();
		System.out.println("@@@@@@@@@@@@@@@");
		for(Node n : nodes) {
			if(pnode.getId().equals(n.getPid())) {
				childNodes.add(n);
			}
		}
		
		return childNodes;
	}
	
	public boolean rootNode(Node node) {
		boolean isRootNode = true;
		
		for(Node n : nodes) {
			if(node.getPid().equals(n.getId())) {
				isRootNode= false;
				break;
			}
		}
		return isRootNode;
	}
	
	public List<Node> getRootNodes(){
		List<Node> rootNodes = new ArrayList<Node>();
		
		for(Node n : nodes) {
			if(rootNode(n)) {
				rootNodes.add(n);
			}
		}
		
		return rootNodes;
	}
}
