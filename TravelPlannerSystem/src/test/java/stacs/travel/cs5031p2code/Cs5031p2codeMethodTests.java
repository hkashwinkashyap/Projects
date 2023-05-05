package stacs.travel.cs5031p2code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stacs.travel.cs5031p2code.exception.InvalidStopException;
import stacs.travel.cs5031p2code.service.TravelServiceImp;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Cs5031p2codeMethodTests {
    TravelServiceImp travelServiceImp;

    @BeforeEach
    void setup() {
        travelServiceImp = new TravelServiceImp();
    }

    @Test
    public void shouldGiveStopTimeTable() {
        ArrayList<String> timetable = null;
        try {
            timetable = travelServiceImp.stopTimeTable("St andrews, bus station");
        } catch (InvalidStopException e) {
            System.out.println(e);
        }
        ArrayList<String> timeTableExpected = new ArrayList<>();
        timeTableExpected.add("91 to St andrews, M&S at 08:10");
        timeTableExpected.add("91 to St andrews, M&S at 09:10");
        timeTableExpected.add("91 to St andrews, M&S at 10:10");
        timeTableExpected.add("91 to St andrews, M&S at 11:10");
        timeTableExpected.add("99 to Dundee, bus station at 08:00");
        timeTableExpected.add("99 to Dundee, bus station at 09:00");
        timeTableExpected.add("99 to Dundee, bus station at 10:00");
        timeTableExpected.add("99 to Dundee, bus station at 11:00");
        timeTableExpected.add("91 to St andrews, bus station at 08:50");
        timeTableExpected.add("91 to St andrews, bus station at 09:50");
        timeTableExpected.add("91 to St andrews, bus station at 10:50");
        timeTableExpected.add("91 to St andrews, bus station at 11:50");
        timeTableExpected.add("99 to St andrews, bus station at 08:40");
        timeTableExpected.add("99 to St andrews, bus station at 09:40");
        timeTableExpected.add("99 to St andrews, bus station at 10:40");
        timeTableExpected.add("99 to St andrews, bus station at 11:40");

        assertEquals(timeTableExpected, timetable);
    }

    @Test
    public void shouldThrowInvalidStopException() {
        try {
            travelServiceImp.stopTimeTable("St andrews, union building");
        } catch (InvalidStopException e) {
            assertEquals(InvalidStopException.class, e.getClass());
        }
    }

    @Test
    public void shouldSayStopNameIsNull() {
        try {
            travelServiceImp.stopTimeTable("");
        } catch (InvalidStopException e) {
            assertEquals(InvalidStopException.class, e.getClass());
        }
    }

    @Test
    public void shouldSayStopOrRouteExists() {
        try {
            travelServiceImp.addStopToRoute("St andrews, end", "91 to morrisons", "St andrews, market street");
        } catch (InvalidStopException e) {
            assertEquals(InvalidStopException.class, e.getClass());
        }
    }

    @Test
    public void shouldGiveNotValidStopOrRoute() {
        try {
            travelServiceImp.addStopToRoute("", "99 to Dundee, bus station", "");
        } catch (InvalidStopException e) {
            assertEquals(InvalidStopException.class, e.getClass());
        }
        try {
            travelServiceImp.addStopToRoute("new stop name", "", "");
        } catch (InvalidStopException e) {
            assertEquals(InvalidStopException.class, e.getClass());
        }
        try {
            travelServiceImp.addStopToRoute("", "", "");
        } catch (InvalidStopException e) {
            assertEquals(InvalidStopException.class, e.getClass());
        }
    }

    //    run the "shouldAddStop" test only once after executing the main program, as it changes the data
    @Test
    public void shouldAddStop() {
//        try {
//            String result = travelServiceImp.addStopToRoute("St andrews, union building", "91 to St andrews, morrisons", "St andrews, market street");
//            assertEquals("Added St andrews, union building in 91 to St andrews, morrisons before St andrews, market street", result);
//        } catch (InvalidStopException e) {
//            System.out.println(e);
//        }
    }


}
