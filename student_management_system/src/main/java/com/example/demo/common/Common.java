package com.example.demo.common;

import javax.xml.bind.DatatypeConverter;

import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class Common {

	private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

	public String getRoleByToken(HttpServletRequest req) {
		String token = req.getHeader("Authorization");
		if (token == null || !token.startsWith("Bearer ")) {
			return null;
		}
		token = token.substring(7);
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret))
					.parseClaimsJws(token).getBody();
			return claims.getSubject();

		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}
}
