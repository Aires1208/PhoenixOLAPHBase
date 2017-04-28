package com.zte.ums.esight.domain.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest {
    @Test
    public void testStatus() {
        Result result = new Result();

        result.setStatus(1);

        assertEquals(1, result.getStatus());
    }

    @Test
    public void testData() {
        Result result = new Result();

        result.setData("test");

        assertEquals("test", result.getData());
    }

    @Test
    public void testMessage() {
        Result result = new Result();

        result.setMessage("test");

        assertEquals("test", result.getMessage());
    }

}