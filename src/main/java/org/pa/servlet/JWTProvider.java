/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.servlet;

import java.io.IOException;
import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.servlet.ServletConfig;
import static org.pa.definitions.RequestDefinitions.JWT_ATTR_KEY;

/**
 * Servlet implementation class JWTProvider
 */
@WebServlet("/JWTProvider")
public class JWTProvider extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String jwt_key;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public JWTProvider() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwt_key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, sigAlg.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setSubject(request.getUserPrincipal().getName())
                .signWith(sigAlg, signingKey);

        response.getWriter().write(builder.compact());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); //To change body of generated methods, choose Tools | Templates.
        jwt_key = (String) config.getServletContext().getAttribute(JWT_ATTR_KEY);
    }

}
