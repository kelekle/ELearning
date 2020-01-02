package com.star.e_learning;

import com.star.e_learning.util.DateConverter;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SimpleAndroidTest extends TestCase {


    @Test
    public void dateConverter_isCorrect() {
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date myDate2 = dateFormat2.parse("2010-09-13 22:36:01");
            assertEquals("2010-09-13 20:06:01", DateConverter.dateToTimestamp(myDate2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
