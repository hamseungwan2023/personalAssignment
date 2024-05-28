package com.sparta.personalassignment.Exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j(topic = "error")
public class SetErrorResponse {

    public void setErrorMessage(HttpServletResponse response, String errorMessage) {
        response.setStatus(response.getStatus());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(Map.of("error", errorMessage));
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            log.error("Error writing error response", e);
        }
    }
}
