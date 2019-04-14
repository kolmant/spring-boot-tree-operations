package com.masivian.example.treeoperations.controllers;

import com.masivian.example.treeoperations.Router;
import com.masivian.example.treeoperations.entities.LCARequest;
import com.masivian.example.treeoperations.entities.PlainTree;
import com.masivian.example.treeoperations.exceptions.InconsistentOperationException;
import com.masivian.example.treeoperations.services.TreeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping(Router.TREE)
public class TreeController {
    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping
    public PlainTree get(@RequestParam Integer treeId) {
        return treeService.findById(treeId);
    }

    @PostMapping
    public Integer insert(@RequestBody @Valid PlainTree request) {
        return treeService.insert(request);
    }

    @PutMapping
    public Integer update(@RequestBody @Valid PlainTree request) {
        return treeService.update(request);
    }

    @DeleteMapping("/{treeId}")
    public void delete(@PathVariable Integer treeId) {
        treeService.delete(treeId);
    }

    @PostMapping("/{treeId}/lca")
    public Integer LCA(@PathVariable Integer treeId, @RequestBody LCARequest lca) throws InconsistentOperationException {
        try {
            return treeService.CalculateLCA(treeId, lca.getFirstNode(), lca.getSecondNode());
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
