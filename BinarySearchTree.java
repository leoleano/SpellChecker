package assign08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * Represents a binary search tree of binary nodes of generic types. Offers methods for 
 * basic operations within a binary search tree.
 * 
 * @author Leonardo Leano and Tristen Kilgrow
 * @version March 22, 2022
 */

public class BinarySearchTree<TreeType extends Comparable<? super TreeType>> implements SortedSet<TreeType>
{
	
	private BinaryNode<TreeType> root;
	private int size;

	public BinarySearchTree() {
		root = null;
		size = 0;
	}
	

	/**
	 * Add the new item to the BST such that the order is maintained. Duplicates are
	 * not allowed.
	 * 
	 * COST: O(tree height)
	 * 
	 * @param item - the item whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if
	 *         the input item was actually inserted); otherwise, returns false
	 */
	@Override
	public boolean add(TreeType item)
	{
		
		BinaryNode<TreeType> temp = root;
		
		//If there is not a root yet, the root becomes a binary node with item data and no parent.
		if (isEmpty())
		{
			root = new BinaryNode<TreeType> (item, null, null, null);
			size++;
			return true;
		}
		
		while (temp != null)
		{
			// CASE 1--they are equal: return (do nothing because item is a duplicate)
			if (temp.getData().compareTo(item) == 0)
				return false;
			
			
			// CASE 2--item is bigger: if temp has a right child, advance temp to the right;
			// else set temp's right child to be a new node containing item and return
			else if(temp.getData().compareTo(item) < 0)
			{
				if (temp.getRightChild() != null)
					temp = temp.getRightChild();
				else
				{
					temp.setRightChild(new BinaryNode<TreeType>(item, temp));
					size++;
					return true;
				}
			}
			
			// CASE 3--item is smaller: do the opposite of CASE 2 (i.e., go down the left
			// side of the tree)
			else 
			{
				if (temp.getLeftChild() != null)
					temp = temp.getLeftChild();
				else
				{
					temp.setLeftChild(new BinaryNode<TreeType>(item, temp));
					size++;
					return true;
				}
			}
				
		}
		
		
		return false;
	}

	/**
	 * Adds all the elements in items to the binary search tree. 
	 * 
	 * @param items - the collection of items whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if
	 *         any item in the input collection was actually inserted); otherwise,
	 *         returns false
	 */
	@Override
	public boolean addAll(Collection<? extends TreeType> items)
	{
		//Iterator to go through all data in items
		Iterator<? extends TreeType> itemsIter = items.iterator();
		TreeType currentItem;
		/**
		 * Purposefully set to false so that if add() returns true once, it will remain true
		 * always; otherwise will stay false.
		 */
		boolean insertionCase = false;
		
		while (itemsIter.hasNext())
		{
			currentItem = itemsIter.next();
			
			if (add(currentItem) == true)
				insertionCase = true;
		}
		
		return insertionCase;
	}

	/**
	 * Removes all items from this set. The set will be empty after this method
	 * call.
	 */
	@Override
	public void clear()
	{
		root = null;
		size = 0;
		
	}

	/**
	 * Determines if there is an binary node in this set whose data is equal to the specified
	 * item.
	 * 
	 * @param item - the item sought in this set
	 * @return true if there is an item in this set that is equal to the input item;
	 *         otherwise, returns false
	 */
	@Override
	public boolean contains(TreeType item)
	{
		
		BinaryNode<TreeType> temp = root;
		
		//Loops through tree until binary node with data that matches item is found or not.
		while (temp != null)
		{
			if (temp.getData().compareTo(item) == 0)
				return true;
			else if (temp.getData().compareTo(item) < 0)
				temp = temp.getRightChild();
			else 
				temp = temp.getLeftChild();
		}
		
		//A binary node with data that matches item has not been found, return false. 
		return false;
	}

	/**
	 * Determines if for each item in the specified collection, there is an item in
	 * this set that is equal to it.
	 * 
	 * @param items - the collection of items sought in this set
	 * @return true if for each item in the specified collection, there is an item
	 *         in this set that is equal to it; otherwise, returns false
	 */
	@Override
	public boolean containsAll(Collection<? extends TreeType> items)
	{
		//Iterator to go through every data in items
		Iterator<? extends TreeType> itemsIter = items.iterator();
		TreeType currentItem;
		
		//Will loop through each data in items
		while (itemsIter.hasNext())
		{
			currentItem = itemsIter.next();
			//If contains comes back as false, immediately return false as one of the items is not in the tree.
			if (contains(currentItem) == false)
				return false;
			
		}
		
		//If contains never returned false, then all of the items are in the tree; return true.
		return true;
	}

	/**
	 * Returns the first (i.e., smallest) item in this set.
	 * 
	 * @throws NoSuchElementException if the set is empty
	 */
	@Override
	public TreeType first() throws NoSuchElementException
	{
		if (isEmpty())
			throw new NoSuchElementException();
		
		return root.getLeftmostNode().getData();
	}

	/**
	 * Returns true if this binary search tree contains no items.
	 */
	@Override
	public boolean isEmpty()
	{
		if (size == 0)
			return true;
		
		return false;
	}

	/**
	 * Returns the last (i.e., largest) item in this binary search tree.
	 * 
	 * @throws NoSuchElementException if the set is empty
	 */
	@Override
	public TreeType last() throws NoSuchElementException
	{
		
		if (isEmpty())
			throw new NoSuchElementException();
		
		return root.getRightmostNode().getData();

	}

	/**
	 * Ensures that this binary search tree does not contain the specified item.
	 * 
	 * @param item - the item whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if
	 *         the input item was actually removed); otherwise, returns false
	 */
	@Override
	public boolean remove(TreeType item)
	{
		
		BinaryNode<TreeType> temp = root;
		
		while (temp != null)
		{
			// CASE 1--they are equal: do the remove, see CASES ROOT & A-C below
			if (temp.getData().compareTo(item) == 0)
			{
				
				BinaryNode<TreeType> parentOfTemp = temp.getParent();
				
				//CASE ROOT -- root node: Find successor of root and replace appropriately
				//The reason why this is separate from the other cases is that root has no parent node.
				if (parentOfTemp == null)
				{
					BinaryNode<TreeType> successor = temp.successor();
					BinaryNode<TreeType> parentOfSuccessor = successor.getParent();
					
					//Case of removing the root and it's the only node
					if(root.getRightChild() == null && root.getLeftChild() == null)
					{
						clear();
						return true;
					}
					
					//Case of successor being root's left child and therefore right child is null.
					if(temp.getLeftChild().getData().compareTo(successor.getData()) == 0)
					{
						root = successor;
						successor.setParent(null);
						size--;
						return true;
					}
					
					successor.setLeftChild(temp.getLeftChild());					
					
					//If successor already has a right child, include via setting it's parent to
					//have it as it's new left child. Reassign root to new root
					if (successor.getRightChild() != null)
					{					
						//The case of the successor being root/temp's right child with its
						//own right child.
						if (parentOfSuccessor.getData().compareTo(temp.getData()) == 0)
						{
							successor.setParent(null);  
							root = successor;
							size--;
							return true;
						}
							
						//Case of successor's parent needing to adopt the successor's right child as its
						//left child.
						parentOfSuccessor.setLeftChild(successor.getRightChild());
						parentOfSuccessor.getLeftChild().setParent(parentOfSuccessor);
						successor.setRightChild(temp.getRightChild());
						successor.setParent(null);
						successor.getLeftChild().setParent(successor);
						successor.getRightChild().setParent(successor);
						root = successor;
						size--;
						return true;
					}
					//Doesn't have right child, just set root's right child to its successor's right child.
					//Reassign root to new root
					else
					{
						//The case that the successor is the right child of root, and therefore
						//just set successor's (which is the new root) right child to null
						if (parentOfSuccessor.getData().compareTo(temp.getData()) == 0)
						{
							successor.setRightChild(null);
							root = successor;
							size--;
							return true;
						}
						
						
						successor.setRightChild(temp.getRightChild()); 
						parentOfSuccessor.setLeftChild(null);
						root = successor;
						size--;
						return true;
					}

					
				}
				
				// CASE A--leaf node: simply delete it (i.e., set the parent's child link to
				// null)
				if (temp.isLeaf())
				{
					if (parentOfTemp.getRightChild() == null || parentOfTemp.getRightChild().getData().compareTo(temp.getData()) != 0)
					{
						parentOfTemp.setLeftChild(null);
						size--;
						return true;
					}
					else
					{
						parentOfTemp.setRightChild(null);
						size--;
						return true;
					}
				}
				
				// CASE B--node with one child: adjust its parent's child link to bypass the
				// node and go directly to the node's child
				if (temp.oneChild())
				{	
					
					BinaryNode<TreeType> successor = temp.successor();
					
					//Case of temp being left child of parent
					//First checks if right child is null in case of parent only having one child
					//and therefore .getData() wouldn't work on a null
					if (parentOfTemp.getRightChild() == null || parentOfTemp.getRightChild().getData().compareTo(temp.getData()) != 0)
					{
						parentOfTemp.setLeftChild(successor);
						successor.setParent(parentOfTemp);
						size--;
						return true;
					}
					//Case of temp being right child of parent
					else
					{
						parentOfTemp.setRightChild(successor);
						successor.setParent(parentOfTemp);
						size--;
						return true;
					}
				}
				
				// CASE C--node with two children: replace the node's data with that of the
				// smallest node of its right subtree (its successor),
				// then remove the successor node (guaranteed to have at most one child)
				if (temp.twoChild())
				{
					BinaryNode<TreeType> successor = temp.successor();
					
					
					//Case of temp being the parent's right child
					//First checks if left child is null in case of parent only having one child
					//and therefore .getData() wouldn't work on a null
					if (parentOfTemp.getLeftChild() == null || parentOfTemp.getLeftChild().getData().compareTo(temp.getData()) != 0)
					{
						//Purpose of parentOfSuccessor is to re-assign the right child of the successor
						//(if there's any) to be the left child of the successor's parent unless if 
						//parentOfSuccessor is temp.
						BinaryNode<TreeType> parentOfSuccessor = successor.getParent();
						
						//Case of successor being right child of temp (what we're trying to delete). 
						if (parentOfSuccessor.getData().compareTo(temp.getData()) == 0)
						{
							successor.setLeftChild(parentOfSuccessor.getLeftChild());
							successor.getLeftChild().setParent(successor);
							parentOfTemp.setRightChild(successor);
							successor.setParent(parentOfTemp);
							size--;
							return true;
						}
						
						parentOfTemp.setRightChild(successor);
						parentOfTemp.getRightChild().setParent(parentOfTemp);
						successor.setLeftChild(temp.getLeftChild());
						successor.getLeftChild().setParent(successor);
						
						//If the successor has a right child, set parent of successor to be that right child
						//Then set successor's new right child to include temp's right child
						if (successor.getRightChild() != null)
						{
							parentOfSuccessor.setLeftChild(successor.getRightChild());
							parentOfSuccessor.getLeftChild().setParent(parentOfSuccessor);
							successor.setRightChild(temp.getRightChild());
							successor.getRightChild().setParent(successor);
							size--;
							return true;
						}
						//If the successor doesn't have a right child, just set temp's right child.
						else
						{
							successor.setRightChild(temp.getRightChild());
							successor.getRightChild().setParent(successor);
							size--;
							return true;
						}
					}
					//Case of temp being the parent's left child.
					else
					{
						
						
						//Same thing as above, except successor is being set as temp's parent's left child instead.
						BinaryNode<TreeType> parentOfSuccessor = successor.getParent();
						
						
						parentOfTemp.setLeftChild(successor);
						successor.setParent(parentOfTemp);
						successor.setLeftChild(temp.getLeftChild());
						successor.getLeftChild().setParent(successor);
						
						if (parentOfSuccessor.getData().compareTo(temp.getData()) == 0)
						{
							size--;
							return true;
						}
						
						if (successor.getRightChild() != null)
						{
							parentOfSuccessor.setLeftChild(successor.getRightChild());
							parentOfSuccessor.getLeftChild().setParent(parentOfSuccessor);
							successor.setRightChild(temp.getRightChild());
							successor.getRightChild().setParent(successor);
							size--;
							return true;
						}
						else
						{
							
							successor.setRightChild(temp.getRightChild());
							successor.getRightChild().setParent(successor);
							parentOfSuccessor.setLeftChild(null);
						}
					}
				}
			}
			
			// CASE 2--item is bigger: if temp does not have a right child, return (the item
			// is not in the tree);
			// else advance temp to the right
			else if (temp.getData().compareTo(item) < 0)
			{
				if (temp.getRightChild() == null)
					return false;
				temp = temp.getRightChild();
			}
			// CASE 3--item is smaller: if temp does not have a left child, return (the item
			// is not in the tree);
			// else advance temp to the left
			else
			{
				if (temp.getLeftChild() == null)
					return false;
				temp = temp.getLeftChild();
			}
		}
		
		//Node proposed is not in the set
		return false;
	}

	/**
	 * Ensures that this set does not contain any of the items in the specified
	 * collection.
	 * 
	 * @param items - the collection of items whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if
	 *         any item in the input collection was actually removed); otherwise,
	 *         returns false
	 */
	@Override
	public boolean removeAll(Collection<? extends TreeType> items)
	{
		
		Iterator<? extends TreeType> itemsIter = items.iterator();
		TreeType currentItem;
		boolean removeCase = false;
		
		//If any of the nodes within the tree with values that match any of the ones in items,
		//it will return true.
		while (itemsIter.hasNext())
		{
			currentItem = itemsIter.next();
			
			if (remove(currentItem))
				removeCase = true;
		}
		
		//None of the values in items are in the binary tree and therefore nothing was changed
		return removeCase;
	}

	/**
	 * Returns the number of items in this set.
	 */
	@Override
	public int size()
	{
		return size;
	}

	/**
	 * Returns an ArrayList containing all of the items in this set, in sorted
	 * order.
	 */
	@Override
	public ArrayList<TreeType> toArrayList()
	{
		ArrayList<TreeType> sortedArrayList = new ArrayList <TreeType>();
		
		if (isEmpty())
			return sortedArrayList;
		
		root.getSortedArrayList(sortedArrayList);
		
		return sortedArrayList;
	}
	
	/**
	 * Get height of tree from the root
	 * @return
	 */
	public int getHeight()
	{
		return root.height();
	}

}
