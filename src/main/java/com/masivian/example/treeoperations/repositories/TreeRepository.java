package com.masivian.example.treeoperations.repositories;

import com.masivian.example.treeoperations.entities.PlainTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreeRepository extends JpaRepository<PlainTree, Integer> {

}
