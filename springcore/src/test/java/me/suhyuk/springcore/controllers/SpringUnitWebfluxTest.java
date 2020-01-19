package me.suhyuk.springcore.controllers;

import me.suhyuk.springcore.entities.VehicleDetails;
import me.suhyuk.springcore.services.UserVehicleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebFluxTest
public class SpringUnitWebfluxTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserVehicleService userVehicleService;

    @Test
    public void testWebClient() {
        given(userVehicleService.getVehicleDetails("sboot"))
                .willReturn(new VehicleDetails("Honda", "Civic"));

        webTestClient.get().uri("/sboot/vehicle").accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Honda,Civic");
    }

}
