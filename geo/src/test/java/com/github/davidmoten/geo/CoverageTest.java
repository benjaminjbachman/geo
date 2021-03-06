package com.github.davidmoten.geo;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * Unit tests for {@link Coverage}.
 * 
 * @author dave
 * 
 */
public class CoverageTest {

    @Test
    public void testCoverageHashLength() {
        Coverage coverage = new Coverage(Sets.<String> newHashSet(), 1.0);
        assertEquals(0, coverage.getHashLength());
        // get coverage of toString
        System.out.println(coverage);
    }

    @Test
    public void testCoverageOfAnAreaThatCantBeCoveredWithHashOfLengthOne() {
        Coverage coverage = GeoHash.coverBoundingBox(-5, 100, -45, 170);
        assertEquals(1, coverage.getHashLength());
        assertEquals(Sets.newHashSet("q", "r"), coverage.getHashes());
    }

    /**
     * bug fixed by https://github.com/davidmoten/geo/pull/25/files where this bounding box
     * didn't return the correct results.  (-30,174) is in the "r" hash, and so
     * any coverBoundingBox should include "r", but this one doesn't
     */
    @Test 
    public void testWideCoverageBug() {
        double top = -1;
        double left = -26;
        double bottom = -2;
        double right = 175;

        Coverage coverage = com.github.davidmoten.geo.GeoHash.coverBoundingBox(top, left, bottom, right, 1);
        assertTrue(coverage.getHashes().contains("r"));
    }

    /**
     * before https://github.com/davidmoten/geo/pull/25/files this test would pass while
     * testWideCoverageBug would fail
     */
    @Test
    public void testLessWideCoverageBug() {
        double top = -1;
        double left = -25;
        double bottom = -2;
        double right = 175;

        Coverage coverage = com.github.davidmoten.geo.GeoHash.coverBoundingBox(top, left, bottom, right, 1);
        assertTrue(coverage.getHashes().contains("r"));
    }
}
