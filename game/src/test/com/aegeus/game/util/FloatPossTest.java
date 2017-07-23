package com.aegeus.game.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by Silvre on 7/19/2017.
 */
@RunWith(PowerMockRunner.class)
public class FloatPossTest {
    FloatPoss poss = new FloatPoss(0.5f, 10.5f);
    @Test
    public void getMin() throws Exception {
        assertTrue(poss.getMin() == 0.5f);
    }

    @Test
    public void setMin() throws Exception {
        poss.setMin(0.1f);
        assertTrue(poss.getMin() == 0.1f);
        poss.setMin(0.5f);
    }

    @Test
    public void getMax() throws Exception {
        assertTrue(poss.getMax() == 10.5f);
    }

    @Test
    public void setMax() throws Exception {
        poss.setMax(1.5f);
        assertTrue(poss.getMax() == 1.5f);
        poss.setMax(10.5f);
    }

}