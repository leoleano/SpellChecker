package assign08;

import java.util.ArrayList;

/**
 * Represents a generically-typed binary tree node. Each binary node contains
 * data, a reference to the left child, and a reference to the right child.
 * 
 * @author Erin Parker, Leonardo Leano, and Tristen Kilgrow
 * @version March 22, 2022
 */
public class BinaryNode<NodeType> {

	private NodeType data;

	private BinaryNode<NodeType> leftChild;

	private BinaryNode<NodeType> rightChild;
	
	private BinaryNode<NodeType> parentNode;

	public BinaryNode(NodeType data, BinaryNode<NodeType> parent, BinaryNode<NodeType> leftChild, BinaryNode<NodeType> rightChild) {
		this.data = data;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.parentNode = parent;
	}

	public BinaryNode(NodeType data, BinaryNode<NodeType> parent) {
		this(data, parent, null, null);
	}

	/**
	 * @return the node data
	 */
	public NodeType getData() {
		return data;
	}

	/**
	 * @param data - the node data to be set
	 */
	public void setData(NodeType data) {
		this.data = data;
	}

	/**
	 * @return reference to the left child node
	 */
	public BinaryNode<NodeType> getLeftChild() {
		return leftChild;
	}

	/**
	 * @param leftChild - reference of the left child node to be set
	 */
	public void setLeftChild(BinaryNode<NodeType> leftChild) {
		this.leftChild = leftChild;
	}

	/**
	 * @return reference to the right child node
	 */
	public BinaryNode<NodeType> getRightChild() {
		return rightChild;
	}

	/**
	 * @param rightChild - reference of the right child node to be set
	 */
	public void setRightChild(BinaryNode<NodeType> rightChild) {
		this.rightChild = rightChild;
	}

	/**
	 * @return reference to the leftmost node in the binary tree rooted at this node
	 */
	public BinaryNode<NodeType> getLeftmostNode() {
		
		if (leftChild == null)
			return this;
		
		
		return leftChild.getLeftmostNode();
	}

	/**
	 * @return reference to the rightmost node in the binary tree rooted at this node
	 */
	public BinaryNode<NodeType> getRightmostNode() {
		if (rightChild == null)
			return this;
		
		
		return rightChild.getRightmostNode();
	}

	/**
	 * @return the height of the binary tree rooted at this node
	 * 
	 * The height of a tree is the length of the longest path to a leaf
	 * node. Consider a tree with a single node to have a height of zero.
	 */
	public int height() {
		
		if (leftChild == null & rightChild == null)
			return 0;
		
		
		if (rightChild != null && leftChild == null)
		{
			return rightChild.height() + 1;
		}
		if (leftChild != null && rightChild == null)
		{
			return leftChild.height() + 1;
		}
		
		if (rightChild != null && leftChild != null)
			return 1 + Math.max(leftChild.height(), rightChild.height());
		
		return 0;
	}
	
	/**
	 * Finds the successor of the node calling this method. Useful in the case of removal
	 * of the said node.
	 * @return the successor node of the node calling this method
	 */
	public BinaryNode<NodeType> successor() 
	{
		if (leftChild != null & rightChild != null)
			return rightChild.getLeftmostNode();
		else if (leftChild != null)
			return leftChild;
		else if (rightChild != null)
			return rightChild;
		
		return null;
	}
	
	
	/**
	 * Checks and returns true if the node is a leaf node, false if it isn't
	 * @return
	 */
	public boolean isLeaf()
	{
		if (getRightChild() == null && getLeftChild() == null)
			return true;
		
		return false;
	}
	
	/**
	 * Returns the parent node of this node
	 * @return
	 */
	public BinaryNode<NodeType> getParent ()
	{
		return parentNode;
	}
	
	/**
	 * Setter for a new parent node
	 * @param newParent
	 */
	public void setParent(BinaryNode<NodeType> newParent)
	{
		parentNode = newParent;
	}
	
	/**
	 *  Checks and returns true if the node has only one child, false if it 
	 *  doesn't
	 * @return
	 */
	public boolean oneChild ()
	{
		//If it has two children, it doesn't have just one child
		if (twoChild())
			return false;
		
		if (getRightChild() != null || getLeftChild() != null)
			return true;
		
		//At this point, it must not have any children
		return false;
	}
	
	/**
	 * Checks and returns true if the node has two child nodes, false if it
	 * doesn't.
	 * 
	 * @return
	 */
	public boolean twoChild ()
	{
		if (getRightChild() != null && getLeftChild() != null)
			return true;
		
		return false;
	}
	
	/**
	 * Generates list of the data of the tree rooted at this node,
	 * using inorder traversal (left subtree to element itself to the 
	 * right subtree).
	 * 
	 * @param list of elements visited so far
	 */
	public void getSortedArrayList(ArrayList<NodeType> list)
	{
		//do a recursive traversal of the subtree on the left
		if (leftChild != null)
			leftChild.getSortedArrayList(list);
		// "visit" this node
		list.add(data);
		//do a recursive traversal of the subtree on the right
		if (rightChild != null)
			rightChild.getSortedArrayList(list);
	}
}
