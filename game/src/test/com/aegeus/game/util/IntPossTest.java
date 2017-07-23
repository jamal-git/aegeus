package com.aegeus.game.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Silvre on 7/19/2017.
 */
@RunWith(PowerMockRunner.class)
public class IntPossTest {
    private IntPoss poss = new IntPoss(10, 100);

    @Test
    public void getMin() throws Exception {
        assertTrue(poss.getMin() == 10);
    }

    @Test
    public void getMax() throws Exception {
        assertTrue(poss.getMax() == 100);
    }

    @Test
    public void setMax() throws Exception {
        poss.setMax(10);
        assertTrue(poss.getMax() == 10);
        poss.setMax(100);
    }

    @Test
    public void setMin() throws Exception {
        poss.setMin(5);
        assertTrue(poss.getMin() == 5);
        poss.setMax(5);
    }

}