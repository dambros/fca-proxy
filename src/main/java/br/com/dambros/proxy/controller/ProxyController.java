package br.com.dambros.proxy.controller;

import com.mashape.unirest.http.Unirest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ProxyController {

    private static final String FCA_ADDRESS = "http://mscs-servicesh.portalfiat.com.br/mscRestService";

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public String create(HttpServletRequest request) throws Exception {
        String url = FCA_ADDRESS + request.getRequestURI();
        String header1 = "clientUser";
        String header2 = "clientToken";
        String getRequest = Unirest.get(url)
                .header(header1, request.getHeader(header1))
                .header(header2, request.getHeader(header2))
                .asJson().getBody().toString();
        return getRequest;
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> e = request.getHeaderNames();
        while (e.hasMoreElements()) {
            String param = e.nextElement();
            map.put(param, request.getHeader(param));
        }
        return map;
    }
}
