package com.tadjmerwan.niceweatherapp.utils


import org.junit.Assert
import org.junit.Test


internal class LongExtKtTest {


    @Test
    fun `get Day Name from Long`() {
        Assert.assertEquals((1655619097L).getDayName(),"Sunday")
        Assert.assertEquals((1653619097L).getDayName(),"Friday")
        Assert.assertEquals((1655705497L).getDayName(),"Monday")

    }

    @Test
    fun `get Time from Long`() {
        Assert.assertEquals((1655619097L).getTime(),"6:11")
        Assert.assertEquals((1655765737L).getTime(),"22:55")
    }
}