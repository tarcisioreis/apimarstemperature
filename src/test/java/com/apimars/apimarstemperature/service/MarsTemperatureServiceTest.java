package com.apimars.apimarstemperature.service;

import com.apimars.apimarstemperature.config.ConfigProperties;
import com.apimars.apimarstemperature.dto.DataDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ConfigProperties.class)
@TestPropertySource(value = "file:./src/main/resources/application.properties")
public class MarsTemperatureServiceTest {

    @InjectMocks
    private MarsTemperatureService service;

    @Autowired
    private ConfigProperties configProperties;

    @Before
    public void init() {
        service = new MarsTemperatureService(configProperties);
    }

    @Test
    public void successSearchSOL() throws IOException {
        List<DataDTO> lista = service.getTemperature(416);

        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void success() throws IOException {
        List<DataDTO> lista = service.getTemperature(null);

        assertNotNull(lista);
        assertEquals(7, lista.size());
    }

    @Test
    public void fail() throws IOException {
        List<DataDTO> lista = service.getTemperature(anyInt());

        assertNull(lista);
    }

    @Test
    public void failSearchSOL() throws IOException {
        List<DataDTO> lista = service.getTemperature(600);

        assertNull(lista);
    }

}