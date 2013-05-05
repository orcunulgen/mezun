package com.orcun.mezun.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class MyURLUtil {

	/**
	 * <p>Determines the Base URL, e.g.,
	 * {@literal http://localhost:8080/myApplication} from the
	 * {@link FacesContext}.</p>
	 *
	 * @param facesContext The {@link FacesContext} to examine.
	 * @return the base URL.
	 * @throws MalformedURLException if an exception occurs during parsing of
	 * the URL.
	 * @since 1.3
	 */
	public static String getBaseURL(final FacesContext facesContext) throws MalformedURLException {
	    return getBaseURL(facesContext.getExternalContext());
	}
	 
	/**
	 * <p>Determines the Base URL, e.g.,
	 * {@literal http://localhost:8080/myApplication} from the
	 * {@link ExternalContext}.</p>
	 *
	 * @param externalContext The {@link ExternalContext} to examine.
	 * @return the base URL.
	 * @throws MalformedURLException if an exception occurs during parsing of
	 * the URL.
	 * @since 1.3
	 */
	public static String getBaseURL(final ExternalContext externalContext) throws MalformedURLException {
	    return getBaseURL((HttpServletRequest) externalContext.getRequest());
	}
	 
	/**
	 * <p>Determines the Base URL, e.g.,
	 * {@literal http://localhost:8080/myApplication} from the
	 * {@link HttpServletRequest}.</p>
	 *
	 * @param request The {@link HttpServletRequest} to examine.
	 * @return the base URL.
	 * @throws MalformedURLException if an exception occurs during parsing of
	 * the URL.
	 * @see URL
	 * @since 1.3
	 */
	public static String getBaseURL(final HttpServletRequest request) throws MalformedURLException {
	    return new URL(request.getScheme(),
	            request.getServerName(),
	            request.getServerPort(),
	            request.getContextPath()).toString();
	}
}
