package com.masivian.example.treeoperations.services;

import com.masivian.example.treeoperations.entities.*;
import com.masivian.example.treeoperations.exceptions.InconsistentOperationException;
import com.masivian.example.treeoperations.repositories.TreeRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class TreeService {

    private final TreeRepository treeRepository;

    public TreeService(TreeRepository treeRepository) {
        this.treeRepository = treeRepository;
    }

    /**
     * Inserts a tree
     * @param tree tree to be inserted
     * @return tree's identifier
     */
    public Integer insert(PlainTree tree) {
        return treeRepository.save(tree).getId();
    }

    /**
     * Updates a tree
     * @param tree tree to be inserted
     * @return tree's identifier
     */
    public Integer update(PlainTree tree) {
        return treeRepository.save(tree).getId();
    }

    /**
     * Finds a tree from its id
     * @param treeId tree id to be used for consulting a tre
     * @return tree identified by treeId, or throws an exception if it doesn't exist
     */
    public PlainTree findById(Integer treeId) {
        return treeRepository.findById(treeId).orElseThrow(
                () -> new EntityNotFoundException("Tree "+treeId+" not found.")
        );
    }

    /**
     * Deletes a tree
     * @param treeId tree identifier to be removed
     */
    public void delete(Integer treeId) {
        treeRepository.deleteById(treeId);
    }

    /**
     * Calculates the the lowest common ancestor (LCA) of two given nodes in the given tree.
     * @param treeId tree identifier. It must be previously stored
     * @param firstNodeId first node identifier
     * @param secondNodeId second node identifier
     * @return the LCA of given two nodes
     * @throws InconsistentOperationException if any node is not present in the tree
     */
    public Integer CalculateLCA(Integer treeId, Integer firstNodeId, Integer secondNodeId) throws InconsistentOperationException {
        PlainTree plainTree = findById(treeId);
        SearchTree completeTree = calculateTree(plainTree, firstNodeId, secondNodeId);

        Node root = completeTree.getTree().getRoot();
        Node firstNode = completeTree.getFirstNode();
        Node secondNode = completeTree.getSecondNode();

        Integer response;

        if (firstNode != null && secondNode != null) {
            response = CalculateLCA(root, firstNode, secondNode).getId();
        } else {
            throw new InconsistentOperationException("Nodes are not present in the tree.");
        }

        return response;
    }

    /**
     * Transforms a PlainTree structure onto a SearchTree structure
     * @param plainTree tree to be transformed
     * @param node1 first node
     * @param node2 second note
     * @return SearchTree fully transformed from given PlainTree
     */
    private SearchTree calculateTree(PlainTree plainTree, Integer node1, Integer node2) {
        SearchTree completeTree = new SearchTree();

        Tree tree = new Tree();
        completeTree.setTree(tree);

        Node actualNode = new Node(null);
        tree.setRoot(actualNode);

        StringTokenizer lines = new StringTokenizer(plainTree.getStructure(), "\n");
        while(lines.hasMoreTokens()) {
            StringTokenizer nodes = new StringTokenizer(lines.nextToken(), ",");
            while (nodes.hasMoreTokens()) {
                Integer idToPlace = Integer.parseInt(nodes.nextToken());

                if (actualNode.getId() == null) {
                    actualNode.setId(idToPlace);
                } else {
                    if (actualNode.getId() > idToPlace) {
                        if (actualNode.getLeft() == null) {
                            actualNode.setLeft(new Node(idToPlace));
                        }

                        actualNode = actualNode.getLeft();
                    } else if (actualNode.getId() < idToPlace) {
                        if (actualNode.getRight() == null) {
                            actualNode.setRight(new Node(idToPlace));
                        }

                        actualNode = actualNode.getRight();
                    }
                }

                if (actualNode.getId().equals(node1)) {
                    completeTree.setFirstNode(actualNode);
                } else if (actualNode.getId().equals(node2)) {
                    completeTree.setSecondNode(actualNode);
                }
            }
            actualNode = tree.getRoot();
        }

        return completeTree;
    }

    /**
     * Calculates the the lowest common ancestor (LCA) of two given nodes in the given root node.
     * @param root actual root node
     * @param firstNode first node
     * @param secondNode second node
     * @return the LCA of given two nodes
     */
    private Node CalculateLCA(Node root, Node firstNode, Node secondNode) {
        Node response;
        if(root == null || root.getId().equals(firstNode.getId()) || root.getId().equals(secondNode.getId())) {
            response = root;
        } else {
            Node left = CalculateLCA(root.getLeft(), firstNode, secondNode);
            Node right = CalculateLCA(root.getRight(), firstNode, secondNode);

            //If a node was found in the left branch and the another node was found in the right branch, then the root is the LCA
            if (left != null && right != null) {
                response = root;
            } else {
                //Check if a node was found by the left or the right branch choose the found one
                if (left == null) {
                    response = right;
                } else {
                    response = left;
                }
            }
        }

        return response;
    }
}
