package com.search.service;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LeaderServiceTest extends TestCase {

    @Test
    public void testSplitStringArray() {
        String [] arr = {"a","b","c"};
        List<String[]> partitions = LeaderService.splitStringArray(arr, 2);
        assertEquals(partitions.size(), 2);
        assertEquals(partitions.get(0).length + partitions.get(1).length, 3);
        String [] arr2 = {"a","b","c","d"};
        partitions = LeaderService.splitStringArray(arr2, 4);
        assertEquals(partitions.size(), 4);
        partitions = LeaderService.splitStringArray(arr2, 1);
        assertEquals(partitions.size(), 1);
        partitions = LeaderService.splitStringArray(arr2, 0);
        assertEquals(partitions.size(), 0);
    }
}