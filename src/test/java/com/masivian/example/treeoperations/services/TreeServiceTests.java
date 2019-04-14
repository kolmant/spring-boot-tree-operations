package com.masivian.example.treeoperations.services;

import com.masivian.example.treeoperations.entities.PlainTree;
import com.masivian.example.treeoperations.exceptions.InconsistentOperationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.persistence.EntityNotFoundException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		MockitoTestExecutionListener.class
})
@SpringBootTest
public class TreeServiceTests {

	@Autowired
	private TreeService treeService;

	@Test
	public void validTreeCRUD() {
		PlainTree expectedTree = new PlainTree();
		expectedTree.setStructure("70,84,85\n70,84,78,80\n70,84,78,76\n70,49,54,51\n70,49,37,40\n70,49,37,22");

		Integer treeId = treeService.insert(expectedTree);
		PlainTree actualTree = treeService.findById(treeId);

		Assert.assertEquals("Unexpected structure", expectedTree.getStructure(), actualTree.getStructure());

		expectedTree.setId(treeId);
		expectedTree.setStructure("1,2,3");

		treeService.update(expectedTree);
		actualTree = treeService.findById(treeId);

		Assert.assertEquals("Unexpected structure", expectedTree.getStructure(), actualTree.getStructure());

		treeService.delete(treeId);
		try {
			treeService.findById(treeId);
			Assert.fail("Tree is still in DB");
		} catch (EntityNotFoundException e) {
			Assert.assertEquals("Tree "+treeId+" not found.", e.getMessage());
		}
	}

	@Test
	public void validateLCA() {
		PlainTree expectedTree = new PlainTree();
		expectedTree.setStructure("70,84,85\n70,84,78,80\n70,84,78,76\n70,49,54,51\n70,49,37,40\n70,49,37,22");

		Integer treeId = treeService.insert(expectedTree);
		PlainTree actualTree = treeService.findById(treeId);
		try {
			checkLCA(actualTree.getId(), 40, 78, 70);
			checkLCA(actualTree.getId(), 51, 37, 49);
			checkLCA(actualTree.getId(), 76, 85, 84);
		} catch (InconsistentOperationException e) {
			Assert.fail("Invalid inconsistent operation exception was thrown");
		}
	}

	@Test(expected = InconsistentOperationException.class)
	public void validateInvalidLCA() throws InconsistentOperationException {
		PlainTree expectedTree = new PlainTree();
		expectedTree.setStructure("70,84,85\n70,84,78,80\n70,84,78,76\n70,49,54,51\n70,49,37,40\n70,49,37,22");

		Integer treeId = treeService.insert(expectedTree);
		PlainTree actualTree = treeService.findById(treeId);

		checkLCA(actualTree.getId(), 400, 780, 0);
	}

	private void checkLCA(Integer treeId, Integer firstNode, Integer secondNode, int expectedLCA) throws InconsistentOperationException {
		int LCA = treeService.CalculateLCA(treeId, firstNode, secondNode);
		Assert.assertEquals("Invalid LCA", expectedLCA, LCA);

		LCA = treeService.CalculateLCA(treeId, secondNode, firstNode);
		Assert.assertEquals("Invalid LCA", expectedLCA, LCA);
	}
}
