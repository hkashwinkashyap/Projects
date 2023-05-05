package stacs.travel.cs5031p2code;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import stacs.travel.cs5031p2code.controller.TravelController;
import stacs.travel.cs5031p2code.exception.InvalidStopException;
import stacs.travel.cs5031p2code.model.Route;
import stacs.travel.cs5031p2code.model.Stops;
import stacs.travel.cs5031p2code.service.TravelService;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TravelController.class)
class Cs5031p2codeApiTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TravelService travelService;

    @Test
    public void shouldReturnAllRoutesAtStopAndTime() {
        ArrayList<Route> timeTable = new ArrayList<>();
        Stops stop1 = new Stops("St andrews, bus station");
        Stops stop2 = new Stops("St andrews, market street");
        timeTable.add(new Route("91 to St andrews, bus station", new ArrayList<Stops>(Arrays.asList(stop1, stop2))));
        timeTable.add(new Route("93 to St andrews, bus station", new ArrayList<Stops>(Arrays.asList(stop1, stop2))));
        Mockito.when(travelService.allRoutesAtStopAndTime("st andrews, bus station", "10:00")).thenReturn(timeTable);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/route/st andrews, bus station/10:00");
        MvcResult result = null;
        try {
            result = mockMvc.perform(requestBuilder).andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(result.getResponse());
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void shouldReturnStopTimeTable() {
        ArrayList<String> timeTable = new ArrayList<>();
        timeTable.add("91 to morrisons at 9:00");
        timeTable.add("99 to dundee, bus station at 9:00");
        timeTable.add("91 to st andrews, bus station at 9:50");
        timeTable.add("99 to st andrews, bus station at 9:40");
        try {
            Mockito.when(travelService.stopTimeTable("st andrews, bus station")).thenReturn(timeTable);
        } catch (InvalidStopException e) {
            System.out.println(e);
            ;
        }
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/time/St andrews, bus station");
        MvcResult result = null;
        try {
            result = mockMvc.perform(requestBuilder).andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void shouldReturnAllRoutesAtStop() {
        ArrayList<Route> timeTable = new ArrayList<>();
        Stops stop1 = new Stops("St andrews, bus station");
        Stops stop2 = new Stops("St andrews, market street");
        timeTable.add(new Route("91 to St andrews, bus station", new ArrayList<Stops>(Arrays.asList(stop1, stop2))));
        Mockito.when(travelService.allRoutesAtStop("st andrews, bus station")).thenReturn(timeTable);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/route/St andrews");
        MvcResult result = null;
        try {
            result = mockMvc.perform(requestBuilder).andReturn();
        } catch (Exception e) {
            System.out.println(e);
        }

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    @Test
    public void shouldAddStop() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("stopName", "St andrews, union building");
            jsonObject.put("routeName", "91 to morrisons");
            jsonObject.put("beforeStopName", "St andrews, market street");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        try {
            Mockito.when(travelService.addStopToRoute("St andrews, union building", "91 to morrisons", "St andrews, market street")).thenReturn("Stop already exits in route 91 to morrisons");
        } catch (InvalidStopException e) {
            System.out.println(e);
        }
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/route").content(jsonObject.toString()).contentType(APPLICATION_JSON);
        MvcResult result = null;
        try {
            result = mockMvc.perform(requestBuilder).andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}
