package com.example.ms_leaderboard.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_leaderboard.dto.AtletaDTO;

@FeignClient(name = "ms-atletas", path = "/api/v1/atletas")
public interface AtletaClient {

    @GetMapping("/{rut}")
    AtletaDTO obtenerAtletaPorRut(@PathVariable("rut") String rut);
}
