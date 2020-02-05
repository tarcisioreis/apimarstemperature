package com.apimars.apimarstemperature.controller;

import com.apimars.apimarstemperature.config.ConfigProperties;
import com.apimars.apimarstemperature.constantes.Constantes;
import com.apimars.apimarstemperature.dto.DataDTO;
import com.apimars.apimarstemperature.exceptions.BusinessException;
import com.apimars.apimarstemperature.service.MarsTemperatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Constantes.PATH_ENDPOINT_TEMPERATURE)
@CrossOrigin(origins="*")
@Api(value=Constantes.API_DESCRIPTION)
@SuppressWarnings({"squid:S1854"})
public class MarsTemperatureController {

    private final ConfigProperties configProperties;
    private final MarsTemperatureService marsTemperatureService;

    public MarsTemperatureController(ConfigProperties configProperties, MarsTemperatureService marsTemperatureService) {
        this.configProperties = configProperties;
        this.marsTemperatureService = marsTemperatureService;
    }

    @GetMapping(Constantes.ENDPOINT_TEMPERATURE)
    @ApiOperation(value=Constantes.ENDPOINT_DESCRIPTION)
    public ResponseEntity<List<DataDTO>> temperature(@Valid @RequestParam(required = false, name = "sol") Integer sol) {
        List<DataDTO> lista = null;
        HttpStatus status = HttpStatus.OK;

        try {
            if (sol != null) {
                if (sol <= 0) {
                    status = HttpStatus.BAD_REQUEST;
                    throw new BusinessException(configProperties.getERROR_INVALID_PARAMETER());
                }
            } else {
                status = HttpStatus.BAD_REQUEST;
                throw new BusinessException(configProperties.getERROR_INVALID_PARAMETER());
            }

            lista = marsTemperatureService.getTemperature(sol);

            if (lista == null) {
                status = HttpStatus.NOT_FOUND;
                throw new BusinessException(configProperties.getERROR_RETURN_ENDPOINT());
            }

        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return new ResponseEntity<>(lista, status);
    }

}
