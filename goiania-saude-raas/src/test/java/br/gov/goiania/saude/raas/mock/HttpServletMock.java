package br.gov.goiania.saude.raas.mock;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mockito.Mockito;

public final class HttpServletMock {

    private HttpServletMock() { }

    public static HttpServletRequest requestComQueryString(String queryString) {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getQueryString()).thenReturn(queryString);
        Mockito.when(request.getMethod()).thenReturn("GET");
        Mockito.when(request.getRequestURI()).thenReturn("/api/teste");
        return request;
    }

    public static HttpServletRequest requestSemQueryString() {
        return requestComQueryString(null);
    }

    public static HttpServletResponse responseComStatus(int status) {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(response.getStatus()).thenReturn(status);
        return response;
    }
}

